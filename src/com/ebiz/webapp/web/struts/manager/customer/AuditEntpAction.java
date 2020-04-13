package com.ebiz.webapp.web.struts.manager.customer;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.ssi.web.struts.bean.UploadFile;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;

/**
 * @author Li,Yuan
 * @version 2012-02-22
 */
public class AuditEntpAction extends BaseCustomerAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}

		DynaBean dynaBean = setAuditEntpInfo(form, request, ui);

		dynaBean.set("province", Keys.P_INDEX_INIT);
		return mapping.findForward("list");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String entp_logo_s = (String) dynaBean.get("entp_logo_s");
		String entp_licence_img_s = (String) dynaBean.get("entp_licence_img_s");
		String img_id_card_zm_s = (String) dynaBean.get("img_id_card_zm_s");
		String img_id_card_fm_s = (String) dynaBean.get("img_id_card_fm_s");
		String img_entp_mentou_s = (String) dynaBean.get("img_entp_mentou_s");
		String is_audit = (String) dynaBean.get("is_audit");
		String mod_id = (String) dynaBean.get("mod_id");
		String par_id = (String) dynaBean.get("par_id");

		UserInfo userInfo = super.getUserInfoFromSession(request);

		EntpInfo entity = new EntpInfo();
		super.copyProperties(entity, form);

		entity.setEntp_logo(entp_logo_s);
		entity.setEntp_licence_img(entp_licence_img_s);
		entity.setImg_id_card_zm(img_id_card_zm_s);
		entity.setImg_id_card_fm(img_id_card_fm_s);
		entity.setImg_entp_mentou(img_entp_mentou_s);

		List<UploadFile> uploadFileList = super.uploadFile(form, true, false, Keys.NEWS_INFO_IMAGE_SIZE);
		for (UploadFile uploadFile : uploadFileList) {
			if (StringUtils.isNotBlank(uploadFile.getFileSavePath())) {
				if ("entp_licence_img".equalsIgnoreCase(uploadFile.getFormName())) {
					entity.setEntp_licence_img(uploadFile.getFileSavePath());
				}
				if ("entp_logo".equalsIgnoreCase(uploadFile.getFormName())) {
					entity.setEntp_logo(uploadFile.getFileSavePath());
				}
				if ("img_id_card_zm".equalsIgnoreCase(uploadFile.getFormName())) {
					entity.setImg_id_card_zm(uploadFile.getFileSavePath());
				}
				if ("img_id_card_fm".equalsIgnoreCase(uploadFile.getFormName())) {
					entity.setImg_id_card_fm(uploadFile.getFileSavePath());
				}
				if ("img_entp_mentou".equalsIgnoreCase(uploadFile.getFormName())) {
					entity.setImg_entp_mentou(uploadFile.getFileSavePath());
				}
			}
		}

		if (!GenericValidator.isLong(id)) {
			entity.setIs_del(0);
			entity.setUuid(UUID.randomUUID().toString());
			entity.setAdd_date(new Date());
			entity.setAdd_user_id(userInfo.getId());
			entity.setAdd_user_name(userInfo.getUser_name());
			super.getFacade().getEntpInfoService().createEntpInfo(entity);
			saveMessage(request, "entity.inerted");
		} else {
			if (StringUtils.isNotBlank(is_audit)) {
				entity.setAudit_user_id(userInfo.getId());
				entity.setAudit_date(new Date());
				entity.getMap().put("update_user_info", "true");

				EntpInfo entityQuery = new EntpInfo();
				entityQuery.setId(Integer.valueOf(id));
				entityQuery = super.getFacade().getEntpInfoService().getEntpInfo(entityQuery);
				if (null != entityQuery) {
					entity.getMap().put("user_id", entityQuery.getAdd_user_id());
				}
			}

			entity.setUpdate_date(new Date());
			entity.setUpdate_user_id(userInfo.getId());
			super.getFacade().getEntpInfoService().modifyEntpInfo(entity);
			if (StringUtils.isNotBlank(is_audit))
				saveMessage(request, "entity.audit");
			else
				saveMessage(request, "entity.updated");
		}

		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&par_id=" + par_id);
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		getsonSysModuleList(request, dynaBean);

		if (GenericValidator.isLong(id)) {
			EntpInfo entity = new EntpInfo();
			entity.setId(new Integer(id));
			entity = getFacade().getEntpInfoService().getEntpInfo(entity);

			// the line below is added for pagination
			entity.setQueryString(super.serialize(request, "id", "method"));
			// end
			super.copyProperties(form, entity);

			// 查询商家从事行业，线下店铺从事行业
			super.setEntpBaseClassList(request);
		}
		return mapping.findForward("view");
	}

	public ActionForward audit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}

		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);

		String id = (String) dynaBean.get("id");
		getsonSysModuleList(request, dynaBean);

		UserInfo userInfoTemp = super.getUserInfo(ui.getId());
		request.setAttribute("userInfoTemp", userInfoTemp);

		if (GenericValidator.isLong(id)) {
			EntpInfo entity = new EntpInfo();
			entity.setId(new Integer(id));
			entity = getFacade().getEntpInfoService().getEntpInfo(entity);
			if (null == entity) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");

			}

			if (null != entity.getAdd_user_id()) {
				UserInfo userInfo = new UserInfo();
				userInfo.setId(entity.getAdd_user_id());
				userInfo.setIs_del(0);
				List<UserInfo> userInfoList = super.getFacade().getUserInfoService().getUserInfoList(userInfo);
				request.setAttribute("userInfoList", userInfoList);
				request.setAttribute("list_size", userInfoList.size());

				UserInfo entpUser = new UserInfo();
				if (userInfoList.size() > 0) {
					entpUser = userInfoList.get(0);
				}// 取性别用的
				request.setAttribute("entpUser", entpUser);
			}

			request.setAttribute("shopTypes", Keys.ShopType.values());// 店铺类型，用的企业表is_nx_entp这个字段，没有增加字段了

			// the line below is added for pagination
			entity.setQueryString(super.serialize(request, "id", "method"));
			// end
			super.copyProperties(form, entity);

			// 查询商家从事行业，线下店铺从事行业
			super.setEntpBaseClassList(request);

			return new ActionForward("/../manager/customer/AuditEntp/audit.jsp");
		} else {
			this.saveError(request, "errors.Integer", new String[] { id });
			return mapping.findForward("list");
		}
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

}
