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
import com.ebiz.webapp.domain.BaseProvince;
import com.ebiz.webapp.domain.ServiceCenterInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.VillageTour;

public class VillageTourAction extends BaseCustomerAction {
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

		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}
		Pager pager = (Pager) dynaBean.get("pager");
		String tour_name_like = (String) dynaBean.get("tour_name_like");
		String st_add_date = (String) dynaBean.get("st_add_date");
		String en_add_date = (String) dynaBean.get("en_add_date");
		// 查询县信息
		ui = super.getUserInfo(ui.getId());
		if (null == ui || ui.getIs_fuwu() != 1) {
			String msg = "您还未登录，请先登录系统！";
			super.showMsgForCustomer(request, response, msg);
			return null;
		}
		ServiceCenterInfo centerInfo = new ServiceCenterInfo();
		// centerInfo.getMap().put("is_virtual_no_def", true);
		centerInfo.setAdd_user_id(ui.getId());
		centerInfo.setIs_del(0);
		centerInfo = getFacade().getServiceCenterInfoService().getServiceCenterInfo(centerInfo);
		if (null == centerInfo || null == centerInfo.getP_index()) {
			String msg = "县域不存在或已删除！";
			super.showMsgForCustomer(request, response, msg);
			return null;
		}

		VillageTour entity = new VillageTour();
		super.copyProperties(entity, form);
		entity.setIs_del(0);
		entity.getMap().put("p_index_like", centerInfo.getP_index().toString());
		entity.getMap().put("tour_name_like", tour_name_like);
		entity.getMap().put("begin_date", st_add_date);
		entity.getMap().put("end_date", en_add_date);

		Integer recordCount = getFacade().getVillageTourService().getVillageTourCount(entity);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<VillageTour> list = getFacade().getVillageTourService().getVillageTourPaginatedList(entity);
		if (null != list && list.size() > 0) {
			for (VillageTour temp : list) {
				BaseProvince baseProvince = new BaseProvince();
				baseProvince.setP_index(temp.getP_index());
				baseProvince = getFacade().getBaseProvinceService().getBaseProvince(baseProvince);
				if (null != baseProvince && null != baseProvince.getFull_name()) {
					temp.getMap().put("full_name", baseProvince.getFull_name());
				}
			}
		}
		request.setAttribute("entityList", list);

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

		ServiceCenterInfo centerInfo = new ServiceCenterInfo();
		// centerInfo.getMap().put("is_virtual_no_def", true);
		centerInfo.setAdd_user_id(ui.getId());
		centerInfo.setIs_del(0);
		centerInfo = getFacade().getServiceCenterInfoService().getServiceCenterInfo(centerInfo);
		if (null == centerInfo) {
			String msg = "县域不存在或已删除！";
			super.showMsgForCustomer(request, response, msg);
			return null;
		}
		if (centerInfo.getP_index() != null) {
			super.setprovinceAndcityAndcountryToFrom(dynaBean, centerInfo.getP_index().longValue());
		}
		return mapping.findForward("input");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		DynaBean dynaBean = (DynaBean) form;

		String id = (String) dynaBean.get("id");
		String mod_id = (String) dynaBean.get("mod_id");

		String province = (String) dynaBean.get("province");
		String city = (String) dynaBean.get("city");
		String country = (String) dynaBean.get("country");
		String town = (String) dynaBean.get("town");
		String village = (String) dynaBean.get("village");

		VillageTour entity = new VillageTour();
		super.copyProperties(entity, form);

		BaseProvince baseProvince = new BaseProvince();

		String village_address = "";
		if (StringUtils.isNotBlank(province)) {
			baseProvince.setP_index(Long.valueOf(province));
		}
		if (StringUtils.isNotBlank(city)) {
			baseProvince.setP_index(Long.valueOf(city));
		}
		if (StringUtils.isNotBlank(country)) {
			baseProvince.setP_index(Long.valueOf(country));
		}
		if (StringUtils.isNotBlank(town)) {
			baseProvince.setP_index(Long.valueOf(town));
		}
		if (StringUtils.isNotBlank(village)) {
			baseProvince.setP_index(Long.valueOf(village));
		}
		if (baseProvince.getP_index() != null) {
			baseProvince = getFacade().getBaseProvinceService().getBaseProvince(baseProvince);
		}

		if (!GenericValidator.isLong(id)) {// 插入
			entity.setAdd_user_id(ui.getId());
			entity.setAdd_user_name(ui.getUser_name());
			entity.setAdd_date(new Date());
			entity.setAudit_state(1);
			entity.setAudit_date(new Date());
			entity.setAudit_user_id(ui.getId());
			entity.setP_index(baseProvince.getP_index());
			super.getFacade().getVillageTourService().createVillageTour(entity);
			saveMessage(request, "entity.inerted");
		} else {// 修改

			entity.setUpdate_date(new Date());
			entity.setUpdate_user_id(ui.getId());

			super.getFacade().getVillageTourService().modifyVillageTour(entity);
			saveMessage(request, "entity.updated");
		}

		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
		pathBuffer.append("&mod_id=" + mod_id);
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "8")) {
			return super.checkPopedomInvalid(request, response);
		}
		setNaviStringToRequestScope(request);
		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (GenericValidator.isLong(id)) {

			VillageTour entity = new VillageTour();
			entity.setId(new Integer(id));
			entity = getFacade().getVillageTourService().getVillageTour(entity);
			if (null == entity) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");
			}
			if (entity.getP_index() != null) {
				super.setprovinceAndcityAndcountryToFrom(dynaBean, entity.getP_index());
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
			VillageTour entity = new VillageTour();
			entity.setIs_del(1);
			entity.setId(new Integer(id));
			entity.setDel_date(new Date());
			entity.setDel_user_id(userInfo.getId());
			getFacade().getVillageTourService().modifyVillageTour(entity);
			saveMessage(request, "entity.deleted");
		} else if (!ArrayUtils.isEmpty(pks)) {

			for (String cur_id : pks) {
				VillageTour entity = new VillageTour();
				entity.setIs_del(1);
				entity.setId(new Integer(cur_id));
				entity.setDel_date(new Date());
				entity.setDel_user_id(userInfo.getId());
				entity.getMap().put("is_virtual_no_def", true);
				getFacade().getVillageTourService().modifyVillageTour(entity);
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
