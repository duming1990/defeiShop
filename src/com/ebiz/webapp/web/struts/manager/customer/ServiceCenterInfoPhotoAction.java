package com.ebiz.webapp.web.struts.manager.customer;

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

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.BaseFiles;
import com.ebiz.webapp.domain.ServiceCenterInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;

public class ServiceCenterInfoPhotoAction extends BaseCustomerAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "1")) {
			return super.checkPopedomInvalid(request, response);
		}

		UserInfo userInfo = super.getUserInfoFromSession(request);
		if (null == userInfo) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String photo_desc_like = (String) dynaBean.get("photo_desc_like");
		String st_add_date = (String) dynaBean.get("st_add_date");
		String en_add_date = (String) dynaBean.get("en_add_date");

		getsonSysModuleList(request, dynaBean);

		ServiceCenterInfo centerInfo = new ServiceCenterInfo();
		centerInfo.getMap().put("is_virtual_no_def", true);
		centerInfo.setAdd_user_id(userInfo.getId());
		centerInfo.setIs_del(0);
		centerInfo = getFacade().getServiceCenterInfoService().getServiceCenterInfo(centerInfo);

		BaseFiles entity = new BaseFiles();
		super.copyProperties(entity, form);

		entity.setLink_id(centerInfo.getId());
		entity.setType(Keys.BaseFilesType.Base_Files_TYPE_60.getIndex());
		entity.setIs_del(0);
		entity.getMap().put("file_desc_like", photo_desc_like);
		entity.getMap().put("st_add_date", st_add_date);
		entity.getMap().put("en_add_date", en_add_date);

		Integer recordCount = getFacade().getBaseFilesService().getBaseFilesCount(entity);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<BaseFiles> entityList = getFacade().getBaseFilesService().getBaseFilesPaginatedList(entity);
		request.setAttribute("entityList", entityList);

		return mapping.findForward("list");
	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}
		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);

		return mapping.findForward("input");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		UserInfo userInfo = super.getUserInfoFromSession(request);

		DynaBean dynaBean = (DynaBean) form;
		String mod_id = (String) dynaBean.get("mod_id");
		String id = (String) dynaBean.get("id");

		BaseFiles entity = new BaseFiles();
		super.copyProperties(entity, form);

		if (StringUtils.isNotBlank(id)) {// 修改
			entity.setUpdate_date(new Date());
			entity.setUpdate_user_id(userInfo.getId());
			getFacade().getBaseFilesService().modifyBaseFiles(entity);
			saveMessage(request, "entity.updated");
		} else {
			ServiceCenterInfo centerInfo = new ServiceCenterInfo();
			centerInfo.getMap().put("is_virtual_no_def", true);
			centerInfo.setAdd_user_id(userInfo.getId());
			centerInfo.setIs_del(0);
			centerInfo = getFacade().getServiceCenterInfoService().getServiceCenterInfo(centerInfo);
			entity.setLink_id(centerInfo.getId());
			entity.setIs_del(0);
			entity.setAdd_date(new Date());
			entity.setAdd_user_id(userInfo.getId());
			entity.setType(Keys.BaseFilesType.Base_Files_TYPE_60.getIndex());
			getFacade().getBaseFilesService().createBaseFiles(entity);
			saveMessage(request, "entity.inerted");
		}

		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
		pathBuffer.append("&mod_id=" + mod_id);
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		return forward;
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (GenericValidator.isLong(id)) {

			BaseFiles entity = new BaseFiles();
			entity.setId(new Integer(id));
			entity = getFacade().getBaseFilesService().getBaseFiles(entity);
			if (null == entity) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");
			}
			// the line below is added for pagination
			entity.setQueryString(super.serialize(request, "id", "method"));
			// end
			super.copyProperties(form, entity);

			return mapping.findForward("input");
		} else {
			this.saveError(request, "errors.Integer", new String[] { id });
			return mapping.findForward("list");
		}
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
			BaseFiles entity = new BaseFiles();
			entity.setIs_del(1);
			entity.setId(new Integer(id));
			entity.setDel_date(new Date());
			entity.setDel_user_id(userInfo.getId());
			getFacade().getBaseFilesService().modifyBaseFiles(entity);
			saveMessage(request, "entity.deleted");
		} else if (!ArrayUtils.isEmpty(pks)) {

			for (String cur_id : pks) {
				BaseFiles entity = new BaseFiles();
				entity.setIs_del(1);
				entity.setId(new Integer(cur_id));
				entity.setDel_date(new Date());
				entity.setDel_user_id(userInfo.getId());
				getFacade().getBaseFilesService().modifyBaseFiles(entity);
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
}
