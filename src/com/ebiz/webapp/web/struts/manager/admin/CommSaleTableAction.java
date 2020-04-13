package com.ebiz.webapp.web.struts.manager.admin;

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
 * @author Liu,Jia
 * @version 2016-07-29
 */

public class CommSaleTableAction extends BaseAdminAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		String orderDay = (String) dynaBean.get("orderDay");
		String st_date = (String) dynaBean.get("st_date");
		String en_date = (String) dynaBean.get("en_date");
		String own_entp_id = (String) dynaBean.get("own_entp_id");
		String orderType = (String) dynaBean.get("orderType");

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

		String order_type_ne_in = Keys.OrderType.ORDER_TYPE_40.getIndex() + ","
				+ Keys.OrderType.ORDER_TYPE_50.getIndex();
		// 销售金额
		OrderInfo orderInfoTodaySum = new OrderInfo();
		if (StringUtils.isNotBlank(own_entp_id)) {
			orderInfoTodaySum.setEntp_id(Integer.valueOf(own_entp_id));
		}

		if (StringUtils.isNotBlank(orderType) && Integer.valueOf(orderType) == Keys.OrderType.ORDER_TYPE_90.getIndex()) {
			orderInfoTodaySum.setOrder_type(Integer.valueOf(orderType));
		}

		orderInfoTodaySum.setIs_test(0);// 过滤测试订单
		orderInfoTodaySum.getMap().put("st_add_date", st_date);
		orderInfoTodaySum.getMap().put("en_add_date", en_date);
		// orderInfoTodaySum.getMap().put("order_type_ne_in", order_type_ne_in);
		orderInfoTodaySum.getMap().put("order_state_ne", Keys.OrderState.ORDER_STATE_90.getIndex());
		orderInfoTodaySum.getMap().put("order_state_ge", Keys.OrderState.ORDER_STATE_10.getIndex());
		orderInfoTodaySum = super.getFacade().getOrderInfoService().getOrderInfoStatisticaSum(orderInfoTodaySum);
		request.setAttribute("orderInfoTodaySum", orderInfoTodaySum);

		// 新增会员数
		UserInfo userInfoTotalToday = new UserInfo();
		userInfoTotalToday.setIs_active(1);
		userInfoTotalToday.setIs_del(0);
		userInfoTotalToday.getMap().put("user_type_ne", Keys.UserType.USER_TYPE_1.getIndex());
		userInfoTotalToday.getMap().put("st_add_date", st_date);
		userInfoTotalToday.getMap().put("en_add_date", en_date);
		int userInfoTotalTodayCount = super.getFacade().getUserInfoService().getUserInfoCount(userInfoTotalToday);
		request.setAttribute("userInfoTotalTodayCount", userInfoTotalTodayCount);

		// 微信支付金额
		OrderInfo orderInfoPayType = new OrderInfo();
		orderInfoPayType.setIs_test(0);// 过滤测试订单
		if (StringUtils.isNotBlank(own_entp_id)) {
			orderInfoPayType.setEntp_id(Integer.valueOf(own_entp_id));
		}

		if (StringUtils.isNotBlank(orderType) && Integer.valueOf(orderType) == Keys.OrderType.ORDER_TYPE_90.getIndex()) {
			orderInfoPayType.setOrder_type(Integer.valueOf(orderType));
		}

		orderInfoPayType.getMap().put("st_add_date", st_date);
		orderInfoPayType.getMap().put("en_add_date", en_date);
		// orderInfoPayType.getMap().put("order_type_ne_in", order_type_ne_in);
		orderInfoPayType.getMap().put("order_state_ne", Keys.OrderState.ORDER_STATE_90.getIndex());
		orderInfoPayType.getMap().put("order_state_ge", Keys.OrderState.ORDER_STATE_10.getIndex());
		orderInfoPayType.setPay_type(Keys.PayType.PAY_TYPE_3.getIndex());
		orderInfoPayType = super.getFacade().getOrderInfoService().getOrderInfoStatisticaSum(orderInfoPayType);
		request.setAttribute("payType3SumMoney", orderInfoPayType.getMap().get("sum_pay_money"));

		// 支付宝支付金额
		if (StringUtils.isNotBlank(own_entp_id)) {
			orderInfoPayType.setEntp_id(Integer.valueOf(own_entp_id));
		}
		if (StringUtils.isNotBlank(orderType) && Integer.valueOf(orderType) == Keys.OrderType.ORDER_TYPE_90.getIndex()) {
			orderInfoPayType.setOrder_type(Integer.valueOf(orderType));
		}
		orderInfoPayType.setIs_test(0);
		orderInfoPayType.getMap().put("st_add_date", st_date);
		orderInfoPayType.getMap().put("en_add_date", en_date);
		// orderInfoPayType.getMap().put("order_type_ne_in", order_type_ne_in);
		orderInfoPayType.getMap().put("order_state_ne", Keys.OrderState.ORDER_STATE_90.getIndex());
		orderInfoPayType.getMap().put("order_state_ge", Keys.OrderState.ORDER_STATE_10.getIndex());
		orderInfoPayType.setPay_type(Keys.PayType.PAY_TYPE_1.getIndex());
		logger.info("===支付宝支付金额===");
		orderInfoPayType = super.getFacade().getOrderInfoService().getOrderInfoStatisticaSum(orderInfoPayType);
		request.setAttribute("payType1SumMoney", orderInfoPayType.getMap().get("sum_pay_money"));
		// 通联支付金额
		if (StringUtils.isNotBlank(own_entp_id)) {
			orderInfoPayType.setEntp_id(Integer.valueOf(own_entp_id));
		}
		if (StringUtils.isNotBlank(orderType) && Integer.valueOf(orderType) == Keys.OrderType.ORDER_TYPE_90.getIndex()) {
			orderInfoPayType.setOrder_type(Integer.valueOf(orderType));
		}
		orderInfoPayType.setIs_test(0);
		orderInfoPayType.getMap().put("st_add_date", st_date);
		orderInfoPayType.getMap().put("en_add_date", en_date);
		// orderInfoPayType.getMap().put("order_type_ne_in", order_type_ne_in);
		orderInfoPayType.getMap().put("order_state_ne", Keys.OrderState.ORDER_STATE_90.getIndex());
		orderInfoPayType.getMap().put("order_state_ge", Keys.OrderState.ORDER_STATE_10.getIndex());
		orderInfoPayType.setPay_type(Keys.PayType.PAY_TYPE_4.getIndex());
		logger.info("===通联支付金额===");
		orderInfoPayType = super.getFacade().getOrderInfoService().getOrderInfoStatisticaSum(orderInfoPayType);
		request.setAttribute("payType4SumMoney", orderInfoPayType.getMap().get("sum_pay_money"));

		// 余额支付金额
		if (StringUtils.isNotBlank(own_entp_id)) {
			orderInfoPayType.setEntp_id(Integer.valueOf(own_entp_id));
		}
		if (StringUtils.isNotBlank(orderType) && Integer.valueOf(orderType) == Keys.OrderType.ORDER_TYPE_90.getIndex()) {
			logger.info("===余额支付金额111===");
			orderInfoPayType.setOrder_type(Integer.valueOf(orderType));
		}
		orderInfoPayType.setIs_test(0);
		orderInfoPayType.getMap().put("st_add_date", st_date);
		orderInfoPayType.getMap().put("en_add_date", en_date);
		// orderInfoPayType.getMap().put("order_type_ne_in", order_type_ne_in);
		orderInfoPayType.getMap().put("order_state_ne", Keys.OrderState.ORDER_STATE_90.getIndex());
		orderInfoPayType.getMap().put("order_state_ge", Keys.OrderState.ORDER_STATE_10.getIndex());
		orderInfoPayType.setPay_type(Keys.PayType.PAY_TYPE_0.getIndex());
		logger.info("===余额支付金额===");
		orderInfoPayType = super.getFacade().getOrderInfoService().getOrderInfoStatisticaSum(orderInfoPayType);
		request.setAttribute("payType0SumMoney", orderInfoPayType.getMap().get("sum_pay_money"));

		// 现金支付金额
		if (StringUtils.isNotBlank(own_entp_id)) {
			orderInfoPayType.setEntp_id(Integer.valueOf(own_entp_id));
		}
		if (StringUtils.isNotBlank(orderType) && Integer.valueOf(orderType) == Keys.OrderType.ORDER_TYPE_90.getIndex()) {
			orderInfoPayType.setOrder_type(Integer.valueOf(orderType));
		}
		orderInfoPayType.setIs_test(0);
		orderInfoPayType.getMap().put("st_add_date", st_date);
		orderInfoPayType.getMap().put("en_add_date", en_date);
		// orderInfoPayType.getMap().put("order_type_ne_in", order_type_ne_in);
		orderInfoPayType.getMap().put("order_state_ne", Keys.OrderState.ORDER_STATE_90.getIndex());
		orderInfoPayType.getMap().put("order_state_ge", Keys.OrderState.ORDER_STATE_10.getIndex());
		orderInfoPayType.setPay_type(Keys.PayType.PAY_TYPE_6.getIndex());
		logger.info("===现金支付金额===");
		orderInfoPayType = super.getFacade().getOrderInfoService().getOrderInfoStatisticaSum(orderInfoPayType);
		request.setAttribute("payType2SumMoney", orderInfoPayType.getMap().get("sum_pay_money"));

		dynaBean.set("st_date", st_date);
		dynaBean.set("en_date", en_date);
		return mapping.findForward("list");
	}
}