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

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.WlCompInfo;
import com.ebiz.webapp.util.PinyinTools;

public class WlCompInfoAction extends BaseAdminAction {

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String is_del = (String) dynaBean.get("is_del");
		String wl_comp_name_like = (String) dynaBean.get("wl_comp_name_like");
		String province = (String) dynaBean.get("province");
		String city = (String) dynaBean.get("city");
		String country = (String) dynaBean.get("country");
		WlCompInfo entity = new WlCompInfo();
		if (is_del == null) {
			entity.setIs_del(0);
			dynaBean.set("is_del", "0");
		}
		if (StringUtils.isNotBlank(wl_comp_name_like)) {
			entity.getMap().put("wl_comp_name_like", wl_comp_name_like);
		}
		if (StringUtils.isNotBlank(country)) {
			entity.setP_index(Integer.valueOf(country));
		} else if (StringUtils.isNotBlank(city)) {
			entity.getMap().put("p_index_city", StringUtils.substring(city, 0, 4));
		} else if (StringUtils.isNotBlank(province)) {
			entity.getMap().put("p_index_province", StringUtils.substring(province, 0, 2));
		}
		super.copyProperties(entity, form);

		Integer recordCount = getFacade().getWlCompInfoService().getWlCompInfoCount(entity);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<WlCompInfo> entityList = getFacade().getWlCompInfoService().getWlCompInfoPaginatedList(entity);
		request.setAttribute("entityList", entityList);
		return mapping.findForward("list");
	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		saveToken(request);
		DynaBean dynaBean = (DynaBean) form;
		dynaBean.set("order_value", 0);
		return mapping.findForward("input");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String mod_id = (String) dynaBean.get("mod_id");
		String country = (String) dynaBean.get("country");
		String city = (String) dynaBean.get("city");
		String province = (String) dynaBean.get("province");
		WlCompInfo entity = new WlCompInfo();
		UserInfo userInfo = super.getUserInfoFromSession(request);
		super.copyProperties(entity, form);

		if (StringUtils.isNotBlank(country)) {
			entity.setP_index(Integer.valueOf(country));
		} else if (StringUtils.isNotBlank(city)) {
			entity.setP_index(Integer.valueOf(city));
		} else if (StringUtils.isNotBlank(province)) {
			entity.setP_index(Integer.valueOf(province));
		}

		if (StringUtils.isNotBlank(entity.getWl_comp_name())) {
			entity.setP_alpha(PinyinTools.cn2FirstSpell(StringUtils.substring(entity.getWl_comp_name(), 0, 1))
					.toUpperCase());
		}

		if (null == entity.getId()) {// insert
			entity.setAdd_user_id(userInfo.getId());
			entity.setAdd_date(new Date());
			super.getFacade().getWlCompInfoService().createWlCompInfo(entity);
			saveMessage(request, "entity.inerted");
		} else {// update
			WlCompInfo wlCompInfo = new WlCompInfo();
			wlCompInfo.setId(entity.getId());
			wlCompInfo = getFacade().getWlCompInfoService().getWlCompInfo(wlCompInfo);
			if (null != wlCompInfo) {
				wlCompInfo.setUpdate_date(new Date());
				wlCompInfo.setUpdate_user_id(userInfo.getId());
				getFacade().getWlCompInfoService().modifyWlCompInfo(entity);
				saveMessage(request, "entity.updated");
			} else {
				saveError(request, "entity.missing");
			}
		}
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		return forward;
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		WlCompInfo entity = new WlCompInfo();
		if (GenericValidator.isLong(id)) {
			entity.setId(Integer.valueOf(id));
			entity = super.getFacade().getWlCompInfoService().getWlCompInfo(entity);

			// the line below is added for pagination
			entity.setQueryString(super.serialize(request, "id", "method"));
			// end

			super.copyProperties(form, entity);
			setprovinceAndcityAndcountryToFrom(dynaBean, (entity.getP_index()));
		}
		return mapping.findForward("input");
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
			UserInfo ui = new UserInfo();
			ui.setOwn_entp_id(new Integer(id));
			ui.setIs_del(0);
			WlCompInfo entity = new WlCompInfo();
			entity.setIs_del(1);
			entity.setId(new Integer(id));
			entity.setDel_date(new Date());
			entity.setDel_user_id(userInfo.getId());
			getFacade().getWlCompInfoService().modifyWlCompInfo(entity);
			saveMessage(request, "entity.deleted");
		} else if (!ArrayUtils.isEmpty(pks)) {
			for (String cur_id : pks) {
				UserInfo ui = new UserInfo();
				ui.setOwn_entp_id(new Integer(cur_id));
				ui.setIs_del(0);
			}
			WlCompInfo entity = new WlCompInfo();
			entity.setIs_del(1);
			entity.setDel_date(new Date());
			entity.setDel_user_id(userInfo.getId());
			entity.getMap().put("pks", pks);
			getFacade().getWlCompInfoService().modifyWlCompInfo(entity);
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
			WlCompInfo entity = new WlCompInfo();
			entity.setId(new Integer(id));
			entity = getFacade().getWlCompInfoService().getWlCompInfo(entity);

			// the line below is added for pagination
			entity.setQueryString(super.serialize(request, "id", "method"));
			// end
			super.copyProperties(form, entity);

			dynaBean.set("full_name", entity.getMap().get("full_name"));

		}
		return mapping.findForward("view");
	}

	public ActionForward updatePalpha(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		WlCompInfo entity = new WlCompInfo();
		entity.setIs_del(0);
		List<WlCompInfo> allList = super.getFacade().getWlCompInfoService().getWlCompInfoList(entity);
		if (null != allList && allList.size() > 0) {

			WlCompInfo wlCompInfoUpdate = null;
			for (WlCompInfo temp : allList) {
				wlCompInfoUpdate = new WlCompInfo();
				wlCompInfoUpdate.setId(temp.getId());
				if (StringUtils.isNotBlank(temp.getWl_comp_name())) {
					wlCompInfoUpdate.setP_alpha(PinyinTools.cn2FirstSpell(
							StringUtils.substring(temp.getWl_comp_name(), 0, 1)).toUpperCase());
				}
				super.getFacade().getWlCompInfoService().modifyWlCompInfo(wlCompInfoUpdate);

			}
		}

		return null;
	}
}
