package com.ebiz.webapp.web.struts.manager.customer;

import java.text.SimpleDateFormat;
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

import com.alibaba.fastjson.JSONObject;
import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.CardApply;
import com.ebiz.webapp.domain.CardPIndex;
import com.ebiz.webapp.domain.ServiceCenterInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;

public class WelfareCardApplyAction extends BaseCustomerAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(Keys.SESSION_USERINFO_KEY);
		if (null == userInfo) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(Keys.SESSION_USERINFO_KEY);

		if (null == userInfo) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}
		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);
		Pager pager = (Pager) dynaBean.get("pager");
		String title_like = (String) dynaBean.get("title_like");
		String st_add_date = (String) dynaBean.get("st_add_date");
		String en_add_date = (String) dynaBean.get("en_add_date");

		CardApply entity = new CardApply();
		super.copyProperties(entity, form);
		entity.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		entity.setAdd_uid(userInfo.getId());

		entity.getMap().put("title_like", title_like);
		entity.getMap().put("st_add_date", st_add_date);
		entity.getMap().put("en_add_date", en_add_date);
		Integer recordCount = getFacade().getCardApplyService().getCardApplyCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<CardApply> entityList = super.getFacade().getCardApplyService().getCardApplyPaginatedList(entity);
		if (null != entityList && entityList.size() > 0) {
			for (CardApply temp : entityList) {
				CardPIndex cPIndex = new CardPIndex();
				cPIndex.setCard_apply_id(temp.getId());
				List<CardPIndex> cPIndexList = getFacade().getCardPIndexService().getCardPIndexList(cPIndex);
				temp.getMap().put("cPIndexList", cPIndexList);
			}
		}
		request.setAttribute("entityList", entityList);
		return mapping.findForward("list");
	}

	public ActionForward getArea(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);
		Pager pager = (Pager) dynaBean.get("pager");
		String card_id = (String) dynaBean.get("id");

		CardPIndex entity = new CardPIndex();
		entity.setCard_apply_id(Integer.valueOf(card_id));
		List<CardPIndex> entityList = super.getFacade().getCardPIndexService().getCardPIndexList(entity);
		request.setAttribute("entityList", entityList);
		return new ActionForward("/../manager/customer/WelfareCardApply/area.jsp");
	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(Keys.SESSION_USERINFO_KEY);
		UserInfo userInfoTemp = super.getUserInfo(userInfo.getId());

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		saveToken(request);
		getsonSysModuleList(request, dynaBean);

		if (StringUtils.isNotBlank(id)) {
			CardApply entity = new CardApply();
			entity.setId(Integer.valueOf(id));
			entity.setIs_del(0);
			entity = getFacade().getCardApplyService().getCardApply(entity);
			if (null != entity) {
				CardPIndex cPIndex = new CardPIndex();
				cPIndex.setCard_apply_id(entity.getId());
				List<CardPIndex> list = getFacade().getCardPIndexService().getCardPIndexList(cPIndex);
				request.setAttribute("list", list);
				super.copyProperties(form, entity);
			}
		}

		return mapping.findForward("input");
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(Keys.SESSION_USERINFO_KEY);
		UserInfo userInfoTemp = super.getUserInfo(userInfo.getId());

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		saveToken(request);
		getsonSysModuleList(request, dynaBean);

		if (StringUtils.isBlank(id)) {
			String msg = "参数错误！";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		CardApply entity = new CardApply();
		entity.setId(Integer.valueOf(id));
		entity.setIs_del(0);
		entity = getFacade().getCardApplyService().getCardApply(entity);
		if (null != entity) {
			CardPIndex cPIndex = new CardPIndex();
			cPIndex.setCard_apply_id(entity.getId());
			List<CardPIndex> list = getFacade().getCardPIndexService().getCardPIndexList(cPIndex);
			request.setAttribute("list", list);
			super.copyProperties(form, entity);
		}

		return mapping.findForward("view");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(Keys.SESSION_USERINFO_KEY);
		if (null == userInfo) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}
		UserInfo userInfoTemp = super.getUserInfo(userInfo.getId());

		resetToken(request);

		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);
		String[] service_ids = request.getParameterValues("service_id");
		String[] p_names = request.getParameterValues("p_name");
		String[] service_names = request.getParameterValues("service_name");
		String[] p_indexs = request.getParameterValues("p_index");
		String id = (String) dynaBean.get("id");
		String end_date = (String) dynaBean.get("end_date");

		if (service_ids == null || service_ids.length < 0) {
			String msg = "参数错误！";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		if (p_names == null || p_names.length < 0) {
			String msg = "参数错误！";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		if (p_indexs == null || p_indexs.length < 0) {
			String msg = "参数错误！";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		ServiceCenterInfo service = new ServiceCenterInfo();
		service.setAdd_user_id(userInfoTemp.getId());
		service.setIs_del(0);
		service.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		service = super.getFacade().getServiceCenterInfoService().getServiceCenterInfo(service);
		if (null == service) {
			String msg = "县域不存在！";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");

		CardApply entity = new CardApply();
		super.copyProperties(entity, form);
		entity.setEnd_date(sdFormat_ymdhms.parse(end_date + " 23:59:59"));
		if (StringUtils.isNotBlank(id)) {// 修改
			entity.setUpdate_date(new Date());
			entity.setUpdate_uid(userInfoTemp.getId());

			List<CardPIndex> cPIndexList = new ArrayList<CardPIndex>();
			for (int i = 0; i < service_ids.length; i++) {
				CardPIndex cPIndex = new CardPIndex();
				cPIndex.setCard_apply_id(Integer.valueOf(id));
				cPIndex.setP_index(Long.valueOf(p_indexs[i]));
				cPIndex.setP_name(p_names[i]);
				cPIndex.setService_id(Integer.valueOf(service_ids[i]));
				cPIndex.setService_name(service_names[i]);
				cPIndexList.add(cPIndex);
			}
			entity.getMap().put("update_CardPIndex_list", cPIndexList);
			getFacade().getCardApplyService().modifyCardApply(entity);
			saveMessage(request, "entity.updated");
		} else {
			entity.setAdd_date(new Date());
			entity.setAdd_uid(userInfoTemp.getId());
			entity.setSevice_center_info_id(service.getId());
			entity.setService_name(service.getServicecenter_name());
			entity.setApply_no("Ap" + fmt.format(new Date()));

			List<CardPIndex> cPIndexList = new ArrayList<CardPIndex>();
			for (int i = 0; i < service_ids.length; i++) {
				CardPIndex cPIndex = new CardPIndex();
				cPIndex.setP_index(Long.valueOf(p_indexs[i]));
				cPIndex.setP_name(p_names[i]);
				cPIndex.setService_id(Integer.valueOf(service_ids[i]));
				cPIndex.setService_name(service_names[i]);
				cPIndexList.add(cPIndex);
			}
			entity.getMap().put("insert_CardPIndex_list", cPIndexList);
			getFacade().getCardApplyService().createCardApply(entity);
			saveMessage(request, "entity.inerted");
		}
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&");
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward chooseService(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String servicecenter_name_like = (String) dynaBean.get("servicecenter_name_like");
		String province = (String) dynaBean.get("province");
		String city = (String) dynaBean.get("city");
		String country = (String) dynaBean.get("country");

		ServiceCenterInfo entity = new ServiceCenterInfo();
		super.copyProperties(entity, form);
		entity.setIs_del(0);
		entity.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		entity.getMap().put("servicecenter_name_like", servicecenter_name_like);
		if (StringUtils.isNotBlank(country)) {
			entity.getMap().put("p_index_like", country);
		} else if (StringUtils.isNotBlank(city)) {
			entity.getMap().put("p_index_like", city.substring(0, 4));
		} else if (StringUtils.isNotBlank(province)) {
			entity.getMap().put("p_index_like", province.substring(0, 2));
		}
		// Integer recordCount = getFacade().getServiceCenterInfoService().getServiceCenterInfoCount(entity);
		// pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		// entity.getRow().setFirst(pager.getFirstRow());
		// entity.getRow().setCount(pager.getRowCount());

		List<ServiceCenterInfo> entityList = super.getFacade().getServiceCenterInfoService()
				.getServiceCenterInfoList(entity);
		request.setAttribute("entityList", entityList);
		return new ActionForward("/../manager/customer/WelfareCardApply/choose_service.jsp");
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

		CardApply entity = new CardApply();
		Date date = new Date();
		entity.setIs_del(1);
		entity.setDel_date(date);
		entity.setDel_uid(ui.getId());
		if (StringUtils.isNotBlank(id) && GenericValidator.isLong(id)) {
			entity.setId(Integer.valueOf(id));
			super.getFacade().getCardApplyService().modifyCardApply(entity);
			saveMessage(request, "entity.deleted");
		} else if (ArrayUtils.isNotEmpty(pks)) {

			String[] ids = new String[pks.length];
			for (int i = 0; i < pks.length; i++) {
				ids[i] = pks[i].split("_")[0];
			}

			entity.getMap().put("pks", ids);
			super.getFacade().getCardApplyService().modifyCardApply(entity);

			saveMessage(request, "entity.deleted");
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

	public ActionForward getService(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String service_ids = (String) dynaBean.get("service_ids");
		JSONObject data = new JSONObject();
		String code = "1";
		if (StringUtils.isNotBlank(service_ids)) {
			String[] ids = service_ids.split(",");
			String[] service_names = new String[ids.length];
			String[] p_indexs = new String[ids.length];
			String[] full_names = new String[ids.length];
			for (int i = 0; i < ids.length; i++) {
				ServiceCenterInfo service = new ServiceCenterInfo();
				service.setId(Integer.valueOf(ids[i]));
				service = getFacade().getServiceCenterInfoService().getServiceCenterInfo(service);
				if (service != null) {
					service_names[i] = service.getServicecenter_name();
					p_indexs[i] = service.getP_index().toString();
					full_names[i] = super.getFullNameByPindex(service.getP_index());
				}
			}
			data.put("service_names", service_names);
			data.put("p_indexs", p_indexs);
			data.put("full_names", full_names);
		}
		super.renderJson(response, data.toString());
		return null;
	}
}
