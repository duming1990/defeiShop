package com.ebiz.webapp.web.struts.m;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.webapp.domain.CartInfo;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.CommTczhAttribute;
import com.ebiz.webapp.domain.CommTczhPrice;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.OrderInfoDetails;
import com.ebiz.webapp.domain.OrderMergerInfo;
import com.ebiz.webapp.domain.ShippingAddress;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.EncryptUtilsV2;

/**
 * @author Wu,Yang
 * @version 2010-8-24
 */
public class MMyNoCartInfoAction extends MBaseWebAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.step1(mapping, form, request, response);
	}

	public ActionForward step1(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		request.setAttribute("header_title", "提交订单");

		DynaBean dynaBean = (DynaBean) form;
		String comm_id = (String) dynaBean.get("comm_id");
		String comm_tczh_id = (String) dynaBean.get("comm_tczh_id");
		String shipping_address_id = (String) dynaBean.get("shipping_address_id");

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}

		if (!GenericValidator.isInt(comm_id) || !GenericValidator.isInt(comm_tczh_id)) {
			String msg = "参数有误！";
			super.renderJavaScript(response, "alert('" + msg + "');history.back();");
			return null;
		}

		saveToken(request);

		super.setPublicInfoWithSearchList(request);
		super.setPublicInfoList(request);

		CommInfo commInfo = new CommInfo();
		commInfo.setId(Integer.valueOf(comm_id));
		commInfo = super.getFacade().getCommInfoService().getCommInfo(commInfo);

		request.setAttribute("commInfo", commInfo);// 商品类型

		if ((commInfo == null) || ((commInfo.getIs_del().intValue() != 0))
				|| ((commInfo.getAudit_state().intValue() != 1)) || ((commInfo.getIs_sell().intValue() == 0))
				|| ((commInfo.getIs_sell().intValue() == 1) && (commInfo.getDown_date().compareTo(new Date()) < 0))) {
			String msg = "商品已经下架！";
			super.renderJavaScript(response, "alert('" + msg + "');history.back();");
			return null;
		}
		String inventoryTip = "有货";
		Integer inventoryCount = 0;
		Boolean lack_inventorty = true;
		BigDecimal totalMoney = new BigDecimal("0");
		CommTczhPrice commTczhPrice = new CommTczhPrice();
		commTczhPrice.setId(Integer.valueOf(comm_tczh_id));
		commTczhPrice = super.getFacade().getCommTczhPriceService().getCommTczhPrice(commTczhPrice);
		if (null != commTczhPrice) {

			inventoryCount = commTczhPrice.getInventory();
			totalMoney = totalMoney.add((commTczhPrice.getComm_price()));

			CommTczhAttribute commTczhAttribute = new CommTczhAttribute();
			commTczhAttribute.setComm_id(commInfo.getId().toString());
			commTczhAttribute.setComm_tczh_id(Integer.valueOf(comm_tczh_id));
			commTczhAttribute.setOrder_value(0);
			commTczhAttribute = super.getFacade().getCommTczhAttributeService().getCommTczhAttribute(commTczhAttribute);
			request.setAttribute("commTczhAttribute", commTczhAttribute);

		}

		if (null != commInfo.getOwn_entp_id()) {
			EntpInfo entpInfo = new EntpInfo();
			entpInfo.setId(commInfo.getOwn_entp_id());
			entpInfo = getFacade().getEntpInfoService().getEntpInfo(entpInfo);
			request.setAttribute("entpInfo", entpInfo);
		}

		request.setAttribute("commTczhPrice", commTczhPrice);
		request.setAttribute("totalMoney", totalMoney);
		request.setAttribute("lack_inventorty", lack_inventorty);

		dynaBean.set("pd_price", commTczhPrice.getComm_price());
		// dynaBean.set("pd_count", 1);

		commInfo.getMap().put("pd_max_count", inventoryCount);
		commInfo.getMap().put("inventoryTip", inventoryTip);

		super.copyProperties(form, commInfo);

		request.setAttribute("stepFlag", 1);

		this.getShippingAddressInfo(request, ui.getId(), shipping_address_id);

		return new ActionForward("/MMyNoCartInfo/step1.jsp");
	}

	public ActionForward step2(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (!isTokenValid(request)) {
			return unspecified(mapping, form, request, response);
		}
		resetToken(request);
		DynaBean dynaBean = (DynaBean) form;
		String comm_id = (String) dynaBean.get("comm_id");
		String pd_count = (String) dynaBean.get("pd_count");
		String comm_tczh_id = (String) dynaBean.get("comm_tczh_id");
		String shipping_address_id = (String) dynaBean.get("shipping_address_id");

		logger.info("===comm_tczh_id:" + comm_tczh_id);
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}

		CommInfo commInfo = new CommInfo();
		commInfo.setId(Integer.valueOf(comm_id));
		commInfo = super.getFacade().getCommInfoService().getCommInfo(commInfo);
		if ((commInfo == null) || ((commInfo.getIs_del().intValue() != 0))
				|| ((commInfo.getAudit_state().intValue() != 1)) || ((commInfo.getIs_sell().intValue() == 0))
				|| ((commInfo.getIs_sell().intValue() == 1) && (commInfo.getDown_date().compareTo(new Date()) < 0))) {
			String msg = "商品已经下架！";
			super.renderJavaScript(response, "alert('" + msg + "');history.back();");
			return null;
		}

		CommTczhPrice commTczhPrice = new CommTczhPrice();
		commTczhPrice.setId(Integer.valueOf(comm_tczh_id));
		commTczhPrice = super.getFacade().getCommTczhPriceService().getCommTczhPrice(commTczhPrice);
		if (null != commTczhPrice) {
			if (Integer.valueOf(pd_count) > commTczhPrice.getInventory()) {
				String msg = "库存不足！";
				super.renderJavaScript(response, "alert('" + msg + "');history.back();");
				return null;
			}
		}
		/*
		 * if (null == commInfo.getOwn_entp_id()) { String msg = "参数有误！"; super.renderJavaScript(response, "alert('" +
		 * msg + "');history.back();"); return null; }
		 */

		/*
		 * EntpInfo entpInfo = super.getEntpInfo(commInfo.getOwn_entp_id()); if (null == entpInfo) { String msg =
		 * "关联企业已被删除！"; super.renderJavaScript(response, "alert('" + msg + "');history.back();"); return null; }
		 */

		CommTczhAttribute commTczhAttribute = new CommTczhAttribute();
		commTczhAttribute.setComm_id(commInfo.getId().toString());
		commTczhAttribute.setComm_tczh_id(Integer.valueOf(comm_tczh_id));
		commTczhAttribute.setOrder_value(0);
		commTczhAttribute = super.getFacade().getCommTczhAttributeService().getCommTczhAttribute(commTczhAttribute);

		List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();

		List<OrderInfoDetails> orderInfoDetailsList = new ArrayList<OrderInfoDetails>();
		Integer order_sum_num = 0;
		BigDecimal order_money = new BigDecimal("0");
		BigDecimal sum_matflow_price = new BigDecimal("0");
		BigDecimal sum_comm_weight = new BigDecimal("0");

		OrderInfoDetails orderInfoDetails = new OrderInfoDetails();
		order_sum_num += Integer.valueOf(pd_count);
		Double pd_price = commTczhPrice.getComm_price().doubleValue();
		sum_comm_weight = commInfo.getComm_weight();
		order_money = order_money.add(new BigDecimal(pd_price).multiply(new BigDecimal(order_sum_num)));
		if (commInfo.getComm_type() == 20) {
			orderInfoDetails.setOrder_type(Keys.OrderType.ORDER_TYPE_100.getIndex());
		} else {
			orderInfoDetails.setOrder_type(Keys.OrderType.ORDER_TYPE_10.getIndex());
		}
		orderInfoDetails.setCls_id(commInfo.getCls_id());
		orderInfoDetails.setCls_name(commInfo.getCls_name());
		orderInfoDetails.setPd_id(commInfo.getPd_id());
		orderInfoDetails.setPd_name(commInfo.getPd_name());
		orderInfoDetails.setGood_count(order_sum_num);
		orderInfoDetails.setComm_id(commInfo.getId());
		orderInfoDetails.setComm_name(commInfo.getComm_name());
		orderInfoDetails.setComm_tczh_id(Integer.valueOf(comm_tczh_id));
		orderInfoDetails.setMatflow_price(new BigDecimal(0));
		if (null != commTczhAttribute) {
			orderInfoDetails.setComm_tczh_name(commTczhAttribute.getAttr_name());
		}
		orderInfoDetails.setHuizhuan_rule(commInfo.getFanxian_rule());
		orderInfoDetails.setGood_price(new BigDecimal(pd_price));
		orderInfoDetails.setGood_sum_price(new BigDecimal(pd_price * order_sum_num));
		orderInfoDetails.setEntp_id(commInfo.getOwn_entp_id());
		orderInfoDetailsList.add(orderInfoDetails);

		OrderInfo orderInfo = new OrderInfo();
		super.copyProperties(orderInfo, form);
		String creatTradeIndex = this.creatTradeIndex();
		orderInfo.setTrade_index(creatTradeIndex);

		orderInfo.setOrderInfoDetailsList(orderInfoDetailsList);

		orderInfo.setOrder_num(order_sum_num);
		orderInfo.setOrder_money(order_money);
		orderInfo.setReal_pay_money(order_money);
		orderInfo.setOrder_state(0);
		orderInfo.setMatflow_price(sum_matflow_price);
		orderInfo.setOrder_weight(sum_comm_weight);

		orderInfo.setOrder_date(new Date());
		orderInfo.setAdd_date(new Date());
		// 订单默认7天后失效
		orderInfo.setEnd_date(DateUtils.addDays(new Date(), Keys.ORDER_END_DATE));
		orderInfo.setAdd_user_id(ui.getId());
		orderInfo.setAdd_user_name(ui.getUser_name());
		/*
		 * orderInfo.setEntp_id(entpInfo.getId()); orderInfo.setEntp_name(entpInfo.getEntp_name());
		 */

		if (null != ui.getMobile()) {
			orderInfo.setRel_phone(ui.getMobile());
		}
		orderInfo.setRel_name(ui.getUser_name());
		// orderInfo.setOrder_type(Keys.OrderType.ORDER_TYPE_10.getIndex());
		orderInfo.setPay_platform(Keys.PayPlatform.WAP.getIndex());

		Boolean isApp = super.isApp(request);
		if (isApp) {
			String jugdeAppPt = super.jugdeAppPt(request);
			if (jugdeAppPt.contains("android")) {
				orderInfo.setPay_platform(Keys.PayPlatform.APP_ANDROID.getIndex());
			}
			if (jugdeAppPt.contains("iphone")) {
				orderInfo.setPay_platform(Keys.PayPlatform.APP_IPHONE.getIndex());
			}
		}

		orderInfoList.add(orderInfo);

		OrderInfo entity = new OrderInfo();
		if (commInfo.getComm_type() == 20) {
			super.copyProperties(entity, orderInfo);
			// entity.getMap().put("payOrder", "pintuan");
			if (StringUtils.isNotBlank(shipping_address_id)) {
				// 保存收货地址信息到订单表
				ShippingAddress address = new ShippingAddress();
				address.setId(Integer.valueOf(shipping_address_id));
				address = super.getFacade().getShippingAddressService().getShippingAddress(address);
				if (null != address) {// 拼团订单收货地址
					entity.setRel_addr(address.getRel_addr());
					entity.setRel_city(address.getRel_city());
					entity.setRel_province(address.getRel_province());
					entity.setRel_region(address.getRel_region());
				}
			}
		} else {
			entity.getMap().put("payOrder", "true"); // 订单支付
		}
		entity.setOrderInfoList(orderInfoList);

		entity.setAdd_user_id(ui.getId());
		if (commInfo.getComm_type() == 20) {
			entity.setOrder_type(Keys.OrderType.ORDER_TYPE_100.getIndex());
		} else {
			entity.setOrder_type(Keys.OrderType.ORDER_TYPE_10.getIndex());
		}
		// entity.setOrder_type(Keys.OrderType.ORDER_TYPE_10.getIndex());
		entity.getMap().put("update_comm_info_saleCountAndInventory", true);

		UserInfo userInfo = new UserInfo();
		userInfo.setId(ui.getId());
		userInfo = super.getFacade().getUserInfoService().getUserInfo(userInfo);
		entity.getMap().put("userInfo", userInfo);
		int insertFlag = getFacade().getOrderInfoService().createOrderInfo(entity);
		if (insertFlag > 0) {
			super.renderJavaScript(response, "location.href='MMyCartInfo.do?method=selectPayType&trade_index="
					+ creatTradeIndex + "'");
			return null;
		} else if (insertFlag == -1) {
			String msg = "商品库存不足，请联系商家！";
			super.renderJavaScript(response, "alert('" + msg + "');history.back();");
			return null;
		} else {
			String msg = "保存订单有误，请联系管理员！";
			super.renderJavaScript(response, "alert('" + msg + "');history.back();");
			return null;
		}
	}

	public ActionForward selectPayType(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		request.setAttribute("header_title", "支付方式");

		String trade_index = (String) dynaBean.get("trade_index");
		String pay_type = (String) dynaBean.get("pay_type");
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return super.showTipNotLogin(mapping, form, request, response, msg);
		}
		if (StringUtils.isBlank(trade_index)) {
			String msg = "参数错误";
			super.showTipMsg(mapping, form, request, response, msg);
			return null;
		}

		request.setAttribute("isWeixin", super.isWeixin(request));
		request.setAttribute("isApp", super.isApp(request));

		trade_index = trade_index.trim();
		List<OrderInfo> orderList = new ArrayList<OrderInfo>();
		String[] trade_indexs = trade_index.split(",");
		if (ArrayUtils.isNotEmpty(trade_indexs) && (trade_indexs.length > 0)) {
			for (String trade_index2 : trade_indexs) {
				if (StringUtils.isNotBlank(trade_index2)) {
					OrderInfo orderInfo = new OrderInfo();
					orderInfo.setAdd_user_id(ui.getId());
					orderInfo.setTrade_index(trade_index2);
					orderInfo = getFacade().getOrderInfoService().getOrderInfo(orderInfo);
					if (null == orderInfo) {
						String msg = "订单不存在！";
						return super.showTipMsg(mapping, form, request, response, msg);
					}
					if (orderInfo.getOrder_state().intValue() != 0) {
						String msg = "订单" + trade_index2 + "状态已更新！";
						return super.showTipMsg(mapping, form, request, response, msg);
					}
					orderList.add(orderInfo);
				}
			}
		}
		BigDecimal order_money = new BigDecimal("0.0");
		if (orderList.size() > 0) {
			String out_trade_no = creatTradeIndex();
			OrderInfo orderInfo = new OrderInfo();

			OrderMergerInfo orderMergerInfo = new OrderMergerInfo();
			orderMergerInfo.setOut_trade_no(out_trade_no);
			orderMergerInfo.setPar_id(0);
			orderMergerInfo.setAdd_user_id(ui.getId());
			orderMergerInfo.setAdd_date(new Date());
			orderMergerInfo.setPay_state(new Integer(0));

			Integer id = getFacade().getOrderMergerInfoService().createOrderMergerInfo(orderMergerInfo);
			if (null != id) {
				for (int i = 0; i < orderList.size(); i++) {

					orderInfo = orderList.get(i);

					OrderMergerInfo orderMergerInfo2 = new OrderMergerInfo();
					orderMergerInfo2.setPar_id(id);
					orderMergerInfo2.setTrade_index(orderInfo.getTrade_index());
					orderMergerInfo2.setEntp_id(orderInfo.getEntp_id());
					orderMergerInfo2.setAdd_user_id(orderInfo.getAdd_user_id());
					orderMergerInfo2.setAdd_date(new Date());
					orderMergerInfo2.setIs_freeship(orderInfo.getIs_freeship());
					orderMergerInfo2.setFlow_type(new Integer(0));
					orderMergerInfo2.setIs_price_modify(orderInfo.getIs_price_modify());
					orderMergerInfo2.setPay_state(orderInfo.getOrder_state());
					orderMergerInfo2.getMap().put("updateOrderInfo", "true");

					getFacade().getOrderMergerInfoService().createOrderMergerInfo(orderMergerInfo2);
					order_money = order_money.add(orderInfo.getOrder_money());
				}
			}
			request.setAttribute("orderInfoList", orderList);
			request.setAttribute("out_trade_no", out_trade_no);
			request.setAttribute("order_money", order_money);
		}

		UserInfo userInfo = super.getUserInfo(ui.getId());
		BigDecimal dianzibi_to_rmb = super.BiToBi(userInfo.getBi_dianzi(),
				Keys.BASE_DATA_ID.BASE_DATA_ID_904.getIndex());
		request.setAttribute("dianzibi_to_rmb", dianzibi_to_rmb);
		request.setAttribute("userInfo", userInfo);

		/*
		 * if (StringUtils.isNotBlank(pay_type) && pay_type.equals(String.valueOf(Keys.PayType.PAY_TYPE_0.getIndex())))
		 * { if (order_money.compareTo(dianzibi_to_rmb) > 0) { request.setAttribute("canPay", false); } else { } }
		 * request.setAttribute("canPay", true);
		 */
		/*
		 * BigDecimal huoKuanBi_to_rmb = super.BiToBi(userInfo.getBi_huokuan(),
		 * Keys.BASE_DATA_ID.BASE_DATA_ID_905.getIndex()); request.setAttribute("huoKuanBi_to_rmb", huoKuanBi_to_rmb);
		 * if (StringUtils.isNotBlank(pay_type)) { if (order_money.compareTo(huoKuanBi_to_rmb) > 0) {
		 * request.setAttribute("canPay", false); } else { request.setAttribute("canPay", true); } }
		 */

		saveToken(request);
		request.setAttribute("canPay", true);
		request.setAttribute("isPayDianzi", super.getSysSetting("isPayDianzi"));
		return new ActionForward("/MMyCartInfo/step2.jsp");
	}

	public ActionForward step3(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		request.setAttribute("header_title", "支付成功");

		DynaBean dynaBean = (DynaBean) form;
		String out_trade_no = (String) dynaBean.get("out_trade_no");
		String pay_password = (String) dynaBean.get("pay_password");
		String pay_type = (String) dynaBean.get("pay_type");

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return super.showTipNotLogin(mapping, form, request, response, msg);
		}
		if (StringUtils.isBlank(out_trade_no) || StringUtils.isBlank(pay_password) || StringUtils.isBlank(pay_type)) {
			String msg = "参数有误，请联系管理员！";
			return super.showTipMsg(mapping, form, request, response, msg);
		}

		ui = super.getUserInfo(ui.getId());

		if (null == ui.getPassword_pay()) {
			String msg = "未设置支付密码！";
			return super.showTipMsg(mapping, form, request, response, msg);
		}

		if (!EncryptUtilsV2.MD5Encode(pay_password.trim()).equals(ui.getPassword_pay())) {
			String msg = "支付密码输入有误！";
			return super.showTipMsg(mapping, form, request, response, msg);
		}
		// 更新订单状态

		if (!isTokenValid(request)) {
			return unspecified(mapping, form, request, response);
		}
		resetToken(request);

		if (pay_type.equals("0") || pay_type.equals("4")) {

			int count = super.modifyOrderInfo(request, out_trade_no, out_trade_no, Integer.valueOf(pay_type), null);
			if (count <= 0) {
				if (count == -2) {
					request.setAttribute("pay_state", -2);
				} else {
					request.setAttribute("pay_state", -1);
				}
			} else {
				request.setAttribute("pay_state", 1);
			}
			request.setAttribute("hasGoHome", true);

			return new ActionForward("/MMyCartInfo/step3.jsp");
		} else {
			super.renderJavaScript(response, "location.href='" + super.getCtxPath(request)
					+ "/m/MIndexPayment.do?out_trade_no=" + out_trade_no + "&pay_type=" + pay_type + "';");
			return null;
		}
	}

	public void setEntpInfoListToForm(HttpServletRequest request, Integer user_id) {

		CartInfo cartInfo = new CartInfo();
		cartInfo.setIs_del(0);
		cartInfo.setUser_id(user_id);
		cartInfo.setCart_type(Keys.CartType.CART_TYPE_10.getIndex());
		String tip = "";
		List<CartInfo> cartInfoList = getFacade().getCartInfoService().getCartInfoList(cartInfo);
		BigDecimal totalMoney = new BigDecimal("0");
		Boolean lack_inventorty = true;
		for (CartInfo ci : cartInfoList) {
			CommInfo commInfo = new CommInfo();
			commInfo.setId(ci.getComm_id());
			commInfo = super.getFacade().getCommInfoService().getCommInfo(commInfo);

			String inventoryTip = "有货";
			Integer inventoryCount = 0;

			if ((commInfo == null) || ((commInfo.getIs_del().intValue() != 0))
					|| ((commInfo.getAudit_state().intValue() != 1)) || ((commInfo.getIs_sell().intValue() == 0))
					|| ((commInfo.getIs_sell().intValue() == 1) && (commInfo.getDown_date().compareTo(new Date()) < 0))) {
				cartInfo = new CartInfo();
				cartInfo.setId(ci.getId());
				super.getFacade().getCartInfoService().removeCartInfo(cartInfo);
				tip = tip + "<br/>" + ci.getComm_name() + "已经下架！";
			} else {

				CommTczhPrice commTczhPrice = new CommTczhPrice();
				commTczhPrice.setId(ci.getComm_tczh_id());
				commTczhPrice = super.getFacade().getCommTczhPriceService().getCommTczhPrice(commTczhPrice);
				if (null != commTczhPrice) {
					if (ci.getPd_count() > commTczhPrice.getInventory()) {
						inventoryTip = "库存不足";
						lack_inventorty = false;
					}
					inventoryCount = commTczhPrice.getInventory();
				}
			}
			ci.getMap().put("pd_max_count", inventoryCount);
			ci.getMap().put("inventoryTip", inventoryTip);

			totalMoney = totalMoney.add((ci.getPd_price().add(ci.getService_single_money())).multiply(new BigDecimal(ci
					.getPd_count())));

		}

		request.setAttribute("totalMoney", totalMoney);
		request.setAttribute("lack_inventorty", lack_inventorty);
		request.setAttribute("tip", tip);
		request.setAttribute("cartInfoList", cartInfoList);
	}

	public ActionForward addressList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		request.setAttribute("header_title", "选择收货地址");
		DynaBean dynaBean = (DynaBean) form;
		String shipping_address_id = (String) dynaBean.get("shipping_address_id");
		String cart_ids = (String) dynaBean.get("cart_ids");
		String isYs = (String) dynaBean.get("isYs");

		String comm_id = request.getParameter("comm_id");
		String comm_tczh_id = request.getParameter("comm_tczh_id");
		;
		request.setAttribute("comm_id", comm_id);
		request.setAttribute("comm_tczh_id", comm_tczh_id);

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return super.showTipNotLogin(mapping, form, request, response, msg);
		}

		this.getShippingAddressInfo(request, ui.getId(), shipping_address_id);
		if (StringUtils.isNotBlank(cart_ids)) {
			request.setAttribute("cart_ids", cart_ids);
		}
		request.setAttribute("isYs", isYs);
		return new ActionForward("/MMyNoCartInfo/addrList.jsp");
	}

	public ActionForward addAddr(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		request.setAttribute("header_title", "添加地址");

		String comm_id = request.getParameter("comm_id");
		String comm_tczh_id = request.getParameter("comm_tczh_id");

		request.setAttribute("comm_id", comm_id);
		request.setAttribute("comm_tczh_id", comm_tczh_id);

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return super.showTipNotLogin(mapping, form, request, response, msg);
		}
		saveToken(request);

		dynaBean.set("rel_phone", ui.getMobile());
		dynaBean.set("rel_name", ui.getReal_name());
		// if (null != ui.getP_index()) {
		// super.setMprovinceAndcityAndcountryToFrom(dynaBean, ui.getP_index());
		// }

		return new ActionForward("/MMyNoCartInfo/addAddr.jsp");
	}

}
