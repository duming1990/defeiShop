package com.ebiz.webapp.web.struts;

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

import com.ebiz.webapp.domain.BaseProvince;
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
 * @author Liu, Jia
 * @version 2016-03-26
 */
public class IndexShoppingNoCarAction extends BasePayAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.step1(mapping, form, request, response);
	}

	public ActionForward step1(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String comm_id = (String) dynaBean.get("comm_id");
		String pd_count = (String) dynaBean.get("pd_count");
		String comm_tczh_id = (String) dynaBean.get("comm_tczh_id");
		String showAddress = (String) dynaBean.get("showAddress");
		String shipping_address_id = (String) dynaBean.get("shipping_address_id");

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}

		if (!GenericValidator.isInt(comm_id) || !GenericValidator.isInt(pd_count)
				|| !GenericValidator.isInt(comm_tczh_id)) {
			String msg = "参数有误！";
			super.renderJavaScript(response, "alert('" + msg + "');history.back();");
			return null;
		}
		if (StringUtils.isNotBlank(showAddress)) {
			request.setAttribute("showAddress", true);
		}
		if (StringUtils.isNotBlank(shipping_address_id)) {
			request.setAttribute("shipping_address_id", shipping_address_id);
		}

		saveToken(request);

		super.setPublicInfoWithSearchList(request);
		super.setPublicInfoList(request);

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
		String inventoryTip = "有货";
		Integer inventoryCount = 0;
		Boolean lack_inventorty = true;
		BigDecimal totalMoney = new BigDecimal("0");
		CommTczhPrice commTczhPrice = new CommTczhPrice();
		commTczhPrice.setId(Integer.valueOf(comm_tczh_id));
		commTczhPrice = super.getFacade().getCommTczhPriceService().getCommTczhPrice(commTczhPrice);
		if (null != commTczhPrice) {
			if (Integer.valueOf(pd_count) > commTczhPrice.getInventory()) {
				inventoryTip = "库存不足";
				lack_inventorty = false;
			}
			inventoryCount = commTczhPrice.getInventory();
			totalMoney = totalMoney.add((commTczhPrice.getComm_price()).multiply(new BigDecimal(pd_count)));

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

		commInfo.getMap().put("pd_max_count", inventoryCount);
		commInfo.getMap().put("inventoryTip", inventoryTip);

		super.copyProperties(form, commInfo);

		request.setAttribute("stepFlag", 1);
		this.getShippingAddressInfo(request, ui.getId(), shipping_address_id);
		return new ActionForward("/index/IndexShoppingNoCar/step1.jsp");

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
		String showAddress = (String) dynaBean.get("showAddress");

		if (null == pd_count || Integer.valueOf(pd_count) < 1) {
			String msg = "商品数量有误！";
			super.renderJavaScript(response, "alert('" + msg + "');history.back();");
			return null;
		}

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}
		if (StringUtils.isNotBlank(showAddress)) {
			if (StringUtils.isBlank(shipping_address_id)) {
				String msg = "请填写收货信息！";
				super.renderJavaScript(response, "alert('" + msg + "');history.back();");
				return null;
			}
			ShippingAddress adds = new ShippingAddress();
			adds.setId(Integer.valueOf(shipping_address_id));
			adds.setIs_del(0);
			adds = getFacade().getShippingAddressService().getShippingAddress(adds);
			if (null == adds) {
				String msg = "收货信息为空！";
				super.renderJavaScript(response, "alert('" + msg + "');history.back();");
				return null;
			}
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
		if (null != commTczhPrice && Integer.valueOf(pd_count) > commTczhPrice.getInventory()) {
			String msg = "库存不足！";
			super.renderJavaScript(response, "alert('" + msg + "');history.back();");
			return null;
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

		orderInfoDetails.setOrder_type(Keys.OrderType.ORDER_TYPE_10.getIndex());
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
		orderInfo.setReal_pay_money(orderInfo.getOrder_money());
		orderInfo.setNo_dis_money(order_money);
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

		orderInfo.setOrder_type(Keys.OrderType.ORDER_TYPE_10.getIndex());
		orderInfo.setPay_platform(Keys.PayPlatform.PC.getIndex());
		orderInfo.setPublish_user_id(commInfo.getAdd_user_id());

		if (StringUtils.isNotBlank(showAddress)) {
			// 保存收货地址信息到订单表
			ShippingAddress address = new ShippingAddress();
			address.setId(Integer.valueOf(shipping_address_id));
			address = super.getFacade().getShippingAddressService().getShippingAddress(address);
			if (null != address) {
				super.copyProperties(orderInfo, address);
				orderInfo.setId(null);
			}
			orderInfo.setOrder_type(Keys.OrderType.ORDER_TYPE_7.getIndex());
		}

		orderInfoList.add(orderInfo);

		OrderInfo entity = new OrderInfo();
		entity.setOrderInfoList(orderInfoList);
		entity.getMap().put("payOrder", "true"); // 订单支付
		entity.setAdd_user_id(ui.getId());

		if (StringUtils.isNotBlank(showAddress)) {
			entity.setOrder_type(Keys.OrderType.ORDER_TYPE_7.getIndex());
		} else {
			entity.setOrder_type(Keys.OrderType.ORDER_TYPE_10.getIndex());
		}
		entity.getMap().put("update_comm_info_saleCountAndInventory", true);

		UserInfo userInfo = new UserInfo();
		userInfo.setId(ui.getId());
		userInfo = super.getFacade().getUserInfoService().getUserInfo(userInfo);
		entity.getMap().put("userInfo", userInfo);
		int insertFlag = getFacade().getOrderInfoService().createOrderInfo(entity);
		if (insertFlag > 0) {
			super.renderJavaScript(response, "location.href='IndexShoppingNoCar.do?method=selectPayType&trade_index="
					+ creatTradeIndex + "'");
			return null;
		} else if (insertFlag == -1) {
			String msg = "商品库存不足，请联系商家！";
			super.renderJavaScript(response, "alert('" + msg + "');history.back();");
			return null;
		} else if (insertFlag == -10) {
			String msg = "订单数量异常，请联系管理员！";
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
		String trade_index = (String) dynaBean.get("trade_index");
		String pay_type = (String) dynaBean.get("pay_type");

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}
		if (StringUtils.isBlank(trade_index)) {
			String msg = "参数错误";
			super.showMsgForManager(request, response, msg);
			return null;
		}
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
						super.renderJavaScript(response, "alert('" + msg + "');history.back();");
						return null;
					}
					if (orderInfo.getOrder_state().intValue() != 0) {
						String msg = "订单" + trade_index2 + "状态已更新！";
						super.renderJavaScript(response, "alert('" + msg + "');history.back();");
						return null;
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

		super.setPublicInfoWithSearchList(request);
		super.setPublicInfoList(request);
		request.setAttribute("stepFlag", 2);
		request.setAttribute("trade_index", trade_index);

		UserInfo userInfo = super.getUserInfo(ui.getId());
		BigDecimal dianzibi_to_rmb = super.BiToBi(userInfo.getBi_dianzi(),
				Keys.BASE_DATA_ID.BASE_DATA_ID_904.getIndex());
		request.setAttribute("dianzibi_to_rmb", dianzibi_to_rmb);

		request.setAttribute("canPay", true);
		if (StringUtils.isNotBlank(pay_type) && pay_type.equals(String.valueOf(Keys.PayType.PAY_TYPE_0.getIndex()))) {
			if (order_money.compareTo(dianzibi_to_rmb.add(userInfo.getBi_fuxiao())) > 0) {
				request.setAttribute("canPay", false);
			} else {
				request.setAttribute("canPay", true);
			}
		}
		saveToken(request);

		BigDecimal huoKuanBi_to_rmb = super.BiToBi(userInfo.getBi_huokuan(),
				Keys.BASE_DATA_ID.BASE_DATA_ID_905.getIndex());
		request.setAttribute("huoKuanBi_to_rmb", huoKuanBi_to_rmb);
		request.setAttribute("canPay", true);

		return new ActionForward("/index/IndexShoppingNoCar/step2.jsp");
	}

	public ActionForward step3(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String out_trade_no = (String) dynaBean.get("out_trade_no");
		String trade_index = (String) dynaBean.get("trade_index");
		String pay_password = (String) dynaBean.get("pay_password");
		String pay_type = (String) dynaBean.get("pay_type");
		String is_test = (String) dynaBean.get("is_test");

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}
		if (StringUtils.isBlank(out_trade_no) || StringUtils.isBlank(pay_password) || StringUtils.isBlank(pay_type)) {
			String msg = "参数有误，请联系管理员！";
			super.renderJavaScript(response, "alert('" + msg + "');history.back();");
			return null;
		}

		ui = super.getUserInfo(ui.getId());

		if (null == ui.getPassword_pay()) {
			String msg = "未设置支付密码！";
			super.renderJavaScript(response, "alert('" + msg + "');history.back();");
			return null;
		}

		if (!EncryptUtilsV2.MD5Encode(pay_password.trim()).equals(ui.getPassword_pay())) {
			String msg = "支付密码输入有误！";
			super.renderJavaScript(response, "alert('" + msg + "');history.back();");
			return null;
		}
		// 更新订单状态

		if (!isTokenValid(request)) {
			String ctx = super.getCtxPath(request);
			return new ActionForward(ctx + "/IndexShoppingNoCar.do?method=selectPayType&trade_index=" + trade_index
					+ "&pay_type=" + pay_type, true);
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

			super.setPublicInfoWithSearchList(request);
			super.setPublicInfoList(request);
			request.setAttribute("stepFlag", 3);

			return new ActionForward("/index/IndexShoppingNoCar/step3.jsp");
		} else {
			super.renderJavaScript(response, "location.href='" + super.getCtxPath(request)
					+ "/IndexPayment.do?out_trade_no=" + out_trade_no + "&pay_type=" + pay_type + "&is_test=" + is_test
					+ "';");
			return null;
		}
	}

	public String getProvinceName(Integer p_index) {
		String p_name = "";
		BaseProvince baseProvince = new BaseProvince();
		baseProvince.setP_index(p_index.longValue());
		baseProvince.setIs_del(0);
		baseProvince = getFacade().getBaseProvinceService().getBaseProvince(baseProvince);
		if (null != baseProvince) {
			p_name = baseProvince.getP_name();
		}
		return p_name;
	}

	public ActionForward addAddr(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}
		saveToken(request);

		dynaBean.set("rel_phone", ui.getMobile());
		if (null != ui.getP_index()) {
			super.setMprovinceAndcityAndcountryToFrom(dynaBean, ui.getP_index());
		}

		return new ActionForward("/index/IndexShoppingNoCar/addAddr.jsp");
	}

}
