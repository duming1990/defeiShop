package com.ebiz.webapp.web.struts;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.util.CookieGenerator;
import org.springframework.web.util.WebUtils;

import com.aiisen.weixin.CommonApi;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.BaseAttributeSon;
import com.ebiz.webapp.domain.BaseBrandInfo;
import com.ebiz.webapp.domain.BaseComminfoTags;
import com.ebiz.webapp.domain.BaseData;
import com.ebiz.webapp.domain.BaseProvince;
import com.ebiz.webapp.domain.CartInfo;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.CommInfoPoors;
import com.ebiz.webapp.domain.CommTczhAttribute;
import com.ebiz.webapp.domain.CommTczhPrice;
import com.ebiz.webapp.domain.CommentInfo;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.Freight;
import com.ebiz.webapp.domain.HelpModule;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.OrderMergerInfo;
import com.ebiz.webapp.domain.PoorInfo;
import com.ebiz.webapp.domain.ServiceCenterInfo;
import com.ebiz.webapp.domain.ShippingAddress;
import com.ebiz.webapp.domain.SysModule;
import com.ebiz.webapp.domain.UserBiRecord;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.VillageInfo;
import com.ebiz.webapp.domain.WlCompInfo;
import com.ebiz.webapp.util.GetApiUtils;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.IdGen;

/**
 * @author Wu,Yang
 * @version 2011-04-27
 */
public class BaseCsAjaxAction extends BaseWebAction {

