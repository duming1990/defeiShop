package com.ebiz.webapp.web.struts.manager.admin;

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
import com.ebiz.webapp.domain.CardApply;
import com.ebiz.webapp.domain.CardGenHis;
import com.ebiz.webapp.domain.CardInfo;
import com.ebiz.webapp.domain.CardPIndex;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.DESPlus;
import com.ebiz.webapp.web.util.EncryptUtilsV2;

public class WelfareCardAction extends BaseAdminAction {
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

		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String gen_no_like = (String) dynaBean.get("gen_no_like");
		String st_add_date = (String) dynaBean.get("st_add_date");
		String en_add_date = (String) dynaBean.get("en_add_date");

		CardGenHis entity = new CardGenHis();
		super.copyProperties(entity, form);
		entity.getMap().put("gen_no_like", gen_no_like);
		entity.getMap().put("st_add_date", st_add_date);
		entity.getMap().put("en_add_date", en_add_date);
		Integer recordCount = getFacade().getCardGenHisService().getCardGenHisCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<CardGenHis> entityList = super.getFacade().getCardGenHisService().getCardGenHisPaginatedList(entity);
		if (null != entityList && entityList.size() > 0) {
			for (CardGenHis temp : entityList) {
				CardInfo card = new CardInfo();
				card.setGen_id(temp.getId());
				List<CardInfo> cardList = getFacade().getCardInfoService().getCardInfoList(card);
				temp.getMap().put("cardList", cardList);
			}
		}
		request.setAttribute("entityList", entityList);
		return mapping.findForward("list");
	}

	public ActionForward cardList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "0")) {
			return super.checkPopedomInvalid(request, response);
		}

		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
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

		Integer recordCount = getFacade().getCardInfoService().getCardInfoCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<CardInfo> entityList = super.getFacade().getCardInfoService().getCardInfoPaginatedList(entity);
		if (null != entityList && entityList.size() > 0) {
			for (CardInfo temp : entityList) {
				DESPlus DES = new DESPlus();
				String card_pwd = DES.decrypt(temp.getCard_pwd());
				temp.getMap().put("card_pwd", card_pwd);
			}
		}
		request.setAttribute("entityList", entityList);
		return new ActionForward("/../manager/admin/WelfareCard/card_list.jsp");

	}

	public ActionForward toExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		Map<String, Object> model = new HashMap<String, Object>();

		String gen_id = (String) dynaBean.get("gen_id");
		String card_no_like = (String) dynaBean.get("card_no_like");
		String code = (String) dynaBean.get("code");

		if (StringUtils.isBlank(gen_id)) {
			super.showMsgForManager(request, response, "参数错误！");
			return null;
		}

		CardInfo entity = new CardInfo();
		super.copyProperties(entity, form);
		entity.setGen_id(Integer.valueOf(gen_id));
		entity.getMap().put("card_no_like", card_no_like);
		List<CardInfo> entityList = super.getFacade().getCardInfoService().getCardInfoList(entity);
		if (null != entityList && entityList.size() > 0) {
			for (CardInfo temp : entityList) {
				DESPlus DES = new DESPlus();
				String card_pwd = DES.decrypt(temp.getCard_pwd());
				temp.getMap().put("card_pwd", card_pwd);
			}
		}
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
		return new ActionForward("/../manager/admin/WelfareCard/apply_view.jsp");
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String mod_id = (String) dynaBean.get("mod_id");
		String gen_id = (String) dynaBean.get("gen_id");
		String is_del = (String) dynaBean.get("is_del");

		if (StringUtils.isBlank(gen_id) || StringUtils.isBlank(is_del)) {
			String msg = "参数不能为空";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');window.close();}");
			return null;
		}

		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		Date date = new Date();
		if (StringUtils.isNotBlank(gen_id)) {
			CardGenHis entity = new CardGenHis();
			entity.setId(Integer.valueOf(gen_id));
			entity.setIs_del(Integer.valueOf(is_del));
			entity.setModify_date(date);
			entity.setModify_uid(ui.getId());
			entity.getMap().put("update_cardInfo_is_del", true);
			getFacade().getCardGenHisService().modifyCardGenHis(entity);
		}
		saveMessage(request, "entity.updated");
		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(super.serialize(request, "cls_id", "id", "method")));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward stop(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String is_del = (String) dynaBean.get("is_del");

		JSONObject data = new JSONObject();
		String code = "0", msg = "";

		if (StringUtils.isBlank(id) || StringUtils.isBlank(is_del)) {
			msg = "参数不能为空";
			data.put("code", code);
			data.put("msg", msg);
			super.renderJson(response, data.toString());
			return null;
		}

		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		Date date = new Date();
		CardInfo entity = new CardInfo();
		entity.setId(Integer.valueOf(id));
		entity.setIs_del(Integer.valueOf(is_del));
		getFacade().getCardInfoService().modifyCardInfo(entity);

		msg = "操作成功！";
		data.put("code", 1);
		data.put("msg", msg);
		super.renderJson(response, data.toString());
		return null;
	}
}
