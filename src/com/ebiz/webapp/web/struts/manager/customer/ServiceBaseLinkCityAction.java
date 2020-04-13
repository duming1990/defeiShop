package com.ebiz.webapp.web.struts.manager.customer;

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
import com.ebiz.webapp.domain.ServiceBaseLink;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.struts.manager.admin.BaseAdminAction;

/**
 * 轮播图管理后台
 * 
 * @author 戴诗学
 * @version 2018-4-19
 * @desc 市域维护的link_id是userinfo的id
 */
public class ServiceBaseLinkCityAction extends BaseAdminAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		saveToken(request);
		if (!StringUtils.equalsIgnoreCase(request.getMethod(), "POST")) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		String link_type = (String) dynaBean.get("link_type");
		String type = (String) dynaBean.get("type");
		dynaBean.set("order_value", 0);
		if (StringUtils.isNotBlank(link_type) && StringUtils.isNotBlank(type)) {
			if (type.equals("txt")) {// 不带有图片的
				return new ActionForward("/customer/ServiceBaseLinkCity/formTxt.jsp");
			}
		}

		// 默认跳转带图片的
		return new ActionForward("/customer/ServiceBaseLinkCity/editPic.jsp");
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		String is_del = (String) dynaBean.get("is_del");

		String title_like = (String) dynaBean.get("title_like");
		String link_id = (String) dynaBean.get("link_id");
		String main_type = (String) dynaBean.get("main_type");
		Pager pager = (Pager) dynaBean.get("pager");

		ServiceBaseLink entity = new ServiceBaseLink();
		super.copyProperties(entity, form);
		// 编辑楼层 特殊处理 防止删除楼层出现数据不对
		if (null != entity.getPar_son_type() && null != entity.getPar_id()) {
			entity.setLink_type(null);
		}
		entity.getMap().put("title_like", title_like);

		if (null == is_del) {
			entity.setIs_del(Integer.valueOf("0"));
			dynaBean.set("is_del", "0");
		}
		if (null != link_id) {
			entity.setLink_id(Integer.valueOf(link_id));
		}
		if (null != main_type && main_type.equals("")) {
			entity.setMain_type(Integer.valueOf(main_type));
		}
		Integer recordCount = getFacade().getServiceBaseLinkService().getServiceBaseLinkCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<ServiceBaseLink> ServiceBaseLinkList = getFacade().getServiceBaseLinkService()
				.getServiceBaseLinkPaginatedList(entity);
		request.setAttribute("entityList", ServiceBaseLinkList);
		request.setAttribute("link_id", entity.getLink_id());
		request.setAttribute("main_type", entity.getMain_type());
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

		DynaBean dynaBean = (DynaBean) form;
		String mod_id = (String) dynaBean.get("mod_id");
		String link_type = (String) dynaBean.get("link_type");
		String type = (String) dynaBean.get("type");
		String par_id = (String) dynaBean.get("par_id");
		String par_son_type = (String) dynaBean.get("par_son_type");
		String link_id = (String) dynaBean.get("link_id");
		String main_type = (String) dynaBean.get("main_type");
		String pre_number = (String) dynaBean.get("pre_number");
		ServiceBaseLink entity = new ServiceBaseLink();

		super.copyProperties(entity, form);
		if (null == entity.getId()) {
			Date sysDate = new Date();
			entity.setLink_id(Integer.valueOf(link_id));
			entity.setIs_del(0);
			entity.setAdd_time(sysDate);
			entity.setMain_type(Integer.valueOf(main_type));
			entity.setAdd_uid(new Integer(new Integer(ui.getId())));
			getFacade().getServiceBaseLinkService().createServiceBaseLink(entity);
			saveMessage(request, "entity.inerted");

		} else {
			entity.setUpdate_time(new Date());
			entity.setUpdate_uid(new Integer(ui.getId()));
			getFacade().getServiceBaseLinkService().modifyServiceBaseLink(entity);
			saveMessage(request, "entity.updated");
		}

		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&link_type=" + link_type);
		pathBuffer.append("&link_id=" + link_id);
		pathBuffer.append("&main_type=" + main_type);
		if (Integer.valueOf(link_type) != 20) {
			pathBuffer.append("&pre_number=" + pre_number);
		}
		pathBuffer.append("&type=" + type);
		if (StringUtils.isNotBlank(par_id)) {
			pathBuffer.append("&par_id=" + par_id);
		}
		if (StringUtils.isNotBlank(par_son_type)) {
			pathBuffer.append("&par_son_type=" + par_son_type);
		}
		pathBuffer.append("&");
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (!StringUtils.equalsIgnoreCase(request.getMethod(), "POST")) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String[] pks = (String[]) dynaBean.get("pks");
		String mod_id = (String) dynaBean.get("mod_id");
		String link_type = (String) dynaBean.get("link_type");
		String link_id = (String) dynaBean.get("link_id");
		String main_type = (String) dynaBean.get("main_type");
		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		ServiceBaseLink entity = new ServiceBaseLink();
		entity.setUpdate_time(new Date());
		entity.setUpdate_uid(new Integer(ui.getId()));
		entity.setDel_time(new Date());
		entity.setDel_uid(new Integer(ui.getId()));
		entity.setIs_del(1);

		if (!StringUtils.isBlank(id) && GenericValidator.isLong(id)) {
			entity.setId(Integer.valueOf(id));
			if (null != link_type && link_type.equals("20")) {
				entity.getMap().put("del_id", ui.getId());
			}
			getFacade().getServiceBaseLinkService().modifyServiceBaseLink(entity);

			saveMessage(request, "entity.deleted");
		} else if (!ArrayUtils.isEmpty(pks)) {
			entity.getMap().put("pks", pks);
			if (null != link_type && link_type.equals("20")) {
				entity.getMap().put("del_id", ui.getId());
			}
			getFacade().getServiceBaseLinkService().modifyServiceBaseLink(entity);
			saveMessage(request, "entity.deleted");

		}
		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&link_id=" + link_id);
		pathBuffer.append("&link_type=" + link_type);
		pathBuffer.append("&main_type=" + main_type);
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(super.serialize(request, "id", "method")));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		saveToken(request);
		if (!StringUtils.equalsIgnoreCase(request.getMethod(), "POST")) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String type = (String) dynaBean.get("type");
		if (GenericValidator.isLong(id)) {
			ServiceBaseLink ServiceBaseLink = new ServiceBaseLink();
			ServiceBaseLink.setId(Integer.valueOf(id));
			ServiceBaseLink = getFacade().getServiceBaseLinkService().getServiceBaseLink(ServiceBaseLink);

			if (null == ServiceBaseLink) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");
			}
			super.copyProperties(form, ServiceBaseLink);
			if (type.equals("price")) {// 带有图片和价格
				return new ActionForward("/customer/ServiceBaseLinkCity/editPicAndPrice.jsp");
			}
			return new ActionForward("/customer/ServiceBaseLinkCity/editPic.jsp");// 默认调跳转页面
		} else {
			saveError(request, "errors.Integer", id);
			return mapping.findForward("list");
		}
	}

	public ActionForward indexTsg(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (null == super.checkUserModPopeDom(form, request, "1")) {
			return super.checkPopedomInvalid(request, response);
		}
		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		saveToken(request);
		String link_id = (String) dynaBean.get("link_id");
		String p_index = (String) dynaBean.get("p_index");
		String main_type = (String) dynaBean.get("main_type");
		ServiceBaseLink entity = new ServiceBaseLink();
		if (StringUtils.isNotEmpty(link_id)) {
			entity.setLink_id(Integer.valueOf(link_id));
		}
		if (StringUtils.isNotEmpty(main_type)) {
			entity.setMain_type(Integer.valueOf(main_type));
		}
		if (StringUtils.isNotEmpty(p_index)) {
			entity.setP_index(Integer.valueOf(p_index));
		}
		entity.setIs_del(0);
		entity.setLink_type(30);
		entity = getFacade().getServiceBaseLinkService().getServiceBaseLink(entity);
		super.copyProperties(form, entity);
		return new ActionForward("/customer/ServiceBaseLinkCity/formCityTsg.jsp");
	}

	public ActionForward saveTsg(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);

		resetToken(request);
		DynaBean dynaBean = (DynaBean) form;
		String mod_id = (String) dynaBean.get("mod_id");
		String link_id = (String) dynaBean.get("link_id");
		String main_type = (String) dynaBean.get("main_type");
		String p_index = (String) dynaBean.get("p_index");
		ServiceBaseLink entity = new ServiceBaseLink();
		super.copyProperties(entity, form);
		if (StringUtils.isNotEmpty(link_id)) {
			entity.setLink_id(Integer.valueOf(link_id));
		}
		if (StringUtils.isNotEmpty(main_type)) {
			entity.setMain_type(Integer.valueOf(main_type));
		}
		if (StringUtils.isNotEmpty(p_index)) {
			entity.setP_index(Integer.valueOf(p_index));
		}

		String title = super.getBaseProvince(Long.valueOf(p_index)).getP_name();
		if (null == entity.getId()) {
			entity.setIs_del(0);
			entity.setAdd_time(new Date());
			entity.setLink_type(30);
			entity.setTitle(title);
			getFacade().getServiceBaseLinkService().createServiceBaseLink(entity);
			saveMessage(request, "entity.inerted");
		} else {

			getFacade().getServiceBaseLinkService().modifyServiceBaseLink(entity);
			saveMessage(request, "entity.updated");
		}

		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append("/customer/ServiceBaseLinkCity.do?method=indexTsg");
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&link_id=" + link_id);
		pathBuffer.append("&main_type=" + main_type);
		pathBuffer.append("&p_index=" + p_index);
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		return forward;
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		if (GenericValidator.isInt(id)) {
			ServiceBaseLink ServiceBaseLink = new ServiceBaseLink();
			ServiceBaseLink.setId(Integer.valueOf(id));
			ServiceBaseLink = getFacade().getServiceBaseLinkService().getServiceBaseLink(ServiceBaseLink);

			if (null == ServiceBaseLink) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");
			}
			super.copyProperties(form, ServiceBaseLink);
			return mapping.findForward("view");
		} else {
			saveError(request, "errors.Integer", id);
			return mapping.findForward("list");
		}
	}
}
