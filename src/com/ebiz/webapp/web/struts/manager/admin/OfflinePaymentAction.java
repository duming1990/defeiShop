package com.ebiz.webapp.web.struts.manager.admin;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONObject;

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.CommTczhPrice;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.OrderInfoDetails;
import com.ebiz.webapp.domain.ShippingAddress;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;

/**
 * Created by QiuTao on 2017/4/18.
 */
public class OfflinePaymentAction extends BaseAdminAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		saveToken(request);

		request.setAttribute("endDate", new Date());
		request.setAttribute("beginDate", DateUtils.addMonths(new Date(), -3));

		return mapping.findForward("input");
	}

	public ActionForward listUser(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String user_name_like = (String) dynaBean.get("user_name_like");
		String user_ids = (String) dynaBean.get("user_ids");
		String user_names = (String) dynaBean.get("user_names");
		logger.info(user_names);

		UserInfo userInfo = new UserInfo();
		userInfo.setIs_del(0);
		userInfo.setUser_type(2);
		// userInfo.setIs_shuadan(1);
		userInfo.getMap().put("user_name_like", user_name_like);

		Integer recordCount = getFacade().getUserInfoService().getUserInfoCount(userInfo);
		pager.init(recordCount.longValue(), Integer.valueOf("20"), pager.getRequestPage());
		userInfo.getRow().setFirst(pager.getFirstRow());
		userInfo.getRow().setCount(pager.getRowCount());

		List<UserInfo> userInfoList = super.getFacade().getUserInfoService().getUserInfoPaginatedList(userInfo);
		if (StringUtils.isNotBlank(user_ids)) {
			UserInfo userInfo2 = new UserInfo();
			userInfo2.setIs_del(0);
			userInfo2.getMap().put("user_ids_in", user_ids);

			List<UserInfo> userInfo2List = super.getFacade().getUserInfoService().getUserInfoList(userInfo2);
			request.setAttribute("userInfo2List", userInfo2List);
			if (StringUtils.isBlank(user_names)) {
				if ((null != userInfo2List) && (userInfo2List.size() > 0)) {
					for (int i = 0; i < userInfo2List.size(); i++) {
						UserInfo yur = userInfo2List.get(i);
						user_names += yur.getUser_name();
						if ((userInfo2List.size() - 1) != i) {
							user_names += ",";
						}
					}
					dynaBean.set("user_count", String.valueOf(userInfo2List.size()));
				}
			}
		}
		dynaBean.set("user_ids", user_ids);
		dynaBean.set("user_names", user_names);
		request.setAttribute("entityList", userInfoList);

		return new ActionForward("/../manager/admin/OfflinePayment/listUserInfo.jsp");
	}

	public ActionForward listCommInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String comm_name_like = (String) dynaBean.get("comm_name_like");
		String own_entp_id = (String) dynaBean.get("own_entp_id");
		String discount_comm_ids = (String) dynaBean.get("discount_comm_ids");

		// 获取刷单企业
		EntpInfo entpInfo = new EntpInfo();
		entpInfo.setIs_del(0);
		entpInfo.setId(Integer.valueOf(own_entp_id));
		entpInfo = super.getFacade().getEntpInfoService().getEntpInfo(entpInfo);
		// int comm_type = (entpInfo.getEntp_type() == 10) ? 2 : 3;

		CommInfo entity = new CommInfo();
		if (StringUtils.isNotBlank(comm_name_like)) {
			entity.getMap().put("comm_name_like", comm_name_like);
		}
		entity.setOwn_entp_id(Integer.valueOf(own_entp_id));
		entity.setAudit_state(1);
		entity.setIs_del(0);
		entity.setComm_type(Keys.CommType.COMM_TYPE_2.getIndex());

		Integer recordCount = getFacade().getCommInfoService().getCommInfoCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<CommInfo> entityList = super.getFacade().getCommInfoService().getCommInfoPaginatedList(entity);
		for (CommInfo commInfo : entityList) {
			CommTczhPrice ctp = new CommTczhPrice();
			ctp.setComm_id(commInfo.getId().toString());
			List<CommTczhPrice> ctpList = super.getFacade().getCommTczhPriceService().getCommTczhPriceList(ctp);
			if (null != ctpList && ctpList.size() > 0) {
				ctp = ctpList.get(0);
				commInfo.getMap().put("price", ctp.getComm_price());
			} else {
				commInfo.getMap().put("price", commInfo.getPrice_ref());
			}
			commInfo.getMap().put("count", 1);
		}

		if (StringUtils.isNotBlank(discount_comm_ids)) {
			CommInfo ci = new CommInfo();
			ci.setAudit_state(1);
			ci.setIs_del(0);
			ci.getMap().put("comm_ids_in", discount_comm_ids);
			List<CommInfo> ciList = getFacade().getCommInfoService().getCommInfoList(ci);
			request.setAttribute("ciList", ciList);
		}
		request.setAttribute("entityList", entityList);
		request.setAttribute("comm_type", 2);
		return new ActionForward("/../manager/admin/OfflinePayment/listCommInfo.jsp");
	}

	public ActionForward commInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String discount_comm_ids = (String) dynaBean.get("discount_comm_ids");
		String comm_type = (String) dynaBean.get("comm_type");

		JSONObject jsonObject = new JSONObject();
		String msg = "";
		String ret = "";

		if (StringUtils.isBlank(discount_comm_ids)) {
			msg = "参数不能为空";
			ret = "1";
		}

		DecimalFormat dfFormat = new DecimalFormat("￥#,##0.00");
		CommInfo entity = new CommInfo();

		entity.setAudit_state(1);
		entity.setIs_del(0);
		// entity.setComm_type(Integer.valueOf(comm_type));
		entity.getMap().put("comm_ids_in", discount_comm_ids);

		List<CommInfo> entityList = super.getFacade().getCommInfoService().getCommInfoList(entity);

		BigDecimal sum = new BigDecimal(0);
		if (null != entityList && entityList.size() > 0) {
			for (CommInfo commInfo : entityList) {
				CommTczhPrice ctp = new CommTczhPrice();
				ctp.setComm_id(commInfo.getId().toString());
				List<CommTczhPrice> ctpList = super.getFacade().getCommTczhPriceService().getCommTczhPriceList(ctp);
				if (null != ctpList && ctpList.size() > 0) {
					ctp = ctpList.get(0);
					commInfo.getMap().put("price", ctp.getComm_price());
					sum = sum.add(ctp.getComm_price());
				} else {
					commInfo.getMap().put("price", commInfo.getPrice_ref());
					sum = sum.add(commInfo.getPrice_ref());
				}
				commInfo.getMap().put("count", 1);
			}
			jsonObject.put("entityList", entityList);
			jsonObject.put("sum", dfFormat.format(sum));
			jsonObject.put("sum2", dfFormat.format(sum));
		}

		jsonObject.put("msg", msg);
		jsonObject.put("ret", ret);
		super.renderJson(response, jsonObject.toString());
		return null;
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String user_id = (String) dynaBean.get("user_id");
		String own_entp_id = (String) dynaBean.get("own_entp_id");
		String count = (String) dynaBean.get("count");
		String beginDate = (String) dynaBean.get("beginDate");
		String is_send_fp = (String) dynaBean.get("is_send_fp");
		String comm_id[] = request.getParameterValues("this_comm_id");
		String comm_count[] = request.getParameterValues("this_comm_count");

		ShippingAddress entityShip = new ShippingAddress();
		entityShip.setIs_del(0);
		entityShip.getMap().put("user_ids_in", user_id);
		String real_name_like = (String) dynaBean.get("real_name_like");
		if (StringUtils.isNotBlank(real_name_like)) {
			entityShip.getMap().put("real_name_like", real_name_like);
		}
		if (StringUtils.isBlank(beginDate)) {
			String msg = "下单时间不能为空！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}

		if (StringUtils.isBlank(user_id) || StringUtils.isBlank(own_entp_id) || StringUtils.isBlank(count)
				|| ArrayUtils.isEmpty(comm_id) || ArrayUtils.isEmpty(comm_count)) {
			String msg = "参数不能为空";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}

		if ("0".equals(count)) {
			String msg = "下单数量不能为0！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}
		// 获取刷单企业
		EntpInfo entpInfo = new EntpInfo();
		entpInfo.setIs_del(0);
		entpInfo.setId(Integer.valueOf(own_entp_id));
		entpInfo = super.getFacade().getEntpInfoService().getEntpInfo(entpInfo);
		if (null == entpInfo) {
			String msg = "参数不能为空";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}

		String comm_ids = "";
		if (comm_id != null && comm_id.length > 0) {
			for (int i = 0; i < comm_id.length - 1; i++) {
				comm_ids += comm_id[i] + ",";
			}
			comm_ids += comm_id[comm_id.length - 1];
		}
		// 获取刷单商品
		CommInfo ciQuery = new CommInfo();
		ciQuery.getMap().put("comm_ids_in", comm_ids);
		ciQuery.setIs_del(0);
		List<CommInfo> ciQueryList = super.getFacade().getCommInfoService().getCommInfoList(ciQuery);
		if (null == ciQueryList || ciQueryList.size() != comm_id.length) {
			String msg = "参数错误!";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}

		List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();
		for (int i = 0; i < Integer.valueOf(count); i++) {// 循环刷单数量
			UserInfo userInfo = new UserInfo();
			userInfo.setIs_del(0);
			userInfo.getMap().put("user_ids_in", user_id);
			List<UserInfo> userInfoList = super.getFacade().getUserInfoService().getUserInfoList(userInfo);
			if (null != userInfoList && userInfoList.size() == user_id.split(",").length) {
				int j = 0;
				for (UserInfo userInfo2 : userInfoList) {// 循环刷单用户
					// 获取用户的随机地址
					String shippingAddress_id = String.valueOf(getRandomAddress(userInfo2).getId());
					// randomDate = randomDate(beginDate, endDate);
					List<OrderInfoDetails> orderInfoDetailsList = new ArrayList<OrderInfoDetails>();
					Integer order_sum_num = 0;
					BigDecimal order_money = new BigDecimal("0");
					BigDecimal sum_matflow_price = new BigDecimal("0");
					BigDecimal sum_comm_weight = new BigDecimal("0");
					for (int k = 0; k < comm_id.length; k++) {
						if (!"0".equals(comm_count[k])) {// 商品数量不为0，加入order_info_details表
							OrderInfoDetails orderInfoDetails = new OrderInfoDetails();
							for (CommInfo commInfo : ciQueryList) {
								if (Integer.valueOf(comm_id[k]).equals(commInfo.getId())) {
									order_sum_num += Integer.valueOf(comm_count[k]);

									orderInfoDetails.setOrder_type(Keys.OrderType.ORDER_TYPE_60.getIndex());
									orderInfoDetails.setCls_id(commInfo.getCls_id());
									orderInfoDetails.setCls_name(commInfo.getCls_name());
									orderInfoDetails.setPd_id(commInfo.getPd_id());
									orderInfoDetails.setPd_name(commInfo.getPd_name());
									orderInfoDetails.setGood_count(Integer.valueOf(comm_count[k]));
									orderInfoDetails.setComm_id(commInfo.getId());
									orderInfoDetails.setComm_name(commInfo.getComm_name());
									orderInfoDetails.setEntp_id(commInfo.getOwn_entp_id());

									// 获取套餐价格及套餐id
									CommTczhPrice ctp = new CommTczhPrice();
									ctp.setComm_id(commInfo.getId().toString());
									List<CommTczhPrice> ctpList = super.getFacade().getCommTczhPriceService()
											.getCommTczhPriceList(ctp);
									if (null != ctpList && ctpList.size() > 0) {
										ctp = ctpList.get(0);

										orderInfoDetails.setComm_tczh_name(ctp.getTczh_name());
										orderInfoDetails.setComm_tczh_id(ctp.getId());
										orderInfoDetails.setGood_price(ctp.getComm_price());
										orderInfoDetails.setGood_sum_price(ctp.getComm_price().multiply(
												new BigDecimal(Integer.valueOf(comm_count[k]))));
										order_money = order_money.add(ctp.getComm_price().multiply(
												new BigDecimal(Integer.valueOf(comm_count[k]))));

									} else {
										orderInfoDetails.setGood_price(commInfo.getPrice_ref());
										orderInfoDetails.setGood_sum_price(commInfo.getPrice_ref().multiply(
												new BigDecimal(Integer.valueOf(comm_count[k]))));
										order_money = order_money.add(commInfo.getPrice_ref().multiply(
												new BigDecimal(Integer.valueOf(comm_count[k]))));
									}
									// 刷单运费包邮
									orderInfoDetails.setMatflow_price(sum_matflow_price);
									orderInfoDetailsList.add(orderInfoDetails);

									// 修改销量
									CommInfo commInfoForSaleCount = new CommInfo();
									commInfoForSaleCount.setId(commInfo.getId());
									commInfoForSaleCount.getMap().put("add_sale_count", comm_count[k]);
									getFacade().getCommInfoService().modifyCommInfo(commInfoForSaleCount);

								}
							}
						}
					}
					OrderInfo orderInfo = new OrderInfo();

					String creatTradeIndex = this.creatTradeIndex() + j;
					orderInfo.setTrade_index(creatTradeIndex);
					j++;

					orderInfo.setOrderInfoDetailsList(orderInfoDetailsList);

					orderInfo.setOrder_num(order_sum_num);
					orderInfo.setOrder_money(order_money);
					orderInfo.setReal_pay_money(orderInfo.getOrder_money());
					// // 刷单订单
					// orderInfo.setIs_test(1);
					// 刷单订单直接确认收货状态
					orderInfo.setOrder_state(Keys.OrderState.ORDER_STATE_50.getIndex());

					// 获取用户收货地址信息

					if (StringUtils.isNotBlank(shippingAddress_id)) {
						ShippingAddress ship_query = new ShippingAddress();
						ship_query.getMap().put("user_ids_in", user_id);
						ship_query.setId(Integer.valueOf(shippingAddress_id));
						ship_query = super.getFacade().getShippingAddressService().getShippingAddress(ship_query);
						if (null == ship_query) {
							String msg = "参数错误!";
							super.renderJavaScript(response, "window.onload=function(){alert('" + msg
									+ "');history.back();}");
							return null;
						}
						orderInfo.setShipping_address_id(ship_query.getId());
						orderInfo.setRel_addr(ship_query.getRel_addr());
						orderInfo.setRel_city(ship_query.getRel_city());
						orderInfo.setRel_name(ship_query.getRel_name());
						orderInfo.setRel_phone(ship_query.getRel_phone());
						orderInfo.setRel_province(ship_query.getRel_province());
						orderInfo.setRel_region(ship_query.getRel_region());
						orderInfo.setRel_zip(ship_query.getRel_zip());
					} else {
						ShippingAddress shippingAddress = getRandomAddress();
						orderInfo.setShipping_address_id(shippingAddress.getId());
						orderInfo.setRel_addr(shippingAddress.getRel_addr());
						orderInfo.setRel_city(shippingAddress.getRel_city());
						orderInfo.setRel_name(shippingAddress.getRel_name());
						orderInfo.setRel_phone(shippingAddress.getRel_phone());
						orderInfo.setRel_province(shippingAddress.getRel_province());
						orderInfo.setRel_region(shippingAddress.getRel_region());
						orderInfo.setRel_zip(shippingAddress.getRel_zip());
					}
					orderInfo.setPay_type(Keys.PayType.PAY_TYPE_6.getIndex());
					orderInfo.setMatflow_price(sum_matflow_price);
					orderInfo.setOrder_weight(sum_comm_weight);
					orderInfo.setOrder_date(sdFormat_ymdhms.parse(beginDate));
					orderInfo.setAdd_date(sdFormat_ymdhms.parse(beginDate));
					orderInfo.setAdd_user_id(userInfo2.getId());
					orderInfo.setAdd_user_name(userInfo2.getReal_name());
					orderInfo.setEntp_id(entpInfo.getId());
					orderInfo.setEntp_name(entpInfo.getEntp_name());

					// Date randomQrsh_Date = null;
					// SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					// randomQrsh_Date = randomDate(DateTools.addDay(randomDate, 5, 1), DateTools.addDay(randomDate, 5,
					// 3));

					orderInfo.setQrsh_date(new Date());
					orderInfo.setPay_platform(10);
					orderInfo.setNo_dis_money(order_money);
					orderInfo.setOrder_type(Keys.OrderType.ORDER_TYPE_60.getIndex());
					orderInfo.setPay_date(sdFormat_ymdhms.parse(beginDate));
					if (null != userInfo2.getMobile() && null == orderInfo.getRel_phone())
						orderInfo.setRel_phone(userInfo2.getMobile());

					orderInfoList.add(orderInfo);
				}
			}
		}

		OrderInfo entity = new OrderInfo();
		entity.setOrderInfoList(orderInfoList);
		entity.getMap().put("payOrder", "true"); // 订单支付
		 if (StringUtils.isNotBlank(is_send_fp) && Integer.valueOf(is_send_fp) == 1) {
		 entity.getMap().put("is_send_fp", true); // 发放扶贫金
		 }
		int insertFlag = getFacade().getOrderInfoService().createShuaDanOrderInfo(entity);
		if (insertFlag > 0) {
			saveMessage(request, "operate.ok");
			StringBuffer pathBuffer = new StringBuffer();
			pathBuffer.append(mapping.findForward("success").getPath());
			ActionForward forward = new ActionForward(pathBuffer.toString(), true);
			return forward;
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

	private ShippingAddress getRandomAddress(UserInfo userInfo) {
		ShippingAddress shippingAddress = new ShippingAddress();
		shippingAddress.setAdd_user_id(userInfo.getId());
		shippingAddress.setIs_del(0);
		List<ShippingAddress> shippingAddressList = super.getFacade().getShippingAddressService()
				.getShippingAddressList(shippingAddress);
		if (null != shippingAddressList && shippingAddressList.size() > 0) {
			shippingAddress = shippingAddressList.get(0);
		} else {
			// 用户收货地址为空时，随机获取一个收货地址
			shippingAddress = getRandomAddress();
		}
		return shippingAddress;
	}

	private ShippingAddress getRandomAddress() {
		ShippingAddress shippingAddress = new ShippingAddress();
		Integer shippingAddressCount = super.getFacade().getShippingAddressService()
				.getShippingAddressCount(shippingAddress);
		int shipping_address_id = (int) (1 + Math.random() * shippingAddressCount);
		shippingAddress.setId(shipping_address_id);
		shippingAddress.setIs_del(0);
		shippingAddress = super.getFacade().getShippingAddressService().getShippingAddress(shippingAddress);
		if (null == shippingAddress) {
			shippingAddress = getRandomAddress();
		}
		return shippingAddress;
	}

	private static Date randomDate(String beginDate, String endDate) throws Exception {
		Date start = sdFormat_ymd.parse(beginDate);// 开始日期
		Date end = sdFormat_ymd.parse(endDate);// 结束日期
		if (start.getTime() >= end.getTime()) {
			return null;
		}
		long date = random(start.getTime(), end.getTime());

		return new Date(date);
	}

	private static long random(long begin, long end) {
		long rtnn = begin + (long) (Math.random() * (end - begin));
		return rtnn;
	}

	public String creatTradeIndex() {
		String trade_no = sdFormatymdhmsS.format(new Date());

		return trade_no;
	}
}