	public ActionForward saveCommentInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommentInfo entity = new CommentInfo();
		super.copyProperties(entity, form);
		entity.setComm_ip(this.getIpAddr(request));
		entity.setComm_time(new Date());
		entity.setComm_state(0);// 未发布
		entity.setOrder_value(0);
		Integer count = getFacade().getCommentInfoService().createCommentInfo(entity);
		StringBuffer result = new StringBuffer();
		result.append("{\"result\":");
		if (null != count) {
			result.append("true");
		} else {
			result.append("false");
		}
		result.append("}");
		super.renderJson(response, result.toString());
		return null;
	}

	public ActionForward saveCartInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		CartInfo entity = new CartInfo();
		CartInfo cartInfo = new CartInfo();
		super.copyProperties(entity, form);

		UserInfo ui = super.getUserInfoFromSession(request);
		StringBuffer result = new StringBuffer();
		result.append("{\"result\":");
		Integer cart_id = 0;
		if (null == ui) {
			result.append("false");
			result.append("}");
			super.renderJson(response, result.toString());
			return null;
		}
		CommInfo commInfo = new CommInfo();
		commInfo.setId(entity.getComm_id());
		commInfo = super.getFacade().getCommInfoService().getCommInfo(commInfo);
		if ((commInfo == null) || (commInfo.getIs_del().intValue() != 0) || (commInfo.getAudit_state().intValue() != 1)
				|| (commInfo.getIs_sell().intValue() == 0)
				|| ((commInfo.getIs_sell().intValue() == 1) && (commInfo.getDown_date().compareTo(new Date()) < 0))) {
			result.append("false");
			result.append(",\"tip\":\"商品已下架\"}");
			super.renderJson(response, result.toString());
			return null;
		}
		// 去掉此次查询delever_p_index和pd_count条件
		Integer delever_p_index = entity.getDelivery_p_index();
		Integer pd_count = entity.getPd_count();
		entity.setPd_count(null);
		entity.setDelivery_p_index(null);
		entity.setUser_id(ui.getId());
		entity.setIs_del(0);
		CartInfo cartInfoTemp = getFacade().getCartInfoService().getCartInfo(entity);
		entity.setDelivery_p_index(delever_p_index);
		entity.setPd_count(pd_count);
		BigDecimal service_single_money = new BigDecimal("0");

		// 获取套餐各子属性的名称,如"8G 电信 黑色"
		StringBuffer comm_tczh_name = new StringBuffer("");
		CommTczhAttribute cta = new CommTczhAttribute();
		if (entity.getComm_tczh_id() != null) {
			cta.setComm_tczh_id(entity.getComm_tczh_id());
			cta.getMap().put("orderby_order_value_asc", "true");
			List<CommTczhAttribute> CommTczhAttributeList = super.getFacade().getCommTczhAttributeService()
					.getCommTczhAttributeList(cta);
			for (CommTczhAttribute temp_cta : CommTczhAttributeList) {
				BaseAttributeSon baseAttributeSon = new BaseAttributeSon();
				baseAttributeSon.setId(Integer.valueOf(temp_cta.getAttr_id()));
				BaseAttributeSon bas = super.getFacade().getBaseAttributeSonService()
						.getBaseAttributeSon(baseAttributeSon);
				comm_tczh_name.append(bas.getAttr_name() + " ");
			}
		}
		entity.setComm_tczh_name(comm_tczh_name.toString());
		cartInfo.setComm_tczh_name(comm_tczh_name.toString());

		entity.getMap().put("with_detail", "true");
		cartInfo.getMap().put("with_detail", "true");
		cartInfo.setService_single_money(service_single_money);
		entity.setService_single_money(service_single_money);
		cartInfo.setService_single_money(service_single_money);

		entity.setGn_type(commInfo.getGn_type());
		cartInfo.setGn_type(commInfo.getGn_type());

		if (null != cartInfoTemp) {// 更新
			cartInfo.setId(cartInfoTemp.getId());
			cartInfo.setPd_count(cartInfoTemp.getPd_count() + (entity.getPd_count() == null ? 0 : entity.getPd_count()));
			getFacade().getCartInfoService().modifyCartInfo(cartInfo);
			cart_id = cartInfoTemp.getId();
		} else {// 插入
			entity.setPd_count(entity.getPd_count() == null ? 0 : entity.getPd_count());
			entity.setUser_name(ui.getUser_name());
			if (null != entity.getEntp_id()) {
				EntpInfo entpInfo = new EntpInfo();
				entpInfo.setId(entity.getEntp_id());
				entpInfo.setIs_del(0);
				entpInfo = getFacade().getEntpInfoService().getEntpInfo(entpInfo);
				if (null != entpInfo) {
					entity.setEntp_name(entpInfo.getEntp_name());
				}
			}
			cart_id = getFacade().getCartInfoService().createCartInfo(entity);
		}

		if (null != cart_id) {
			result.append(cart_id);
		} else {
			result.append("false");
		}

		result.append("}");
		super.renderJson(response, result.toString());
		return null;

	}

	public ActionForward delCart(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String cart_id = (String) dynaBean.get("cart_id");
		String isClearAll = (String) dynaBean.get("isClearAll");
		UserInfo ui = super.getUserInfoFromSession(request);
		StringBuffer result = new StringBuffer();
		result.append("{\"result\":");
		if (null == ui) {
			result.append("false").append("}");
			super.renderJson(response, result.toString());
			return null;
		}
		if (StringUtils.isNotBlank(isClearAll)) {
			CartInfo entity = new CartInfo();
			entity.setUser_id(ui.getId());
			getFacade().getCartInfoService().removeCartInfo(entity);
			result.append("true").append("}");
			super.renderJson(response, result.toString());
			return null;
		}
		if (StringUtils.isBlank(cart_id)) {
			result.append("false");
		} else {
			CartInfo entity = new CartInfo();
			entity.setId(Integer.valueOf(cart_id));
			getFacade().getCartInfoService().removeCartInfo(entity);
			result.append("true");
			result.append(",\"cartCount\":");
			CartInfo cartInfo2 = new CartInfo();
			cartInfo2.setUser_id(ui.getId());
			List<CartInfo> CartInfoList = super.getFacade().getCartInfoService().getCartInfoList(cartInfo2);
			Integer count = 0;
			for (CartInfo cartinfo_ : CartInfoList) {
				count += cartinfo_.getPd_count();
			}
			result.append(count);
		}
		result.append("}");
		super.renderJson(response, result.toString());
		return null;

	}

	public ActionForward updateCartInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// DynaBean dynaBean = (DynaBean) form;
		CartInfo entity = new CartInfo();
		super.copyProperties(entity, form);
		UserInfo ui = super.getUserInfoFromSession(request);
		StringBuffer result = new StringBuffer();
		result.append("{\"result\":");
		if (null == ui) {
			result.append("false");
			result.append("}");
			super.renderJson(response, result.toString());
			return null;
		}
		int rows = getFacade().getCartInfoService().modifyCartInfo(entity);
		if (rows >= 1) {
			result.append("true");
		} else {
			result.append("false");
		}

		result.append("}");
		super.renderJson(response, result.toString());
		return null;

	}

	public ActionForward saveShippingAddress(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// DynaBean dynaBean = (DynaBean) form;

		ShippingAddress entity = new ShippingAddress();
		super.copyProperties(entity, form);

		UserInfo ui = super.getUserInfoFromSession(request);
		StringBuffer result = new StringBuffer();
		result.append("{\"result\":");
		Integer id = 0;
		if (null == ui) {
			result.append("false");
			result.append("}");
			super.renderJson(response, result.toString());
			return null;
		}
		entity.setAdd_user_id(ui.getId());
		entity.setAdd_date(new Date());
		id = getFacade().getShippingAddressService().createShippingAddress(entity);

		if (null != id) {
			result.append(id);
		} else {
			result.append("false");
		}

		result.append("}");
		super.renderJson(response, result.toString());
		return null;

	}

	public ActionForward delShippingAddress(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		UserInfo ui = super.getUserInfoFromSession(request);
		StringBuffer result = new StringBuffer();
		result.append("{\"result\":");
		if (null == ui) {
			result.append("false").append("}");
			super.renderJson(response, result.toString());
			return null;
		}
		if (StringUtils.isBlank(id)) {
			result.append("false");
		} else {
			ShippingAddress entity = new ShippingAddress();
			entity.setId(Integer.valueOf(id));
			getFacade().getShippingAddressService().removeShippingAddress(entity);
			result.append("true");
		}
		result.append("}");
		super.renderJson(response, result.toString());
		return null;

	}

	public ActionForward getServerTime(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Calendar cal = Calendar.getInstance();
		String sysDate = DateFormatUtils.format(cal.getTime(), "yyyy,MM,dd,HH,mm,ss");
		logger.info("sysDate:{}", sysDate);
		String[] dates = StringUtils.split(sysDate, ",");
		Integer month = Integer.valueOf(dates[1]) - 1;
		dates[1] = String.valueOf(month);
		logger.info("sysDate_now:{}", StringUtils.join(dates, ","));
		super.renderText(response, StringUtils.join(dates, ","));
		return null;
	}

	/**
	 * @author minyg
	 * @since 2013-09-26
	 */

	public ActionForward chooseEntpInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='../../login.shtml'}");
			return null;
		}

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String entp_name_like = (String) dynaBean.get("entp_name_like");
		String user_name_like = (String) dynaBean.get("user_name_like");
		String wl_name_like = (String) dynaBean.get("wl_name_like");
		String _entp_type = (String) dynaBean.get("_entp_type");
		String own_sys = (String) dynaBean.get("own_sys");
		EntpInfo entity = new EntpInfo();
		super.copyProperties(entity, form);
		entity.setAudit_state(2);
		entity.setIs_del(0);

		if (null == own_sys) {
			entity.setOwn_sys(0);
			dynaBean.set("own_sys", "0");
		}

		entity.getMap().put("entp_name_like", entp_name_like);
		entity.getMap().put("add_user_name_like", user_name_like);

		if (StringUtils.isNotBlank(_entp_type)) {
			dynaBean.set("_entp_type", _entp_type);
			if (_entp_type.equals("5")) {
				WlCompInfo wlCompInfo = new WlCompInfo();
				wlCompInfo.setIs_del(0);
				wlCompInfo.setIs_collaborate(1);
				if (StringUtils.isNotBlank(wl_name_like)) {
					wlCompInfo.getMap().put("wl_comp_name_like", wl_name_like);
				}
				Integer recordCount = getFacade().getWlCompInfoService().getWlCompInfoCount(wlCompInfo);
				pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
				wlCompInfo.getRow().setFirst(pager.getFirstRow());
				wlCompInfo.getRow().setCount(pager.getRowCount());
				List<WlCompInfo> entityList = super.getFacade().getWlCompInfoService()
						.getWlCompInfoPaginatedList(wlCompInfo);
				request.setAttribute("wlCompInfoList", entityList);
				request.setAttribute("wl_comp_info", true);
				return new ActionForward("/index/CsAjax/choose_wlCompInfo.jsp");

			} else {

				Integer recordCount = getFacade().getEntpInfoService().getEntpInfoCount(entity);
				pager.init(recordCount.longValue(), 10, pager.getRequestPage());
				entity.getRow().setFirst(pager.getFirstRow());
				entity.getRow().setCount(pager.getRowCount());
				List<EntpInfo> entpInfoList = getFacade().getEntpInfoService().getEntpInfoList(entity);
				request.setAttribute("entityList", entpInfoList);
				return new ActionForward("/index/CsAjax/choose_entpinfo.jsp");
			}
		} else {

			Integer recordCount = getFacade().getEntpInfoService().getEntpInfoCount(entity);
			pager.init(recordCount.longValue(), 10, pager.getRequestPage());
			entity.getRow().setFirst(pager.getFirstRow());
			entity.getRow().setCount(pager.getRowCount());
			List<EntpInfo> entityList = super.getFacade().getEntpInfoService().getEntpInfoPaginatedList(entity);
			request.setAttribute("entityList", entityList);
			return new ActionForward("/index/CsAjax/choose_entpinfo.jsp");
		}

	}

	/**
	 * @author dxj
	 * @desc 合伙人查询门店
	 * @since 2017-11-27
	 */

	public ActionForward chooseEntpInfo2(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='../../login.shtml'}");
			return null;
		}

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String entp_name_like = (String) dynaBean.get("entp_name_like");
		String user_name_like = (String) dynaBean.get("user_name_like");
		String own_sys = (String) dynaBean.get("own_sys");
		EntpInfo entity = new EntpInfo();
		super.copyProperties(entity, form);
		entity.setAudit_state(2);
		entity.setIs_del(0);

		if (null == own_sys) {
			entity.setOwn_sys(0);
			dynaBean.set("own_sys", "0");
		}

		entity.getMap().put("entp_name_like", entp_name_like);
		entity.getMap().put("add_user_name_like", user_name_like);

		ServiceCenterInfo serviceCenterInfo = super.getUserLinkServiceInfo(ui.getId());
		entity.setLink_service_center_id(serviceCenterInfo.getId());

		Integer recordCount = getFacade().getEntpInfoService().getEntpInfoCount(entity);
		pager.init(recordCount.longValue(), 10, pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<EntpInfo> entityList = super.getFacade().getEntpInfoService().getEntpInfoPaginatedList(entity);
		request.setAttribute("entityList", entityList);
		return new ActionForward("/index/CsAjax/choose_entpinfo2.jsp");
	}

	public ActionForward waitBindWeixin(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String ret = "2";
		String msg = "等待绑定中...";
		JSONObject json = new JSONObject();

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			ret = "0";
			msg = "登陆超时";
			json.put("msg", msg);
			json.put("ret", ret);
			super.renderJson(response, json.toString());
			return null;
		}
		UserInfo uif = new UserInfo();
		uif.setId(ui.getId());
		uif = getFacade().getUserInfoService().getUserInfo(uif);
		if (null == uif) {
			ret = "0";
			msg = "用户不存在";
			json.put("msg", msg);
			json.put("ret", ret);
			super.renderJson(response, json.toString());
			return null;
		}
		logger.info("===uif.getAppid_weixin():{}", uif.getAppid_weixin());
		if (StringUtils.isNotBlank(uif.getAppid_weixin())) {
			ret = "1";
			msg = "绑定成功，正在进入支付页面...";
			super.setUserInfoToSession(request, uif);
		}

		json.put("msg", msg);
		json.put("ret", ret);
		super.renderJson(response, json.toString());
		return null;
	}

	public ActionForward waitIsPay(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String out_trade_no = (String) dynaBean.get("out_trade_no");
		String order_type = (String) dynaBean.get("order_type");
		String ret = "2";
		String msg = "等待支付中...";
		JSONObject json = new JSONObject();

		if (StringUtils.isBlank(out_trade_no)) {
			ret = "0";
			msg = "out_trade_no订单号为空";
			json.put("msg", msg);
			json.put("ret", ret);
			super.renderJson(response, json.toString());
			return null;
		}
		if (!GenericValidator.isInt(order_type)) {
			ret = "0";
			msg = "order_type参数错误";
			json.put("msg", msg);
			json.put("ret", ret);
			super.renderJson(response, json.toString());
			return null;
		}

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			ret = "0";
			msg = "登陆超时";
			json.put("msg", msg);
			json.put("ret", ret);
			super.renderJson(response, json.toString());
			return null;
		}
		UserInfo uif = new UserInfo();
		uif.setId(ui.getId());
		uif = getFacade().getUserInfoService().getUserInfo(uif);
		if (null == uif) {
			ret = "0";
			msg = "用户不存在";
			json.put("msg", msg);
			json.put("ret", ret);
			super.renderJson(response, json.toString());
			return null;
		}

		if (!GenericValidator.isInt(order_type)) {
			ret = "0";
			msg = "order_type参数为空";
			json.put("msg", msg);
			json.put("ret", ret);
			super.renderJson(response, json.toString());
			return null;
		}

		if (Integer.valueOf(order_type).intValue() != Keys.OrderType.ORDER_TYPE_20.getIndex()) { // 普通订单
			OrderMergerInfo orderMergerInfo = new OrderMergerInfo();
			orderMergerInfo.setOut_trade_no(out_trade_no);
			orderMergerInfo = super.getFacade().getOrderMergerInfoService().getOrderMergerInfo(orderMergerInfo);
			if (null == orderMergerInfo) {
				ret = "0";
				msg = "订单号不存在";
				json.put("msg", msg);
				json.put("ret", ret);
				super.renderJson(response, json.toString());
				return null;
			}

			OrderMergerInfo orderMergerInfoSon = new OrderMergerInfo();
			orderMergerInfoSon.setPar_id(orderMergerInfo.getId());
			List<OrderMergerInfo> orderMergerInfoList = getFacade().getOrderMergerInfoService().getOrderMergerInfoList(
					orderMergerInfoSon);
			if ((orderMergerInfoList == null) || (orderMergerInfoList.size() <= 0)) {
				ret = "0";
				msg = "子订单号不存在";
				json.put("msg", msg);
				json.put("ret", ret);
				super.renderJson(response, json.toString());
				return null;
			}
			for (OrderMergerInfo m : orderMergerInfoList) {
				if ((m.getPay_state() == 1)) {

					ret = "1";
					msg = "支付成功，正在进入个人中心...";
					break;
				}
			}
		} else {// 其他订单都是直接存的orderinfo
			OrderInfo orderInfo = new OrderInfo();
			orderInfo.setTrade_index(out_trade_no);
			orderInfo = getFacade().getOrderInfoService().getOrderInfo(orderInfo);
			if (null == orderInfo) {
				ret = "0";
				msg = "订单不存在或已支付!";
				json.put("msg", msg);
				json.put("ret", ret);
				super.renderJson(response, json.toString());
			}
			if (orderInfo.getOrder_state().intValue() >= Keys.OrderState.ORDER_STATE_40.getIndex()) {
				ret = "1";
				msg = "支付成功，正在进入个人中心...";
			}
		}

		json.put("msg", msg);
		json.put("ret", ret);
		super.renderJson(response, json.toString());
		return null;
	}

	public ActionForward waitIsPayOrderNoOrderMerger(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String out_trade_no = (String) dynaBean.get("out_trade_no");
		String ret = "2";
		String msg = "等待支付中...";
		JSONObject json = new JSONObject();

		if (StringUtils.isBlank(out_trade_no)) {
			ret = "0";
			msg = "out_trade_no订单号为空";
			json.put("msg", msg);
			json.put("ret", ret);
			super.renderJson(response, json.toString());
			return null;
		}

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			ret = "0";
			msg = "登陆超时";
			json.put("msg", msg);
			json.put("ret", ret);
			super.renderJson(response, json.toString());
			return null;
		}
		UserInfo uif = new UserInfo();
		uif.setId(ui.getId());
		uif = getFacade().getUserInfoService().getUserInfo(uif);
		if (null == uif) {
			ret = "0";
			msg = "用户不存在";
			json.put("msg", msg);
			json.put("ret", ret);
			super.renderJson(response, json.toString());
			return null;
		}

		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setTrade_index(out_trade_no);
		orderInfo = getFacade().getOrderInfoService().getOrderInfo(orderInfo);
		if (null == orderInfo) {
			ret = "0";
			msg = "订单不存在或已支付!";
			json.put("msg", msg);
			json.put("ret", ret);
			super.renderJson(response, json.toString());
		}
		if (orderInfo.getOrder_state().intValue() >= Keys.OrderState.ORDER_STATE_40.getIndex()) {
			ret = "1";
			msg = "支付成功，正在进入个人中心...";
		}

		json.put("msg", msg);
		json.put("ret", ret);
		super.renderJson(response, json.toString());
		return null;
	}

	public ActionForward waitIsServiceCenterState(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String link_id = (String) dynaBean.get("link_id");
		String ret = "2";
		String msg = "等待支付中...";
		JSONObject json = new JSONObject();

		if (StringUtils.isBlank(link_id)) {
			ret = "0";
			msg = "link_id为空";
			json.put("msg", msg);
			json.put("ret", ret);
			super.renderJson(response, json.toString());
			return null;
		}

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			ret = "0";
			msg = "登陆超时";
			json.put("msg", msg);
			json.put("ret", ret);
			super.renderJson(response, json.toString());
			return null;
		}
		UserInfo uif = new UserInfo();
		uif.setId(ui.getId());
		uif = getFacade().getUserInfoService().getUserInfo(uif);
		if (null == uif) {
			ret = "0";
			msg = "用户不存在";
			json.put("msg", msg);
			json.put("ret", ret);
			super.renderJson(response, json.toString());
			return null;
		}

		ServiceCenterInfo entity = new ServiceCenterInfo();
		entity.setId(Integer.valueOf(link_id));
		entity = super.getFacade().getServiceCenterInfoService().getServiceCenterInfo(entity);
		if (null == entity) {
			ret = "0";
			msg = "订单不存在或已支付!";
			json.put("msg", msg);
			json.put("ret", ret);
			super.renderJson(response, json.toString());
		}
		if (entity.getPay_success().intValue() == 1) {
			ret = "1";
			msg = "支付成功，正在进入个人中心...";
		}

		json.put("msg", msg);
		json.put("ret", ret);
		super.renderJson(response, json.toString());
		return null;
	}

	public ActionForward waitIsCityManagerState(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String link_id = (String) dynaBean.get("link_id");
		String ret = "2";
		String msg = "等待支付中...";
		JSONObject json = new JSONObject();

		if (StringUtils.isBlank(link_id)) {
			ret = "0";
			msg = "link_id为空";
			json.put("msg", msg);
			json.put("ret", ret);
			super.renderJson(response, json.toString());
			return null;
		}

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			ret = "0";
			msg = "登陆超时";
			json.put("msg", msg);
			json.put("ret", ret);
			super.renderJson(response, json.toString());
			return null;
		}
		UserInfo uif = new UserInfo();
		uif.setId(ui.getId());
		uif = getFacade().getUserInfoService().getUserInfo(uif);
		if (null == uif) {
			ret = "0";
			msg = "用户不存在";
			json.put("msg", msg);
			json.put("ret", ret);
			super.renderJson(response, json.toString());
			return null;
		}
		if (uif.getId().intValue() != Integer.valueOf(link_id).intValue()) {
			ret = "0";
			msg = "充值单号于当前账号不匹配";
			json.put("msg", msg);
			json.put("ret", ret);
			super.renderJson(response, json.toString());
			return null;
		}

		if (uif.getIs_city_manager().intValue() == 1) {
			ret = "1";
			msg = "支付成功，正在进入个人中心...";
		}

		json.put("msg", msg);
		json.put("ret", ret);
		super.renderJson(response, json.toString());
		return null;
	}

	public ActionForward getCommInfoList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String comm_name_like = (String) dynaBean.get("comm_name_like");
		String comm_type_in = (String) dynaBean.get("comm_type_in");
		String is_jd = (String) dynaBean.get("is_jd");
		String is_p_index = (String) dynaBean.get("is_p_index");
		CommInfo entity = new CommInfo();
		super.copyProperties(entity, form);
		entity.setAudit_state(1);
		entity.setIs_del(0);
		entity.getMap().put("keyword", comm_name_like);
		entity.getMap().put("comm_type_in", comm_type_in);
		if (StringUtils.isNotBlank(is_jd)) {
			entity.setIs_jd(Integer.valueOf(is_jd));
		}
		System.out.println("is_p_index:" + is_p_index);
		if (StringUtils.isNotBlank(is_p_index)) {
			UserInfo ui = super.getUserInfoFromSession(request);

			ServiceCenterInfo ser = new ServiceCenterInfo();
			ser.setAdd_user_id(ui.getId());
			ser.setIs_del(0);
			ser.setAudit_state(1);
			ser = getFacade().getServiceCenterInfoService().getServiceCenterInfo(ser);

			System.out.println("p_index_like:" + ser.getP_index());
			entity.getMap().put("p_index_like", ser.getP_index().toString().substring(0, 3));

		}

		Integer recordCount = getFacade().getCommInfoService().getCommInfoCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<CommInfo> entityList = super.getFacade().getCommInfoService().getCommInfoPaginatedList(entity);
		request.setAttribute("entityList", entityList);
		return new ActionForward("/manager/admin/BaseCsAjax/getCommInfoList.jsp");
	}

	public ActionForward getEntpInfoList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String entp_name_like = (String) dynaBean.get("entp_name_like");
		EntpInfo entity = new EntpInfo();
		super.copyProperties(entity, form);
		entity.getMap().put("audit_state_in", "1,2");
		entity.setIs_del(0);
		entity.getMap().put("entp_name_like", entp_name_like);

		Integer recordCount = getFacade().getEntpInfoService().getEntpInfoCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<EntpInfo> entityList = super.getFacade().getEntpInfoService().getEntpInfoPaginatedList(entity);
		request.setAttribute("entityList", entityList);
		return new ActionForward("/manager/admin/BaseCsAjax/getEntpInfoList.jsp");
	}

	public ActionForward getFreightInfoList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String own_entp_id = (String) dynaBean.get("own_entp_id");
		String fre_title_like = (String) dynaBean.get("fre_title_like");

		Freight entity = new Freight();
		entity.setIs_del(0);
		entity.setEntp_id(Integer.valueOf(own_entp_id));

		entity.getMap().put("fre_title_like", fre_title_like);

		Integer recordCount = getFacade().getFreightService().getFreightCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<Freight> entityList = super.getFacade().getFreightService().getFreightPaginatedList(entity);
		request.setAttribute("entityList", entityList);

		return new ActionForward("/index/CsAjax/choose_fre_info.jsp");
	}

	public ActionForward getBrandInfoList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String brand_name_like = (String) dynaBean.get("brand_name_like");

		BaseBrandInfo entity = new BaseBrandInfo();
		entity.setIs_del(0);
		entity.setAudit_state(1);
		entity.getMap().put("brand_name_like", brand_name_like);

		Integer recordCount = getFacade().getBaseBrandInfoService().getBaseBrandInfoCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<BaseBrandInfo> entityList = super.getFacade().getBaseBrandInfoService()
				.getBaseBrandInfoPaginatedList(entity);
		request.setAttribute("entityList", entityList);

		return new ActionForward("/index/CsAjax/choose_brand_info.jsp");
	}

	public ActionForward getPIdexFromPname(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		String ret = "0";
		String p_index = "";
		String p_name = "";

		DynaBean dynaBean = (DynaBean) form;
		String in_p_name = (String) dynaBean.get("p_name");
		BaseProvince entity = new BaseProvince();
		entity.setP_name(in_p_name);
		List<BaseProvince> lists = super.getFacade().getBaseProvinceService().getBaseProvinceList(entity);
		if (lists.size() > 0) {
			json.put("ret", ret);
			json.put("p_index", lists.get(0).getP_index());
			json.put("p_name", lists.get(0).getP_name());

		}
		super.renderJson(response, json.toString());
		return null;
	}

	public ActionForward getCookiePindex(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		String ret = "0";
		String p_index = "";
		String p_name = "";
		Cookie current_p_index = WebUtils.getCookie(request, Keys.COOKIE_P_INDEX);
		Cookie current_p_name = WebUtils.getCookie(request, Keys.COOKIE_P_NAME);

		if (null != current_p_index) {
			p_index = URLDecoder.decode(current_p_index.getValue(), "UTF-8");
			ret = "1";
		} else {
			ret = "1";
			p_index = Keys.P_INDEX_CITY;
		}
		if (null != current_p_name) {
			p_name = URLDecoder.decode(current_p_name.getValue(), "UTF-8");
		} else {
			p_name = Keys.P_INDEX_CITY_NAME;
		}
		logger.info("current_p_index:" + p_index);
		logger.info("current_p_name:" + p_name);
		json.put("ret", ret);
		json.put("p_index", p_index);
		json.put("p_name", p_name);
		super.renderJson(response, json.toString());
		return null;
	}

	public ActionForward getAreaList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		DynaBean dynaBean = (DynaBean) form;
		String p_index = (String) dynaBean.get("p_index");

		BaseProvince entity = new BaseProvince();
		entity.setPar_index(Integer.valueOf(p_index).longValue());
		entity.setIs_del(0);
		List<BaseProvince> tempList = getFacade().getBaseProvinceService().getBaseProvinceList(entity);
		if (null != tempList && tempList.size() > 0) {
			JSONArray tempArray = new JSONArray();
			for (BaseProvince temp : tempList) {
				JSONObject tempEntity = new JSONObject();
				tempEntity.put("p_name", temp.getP_name());
				tempEntity.put("p_index", temp.getP_index());
				tempArray.add(tempEntity);
			}
			json.put("list", tempArray);
		}
		super.renderJson(response, json.toString());
		return null;
	}

	public ActionForward getUrlLinkModId(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject json = new JSONObject();
		String ret = "0";
		String data_url = "";
		String par_id = "";
		DynaBean dynaBean = (DynaBean) form;
		String mod_id = (String) dynaBean.get("mod_id");
		String ctx = super.getCtxPath(request);
		if (StringUtils.isNotBlank(mod_id)) {
			SysModule entity = new SysModule();
			entity.setMod_id(Long.valueOf(mod_id));
			entity = super.getFacade().getSysModuleService().getSysModule(entity);
			if (null != entity) {
				ret = "1";
				if (null != entity.getMod_desc()) {
					String linkUrl = "?";
					if (StringUtils.contains(entity.getMod_url(), linkUrl)) {
						linkUrl = "&";
					}
					data_url = ctx + entity.getMod_url() + linkUrl + "par_id=" + entity.getPar_id() + "&mod_id="
							+ entity.getMod_id();
				}
				par_id = entity.getPar_id().toString();
			}
		}
		json.put("ret", ret);
		json.put("par_id", par_id);
		json.put("data_url", data_url);
		super.renderJson(response, json.toString());
		return null;
	}

	public ActionForward getUserLockBiDianzi(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String user_id = (String) dynaBean.get("user_id");

		JSONObject json = new JSONObject();
		String ret = "0";
		BigDecimal lock_dianzibi = new BigDecimal(0);
		BigDecimal dianzibi = new BigDecimal(0);

		if (StringUtils.isNotBlank(user_id)) {
			UserInfo ui = super.getUserInfo(Integer.valueOf(user_id));
			if (null != ui) {
				ret = "1";
				BigDecimal lock_dianzibi_to_rmb = super.BiToBi(ui.getBi_dianzi_lock(),
						Keys.BASE_DATA_ID.BASE_DATA_ID_904.getIndex());
				lock_dianzibi = lock_dianzibi_to_rmb;

				BigDecimal dianzibi_to_rmb = super.BiToBi(ui.getBi_dianzi(),
						Keys.BASE_DATA_ID.BASE_DATA_ID_904.getIndex());
				dianzibi = dianzibi_to_rmb;
			}
		}
		json.put("ret", ret);
		json.put("lock_dianzibi", lock_dianzibi);
		json.put("dianzibi", dianzibi);
		super.renderJson(response, json.toString());
		return null;
	}

	public ActionForward getUserInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String user_name_like = (String) dynaBean.get("user_name_like");
		String mobile_like = (String) dynaBean.get("mobile_like");
		String user_ids = (String) dynaBean.get("user_ids");
		String user_names = (String) dynaBean.get("user_names");
		String userTypeNotIn = (String) dynaBean.get("userTypeNotIn");

		String type = (String) dynaBean.get("type");

		if (StringUtils.isBlank(type)) {// multiple , single
			type = "single";
		}
		int pagesize = 10;
		if ((type).equals("multiple")) {// multiple , single
			pagesize = 44;
		}

		UserInfo entity = new UserInfo();
		super.copyProperties(entity, form);
		entity.setIs_del(0);
		entity.getMap().put("user_name_like", user_name_like);
		entity.getMap().put("mobile_like", mobile_like);
		entity.getMap().put("user_type_not_in", userTypeNotIn);

		long recordCount = getFacade().getUserInfoService().getUserInfoCount(entity);
		pager.init(recordCount, pagesize, pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<UserInfo> entityList = super.getFacade().getUserInfoService().getUserInfoPaginatedList(entity);
		request.setAttribute("entityList", entityList);
		logger.info("==type:{}", type);

		if (StringUtils.isNotBlank(user_ids)) {
			UserInfo user_selected = new UserInfo();
			user_selected.setIs_del(0);
			user_selected.getMap().put("user_ids_in", user_ids);
			List<UserInfo> userListSelected = getFacade().getUserInfoService().getUserInfoList(user_selected);
			request.setAttribute("userListSelected", userListSelected);
		}

		return new ActionForward("/index/CsAjax/list_userinfo_" + type + ".jsp");

	}

	public ActionForward getCommNo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String cls_id = (String) dynaBean.get("cls_id");
		logger.info("cls_id:{" + cls_id + "}");
		JSONObject result = new JSONObject();
		String comm_no = "";
		if (StringUtils.isNotBlank(cls_id)) {

			comm_no = getCommNoBase(cls_id);
		}
		logger.info(comm_no);
		result.put("comm_no", comm_no);
		super.render(response, result.toString(), "text/x-json;charset=UTF-8");

		return null;
	}

	public ActionForward choosePIndex(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String p_name_like = (String) dynaBean.get("p_name_like");
		BaseProvince baseprovince = new BaseProvince();
		baseprovince.setIs_del(0);
		baseprovince.setIs_west(1);

		baseprovince.getMap().put("p_name_like", p_name_like);

		Integer recordCount = getFacade().getBaseProvinceService().getBaseProvinceCount(baseprovince);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		baseprovince.getRow().setFirst(pager.getFirstRow());
		baseprovince.getRow().setCount(pager.getRowCount());

		List<BaseProvince> entityList = super.getFacade().getBaseProvinceService()
				.getBaseProvincePaginatedList(baseprovince);
		request.setAttribute("entityList", entityList);

		return new ActionForward("/index/CsAjax/choose_p_index.jsp");

	}

	public ActionForward chooseCommInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String comm_name_like = (String) dynaBean.get("comm_name_like");
		String comm_type = (String) dynaBean.get("comm_type");
		String p_indexs = (String) dynaBean.get("p_indexs");
		String noNeedState = (String) dynaBean.get("noNeedState");
		String ajax = (String) dynaBean.get("ajax");
		String is_jd = (String) dynaBean.get("is_jd");
		String is_p_index = (String) dynaBean.get("is_p_index");
		CommInfo entity = new CommInfo();
		entity.getMap().put("ajax", ajax);
		super.copyProperties(entity, form);
		if (StringUtils.isBlank(noNeedState)) {
			entity.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		}
		entity.setIs_del(0);
		entity.getMap().put("keyword", comm_name_like);

		if (StringUtils.isNotBlank(is_jd)) {
			entity.setIs_jd(Integer.valueOf(is_jd));
		}

		if (StringUtils.isNotBlank(comm_type)) {
			entity.getMap().put("comm_type_in", comm_type);
		} else {
			entity.setComm_type(Keys.CommType.COMM_TYPE_2.getIndex());
		}

		if (StringUtils.isNotBlank(is_p_index)) {
			UserInfo ui = super.getUserInfoFromSession(request);
			log.info("ui:" + ui.getUser_name());
			if (null != ui) {
				ServiceCenterInfo ser = new ServiceCenterInfo();
				ser.setAdd_user_id(ui.getId());
				ser.setIs_del(0);
				ser.setAudit_state(1);
				ser = getFacade().getServiceCenterInfoService().getServiceCenterInfo(ser);
				if (null != ser) {
					entity.getMap().put("p_index_like", ser.getP_index().toString().substring(0, 4));
				}
			}

		}

		if (StringUtils.isNotBlank(p_indexs)) {
			if (p_indexs.endsWith("0000")) {
				entity.setP_index(Integer.valueOf(p_indexs));
			} else if (p_indexs.endsWith("00")) {
				entity.getMap().put("p_index_city", StringUtils.substring(p_indexs, 0, 4));
			} else {
				entity.getMap().put("p_index_province", StringUtils.substring(p_indexs, 0, 2));
			}
		}

		Integer recordCount = getFacade().getCommInfoService().getCommInfoCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<CommInfo> entityList = super.getFacade().getCommInfoService().getCommInfoPaginatedList(entity);
		List<CommInfo> list = new ArrayList<CommInfo>();
		if (null != entityList && entityList.size() > 0) {
			for (CommInfo commInfo : entityList) {
				commInfo.setComm_name(commInfo.getComm_name().replace("\"", ""));
				list.add(commInfo);

			}
		}
		request.setAttribute("entityList", list);

		return new ActionForward("/index/CsAjax/choose_comminfo.jsp");

	}

	public ActionForward chooseBaseDataList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String type_name_like = (String) dynaBean.get("type_name_like");

		BaseData entity = new BaseData();
		super.copyProperties(entity, dynaBean);
		entity.setIs_del(0);
		entity.getMap().put("type_name_like", type_name_like);
		Integer recordCount = getFacade().getBaseDataService().getBaseDataCount(entity);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<BaseData> entityList = getFacade().getBaseDataService().getBaseDataPaginatedList(entity);
		request.setAttribute("entityList", entityList);

		return new ActionForward("/index/CsAjax/chooseBaseDataList.jsp");

	}

	public ActionForward chooseEntpInfoWithHome(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String entp_name_like = (String) dynaBean.get("entp_name_like");
		String p_indexs = (String) dynaBean.get("p_indexs");

		EntpInfo entity = new EntpInfo();
		super.copyProperties(entity, form);
		entity.getMap().put("audit_state_in", "1,2");
		entity.setIs_del(0);
		entity.setEntp_type(Keys.EntpType.ENTP_TYPE_10.getIndex());
		entity.getMap().put("entp_name_like", entp_name_like);

		if (StringUtils.isNotBlank(p_indexs)) {
			if (p_indexs.endsWith("0000")) {
				entity.setP_index(Integer.valueOf(p_indexs));
			} else if (p_indexs.endsWith("00")) {
				entity.getMap().put("p_index_city", StringUtils.substring(p_indexs, 0, 4));
			} else {
				entity.getMap().put("p_index_province", StringUtils.substring(p_indexs, 0, 2));
			}
		}

		Integer recordCount = getFacade().getEntpInfoService().getEntpInfoCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<EntpInfo> entityList = super.getFacade().getEntpInfoService().getEntpInfoPaginatedList(entity);
		request.setAttribute("entityList", entityList);

		return new ActionForward("/index/CsAjax/choose_entp_info.jsp");

	}

	public ActionForward getWlCompInfoList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		WlCompInfo entity = new WlCompInfo();
		entity.setIs_del(0);
		List<WlCompInfo> entityList = super.getFacade().getWlCompInfoService().getWlCompInfoGroupByPalpha(entity);

		for (WlCompInfo temp : entityList) {
			WlCompInfo entityEvery = new WlCompInfo();
			entityEvery.setIs_del(0);
			entityEvery.setP_alpha(temp.getP_alpha());
			List<WlCompInfo> tempSonList = super.getFacade().getWlCompInfoService().getWlCompInfoList(entityEvery);
			temp.getMap().put("tempSonList", tempSonList);
		}
		request.setAttribute("entityList", entityList);

		return new ActionForward("/index/CsAjax/getWlCompInfoList.jsp");

	}

	public String getPnamePindexFromIp(HttpServletRequest request, String ip) throws Exception {

		String url = "http://ip.taobao.com/service/getIpInfo.php?ip=" + ip;
		String result = GetApiUtils.getApiWithUrl(url);

		String p_name = "";
		try {
			JSONObject json = (JSONObject) JSONObject.parse(result.toString());
			JSONObject json_data = json.getJSONObject("data");
			p_name = (String) json_data.get("city");
		} catch (Exception e) {
			p_name = Keys.P_INDEX_CITY_NAME;
		}

		BaseProvince entity = new BaseProvince();
		entity.setIs_west(1);
		entity.setIs_del(0);
		entity.setP_name(p_name);
		List<BaseProvince> entityList = super.getFacade().getBaseProvinceService().getBaseProvinceList(entity);
		if (null != entityList && entityList.size() > 0) {
			p_name = entityList.get(0).getP_name();
		}

		CookieGenerator cg_p_index = new CookieGenerator();
		cg_p_index.setCookieMaxAge(7 * 24 * 60 * 60);
		cg_p_index.setCookieName(Keys.COOKIE_P_INDEX);

		CookieGenerator cg_p_name = new CookieGenerator();
		cg_p_name.setCookieMaxAge(7 * 24 * 60 * 60);
		cg_p_name.setCookieName(Keys.COOKIE_P_NAME);

		return p_name;
	}

	public ActionForward getPindexFromIp(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String ip = this.getIpAddr(request);

		String url = "http://ip.taobao.com/service/getIpInfo.php?ip=" + ip;
		String result = GetApiUtils.getApiWithUrl(url);

		JSONObject data = new JSONObject();
		int ret = 1;
		String msg = "取到地址";

		String p_name = "";
		String p_index = "";
		try {
			logger.info("===ss:" + result.toString());
			JSONObject json = (JSONObject) JSONObject.parse(result.toString());
			JSONObject json_data = json.getJSONObject("data");
			p_name = (String) json_data.get("city");
		} catch (Exception e) {
			p_name = Keys.P_INDEX_CITY_NAME;
			p_index = Keys.P_INDEX_CITY;
		}

		if (StringUtils.isBlank(p_name)) {
			p_name = Keys.QUANGUO_P_NAME;
			p_index = Keys.QUANGUO_P_INDEX;
		}

		BaseProvince entity = new BaseProvince();
		entity.setIs_west(1);
		entity.setIs_del(0);
		entity.setP_name(p_name);
		List<BaseProvince> entityList = super.getFacade().getBaseProvinceService().getBaseProvinceList(entity);
		if (null != entityList && entityList.size() > 0) {
			p_name = entityList.get(0).getP_name();
			p_index = entityList.get(0).getP_index().toString();
		}
		data.put("ret", ret);
		data.put("msg", msg);
		data.put("p_index", p_index);
		data.put("p_name", p_name);

		super.renderJson(response, data.toJSONString());
		return null;
	}

	public ActionForward ajaxGetPindex(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String p_name_param = (String) dynaBean.get("p_name");

		String p_name = Keys.QUANGUO_P_NAME;
		String p_index = Keys.QUANGUO_P_INDEX;
		if (StringUtils.isNotBlank(p_name_param)) {
			p_name = p_name_param;
		}

		JSONObject jsonObject = new JSONObject();

		BaseProvince entity = new BaseProvince();
		entity.setIs_del(0);
		entity.getMap().put("p_name_like", p_name);
		List<BaseProvince> entityList = super.getFacade().getBaseProvinceService().getBaseProvinceList(entity);

		if (null != entityList && entityList.size() > 0) {
			p_name = entityList.get(0).getP_name();
			p_index = entityList.get(0).getP_index().toString();
		}

		CookieGenerator cg_p_index = new CookieGenerator();
		cg_p_index.setCookieMaxAge(7 * 24 * 60 * 60);
		cg_p_index.setCookieName(Keys.COOKIE_P_INDEX);

		CookieGenerator cg_p_name = new CookieGenerator();
		cg_p_name.setCookieMaxAge(7 * 24 * 60 * 60);
		cg_p_name.setCookieName(Keys.COOKIE_P_NAME);

		try {
			cg_p_index.addCookie(response, URLEncoder.encode(p_index.toString(), "UTF-8"));
			cg_p_name.addCookie(response, URLEncoder.encode(p_name, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			logger.warn("==setCookiePindex:" + e.getMessage());
		}

		jsonObject.put("ret", 1);
		jsonObject.put("p_name", p_name);
		jsonObject.put("p_index", p_index);
		super.renderJson(response, jsonObject.toString());
		return null;

	}

	public ActionForward setPindexFromIp(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String ip = this.getIpAddr(request);
		// ip = "220.178.14.98";
		logger.info("==ip:{}", ip);
		String url = "http://ip.taobao.com/service/getIpInfo.php?ip=" + ip;
		String result = GetApiUtils.getApiWithUrl(url);

		JSONObject data = new JSONObject();
		int ret = 0;
		String msg = "取到地址";

		String p_name = "";
		String p_index = "";
		try {
			logger.info("===ss:" + result.toString());
			JSONObject json = (JSONObject) JSONObject.parse(result.toString());
			JSONObject json_data = json.getJSONObject("data");
			p_name = (String) json_data.get("city");
		} catch (Exception e) {
			data.put("ret", 0);
			data.put("msg", "获取地址出错");
			super.renderJson(response, data.toJSONString());
			return null;
		}
		// p_name = "合肥";

		BaseProvince entity = new BaseProvince();
		entity.setIs_del(0);
		entity.getMap().put("p_name_like", p_name);
		List<BaseProvince> entityList = super.getFacade().getBaseProvinceService().getBaseProvinceList(entity);
		if (null != entityList && entityList.size() > 0) {
			BaseProvince bp = entityList.get(0);
			p_name = bp.getP_name();
			p_index = bp.getP_index().toString();

			BaseProvince bpPar = new BaseProvince();
			bpPar.setP_index(bp.getPar_index());
			bpPar = getFacade().getBaseProvinceService().getBaseProvince(bpPar);

			data.put("ret", 1);
			data.put("msg", msg);
			data.put("p_index", p_index);
			data.put("p_name", p_name);
			data.put("province", bpPar.getP_index());
			// data.put("province_name", bpPar.getP_name());
			super.renderJson(response, data.toJSONString());
			return null;
		}

		data.put("ret", 0);
		data.put("msg", "获取地址出错");
		super.renderJson(response, data.toJSONString());
		return null;

	}

	public ActionForward ajaxSetPindex(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String p_name_param = (String) dynaBean.get("p_name");
		// ip = "220.178.14.98";
		logger.info("==p_name_param:{}", p_name_param);
		JSONObject data = new JSONObject();
		if (StringUtils.isBlank(p_name_param)) {
			data.put("ret", 0);
			data.put("msg", "获取地址出错");
			super.renderJson(response, data.toJSONString());
			return null;
		}

		BaseProvince entity = new BaseProvince();
		entity.setIs_del(0);
		entity.getMap().put("p_name_like", p_name_param);
		List<BaseProvince> entityList = super.getFacade().getBaseProvinceService().getBaseProvinceList(entity);

		if (null != entityList && entityList.size() > 0) {
			BaseProvince bp = entityList.get(0);
			String p_name = bp.getP_name();
			String p_index = bp.getP_index().toString();

			BaseProvince bpPar = new BaseProvince();
			bpPar.setP_index(bp.getPar_index());
			bpPar = getFacade().getBaseProvinceService().getBaseProvince(bpPar);

			data.put("ret", 1);
			data.put("msg", "成功");
			data.put("p_index", p_index);
			data.put("p_name", p_name);
			data.put("province", bpPar.getP_index());
			// data.put("province_name", bpPar.getP_name());
			super.renderJson(response, data.toJSONString());
			return null;
		}

		data.put("ret", 0);
		data.put("msg", "获取地址出错");
		super.renderJson(response, data.toJSONString());
		return null;

	}

	public ActionForward getUserInfoForMobile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String user_name_like = (String) dynaBean.get("user_name_like");
		String mobile_like = (String) dynaBean.get("mobile_like");
		String userTypeNotIn = (String) dynaBean.get("userTypeNotIn");

		int pagesize = 10;

		UserInfo entity = new UserInfo();
		super.copyProperties(entity, form);
		entity.setIs_del(0);
		entity.getMap().put("user_name_like", user_name_like);
		entity.getMap().put("mobile_like", mobile_like);
		entity.getMap().put("user_type_not_in", userTypeNotIn);

		long recordCount = getFacade().getUserInfoService().getUserInfoCount(entity);
		pager.init(recordCount, pagesize, pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<UserInfo> entityList = super.getFacade().getUserInfoService().getUserInfoPaginatedList(entity);
		request.setAttribute("entityList", entityList);

		return new ActionForward("/index/CsAjax/list_userinfo_mobile.jsp");

	}

	public ActionForward selectHelpInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");

		HelpModule entity = new HelpModule();
		super.copyProperties(entity, form);
		entity.setPar_id(1);
		long recordCount = getFacade().getHelpModuleService().getHelpModuleCount(entity);
		pager.init(recordCount, 10, pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<HelpModule> entityList = super.getFacade().getHelpModuleService().getHelpModulePaginatedList(entity);
		request.setAttribute("entityList", entityList);

		return new ActionForward("/index/CsAjax/selectHelpInfo.jsp");

	}

	public ActionForward ajaxGetJsApiTicketRetrunParam(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String targetUrl = (String) dynaBean.get("targetUrl");
		// StringBuffer requestUrl = request.getRequestURL();
		// String queryString = request.getQueryString();
		// String url = requestUrl.toString();
		// if (StringUtils.isNotBlank(queryString)) {
		// url += "?" + queryString;
		// }

		if (StringUtils.contains(targetUrl, "#")) {
			targetUrl = StringUtils.split(targetUrl, "#")[0];
		}
		JSONObject jsonObject = new JSONObject();
		boolean is_weixin = super.isWeixin(request);
		int code = 0;
		String msg = "加载失败";
		if (is_weixin && StringUtils.isNotBlank(targetUrl)) {
			code = 1;
			msg = "加载微信js成功";
			jsonObject = CommonApi.getJsApiTicketRetrunParam(targetUrl);
			jsonObject.put("timestamp", jsonObject.getLong("timestamp"));
		}
		jsonObject.put("code", code);
		jsonObject.put("msg", msg);
		super.renderJson(response, jsonObject.toString());
		return null;

	}

	public ActionForward applogin(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		// String code_sign = (String) dynaBean.get("code");

		String code = "0";
		String msg = "加载失败";

		// if (StringUtils.isBlank(code_sign) || StringUtils.length(code_sign) != 32) {
		// msg = "code参数不正确";
		// super.ajaxReturnInfo(response, code, msg, null);
		// return null;
		// }
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			msg = "请在APP登陆后扫描";
			super.ajaxReturnInfo(response, code, msg, null);
			return null;
		}

		UserInfo uiup = new UserInfo();
		uiup.setId(ui.getId());
		// uiup.setSign(code_sign);
		uiup.setIs_activate(1);
		getFacade().getUserInfoService().modifyUserInfo(uiup);

		code = "1";
		msg = "登陆成功";
		super.ajaxReturnInfo(response, code, msg, null);
		return null;

	}

	public ActionForward updateQrCode(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JSONObject datas = new JSONObject();

		String ctx = super.getCtxPath(request);
		String uuid = IdGen.uuid();
		String code_url = ctx + Keys.app_scan_login_url;
		code_url = StringUtils.replace(code_url, "{0}", uuid);

		datas.put("code_url", URLEncoder.encode(code_url, "utf-8"));
		datas.put("code", uuid);
		super.renderJson(response, datas.toString());
		return null;

	}

	public ActionForward waitIsAppConfirmLogin(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String codeUuid = (String) dynaBean.get("code");

		String code = "0";
		String msg = "加载失败";

		if (StringUtils.isBlank(codeUuid) || StringUtils.length(codeUuid) != 32) {
			msg = "code参数不正确";
			super.ajaxReturnInfo(response, code, msg, null);
			return null;
		}

		UserInfo uiup = new UserInfo();
		uiup.setSign(codeUuid);
		uiup = getFacade().getUserInfoService().getUserInfo(uiup);
		if (null == uiup) {
			msg = "继续检测中。。。";
			super.ajaxReturnInfo(response, code, msg, null);
			return null;
		}

		if (uiup.getIs_activate().intValue() == 1) {

			super.setUserInfoToSession(request, uiup);

			// 重复登录相关信息存储
			ServletContext sc = this.getServlet().getServletContext();
			Object obj = sc.getAttribute(uiup.getId().toString());
			if (null != obj) {
				sc.removeAttribute(uiup.getId().toString());
			}
			HttpSession session = request.getSession();
			session.setAttribute("loginDate", new Date());
			session.setAttribute("loginIp", getIpAddr(request));
			sc.setAttribute("repeatLogin_" + uiup.getId().toString(), session);

			code = "1";
			msg = "登陆成功";
			super.ajaxReturnInfo(response, code, msg, null);
			return null;
		} else {
			code = "2";
			msg = "请在手机上点击确认登录！";
			super.ajaxReturnInfo(response, code, msg, null);
			return null;
		}
	}

	// 获取商品套餐
	public ActionForward getCommInfoTcList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String comm_id = (String) dynaBean.get("comm_id");
		String tg_tczh_ids = (String) dynaBean.get("tg_tczh_ids");
		String comm_tczh_names = (String) dynaBean.get("comm_tczh_names");
		String choose_comm_tczh_ids = (String) dynaBean.get("comm_tczh_ids");
		logger.info("==choose_comm_tczh_ids:" + choose_comm_tczh_ids);
		dynaBean.set("comm_tczh_ids", tg_tczh_ids);
		dynaBean.set("comm_tczh_names", comm_tczh_names);
		logger.info("===comm_tczh_names+" + comm_tczh_names);

		if (StringUtils.isBlank(comm_id)) {
			String msg = "关联参数为空！";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		CommTczhAttribute entity = new CommTczhAttribute();
		entity.setComm_id(comm_id);
		entity.setType(Keys.tczh_type.tczh_type_0.getIndex());
		entity.setOrder_value(0);
		List<CommTczhAttribute> entityList = super.getFacade().getCommTczhAttributeService()
				.getCommTczhAttributeList(entity);
		if (null != entityList && entityList.size() > 0) {
			String comm_tczh_ids = "";
			for (CommTczhAttribute temp : entityList) {
				CommTczhPrice commTczhPrice = new CommTczhPrice();
				commTczhPrice.setId(temp.getComm_tczh_id());
				commTczhPrice.setTczh_type(Keys.tczh_type.tczh_type_0.getIndex());
				commTczhPrice = super.getFacade().getCommTczhPriceService().getCommTczhPrice(commTczhPrice);
				if (null != commTczhPrice) {
					temp.getMap().put("comm_price", commTczhPrice.getComm_price());
					comm_tczh_ids += commTczhPrice.getId() + ",";
				}
			}
			if (comm_tczh_ids.length() > 0) {
				comm_tczh_ids = comm_tczh_ids.substring(0, comm_tczh_ids.length() - 1);
			}
			dynaBean.set("comm_tczh_ids", comm_tczh_ids);
		}
		request.setAttribute("entityList", entityList);

		if (StringUtils.isNotBlank(tg_tczh_ids)) {
			CommTczhAttribute commTczhAttribute = new CommTczhAttribute();
			// commTczhAttribute.setComm_id(comm_id);
			commTczhAttribute.setType(Keys.tczh_type.tczh_type_1.getIndex());
			commTczhAttribute.getMap().put("comm_tczh_ids", tg_tczh_ids);
			logger.info("===查询已有套餐===");
			List<CommTczhAttribute> commTczhAttributeList = getFacade().getCommTczhAttributeService()
					.getCommTczhAttributeList(commTczhAttribute);
			if (null != commTczhAttributeList && commTczhAttributeList.size() > 0) {
				String par_comm_tczh_ids = "";
				for (CommTczhAttribute temp : commTczhAttributeList) {
					if (par_comm_tczh_ids.equals("")) {
						par_comm_tczh_ids = temp.getPar_id().toString();
					} else {
						par_comm_tczh_ids = par_comm_tczh_ids + "," + temp.getPar_id().toString();
					}
				}
				dynaBean.set("par_comm_tczh_ids", par_comm_tczh_ids);
				logger.info("=============par_comm_tczh_ids========" + par_comm_tczh_ids);
			}
			request.setAttribute("commTczhAttributeList", commTczhAttributeList);
		}
		if (StringUtils.isNotBlank(choose_comm_tczh_ids)) {
			dynaBean.set("par_comm_tczh_ids", choose_comm_tczh_ids);
		}

		return new ActionForward("/index/CsAjax/getCommInfoTc_list.jsp");
	}

	public ActionForward choosePoorInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='../../login.shtml'}");
			return null;
		}

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String real_name_like = (String) dynaBean.get("real_name_like");
		String province = (String) dynaBean.get("province");
		String city = (String) dynaBean.get("city");
		String country = (String) dynaBean.get("country");
		String town = (String) dynaBean.get("town");
		String village = (String) dynaBean.get("village");

		PoorInfo entity = new PoorInfo();
		super.copyProperties(entity, form);
		entity.setAudit_state(1);
		entity.setIs_del(0);
		entity.getMap().put("real_name_like", real_name_like);
		if (GenericValidator.isLong(village)) {
			entity.setP_index(Long.valueOf(village));
		} else if (GenericValidator.isLong(town)) {
			entity.getMap().put("town", town.substring(0, 9));
		} else if (GenericValidator.isLong(country)) {
			entity.getMap().put("country", country.substring(0, 6));
		} else if (GenericValidator.isLong(city)) {
			entity.getMap().put("city", city.substring(0, 4));
		} else if (GenericValidator.isLong(province)) {
			entity.getMap().put("province", province.substring(0, 2));
		}
		// 一个贫困户最多拥有扶贫商品数量
		BaseData baseData1902 = super.getBaseData(Keys.BASE_DATA_ID.BASE_DATA_ID_1902.getIndex());

		/*********************** 随机选择贫困户所需数据 *************************/
		List<PoorInfo> poorInfoList = super.getFacade().getPoorInfoService().getPoorInfoListSortByCommCount(entity);
		int enableChooseCount = 0;// 可选贫困户数量
		if (null != poorInfoList && poorInfoList.size() > 0) {
			for (PoorInfo t : poorInfoList) {
				CommInfoPoors commInfoPoors = new CommInfoPoors();
				commInfoPoors.setPoor_id(t.getId());
				int comm_count = super.getFacade().getCommInfoPoorsService().getCommInfoPoorsCount(commInfoPoors);
				t.getMap().put("comm_count", comm_count);
				if (comm_count < baseData1902.getPre_number()) {
					enableChooseCount++;
					t.getMap().put("enable_choose", true);
				}
			}
		}
		request.setAttribute("poorInfoList", poorInfoList);
		request.setAttribute("enableChooseCount", enableChooseCount);
		/************************************************/

		Integer recordCount = getFacade().getPoorInfoService().getPoorInfoCount(entity);
		pager.init(recordCount.longValue(), 10, pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());
		List<PoorInfo> entityList = super.getFacade().getPoorInfoService().getPoorInfoPaginatedList(entity);
		if (null != entityList && entityList.size() > 0) {
			// 一个贫困户最高年扶贫金额限制
			BaseData baseData1903 = super.getBaseData(Keys.BASE_DATA_ID.BASE_DATA_ID_1903.getIndex());

			for (PoorInfo t : entityList) {
				CommInfoPoors commInfoPoors = new CommInfoPoors();
				commInfoPoors.setPoor_id(t.getId());
				int comm_count = super.getFacade().getCommInfoPoorsService().getCommInfoPoorsCount(commInfoPoors);
				t.getMap().put("comm_count", comm_count);

				UserBiRecord userBiRecord = new UserBiRecord();
				userBiRecord.setBi_type(Keys.BiType.BI_TYPE_500.getIndex());// 扶贫金
				userBiRecord.setBi_chu_or_ru(Keys.BI_CHU_OR_RU.BASE_DATA_ID_1.getIndex());// 入
				userBiRecord.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
				userBiRecord.setAdd_user_id(t.getUser_id());// 贫困户的账号

				Calendar cal = GregorianCalendar.getInstance();
				cal.setTime(new Date());
				String year = cal.get(Calendar.YEAR) + "";

				userBiRecord.getMap().put("begin_date", year + "-01-01");
				userBiRecord.getMap().put("end_date", year + "-12-31");
				BigDecimal sum = super.getFacade().getUserBiRecordService().getUserBiSum(userBiRecord);
				if (sum.compareTo(new BigDecimal(baseData1903.getPre_number())) >= 0) {
					t.getMap().put("year_aid_out", "true");
				}
				t.getMap().put("year_aid", sum.setScale(2, RoundingMode.HALF_DOWN));
			}
		}

		request.setAttribute("entityList", entityList);

		request.setAttribute("baseData1902", baseData1902);
		return new ActionForward("/index/CsAjax/choose_poorinfo.jsp");

	}

	public ActionForward ajaxGetPindexForEntp(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String p_name_param = (String) dynaBean.get("p_name");

		String p_name = Keys.P_INDEX_CITY_NAME;
		String p_index = Keys.P_INDEX_CITY;
		if (StringUtils.isNotBlank(p_name_param)) {
			p_name = p_name_param;
		}

		JSONObject jsonObject = new JSONObject();

		BaseProvince entity = new BaseProvince();
		entity.setIs_west(1);
		entity.setIs_del(0);
		entity.setP_name(p_name);
		List<BaseProvince> entityList = super.getFacade().getBaseProvinceService().getBaseProvinceList(entity);

		if (null != entityList && entityList.size() > 0) {
			p_name = entityList.get(0).getP_name();
			p_index = entityList.get(0).getP_index().toString();
		}

		CookieGenerator cg_p_index = new CookieGenerator();
		cg_p_index.setCookieMaxAge(7 * 24 * 60 * 60);
		cg_p_index.setCookieName(Keys.COOKIE_P_INDEX);

		CookieGenerator cg_p_name = new CookieGenerator();
		cg_p_name.setCookieMaxAge(7 * 24 * 60 * 60);
		cg_p_name.setCookieName(Keys.COOKIE_P_NAME);

		try {
			cg_p_index.addCookie(response, URLEncoder.encode(p_index.toString(), "UTF-8"));
			cg_p_name.addCookie(response, URLEncoder.encode(p_name, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			logger.warn("==setCookiePindex:" + e.getMessage());
		}

		if (StringUtils.isNotBlank(p_index)) {

			BaseProvince entitySon = new BaseProvince();
			entitySon.setPar_index(Long.valueOf(p_index));
			entitySon.setIs_del(0);
			List<BaseProvince> entitySonList = super.getFacade().getBaseProvinceService()
					.getBaseProvinceList(entitySon);
			if (null != entitySonList && entitySonList.size() > 0) {
				JSONArray dataList = new JSONArray();
				for (BaseProvince bp : entitySonList) {

					EntpInfo bascEntp = new EntpInfo();
					bascEntp.setP_index(Integer.valueOf(bp.getP_index().toString()));
					bascEntp.setIs_del(0);
					bascEntp.setAudit_state(2);
					// bascEntp.setIs_closed(Keys.IsClosed.IS_CLOSED_0.getIndex());
					List<EntpInfo> bascEntpList = super.getFacade().getEntpInfoService().getEntpInfoList(bascEntp);
					if (null != bascEntpList && bascEntpList.size() > 0) {
						JSONObject dataObject = new JSONObject();
						dataObject.put("p_name", bp.getP_name());
						JSONArray entpList = new JSONArray();
						for (EntpInfo entpTemp : bascEntpList) {
							JSONObject entpObject = new JSONObject();
							entpObject.put("entp_id", entpTemp.getId());
							entpObject.put("entp_name", entpTemp.getEntp_name());
							entpList.add(entpObject);
						}
						dataObject.put("entpList", entpList);
						dataList.add(dataObject);
					}
				}

				jsonObject.put("dataList", dataList);

			}

		}

		jsonObject.put("ret", 1);
		jsonObject.put("p_name", p_name);
		jsonObject.put("p_index", p_index);
		super.renderJson(response, jsonObject.toString());
		return null;

	}

	public ActionForward ajaxSetEntpCookies(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String entp_id = (String) dynaBean.get("entp_id");

		JSONObject jsonObject = new JSONObject();

		EntpInfo entpInfo = super.getEntpInfo(Integer.valueOf(entp_id));
		if (entpInfo.getIs_has_open_online_shop() == 1) {
			jsonObject.put("ret", 0);
			jsonObject.put("msg", "该店铺暂未开通网上店铺功能，请重新选择！");
			super.renderJson(response, jsonObject.toString());
			return null;
		}

		CookieGenerator cg_p_index = new CookieGenerator();
		cg_p_index.setCookieMaxAge(7 * 24 * 60 * 60);
		cg_p_index.setCookieName(Keys.COOKIE_ENTP_ID);

		try {
			cg_p_index.addCookie(response, URLEncoder.encode(entp_id.toString(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			logger.warn("==setCookiePindex:" + e.getMessage());
		}

		jsonObject.put("ret", 1);
		super.renderJson(response, jsonObject.toString());
		return null;

	}

	public ActionForward ajaxGetTags(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String p_index = (String) dynaBean.get("p_index");

		JSONObject jsonObject = new JSONObject();
		if (StringUtils.isNotBlank(p_index) && p_index.length() >= 6) {
			BaseComminfoTags baseComminfoTags = new BaseComminfoTags();
			baseComminfoTags.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
			baseComminfoTags.getMap().put("p_index_0_or_value", p_index.toString().substring(0, 2) + "0000");
			List<BaseComminfoTags> baseComminfoTagsList = super.getFacade().getBaseComminfoTagsService()
					.getBaseComminfoTagsList(baseComminfoTags);

			JSONArray dataList = new JSONArray();
			if (null != baseComminfoTagsList && baseComminfoTagsList.size() > 0) {
				for (BaseComminfoTags t : baseComminfoTagsList) {
					JSONObject dataObject = new JSONObject();
					dataObject.put("tag_id", t.getId());
					dataObject.put("tag_name", t.getTag_name());
					dataObject.put("p_index", t.getP_index());
					dataList.add(dataObject);
				}
			}
			jsonObject.put("dataList", dataList);

			jsonObject.put("ret", 1);
			jsonObject.put("msg", "成功");
			super.renderJson(response, jsonObject.toString());
			return null;
		} else {
			BaseComminfoTags baseComminfoTags = new BaseComminfoTags();
			baseComminfoTags.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
			baseComminfoTags.setP_index(0);
			List<BaseComminfoTags> baseComminfoTagsList = super.getFacade().getBaseComminfoTagsService()
					.getBaseComminfoTagsList(baseComminfoTags);

			JSONArray dataList = new JSONArray();
			if (null != baseComminfoTagsList && baseComminfoTagsList.size() > 0) {
				for (BaseComminfoTags t : baseComminfoTagsList) {
					JSONObject dataObject = new JSONObject();
					dataObject.put("tag_id", t.getId());
					dataObject.put("tag_name", t.getTag_name());
					dataObject.put("p_index", t.getP_index());
					dataList.add(dataObject);
				}
			}
			jsonObject.put("dataList", dataList);

			jsonObject.put("ret", 1);
			jsonObject.put("msg", "成功");
			super.renderJson(response, jsonObject.toString());
			return null;
		}

	}

	public ActionForward getBaseProvinceList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String par_index = (String) dynaBean.get("p_index");

		if (!GenericValidator.isLong(par_index)) {
			return null;
		}

		BaseProvince baseProvince = new BaseProvince();
		baseProvince.setPar_index(Long.valueOf(par_index));
		baseProvince.setIs_del(new Integer("0"));

		List<BaseProvince> baseProvinceList = getFacade().getBaseProvinceService().getBaseProvinceList(baseProvince);
		List<String> dataList = new ArrayList<String>();

		for (BaseProvince entity : baseProvinceList) {
			dataList.add(StringUtils.join(new String[] { "[\"", entity.getP_name(), "\",\"",
					String.valueOf(entity.getP_index()), "\"]" }));
		}

		super.renderJson(response, StringUtils.join(new String[] { "[", StringUtils.join(dataList, ","), "]" }));
		return null;
	}

	public ActionForward chooseUserInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='../../login.shtml'}");
			return null;
		}

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String user_name_like = (String) dynaBean.get("user_name_like");

		UserInfo entity = new UserInfo();
		super.copyProperties(entity, form);
		entity.setIs_renzheng(1);
		entity.setIs_fuwu(0);
		entity.setIs_village(0);
		entity.setUser_type(Keys.UserType.USER_TYPE_2.getIndex());
		entity.setIs_del(0);
		entity.getMap().put("user_name_like", user_name_like);

		Integer recordCount = getFacade().getUserInfoService().getUserInfoCount(entity);
		pager.init(recordCount.longValue(), 10, pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<UserInfo> entityList = super.getFacade().getUserInfoService().getUserInfoPaginatedList(entity);
		request.setAttribute("entityList", entityList);
		return new ActionForward("/index/CsAjax/choose_userInfo.jsp");
	}

	public ActionForward getXians(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='../../login.shtml'}");
			return null;
		}

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String servicecenter_name_like = (String) dynaBean.get("servicecenter_name_like");
		ServiceCenterInfo entity = new ServiceCenterInfo();
		super.copyProperties(entity, form);
		entity.setIs_del(0);
		entity.getMap().put("servicecenter_name_like", servicecenter_name_like);

		Integer recordCount = getFacade().getServiceCenterInfoService().getServiceCenterInfoCount(entity);
		pager.init(recordCount.longValue(), 10, pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<ServiceCenterInfo> entityList = super.getFacade().getServiceCenterInfoService()
				.getServiceCenterInfoPaginatedList(entity);
		request.setAttribute("entityList", entityList);
		return new ActionForward("/index/CsAjax/getXians.jsp");
	}

	public ActionForward getCuns(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='../../login.shtml'}");
			return null;
		}

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String village_name_like = (String) dynaBean.get("village_name_like");
		VillageInfo entity = new VillageInfo();
		super.copyProperties(entity, form);
		entity.setIs_del(0);
		entity.getMap().put("village_name_like", village_name_like);

		Integer recordCount = getFacade().getVillageInfoService().getVillageInfoCount(entity);
		pager.init(recordCount.longValue(), 10, pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<VillageInfo> entityList = super.getFacade().getVillageInfoService().getVillageInfoPaginatedList(entity);
		request.setAttribute("entityList", entityList);
		return new ActionForward("/index/CsAjax/getCuns.jsp");
	}

}
