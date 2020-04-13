package com.ebiz.webapp.web.struts.m;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.alibaba.fastjson.JSONObject;
import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.VillageContactList;
import com.ebiz.webapp.domain.VillageMember;
import com.ebiz.webapp.web.Keys;

/**
 * @author 刘佳
 * @date: 2018年2月2日 下午12:13:17
 */
public class MVillageMemberAction extends MBaseWebAction {

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
			return showTipNotLogin(mapping, form, request, response, msg);
		}

		request.setAttribute("header_title", "村站通讯录");

		return new ActionForward("/../m/MVillageMember/list.jsp");
	}

	public ActionForward getAjaxData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = "-1", msg = "";
		JSONObject data = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String village_id = (String) dynaBean.get("village_id");
		String user_name_like = (String) dynaBean.get("user_name_like");
		String startPage = (String) dynaBean.get("startPage");
		String pageSize = (String) dynaBean.get("pageSize");

		if (StringUtils.isBlank(village_id)) {
			msg = "参数有误！";
			super.ajaxReturnInfo(response, code, msg, data);
			return null;
		}

		UserInfo ui = super.getUserInfoFromSession(request);

		log.info("===villageMember==");
		if (null != ui) {
			VillageMember villageMember = super.getApplyVillage(Integer.valueOf(village_id), ui.getId(), 1);
			if (null == villageMember) {
				msg = "未加入村子无法查看!";
				super.ajaxReturnInfo(response, code, msg, data);
				return null;
			}
		}

		Pager pager = (Pager) dynaBean.get("pager");

		if (StringUtils.isBlank(pageSize)) {
			pageSize = "10";
		}
		if (StringUtils.isBlank(startPage)) {
			startPage = "0";
		}

		VillageMember entity = new VillageMember();
		entity.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		entity.setVillage_id(Integer.valueOf(village_id));
		entity.setIs_del(0);

		entity.getMap().put("user_name_like", user_name_like);

		Integer recordCount = getFacade().getVillageMemberService().getVillageMemberCount(entity);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		entity.getRow().setFirst(Integer.valueOf(startPage) * Integer.valueOf(pageSize));
		entity.getRow().setCount(pager.getRowCount());

		List<VillageMember> entityList = getFacade().getVillageMemberService().getVillageMemberPaginatedList(entity);

		data.put("entityList", entityList);
		code = "1";
		super.returnInfo(response, code, msg, data);
		return null;
	}

	public ActionForward guanzhu(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}
		DynaBean dynaBean = (DynaBean) form;
		String type = (String) dynaBean.get("type");

		if (type.equals("1")) {
			request.setAttribute("header_title", "我的关注");

		} else {
			request.setAttribute("header_title", "我的粉丝");
		}

		return new ActionForward("/../m/MVillageMember/guanzhu.jsp");
	}

	/**
	 * @desc获取关注人列表
	 */
	public ActionForward getAjaxDataFollow(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = "-1", msg = "";
		JSONObject data = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String user_id = (String) dynaBean.get("user_id");
		String type = (String) dynaBean.get("type");
		String user_name_like = (String) dynaBean.get("user_name_like");
		String startPage = (String) dynaBean.get("startPage");
		String pageSize = (String) dynaBean.get("pageSize");

		if (StringUtils.isBlank(user_id)) {
			msg = "参数有误！";
			super.ajaxReturnInfo(response, code, msg, data);
			return null;
		}
		UserInfo ui = super.getUserInfo(user_id);
		data.put("ui", ui);

		UserInfo userInfo = super.getUserInfoFromSession(request);
		data.put("userInfo", userInfo);

		Pager pager = (Pager) dynaBean.get("pager");

		if (StringUtils.isBlank(pageSize)) {
			pageSize = "10";
		}
		if (StringUtils.isBlank(startPage)) {
			startPage = "0";
		}

		VillageContactList entity = new VillageContactList();
		if (Integer.valueOf(type) == 1) {// 关注列表
			entity.setAdd_user_id(Integer.valueOf(user_id));
		}
		if (Integer.valueOf(type) == 2) {// 粉丝列表
			entity.setContact_user_id(Integer.valueOf(user_id));
		}
		entity.setIs_del(0);

		entity.getMap().put("user_name_like", user_name_like);

		Integer recordCount = getFacade().getVillageContactListService().getVillageContactListCount(entity);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		entity.getRow().setFirst(Integer.valueOf(startPage) * Integer.valueOf(pageSize));
		entity.getRow().setCount(pager.getRowCount());

		List<VillageContactList> entityList = getFacade().getVillageContactListService()
				.getVillageContactListPaginatedList(entity);

		data.put("entityList", entityList);
		data.put("type", type);
		code = "1";
		super.returnInfo(response, code, msg, data);
		return null;
	}
}
