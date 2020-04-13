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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ebiz.webapp.domain.BaseClass;
import com.ebiz.webapp.domain.BaseData;
import com.ebiz.webapp.domain.BaseProvince;
import com.ebiz.webapp.domain.CartInfo;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.CommTczhPrice;
import com.ebiz.webapp.domain.DiscountStores;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.OrderInfoDetails;
import com.ebiz.webapp.domain.OrderMergerInfo;
import com.ebiz.webapp.domain.ShippingAddress;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.YhqInfo;
import com.ebiz.webapp.domain.YhqInfoSon;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.EncryptUtilsV2;

/**
 * @author Wu,Yang
 * @version 2013-03-29
 */
public class IndexShoppingCarAction extends BasePayAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.step0(mapping, form, request, response);
	}

	public ActionForward step0(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}
		UserInfo userInfo = super.getUserInfo(ui.getId());
		if (null == userInfo) {
			String msg = "用户不存在！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}
		request.setAttribute("userInfo", userInfo);

		super.setPublicInfoWithSearchList(request);
		super.setPublicInfoList(request);
		this.setEntpInfoListToFormStep0(request, ui.getId());

		request.setAttribute("stepFlag", 0);
		super.setWhereCartInfoStep0(request);

		return new ActionForward("/index/IndexShoppingCar/step0.jsp");

	}

	public ActionForward step1(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String shipping_address_id = (String) dynaBean.get("shipping_address_id");

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}
		saveToken(request);
		request.setAttribute("ui", ui);
		super.setPublicInfoWithSearchList(request);
		super.setPublicInfoList(request);

		CartInfo cartInfo = new CartInfo();
		cartInfo.setIs_del(0);
		cartInfo.setUser_id(ui.getId());
		cartInfo.setCart_type(Keys.CartType.CART_TYPE_10.getIndex());
		List<CartInfo> cartInfoList = getFacade().getCartInfoService().getCartInfoList(cartInfo);
		if (null != cartInfoList && cartInfoList.size() > 0) {
			String select_address_id = this.getShippingAddressInfo(request, ui.getId(), shipping_address_id);
			this.setEntpInfoListToForm(request, response, ui.getId(), dynaBean, select_address_id);
		}

		request.setAttribute("stepFlag", 1);
		return new ActionForward("/index/IndexShoppingCar/step1.jsp");

	}

	public String getShippingAddressInfo(HttpServletRequest request, Integer user_id, String shipping_address_id) {
		ShippingAddress shippingAddress = new ShippingAddress();
		shippingAddress.setAdd_user_id(user_id);
		shippingAddress.setIs_del(0);
		List<ShippingAddress> shippingAddressList = getFacade().getShippingAddressService().getShippingAddressList(
				shippingAddress);

		ShippingAddress dftAddress = new ShippingAddress();

		if ((null != shippingAddressList) && (shippingAddressList.size() > 0)) {
			int i = 0, j = 0;
			for (ShippingAddress sa : shippingAddressList) {

				String province = this.getProvinceName(Long.valueOf(sa.getRel_province()));
				String city = this.getProvinceName(Long.valueOf(sa.getRel_city()));
				String region = this.getProvinceName(Long.valueOf(sa.getRel_region()));

				sa.getMap().put("province", province);
				sa.getMap().put("city", city);
				sa.getMap().put("region", region);

				if (null != sa.getRel_region_four()) {
					String regionFour = this.getProvinceName(Long.valueOf(sa.getRel_region_four()));
					sa.getMap().put("regionFour", regionFour);
					sa.getMap().put(
							"full_addr",
							province.concat(" ").concat(city).concat(" ").concat(region).concat(" ").concat(regionFour)
									.concat(" ").concat(sa.getRel_addr()));
				} else {
					sa.getMap().put(
							"full_addr",
							province.concat(" ").concat(city).concat(" ").concat(region).concat(" ")
									.concat(sa.getRel_addr()));
				}

				if (StringUtils.isNotBlank(shipping_address_id)) {
					if (sa.getId().toString().equals(shipping_address_id)) {
						dftAddress = sa;
						j = i;
					}
				} else {
					if (sa.getIs_default() == 1) {
						dftAddress = sa;
						j = i;
					} else {
						dftAddress = shippingAddressList.get(0);
					}
				}
				i++;
			}
			ShippingAddress temp = shippingAddressList.get(j);
			shippingAddressList.remove(j);
			shippingAddressList.add(0, temp);
		}

		request.setAttribute("shippingAddressList", shippingAddressList);

		if (null != dftAddress.getId()) {
			request.setAttribute("p_index", dftAddress.getRel_region());
			request.setAttribute("shipping_address_id", dftAddress.getId());
			request.setAttribute("dftAddress", dftAddress);
			return dftAddress.getId().toString();
		}
		return null;
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

		return new ActionForward("/index/IndexShoppingCar/addAddr.jsp");
	}

	public ActionForward step2(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (!isTokenValid(request)) {
			return unspecified(mapping, form, request, response);
		}
		resetToken(request);
		DynaBean dynaBean = (DynaBean) form;
		String delivery_p_index = (String) dynaBean.get("delivery_p_index");
		String shipping_address_id = (String) dynaBean.get("shipping_address_id");
		String[] cart_ids = request.getParameterValues("cart_ids");
		String[] remarks = request.getParameterValues("remark");
		String is_yue_dikou = (String) dynaBean.get("is_yue_dikou");
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}

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

		List<String> cart_ids_in = new ArrayList<String>();

		if (null != cart_ids && cart_ids.length > 0) {
			for (int i = 0; i < cart_ids.length; i++) {
				if (StringUtils.isNotBlank(cart_ids[i])) {
					cart_ids_in.add(cart_ids[i]);
				}
			}
		}

		Boolean tipFlag = true;
		CartInfo cartInfo = new CartInfo();
		cartInfo.setIs_del(0);
		cartInfo.setUser_id(ui.getId());
		cartInfo.getMap().put("cart_ids_in", StringUtils.join(cart_ids_in, ","));
		cartInfo.setCart_type(Keys.CartType.CART_TYPE_10.getIndex());
		List<CartInfo> cartInfoList = getFacade().getCartInfoService().getCartInfoList(cartInfo);
		for (CartInfo ci : cartInfoList) {
			CommInfo temp = new CommInfo();
			temp.setId(ci.getComm_id());
			temp = super.getFacade().getCommInfoService().getCommInfo(temp);

			if ((temp == null) || (temp.getIs_del().intValue() != 0) || (temp.getAudit_state().intValue() != 1)
					|| ((temp.getIs_sell().intValue() == 0))
					|| ((temp.getIs_sell().intValue() == 1) && (temp.getDown_date().compareTo(new Date()) < 0))) {
				cartInfo = new CartInfo();
				cartInfo.setId(ci.getId());
				super.getFacade().getCartInfoService().removeCartInfo(cartInfo);
				tipFlag = false;
				break;
			}
		}

		if (!tipFlag) {
			return this.step1(mapping, form, request, response);
		}

		List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();
		EntpInfo entpInfo = new EntpInfo();
		entpInfo.setIs_del(0);
		entpInfo.getMap().put("carts_of_entp", ui.getId());
		entpInfo.getMap().put("cart_ids_in", StringUtils.join(cart_ids_in, ","));
		String trade_indexs = "";

		List<EntpInfo> entpInfoList = getFacade().getEntpInfoService().getEntpInfoList(entpInfo);

		if ((null != entpInfoList) && (entpInfoList.size() > 0)) {
			int i = 0;
			for (EntpInfo ei : entpInfoList) {

				BigDecimal sum_matflow_price = new BigDecimal("0");

				BigDecimal card_user_dis_money = new BigDecimal("0");

				CartInfo tempCartInfo = new CartInfo();
				tempCartInfo.setIs_del(0);
				tempCartInfo.setEntp_id(ei.getId());
				tempCartInfo.setUser_id(ui.getId());
				tempCartInfo.setCart_type(Keys.CartType.CART_TYPE_10.getIndex());
				if (null != cart_ids) {
					tempCartInfo.getMap().put("cart_ids_in", StringUtils.join(cart_ids_in, ","));
				}
				List<CartInfo> cartInfoTempList = getFacade().getCartInfoService().getCartInfoList(tempCartInfo);
				if ((cartInfoTempList != null) && (cartInfoTempList.size() > 0)) {

					// 新版本计算运费模板
					sum_matflow_price = calMatflowMoneyNew(0, Integer.valueOf(delivery_p_index), ui.getId(),
							Integer.valueOf(shipping_address_id), ei.getId(), cartInfoTempList,
							StringUtils.join(cart_ids_in, ","));

					if (sum_matflow_price.compareTo(new BigDecimal(0)) < 0) {// 证明有商品配送不到
						String msg = "该商品无法送达！";
						return showTipMsg(mapping, form, request, response, msg);
					}

					List<OrderInfoDetails> orderInfoDetailsList = new ArrayList<OrderInfoDetails>();
					Integer order_sum_num = 0;
					BigDecimal order_money = new BigDecimal("0");
					BigDecimal sum_comm_weight = new BigDecimal("0");
					BigDecimal sum_red_money = new BigDecimal("0");
					BigDecimal sum_yhq_money = new BigDecimal("0");
					for (CartInfo ci : cartInfoTempList) {

						CommInfo commInfo = super.getCommInfoOnlyById(ci.getComm_id());

						OrderInfoDetails orderInfoDetails = new OrderInfoDetails();
						order_sum_num += ci.getPd_count();
						Double pd_price = ci.getPd_price().doubleValue();
						sum_comm_weight = ci.getComm_weight();

						sum_red_money = sum_red_money.add(ci.getRed_money());

						order_money = order_money.add(new BigDecimal(pd_price * ci.getPd_count()));

						orderInfoDetails.setOrder_type(ci.getCart_type());
						orderInfoDetails.setCls_id(ci.getCls_id());
						orderInfoDetails.setCls_name(ci.getCls_name());
						orderInfoDetails.setPd_id(ci.getPd_id());
						orderInfoDetails.setPd_name(ci.getPd_name());
						orderInfoDetails.setGood_count(ci.getPd_count().intValue());
						orderInfoDetails.setComm_id(ci.getComm_id());
						orderInfoDetails.setComm_name(ci.getComm_name());
						orderInfoDetails.setComm_tczh_id(ci.getComm_tczh_id());
						orderInfoDetails.setComm_tczh_name(ci.getComm_tczh_name());
						if (GenericValidator.isLong(delivery_p_index)) {
							orderInfoDetails.setDelivery_p_index(Integer.valueOf(delivery_p_index));
						}

						orderInfoDetails.setHuizhuan_rule(commInfo.getFanxian_rule());
						orderInfoDetails.setGood_price(ci.getPd_price());
						orderInfoDetails.setGood_sum_price(new BigDecimal(pd_price * ci.getPd_count()));

						BigDecimal actual_money = orderInfoDetails.getGood_sum_price();

						// 这个地方计算会员优惠金额
						// 是会员 并且 商品为返现商品
						if (commInfo.getIs_rebate().intValue() == 1
								&& ui.getUser_level().intValue() == Keys.USER_LEVEL_ONE) {
							BaseData baseData = super.getBaseData(Keys.REBATE_BASE_DATA_ID.REBATE_BASE_DATA_ID_1001
									.getIndex());
							BigDecimal thisCommDisMoney = commInfo.getRebate_scale().multiply(ci.getPd_price())
									.multiply(new BigDecimal(baseData.getPre_number2())).divide(new BigDecimal(10000))
									.multiply(new BigDecimal(ci.getPd_count()));

							card_user_dis_money = card_user_dis_money.add(thisCommDisMoney);
							actual_money = orderInfoDetails.getGood_sum_price().subtract(thisCommDisMoney);
						}

						// 实际支付金额 - 优惠券金额
						if (null != ci.getYhq_id()) {
							YhqInfoSon yhqson = super.getYhqInfoSonAvailable(ci.getYhq_id());
							if (null != yhqson) {
								YhqInfo yhq = super.getYhqInfo(yhqson.getLink_id());
								if (null != yhq) {
									sum_yhq_money = sum_yhq_money.add(yhq.getYhq_money());
									actual_money = actual_money.subtract(yhq.getYhq_money());
									order_money = order_money.subtract(yhq.getYhq_money());

									orderInfoDetails.setYhq_money(yhq.getYhq_money());
									orderInfoDetails.setYhq_son_id(ci.getYhq_id());

									// 优惠券置为已使用
									YhqInfoSon updateyhq = new YhqInfoSon();
									updateyhq.setId(yhqson.getId());
									updateyhq.setYhq_state(Keys.YhqState.YHQ_STATE_20.getIndex());
									updateyhq.setIs_used(1);
									updateyhq.setUse_date(new Date());
									getFacade().getYhqInfoSonService().modifyYhqInfoSon(updateyhq);
								}
							}
						}

						orderInfoDetails.setActual_money(actual_money);

						orderInfoDetails.setEntp_id(ci.getEntp_id());

						// 这个地方需要平均发配运费
						orderInfoDetails.setMatflow_price(sum_matflow_price.divide(
								new BigDecimal(cartInfoTempList.size()), BigDecimal.ROUND_HALF_DOWN));

						orderInfoDetails.setWl_comp_name(ci.getWl_comp_name());
						orderInfoDetails.setDelivery_way(ci.getDelivery_way());

						orderInfoDetails.setSum_red_money(ci.getRed_money());// 消费券抵消金额
						orderInfoDetailsList.add(orderInfoDetails);
					}
					OrderInfo orderInfo = new OrderInfo();
					super.copyProperties(orderInfo, form);
					String creatTradeIndex = this.creatTradeIndex() + i;
					orderInfo.setTrade_index(creatTradeIndex);
					trade_indexs += creatTradeIndex + ",";

					orderInfo.setOrderInfoDetailsList(orderInfoDetailsList);

					// 保存收货地址信息到订单表
					ShippingAddress address = new ShippingAddress();
					address.setId(Integer.valueOf(shipping_address_id));
					address = super.getFacade().getShippingAddressService().getShippingAddress(address);
					if (null != address) {
						super.copyProperties(orderInfo, address);
						orderInfo.setId(null);
					}

					orderInfo.setOrder_num(order_sum_num);
					orderInfo.setNo_dis_money(order_money.add(sum_matflow_price));
					if (ui.getUser_level().intValue() == Keys.USER_LEVEL_ONE) {
						if (card_user_dis_money.compareTo(new BigDecimal(0)) > 0) {
							orderInfo.setYhq_tip_desc("挑夫会员立减："
									+ card_user_dis_money.setScale(2, BigDecimal.ROUND_HALF_DOWN));
							orderInfo.setRed_money(card_user_dis_money);
						}
						order_money = order_money.subtract(card_user_dis_money);
					}
					order_money = order_money.add(sum_matflow_price);
					// 使用余额抵扣
					if (StringUtils.isNotBlank(is_yue_dikou) && Integer.valueOf(is_yue_dikou) == 1) {
						if (ui.getBi_dianzi().compareTo(order_money) < 0) {// 可抵扣余额小于支付金额
							order_money = order_money.subtract(ui.getBi_dianzi());
							orderInfo.setMoney_bi(ui.getBi_dianzi());
						} else {
							orderInfo.setMoney_bi(order_money);
							order_money = new BigDecimal(0);
						}
					}
					orderInfo.setOrder_money(order_money);
					orderInfo.setReal_pay_money(order_money);

					orderInfo.setOrder_state(0);

					orderInfo.setMatflow_price(sum_matflow_price);
					orderInfo.setOrder_weight(sum_comm_weight);

					orderInfo.setOrder_date(new Date());
					orderInfo.setAdd_date(new Date());
					if (StringUtils.isNotBlank(remarks[i])) {
						orderInfo.setRemark(remarks[i]);
					}
					i++;

					// 订单默认7天后失效
					orderInfo.setEnd_date(DateUtils.addDays(new Date(), Keys.ORDER_END_DATE));
					orderInfo.setAdd_user_id(ui.getId());
					orderInfo.setAdd_user_name(ui.getUser_name());
					orderInfo.setEntp_id(ei.getId());
					orderInfo.setEntp_name(ei.getEntp_name());

					if (ei.getId().intValue() == Integer.valueOf(Keys.jd_entp_id)) {
						orderInfo.setOrder_type(Keys.OrderType.ORDER_TYPE_30.getIndex());
					} else {
						orderInfo.setOrder_type(Keys.OrderType.ORDER_TYPE_10.getIndex());
					}
					orderInfo.setPay_platform(Keys.PayPlatform.WAP.getIndex());
					orderInfo.setRed_money(sum_red_money);// 消费券抵消金额

					orderInfoList.add(orderInfo);
				}
			}
		}

		OrderInfo entity = new OrderInfo();
		entity.setOrderInfoList(orderInfoList);
		entity.getMap().put("payOrder", "true"); // 订单支付
		entity.setAdd_user_id(ui.getId());
		entity.setOrder_type(Keys.OrderType.ORDER_TYPE_10.getIndex());
		entity.getMap().put("cart_ids", cart_ids);
		entity.getMap().put("update_comm_info_saleCountAndInventory", true);

		UserInfo userInfo = new UserInfo();
		userInfo.setId(ui.getId());
		userInfo = super.getFacade().getUserInfoService().getUserInfo(userInfo);
		entity.getMap().put("userInfo", userInfo);
		int insertFlag = getFacade().getOrderInfoService().createOrderInfo(entity);
		if (insertFlag > 0) {
			super.renderJavaScript(response, "location.href='IndexShoppingCar.do?method=selectPayType&trade_index="
					+ trade_indexs + "'");
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
		ui = this.getUserInfo(ui.getId());
		request.setAttribute("userInfo", ui);

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
		request.setAttribute("fuxiao", userInfo.getBi_fuxiao());

		request.setAttribute("canPay", true);
		if (StringUtils.isNotBlank(pay_type) && pay_type.equals(String.valueOf(Keys.PayType.PAY_TYPE_0.getIndex()))) {
			if (order_money.compareTo(dianzibi_to_rmb) > 0) {
				request.setAttribute("canPay", false);
			} else {
				request.setAttribute("canPay", true);
			}
		}
		saveToken(request);// 设置token，防止重复提交

		BigDecimal huoKuanBi_to_rmb = super.BiToBi(userInfo.getBi_huokuan(),
				Keys.BASE_DATA_ID.BASE_DATA_ID_905.getIndex());
		request.setAttribute("huoKuanBi_to_rmb", huoKuanBi_to_rmb);

		return new ActionForward("/index/IndexShoppingCar/step2.jsp");
	}

	public ActionForward step3(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String out_trade_no = (String) dynaBean.get("out_trade_no");
		String pay_password = (String) dynaBean.get("pay_password");
		String pay_type = (String) dynaBean.get("pay_type");
		String is_test = (String) dynaBean.get("is_test");
		logger.info("==========pay_type===========" + pay_type);
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}
		if (StringUtils.isBlank(out_trade_no) || StringUtils.isBlank(pay_type)) {
			String msg = "参数有误，请联系管理员！";
			super.renderJavaScript(response, "alert('" + msg + "');history.back();");
			return null;
		}

		ui = super.getUserInfo(ui.getId());

		if (pay_type.equals("0")) {
			if (null == ui.getPassword_pay()) {
				String msg = "未设置支付密码！";
				super.renderJavaScript(response, "alert('" + msg + "');history.back();");
				return null;
			}

			if (StringUtils.isBlank(pay_password)) {
				String msg = "参数有误，请联系管理员！";
				super.renderJavaScript(response, "alert('" + msg + "');history.back();");
			}

			if (!EncryptUtilsV2.MD5Encode(pay_password.trim()).equals(ui.getPassword_pay())) {
				String msg = "支付密码输入有误！";
				super.renderJavaScript(response, "alert('" + msg + "');history.back();");
			}
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

			super.setPublicInfoWithSearchList(request);
			super.setPublicInfoList(request);
			request.setAttribute("stepFlag", 3);

			return new ActionForward("/index/IndexShoppingCar/step3.jsp");
		} else {
			super.renderJavaScript(response, "location.href='" + super.getCtxPath(request)
					+ "/IndexPayment.do?out_trade_no=" + out_trade_no + "&pay_type=" + pay_type + "&is_test=" + is_test
					+ "';");
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
		BigDecimal totalMatflowMoney = new BigDecimal("0");
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

			totalMoney = totalMoney.add((ci.getPd_price()).multiply(new BigDecimal(ci.getPd_count())));
		}

		// 新版本计算运费模板
		Integer p_index = (Integer) request.getAttribute("p_index");
		totalMatflowMoney = calMatflowMoney(p_index, user_id, null);
		if (totalMatflowMoney.compareTo(new BigDecimal(0)) < 0) {// 证明有商品配送不到
			request.setAttribute("is_send", 0);
			totalMatflowMoney = new BigDecimal(0);
		} else {
			request.setAttribute("is_send", 1);
		}

		request.setAttribute("totalMoney", totalMoney);
		request.setAttribute("lack_inventorty", lack_inventorty);
		request.setAttribute("totalMatflowMoney", totalMatflowMoney);
		request.setAttribute("tip", tip);
		request.setAttribute("cartInfoList", cartInfoList);
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

	/**
	 * @author Liu,Jia
	 * @desc 全店优惠功能的判断
	 */
	protected void getDiscountStoreList(List<CartInfo> cartInfoList, EntpInfo ei, Integer user_id, Integer p_index) {

		// 查询企业是否添加了优惠政策 去查询出对应的信息
		// 第一层过滤掉购物车中商品是否被包含在打折信息中

		Date date2 = new Date();
		@SuppressWarnings("static-access")
		String nowDate = super.sdFormat_ymdhms.format(date2);

		DiscountStores discountStores = new DiscountStores();
		discountStores.setEntp_id(ei.getId());
		discountStores.setIs_del(0);
		discountStores.getMap().put("now_date", nowDate);
		List<DiscountStores> discountStoresList = super.getFacade().getDiscountStoresService()
				.getDiscountStoresList(discountStores);

		List<DiscountStores> _discountStoresList = new ArrayList<DiscountStores>();
		if ((null != discountStoresList) && (discountStoresList.size() > 0)) {// 取出来多个
			for (DiscountStores ds : discountStoresList) {
				if (ds.getDiscount_comm_type() == 1) {// 证明选择了所有的商品 全店优惠
					_discountStoresList.add(ds);
				} else {
					String[] cls_ids = StringUtils.splitByWholeSeparator(ds.getCls_ids(), ",");
					// 先判断商品所选择项目
					if (ds.getDiscount_comm_type() == 2) {// 全店部门商品
						if (null != ds.getDiscount_comm_ids()) {// 如果选择商品 证明只有这些选择的商品被包含
							// 循环商品是否被添加在这个打折信息中
							String[] Discount_comm_ids = StringUtils.splitByWholeSeparator(ds.getDiscount_comm_ids(),
									",");
							if ((null != Discount_comm_ids) && (Discount_comm_ids.length > 0)) {
								for (CartInfo ci2 : cartInfoList) {
									if (ArrayUtils.contains(Discount_comm_ids, ci2.getComm_id().toString())) {// 如果打折信息包含该商品
										_discountStoresList.add(ds);
										// 防止一个商家出现多次优惠信息
										break;
									}
								}
							}
						} else if (null == ds.getDiscount_comm_ids()) {// 如果没选择商品，根据类别来判断
							if ((null != cls_ids) && (cls_ids.length > 0)) {
								for (CartInfo ci2 : cartInfoList) {
									// 查询商品类别对应的二级类别
									BaseClass bpz2 = new BaseClass();
									bpz2.getMap().put("all_par_cls", ci2.getCls_id());
									bpz2.getMap().put("no_have_self", 1);
									bpz2.setIs_del(0);
									bpz2.setCls_level(3);
									bpz2 = super.getFacade().getBaseClassService().getBaseClass(bpz2);
									if (null != bpz2) {
										if (ArrayUtils.contains(cls_ids, bpz2.getCls_id().toString())) {// 如果商品被包含在该类别下面
											_discountStoresList.add(ds);
											// 防止一个商家出现多次优惠信息
											break;
										}
									}
								}
							}
						}
					} else if (ds.getDiscount_comm_type() == 3) {// 选择排除商品
						if (null != ds.getDiscount_comm_ids()) {
							// 循环商品是否被添加在这个打折信息中
							String[] Discount_comm_ids = StringUtils.splitByWholeSeparator(ds.getDiscount_comm_ids(),
									",");
							if ((null != Discount_comm_ids) && (Discount_comm_ids.length > 0)) {
								for (CartInfo ci2 : cartInfoList) {
									if (!ArrayUtils.contains(Discount_comm_ids, ci2.getComm_id().toString())) {// 如果打折信息包含该商品
										_discountStoresList.add(ds);
										// 防止一个商家出现多次优惠信息
										break;
									}
								}
							}
						} else if (null == ds.getDiscount_comm_ids()) {// 如果没选择商品，根据类别来判断
							if ((null != cls_ids) && (cls_ids.length > 0)) {
								for (CartInfo ci2 : cartInfoList) {
									// 查询商品类别对应的二级类别
									BaseClass bpz2 = new BaseClass();
									bpz2.getMap().put("all_par_cls", ci2.getCls_id());
									bpz2.getMap().put("no_have_self", 1);
									bpz2.setIs_del(0);
									bpz2.setCls_level(3);
									bpz2 = super.getFacade().getBaseClassService().getBaseClass(bpz2);
									if (null != bpz2) {
										if (!ArrayUtils.contains(cls_ids, bpz2.getCls_id().toString())) {// 如果商品被包含在该类别下面
											_discountStoresList.add(ds);
											// 防止一个商家出现多次优惠信息
											break;
										}
									}
								}
							}
						}
					}
				}
			}
		}

		if (_discountStoresList.size() == 0) {// 清空cartInfo中qdyh_id
			CartInfo cartInfo = new CartInfo();
			cartInfo.getMap().put("entp_id", ei.getId());
			cartInfo.getMap().put("user_id", user_id);
			cartInfo.getMap().put("set_qdyh_id_null", true);
			cartInfo.getMap().put("set_discount_tj_null", true);
			cartInfo.getMap().put("set_discount_method_null", true);
			cartInfo.getMap().put("set_discount_type_content_null", true);
			cartInfo.getMap().put("set_discount_tj_content_null", true);
			int count = super.getFacade().getCartInfoService().modifyCartInfo(cartInfo);
			if (count > 0) {
				for (CartInfo ci2 : cartInfoList) {
					if (ci2.getEntp_id().equals(ei.getId())) {
						ci2.getMap().put("qdyh_id_null", true);
					}
				}
			}
		}
		// 第二层过滤是否属于包邮地区
		List<DiscountStores> _discountStoresList2 = new ArrayList<DiscountStores>();
		if (_discountStoresList.size() > 0) {
			for (DiscountStores ds2 : _discountStoresList) {
				if ((ds2.getP_indexs() != null) && !"".equals(ds2.getP_indexs())) {// 根据p_index
					// 匹配
					String[] p_indexs = ds2.getP_indexs().split(",");
					if (ds2.getBy_type() == 0) {// 选择全国包邮
						_discountStoresList2.add(ds2);
					} else if ((ds2.getBy_type() == 1) && (null != p_index)) {// 选择部门地区包邮
						if ((p_indexs != null) && (p_indexs.length > 0)) {
							for (String p_index3 : p_indexs) {
								String[] all_p_index = p_index3.split("\\|");
								String p_index1 = all_p_index[0];
								String p_index2 = "";
								if (all_p_index.length == 2) {
									if ("ALL".equals(all_p_index[1])) {
										p_index2 = p_index1;
									} else {
										p_index2 = all_p_index[1];
									}
								}
								if (!"".equals(p_index2) && (p_index2 != null) && (p_index2.length() > 5)) {
									if (p_index2.endsWith("0000")) {
										if (p_index.toString().substring(0, 2).equals(p_index2.substring(0, 2))) {
											_discountStoresList2.add(ds2);
										}
									} else if (p_index2.endsWith("00")) {
										if (p_index.toString().substring(0, 4).equals(p_index2.substring(0, 4))) {
											_discountStoresList2.add(ds2);
										}
									} else if (p_index.toString().substring(0, 6).equals(p_index2)) {
										_discountStoresList2.add(ds2);
									}
								}
							}
						}
					} else if ((ds2.getBy_type() == 2) && (null != p_index)) {// 选择部分地区不包邮
						if ((p_indexs != null) && (p_indexs.length > 0)) {
							Boolean flag = true;
							for (String p_index3 : p_indexs) {
								String[] all_p_index = p_index3.split("\\|");
								String p_index1 = all_p_index[0];
								String p_index2 = "";
								if (all_p_index.length == 2) {
									if ("ALL".equals(all_p_index[1])) {
										p_index2 = p_index1;
									} else {
										p_index2 = all_p_index[1];
									}
								}
								if (!"".equals(p_index2) && (p_index2 != null) && (p_index2.length() > 5)) {
									if (p_index.toString().substring(0, 2).equals(p_index2.substring(0, 2))
											|| p_index.toString().substring(0, 4).equals(p_index2.substring(0, 4))
											|| p_index.toString().substring(0, 6).equals(p_index2)) {
										flag = false;
										break;
									}
								}

							}
							if (flag) {
								_discountStoresList2.add(ds2);
							}
						}
					}
				}
			}
		}

		if (_discountStoresList2.size() == 0) {// 清空cartInfo中qdyh_id
			CartInfo cartInfo = new CartInfo();
			cartInfo.getMap().put("entp_id", ei.getId());
			cartInfo.getMap().put("user_id", user_id);
			cartInfo.getMap().put("set_qdyh_id_null", true);
			cartInfo.getMap().put("set_discount_tj_null", true);
			cartInfo.getMap().put("set_discount_method_null", true);
			cartInfo.getMap().put("set_discount_type_content_null", true);
			cartInfo.getMap().put("set_discount_tj_content_null", true);
			int count = super.getFacade().getCartInfoService().modifyCartInfo(cartInfo);
			if (count > 0) {
				for (CartInfo ci2 : cartInfoList) {
					if (ci2.getEntp_id().equals(ei.getId())) {
						ci2.getMap().put("qdyh_id_null", true);
					}
				}
			}
		}
		// 第三层 在判断购买商品是否满足包邮条件
		if (_discountStoresList2.size() > 0) {
			List<DiscountStores> _discountStoresList3 = new ArrayList<DiscountStores>();

			for (DiscountStores ds3 : _discountStoresList2) {
				Integer discount_tj = ds3.getDiscount_tj_content();// 包邮条件
				// 获得企业的总订单金额
				CartInfo cartInfoForDis = new CartInfo();
				cartInfoForDis.setIs_del(0);
				cartInfoForDis.setEntp_id(ds3.getEntp_id());
				cartInfoForDis.setUser_id(user_id);
				List<CartInfo> cartInfoForDisList = getFacade().getCartInfoService().getCartInfoList(cartInfoForDis);
				BigDecimal totalMoneyEntp = new BigDecimal("0");
				Integer totalCountEntp = 0;
				if ((null != cartInfoForDisList) && (cartInfoForDisList.size() > 0)) {
					for (CartInfo ci : cartInfoForDisList) {
						totalMoneyEntp = totalMoneyEntp.add((ci.getPd_price().add(ci.getService_single_money()))
								.multiply(new BigDecimal(ci.getPd_count())));
						totalCountEntp = totalCountEntp + ci.getPd_count();

					}
				}

				if (ds3.getDiscount_tj() == 1) {// 属于满多少元

					if (totalMoneyEntp.compareTo(new BigDecimal(discount_tj)) >= 0) {

						_discountStoresList3.add(ds3);
					}

				} else if (ds3.getDiscount_tj() == 2) {// 属于满多少件

					if (totalCountEntp >= discount_tj) {
						_discountStoresList3.add(ds3);
					}
				} else if (ds3.getDiscount_tj() == 3) {// 属于每满多少元

					if (totalMoneyEntp.compareTo(new BigDecimal(discount_tj)) >= 0) {

						_discountStoresList3.add(ds3);
					}
				} else if (ds3.getDiscount_tj() == 4) {// 属于每满多少件

					if (totalCountEntp >= discount_tj) {
						_discountStoresList3.add(ds3);
					}
				}
			}
			if (_discountStoresList3.size() == 0) {// 清空cartInfo中qdyh_id
				CartInfo cartInfo = new CartInfo();
				cartInfo.getMap().put("entp_id", ei.getId());
				cartInfo.getMap().put("user_id", user_id);
				cartInfo.getMap().put("set_qdyh_id_null", true);
				cartInfo.getMap().put("set_discount_tj_null", true);
				cartInfo.getMap().put("set_discount_method_null", true);
				cartInfo.getMap().put("set_discount_type_content_null", true);
				cartInfo.getMap().put("set_discount_tj_content_null", true);
				int count = super.getFacade().getCartInfoService().modifyCartInfo(cartInfo);
				if (count > 0) {
					for (CartInfo ci2 : cartInfoList) {
						if (ci2.getEntp_id().equals(ei.getId())) {
							ci2.getMap().put("qdyh_id_null", true);
						}
					}
				}
			} else {// 默认选中一条数据

				for (CartInfo ci2 : cartInfoList) {
					if ((null == ci2.getQdyh_id()) && (ci2.getFlag_qdyh() == 0)) {// 如果没有选择
						// 默认选中一条数据
						CartInfo cartInfo2 = new CartInfo();
						cartInfo2.getMap().put("entp_id", ei.getId());
						cartInfo2.getMap().put("user_id", user_id);
						cartInfo2.setQdyh_id(_discountStoresList3.get(0).getId());
						cartInfo2.setDiscount_tj(_discountStoresList3.get(0).getDiscount_tj());
						cartInfo2.setDiscount_method(_discountStoresList3.get(0).getDiscount_method());
						cartInfo2.setDiscount_tj_content(_discountStoresList3.get(0).getDiscount_tj_content()
								.toString());
						cartInfo2.setDiscount_type_content(_discountStoresList3.get(0).getDiscount_type_content());
						super.getFacade().getCartInfoService().modifyCartInfo(cartInfo2);
					}
				}
			}
			ei.getMap().put("discountStoresList", _discountStoresList3);
		}
	}

	public void setEntpInfoListToFormStep0(HttpServletRequest request, Integer user_id) {
		CartInfo cartInfo = new CartInfo();
		cartInfo.setIs_del(0);
		cartInfo.setUser_id(user_id);
		cartInfo.setCart_type(Keys.CartType.CART_TYPE_10.getIndex());
		BigDecimal totalMoney = new BigDecimal("0");
		String tip = "";
		Boolean lack_inventorty = true;
		List<CartInfo> cartInfoList = getFacade().getCartInfoService().getCartInfoList(cartInfo);
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
			ci.getMap().put("commInfo", commInfo);

			totalMoney = totalMoney.add((ci.getPd_price()).multiply(new BigDecimal(ci.getPd_count())));
		}

		request.setAttribute("totalMoney", totalMoney);
		request.setAttribute("lack_inventorty", lack_inventorty);
		request.setAttribute("tip", tip);
		request.setAttribute("cartInfoList", cartInfoList);
	}

	public void setEntpInfoListToForm(HttpServletRequest request, HttpServletResponse response, Integer user_id,
			DynaBean dynaBean, String shipping_address_id) throws Exception {

		String[] cart_ids = request.getParameterValues("cart_ids");
		String[] cart_types = { "" + Keys.CartType.CART_TYPE_10.getIndex() };

		List<String> cart_ids_in = new ArrayList<String>();

		if (null != cart_ids && cart_ids.length > 0) {
			for (int i = 0; i < cart_ids.length; i++) {
				if (StringUtils.isNotBlank(cart_ids[i])) {
					cart_ids_in.add(cart_ids[i]);
				}
			}
		}

		EntpInfo entpInfo = new EntpInfo();
		entpInfo.setIs_del(0);
		entpInfo.getMap().put("carts_of_entp", user_id);
		entpInfo.getMap().put("cart_ids", cart_ids);
		entpInfo.getMap().put("cart_types", cart_types);
		List<EntpInfo> entpInfoList = getFacade().getEntpInfoService().getEntpInfoList(entpInfo);
		Boolean is_send = true;

		BigDecimal card_user_dis_money = new BigDecimal(0);

		List<CommInfo> fpCommInfoList = new ArrayList<CommInfo>();

		if ((null != entpInfoList) && (entpInfoList.size() > 0)) {
			BigDecimal totalMoney = new BigDecimal("0");
			BigDecimal totalMatflowMoney = new BigDecimal("0");
			String tip = "";
			Boolean lack_inventorty = true;
			for (EntpInfo ei : entpInfoList) {
				List<CommInfo> ztCommInfoList = new ArrayList<CommInfo>();
				BigDecimal everEntpMatflowMoney = new BigDecimal("0");
				BigDecimal everEntpTotalMoney = new BigDecimal("0");

				CartInfo cartInfo = new CartInfo();
				cartInfo.setIs_del(0);
				cartInfo.setUser_id(user_id);
				cartInfo.setEntp_id(ei.getId());
				cartInfo.getMap().put("cart_ids_in", StringUtils.join(cart_ids_in, ","));
				cartInfo.setCart_type(Keys.CartType.CART_TYPE_10.getIndex());
				List<CartInfo> cartInfoList = getFacade().getCartInfoService().getCartInfoList(cartInfo);

				for (CartInfo ci : cartInfoList) {
					CommInfo commInfo = new CommInfo();
					commInfo.setId(ci.getComm_id());
					commInfo = super.getFacade().getCommInfoService().getCommInfo(commInfo);

					String inventoryTip = "有货";
					Integer inventoryCount = 0;

					if ((commInfo == null)
							|| ((commInfo.getIs_del().intValue() != 0))
							|| ((commInfo.getAudit_state().intValue() != 1))
							|| ((commInfo.getIs_sell().intValue() == 0))
							|| ((commInfo.getIs_sell().intValue() == 1) && (commInfo.getDown_date().compareTo(
									new Date()) < 0))) {
						cartInfo = new CartInfo();
						cartInfo.setId(ci.getId());
						super.getFacade().getCartInfoService().removeCartInfo(cartInfo);
						tip = tip + "<br/>" + ci.getComm_name() + "已经下架！";
					} else {

						// 插入可以开发票的商品
						if (commInfo.getIs_fapiao().intValue() == 1) {
							fpCommInfoList.add(commInfo);
						}

						// 插入无法自提的商品
						if (commInfo.getIs_ziti().intValue() == 0) {
							ztCommInfoList.add(commInfo);
						}

						// 这个地方计算会员优惠金额
						if (commInfo.getIs_rebate().intValue() == 1) {
							BaseData baseData = super.getBaseData(Keys.REBATE_BASE_DATA_ID.REBATE_BASE_DATA_ID_1001
									.getIndex());
							card_user_dis_money = card_user_dis_money.add(commInfo.getRebate_scale()
									.multiply(ci.getPd_price()).multiply(new BigDecimal(baseData.getPre_number2()))
									.divide(new BigDecimal(10000)).multiply(new BigDecimal(ci.getPd_count())));
						}

						// 这个地方如果发现商品是京东自营商品，则需要去京东请求接口计算库存数量以及运费
						if (commInfo.getOwn_entp_id().intValue() == Integer.valueOf(Keys.jd_entp_id)
								&& StringUtils.isNotBlank(shipping_address_id)) {

							ShippingAddress shippingAddress = new ShippingAddress();
							shippingAddress.setId(Integer.valueOf(shipping_address_id));
							shippingAddress = getFacade().getShippingAddressService().getShippingAddress(
									shippingAddress);

							JSONObject jsonKc = new JSONObject();

							JSONObject jsonComm = new JSONObject();
							JSONArray jsonArraySkus = new JSONArray();
							jsonComm.put("sku", commInfo.getJd_skuid());
							jsonComm.put("count", ci.getPd_count());
							jsonArraySkus.add(jsonComm);
							jsonKc.put("skus", JSON.toJSON(jsonArraySkus));

							String[] areas = super.getJdArea(shippingAddress).split(",");
							for (int i = 0; i < areas.length; i++) {
								if (i == 0) {
									jsonKc.put("p", areas[0]);
								}
								if (i == 1) {
									jsonKc.put("c", areas[1]);
								}
								if (i == 2) {
									jsonKc.put("d", areas[2]);
								}
								if (i == 3) {
									jsonKc.put("t", areas[3]);
								}
							}

							JSONObject stocksJson = super.getJdProductStocks(response, jsonKc.toJSONString());

							if (stocksJson.get("StatusCode").toString().equals(Keys.JD_API_RESULT_STATUS_CODE)) {

								String judgeJdProductPriceFlag = super.judgeJdProductPrice(commInfo.getJd_skuid());

								if (judgeJdProductPriceFlag.equals("0")) {
									inventoryTip = "库存不足";
									lack_inventorty = false;
								}

								JSONArray jsonArrayResult = stocksJson.getJSONArray("Data");
								if (null != jsonArrayResult && jsonArrayResult.size() > 0) {
									for (Object temp : jsonArrayResult) {
										JSONObject jsonObjectResulut = (JSONObject) temp;
										if (Integer.valueOf(jsonObjectResulut.get("stockStateId").toString()) == Keys.JD_NO_STOCK_CODE) {
											ci.getMap().put("jd_no_stock", "true");
											inventoryTip = "库存不足";
											lack_inventorty = false;
										}
									}
								}
							} else {
								inventoryTip = "库存不足";
								lack_inventorty = false;
							}

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

						super.getCartYhqInfo(ci);

						ci.getMap().put("comm_images", commInfo.getMain_pic());
						ci.getMap().put("comm_type", commInfo.getComm_type());
					}
					ci.getMap().put("pd_max_count", inventoryCount);
					ci.getMap().put("inventoryTip", inventoryTip);

					totalMoney = totalMoney.add((ci.getPd_price()).multiply(new BigDecimal(ci.getPd_count())));
					everEntpTotalMoney = everEntpTotalMoney.add(
							(ci.getPd_price()).multiply(new BigDecimal(ci.getPd_count()))).subtract(ci.getRed_money());

				}

				if (StringUtils.isNotBlank(shipping_address_id)) {
					// 购物车查询运费
					Integer p_index = (Integer) request.getAttribute("p_index");
					everEntpMatflowMoney = calMatflowMoneyNew(0, p_index, user_id,
							Integer.valueOf(shipping_address_id), ei.getId(), cartInfoList,
							StringUtils.join(cart_ids_in, ","));
					totalMatflowMoney = totalMatflowMoney.add(everEntpMatflowMoney);
				}

				if (everEntpMatflowMoney.compareTo(new BigDecimal(0)) < 0) {// 证明有商品配送不到
					is_send = false;
					totalMatflowMoney = new BigDecimal(0);
					everEntpMatflowMoney = new BigDecimal(0);
				}
				everEntpTotalMoney = everEntpTotalMoney.add(everEntpMatflowMoney);

				ei.getMap().put("everEntpMatflowMoney", everEntpMatflowMoney);
				ei.getMap().put("everEntpTotalMoney", everEntpTotalMoney);
				ei.getMap().put("ztCommInfoList", ztCommInfoList);
				ei.setCartInfoList(cartInfoList);
			}

			if (!is_send) {
				request.setAttribute("is_send", 0);
			} else {
				request.setAttribute("is_send", 1);
			}

			request.setAttribute("totalMoney", totalMoney);
			request.setAttribute("totalMatflowMoney", totalMatflowMoney);
			request.setAttribute("lack_inventorty", lack_inventorty);

			UserInfo userInfo = super.getUserInfo(user_id);
			if (userInfo.getUser_level().intValue() == Keys.USER_LEVEL_FX) {// 分享会员不能见金额
				request.setAttribute("card_user_dis_money_tip",
						card_user_dis_money.setScale(2, BigDecimal.ROUND_HALF_DOWN));
				request.setAttribute("userInfo", userInfo);
				card_user_dis_money = new BigDecimal(0);
			}

			request.setAttribute("card_user_dis_money", card_user_dis_money);

			request.setAttribute("tip", tip);
		}

		request.setAttribute("fpCommInfoList", fpCommInfoList);
		request.setAttribute("fpCommInfoListSize", fpCommInfoList.size());
		request.setAttribute("cart_ids", StringUtils.join(cart_ids_in, ","));
		request.setAttribute("entpInfoList", entpInfoList);
	}

	public ActionForward havingReloadPay(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;

		String trade_index = (String) dynaBean.get("trade_index");

		trade_index = trade_index.trim();
		String[] trade_indexs = trade_index.split(",");

		String code = "0";
		String msg = "";
		JSONObject datas = new JSONObject();

		for (String trade_index2 : trade_indexs) {
			if (StringUtils.isNotBlank(trade_index2)) {
				OrderInfo orderInfo = new OrderInfo();
				orderInfo.setTrade_index(trade_index2);
				orderInfo = getFacade().getOrderInfoService().getOrderInfo(orderInfo);
				if (orderInfo.getIs_price_modify() != null && orderInfo.getIs_price_modify() == 1
						&& orderInfo.getIs_reload_pay() == 0) {
					code = "1";// 需要重新加载支付
					msg = "订单金额已修改，请重新发起支付";

					// 修改订单 is_reload_pay 字段为1，已重新加载支付
					OrderInfo orderInfoUpdate = new OrderInfo();
					orderInfoUpdate.setId(orderInfo.getId());
					orderInfoUpdate.setIs_reload_pay(1);
					super.getFacade().getOrderInfoService().modifyOrderInfo(orderInfoUpdate);
				}
			}
		}

		super.ajaxReturnInfo(response, code, msg, datas);
		return null;
	}
}
