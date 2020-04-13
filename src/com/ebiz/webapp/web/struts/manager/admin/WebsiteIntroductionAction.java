package com.ebiz.webapp.web.struts.manager.admin;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.webapp.domain.NewsInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;

/**
 * @author Li,Ka
 * @version 2012-5-07
 */
public class WebsiteIntroductionAction extends BaseAdminAction {
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String mod_id = (String) dynaBean.get("mod_id");
		NewsInfo entity = new NewsInfo();
		entity.setMod_id(mod_id);
		List<NewsInfo> newsInfoList = getFacade().getNewsInfoService().getNewsInfoList(entity);
		if (newsInfoList.size() < 1) {
			return this.add(mapping, form, request, response);
		} else {
			// request.setAttribute("id", newsInfoList.get(0).getId());
			dynaBean.set("id", newsInfoList.get(0).getId().toString());
			return this.edit(mapping, form, request, response);
		}
	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "1")) {
			return super.checkPopedomInvalid(request, response);
		}
		saveToken(request);
		if (!StringUtils.equalsIgnoreCase(request.getMethod(), "POST")) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;

		String mod_id = (String) dynaBean.get("mod_id");
		NewsInfo entity = new NewsInfo();
		entity.setMod_id(mod_id);
		entity.setOrder_value(0);
		entity.setIs_use_direct_uri(0);
		entity.setIs_use_invalid_date(0);
		entity.setPub_time(new Date());

		super.copyProperties(form, entity);
		return mapping.findForward("input");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (isCancelled(request)) {
			// return add(mapping, form, request, response);
			return mapping.findForward("input");
		}
		if (!isTokenValid(request)) {
			saveError(request, "errors.token");
			return mapping.findForward("input");
		}
		resetToken(request);

		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		DynaBean dynaBean = (DynaBean) form;
		String mod_id = (String) dynaBean.get("mod_id");
		String id = (String) dynaBean.get("id");
		NewsInfo entity = new NewsInfo();
		entity.setMod_id(mod_id);
		super.copyProperties(entity, form);
		entity.setInfo_state(3);
		if (StringUtils.isBlank(id)) {
			entity.setView_count(Integer.valueOf("0"));
			entity.setIs_del(0);
			entity.setAdd_time(new Date());
			entity.setAdd_uid(new Integer(new Integer(ui.getId())));
			entity.setUuid(UUID.randomUUID().toString());
			getFacade().getNewsInfoService().createNewsInfo(entity);
			// dynaBean.set("id", insert_id.toString());
			saveMessage(request, "entity.inerted");
		} else {
			entity.getMap().put("update_content", "true");
			entity.setUpdate_time(new Date());
			entity.setUpdate_uid(new Integer(ui.getId()));
			getFacade().getNewsInfoService().modifyNewsInfo(entity);
			saveMessage(request, "entity.updated");

		}

		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append("/admin/WebsiteIntroduction.do?method=edit");
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&id=" + entity.getId());
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
		logger.info("======={}", pathBuffer.toString());
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;

		// return this.view(mapping, form, request, response);
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "2")) {
			return super.checkPopedomInvalid(request, response);
		}
		saveToken(request);
		if (!StringUtils.equalsIgnoreCase(request.getMethod(), "POST")) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		// String mod_id = (String) dynaBean.get("mod_id");
		if (GenericValidator.isLong(id)) {
			NewsInfo newsInfo = new NewsInfo();
			newsInfo.setId(Integer.valueOf(id));
			newsInfo = getFacade().getNewsInfoService().getNewsInfo(newsInfo);

			if (null == newsInfo) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("add");
			}

			// newsInfo.setQueryString(super.serialize(request, "id", "method"));
			// end
			super.copyProperties(form, newsInfo);
			return mapping.findForward("input");
		} else {
			saveError(request, "errors.Integer", id);
			return mapping.findForward("add");
		}
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "0")) {
			return super.checkPopedomInvalid(request, response);
		}
		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		if (GenericValidator.isLong(id)) {
			NewsInfo newsInfo = new NewsInfo();
			newsInfo.setId(Integer.valueOf(id));
			newsInfo = getFacade().getNewsInfoService().getNewsInfo(newsInfo);

			if (null == newsInfo) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("add");
			}
			super.copyProperties(form, newsInfo);
			return mapping.findForward("view");
		} else {
			saveError(request, "errors.Integer", id);
			return mapping.findForward("add");
		}
	}

}
