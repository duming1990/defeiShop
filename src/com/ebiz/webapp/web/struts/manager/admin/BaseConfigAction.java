package com.ebiz.webapp.web.struts.manager.admin;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.webapp.domain.BaseFiles;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;

/**
 * @author Liu,Jia
 * @version 2016-08-18
 */
public class BaseConfigAction extends BaseAdminAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.loginPic(mapping, form, request, response);
	}

	public ActionForward loginPic(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "1")) {
			return super.checkPopedomInvalid(request, response);
		}
		setNaviStringToRequestScope(request);
		saveToken(request);

		BaseFiles entity = new BaseFiles();
		entity.setType(Keys.BaseFilesType.Base_Files_TYPE_30.getIndex());
		entity.setIs_del(0);
		entity = getFacade().getBaseFilesService().getBaseFiles(entity);
		if (null != entity) {
			super.copyProperties(form, entity);
		}

		return new ActionForward("/../manager/admin/BaseConfig/formLoginPic.jsp");
	}

	public ActionForward saveLoginPic(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		UserInfo userInfo = super.getUserInfoFromSession(request);
		if (isCancelled(request)) {
			return mapping.findForward("input");
		}
		if (!isTokenValid(request)) {
			saveError(request, "errors.token");
			return mapping.findForward("input");
		}
		resetToken(request);

		DynaBean dynaBean = (DynaBean) form;
		String mod_id = (String) dynaBean.get("mod_id");
		BaseFiles entity = new BaseFiles();
		super.copyProperties(entity, form);
		entity.setType(Keys.BaseFilesType.Base_Files_TYPE_30.getIndex());
		if (null == entity.getId()) {
			entity.setIs_del(0);
			entity.setAdd_user_id(userInfo.getId());
			entity.setAdd_date(new Date());
			getFacade().getBaseFilesService().createBaseFiles(entity);
			saveMessage(request, "entity.inerted");
		} else {
			getFacade().getBaseFilesService().modifyBaseFiles(entity);
			saveMessage(request, "entity.updated");
		}

		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append("/admin/BaseConfig.do?method=loginPic");
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&id=" + entity.getId());
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		return forward;
	}
}
