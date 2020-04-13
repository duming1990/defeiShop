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

import com.alibaba.fastjson.JSONObject;
import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.BaseData;
import com.ebiz.webapp.domain.BaseProvince;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.Role;
import com.ebiz.webapp.domain.RoleUser;
import com.ebiz.webapp.domain.ShippingAddress;
import com.ebiz.webapp.domain.SysOperLog;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.EncryptUtilsV2;

/**
 * @author Wu,yang
 * @version 2011-04-21
 */
public class UserInfo100Action extends BaseAdminAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "1")) {
			return super.checkPopedomInvalid(request, response);
		}
		setNaviStringToRequestScope(request);
		// super.setBaseDataListToSession(10, request);// 用户类型
		// super.setBaseDataListToSession(200, request);// 会员等级
		saveToken(request);
		// EntpInfo manage = new EntpInfo();
		// manage.setIs_del(0);
		// List<EntpInfo> entpList = getFacade().getEntpInfoService().getEntpInfoList(manage);
		// request.setAttribute("entpList", entpList);

		DynaBean dynaBean = (DynaBean) form;
		dynaBean.set("order_value", "0");
		dynaBean.set("user_type", Keys.UserType.USER_TYPE_100.getIndex());
		dynaBean.set("province", Keys.P_INDEX_INIT);
		dynaBean.set("city", Keys.P_INDEX_CITY);
		return mapping.findForward("input");
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "0")) {
			return super.checkPopedomInvalid(request, response);
		}
		setNaviStringToRequestScope(request);
		// super.setBaseDataListToSession(10, request);// 用户类型

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String is_del = (String) dynaBean.get("is_del");
		String user_name_like = (String) dynaBean.get("user_name_like");
		String own_entp_name_like = (String) dynaBean.get("own_entp_name_like");
		String today_date = (String) dynaBean.get("today_date");
		String province = (String) dynaBean.get("province");
		String city = (String) dynaBean.get("city");
		String country = (String) dynaBean.get("country");

		UserInfo entity = new UserInfo();
		super.copyProperties(entity, form);
		entity.getMap().put("user_name_like", user_name_like);
		entity.getMap().put("own_entp_name_like", own_entp_name_like);
		if (null == is_del) {
			entity.setIs_del(0);
			dynaBean.set("is_del", "0");
		}
		if (StringUtils.isNotBlank(today_date)) {
			entity.getMap().put("today_date", today_date);
		}
		if (StringUtils.isNotBlank(country)) {
			entity.getMap().put("p_index_like", country);
		} else if (StringUtils.isNotBlank(city)) {
			entity.getMap().put("p_index_like", city.substring(0, 4));
		} else if (StringUtils.isNotBlank(province)) {
			entity.getMap().put("p_index_like", province.substring(0, 2));
		}
		entity.setUser_type(Keys.UserType.USER_TYPE_100.getIndex());
		Integer recordCount = getFacade().getUserInfoService().getUserInfoCount(entity);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<UserInfo> list = getFacade().getUserInfoService().getUserInfoPaginatedList(entity);

		if ((null != list) && (list.size() > 0)) {
			for (UserInfo ui : list) {
				if (null != ui.getOwn_entp_id()) {
					EntpInfo ei = new EntpInfo();
					ei.setId(ui.getOwn_entp_id());
					ei = super.getFacade().getEntpInfoService().getEntpInfo(ei);
					if (null != ei) {
						ui.getMap().put("entpInfo", ei);
					}
				}

				int user_state = 0;
				// 去查询一下是否非法 或者合法
				SysOperLog sysOperLog = new SysOperLog();
				sysOperLog.setLink_id(ui.getId());
				sysOperLog.setOper_type(Keys.SysOperType.SysOperType_30.getIndex());
				sysOperLog.setIs_del(0);
				sysOperLog = super.getFacade().getSysOperLogService().getSysOperLog(sysOperLog);
				if (null != sysOperLog && sysOperLog.getPre_number() >= Keys.LOGIN_ERROR_TIME) {
					user_state = 1;
				}
				ui.getMap().put("user_state", user_state);
			}
		}

		request.setAttribute("entityList", list);

		request.setAttribute("UserLevelBaseDataList",
				super.getBaseDataList(Keys.BASE_DATA_ID.BASE_DATA_ID_200.getIndex()));

		return mapping.findForward("list");
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "2")) {
			return super.checkPopedomInvalid(request, response);
		}
		setNaviStringToRequestScope(request);
		super.setBaseDataListToSession(10, request);// 用户类型
		super.setBaseDataListToSession(200, request);// 会员等级
		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		UserInfo entity = getFacade().getUserInfoService().getUserInfo(new UserInfo(new Integer(id)));
		entity.setQueryString(super.serialize(request, "id", "method"));
		super.copyProperties(form, entity);
		if (null != entity.getP_index()) {
			setprovinceAndcityAndcountryToFrom(dynaBean, Integer.valueOf(entity.getP_index()));
		}

		// EntpInfo manage = new EntpInfo();
		// manage.setIs_del(0);
		// List<EntpInfo> entpList = getFacade().getEntpInfoService().getEntpInfoList(manage);
		// request.setAttribute("entpList", entpList);

		return mapping.findForward("input");
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

		UserInfo sessionUi = super.getUserInfoFromSession(request);

		DynaBean dynaBean = (DynaBean) form;
		String user_name = (String) dynaBean.get("user_name");
		String mod_id = (String) dynaBean.get("mod_id");
		String province = (String) dynaBean.get("province");
		String city = (String) dynaBean.get("city");
		String country = (String) dynaBean.get("country");
		String id = (String) dynaBean.get("id");
		UserInfo entity = new UserInfo();
		super.copyProperties(entity, form);
		if (StringUtils.isNotBlank(country)) {
			entity.setP_index(new Integer(country));
		} else if (StringUtils.isNotBlank(city)) {
			entity.setP_index(new Integer(city));
		} else if (StringUtils.isNotBlank(province)) {
			entity.setP_index(new Integer(province));
		}

		if (null != user_name) {
			entity.setUser_name(user_name.trim());
		}
		String password = entity.getPassword();

		UserInfo ui1 = new UserInfo();
		ui1.setUser_name(user_name);
		ui1.setIs_del(0);
		ui1.getMap().put("not_in_id", id);
		int count1 = getFacade().getUserInfoService().getUserInfoCount(ui1);
		if (count1 > 0) {
			String msg = "你的用户名太响亮了，已经被注册。";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		resetToken(request);// 全部的判断完后在resetToken
		if (null == entity.getId()) {// insert
			entity.setPassword(EncryptUtilsV2.MD5Encode(password));
			entity.setLogin_count(new Integer("0"));
			entity.setIs_del(0);
			entity.setIs_active(1);// 已激活
			entity.setAdd_date(new Date());
			entity.setAdd_user_id(sessionUi.getId());

			// entity.getMap().put("insert_user_realtion", "true");

			getFacade().getUserInfoService().createUserInfo(entity);
			saveMessage(request, "entity.inerted");
		} else {// update
			entity.setUpdate_date(new Date());
			entity.setUpdate_user_id(sessionUi.getId());
			// entity.getMap().put("update_role_user", "ture");
			getFacade().getUserInfoService().modifyUserInfo(entity);
			saveMessage(request, "entity.updated");
		}
		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=").append(mod_id);
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "4")) {
			return super.checkPopedomInvalid(request, response);
		}
		DynaBean dynaBean = (DynaBean) form;

		String id = (String) dynaBean.get("id");
		String[] pks = (String[]) dynaBean.get("pks");
		// String mod_id = (String) dynaBean.get("mod_id");

		UserInfo sessionUi = super.getUserInfoFromSession(request);

		if (!StringUtils.isBlank(id) && GenericValidator.isLong(id)) {
			UserInfo entity = new UserInfo();
			entity.setIs_del(1);
			entity.setId(new Integer(id));
			entity.setDel_date(new Date());
			entity.setDel_user_id(sessionUi.getId());
			getFacade().getUserInfoService().modifyUserInfo(entity);
			saveMessage(request, "entity.deleted");
		} else if (!ArrayUtils.isEmpty(pks)) {
			UserInfo entity = new UserInfo();
			entity.setIs_del(1);
			entity.setDel_date(new Date());
			entity.setDel_user_id(sessionUi.getId());
			entity.getMap().put("pks", pks);
			getFacade().getUserInfoService().modifyUserInfo(entity);
			saveMessage(request, "entity.deleted");
		}
		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(super.serialize(request, "id", "ids", "method")));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	@SuppressWarnings("unused")
	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "0")) {
			return super.checkPopedomInvalid(request, response);
		}
		setNaviStringToRequestScope(request);
		super.setBaseDataListToSession(10, request);// 用户类型
		super.setBaseDataListToSession(200, request);// 会员等级

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (GenericValidator.isLong(id)) {
			UserInfo userInfo = new UserInfo();
			userInfo.setId(new Integer(id));
			UserInfo entity = getFacade().getUserInfoService().getUserInfo(userInfo);
			if (null == entity) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");
			}

			if (null != entity.getOwn_entp_id()) {
				EntpInfo ei = new EntpInfo();
				ei.setId(entity.getOwn_entp_id());
				ei = super.getFacade().getEntpInfoService().getEntpInfo(ei);
				if (null != ei) {
					request.setAttribute("entpInfo", ei);
				}
			}

			if (null != entity.getUser_type()) {
				BaseData baseData = new BaseData();
				baseData.setIs_del(0);
				baseData.setId(entity.getUser_type());
				baseData = super.getFacade().getBaseDataService().getBaseData(baseData);
				if (null != baseData) {
					request.setAttribute("type_name", baseData.getType_name());
				}
			}

			if (null != entity.getUser_level()) {
				BaseData baseData = new BaseData();
				baseData.setIs_del(0);
				baseData.setId(entity.getUser_level());
				baseData = super.getFacade().getBaseDataService().getBaseData(baseData);
				if (null != baseData) {
					request.setAttribute("user_level", baseData.getType_name());
				}
			}

			super.copyProperties(form, entity);

			if (null != entity.getP_index()) {
				BaseProvince baseProvince = new BaseProvince();
				baseProvince.setP_index(entity.getP_index().longValue());
				baseProvince = super.getFacade().getBaseProvinceService().getBaseProvince(baseProvince);
				if (null != baseProvince) {
					dynaBean.set("full_name", baseProvince.getFull_name());
				}
			}

			// 收货人信息
			ShippingAddress sd = new ShippingAddress();
			sd.setAdd_user_id(new Integer(id));
			List<ShippingAddress> addr = getFacade().getShippingAddressService().getShippingAddressList(sd);
			if ((addr != null) && (addr.size() > 0)) {
				entity.getMap().put("shippingAddress",
						getFacade().getShippingAddressService().getShippingAddress(addr.get(0)));
			}
			return mapping.findForward("view");
		} else {
			this.saveError(request, "errors.Integer", new String[] { id });
			return mapping.findForward("list");
		}
	}

	public ActionForward getQueryUserNames(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String query = (String) dynaBean.get("query");
		String pageCount = (String) dynaBean.get("pageCount");

		StringBuffer sb = new StringBuffer();
		int count = 20;

		if (StringUtils.isNotBlank(pageCount)) {
			count = Integer.valueOf(pageCount);
		}

		if (StringUtils.isNotBlank(query)) {

			UserInfo userInfo = new UserInfo();
			userInfo.setIs_del(0);
			userInfo.getMap().put("user_name_like", query);
			userInfo.getRow().setCount(count);// 默认取前20调符合匹配数据
			List<UserInfo> userInfoList = getFacade().getUserInfoService().getUserInfoList(userInfo);
			if ((null != userInfoList) && (userInfoList.size() > 0)) {
				sb.append("{");
				sb.append("\"query\":\"").append(query).append("\",");
				sb.append("\"suggestions\":[");

				String[] userIdsArray = new String[userInfoList.size()];
				String[] userNamesArray = new String[userInfoList.size()];
				for (int i = 0; i < userInfoList.size(); i++) {
					userNamesArray[i] = "\"" + userInfoList.get(i).getUser_name() + "\"";
					userIdsArray[i] = "\"" + userInfoList.get(i).getId() + "\"";
				}

				sb.append(StringUtils.join(userNamesArray, ","));
				sb.append("],");
				sb.append("\"data\":[").append(StringUtils.join(userIdsArray, ",")).append("]");

				sb.append("}");

			}

		}
		logger.info(sb.toString());
		super.renderJson(response, sb.toString());

		return null;
	}

	public ActionForward checkLoginNameAndEmail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String user_name = (String) dynaBean.get("user_name");
		String id = (String) dynaBean.get("id");
		String email = (String) dynaBean.get("email");

		Integer recordCount = 0;
		if (StringUtils.isBlank(user_name)) {
			UserInfo userInfo = new UserInfo();
			userInfo.getMap().put("not_in_id", id);
			userInfo.setUser_name(user_name);
			recordCount = super.getFacade().getUserInfoService().getUserInfoCount(userInfo);
		}

		Integer recordCount2 = 0;
		if (StringUtils.isNotBlank(email)) {
			UserInfo userInfo2 = new UserInfo();
			userInfo2.setEmail(email);
			userInfo2.getMap().put("not_in_id", id);
			recordCount2 = super.getFacade().getUserInfoService().getUserInfoCount(userInfo2);
		}
		String flag = "0";
		if (recordCount.intValue() != 0) {
			flag = "1";
		}
		if (recordCount2.intValue() != 0) {
			flag = "2";
		}
		if ((recordCount.intValue() != 0) && (recordCount2.intValue() != 0)) {
			flag = "3";
		}
		super.renderJson(response, flag);
		return null;
	}

	public ActionForward setRoleUser(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "1")) {
			return super.checkPopedomInvalid(request, response);
		}
		DynaBean dynaBean = (DynaBean) form;
		String user_id = (String) dynaBean.get("user_id");
		if (!GenericValidator.isInt(user_id)) {
			String msg = "user_id参数不正确";
			return super.showMsgForManager(request, response, msg);
		}

		setNaviStringToRequestScope(request);
		saveToken(request);
		UserInfo ui = super.getUserInfo(Integer.valueOf(user_id));

		Role roleHasSet = new Role();
		roleHasSet.getMap().put("user_id_has_set", ui.getId());
		roleHasSet.setIs_del(0);
		List<Role> roleHasSetList = getFacade().getRoleService().getRoleList(roleHasSet);
		request.setAttribute("roleHasSetList", roleHasSetList);

		Role roleNotSet = new Role();
		roleNotSet.getMap().put("role_id_gt", 10);// 这里写死了
		roleNotSet.getMap().put("user_id_not_set", ui.getId());
		roleNotSet.setIs_del(0);
		List<Role> roleNotSetList = getFacade().getRoleService().getRoleList(roleNotSet);
		request.setAttribute("roleNotSetList", roleNotSetList);

		return new ActionForward("/admin/UserInfo100/form_role.jsp");
	}

	public ActionForward saveRoleUser(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "1")) {
			return super.checkPopedomInvalid(request, response);
		}
		DynaBean dynaBean = (DynaBean) form;
		String user_id = (String) dynaBean.get("user_id");
		String[] role_ids = request.getParameterValues("role_ids");
		String mod_id = (String) dynaBean.get("mod_id");
		if (!GenericValidator.isInt(user_id)) {
			String msg = "user_id参数不正确";
			return super.showMsgForManager(request, response, msg);
		}
		if (ArrayUtils.isEmpty(role_ids)) {
			String msg = "请选择分配角色";
			return super.showMsgForManager(request, response, msg);
		}
		if (isCancelled(request)) {
			return list(mapping, form, request, response);
		}
		if (!isTokenValid(request)) {
			saveError(request, "errors.token");
			return list(mapping, form, request, response);
		}
		resetToken(request);// 全部的判断完后在resetToken

		for (String role_id : role_ids) {
			RoleUser queryHasExist = new RoleUser();
			queryHasExist.setRole_id(Integer.valueOf(role_id));
			queryHasExist.setUser_id(Integer.valueOf(user_id));
			int count = super.getFacade().getRoleUserService().getRoleUserCount(queryHasExist);
			if (count == 0) {
				RoleUser entity = new RoleUser();
				entity.setRole_id(Integer.valueOf(role_id));
				entity.setUser_id(Integer.valueOf(user_id));
				getFacade().getRoleUserService().createRoleUser(entity);
			}
		}
		UserInfo userInfo = new UserInfo();
		super.copyProperties(userInfo, form);
		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=").append(mod_id);
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(userInfo.getQueryString()));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward cancleRoleUser(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String user_id = (String) dynaBean.get("user_id");
		String role_id = (String) dynaBean.get("role_id");

		String code = "0";
		String msg = "参数有误！";

		if (!GenericValidator.isInt(user_id)) {
			msg = "user_id参数不正确";
			super.ajaxReturnInfo(response, code, msg, null);
			return null;
		}
		if (!GenericValidator.isInt(user_id)) {
			msg = "role_id参数不正确";
			super.ajaxReturnInfo(response, code, msg, null);
			return null;
		}

		// 获取该角色的相应用户列表
		RoleUser entity = new RoleUser();
		entity.getMap().put("role_id", role_id);
		entity.getMap().put("user_id", user_id);
		int count = super.getFacade().getRoleUserService().removeRoleUser(entity);
		if (count > 0) {
			code = "1";
			msg = "操作成功！";
		} else {
			code = "0";
			msg = "操作失败！";
		}
		super.ajaxReturnInfo(response, code, msg, null);
		return null;
	}

	public ActionForward updateUserIsNomal(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String user_id = (String) dynaBean.get("user_id");

		JSONObject data = new JSONObject();
		String ret = "0";
		String msg = "";

		if (StringUtils.isBlank(user_id)) {
			msg = "参数有误";
			data.put("ret", ret);
			data.put("msg", msg);
			super.renderJson(response, data.toJSONString());
			return null;
		}

		UserInfo userInfo = super.getUserInfo(Integer.valueOf(user_id));

		if (null == userInfo) {
			msg = "用户不存在！";
			data.put("ret", ret);
			data.put("msg", msg);
			super.renderJson(response, data.toJSONString());
			return null;
		}

		SysOperLog sysOperLogUpdate = new SysOperLog();
		sysOperLogUpdate.getMap().put("link_id", user_id);
		sysOperLogUpdate.getMap().put("oper_type", Keys.SysOperType.SysOperType_30.getIndex());
		sysOperLogUpdate.setPre_number(0);
		super.getFacade().getSysOperLogService().modifySysOperLog(sysOperLogUpdate);

		msg = "修改成功";
		ret = "1";
		data.put("ret", ret);
		data.put("msg", msg);
		super.renderJson(response, data.toJSONString());
		return null;

	}

	public ActionForward updateToCityManager(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JSONObject data = new JSONObject();
		String ret = "0";
		String msg = "";

		DynaBean dynaBean = (DynaBean) form;
		String user_id = (String) dynaBean.get("user_id");
		String is_city_manager = (String) dynaBean.get("is_city_manager");
		if (StringUtils.isNotBlank(user_id) && GenericValidator.isInt(user_id)
				&& StringUtils.isNotBlank(is_city_manager)) {
			UserInfo userInfoQuery = super.getUserInfo(Integer.valueOf(user_id));
			if (null != userInfoQuery) {
				UserInfo userInfoUpdate = new UserInfo();
				userInfoUpdate.setId(userInfoQuery.getId());
				userInfoUpdate.setIs_city_manager(Integer.valueOf(is_city_manager));

				if (is_city_manager.equals("1")) {// 设置成专员 需要直接升级为2星 然后给对应的积分
					userInfoUpdate.getMap().put("set_city_manager", "true");
				} else if (is_city_manager.equals("0")) {// 取消专员 需要降星级 然后去除升级给的积分
					userInfoUpdate.getMap().put("cancal_city_manager", "true");
				}

				int count = super.getFacade().getUserInfoService().modifyUserInfo(userInfoUpdate);
				if (count > 0) {
					msg = "设置成功!";
					ret = "1";
				}
			}
		}

		data.put("ret", ret);
		data.put("msg", msg);
		super.renderJson(response, data.toJSONString());
		return null;
	}

}
