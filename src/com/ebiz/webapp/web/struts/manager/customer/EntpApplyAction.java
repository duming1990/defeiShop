package com.ebiz.webapp.web.struts.manager.customer;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.alibaba.fastjson.JSONObject;
import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.EntpContent;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.NewsInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.DESPlus;

/**
 * @author Qi,NengFei
 * @version 2017-11-13
 */
public class EntpApplyAction extends BaseCustomerAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfoFromSession(request);

		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);
		dynaBean.set("order_value", "0");
		dynaBean.set("province", Keys.P_INDEX_INIT);
		dynaBean.set("p_index_pro", Keys.P_INDEX_INIT);

		dynaBean.set("entp_tel", ui.getMobile());

		EntpInfo entity = new EntpInfo();
		entity.setAdd_user_id(ui.getId());
		entity.setIs_del(0);
		entity = getFacade().getEntpInfoService().getEntpInfo(entity);

		super.copyProperties(form, entity);
		if (entity != null) {

			EntpContent entpContent = new EntpContent();
			entpContent.setType(0);
			entpContent.setEntp_id(entity.getId());
			entpContent = super.getFacade().getEntpContentService().getEntpContent(entpContent);
			if (null != entpContent) {
				dynaBean.set("entp_content", entpContent.getContent());
			}
			setprovinceAndcityAndcountryToFrom(dynaBean, Integer.valueOf(entity.getP_index()));
		}

		return mapping.findForward("input");
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return super.showMsgForCustomer(request, response, msg);
		}

		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);

		String entp_name_like = (String) dynaBean.get("entp_name_like");
		String audit_state = (String) dynaBean.get("audit_state");
		String entp_no = (String) dynaBean.get("entp_no");
		String is_del = (String) dynaBean.get("is_del");
		String today_date = (String) dynaBean.get("today_date");

		Pager pager = (Pager) dynaBean.get("pager");
		EntpInfo entpInfo = new EntpInfo();
		super.copyProperties(entpInfo, form);
		entpInfo.setAdd_user_id(ui.getId());
		entpInfo.getMap().put("entp_name_like", entp_name_like);
		entpInfo.getMap().put("audit_state", audit_state);
		entpInfo.getMap().put("entp_no", entp_no);

		entpInfo.getMap().put("today_date", today_date);

		if (StringUtils.isNotBlank(is_del)) {
			if ("-1".equals(is_del)) {
				entpInfo.setIs_del(null);
			}
			if ("0".equals(is_del)) {
				entpInfo.setIs_del(0);
			}
			if ("1".equals(is_del)) {
				entpInfo.setIs_del(1);
			}
		} else {
			entpInfo.setIs_del(0);
			dynaBean.set("is_del", "0");
		}

		Integer recordCount = getFacade().getEntpInfoService().getEntpInfoCount(entpInfo);
		pager.init(recordCount.longValue(), 10, pager.getRequestPage());
		entpInfo.getRow().setFirst(pager.getFirstRow());
		entpInfo.getRow().setCount(pager.getRowCount());
		List<EntpInfo> entpInfoList = getFacade().getEntpInfoService().getEntpInfoList(entpInfo);
		request.setAttribute("entityList", entpInfoList);

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

		UserInfo userInfo = super.getUserInfoFromSession(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String is_audit = (String) dynaBean.get("is_audit");
		String mod_id = (String) dynaBean.get("mod_id");
		String par_id = (String) dynaBean.get("par_id");

		String entp_content = (String) dynaBean.get("entp_content");

		EntpInfo entity = new EntpInfo();
		super.copyProperties(entity, form);
		entity.getMap().put("update_entp_content", "true");
		entity.getMap().put("entp_content", entp_content);

		if (!GenericValidator.isLong(id)) {
			entity.setIs_del(0);
			entity.setUuid(UUID.randomUUID().toString());
			entity.setAdd_date(new Date());
			entity.setAdd_user_id(userInfo.getId());
			entity.setAdd_user_name(userInfo.getUser_name());
			entity.setEntp_type(10);

			super.getFacade().getEntpInfoService().createEntpInfo(entity);
			saveMessage(request, "entity.inerted");
		} else {

			entity.setUpdate_date(new Date());
			entity.setUpdate_user_id(userInfo.getId());
			entity.getMap().put("user_id", userInfo.getId());
			super.getFacade().getEntpInfoService().modifyEntpInfo(entity);
			if (StringUtils.isNotBlank(is_audit))
				saveMessage(request, "entity.audit");
			else
				saveMessage(request, "entity.entp.apply");
		}

		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&par_id=" + par_id);
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		saveToken(request);
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		getsonSysModuleList(request, dynaBean);

		if (GenericValidator.isLong(id)) {
			EntpInfo entity = new EntpInfo();
			entity.setId(new Integer(id));
			entity = getFacade().getEntpInfoService().getEntpInfo(entity);

			// the line below is added for pagination
			entity.setQueryString(super.serialize(request, "id", "method"));
			// end
			super.copyProperties(form, entity);

			EntpContent entpContent = new EntpContent();
			entpContent.setType(0);
			entpContent.setEntp_id(new Integer(id));
			entpContent = super.getFacade().getEntpContentService().getEntpContent(entpContent);
			if (null != entpContent) {
				dynaBean.set("entp_content", entpContent.getContent());
			}

			setprovinceAndcityAndcountryToFrom(dynaBean, Integer.valueOf(entity.getP_index()));

		}
		return mapping.findForward("input");
	}

	public ActionForward getEntpApplyInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 商家入驻电子协议
		NewsInfo newsInfo = new NewsInfo();
		newsInfo.setIs_del(0);
		newsInfo.setMod_id(Keys.Merchant_entry_MOD_ID);
		newsInfo = super.getFacade().getNewsInfoService().getNewsInfo(newsInfo);

		request.setAttribute("newsInfo", newsInfo);
		return new ActionForward("/../manager/customer/EntpApply/info.jsp");
	}

	public ActionForward editContent(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (StringUtils.isBlank(id)) {
			String msg = "参数有误!";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		EntpInfo entpInfo = new EntpInfo();
		entpInfo.setId(Integer.valueOf(id));
		entpInfo.setIs_del(0);
		entpInfo = super.getFacade().getEntpInfoService().getEntpInfo(entpInfo);
		if (null != entpInfo) {
			EntpContent entpContent = new EntpContent();
			entpContent.setType(0);
			entpContent.setEntp_id(new Integer(id));
			entpContent = super.getFacade().getEntpContentService().getEntpContent(entpContent);
			if (null != entpContent) {
				dynaBean.set("entp_content", entpContent.getContent());
			}
		}
		return new ActionForward("/../manager/customer/EntpApply/editContent.jsp");
	}

	public ActionForward saveContent(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String entp_id = (String) dynaBean.get("entp_id");
		String entp_content = (String) dynaBean.get("entp_content");

		JSONObject data = new JSONObject();
		String ret = "0";
		String msg = "";

		if (StringUtils.isBlank(entp_id) || StringUtils.isBlank(entp_content)) {
			msg = "参数有误";
			data.put("ret", ret);
			data.put("msg", msg);
			super.renderJson(response, data.toJSONString());
			return null;
		}

		EntpInfo entpInfo = new EntpInfo();
		entpInfo.setId(Integer.valueOf(entp_id));
		entpInfo = super.getFacade().getEntpInfoService().getEntpInfo(entpInfo);
		if (null == entpInfo) {
			msg = "企业不存在！";
			data.put("ret", ret);
			data.put("msg", msg);
			super.renderJson(response, data.toJSONString());
			return null;
		}
		EntpContent entityQuyery = new EntpContent();
		entityQuyery.setEntp_id(Integer.valueOf(entp_id));
		entityQuyery = super.getFacade().getEntpContentService().getEntpContent(entityQuyery);

		EntpContent entity = new EntpContent();
		super.copyProperties(entity, form);
		if (null == entityQuyery) {// 增加
			entity.setContent(entp_content);
			super.getFacade().getEntpContentService().createEntpContent(entity);
		} else {// 修改
			entity.setId(entityQuyery.getId());
			entity.setContent(entp_content);
			super.getFacade().getEntpContentService().modifyEntpContent(entity);
		}

		msg = "修改成功";
		ret = "1";
		data.put("ret", ret);
		data.put("msg", msg);
		super.renderJson(response, data.toJSONString());
		return null;

	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfoFromSession(request);

		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);

		UserInfo userInfoTemp = super.getUserInfo(ui.getId());

		String id = (String) dynaBean.get("id");
		if (StringUtils.isBlank(id)) {
			id = userInfoTemp.getOwn_entp_id().toString();
		}

		request.setAttribute("userInfoTemp", userInfoTemp);

		EntpInfo entity = new EntpInfo();
		entity.setId(new Integer(id));
		entity = getFacade().getEntpInfoService().getEntpInfo(entity);
		if (null == entity) {
			String msg = "您还没有申请，点击右上角我要开店!";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		if (null != entity.getAdd_user_id()) {
			UserInfo userInfo = new UserInfo();
			userInfo.setId(entity.getAdd_user_id());
			userInfo.setIs_del(0);
			List<UserInfo> userInfoList = super.getFacade().getUserInfoService().getUserInfoList(userInfo);
			request.setAttribute("userInfoList", userInfoList);
			request.setAttribute("list_size", userInfoList.size());

			UserInfo entpUser = new UserInfo();
			if (userInfoList.size() > 0) {
				entpUser = userInfoList.get(0);
			}// 取性别用的
			request.setAttribute("entpUser", entpUser);
		}

		request.setAttribute("shopTypes", Keys.ShopType.values());

		// the line below is added for pagination
		entity.setQueryString(super.serialize(request, "id", "method"));
		// end
		super.copyProperties(form, entity);

		return mapping.findForward("view");
	}

	public ActionForward addUser(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		saveToken(request);
		DynaBean dynaBean = (DynaBean) form;
		String own_entp_id = (String) dynaBean.get("own_entp_id");

		if (StringUtils.isBlank(own_entp_id) || !GenericValidator.isInt(own_entp_id)) {
			String msg = "参数不正确！";
			return super.showMsgForCustomer(request, response, msg);
		}

		EntpInfo entpInfo = super.getEntpInfo(Integer.valueOf(own_entp_id));

		if (null == entpInfo) {
			String msg = "未查询到相关企业！";
			return super.showMsgForCustomer(request, response, msg);
		}

		UserInfo userInfo = super.getUserInfoWithEntpId(Integer.valueOf(own_entp_id));

		if (null == userInfo) {
			request.setAttribute("entpInfo", entpInfo);
			return new ActionForward("/../manager/customer/EntpApply/addUser.jsp");
		} else {
			request.setAttribute("init_pwd", Keys.INIT_PWD);
			request.setAttribute("userInfo", userInfo);
			return new ActionForward("/../manager/customer/EntpApply/userInfo.jsp");
		}
	}

	public ActionForward saveEntpLinkUserInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfoFromSession(request);

		JSONObject datas = new JSONObject();
		String code = "0", msg = "";

		DynaBean dynaBean = (DynaBean) form;

		String password = (String) dynaBean.get("password");

		UserInfo entity = new UserInfo();
		super.copyProperties(entity, form);
		entity.setMobile(entity.getUser_name());
		EntpInfo entpInfo = super.getEntpInfo(entity.getOwn_entp_id());

		if (null == entpInfo) {
			msg = "未查询到关联企业信息";
			super.ajaxReturnInfo(response, code, msg, datas);
			return null;
		}

		entity.setUser_type(Keys.UserType.USER_TYPE_2.getIndex());
		entity.setIs_entp(1);
		entity.setOwn_entp_name(entpInfo.getEntp_name());
		entity.setIs_del(0);
		DESPlus des = new DESPlus();
		entity.setPassword(des.encrypt(password));
		entity.setIs_active(1);// 已激活
		entity.setAdd_date(new Date());
		entity.setAdd_user_id(ui.getId());
		getFacade().getUserInfoService().createUserInfo(entity);

		code = "1";
		super.ajaxReturnInfo(response, code, msg, datas);
		return null;
	}

	public ActionForward updateOnlineShopState(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JSONObject datas = new JSONObject();
		String code = "0", msg = "";

		DynaBean dynaBean = (DynaBean) form;

		String id = (String) dynaBean.get("id");
		String is_has_open_online_shop = (String) dynaBean.get("is_has_open_online_shop");

		EntpInfo entpInfo = new EntpInfo();
		entpInfo.setIs_del(0);
		entpInfo.setId(Integer.valueOf(id));
		entpInfo = getFacade().getEntpInfoService().getEntpInfo(entpInfo);
		if (null == entpInfo) {
			msg = "未查询到关联企业信息";
			super.ajaxReturnInfo(response, code, msg, datas);
			return null;
		}

		EntpInfo entpUpdate = new EntpInfo();
		entpUpdate.setId(Integer.valueOf(id));
		entpUpdate.setIs_has_open_online_shop(Integer.valueOf(is_has_open_online_shop));
		getFacade().getEntpInfoService().modifyEntpInfo(entpUpdate);

		code = "1";
		super.ajaxReturnInfo(response, code, msg, datas);
		return null;
	}

	public ActionForward initPassword(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String uid = request.getParameter("uid");
		String password = request.getParameter("password");

		UserInfo entity = new UserInfo();
		entity.setId(new Integer(uid));
		DESPlus des = new DESPlus();
		entity.setPassword(des.encrypt(password));
		entity.setIs_has_update_pass(1);
		JSONObject result = new JSONObject();
		int rows = super.getFacade().getUserInfoService().modifyUserInfo(entity);
		String msg = getMessage(request, "password.updated.success");

		result.put("result", rows);
		result.put("msg", msg);

		super.render(response, result.toString(), "text/x-json;charset=UTF-8");
		return null;
	}

	public ActionForward entpKefu(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (ui == null) {
			String msg = "您还未登录，请先登录系统！";
			return super.showMsgForCustomer(request, response, msg);
		}

		EntpInfo entp = new EntpInfo();
		entp.setId(ui.getOwn_entp_id());
		entp = getFacade().getEntpInfoService().getEntpInfo(entp);
		super.copyProperties(form, entp);

		return new ActionForward("/../manager/customer/EntpApply/updateEntpKefu.jsp");
	}

	public ActionForward saveEntpKefu(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String kefu_tel = (String) dynaBean.get("kefu_tel");
		String kefu_qr_code = (String) dynaBean.get("kefu_qr_code");
		log.info("===kefu_tel:" + kefu_tel);
		log.info("===kefu_qr_code:" + kefu_qr_code);

		UserInfo ui = super.getUserInfoFromSession(request);
		if (ui == null) {
			String msg = "您还未登录，请先登录系统！";
			return super.showMsgForCustomer(request, response, msg);
		}

		EntpInfo entp = new EntpInfo();
		super.copyProperties(entp, form);
		entp.setId(ui.getOwn_entp_id());
		entp.setKefu_qr_code(kefu_qr_code);
		entp.setKefu_tel(kefu_tel);
		getFacade().getEntpInfoService().modifyEntpInfo(entp);

		return new ActionForward("/../manager/customer/EntpApply/updateEntpKefu.jsp");
	}

}
