package com.ebiz.webapp.web.struts.manager.admin;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.alibaba.fastjson.JSONObject;
import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.BaseData;
import com.ebiz.webapp.domain.BaseProvince;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.ShippingAddress;
import com.ebiz.webapp.domain.SysOperLog;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.UserRelation;
import com.ebiz.webapp.domain.VillageInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.DESPlus;
import com.ebiz.webapp.web.util.EncryptUtilsV2;

/**
 * @author Wu,yang
 * @version 2011-04-21
 */
public class UserInfoAction extends BaseAdminAction {

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
		super.setBaseDataListToSession(Keys.BaseDataType.Base_Data_type_10.getIndex(), request);// 用户类型
		super.setBaseDataListToSession(Keys.BaseDataType.Base_Data_type_200.getIndex(), request);// 会员等级
		saveToken(request);
		DynaBean dynaBean = (DynaBean) form;
		dynaBean.set("order_value", "0");
		return mapping.findForward("input");
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "0")) {
			return super.checkPopedomInvalid(request, response);
		}
		setNaviStringToRequestScope(request);
		super.setBaseDataListToSession(10, request);// 用户类型

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String is_del = (String) dynaBean.get("is_del");
		String user_name_like = (String) dynaBean.get("user_name_like");

		String real_name_like = (String) dynaBean.get("real_name_like");
		String own_entp_name_like = (String) dynaBean.get("own_entp_name_like");
		String st_add_date = (String) dynaBean.get("st_add_date");
		String en_add_date = (String) dynaBean.get("en_add_date");
		String province = (String) dynaBean.get("province");
		String city = (String) dynaBean.get("city");
		String country = (String) dynaBean.get("country");
		String par_user_name_like = (String) dynaBean.get("par_user_name_like");

		UserInfo entity = new UserInfo();
		super.copyProperties(entity, form);
		entity.getMap().put("user_name_like", user_name_like);
		entity.getMap().put("real_name_like", real_name_like);
		entity.getMap().put("own_entp_name_like", own_entp_name_like);
		if (null == is_del) {
			entity.setIs_del(0);
			dynaBean.set("is_del", "0");
		}
		entity.getMap().put("st_add_date", st_add_date);
		entity.getMap().put("en_add_date", en_add_date);

		if (StringUtils.isNotBlank(country)) {
			entity.getMap().put("p_index_like", country);
		} else if (StringUtils.isNotBlank(city)) {
			entity.getMap().put("p_index_like", city.substring(0, 4));
		} else if (StringUtils.isNotBlank(province)) {
			entity.getMap().put("p_index_like", province.substring(0, 2));
		}

		if (StringUtils.isNotEmpty(par_user_name_like)) {
			entity.setYmid(par_user_name_like);
		}
		// 不查询内部员工的用户
		entity.getMap().put("user_type_ne", Keys.UserType.USER_TYPE_100.getIndex());

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
			}
		}

		request.setAttribute("entityList", list);
		request.setAttribute("init_pwd", Keys.INIT_PWD);

		request.setAttribute("basedatas", super.getBaseDataList(Keys.BaseDataType.Base_Data_type_200.getIndex()));
		return mapping.findForward("list");
	}

	public ActionForward toExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "0")) {
			return super.checkPopedomInvalid(request, response);
		}
		setNaviStringToRequestScope(request);
		super.setBaseDataListToSession(Keys.BaseDataType.Base_Data_type_10.getIndex(), request);// 用户类型

		DynaBean dynaBean = (DynaBean) form;
		Map<String, Object> model = new HashMap<String, Object>();

		String is_del = (String) dynaBean.get("is_del");
		String user_name_like = (String) dynaBean.get("user_name_like");
		String real_name_like = (String) dynaBean.get("real_name_like");
		String own_entp_name_like = (String) dynaBean.get("own_entp_name_like");
		String today_date = (String) dynaBean.get("today_date");
		String province = (String) dynaBean.get("province");
		String city = (String) dynaBean.get("city");
		String country = (String) dynaBean.get("country");

		String code = (String) dynaBean.get("code");

		UserInfo entity = new UserInfo();
		super.copyProperties(entity, form);
		entity.getMap().put("user_name_like", user_name_like);
		entity.getMap().put("real_name_like", real_name_like);
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

		// 不查询内部员工的用户
		entity.getMap().put("user_type_ne", Keys.UserType.USER_TYPE_100.getIndex());

		List<UserInfo> list = getFacade().getUserInfoService().getUserInfoList(entity);

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
			}
		}
		model.put("rmb_to_fanxianbi_rate", Keys.rmb_to_fanxianbi_rate);
		model.put("entityList", list);

		String content = getFacade().getTemplateService().getContent("UserInfo/list.ftl", model);
		// 下载文件出现乱码时，请参见此处
		String fname = EncryptUtilsV2.encodingFileName("用户信息表.xls");

		if (StringUtils.isBlank(code)) {
			code = "UTF-8";
		}
		response.setCharacterEncoding(code);
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename=" + fname);

		PrintWriter out = response.getWriter();
		out.println(content);
		out.flush();
		out.close();
		return null;
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "2")) {
			return super.checkPopedomInvalid(request, response);
		}
		setNaviStringToRequestScope(request);
		super.setBaseDataListToSession(Keys.BaseDataType.Base_Data_type_10.getIndex(), request);// 用户类型
		super.setBaseDataListToSession(Keys.BaseDataType.Base_Data_type_200.getIndex(), request);// 会员等级
		saveToken(request);
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		UserInfo entity = getFacade().getUserInfoService().getUserInfo(new UserInfo(new Integer(id)));
		entity.setQueryString(super.serialize(request, "id", "method"));
		super.copyProperties(form, entity);
		if (null != entity.getP_index()) {
			setprovinceAndcityAndcountryToFrom(dynaBean, Integer.valueOf(entity.getP_index()));
		}

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
		String ymid = (String) dynaBean.get("ymid");
		String id = (String) dynaBean.get("id");
		String user_type = (String) dynaBean.get("user_type");
		String update_bi_aid = (String) dynaBean.get("update_bi_aid");// 修改用户贫困金
		UserInfo entity = new UserInfo();
		super.copyProperties(entity, form);

		if (StringUtils.isNotBlank(update_bi_aid)) {
			entity.getMap().put("update_bi_aid", update_bi_aid);
		}

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

		UserInfo userInfo = new UserInfo();

		// 不是城市管理员的情况下
		if (Integer.valueOf(user_type) != Keys.UserType.USER_TYPE_19.getIndex()) {
			if (StringUtils.isBlank(ymid)) {
				String msg = "请填写邀请人";
				super.showMsgForManager(request, response, msg);
				return null;
			}
			UserInfo ui = new UserInfo();
			ui.getMap().put("ym_id", ymid);
			ui.setIs_del(0);
			int recordCount = getFacade().getUserInfoService().getUserInfoCount(ui);
			if (recordCount <= 0) {
				String msg = "填写邀请人不存在";
				super.showMsgForManager(request, response, msg);
				return null;
			}
			if (recordCount > 0) {
				ui = super.getFacade().getUserInfoService().getUserInfo(ui);
				if (ui.getIs_entp().intValue() == 1 || ui.getIs_fuwu().intValue() == 1
						|| ui.getUser_type().intValue() == Keys.UserType.USER_TYPE_1.getIndex()) {// 这几个类型不能作为推荐人
					String msg = "该用户不能作为推荐人";
					super.showMsgForManager(request, response, msg);
					return null;
				}
			}
			entity.getMap().put("insert_user_realtion", "true");
		} else {// 市门户管理员
			UserInfo ui1 = new UserInfo();
			ui1.setP_index(entity.getP_index());
			ui1.setIs_del(0);
			ui1.getMap().put("not_in_id", id);
			int count1 = getFacade().getUserInfoService().getUserInfoCount(ui1);
			if (count1 > 0) {
				String msg = "您选择的市已经被创建。";
				super.showMsgForManager(request, response, msg);
				return null;
			}
		}

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

		if (StringUtils.isNotBlank(entity.getMobile())) {
			ui1 = new UserInfo();
			ui1.setUser_name(user_name);
			ui1.setMobile(entity.getMobile());
			ui1.setIs_del(0);
			ui1.getMap().put("not_in_id", id);
			int count2 = getFacade().getUserInfoService().getUserInfoCount(ui1);
			if (count2 > 0) {
				String msg = "用户名或者手机号，已经被注册。";
				super.showMsgForManager(request, response, msg);
				return null;
			}
			ui1 = new UserInfo();
			ui1.setUser_type(entity.getUser_type());
			ui1.setMobile(entity.getMobile());
			ui1.setIs_del(0);
			ui1.getMap().put("not_in_id", id);
			int count3 = getFacade().getUserInfoService().getUserInfoCount(ui1);
			if (count3 > 0) {
				String msg = "手机号，已经被注册。";
				super.showMsgForManager(request, response, msg);
				return null;
			}
		}

		resetToken(request);// 全部的判断完后在resetToken
		if (null == entity.getId()) {// insert
			if (StringUtils.isBlank(password)) {
				password = Keys.INIT_PWD;
			}

			DESPlus des = new DESPlus();
			entity.setPassword(des.encrypt(password));
			entity.setLogin_count(new Integer("0"));
			entity.setIs_del(0);
			entity.setIs_active(1);// 已激活
			entity.setAdd_date(new Date());
			entity.setAdd_user_id(sessionUi.getId());

			getFacade().getUserInfoService().createUserInfo(entity);
			saveMessage(request, "entity.inerted");
		} else {// update
			userInfo = getFacade().getUserInfoService().getUserInfo(new UserInfo(entity.getId()));
			if (null != userInfo) {
				if (password != null && !password.equalsIgnoreCase(userInfo.getPassword())) {
					DESPlus des = new DESPlus();
					entity.setPassword(des.encrypt(password));
				}
				if ((null != entity.getIs_del()) && (entity.getIs_del().intValue() == 0)) {
					entity.setDel_date(null);
				}
				entity.setUpdate_date(new Date());
				entity.setUpdate_user_id(sessionUi.getId());
				entity.getMap().put("update_role_user", "ture");
				getFacade().getUserInfoService().modifyUserInfo(entity);
				saveMessage(request, "entity.updated");
			} else {
				saveError(request, "entity.missing");
			}
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

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "0")) {
			return super.checkPopedomInvalid(request, response);
		}
		setNaviStringToRequestScope(request);
		super.setBaseDataListToSession(Keys.BaseDataType.Base_Data_type_10.getIndex(), request);// 用户类型
		super.setBaseDataListToSession(Keys.BASE_DATA_ID.BASE_DATA_ID_200.getIndex(), request);// 会员等级

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

	public ActionForward active(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		UserInfo userInfo = new UserInfo();
		if (StringUtils.isNotBlank(id)) {
			userInfo.setId(Integer.valueOf(id));
			userInfo.setIs_active(1);
		}
		super.getFacade().getUserInfoService().modifyUserInfo(userInfo);

		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(super.serialize(request, "id", "method")));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
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

	public ActionForward clearUserWeiXin(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String uid = request.getParameter("uid");

		JSONObject jsonObject = new JSONObject();
		String ret = "0", msg = "操作成功！";

		if (StringUtils.isBlank(uid)) {
			msg = "参数有误！";
			jsonObject.put("ret", ret);
			jsonObject.put("msg", msg);
			super.renderJson(response, jsonObject.toString());
			return null;
		}

		UserInfo entity = new UserInfo();
		entity.setId(new Integer(uid));
		entity = super.getFacade().getUserInfoService().getUserInfo(entity);
		if (null == entity) {
			msg = "未查询到该用户！";
			jsonObject.put("ret", ret);
			jsonObject.put("msg", msg);
			super.renderJson(response, jsonObject.toString());
			return null;
		}
		UserInfo entityUpdate = new UserInfo();
		entityUpdate.setId(new Integer(uid));
		entityUpdate.getMap().put("clear_link_weixin_info", "true");

		super.getFacade().getUserInfoService().modifyUserInfo(entityUpdate);
		ret = "1";
		jsonObject.put("ret", ret);
		jsonObject.put("msg", msg);
		super.renderJson(response, jsonObject.toString());
		return null;
	}

	public ActionForward setUserLevel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JSONObject data = new JSONObject();
		String ret = "0";
		String msg = "";

		DynaBean dynaBean = (DynaBean) form;
		String user_id = (String) dynaBean.get("user_id");
		String flag = (String) dynaBean.get("flag");

		if (StringUtils.isNotBlank(user_id) && GenericValidator.isInt(user_id) && StringUtils.isNotBlank(flag)) {
			UserInfo userInfoQuery = super.getUserInfo(Integer.valueOf(user_id));
			if (null != userInfoQuery) {
				UserInfo userInfoUpdate = new UserInfo();
				userInfoUpdate.setId(userInfoQuery.getId());
				if (flag.equals("1")) {// 需要生成订单
					userInfoUpdate.setUser_level(Keys.USER_LEVEL_ONE);
					userInfoUpdate.getMap().put("set_user_level", "true");
					userInfoUpdate.setCard_end_date(DateUtils.addYears(new Date(), 1));
					userInfoUpdate.setUser_no(super.createUserInfoNo(userInfoQuery.getId()));
				} else if (flag.equals("0")) {
					userInfoUpdate.setUser_level(Keys.USER_LEVEL_FX);
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

	public ActionForward unlock(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JSONObject data = new JSONObject();
		String ret = "0";
		String msg = "该用户名没有被锁，不用进行解锁操作！";
		DynaBean dynaBean = (DynaBean) form;
		String user_id = (String) dynaBean.get("user_id");
		if (StringUtils.isNotBlank(user_id)) {
			SysOperLog sysOperLogQuery = new SysOperLog();
			sysOperLogQuery.setLink_id(Integer.valueOf(user_id));
			sysOperLogQuery.setOper_type(Keys.SysOperType.SysOperType_30.getIndex());
			sysOperLogQuery = super.getFacade().getSysOperLogService().getSysOperLog(sysOperLogQuery);
			if (null != sysOperLogQuery) {
				if (sysOperLogQuery.getPre_number().intValue() >= Keys.LOGIN_ERROR_TIME) {
					SysOperLog sysOperLogUpdate = new SysOperLog();
					sysOperLogUpdate.setId(sysOperLogQuery.getId());
					sysOperLogUpdate.setPre_number(0);
					super.getFacade().getSysOperLogService().modifySysOperLog(sysOperLogUpdate);
					msg = "恭喜你，解锁成功!";
					ret = "1";
				}
			}
		}
		data.put("ret", ret);
		data.put("msg", msg);
		super.renderJson(response, data.toJSONString());
		return null;
	}

	public ActionForward updateLogin(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JSONObject data = new JSONObject();
		String ret = "";
		String msg = "";

		DynaBean dynaBean = (DynaBean) form;
		String user_id = (String) dynaBean.get("user_id");
		UserInfo ui = super.getUserInfo(Integer.valueOf(user_id));
		if (null == ui) {
			ret = "-1";
			msg = "您还未登录，请先登录系统！";
			data.put("ret", ret);
			data.put("msg", msg);
			super.renderJson(response, data.toJSONString());
			return null;
		}
		if (StringUtils.isBlank(user_id)) {
			ret = "0";
			msg = "参数错误!";
			data.put("ret", ret);
			data.put("msg", msg);
			super.renderJson(response, data.toJSONString());
			return null;
		}

		UserInfo uInfo = super.getUserInfo(Integer.valueOf(user_id));
		if (uInfo == null) {
			ret = "-2";
			msg = "该用户不存在或已删除！";
			data.put("ret", ret);
			data.put("msg", msg);
			super.renderJson(response, data.toJSONString());
			return null;
		}
		uInfo.setLast_login_time(new Date());
		int flag = super.getFacade().getUserInfoService().modifyUserInfo(uInfo);
		ret = "1";
		if (Integer.valueOf(flag) > 0) {
			msg = "该用户最后登陆时间更新成功！";
		} else {
			msg = "该用户最后登陆时间更新失败！";
		}
		data.put("ret", ret);
		data.put("msg", msg);
		super.renderJson(response, data.toJSONString());
		return null;
	}

	public ActionForward villageInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (GenericValidator.isLong(id)) {
			VillageInfo entity = new VillageInfo();
			entity.setId(new Integer(id));
			entity.getMap().put("is_virtual_no_def", "true");
			entity = getFacade().getVillageInfoService().getVillageInfo(entity);
			if (null == entity) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");

			}
			if (entity.getP_index() != null) {
				BaseProvince baseProvince = new BaseProvince();
				baseProvince.setP_index(entity.getP_index());
				baseProvince = getFacade().getBaseProvinceService().getBaseProvince(baseProvince);
				if (null != baseProvince) {
					request.setAttribute("full_name", baseProvince.getFull_name());
				}
			}
			// the line below is added for pagination
			entity.setQueryString(super.serialize(request, "id", "method"));
			// end
			super.copyProperties(form, entity);
			return new ActionForward("/../manager/admin/UserInfo/village.jsp");
		} else {
			this.saveError(request, "errors.Integer", new String[] { id });
			return mapping.findForward("list");
		}
	}

	public ActionForward updateYmId(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (GenericValidator.isLong(id)) {

			UserInfo userInfo = super.getUserInfo(Integer.valueOf(id));
			request.setAttribute("userInfo", userInfo);
			request.setAttribute("user_name_par", userInfo.getUser_name());

			String ymid = userInfo.getYmid();
			if (StringUtils.isNotBlank(ymid)) {
				UserInfo uipar = new UserInfo();
				uipar.getMap().put("ym_id", ymid);
				uipar.setIs_del(0);
				uipar = getFacade().getUserInfoService().getUserInfo(uipar);
				request.setAttribute("uipar", uipar);
			}

			return new ActionForward("/../manager/admin/UserInfo/updateYmId.jsp");
		} else {
			this.saveError(request, "errors.Integer", new String[] { id });
			return mapping.findForward("list");
		}
	}

	public ActionForward saveUpdateYmId(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JSONObject data = new JSONObject();
		String ret = "";
		String msg = "";

		DynaBean dynaBean = (DynaBean) form;
		String user_id = (String) dynaBean.get("user_id");
		String userParId = (String) dynaBean.get("userParId");
		UserInfo ui = super.getUserInfo(Integer.valueOf(user_id));
		if (null == ui) {
			ret = "-1";
			msg = "您还未登录，请先登录系统！";
			data.put("ret", ret);
			data.put("msg", msg);
			super.renderJson(response, data.toJSONString());
			return null;
		}
		if (StringUtils.isBlank(user_id) || StringUtils.isBlank(userParId)) {
			ret = "0";
			msg = "参数错误!";
			data.put("ret", ret);
			data.put("msg", msg);
			super.renderJson(response, data.toJSONString());
			return null;
		}
		if (Integer.valueOf(user_id).intValue() == Integer.valueOf(userParId).intValue()) {
			ret = "0";
			msg = "上级不能选择自己!";
			data.put("ret", ret);
			data.put("msg", msg);
			super.renderJson(response, data.toJSONString());
			return null;
		}

		UserInfo uInfo = super.getUserInfo(Integer.valueOf(user_id));
		if (uInfo == null) {
			ret = "-2";
			msg = "该用户不存在或已删除！";
			data.put("ret", ret);
			data.put("msg", msg);
			super.renderJson(response, data.toJSONString());
			return null;
		}
		UserInfo userParInfo = super.getUserInfo(Integer.valueOf(userParId));
		if (userParInfo == null) {
			ret = "-2";
			msg = "上级用户不存在或已删除！";
			data.put("ret", ret);
			data.put("msg", msg);
			super.renderJson(response, data.toJSONString());
			return null;
		}

		// 如果发现选择的用户时该用户的下级，需要提示错误
		Boolean isSon = false;
		UserRelation userRelation = new UserRelation();
		userRelation.setUser_id(uInfo.getId());
		List<UserRelation> sonList = super.getFacade().getUserRelationService().getUserRelationSonList(userRelation);
		if (null != sonList && sonList.size() > 0) {
			for (UserRelation temp : sonList) {
				if (temp.getUser_id().intValue() == userParInfo.getId().intValue()) {
					isSon = true;
					break;
				}
			}
		}
		if (isSon) {
			ret = "-2";
			msg = "不能选择自己的下级用户作为上级！";
			data.put("ret", ret);
			data.put("msg", msg);
			super.renderJson(response, data.toJSONString());
			return null;
		}

		// 这个地方需要变更用户的上级
		UserInfo userInfoUpdate = new UserInfo();
		userInfoUpdate.setId(uInfo.getId());
		userInfoUpdate.setYmid(userParInfo.getUser_name());
		userInfoUpdate.getMap().put("update_user_relation", "true");
		super.getFacade().getUserInfoService().modifyUserInfo(userInfoUpdate);

		ret = "1";
		msg = "操作成功！";
		data.put("ret", ret);
		data.put("msg", msg);
		super.renderJson(response, data.toJSONString());
		return null;
	}

	public ActionForward addDomainSite(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		if (StringUtils.isBlank(id)) {
			String msg = "参数有误，请联系管理员！";
			super.showMsgForCustomer(request, response, msg);
			return null;
		}
		UserInfo userInfo = new UserInfo();
		userInfo.setId(Integer.valueOf(id));
		userInfo.setUser_type(Keys.UserType.USER_TYPE_19.getIndex());
		userInfo.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		userInfo = super.getFacade().getUserInfoService().getUserInfo(userInfo);
		if (null == userInfo) {
			String msg = "未查询到该市级管理员！";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		request.setAttribute("userInfo", userInfo);
		return new ActionForward("/../manager/admin/UserInfo/addDomainSite.jsp");
	}

	public ActionForward saveDomainSite(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String domain_site = (String) dynaBean.get("domain_site");

		JSONObject data = new JSONObject();
		String ret = "0";
		String msg = "";

		if (StringUtils.isBlank(id)) {
			msg = "参数有误";
			data.put("ret", ret);
			data.put("msg", msg);
			super.renderJson(response, data.toJSONString());
			return null;
		}

		UserInfo userInfo = new UserInfo();
		userInfo.setId(Integer.valueOf(id));
		userInfo.setUser_type(Keys.UserType.USER_TYPE_19.getIndex());
		userInfo.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		userInfo = super.getFacade().getUserInfoService().getUserInfo(userInfo);
		if (null == userInfo) {
			msg = "该市级管理员不存在！";
			data.put("ret", ret);
			data.put("msg", msg);
			super.renderJson(response, data.toJSONString());
			return null;
		}
		if (StringUtils.isBlank(domain_site)) {
			msg = "二级域名不能为空";
			data.put("ret", ret);
			data.put("msg", msg);
			super.renderJson(response, data.toJSONString());
			return null;
		}
		UserInfo userInfoSelect = new UserInfo();
		userInfoSelect.setId(Integer.valueOf(id));
		userInfoSelect.setDomain_site(domain_site);
		logger.info("===domain_site===" + domain_site);
		logger.info("===domain_site===" + id);
		int count = getFacade().getUserInfoService().getUserInfoCount(userInfoSelect);
		if (count > 0) {
			msg = "二级域名重复，请核对后再操作！";
			data.put("ret", ret);
			data.put("msg", msg);
			super.renderJson(response, data.toJSONString());
			return null;
		} else {
			super.getFacade().getUserInfoService().modifyUserInfo(userInfoSelect);
		}

		msg = "修改成功";
		ret = "1";
		data.put("ret", ret);
		data.put("msg", msg);
		super.renderJson(response, data.toJSONString());
		return null;

	}

	public ActionForward delDomainSite(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		JSONObject data = new JSONObject();
		String ret = "0";
		String msg = "";
		if (StringUtils.isBlank(id)) {
			msg = "参数有误";
			data.put("ret", ret);
			data.put("msg", msg);
			super.renderJson(response, data.toJSONString());
			return null;
		}

		UserInfo userInfo = new UserInfo();
		userInfo.setId(Integer.valueOf(id));
		userInfo.setUser_type(Keys.UserType.USER_TYPE_19.getIndex());
		userInfo.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		userInfo = super.getFacade().getUserInfoService().getUserInfo(userInfo);
		if (null == userInfo) {
			msg = "市管理员不存在！";
			data.put("ret", ret);
			data.put("msg", msg);
			super.renderJson(response, data.toJSONString());
			return null;
		}
		UserInfo userInfoSelect = new UserInfo();
		userInfoSelect.setId(Integer.valueOf(id));
		userInfoSelect.setDomain_site("");
		super.getFacade().getUserInfoService().modifyUserInfo(userInfoSelect);

		msg = "取消成功";
		ret = "1";
		data.put("ret", ret);
		data.put("msg", msg);
		super.renderJson(response, data.toJSONString());
		return null;

	}

	public ActionForward getUser(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;

		UserInfo userInfo = super.getUserInfo(1);

		super.copyProperties(form, userInfo);

		return new ActionForward("/../manager/admin/UserInfo/update_bank.jsp");
	}

	public ActionForward updateBank(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;

		JSONObject jsonObject = new JSONObject();
		String ret = "0", msg = "";

		UserInfo ui = super.getUserInfoFromSession(request);

		UserInfo entity = new UserInfo();
		super.copyProperties(entity, form);
		entity.setId(1);

		if (null == entity.getBank_name() || null == entity.getBank_account() || null == entity.getBank_account_name()) {
			msg = "参数有误！";
			jsonObject.put("ret", ret);
			jsonObject.put("msg", msg);
			super.renderJson(response, jsonObject.toString());
			return null;
		}

		super.getFacade().getUserInfoService().modifyUserInfo(entity);

		ret = "1";
		msg = "修改成功!";
		jsonObject.put("ret", ret);
		jsonObject.put("msg", msg);
		super.renderJson(response, jsonObject.toString());
		return null;
	}

	/**
	 * 修改贫困户列表
	 */
	public ActionForward pinkunhu(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (null == super.checkUserModPopeDom(form, request, "0")) {
			return super.checkPopedomInvalid(request, response);
		}

		setNaviStringToRequestScope(request);
		super.setBaseDataListToSession(10, request);// 用户类型

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String is_del = (String) dynaBean.get("is_del");
		String user_name_like = (String) dynaBean.get("user_name_like");

		String real_name_like = (String) dynaBean.get("real_name_like");
		String own_entp_name_like = (String) dynaBean.get("own_entp_name_like");
		String st_add_date = (String) dynaBean.get("st_add_date");
		String en_add_date = (String) dynaBean.get("en_add_date");
		String province = (String) dynaBean.get("province");
		String city = (String) dynaBean.get("city");
		String country = (String) dynaBean.get("country");
		String par_user_name_like = (String) dynaBean.get("par_user_name_like");

		UserInfo entity = new UserInfo();
		super.copyProperties(entity, form);
		entity.setIs_poor(1);

		entity.getMap().put("user_name_like", user_name_like);
		entity.getMap().put("real_name_like", real_name_like);
		entity.getMap().put("own_entp_name_like", own_entp_name_like);
		if (null == is_del) {
			entity.setIs_del(0);
			dynaBean.set("is_del", "0");
		}
		entity.getMap().put("st_add_date", st_add_date);
		entity.getMap().put("en_add_date", en_add_date);

		if (StringUtils.isNotBlank(country)) {
			entity.getMap().put("p_index_like", country);
		} else if (StringUtils.isNotBlank(city)) {
			entity.getMap().put("p_index_like", city.substring(0, 4));
		} else if (StringUtils.isNotBlank(province)) {
			entity.getMap().put("p_index_like", province.substring(0, 2));
		}

		if (StringUtils.isNotEmpty(par_user_name_like)) {
			entity.setYmid(par_user_name_like);
		}
		// 不查询内部员工的用户
		entity.getMap().put("user_type_ne", Keys.UserType.USER_TYPE_100.getIndex());

		Integer recordCount = getFacade().getUserInfoService().getUserInfoCount(entity);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<UserInfo> list = getFacade().getUserInfoService().getUserInfoPaginatedList(entity);

		request.setAttribute("entityList", list);
		request.setAttribute("init_pwd", Keys.INIT_PWD);

		// return mapping.findForward("list");

		return new ActionForward("/../manager/admin/UserInfo/pinkunhu.jsp");
	}

	public ActionForward editPinkunjin(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "2")) {
			return super.checkPopedomInvalid(request, response);
		}
		setNaviStringToRequestScope(request);
		super.setBaseDataListToSession(Keys.BaseDataType.Base_Data_type_10.getIndex(), request);// 用户类型
		super.setBaseDataListToSession(Keys.BaseDataType.Base_Data_type_200.getIndex(), request);// 会员等级
		saveToken(request);
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		UserInfo entity = getFacade().getUserInfoService().getUserInfo(new UserInfo(new Integer(id)));
		entity.setQueryString(super.serialize(request, "id", "method"));
		super.copyProperties(form, entity);
		if (null != entity.getP_index()) {
			setprovinceAndcityAndcountryToFrom(dynaBean, Integer.valueOf(entity.getP_index()));
		}

		return new ActionForward("/../manager/admin/UserInfo/pinkunhu_edit.jsp");
	}

	public ActionForward savePinkunjin(ActionMapping mapping, ActionForm form, HttpServletRequest request,
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
		String id = (String) dynaBean.get("id");
		String mod_id = (String) dynaBean.get("mod_id");
		String update_bi_aid = (String) dynaBean.get("update_bi_aid");// 修改用户贫困金
		UserInfo entity = new UserInfo();
		super.copyProperties(entity, form);

		if (StringUtils.isNotBlank(update_bi_aid)) {
			entity.getMap().put("update_bi_aid", update_bi_aid);
		}

		UserInfo userInfo = new UserInfo();

		resetToken(request);// 全部的判断完后在resetToken

		userInfo = getFacade().getUserInfoService().getUserInfo(new UserInfo(entity.getId()));
		if (null == userInfo) {
			saveError(request, "entity.missing");
		} else {
			entity.setUpdate_date(new Date());
			entity.setUpdate_user_id(sessionUi.getId());
			int i = getFacade().getUserInfoService().modifyUserInfo(entity);
			if (i < 1) {
				String msg = "修改失败";
				switch (i) {
				case -1:
					msg = "用户不存在!";
					break;
				case -10:
					msg = "修改失败，扶贫金不足!";
					break;
				default:
					break;
				}

				return returnJsMsg(response, msg);
			}
			saveMessage(request, "entity.updated");
		}
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append("/admin/UserInfo.do?method=pinkunhu");
		pathBuffer.append("&mod_id=").append(mod_id);
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		return forward;
	}
}