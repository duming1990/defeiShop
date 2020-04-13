package com.ebiz.webapp.web.struts.m;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aiisen.weixin.ApiUrlConstants;
import com.aiisen.weixin.CommonApi;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ebiz.webapp.domain.BaseData;
import com.ebiz.webapp.domain.CardInfo;
import com.ebiz.webapp.domain.CartInfo;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.CommTczhPrice;
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
 * @author 刘佳
 * @date: 2018年1月23日 下午5:26:20
 */
public class MWelfareCartInfoAction extends MBaseWebAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.step0(mapping, form, request, response);
	}

	public ActionForward step0(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		request.setAttribute("header_title", "购物车");

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			// 这个地方判断如果是微信扫描过来的，如果该没有登录的账号直接生成一个账号
			if (super.isWeixin(request)) {
				HttpSession session = request.getSession();
				String ymid = (String) session.getAttribute("ymid");
				String ctx = super.getCtxPath(request, false);
				String return_url = ctx + "/m/MWelfareCartInfo.do";

				StringBuilder link = new StringBuilder();
				String scope = "snsapi_userinfo";
				String state = "";

				StringBuffer server = new StringBuffer();
				server.append(request.getHeader("host")).append(request.getContextPath());
				request.setAttribute("server_domain", server.toString());
				String redirectUri = ctx.concat("/weixin/WeixinLogin.do?method=onlyCreateUserAndRedirect");
				redirectUri += "&return_url=" + URLEncoder.encode(return_url, "UTF-8");
				if (StringUtils.isNotBlank(ymid)) {
					redirectUri += "&ymid=" + ymid;
				}
				link.append(ApiUrlConstants.OAUTH2_LINK + "?appid=" + CommonApi.APP_ID)
						.append("&redirect_uri=" + URLEncoder.encode(redirectUri, "UTF-8"))
						.append("&response_type=code").append("&scope=" + scope).append("&state=" + state)
						.append("#wechat_redirect");
				response.sendRedirect(link.toString());
			}
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}

		UserInfo userInfo = super.getUserInfo(ui.getId());
		if (null == userInfo) {
			String msg = "用户不存在！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}

		this.setEntpInfoListToForm(request, ui.getId());

		String titleSideName = "<i class=\"fa fa-shopping-cart\"></i>&nbsp;" + Keys.TopBtns.TOJS.getName();

		request.setAttribute("titleSideName", titleSideName);
		request.setAttribute("userInfo", userInfo);

		super.setWhereCartInfoStep0(request);

		return new ActionForward("/MWelfareCartInfo/step0.jsp");
	}

	public ActionForward step1(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		boolean is_app = super.isApp(request);
		if (is_app) {
			super.setIsAppToCookie(request, response);
		} else {
			// return super.toAppDownload(mapping, form, request, response);
		}

		request.setAttribute("header_title", "提交订单");

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}
		request.setAttribute("ui", ui);
		DynaBean dynaBean = (DynaBean) form;
		String shipping_address_id = (String) dynaBean.get("shipping_address_id");

		CartInfo cartInfo = new CartInfo();
		cartInfo.setIs_del(0);
		cartInfo.setUser_id(ui.getId());
		cartInfo.setCart_type(Keys.CartType.CART_TYPE_20.getIndex());
		List<CartInfo> cartInfoList = getFacade().getCartInfoService().getCartInfoList(cartInfo);
		if (null != cartInfoList && cartInfoList.size() > 0) {

			String select_address_id = this.getShippingAddressInfo(request, ui.getId(), shipping_address_id);
			// 收货地址信息
			this.setEntpInfoListToFormGroupByEntp(request, response, ui.getId(), select_address_id);
		}
		saveToken(request);

		return new ActionForward("/MWelfareCartInfo/step1.jsp");
	}

	/**
	 * @return 创建订单
	 **/

	public ActionForward step2(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (!isTokenValid(request)) {
			return unspecified(mapping, form, request, response);
		}
		resetToken(request);
		DynaBean dynaBean = (DynaBean) form;
		String shipping_address_id = (String) dynaBean.get("shipping_address_id");
		String delivery_p_index = (String) dynaBean.get("delivery_p_index");
		String need_fp = (String) dynaBean.get("need_fp");
		String[] cart_ids = request.getParameterValues("cart_ids");
		String[] remarks = request.getParameterValues("remark");
		String[] is_ziti = request.getParameterValues("is_ziti");
		String[] yhq_son_ids = request.getParameterValues("yhq_son_id");

		String is_yue_dikou = (String) dynaBean.get("is_yue_dikou");
		List<String> cart_ids_in = new ArrayList<String>();

		if (null != cart_ids && cart_ids.length > 0) {
			for (int i = 0; i < cart_ids.length; i++) {
				if (StringUtils.isNotBlank(cart_ids[i])) {
					cart_ids_in.add(cart_ids[i]);
				}
			}
		}

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}

		Boolean tipFlag = true;
		CartInfo cartInfo = new CartInfo();
		cartInfo.setIs_del(0);
		cartInfo.setUser_id(ui.getId());
		cartInfo.getMap().put("cart_ids_in", StringUtils.join(cart_ids_in, ","));
		cartInfo.setCart_type(Keys.CartType.CART_TYPE_20.getIndex());
		List<CartInfo> cartInfoList = getFacade().getCartInfoService().getCartInfoList(cartInfo);
		for (CartInfo ci : cartInfoList) {
			CommInfo temp = new CommInfo();
			temp.setId(ci.getComm_id());
			temp = super.getFacade().getCommInfoService().getCommInfo(temp);

			if ((temp == null) || // 没有找到商品，删除掉
					(temp.getIs_del().intValue() != 0) || // 删除的商品，删除掉
					(temp.getAudit_state().intValue() != 1) || // 没审核通过的商标，删除掉
					((temp.getIs_sell().intValue() == 0))// 不允许销售的商品，删除掉
					|| ((temp.getIs_sell().intValue() == 1) && (temp.getDown_date().compareTo(new Date()) < 0))// 下架的商品，删除掉
			) {
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
		String[] cart_types = { "" + Keys.CartType.CART_TYPE_20.getIndex() };
		entpInfo.getMap().put("cart_types", cart_types);
		String trade_indexs = "";

		List<EntpInfo> entpInfoList = getFacade().getEntpInfoService().getEntpInfoList(entpInfo);
		if ((null != entpInfoList) && (entpInfoList.size() > 0)) {

			HttpSession session = request.getSession();
			String card_id = (String) session.getAttribute("card_id");

			if (StringUtils.isBlank(card_id)) {
				String msg = "福利卡信息有误！";
				return showTipMsg(mapping, form, request, response, msg);
			}

			int i = 0;
			for (EntpInfo ei : entpInfoList) {

				BigDecimal sum_matflow_price = new BigDecimal("0");

				BigDecimal card_user_dis_money = new BigDecimal("0");

				CartInfo tempCartInfo = new CartInfo();
				tempCartInfo.setIs_del(0);
				tempCartInfo.setEntp_id(ei.getId());
				tempCartInfo.setUser_id(ui.getId());
				tempCartInfo.setCart_type(Keys.CartType.CART_TYPE_20.getIndex());
				if (null != cart_ids) {
					tempCartInfo.getMap().put("cart_ids_in", StringUtils.join(cart_ids_in, ","));
				}
				List<CartInfo> cartInfoTempList = getFacade().getCartInfoService().getCartInfoList(tempCartInfo);
				if ((cartInfoTempList != null) && (cartInfoTempList.size() > 0)) {
					logger.info("======cartInfoList=======");
					for (CartInfo temp : cartInfoList) {
						CommInfo commInfo = new CommInfo();
						commInfo.setId(temp.getComm_id());
						commInfo = super.getFacade().getCommInfoService().getCommInfo(commInfo);
						if (commInfo.getIs_ziti() == 0 || Integer.valueOf(is_ziti[i]) == 0) {
							// 1、如果选择了自提，商品不是自提，需要计算运费,填写地址
							if (StringUtils.isBlank(shipping_address_id)) {
								String msg = "请选择收货地址！";
								return super.showTipMsg(mapping, form, request, response, msg);
							}
						}
					}

					if (StringUtils.isNotBlank(shipping_address_id)) {
						// 新版本计算运费模板
						sum_matflow_price = calMatflowMoneyNew(Integer.valueOf(is_ziti[i]),
								Integer.valueOf(delivery_p_index), ui.getId(), Integer.valueOf(shipping_address_id),
								ei.getId(), cartInfoTempList, StringUtils.join(cart_ids_in, ","));

						if (sum_matflow_price.compareTo(new BigDecimal(0)) < 0) {// 证明有商品配送不到
							String msg = "该商品无法送达！";
							return showTipMsg(mapping, form, request, response, msg);
						}
					}

					OrderInfo orderInfo = new OrderInfo();
					super.copyProperties(orderInfo, form);

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

						// 这个地方需要判断一下
						if (Integer.valueOf(need_fp).intValue() == Keys.Fp_State.Fp_State_1.getIndex()) {
							if (commInfo.getIs_fapiao().intValue() == Keys.Fp_State.Fp_State_1.getIndex()) {
								orderInfoDetails.setFp_state(Integer.valueOf(need_fp));
							}
						}

						orderInfoDetails.setHuizhuan_rule(commInfo.getFanxian_rule());
						orderInfoDetails.setGood_price(ci.getPd_price());
						orderInfoDetails.setGood_sum_price(new BigDecimal(pd_price * ci.getPd_count()));

						// 实际支付金额
						BigDecimal actual_money = orderInfoDetails.getGood_sum_price();

						// 这个地方计算会员优惠金额
						// 是会员 并且 商品为返现商品
						if (commInfo.getIs_rebate().intValue() == 1
								&& ui.getUser_level().intValue() == Keys.USER_LEVEL_ONE.intValue()) {
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
					orderInfo.setYhq_sun_money(sum_yhq_money);

					String creatTradeIndex = this.creatTradeIndex() + i;
					orderInfo.setTrade_index(creatTradeIndex);
					trade_indexs += creatTradeIndex + ",";

					orderInfo.setOrderInfoDetailsList(orderInfoDetailsList);

					if (StringUtils.isNotBlank(shipping_address_id)) {
						// 保存收货地址信息到订单表
						ShippingAddress address = new ShippingAddress();
						address.setId(Integer.valueOf(shipping_address_id));
						address = super.getFacade().getShippingAddressService().getShippingAddress(address);
						if (null != address) {
							super.copyProperties(orderInfo, address);
							orderInfo.setId(null);
						}
					}
					orderInfo.setOrder_num(order_sum_num);
					orderInfo.setFp_state(Integer.valueOf(need_fp));
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
					orderInfo.setIs_ziti(Integer.valueOf(is_ziti[i]));
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

					orderInfo.setOrder_type(Keys.OrderType.ORDER_TYPE_70.getIndex());
					orderInfo.setCard_id(Integer.valueOf(card_id));
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
					orderInfo.setRed_money(sum_red_money);// 消费券抵消金额

					orderInfoList.add(orderInfo);
				}
			}
		}

		OrderInfo entity = new OrderInfo();
		entity.setOrderInfoList(orderInfoList);
		entity.getMap().put("payOrder", "true"); // 订单支付
		entity.setAdd_user_id(ui.getId());
		entity.getMap().put("cart_ids", cart_ids);
		entity.getMap().put("update_comm_info_saleCountAndInventory", true);
		int insertFlag = getFacade().getOrderInfoService().createOrderInfo(entity);
		if (insertFlag > 0) {
			if (super.isApp(request)) {
				super.renderJavaScript(response, "location.href='MWelfareCartInfo.do?method=selectPayType&trade_index="
						+ trade_indexs + "&isApp=true'");
				return null;
			}
			super.renderJavaScript(response, "location.href='MWelfareCartInfo.do?method=selectPayType&trade_index="
					+ trade_indexs + "'");
			return null;
		} else if (insertFlag == -1) {
			String msg = "商品库存不足，请联系商家！";
			return super.showTipMsg(mapping, form, request, response, msg);
		} else if (insertFlag == -2) {
			String msg = "同步京东订单有误，请联系管理员！";
			return super.showTipMsg(mapping, form, request, response, msg);
		} else {
			String msg = "保存订单有误，请联系管理员！";
			return super.showTipMsg(mapping, form, request, response, msg);
		}
	}

	public ActionForward selectPayType(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		request.setAttribute("header_title", "支付方式");

		String trade_index = (String) dynaBean.get("trade_index");
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return super.showTipNotLogin(mapping, form, request, response, msg);
		}
		if (StringUtils.isBlank(trade_index)) {
			String msg = "参数错误";
			return super.showTipMsg(mapping, form, request, response, msg);
		}
		request.setAttribute("trade_index", trade_index);
		request.setAttribute("isWeixin", super.isWeixin(request));

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
					if (orderInfo.getCard_id() == null) {
						String msg = "福利卡信息有误！";
						return super.showTipMsg(mapping, form, request, response, msg);
					}
					CardInfo card = new CardInfo();
					card.setId(orderInfo.getCard_id());
					card.setIs_del(0);
					card.getMap().put("card_state_ge", Keys.CARD_STATE.CARD_STATE_X1.getIndex());
					card = getFacade().getCardInfoService().getCardInfo(card);
					if (null == card) {
						String msg = "福利卡信息有误！";
						return super.showTipMsg(mapping, form, request, response, msg);
					}

					request.setAttribute("cardInfo", card);
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

					if (orderInfo.getCard_pay_money().compareTo(new BigDecimal(0)) == 1) {
						orderInfo.setOrder_money(orderInfo.getCard_pay_money().add(orderInfo.getOrder_money()));
						orderInfo.setReal_pay_money(orderInfo.getOrder_money());
						orderInfo.setCard_pay_money(new BigDecimal(0));
						getFacade().getOrderInfoService().modifyOrderInfo(orderInfo);
					}
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

		request.setAttribute("canPay", true);

		saveToken(request);

		request.setAttribute("isPayDianzi", super.getSysSetting("isPayDianzi"));

		return new ActionForward("/MWelfareCartInfo/step2.jsp");
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
		if (StringUtils.isBlank(out_trade_no) || StringUtils.isBlank(pay_type)) {
			String msg = "参数有误，请联系管理员！";
			return super.showTipMsg(mapping, form, request, response, msg);
		}

		ui = super.getUserInfo(ui.getId());

		if (pay_type.equals("0")) {
			if (null == ui.getPassword_pay()) {
				String msg = "未设置支付密码！";
				return super.showTipMsg(mapping, form, request, response, msg);
			}
			if (StringUtils.isBlank(pay_password)) {
				String msg = "参数有误，请联系管理员！";
				return super.showTipMsg(mapping, form, request, response, msg);
			}
			if (!EncryptUtilsV2.MD5Encode(pay_password.trim()).equals(ui.getPassword_pay())) {
				String msg = "支付密码输入有误！";
				return super.showTipMsg(mapping, form, request, response, msg);
			}
		}
		// 更新订单状态

		if (!isTokenValid(request)) {
			return unspecified(mapping, form, request, response);
		}
		resetToken(request);

		if (pay_type.equals("0") || pay_type.equals("7")) {

			int count = super.modifyOrderInfo(request, out_trade_no, out_trade_no, Integer.valueOf(pay_type), null);
			if (count <= 0) {
				if (count == -2) {
					request.setAttribute("pay_state", -2);
				} else if (count == -3) {
					request.setAttribute("pay_state", -3);
				} else {
					request.setAttribute("pay_state", -1);
				}
			} else {
				request.setAttribute("pay_state", 1);
			}
			request.setAttribute("hasGoHome", true);

			OrderInfo order = new OrderInfo();
			order.setTrade_merger_index(out_trade_no);
			order = getFacade().getOrderInfoService().getOrderInfo(order);
			request.setAttribute("trade_index", order.getTrade_index());

			return new ActionForward("/MWelfareCartInfo/step3.jsp");
		} else {
			super.renderJavaScript(response, "location.href='" + super.getCtxPath(request)
					+ "/m/MIndexPayment.do?out_trade_no=" + out_trade_no + "&pay_type=" + pay_type + "';");
			return null;
		}
	}

	/**
	 * @return 返回购车list
	 **/
	public void setEntpInfoListToForm(HttpServletRequest request, Integer user_id) {

		CartInfo cartInfo = new CartInfo();
		cartInfo.setIs_del(0);
		cartInfo.setUser_id(user_id);
		cartInfo.setCart_type(Keys.CartType.CART_TYPE_20.getIndex());
		String tip = "";
		List<CartInfo> cartInfoList = getFacade().getCartInfoService().getCartInfoList(cartInfo);
		BigDecimal totalMoney = new BigDecimal("0");
		Boolean lack_inventorty = true;
		BigDecimal totalMatflowMoney = new BigDecimal("0");
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

					// 这个地方查询一下价格是否相同
					if (commTczhPrice.getComm_price().compareTo(ci.getPd_price()) != 0) {
						ci.setPd_price(commTczhPrice.getComm_price());
						CartInfo cartInfoUpdate = new CartInfo();
						cartInfoUpdate.setId(ci.getId());
						cartInfoUpdate.setPd_price(commTczhPrice.getComm_price());
						super.getFacade().getCartInfoService().modifyCartInfo(cartInfoUpdate);
					}
				}
			}

			ci.getMap().put("pd_max_count", inventoryCount);
			ci.getMap().put("inventoryTip", inventoryTip);
			ci.getMap().put("commInfo", commInfo);

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

		request.setAttribute("lack_inventorty", lack_inventorty);
		request.setAttribute("totalMoney", totalMoney);
		request.setAttribute("totalMatflowMoney", totalMatflowMoney);
		request.setAttribute("tip", tip);
		request.setAttribute("cartInfoList", cartInfoList);
	}

	public void setEntpInfoListToFormGroupByEntp(HttpServletRequest request, HttpServletResponse response,
			Integer user_id, String shipping_address_id) throws Exception {

		String[] cart_ids = request.getParameterValues("cart_ids");
		String[] cart_types = { "" + Keys.CartType.CART_TYPE_20.getIndex() };

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
		entpInfo.getMap().put("cart_types", cart_types);
		entpInfo.getMap().put("cart_ids_in", StringUtils.join(cart_ids_in, ","));
		List<EntpInfo> entpInfoList = getFacade().getEntpInfoService().getEntpInfoList(entpInfo);

		// 开关——快递能否抵达
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
				cartInfo.setCart_type(Keys.CartType.CART_TYPE_20.getIndex());
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

							jsonKc.put("areas", super.getJdArea(shippingAddress).split(","));
							JSONObject stocksJson = super.getJdProductStocks(response, jsonKc.toJSONString());

							if (stocksJson.get("status_code").toString().equals(Keys.JD_API_RESULT_STATUS_CODE)
									& Keys.JD_API_RESULT_OK.equals(stocksJson.get("ok").toString())) {

								String judgeJdProductPriceFlag = super.judgeJdProductPrice(commInfo.getJd_skuid());

								if (judgeJdProductPriceFlag.equals("0")) {
									inventoryTip = "库存不足";
									lack_inventorty = false;
								}

								JSONArray jsonArrayResult = stocksJson.getJSONArray("result");
								if (null != jsonArrayResult && jsonArrayResult.size() > 0) {
									for (Object temp : jsonArrayResult) {
										JSONObject jsonObjectResulut = (JSONObject) temp;
										if (Integer.valueOf(jsonObjectResulut.get("stockstateid").toString()) == Keys.JD_NO_STOCK_CODE) {
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
		request.setAttribute("entpInfoList", entpInfoList);
		request.setAttribute("cart_ids", StringUtils.join(cart_ids_in, ","));
	}

	public ActionForward addAddr(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		request.setAttribute("header_title", "添加地址");

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

		return new ActionForward("/MWelfareCartInfo/addAddr.jsp");
	}

	public ActionForward addressList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		request.setAttribute("header_title", "选择收货地址");
		DynaBean dynaBean = (DynaBean) form;
		String shipping_address_id = (String) dynaBean.get("shipping_address_id");
		String cart_ids = (String) dynaBean.get("cart_ids");

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return super.showTipNotLogin(mapping, form, request, response, msg);
		}

		this.getShippingAddressInfo(request, ui.getId(), shipping_address_id);
		if (StringUtils.isNotBlank(cart_ids)) {
			request.setAttribute("cart_ids", cart_ids);
		}
		return new ActionForward("/MWelfareCartInfo/addrList.jsp");
	}

	public ActionForward ztYunFei(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("======ztYunFei=======");
		DynaBean dynaBean = (DynaBean) form;
		String entp_id = (String) dynaBean.get("entp_id");
		String is_open = (String) dynaBean.get("is_open");
		String shipping_address_id = (String) dynaBean.get("shipping_address_id");
		String delivery_p_index = (String) dynaBean.get("delivery_p_index");

		JSONObject data = new JSONObject();
		String ret = "-1", msg = "";
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			msg = "您还未登录，请先登录系统！";
			data.put("ret", ret);
			data.put("msg", msg);
			super.renderJson(response, data.toString());
			return null;
		}

		if (StringUtils.isBlank(is_open)) {
			msg = "请选择是否自提！";
			data.put("ret", ret);
			data.put("msg", msg);
			super.renderJson(response, data.toString());
			return null;
		}

		CartInfo cartInfo = new CartInfo();
		cartInfo.setIs_del(0);
		cartInfo.setUser_id(ui.getId());
		cartInfo.setEntp_id(Integer.valueOf(entp_id));
		cartInfo.setCart_type(Keys.CartType.CART_TYPE_20.getIndex());
		List<CartInfo> cartInfoList = getFacade().getCartInfoService().getCartInfoList(cartInfo);

		if (null != cartInfoList && cartInfoList.size() > 0) {
			logger.info("======cartInfoList=======");
			for (CartInfo temp : cartInfoList) {
				CommInfo commInfo = new CommInfo();
				commInfo.setId(temp.getComm_id());
				commInfo = super.getFacade().getCommInfoService().getCommInfo(commInfo);
				if (commInfo.getIs_ziti() == 0 || Integer.valueOf(is_open) == 0) {
					// 1、如果选择了自提，商品不是自提，需要计算运费,填写地址
					if (StringUtils.isBlank(shipping_address_id)) {
						msg = "请选择收货地址！";
						data.put("ret", ret);
						data.put("msg", msg);
						super.renderJson(response, data.toString());
						return null;
					}
				}
			}
		}

		// 购物车查询不能自提的运费
		BigDecimal curEntpMatflowMoney = new BigDecimal(0);
		if (StringUtils.isNotBlank(delivery_p_index) && StringUtils.isNotBlank(shipping_address_id)) {
			curEntpMatflowMoney = this.calMatflowMoneyNew(1, Integer.valueOf(delivery_p_index), ui.getId(),
					Integer.valueOf(shipping_address_id), Integer.valueOf(entp_id), cartInfoList, null);
		}
		data.put("curEntpMatflowMoney", curEntpMatflowMoney);
		ret = "1";
		data.put("ret", ret);

		// 查看商品能否送达
		String[] cart_ids = request.getParameterValues("cart_ids");
		String[] is_ziti = request.getParameterValues("is_ziti");
		String[] cart_types = { "" + Keys.CartType.CART_TYPE_20.getIndex() };

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
		entpInfo.getMap().put("carts_of_entp", ui.getId());
		entpInfo.getMap().put("cart_types", cart_types);
		entpInfo.getMap().put("cart_ids_in", StringUtils.join(cart_ids_in, ","));
		List<EntpInfo> entpInfoList = getFacade().getEntpInfoService().getEntpInfoList(entpInfo);

		// 开关——快递能否抵达
		String is_send = "1";
		Boolean all_comm_ziti = true;
		if ((null != entpInfoList) && (entpInfoList.size() > 0)) {
			int i = 0;
			for (EntpInfo ei : entpInfoList) {
				BigDecimal everEntpMatflowMoney = new BigDecimal("0");

				CartInfo cartInfo1 = new CartInfo();
				cartInfo1.setIs_del(0);
				cartInfo1.setUser_id(ui.getId());
				cartInfo1.setEntp_id(ei.getId());
				cartInfo1.getMap().put("cart_ids_in", StringUtils.join(cart_ids_in, ","));
				cartInfo1.setCart_type(Keys.CartType.CART_TYPE_20.getIndex());
				List<CartInfo> cartInfoList1 = getFacade().getCartInfoService().getCartInfoList(cartInfo1);

				for (CartInfo temp : cartInfoList1) {
					CommInfo commInfo = new CommInfo();
					commInfo.setId(temp.getComm_id());
					commInfo = super.getFacade().getCommInfoService().getCommInfo(commInfo);
					if (commInfo.getIs_ziti() == 0 || Integer.valueOf(is_ziti[i]) == 0) {
						// 1、如果选择了自提，商品不是自提，需要计算运费,填写地址
						all_comm_ziti = false;
						break;
					}
				}

				if (StringUtils.isNotBlank(shipping_address_id) && !all_comm_ziti) {
					// 购物车查询运费
					everEntpMatflowMoney = calMatflowMoneyNew(0, Integer.valueOf(delivery_p_index), ui.getId(),
							Integer.valueOf(shipping_address_id), ei.getId(), cartInfoList1,
							StringUtils.join(cart_ids_in, ","));
				}

				if (everEntpMatflowMoney.compareTo(new BigDecimal(0)) < 0) {// 证明有商品配送不到
					is_send = "0";
					break;
				}
				i++;
			}

			data.put("is_send", is_send);
		}

		super.renderJson(response, data.toString());
		return null;
	}

	public ActionForward canSend(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("======ztYunFei=======");
		DynaBean dynaBean = (DynaBean) form;
		// String entp_id = (String) dynaBean.get("entp_id");
		// String is_open = (String) dynaBean.get("is_open");
		String shipping_address_id = (String) dynaBean.get("shipping_address_id");
		String delivery_p_index = (String) dynaBean.get("delivery_p_index");

		JSONObject data = new JSONObject();
		String ret = "-1", msg = "";
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			msg = "您还未登录，请先登录系统！";
			data.put("ret", ret);
			data.put("msg", msg);
			super.renderJson(response, data.toString());
			return null;
		}

		// 查看商品能否送达
		String[] cart_ids = request.getParameterValues("cart_ids");
		String[] is_ziti = request.getParameterValues("is_ziti");
		String[] cart_types = { "" + Keys.CartType.CART_TYPE_20.getIndex() };

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
		entpInfo.getMap().put("carts_of_entp", ui.getId());
		entpInfo.getMap().put("cart_types", cart_types);
		entpInfo.getMap().put("cart_ids_in", StringUtils.join(cart_ids_in, ","));
		List<EntpInfo> entpInfoList = getFacade().getEntpInfoService().getEntpInfoList(entpInfo);

		// 开关——快递能否抵达
		String is_send = "1";
		Boolean all_comm_ziti = true;
		if ((null != entpInfoList) && (entpInfoList.size() > 0)) {
			int i = 0;
			for (EntpInfo ei : entpInfoList) {
				BigDecimal everEntpMatflowMoney = new BigDecimal("0");

				CartInfo cartInfo1 = new CartInfo();
				cartInfo1.setIs_del(0);
				cartInfo1.setUser_id(ui.getId());
				cartInfo1.setEntp_id(ei.getId());
				cartInfo1.getMap().put("cart_ids_in", StringUtils.join(cart_ids_in, ","));
				cartInfo1.setCart_type(Keys.CartType.CART_TYPE_20.getIndex());
				List<CartInfo> cartInfoList1 = getFacade().getCartInfoService().getCartInfoList(cartInfo1);

				for (CartInfo temp : cartInfoList1) {
					CommInfo commInfo = new CommInfo();
					commInfo.setId(temp.getComm_id());
					commInfo = super.getFacade().getCommInfoService().getCommInfo(commInfo);
					if (commInfo.getIs_ziti() == 0 || Integer.valueOf(is_ziti[i]) == 0) {
						// 1、如果选择了自提，商品不是自提，需要计算运费,填写地址
						all_comm_ziti = false;
						break;
					}
				}

				if (StringUtils.isNotBlank(shipping_address_id) && !all_comm_ziti) {
					// 购物车查询运费
					everEntpMatflowMoney = calMatflowMoneyNew(0, Integer.valueOf(delivery_p_index), ui.getId(),
							Integer.valueOf(shipping_address_id), ei.getId(), cartInfoList1,
							StringUtils.join(cart_ids_in, ","));
				}

				if (everEntpMatflowMoney.compareTo(new BigDecimal(0)) < 0) {// 证明有商品配送不到
					is_send = "0";
					break;
				}
				i++;
			}

			data.put("is_send", is_send);
		}

		super.renderJson(response, data.toString());
		return null;
	}
}
