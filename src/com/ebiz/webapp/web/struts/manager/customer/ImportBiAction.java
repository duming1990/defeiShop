package com.ebiz.webapp.web.struts.manager.customer;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.ssi.web.struts.bean.UploadFile;
import com.ebiz.webapp.domain.BaseData;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.CommInfoPoors;
import com.ebiz.webapp.domain.CommInfoTags;
import com.ebiz.webapp.domain.CommTczhPrice;
import com.ebiz.webapp.domain.EntpCommClass;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.Freight;
import com.ebiz.webapp.domain.ImportBi;
import com.ebiz.webapp.domain.ImportBiSon;
import com.ebiz.webapp.domain.PdImgs;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.util.ExcelUtils;
import com.ebiz.webapp.web.Keys;

/**
 * @author minyg
 * @version 2013-09-26
 */
public class ImportBiAction extends BaseCustomerAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(Keys.SESSION_USERINFO_KEY);
		if (null == userInfo) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response,
					"window.onload=function(){alert('" + msg + "');location.href='login.shtml'}");
			return null;
		}
		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);
		Pager pager = (Pager) dynaBean.get("pager");
		String st_add_date = (String) dynaBean.get("st_add_date");
		String en_add_date = (String) dynaBean.get("en_add_date");
		ImportBi entity = new ImportBi();
		super.copyProperties(entity, form);
		entity.setIs_del(0);
		entity.setAdd_user_id(userInfo.getId());
		entity.getMap().put("st_add_date", st_add_date);
		entity.getMap().put("en_add_date", en_add_date);
		Integer recordCount = getFacade().getImportBiService().getImportBiCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());
		List<ImportBi> entityList = super.getFacade().getImportBiService().getImportBiPaginatedList(entity);
		request.setAttribute("entityList", entityList);
		return mapping.findForward("list");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (isCancelled(request)) {
			return list(mapping, form, request, response);
		}
		if (!isTokenValid(request)) {
			saveError(request, "errors.token");
			return list(mapping, form, request, response);
		}
		resetToken(request);
		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response,
					"window.onload=function(){alert('" + msg + "');location.href='login.shtml'}");
			return null;
		}
		UserInfo userInfoTemp = super.getUserInfo(ui.getId());
		DynaBean dynaBean = (DynaBean) form;
		String mod_id = (String) dynaBean.get("mod_id");
		String id = (String) dynaBean.get("id");
		if (!GenericValidator.isInt(id)) {
			String msg = "id参数不正确！";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		ImportBi entity = new ImportBi();
		super.copyProperties(entity, form);
		entity.setAudit_date(new Date());
		entity.setAudit_user_id(userInfoTemp.getId());
		entity.setAudit_user_name(userInfoTemp.getUser_name());
		entity.getMap().put("insert_bi", "true");
		super.getFacade().getImportBiService().modifyImportBi(entity);
		saveMessage(request, "entity.updated");
		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String[] pks = (String[]) dynaBean.get("pks");
		String mod_id = (String) dynaBean.get("mod_id");
		if (StringUtils.isBlank(id) && ArrayUtils.isEmpty(pks)) {
			String msg = super.getMessage(request, "参数不能为空");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}
		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);
		ImportBi entity = new ImportBi();
		Date date = new Date();
		entity.setIs_del(1);
		int i = 0;
		if (StringUtils.isNotBlank(id) && GenericValidator.isLong(id)) {
			entity.setId(Integer.valueOf(id));
			entity.getMap().put("del_son", "true");
			i = super.getFacade().getImportBiService().removeImportBi(entity);
			saveMessage(request, "entity.deleted");
		} else if (ArrayUtils.isNotEmpty(pks)) {
			entity.getMap().put("pks", pks);
			i = super.getFacade().getImportBiService().removeImportBi(entity);
			saveMessage(request, "entity.deleted");
		}
		if (i == -1) {
			String msg = "删除失败。删除对象已审核通过或不存在。";
			super.renderJavaScript(response,
					"window.onload=function(){alert('" + msg + "');location.href='login.shtml'}");
			return null;
		}
		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(super.serialize(request, "id", "method")));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(Keys.SESSION_USERINFO_KEY);
		if (null == userInfo) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response,
					"window.onload=function(){alert('" + msg + "');location.href='login.shtml'}");
			return null;
		}
		DynaBean dynaBean = (DynaBean) form;
		saveToken(request);
		getsonSysModuleList(request, dynaBean);
		String id = (String) dynaBean.get("id");
		if (StringUtils.isBlank(id)) {
			String msg = super.getMessage(request, "参数不能为空");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}
		if (!GenericValidator.isLong(id)) {
			saveError(request, "errors.Integer", id);
			return mapping.findForward("list");
		}
		super.getSessionId(request);
		CommInfo entity = new CommInfo();
		entity.setId(Integer.valueOf(id));
		entity = getFacade().getCommInfoService().getCommInfo(entity);
		if (null == entity) {
			saveMessage(request, "entity.missing");
			return mapping.findForward("list");
		}
		List<PdImgs> CommImgsList = entity.getCommImgsList();
		for (int i = 1; i <= CommImgsList.size(); i++) {
			dynaBean.set("base_files" + i + "_file", CommImgsList.get(i - 1).getFile_path());
		}
		int CommImgsListCount = 0;
		if (CommImgsList != null) {
			CommImgsListCount = CommImgsList.size();
		}
		request.setAttribute("CommImgsListCount", CommImgsListCount);
		if (CommImgsList != null) {
			if (CommImgsList.size() < 5) {// 添加CommImgsList
				for (int i = 0; i < (5 - CommImgsListCount); i++) {
					PdImgs pdImgs = new PdImgs();
					CommImgsList.add(pdImgs);
				}
			}
		}
		// 重新赛入CommImgsList
		entity.setPdImgsList(CommImgsList);
		request.setAttribute("CommImgsList", CommImgsList);
		if (null != entity.getP_index()) {
			super.setprovinceAndcityAndcountryToFrom(dynaBean, entity.getP_index());
		}
		// 物流
		if (null != entity.getFreight_id()) {
			Freight fre = super.getFreightInfo(entity.getFreight_id());
			if (null != fre) {
				dynaBean.set("fre_title", fre.getFre_title());
			}
		}
		EntpInfo entpInfo = new EntpInfo();
		entpInfo.setId(entity.getOwn_entp_id());
		entpInfo = super.getFacade().getEntpInfoService().getEntpInfo(entpInfo);
		String entp_name = entpInfo.getEntp_name();
		dynaBean.set("entp_name", entp_name);
		dynaBean.set("is_self_support_entp", entpInfo.getIs_self_support());
		dynaBean.set("qq", entpInfo.getQq());
		request.setAttribute("entity", entity);
		entity.setQueryString(super.serialize(request, "id", "method"));
		super.copyProperties(form, entity);
		// 套餐管理
		CommTczhPrice param_ctp = new CommTczhPrice();
		param_ctp.setComm_id(id);
		List<CommTczhPrice> list_CommTczhPrice = super.getFacade().getCommTczhPriceService()
				.getCommTczhPriceList(param_ctp);
		request.setAttribute("list_CommTczhPrice", list_CommTczhPrice);
		// 商品频道
		CommInfoTags commInfoTags = new CommInfoTags();
		commInfoTags.setComm_id(Integer.valueOf(id));
		List<CommInfoTags> commInfoTagsList = super.getFacade().getCommInfoTagsService()
				.getCommInfoTagsList(commInfoTags);
		if (null != commInfoTagsList && commInfoTagsList.size() > 0) {
			String tag_ids_str = ",";
			for (CommInfoTags t : commInfoTagsList) {
				tag_ids_str += t.getTag_id().toString() + ",";
			}
			request.setAttribute("tag_ids_str", tag_ids_str);
		}
		// 扶贫对象
		CommInfoPoors commInfoPoors = new CommInfoPoors();
		commInfoPoors.setComm_id(Integer.valueOf(id));
		List<CommInfoPoors> commInfoPoorsList = super.getFacade().getCommInfoPoorsService()
				.getCommInfoPoorsList(commInfoPoors);
		if (null != commInfoPoorsList && commInfoPoorsList.size() > 0) {
			String temp_poor_ids = ",";
			for (CommInfoPoors temp : commInfoPoorsList) {
				temp_poor_ids += temp.getPoor_id().toString() + ",";
			}
			request.setAttribute("temp_poor_ids", temp_poor_ids);
		}
		request.setAttribute("commInfoPoorsList", commInfoPoorsList);
		// 一个商品最多扶贫对象数量
		BaseData baseData1901 = super.getBaseData(Keys.BASE_DATA_ID.BASE_DATA_ID_1901.getIndex());
		request.setAttribute("baseData1901", baseData1901);
		EntpCommClass CommClass = new EntpCommClass();// 商家分类
		CommClass.setEntp_id(entpInfo.getId());
		CommClass.setIs_del(0);
		List<EntpCommClass> commClasslist = super.getFacade().getEntpCommClassService().getEntpCommClassList(CommClass);
		request.setAttribute("commClasslist", commClasslist);
		return mapping.findForward("input");
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(Keys.SESSION_USERINFO_KEY);
		if (null == userInfo) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response,
					"window.onload=function(){alert('" + msg + "');location.href='login.shtml'}");
			return null;
		}
		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);
		String id = (String) dynaBean.get("id");
		if (StringUtils.isBlank(id)) {
			String msg = super.getMessage(request, "参数不能为空");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}
		ImportBi entity = new ImportBi();
		entity.setId(Integer.valueOf(id));
		entity = getFacade().getImportBiService().getImportBi(entity);
		if (null == entity) {
			String msg = "对象不存在";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}
		super.copyProperties(form, entity);
		ImportBiSon son = new ImportBiSon();
		son.setLink_id(entity.getId());
		List<ImportBiSon> sonList = getFacade().getImportBiSonService().getImportBiSonList(son);
		request.setAttribute("sonList", sonList);
		return mapping.findForward("view");
	}

	public ActionForward importExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		UserInfo userInfo = super.getUserInfo(1);
		request.setAttribute("adminUser", userInfo);
		return new ActionForward("/../manager/customer/ImportBi/import.jsp");
	}

	public ActionForward saveExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String mod_id = (String) dynaBean.get("mod_id");
		String remark = (String) dynaBean.get("remark");
		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);
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
			String msg = "请上传文件";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}
		// try {
		String[] rowLines = ExcelUtils.readExcelRowLines(ctxDir + fileSavePath, 0, 1);
		logger.info("rowLines长度" + rowLines.length);
		if (null == rowLines || rowLines.length == 0) {
			String msg = "长度为0";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}
		ImportBi entity = new ImportBi();
		super.copyProperties(entity, form);
		entity.setAdd_date(new Date());
		entity.setAdd_user_id(ui.getId());
		entity.setAdd_user_name(ui.getUser_name());
		entity.setAudit_state(Keys.audit_state.audit_state_0.getIndex());
		entity.setIndex(entity.getAdd_date().getTime() + "");
		List<ImportBiSon> sonList = new ArrayList<ImportBiSon>();
		BigDecimal sumBi = new BigDecimal(0);
		int win_count = 0;
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
			// if (values.length != 4) {
			// String msg = "第" + (i + 1) + "行数据不正确，应为4列数据";
			// super.renderJavaScript(response,
			// "window.onload=function(){alert('" + msg +
			// "');history.back();}");
			// return null;
			// }
			if (StringUtils.isBlank(values[0])) {
				break;
			}
			if (StringUtils.isBlank(values[2])) {
				String msg = "第" + (i + 1) + "行，手机号码为空";
				super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
				return null;
			}
			if (StringUtils.isBlank(values[3])) {
				String msg = "第" + (i + 1) + "行，福利金为空";
				super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
				return null;
			}
			String mobile = values[2];
			String bi = values[3];
			String importUserName = "";
			if (null != values[1] && values[1].length() > 0) {
				importUserName = values[1];
			}
			UserInfo mobileUser = new UserInfo();
			mobileUser.setIs_del(0);
			mobileUser.setMobile(mobile);
			mobileUser.setUser_type(Keys.UserType.USER_TYPE_2.getIndex());
			int count = getFacade().getUserInfoService().getUserInfoCount(mobileUser);
			if (count > 1) {
				String msg = "第" + (i + 1) + "行,手机号码：" + mobile + "用户存在多条";
				super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
				return null;
			}
			if (!isBigDecimal(bi)) {
				String msg = "第" + (i + 1) + "行， 福利金金额：" + bi + "不是正确的金额格式";
				super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
				return null;
			}
			mobileUser = getFacade().getUserInfoService().getUserInfo(mobileUser);
			if (null == mobileUser) {
				String msg = "第" + (i + 1) + "行， 手机号码：" + mobile + "用户不存在";
				super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
				return null;
			}
			ImportBiSon insertSon = new ImportBiSon();
			insertSon.setBi_no(new BigDecimal(bi).setScale(2, BigDecimal.ROUND_DOWN));
			insertSon.setMobile(mobileUser.getMobile());
			insertSon.setUser_id(mobileUser.getId());
			insertSon.setUser_name(mobileUser.getUser_name());
			insertSon.setAdd_date(entity.getAdd_date());
			insertSon.setAudit_state(0);
			insertSon.setIs_del(0);
			insertSon.setAdd_user_id(entity.getAdd_user_id());
			if (importUserName.length() > 0) {
				insertSon.setImport_user_name(importUserName);
			}
			sumBi = sumBi.add(insertSon.getBi_no());
			sonList.add(insertSon);
		}
		entity.setBi_sum(sumBi);
		entity.setImportBiSonList(sonList);
		entity.setSum_count(sonList.size());
		if (StringUtils.isNotBlank(filePath)) {
			entity.setFile_path(filePath);
		}
		getFacade().getImportBiService().createImportBi(entity);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		saveMessage(request, "entity.updated");
		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&");
		// pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
		// return this.list(mapping, form, request, response);
		// return new ActionForward("/../manager/customer/ImportBi/import.jsp");
	}

	public static boolean isBigDecimal(String str) {
		if (str == null || str.trim().length() == 0) {
			return false;
		}
		char[] chars = str.toCharArray();
		int sz = chars.length;
		int i = (chars[0] == '-') ? 1 : 0;
		if (i == sz)
			return false;
		if (chars[i] == '.')
			return false;// 除了负号，第一位不能为'小数点'
		boolean radixPoint = false;
		for (; i < sz; i++) {
			if (chars[i] == '.') {
				if (radixPoint)
					return false;
				radixPoint = true;
			} else if (!(chars[i] >= '0' && chars[i] <= '9')) {
				return false;
			}
		}
		return true;
	}
}