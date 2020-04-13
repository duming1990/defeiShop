package com.ebiz.webapp.web.struts.manager.customer;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.DateTools;

/**
 * @author LIUJIA
 * @version 2016-07-29
 */

public class MyCommSaleTableAction extends BaseCustomerAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		UserInfo userInfo = super.getUserInfoFromSession(request);
		getsonSysModuleList(request, dynaBean);

		String orderDay = (String) dynaBean.get("orderDay");
		String st_date = (String) dynaBean.get("st_date");
		String en_date = (String) dynaBean.get("en_date");
		String own_entp_id = (String) dynaBean.get("own_entp_id");
		String order_type = (String) dynaBean.get("order_type");

		if (StringUtils.isNotBlank(orderDay)) {
			if (orderDay.equals("1")) {// 查询上月
				st_date = sdFormat_ymd.format(DateTools.getFirstDayOfLastMonday(new Date()));
				en_date = sdFormat_ymd.format(DateTools.getLastDayOfLastMonday(new Date()));
			} else if (orderDay.equals("2")) {// 查询上周
				int today = DateTools.getWeekOfDateIndex(new Date());
				st_date = DateTools.getLastDay(today + 6);
				en_date = DateTools.getLastDay(today);
			} else if (orderDay.equals("3")) {// 查询昨日
				st_date = DateTools.getLastDay(1);
				en_date = DateTools.getLastDay(1);
			}
		}
		Integer entp_id = userInfo.getOwn_entp_id();
		String entp_id_in = "";

		// 销售金额
		OrderInfo orderInfoTodaySum = new OrderInfo();
		// if (StringUtils.isNotBlank(order_type) && Integer.valueOf(order_type) ==
		// Keys.OrderType.ORDER_TYPE_90.getIndex()) {
		// orderInfoTodaySum.setOrder_type(Integer.valueOf(order_type));
		// }
		orderInfoTodaySum.setIs_test(0);
		orderInfoTodaySum.setEntp_id(entp_id);
		orderInfoTodaySum.getMap().put("entp_id_in", entp_id_in);
		orderInfoTodaySum.getMap().put("st_add_date", st_date);
		orderInfoTodaySum.getMap().put("en_add_date", en_date);
		orderInfoTodaySum.getMap().put("order_state_ne", Keys.OrderState.ORDER_STATE_90.getIndex());
		orderInfoTodaySum.getMap().put("order_state_ge", Keys.OrderState.ORDER_STATE_10.getIndex());

		if (StringUtils.isNotBlank(order_type)) {
			if (order_type.equals(Keys.OrderType.ORDER_TYPE_90.getIndex() + "")) {
				orderInfoTodaySum.setOrder_type(Integer.valueOf(order_type));
			} else {

				orderInfoTodaySum.getMap().put("beside_type_90", Keys.OrderType.ORDER_TYPE_90.getIndex());
			}
		} else {
			orderInfoTodaySum.getMap().put("order_type_not_in", Keys.OrderType.ORDER_TYPE_90.getIndex());
		}

		orderInfoTodaySum = super.getFacade().getOrderInfoService().getOrderInfoStatisticaSum(orderInfoTodaySum);
		logger.info("===getOrderInfoStatisticaSum===");
		request.setAttribute("orderInfoTodaySum", orderInfoTodaySum);

		// 线下订单数量
		OrderInfo orderInfoApp = new OrderInfo();
		logger.info("====orderType:" + order_type);
		// if (StringUtils.isNotBlank(orderType) && Integer.valueOf(orderType) ==
		// ) {
		orderInfoApp.setOrder_type(Keys.OrderType.ORDER_TYPE_90.getIndex());
		// }
		orderInfoApp.setIs_test(0);
		orderInfoApp.setEntp_id(entp_id);
		orderInfoApp.getMap().put("entp_id_in", entp_id_in);
		orderInfoApp.getMap().put("order_state_ne", Keys.OrderState.ORDER_STATE_90.getIndex());
		orderInfoApp.getMap().put("order_state_ge", Keys.OrderState.ORDER_STATE_10.getIndex());
		orderInfoApp.getMap().put("st_add_date", st_date);
		orderInfoApp.getMap().put("en_add_date", en_date);

		if (StringUtils.isNotBlank(order_type) && Integer.valueOf(order_type) == 0) {
			orderInfoApp.setOrder_type(0);
		}
		int orderInfoAppCount = super.getFacade().getOrderInfoService().getOrderInfoCount(orderInfoApp);
		logger.info("====xianxia=====");
		request.setAttribute("orderInfoAppCount", orderInfoAppCount);

		// 通过线上下单数量
		OrderInfo orderInfoWeixin = new OrderInfo();

		orderInfoWeixin.setIs_test(0);
		orderInfoWeixin.setEntp_id(entp_id);
		orderInfoWeixin.getMap().put("entp_id_in", entp_id_in);
		orderInfoWeixin.getMap().put("order_state_ne", Keys.OrderState.ORDER_STATE_90.getIndex());
		orderInfoWeixin.getMap().put("order_state_ge", Keys.OrderState.ORDER_STATE_10.getIndex());
		// orderInfoWeixin.setOrder_type(Keys.OrderType.ORDER_TYPE_10.getIndex());

		if (StringUtils.isNotBlank(order_type)
				&& Integer.valueOf(order_type) == Keys.OrderType.ORDER_TYPE_90.getIndex()) {
			orderInfoWeixin.setOrder_type(0);
		} else {
			orderInfoWeixin.getMap().put("order_type_not_in", Keys.OrderType.ORDER_TYPE_90.getIndex());
		}
		orderInfoWeixin.getMap().put("st_add_date", st_date);
		orderInfoWeixin.getMap().put("en_add_date", en_date);
		logger.info("====xianshang=====");
		int orderInfoWeixinCount = super.getFacade().getOrderInfoService().getOrderInfoCount(orderInfoWeixin);
		request.setAttribute("orderInfoWeixinCount", orderInfoWeixinCount);

		// 微信支付金额
		OrderInfo orderInfoPayType = new OrderInfo();
		if (StringUtils.isNotBlank(order_type)
				&& Integer.valueOf(order_type) == Keys.OrderType.ORDER_TYPE_90.getIndex()) {
			orderInfoPayType.setOrder_type(Integer.valueOf(order_type));
		}
		if (StringUtils.isNotBlank(order_type)
				&& Integer.valueOf(order_type) == Keys.OrderType.ORDER_TYPE_90.getIndex()) {
			orderInfoPayType.setOrder_type(Integer.valueOf(order_type));
		}
		orderInfoPayType.setIs_test(0);
		orderInfoPayType.setEntp_id(entp_id);
		orderInfoPayType.getMap().put("entp_id_in", entp_id_in);
		orderInfoPayType.getMap().put("st_add_date", st_date);
		orderInfoPayType.getMap().put("en_add_date", en_date);
		orderInfoPayType.getMap().put("order_state_ne", Keys.OrderState.ORDER_STATE_90.getIndex());
		orderInfoPayType.getMap().put("order_state_ge", Keys.OrderState.ORDER_STATE_10.getIndex());
		orderInfoPayType.setPay_type(Keys.PayType.PAY_TYPE_3.getIndex());
		orderInfoPayType = super.getFacade().getOrderInfoService().getOrderInfoStatisticaSum(orderInfoPayType);
		request.setAttribute("payType3SumMoney", orderInfoPayType.getMap().get("sum_money"));

		// 支付宝支付金额
		orderInfoPayType.setEntp_id(entp_id);
		orderInfoPayType.setIs_test(0);
		orderInfoPayType.getMap().put("entp_id_in", entp_id_in);
		orderInfoPayType.getMap().put("st_add_date", st_date);
		orderInfoPayType.getMap().put("en_add_date", en_date);
		orderInfoPayType.getMap().put("order_state_ne", Keys.OrderState.ORDER_STATE_90.getIndex());
		orderInfoPayType.getMap().put("order_state_ge", Keys.OrderState.ORDER_STATE_10.getIndex());
		orderInfoPayType.setPay_type(Keys.PayType.PAY_TYPE_1.getIndex());
		orderInfoPayType = super.getFacade().getOrderInfoService().getOrderInfoStatisticaSum(orderInfoPayType);
		request.setAttribute("payType1SumMoney", orderInfoPayType.getMap().get("sum_money"));

		dynaBean.set("st_date", st_date);
		dynaBean.set("en_date", en_date);
		return mapping.findForward("list");
	}
}