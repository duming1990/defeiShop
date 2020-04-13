package com.ebiz.webapp.web.struts.manager.customer;

import java.io.File;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.alibaba.fastjson.JSONObject;
import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.ssi.web.struts.bean.UploadFile;
import com.ebiz.webapp.domain.CardApply;
import com.ebiz.webapp.domain.CardGenHis;
import com.ebiz.webapp.domain.CardInfo;
import com.ebiz.webapp.domain.CardPIndex;
import com.ebiz.webapp.domain.ServiceCenterInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.util.ExcelUtils;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.DESPlus;
import com.ebiz.webapp.web.util.EncryptUtilsV2;

public class WelfareCardAction extends BaseCustomerAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "0")) {
			return super.checkPopedomInvalid(request, response);
		}

		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}

		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String gen_no_like = (String) dynaBean.get("gen_no_like");
		String st_add_date = (String) dynaBean.get("st_add_date");
		String en_add_date = (String) dynaBean.get("en_add_date");

		if (ui.getIs_fuwu().intValue() == 1) {
			ServiceCenterInfo service = new ServiceCenterInfo();
			service.setAdd_user_id(ui.getId());
			service.setIs_del(0);
			service = getFacade().getServiceCenterInfoService().getServiceCenterInfo(service);
			if (null != service) {
				CardGenHis entity = new CardGenHis();
				super.copyProperties(entity, form);
				entity.setOwn_service_id(service.getId());
				entity.getMap().put("gen_no_like", gen_no_like);
				entity.getMap().put("st_add_date", st_add_date);
				entity.getMap().put("en_add_date", en_add_date);
				Integer recordCount = getFacade().getCardGenHisService().getCardGenHisCount(entity);
				pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
				entity.getRow().setFirst(pager.getFirstRow());
				entity.getRow().setCount(pager.getRowCount());

				List<CardGenHis> entityList = super.getFacade().getCardGenHisService()
						.getCardGenHisPaginatedList(entity);
				if (null != entityList && entityList.size() > 0) {
					for (CardGenHis temp : entityList) {
						CardInfo card = new CardInfo();
						card.setGen_id(temp.getId());
						List<CardInfo> cardList = getFacade().getCardInfoService().getCardInfoList(card);
						temp.getMap().put("cardList", cardList);
					}
				}
				request.setAttribute("entityList", entityList);
			}
		}
		return mapping.findForward("list");
	}

	public ActionForward cardList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String gen_id = (String) dynaBean.get("gen_id");
		String mod_id = (String) dynaBean.get("mod_id");
		String card_type = (String) dynaBean.get("card_type");
		String card_no_like = (String) dynaBean.get("card_no_like");

		if (StringUtils.isBlank(gen_id)) {
			super.showMsgForManager(request, response, "参数错误！");
			return null;
		}

		CardInfo entity = new CardInfo();
		super.copyProperties(entity, form);
		entity.setGen_id(Integer.valueOf(gen_id));
		entity.getMap().put("card_no_like", card_no_like);

		Integer recordCount = getFacade().getCardInfoService().getCardInfoCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<CardInfo> entityList = super.getFacade().getCardInfoService().getCardInfoPaginatedList(entity);
		if (null != entityList && entityList.size() > 0) {
			for (CardInfo temp : entityList) {
				DESPlus DES = new DESPlus();
				String card_pwd = DES.decrypt(temp.getCard_pwd());
				// card_pwd = card_pwd.replace(card_pwd.substring(1, card_pwd.length() - 1), "****");
				temp.getMap().put("card_pwd", card_pwd);
			}
		}
		request.setAttribute("entityList", entityList);
		request.setAttribute("card_type", card_type);

		return new ActionForward("/../manager/customer/WelfareCard/card_list.jsp");

	}

	public ActionForward senMsg(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String id = (String) dynaBean.get("id");

		if (StringUtils.isBlank(id)) {
			super.showMsgForManager(request, response, "参数错误！");
			return null;
		}

		CardInfo entity = new CardInfo();
		entity.setId(Integer.valueOf(id));
		entity.setIs_del(0);
		entity.setCard_state(0);
		entity = super.getFacade().getCardInfoService().getCardInfo(entity);
		if (entity == null) {
			super.showMsgForManager(request, response, "该卡不存在或已激活");
			return null;
		}
		super.copyProperties(form, entity);
		DESPlus desPlus = new DESPlus();
		request.setAttribute("pwd", desPlus.decrypt(entity.getCard_pwd()));
		return new ActionForward("/../manager/customer/WelfareCard/msg_active.jsp");
	}

	public ActionForward sendMsgLot(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String gen_id = (String) dynaBean.get("gen_id");

		if (StringUtils.isBlank(gen_id)) {
			super.showMsgForManager(request, response, "参数错误！");
			return null;
		}

		CardInfo entity = new CardInfo();
		entity.setGen_id(Integer.valueOf(gen_id));
		entity.setIs_del(0);
		entity.setCard_state(0);
		entity.setIs_send(0);
		int count = super.getFacade().getCardInfoService().getCardInfoCount(entity);
		if (count <= 0) {
			super.showMsgForManager(request, response, "该批次已没有可发放福利卡！");
			return null;
		}
		request.setAttribute("count", count);
		CardGenHis cardGenHis = new CardGenHis();
		cardGenHis.setId(Integer.valueOf(gen_id));
		cardGenHis.setIs_del(0);
		cardGenHis = getFacade().getCardGenHisService().getCardGenHis(cardGenHis);
		if (null == cardGenHis) {
			super.showMsgForManager(request, response, "该批次生成的福利卡有误！");
			return null;
		}
		super.copyProperties(form, cardGenHis);
		return new ActionForward("/../manager/customer/WelfareCard/batchActive.jsp");
	}

	public ActionForward getApply(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "0")) {
			return super.checkPopedomInvalid(request, response);
		}

		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (StringUtils.isBlank(id)) {
			super.showMsgForManager(request, response, "参数错误！");
			return null;
		}

		CardApply entity = new CardApply();
		entity.setId(Integer.valueOf(id));
		entity = super.getFacade().getCardApplyService().getCardApply(entity);
		if (null != entity) {
			CardPIndex cPIndex = new CardPIndex();
			cPIndex.setCard_apply_id(entity.getId());
			List<CardPIndex> list = getFacade().getCardPIndexService().getCardPIndexList(cPIndex);
			request.setAttribute("list", list);
			super.copyProperties(form, entity);
		}
		return new ActionForward("/../manager/customer/WelfareCard/apply_view.jsp");

	}

	public ActionForward msgActive(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		JSONObject data = new JSONObject();
		String code = "0", msg = "参数错误！";
		if (StringUtils.isBlank(id)) {
			data.put("code", code);
			data.put("msg", msg);
			super.renderJson(response, data.toString());
			return null;
		}

		CardInfo entity = new CardInfo();
		entity.setId(Integer.valueOf(id));
		entity.setIs_del(0);
		entity.setCard_state(0);
		entity = super.getFacade().getCardInfoService().getCardInfo(entity);
		if (null == entity) {
			msg = "福利卡不存在或已激活！";
			data.put("code", code);
			data.put("msg", msg);
			super.renderJson(response, data.toString());
			return null;
		}
		DESPlus desPlus = new DESPlus();

		StringBuffer message = new StringBuffer("");
		message.append("{\"name\":\"" + entity.getCard_no() + "\"");
		message.append(",\"code\":\"" + desPlus.decrypt(entity.getCard_pwd()) + "\"");

		CardInfo card = new CardInfo();
		super.copyProperties(card, form);
		card.setId(Integer.valueOf(id));
		card.setIs_send(1);
		card.getMap().put("send_msg", message);
		int flag = getFacade().getCardInfoService().modifyCardInfo(card);
		if (flag > 0) {
			code = "1";
			msg = "短信已发送！";
			data.put("code", code);
			data.put("msg", msg);
			super.renderJson(response, data.toString());
		}
		msg = "发送失败！";
		data.put("code", code);
		data.put("msg", msg);
		super.renderJson(response, data.toString());
		return null;
	}

	public ActionForward msgBatchActive(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String send_num = (String) dynaBean.get("send_num");
		String mod_id = (String) dynaBean.get("mod_id");
		String id = (String) dynaBean.get("id");
		String remark = (String) dynaBean.get("remark");

		String msg = "";
		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		if (StringUtils.isBlank(send_num)) {
			msg = "请填写发放数量";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}

		CardInfo entity = new CardInfo();
		entity.setGen_id(Integer.valueOf(id));
		entity.setIs_del(0);
		entity.setCard_state(0);
		entity.setIs_send(0);
		List<CardInfo> cardList = getFacade().getCardInfoService().getCardInfoList(entity);
		if (cardList == null || cardList.size() <= 0) {
			super.showMsgForManager(request, response, "该批次已没有可发放福利卡！");
			return null;
		}

		if (cardList.size() < Integer.valueOf(send_num)) {
			super.showMsgForManager(request, response, "该批次可发放福利卡数量不足！");
			return null;
		}
		logger.info("============saveExcel====");
		String uploadDir = StringUtils.join(new String[] { "files", "import", "" }, File.separator);
		List<UploadFile> uploadFileList = super.uploadFile(form, uploadDir, false, false, null);
		// List<UploadFile> uploadFileList = super.uploadFile(form, uploadDir,
		// false);
		String fileSavePath = "";
		String filePath = "";
		for (UploadFile uploadFile : uploadFileList) {
			// logger.info("==========uploadFile.getFormName()====" +
			// uploadFile.getFormName());
			if ("file_show".equals(uploadFile.getFormName())) {
				fileSavePath = uploadFile.getFileSavePath();
			}
			if ("file_path".equals(uploadFile.getFormName())) {
				filePath = uploadFile.getFileSavePath();
			}
		}
		String ctxDir = getServlet().getServletContext().getRealPath(String.valueOf(File.separatorChar));
		if (!ctxDir.endsWith(String.valueOf(File.separatorChar))) {
			ctxDir = ctxDir + File.separatorChar;
		}
		// fileSavePath = fileSavePath.replaceAll("/", "\\\\");
		logger.info("#########################################:" + ctxDir + fileSavePath);
		if (StringUtils.isBlank(fileSavePath)) {
			msg = "请上传文件";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}
		// try {
		String[] rowLines = ExcelUtils.readExcelRowLines(ctxDir + fileSavePath, 0, 1);
		logger.info("rowLines长度" + rowLines.length);
		if (null == rowLines || rowLines.length == 0) {
			msg = "长度为0";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}

		String[] mobile = new String[Integer.valueOf(send_num)];
		String[] name = new String[Integer.valueOf(send_num)];

		int h = 0;
		for (int i = 0; i < rowLines.length; i++) {
			logger.info("===当前循环i:" + i);
			if (StringUtils.isBlank(rowLines[i])) {
				continue;
			}
			logger.info("当前temp:" + rowLines[i]);
			String values[] = StringUtils.split(rowLines[i], ExcelUtils.EXCEL_LINE_DELIMITER);
			logger.info("===values[0]:" + values[0]);
			logger.info("===values.length:" + values.length);
			if (values.length == 0) {
				continue;
			}
			if (StringUtils.isBlank(values[0])) {
				break;
			}
			if (StringUtils.isBlank(values[1])) {
				msg = "第" + (i + 1) + "行，手机号码为空";
				super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
				return null;
			}
			if (StringUtils.isBlank(values[2])) {
				msg = "第" + (i + 1) + "行，姓名为空";
				super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
				return null;
			}
			if (h >= Integer.valueOf(send_num)) {
				msg = "导入的用户数量大于发放数量";
				super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
				return null;
			}

			mobile[h] = values[1];
			name[h] = values[2];
			h++;
		}

		if (Integer.valueOf(send_num) - h != 0) {
			msg = "发放数量与导入用户数量不相等";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}
		DESPlus desPlus = new DESPlus();
		for (int j = 0; j < Integer.valueOf(send_num); j++) {
			StringBuffer message = new StringBuffer("");
			message.append("{\"name\":\"" + cardList.get(j).getCard_no() + "\"");
			message.append(",\"code\":\"" + desPlus.decrypt(cardList.get(j).getCard_pwd()) + "\"");

			CardInfo cInfo = new CardInfo();
			cInfo.setId(cardList.get(j).getId());
			cInfo.setIs_send(1);
			cInfo.setReceived_mobile(mobile[j]);
			cInfo.setReceived_name(name[j]);
			cInfo.setRemark(remark);
			cInfo.getMap().put("send_msg", message);
			int flag = getFacade().getCardInfoService().modifyCardInfo(cInfo);
			if (flag <= 0) {
				msg = "第" + (j + 1) + "行，福利卡发放失败";
				super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
				return null;
			}
		}
		saveMessage(request, "entity.sended");
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&");
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		return forward;
	}

	public ActionForward toExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='../../login.shtml'}");
			return null;
		}

		DynaBean dynaBean = (DynaBean) form;
		Map<String, Object> model = new HashMap<String, Object>();
		String code = (String) dynaBean.get("code");

		getsonSysModuleList(request, dynaBean);

		String gen_id = (String) dynaBean.get("gen_id");
		String card_no_like = (String) dynaBean.get("card_no_like");

		if (StringUtils.isBlank(gen_id)) {
			super.showMsgForManager(request, response, "参数错误！");
			return null;
		}

		CardInfo entity = new CardInfo();
		super.copyProperties(entity, form);
		entity.setGen_id(Integer.valueOf(gen_id));
		entity.getMap().put("card_no_like", card_no_like);

		List<CardInfo> entityList = super.getFacade().getCardInfoService().getCardInfoPaginatedList(entity);

		model.put("entityList", entityList);
		model.put("title", "福利卡导出_日期" + sdFormat_ymd.format(new Date()));

		String content = getFacade().getTemplateService().getContent("WelfareCard/list.ftl", model);
		String fname = EncryptUtilsV2.encodingFileName("福利卡导出_日期" + sdFormat_ymd.format(new Date()) + ".xls");

		if (StringUtils.isBlank(code)) {
			code = "UTF-8";
		}
		response.setCharacterEncoding(code);
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename=" + fname);

		PrintWriter out = response.getWriter();
		out.println(content);
		out.flush();
		out.close();
		return null;
	}

}
