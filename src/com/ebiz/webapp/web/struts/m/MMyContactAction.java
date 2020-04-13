package com.ebiz.webapp.web.struts.m;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.alibaba.fastjson.JSONObject;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.VillageContactGroup;
import com.ebiz.webapp.domain.VillageContactList;
import com.ebiz.webapp.web.Keys;

/**
 * @author 刘佳
 * @date: 2018年2月2日 下午12:13:17
 */
public class MMyContactAction extends MBaseWebAction {

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

		request.setAttribute("header_title", "我的通讯录");
		request.setAttribute("titleSideName", Keys.TopBtns.ADD.getName());

		return new ActionForward("/../m/MMyContact/list.jsp");
	}

	public ActionForward getAjaxData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = "0", msg = "";
		JSONObject data = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String user_id = (String) dynaBean.get("user_id");

		if (StringUtils.isBlank(user_id)) {
			msg = "参数有误！";
			super.ajaxReturnInfo(response, code, msg, data);
			return null;
		}

		VillageContactGroup entity = new VillageContactGroup();
		entity.getMap().put("query_fenzu", user_id);
		entity.setIs_del(0);
		// entity.setAdd_user_id(Integer.valueOf(user_id));
		List<VillageContactGroup> entityList = getFacade().getVillageContactGroupService()
				.getVillageContactGroupNameAndCount(entity);
		if (null != entityList && entityList.size() > 0) {
			for (VillageContactGroup temp : entityList) {
				VillageContactList villageContactList = new VillageContactList();
				villageContactList.setGroup_id(temp.getId());
				villageContactList.setIs_del(0);
				villageContactList.setAdd_user_id(Integer.valueOf(user_id));
				List<VillageContactList> villageContactListList = super.getFacade().getVillageContactListService()
						.getVillageContactListList(villageContactList);
				temp.getMap().put("villageContactListList", villageContactListList);
			}

		}

		data.put("entityList", entityList);
		code = "1";
		super.returnInfo(response, code, msg, data);
		return null;
	}

	public ActionForward updateNickName(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = "0", msg = "";
		JSONObject data = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String nick_name = (String) dynaBean.get("nick_name");

		if (StringUtils.isBlank(id) || StringUtils.isBlank(nick_name)) {
			msg = "参数有误！";
			super.ajaxReturnInfo(response, code, msg, data);
			return null;
		}

		VillageContactList villageContactList = new VillageContactList();
		villageContactList.setId(Integer.valueOf(id));
		villageContactList.setIs_del(0);
		villageContactList = super.getFacade().getVillageContactListService().getVillageContactList(villageContactList);
		if (null == villageContactList) {
			msg = "未找到该联系人！";
			super.ajaxReturnInfo(response, code, msg, data);
			return null;
		}

		VillageContactList villageContactListUpdate = new VillageContactList();
		villageContactListUpdate.setId(villageContactList.getId());
		villageContactListUpdate.setNick_name(nick_name);
		super.getFacade().getVillageContactListService().modifyVillageContactList(villageContactListUpdate);

		msg = "修改成功！";
		code = "1";
		super.returnInfo(response, code, msg, data);
		return null;
	}

	public ActionForward addGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = "0", msg = "";
		JSONObject data = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String group_name = (String) dynaBean.get("group_name");

		if (StringUtils.isBlank(group_name)) {
			msg = "参数有误！";
			super.ajaxReturnInfo(response, code, msg, data);
			return null;
		}

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			msg = "您还未登录，请先登录系统！";
			super.ajaxReturnInfo(response, code, msg, data);
			return null;
		}

		VillageContactGroup entity = new VillageContactGroup();
		entity.setGroup_name(group_name);
		entity.setAdd_date(new Date());
		entity.setAdd_user_id(ui.getId());
		entity.setAdd_user_name(ui.getUser_name());
		int id = super.getFacade().getVillageContactGroupService().createVillageContactGroup(entity);

		VillageContactGroup entityQuery = new VillageContactGroup();
		entityQuery.setId(id);
		entityQuery = super.getFacade().getVillageContactGroupService().getVillageContactGroup(entityQuery);
		entityQuery.getMap().put("fzCount", 0);
		data.put("entity", entityQuery);

		msg = "添加成功！";
		code = "1";
		super.returnInfo(response, code, msg, data);
		return null;
	}

	public ActionForward deleteGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = "0", msg = "";
		JSONObject data = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String groupid = (String) dynaBean.get("id");

		if (StringUtils.isBlank(groupid)) {
			msg = "参数有误！";
			super.ajaxReturnInfo(response, code, msg, data);
			return null;
		}

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			msg = "您还未登录，请先登录系统！";
			super.ajaxReturnInfo(response, code, msg, data);
			return null;
		}

		VillageContactGroup entityQuery = new VillageContactGroup();
		entityQuery.setId(Integer.valueOf(groupid));
		entityQuery.setIs_del(0);
		entityQuery = super.getFacade().getVillageContactGroupService().getVillageContactGroup(entityQuery);
		if (null == entityQuery) {
			msg = "未查询到该分组！";
			super.ajaxReturnInfo(response, code, msg, data);
			return null;
		}

		VillageContactGroup contactGroupUpdate = new VillageContactGroup();
		contactGroupUpdate.setId(Integer.valueOf(groupid));
		contactGroupUpdate.setIs_del(1);
		contactGroupUpdate.setDel_date(new Date());
		contactGroupUpdate.setDel_user_id(ui.getId());
		super.getFacade().getVillageContactGroupService().modifyVillageContactGroup(contactGroupUpdate);

		VillageContactList villageContactList = new VillageContactList();
		villageContactList.setGroup_id(Keys.deafult_group_id);
		villageContactList.getMap().put("group_id", groupid);
		super.getFacade().getVillageContactListService().modifyVillageContactList(villageContactList);

		VillageContactList villageContactListQuery = new VillageContactList();
		villageContactListQuery.setGroup_id(Keys.deafult_group_id);
		villageContactListQuery.setAdd_user_id(ui.getId());
		villageContactListQuery.setIs_del(0);

		List<VillageContactList> villageContactListList = super.getFacade().getVillageContactListService()
				.getVillageContactListList(villageContactListQuery);

		data.put("villageContactListList", villageContactListList);

		msg = "删除成功！";
		code = "1";
		super.returnInfo(response, code, msg, data);
		return null;
	}

	public ActionForward fenzu(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = "0", msg = "";
		JSONObject data = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String groupId = (String) dynaBean.get("groupId");

		if (StringUtils.isBlank(id) || StringUtils.isBlank(groupId)) {
			msg = "参数有误！";
			super.ajaxReturnInfo(response, code, msg, data);
			return null;
		}

		VillageContactGroup villageContactGroup = new VillageContactGroup();
		villageContactGroup.setId(Integer.valueOf(groupId));
		villageContactGroup.setIs_del(0);
		villageContactGroup = super.getFacade().getVillageContactGroupService()
				.getVillageContactGroup(villageContactGroup);
		if (null == villageContactGroup) {
			msg = "未找到该分组！";
			super.ajaxReturnInfo(response, code, msg, data);
			return null;
		}

		VillageContactList villageContactListUpdate = new VillageContactList();
		villageContactListUpdate.setId(Integer.valueOf(id));
		villageContactListUpdate.setGroup_id(Integer.valueOf(groupId));
		super.getFacade().getVillageContactListService().modifyVillageContactList(villageContactListUpdate);

		msg = "修改成功！";
		code = "1";
		super.returnInfo(response, code, msg, data);
		return null;
	}
}
