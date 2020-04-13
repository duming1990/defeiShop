package com.ebiz.webapp.web.struts.manager.admin;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.alibaba.fastjson.JSONObject;
import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.BaseAuditRecord;
import com.ebiz.webapp.domain.BaseImgs;
import com.ebiz.webapp.domain.EntpContent;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;

/**
 * @author Li,Yuan
 * @version 2012-02-22
 */
public class UserAuditAction extends BaseAdminAction {
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
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String real_name_like = (String) dynaBean.get("real_name_like");
		String add_user_name_like = (String) dynaBean.get("add_user_name_like");
		String id_card = (String) dynaBean.get("id_card");
		String audit_state = (String) dynaBean.get("audit_state");

		BaseAuditRecord entity = new BaseAuditRecord();
		super.copyProperties(entity, form);
		entity.setOpt_type(Keys.OptType.OPT_TYPE_10.getIndex());
		entity.getMap().put("real_name_like", real_name_like);
		entity.getMap().put("add_user_name_like", add_user_name_like);
		entity.getMap().put("id_card", id_card);
		if (StringUtils.isNotBlank(audit_state)) {
			entity.setAudit_state(Integer.valueOf(audit_state));
		}

		String add_date_st = (String) dynaBean.get("add_date_st");
		String add_date_en = (String) dynaBean.get("add_date_en");
		String update_date_st = (String) dynaBean.get("update_date_st");
		String update_date_en = (String) dynaBean.get("update_date_en");
		String audit_date_st = (String) dynaBean.get("audit_date_st");
		String audit_date_en = (String) dynaBean.get("audit_date_en");
		entity.getMap().put("add_date_st", add_date_st);
		entity.getMap().put("add_date_en", add_date_en);
		entity.getMap().put("update_date_st", update_date_st);
		entity.getMap().put("update_date_en", update_date_en);
		entity.getMap().put("audit_date_st", audit_date_st);
		entity.getMap().put("audit_date_en", audit_date_en);

		Integer recordCount = getFacade().getBaseAuditRecordService().getBaseAuditRecordCount(entity);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<BaseAuditRecord> list = getFacade().getBaseAuditRecordService().getBaseAuditRecordPaginatedList(entity);
		if (null != list && list.size() > 0) {
			for (BaseAuditRecord temp : list) {
				UserInfo userInfo = new UserInfo();
				userInfo.setId(temp.getLink_id());
				if (StringUtils.isNotBlank(id_card)) {
					userInfo.setId_card(id_card);
				}
				userInfo = getFacade().getUserInfoService().getUserInfo(userInfo);
				if (userInfo != null) {
					temp.getMap().put("id_card", userInfo.getId_card());
				}
				BaseImgs imgs = new BaseImgs();
				imgs.setLink_id(temp.getLink_id());
				imgs.setImg_type(Keys.BaseImgsType.Base_Imgs_TYPE_10.getIndex());
				List<BaseImgs> imgsList = getFacade().getBaseImgsService().getBaseImgsList(imgs);
				if (null != imgsList && imgsList.size() > 0) {
					temp.getMap().put("img_id_card_zm", imgsList.get(0).getFile_path());
					String img_id_card_fm = "";
					if (imgsList.size() > 1) {
						img_id_card_fm = imgsList.get(1).getFile_path();
					}
					temp.getMap().put("img_id_card_fm", img_id_card_fm);
				}
			}

		}
		request.setAttribute("entityList", list);

		return mapping.findForward("list");
	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		saveToken(request);
		setNaviStringToRequestScope(request);

		request.setAttribute("is_add", true);

		return mapping.findForward("input");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String is_audit = (String) dynaBean.get("is_audit");
		String mod_id = (String) dynaBean.get("mod_id");
		String link_id = (String) dynaBean.get("link_id");
		String id_card = (String) dynaBean.get("id_card");

		String img_id_card_zm = (String) dynaBean.get("img_id_card_zm");
		String img_id_card_fm = (String) dynaBean.get("img_id_card_fm");
		String[] basefiles = { img_id_card_zm, img_id_card_fm };

		UserInfo userInfo = super.getUserInfoFromSession(request);

		BaseAuditRecord entity = new BaseAuditRecord();
		super.copyProperties(entity, form);

		UserInfo uiInfo = new UserInfo();
		uiInfo.setId(Integer.valueOf(link_id));
		uiInfo.getMap().put("basefiles", basefiles);
		entity.getMap().put("modUserInfo", uiInfo);
		uiInfo.setId_card(id_card);
		if (!GenericValidator.isLong(id)) {// 创建
			uiInfo.setIs_renzheng(0);
			UserInfo uiInfoQuery = super.getUserInfo(Integer.valueOf(link_id));
			entity.setOpt_type(Keys.OptType.OPT_TYPE_10.getIndex());
			entity.setLink_id(Integer.valueOf(link_id));
			entity.setLink_table("USER_INFO");
			entity.setAdd_date(new Date());
			entity.setAdd_user_id(uiInfoQuery.getId());
			entity.setAdd_user_name(uiInfoQuery.getUser_name());
			entity.setAudit_state(0);
			super.getFacade().getBaseAuditRecordService().createBaseAuditRecord(entity);
			saveMessage(request, "entity.inerted");
		} else {// 修改
			if (StringUtils.isNotBlank(is_audit)) {// 审核
				// entity.setAudit_state(1);
				entity.setId(new Integer(id));
				entity.setAudit_user_id(userInfo.getId());
				entity.setAudit_date(new Date());
				entity.getMap().put("modify_real_name", "true");
				uiInfo.getMap().put("basefiles", null);
				entity.getMap().put("modUserInfo", null);
			}
			super.getFacade().getBaseAuditRecordService().modifyBaseAuditRecord(entity);
			if (StringUtils.isNotBlank(is_audit)) {
				saveMessage(request, "entity.audit");
			} else {
				saveMessage(request, "entity.updated");
			}
		}
		// return null;
		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
		pathBuffer.append("&mod_id=" + mod_id);
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		logger.info("=====forward:" + forward);
		// end
		return forward;
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "0")) {
			return super.checkPopedomInvalid(request, response);
		}
		setNaviStringToRequestScope(request);
		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (GenericValidator.isLong(id)) {
			EntpInfo entity = new EntpInfo();
			entity.setId(new Integer(id));
			entity = getFacade().getEntpInfoService().getEntpInfo(entity);

			if (null != entity) {
				// the line below is added for pagination
				entity.setQueryString(super.serialize(request, "id", "method"));
				// end
				super.copyProperties(form, entity);

				EntpContent entpContent = new EntpContent();
				entpContent.setType(0);
				entpContent.setEntp_id(new Integer(id));
				entpContent = super.getFacade().getEntpContentService().getEntpContent(entpContent);
				if (null != entpContent) {
					dynaBean.set("entp_content", entpContent.getContent());
				}

				// 查询商家从事行业，线下店铺从事行业
				super.setEntpBaseClassList(request);
			}
		}
		return mapping.findForward("view");
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "4")) {
			return super.checkPopedomInvalid(request, response);
		}
		setNaviStringToRequestScope(request);
		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String[] pks = (String[]) dynaBean.get("pks");

		UserInfo userInfo = super.getUserInfoFromSession(request);

		if (!StringUtils.isBlank(id) && GenericValidator.isLong(id)) {
			BaseAuditRecord entity = new BaseAuditRecord();
			entity.setId(new Integer(id));
			getFacade().getBaseAuditRecordService().removeBaseAuditRecord(entity);
			saveMessage(request, "entity.deleted");
		} else if (!ArrayUtils.isEmpty(pks)) {

			for (String cur_id : pks) {

				BaseAuditRecord entity = new BaseAuditRecord();
				entity.setId(Integer.valueOf(cur_id));

				getFacade().getBaseAuditRecordService().removeBaseAuditRecord(entity);

			}
			saveMessage(request, "entity.deleted");
		}
		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(super.serialize(request, "id", "method")));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward audit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// if (null == super.checkUserModPopeDom(form, request, "8")) {
		// return super.checkPopedomInvalid(request, response);
		// }
		setNaviStringToRequestScope(request);
		super.setBaseDataListToSession(10, request);// 用户类型

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (GenericValidator.isLong(id)) {
			BaseAuditRecord entity = new BaseAuditRecord();
			entity.setId(new Integer(id));
			entity = getFacade().getBaseAuditRecordService().getBaseAuditRecord(entity);
			if (null == entity) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");

			}

			UserInfo userInfo = new UserInfo();
			userInfo.setIs_del(0);
			userInfo.setId(entity.getLink_id());
			userInfo = super.getFacade().getUserInfoService().getUserInfo(userInfo);
			if (null != userInfo) {
				request.setAttribute("id_card", userInfo.getId_card());
			}

			BaseImgs imgs = new BaseImgs();
			imgs.setLink_id(entity.getLink_id());
			imgs.setImg_type(Keys.BaseImgsType.Base_Imgs_TYPE_10.getIndex());
			List<BaseImgs> imgsList = getFacade().getBaseImgsService().getBaseImgsList(imgs);
			if (null != imgsList && imgsList.size() > 0) {
				request.setAttribute("img_id_card_zm", imgsList.get(0).getFile_path());
				if (imgsList.size() > 1) {
					request.setAttribute("img_id_card_fm", imgsList.get(1).getFile_path());
				}
			}
			request.setAttribute("imgsList", imgsList);
			// the line below is added for pagination
			entity.setQueryString(super.serialize(request, "id", "method"));
			// end
			super.copyProperties(form, entity);

			return new ActionForward("/../manager/admin/UserAudit/audit.jsp");
		} else {
			this.saveError(request, "errors.Integer", new String[] { id });
			return mapping.findForward("list");
		}
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "2")) {
			return super.checkPopedomInvalid(request, response);
		}
		setNaviStringToRequestScope(request);
		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (GenericValidator.isLong(id)) {
			BaseAuditRecord entity = new BaseAuditRecord();
			entity.setId(new Integer(id));
			entity = getFacade().getBaseAuditRecordService().getBaseAuditRecord(entity);

			UserInfo userInfo = new UserInfo();
			userInfo.setId(entity.getLink_id());
			userInfo = getFacade().getUserInfoService().getUserInfo(userInfo);
			if (null == userInfo) {
				String msg = "用户不存在！";
				super.showMsgForManager(request, response, msg);
				return null;
			}

			super.copyProperties(form, userInfo);
			// request.setAttribute("id_card", userInfo.getId_card());

			BaseImgs imgs = new BaseImgs();
			imgs.setLink_id(entity.getLink_id());
			imgs.setImg_type(Keys.BaseImgsType.Base_Imgs_TYPE_10.getIndex());
			List<BaseImgs> imgsList = getFacade().getBaseImgsService().getBaseImgsList(imgs);
			if (null != imgsList && imgsList.size() > 0) {
				// for (BaseImgs temp : imgsList) {
				request.setAttribute("img_id_card_zm", imgsList.get(0).getFile_path());
				if (imgsList.size() > 1) {
					request.setAttribute("img_id_card_fm", imgsList.get(1).getFile_path());
				}
				// }
			}

			// the line below is added for pagination
			entity.setQueryString(super.serialize(request, "id", "method"));
			// end
			super.copyProperties(form, entity);

		}
		return mapping.findForward("input");
	}

	public ActionForward deleteImageOrVideo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String type = (String) dynaBean.get("type");
		String file_path = (String) dynaBean.get("file_path");

		if (StringUtils.isBlank(id) || StringUtils.isBlank(type) || StringUtils.isBlank(file_path)) {
			super.renderText(response, "fail");
			return null;
		}

		EntpInfo entpInfo = new EntpInfo();
		entpInfo.setId(Integer.valueOf(id));
		entpInfo.getMap().put("clear_" + type, "true");// clear_image_path和clear_video_path
		getFacade().getEntpInfoService().modifyEntpInfo(entpInfo);

		// String ctx = request.getSession().getServletContext().getRealPath(String.valueOf(File.separatorChar));
		// String realFilePath = ctx + file_path;
		// FileTools.deleteFile(realFilePath);

		super.renderText(response, "success");
		return null;
	}

	public ActionForward chooseEntpInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String entp_name_like = (String) dynaBean.get("entp_name_like");
		String entp_type = (String) dynaBean.get("entp_type");
		EntpInfo entity = new EntpInfo();
		super.copyProperties(entity, form);
		entity.setAudit_state(1);
		entity.setIs_del(0);

		entity.getMap().put("entp_name_like", entp_name_like);

		if (StringUtils.isNotBlank(entp_type)) {
			entity.setEntp_type(Integer.valueOf(entp_type));
		}
		Integer recordCount = getFacade().getEntpInfoService().getEntpInfoCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<EntpInfo> entityList = super.getFacade().getEntpInfoService().getEntpInfoPaginatedList(entity);
		request.setAttribute("entityList", entityList);
		return new ActionForward("/../manager/admin/EntpInfoAudit/choose_entpinfo.jsp");
	}

	public ActionForward getEntpInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (GenericValidator.isLong(id)) {
			EntpInfo entity = new EntpInfo();
			entity.setId(new Integer(id));
			entity = getFacade().getEntpInfoService().getEntpInfo(entity);
			if (null != entity) {
				UserInfo userInfo = new UserInfo();
				userInfo.setOwn_entp_id(entity.getId());
				userInfo.setIs_del(0);
				List<UserInfo> userInfoList = super.getFacade().getUserInfoService().getUserInfoList(userInfo);
				request.setAttribute("userInfoList", userInfoList);
				request.setAttribute("list_size", userInfoList.size());
			}
			// the line below is added for pagination
			entity.setQueryString(super.serialize(request, "id", "method"));
			// end
			super.copyProperties(form, entity);

			dynaBean.set("full_name", entity.getMap().get("full_name"));

		}
		return new ActionForward("/../manager/admin/EntpInfoAudit/entpInfoForColorBox.jsp");
	}

	public ActionForward updateEntpInfoForShow(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		String is_show = (String) dynaBean.get("is_show");
		String entp_id = (String) dynaBean.get("entp_id");
		JSONObject json = new JSONObject();
		int ret = 0;
		String msg = "操作失败";
		if (!GenericValidator.isLong(entp_id)) {
			msg = "entp_id参数不正确";
			json.put("ret", ret);
			json.put("msg", msg);
			super.renderJson(response, json.toJSONString());
			return null;
		}
		if (!GenericValidator.isLong(is_show)) {
			msg = "is_show参数不正确";
			json.put("ret", ret);
			json.put("msg", msg);
			super.renderJson(response, json.toJSONString());
			return null;
		}

		EntpInfo entity = new EntpInfo();
		entity.setId(new Integer(entp_id));
		entity.setIs_show(Integer.valueOf(is_show));
		int row = getFacade().getEntpInfoService().modifyEntpInfo(entity);
		logger.info("row:" + row);
		if (row == 1) {
			ret = 1;
			msg = "操作成功";
			json.put("ret", ret);
			json.put("msg", msg);
			super.renderJson(response, json.toJSONString());
			return null;
		}
		json.put("ret", ret);
		json.put("msg", msg);
		super.renderJson(response, json.toJSONString());
		return null;
	}

	public ActionForward updateEntpInfoForCancel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		String entp_id = (String) dynaBean.get("entp_id");
		JSONObject json = new JSONObject();
		int ret = 0;
		String msg = "操作失败";
		if (!GenericValidator.isLong(entp_id)) {
			msg = "entp_id参数不正确";
			json.put("ret", ret);
			json.put("msg", msg);
			super.renderJson(response, json.toJSONString());
			return null;
		}

		EntpInfo entity = new EntpInfo();
		entity.setId(new Integer(entp_id));
		entity.setAudit_state(0);
		EntpInfo entityQuery = new EntpInfo();
		entityQuery.setId(Integer.valueOf(entp_id));
		entityQuery = super.getFacade().getEntpInfoService().getEntpInfo(entityQuery);
		if (null == entityQuery) {
			msg = "企业不存在";
			json.put("ret", ret);
			json.put("msg", msg);
			super.renderJson(response, json.toJSONString());
			return null;
		}
		entity.getMap().put("user_id", entityQuery.getAdd_user_id());
		int row = getFacade().getEntpInfoService().modifyEntpInfoForCancel(entity);
		if (row == 1) {
			ret = 1;
			msg = "操作成功";
			json.put("ret", ret);
			json.put("msg", msg);
			super.renderJson(response, json.toJSONString());
			return null;
		}
		json.put("ret", ret);
		json.put("msg", msg);
		super.renderJson(response, json.toJSONString());
		return null;
	}

	public ActionForward valIsHasAudit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		String user_id = (String) dynaBean.get("user_id");
		JSONObject json = new JSONObject();
		int ret = 0;
		String msg = "操作失败";
		if (!GenericValidator.isLong(user_id)) {
			msg = "user_id参数不正确";
			json.put("ret", ret);
			json.put("msg", msg);
			super.renderJson(response, json.toJSONString());
			return null;
		}

		UserInfo entity = super.getUserInfo(Integer.valueOf(user_id));
		if (null == entity) {
			msg = "用户不存在";
			json.put("ret", ret);
			json.put("msg", msg);
			super.renderJson(response, json.toJSONString());
			return null;
		}
		if (entity.getIs_renzheng().intValue() == 1) {

			BaseAuditRecord baseAuditRecord = new BaseAuditRecord();
			baseAuditRecord.setLink_id(entity.getId());
			baseAuditRecord.setOpt_type(Keys.OptType.OPT_TYPE_10.getIndex());
			baseAuditRecord = super.getFacade().getBaseAuditRecordService().getBaseAuditRecord(baseAuditRecord);
			if (null != baseAuditRecord) {
				json.put("id", baseAuditRecord.getId());
			}
			ret = -2;
			msg = "该用户已认证过，不能再次认证！";
			json.put("ret", ret);
			json.put("msg", msg);

			super.renderJson(response, json.toJSONString());
			return null;
		}

		// 查询是否已经申报过
		BaseImgs img = new BaseImgs();
		img.setLink_id(Integer.valueOf(user_id));
		img.setImg_type(Keys.BaseImgsType.Base_Imgs_TYPE_10.getIndex());
		int count = super.getFacade().getBaseImgsService().getBaseImgsCount(img);

		if (count > 0) {
			ret = -2;
			msg = "该用户已经申请过认证信息了，不能再次申请！";
			json.put("ret", ret);
			json.put("msg", msg);

			BaseAuditRecord baseAuditRecord = new BaseAuditRecord();
			baseAuditRecord.setLink_id(entity.getId());
			baseAuditRecord.setOpt_type(Keys.OptType.OPT_TYPE_10.getIndex());
			baseAuditRecord = super.getFacade().getBaseAuditRecordService().getBaseAuditRecord(baseAuditRecord);
			if (null != baseAuditRecord) {
				json.put("id", baseAuditRecord.getId());
			}

			super.renderJson(response, json.toJSONString());
			return null;
		}
		ret = 1;
		msg = "申请成功！";
		json.put("ret", ret);
		json.put("msg", msg);
		super.renderJson(response, json.toJSONString());
		return null;
	}
}
