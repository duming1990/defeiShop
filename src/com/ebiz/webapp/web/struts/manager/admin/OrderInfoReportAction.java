package com.ebiz.webapp.web.struts.manager.admin;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.DateTools;
import com.ebiz.webapp.web.util.EncryptUtilsV2;

public class OrderInfoReportAction extends BaseAdminAction {
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
		String st_order_date_fmt = (String) dynaBean.get("st_order_date_fmt");
		String en_order_date_fmt = (String) dynaBean.get("en_order_date_fmt");
		String st_pay_date_fmt = (String) dynaBean.get("st_pay_date_fmt");
		String en_pay_date_fmt = (String) dynaBean.get("en_pay_date_fmt");
		String own_entp_id = (String) dynaBean.get("own_entp_id");
		String order_state = (String) dynaBean.get("order_state");

		if (StringUtils.isNotBlank(orderDay)) {
			if (orderDay.equals("1")) {// 查询上月
				st_order_date_fmt = sdFormat_ymdhms.format(DateTools.getFirstDayOfLastMonday(new Date()));
				en_order_date_fmt = sdFormat_ymdhms.format(DateTools.getLastDayOfLastMonday(new Date()));
			} else if (orderDay.equals("2")) {// 查询上周
				int today = DateTools.getWeekOfDateIndex(new Date());
				st_order_date_fmt = DateTools.getLastDay(today + 6) + " 00:00:00";
				en_order_date_fmt = DateTools.getLastDay(today) + " 23:59:59";
			} else if (orderDay.equals("3")) {// 查询昨日
				st_order_date_fmt = DateTools.getLastDay(1) + " 00:00:00";
				en_order_date_fmt = DateTools.getLastDay(1) + " 23:59:59";
			}
			dynaBean.set("st_order_date_fmt", st_order_date_fmt.substring(0, 16));
			dynaBean.set("en_order_date_fmt", en_order_date_fmt.substring(0, 16));
		} else {
			dynaBean.set("st_order_date_fmt", st_order_date_fmt);
			dynaBean.set("en_order_date_fmt", en_order_date_fmt);
			if (StringUtils.isNotBlank(st_order_date_fmt)) {
				st_order_date_fmt = st_order_date_fmt + ":00";
			}
			if (StringUtils.isNotBlank(en_order_date_fmt)) {
				en_order_date_fmt = en_order_date_fmt + ":59";
			}
		}

		// 销售金额
		OrderInfo orderInfoTodaySum = new OrderInfo();
		if (StringUtils.isNotBlank(own_entp_id)) {
			orderInfoTodaySum.setEntp_id(Integer.valueOf(own_entp_id));
		}
		orderInfoTodaySum.setIs_test(0);
		orderInfoTodaySum.getMap().put("st_order_date_fmt", st_order_date_fmt);
		orderInfoTodaySum.getMap().put("en_order_date_fmt", en_order_date_fmt);
		if (StringUtils.isNotBlank(st_pay_date_fmt)) {
			orderInfoTodaySum.getMap().put("st_pay_date_fmt", st_pay_date_fmt + ":00");
		}
		if (StringUtils.isNotBlank(en_pay_date_fmt)) {
			orderInfoTodaySum.getMap().put("en_pay_date_fmt", en_pay_date_fmt + ":59");
		}
		if (StringUtils.isNotBlank(order_state) && GenericValidator.isLong(order_state)) {
			orderInfoTodaySum.setOrder_state(Integer.valueOf(order_state));
		}
		if (StringUtils.isBlank(order_state)) {
			order_state = "10,20,40,50,-20";
		}
		orderInfoTodaySum.getMap().put("order_state_in", order_state);
		orderInfoTodaySum.getMap().put("group_by", "t.entp_id");
		orderInfoTodaySum.getMap().put("order_type_not_in", Keys.OrderType.ORDER_TYPE_20.getIndex());
		List<OrderInfo> orderInfoTodaySumList = super.getFacade().getOrderInfoService()
				.getOrderInfoStatisticaListSum(orderInfoTodaySum);
		request.setAttribute("orderInfoTodaySum", orderInfoTodaySumList);

		return mapping.findForward("list");
	}

	public ActionForward reportOrderList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String st_order_date_fmt = (String) dynaBean.get("st_order_date_fmt");
		String en_order_date_fmt = (String) dynaBean.get("en_order_date_fmt");
		String st_pay_date_fmt = (String) dynaBean.get("st_pay_date_fmt");
		String en_pay_date_fmt = (String) dynaBean.get("en_pay_date_fmt");
		String own_entp_id = (String) dynaBean.get("own_entp_id");
		String order_state = (String) dynaBean.get("order_state");

		// if (StringUtils.isBlank(st_date)) {
		// st_date = DateTools.getFirstDayThisMonth();
		// }
		// if (StringUtils.isBlank(en_date)) {
		// en_date = DateTools.getLastDayThisMonth();
		// }

		OrderInfo orderInfo = new OrderInfo();
		if (StringUtils.isNotBlank(own_entp_id)) {
			orderInfo.setEntp_id(Integer.valueOf(own_entp_id));
		}
		orderInfo.setIs_test(0);
		if (StringUtils.isNotBlank(st_order_date_fmt)) {
			orderInfo.getMap().put("st_order_date_fmt", st_order_date_fmt + ":00");
		}
		if (StringUtils.isNotBlank(en_order_date_fmt)) {
			orderInfo.getMap().put("en_order_date_fmt", en_order_date_fmt + ":59");
		}
		if (StringUtils.isNotBlank(st_pay_date_fmt)) {
			orderInfo.getMap().put("st_pay_date_fmt", st_pay_date_fmt + ":00");
		}
		if (StringUtils.isNotBlank(en_pay_date_fmt)) {
			orderInfo.getMap().put("en_pay_date_fmt", en_pay_date_fmt + ":59");
		}
		if (StringUtils.isNotBlank(order_state) && GenericValidator.isLong(order_state)) {
			orderInfo.setOrder_state(Integer.valueOf(order_state));
		}
		orderInfo.getMap().put(
				"order_state_in",
				Keys.OrderState.ORDER_STATE_X20.getIndex() + "," + Keys.OrderState.ORDER_STATE_10.getIndex() + ","
						+ Keys.OrderState.ORDER_STATE_50.getIndex());
		orderInfo.getMap().put(
				"order_type_in",
				Keys.OrderType.ORDER_TYPE_10.getIndex() + "," + Keys.OrderType.ORDER_TYPE_30.getIndex() + ","
						+ Keys.OrderType.ORDER_TYPE_100.getIndex());
		Integer recordCount = super.getFacade().getOrderInfoService().getOrderInfoCount(orderInfo);
		pager.init(recordCount.longValue(), 10, pager.getRequestPage());
		orderInfo.getRow().setFirst(pager.getFirstRow());
		orderInfo.getRow().setCount(pager.getRowCount());
		logger.info("===========orderInfoList===========");
		List<OrderInfo> orderInfoList = super.getFacade().getOrderInfoService().getOrderInfoPaginatedList(orderInfo);
		request.setAttribute("entityList", orderInfoList);

		dynaBean.set("own_entp_id", own_entp_id);
		return new ActionForward("/../manager/admin/OrderInfoReport/list_details.jsp");
	}

	public ActionForward toExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		Map<String, Object> model = new HashMap<String, Object>();

		String orderDay = (String) dynaBean.get("orderDay");
		String st_order_date_fmt = (String) dynaBean.get("st_order_date_fmt");
		String en_order_date_fmt = (String) dynaBean.get("en_order_date_fmt");
		String st_pay_date_fmt = (String) dynaBean.get("st_pay_date_fmt");
		String en_pay_date_fmt = (String) dynaBean.get("en_pay_date_fmt");
		String own_entp_id = (String) dynaBean.get("own_entp_id");
		String order_state = (String) dynaBean.get("order_state");
		String code = (String) dynaBean.get("code");

		String cnt_all = (String) dynaBean.get("cnt");
		String sum_money_all = (String) dynaBean.get("sum_money");
		String sum_ye_all = (String) dynaBean.get("sum_ye");
		String sum_pay_money_all = (String) dynaBean.get("sum_pay_money");
		String sum_welfare_pay_money_all = (String) dynaBean.get("sum_welfare_pay_money");
		String price_money_1002_all = (String) dynaBean.get("price_money_1002");
		String price_money_1003_all = (String) dynaBean.get("price_money_1003");
		String price_money_1004_all = (String) dynaBean.get("price_money_1004");
		String price_money_1005_all = (String) dynaBean.get("price_money_1005");

		logger.info("=====cnt_all====" + cnt_all);
		if (StringUtils.isNotBlank(orderDay)) {
			if (orderDay.equals("1")) {// 查询上月
				st_order_date_fmt = sdFormat_ymdhms.format(DateTools.getFirstDayOfLastMonday(new Date()));
				en_order_date_fmt = sdFormat_ymdhms.format(DateTools.getLastDayOfLastMonday(new Date()));
			} else if (orderDay.equals("2")) {// 查询上周
				int today = DateTools.getWeekOfDateIndex(new Date());
				st_order_date_fmt = DateTools.getLastDay(today + 6) + " 00:00:00";
				en_order_date_fmt = DateTools.getLastDay(today) + " 23:59:59";
			} else if (orderDay.equals("3")) {// 查询昨日
				st_order_date_fmt = DateTools.getLastDay(1) + " 00:00:00";
				en_order_date_fmt = DateTools.getLastDay(1) + " 23:59:59";
			}
			dynaBean.set("st_order_date_fmt", st_order_date_fmt.substring(0, 16));
			dynaBean.set("en_order_date_fmt", en_order_date_fmt.substring(0, 16));
		} else {
			dynaBean.set("st_order_date_fmt", st_order_date_fmt);
			dynaBean.set("en_order_date_fmt", en_order_date_fmt);
			if (StringUtils.isNotBlank(st_order_date_fmt)) {
				st_order_date_fmt = st_order_date_fmt + ":00";
			}
			if (StringUtils.isNotBlank(en_order_date_fmt)) {
				en_order_date_fmt = en_order_date_fmt + ":59";
			}
		}

		// 销售金额
		OrderInfo orderInfoTodaySum = new OrderInfo();
		if (StringUtils.isNotBlank(own_entp_id)) {
			orderInfoTodaySum.setEntp_id(Integer.valueOf(own_entp_id));
		}
		orderInfoTodaySum.setIs_test(0);
		orderInfoTodaySum.getMap().put("st_order_date_fmt", st_order_date_fmt);
		orderInfoTodaySum.getMap().put("en_order_date_fmt", en_order_date_fmt);
		if (StringUtils.isNotBlank(st_pay_date_fmt)) {
			orderInfoTodaySum.getMap().put("st_pay_date_fmt", st_pay_date_fmt + ":00");
		}
		if (StringUtils.isNotBlank(en_pay_date_fmt)) {
			orderInfoTodaySum.getMap().put("en_pay_date_fmt", en_pay_date_fmt + ":59");
		}
		if (StringUtils.isNotBlank(order_state) && GenericValidator.isLong(order_state)) {
			orderInfoTodaySum.setOrder_state(Integer.valueOf(order_state));
		}
		// orderInfoTodaySum.getMap().put("order_state_ne",
		// Keys.OrderState.ORDER_STATE_90.getIndex());
		// orderInfoTodaySum.getMap().put("order_state_ge",
		// Keys.OrderState.ORDER_STATE_X20.getIndex());
		orderInfoTodaySum.getMap().put(
				"order_state_in",
				Keys.OrderState.ORDER_STATE_X20.getIndex() + "," + Keys.OrderState.ORDER_STATE_10.getIndex() + ","
						+ Keys.OrderState.ORDER_STATE_50.getIndex() + "," + Keys.OrderState.ORDER_STATE_20.getIndex()
						+ "," + Keys.OrderState.ORDER_STATE_40.getIndex());
		orderInfoTodaySum.getMap().put("order_type_not_in", Keys.OrderType.ORDER_TYPE_20.getIndex());
		orderInfoTodaySum.getMap().put("group_by", "entp_id");
		List<OrderInfo> orderInfoTodaySumList = super.getFacade().getOrderInfoService()
				.getOrderInfoStatisticaListSum(orderInfoTodaySum);

		model.put("entityList", orderInfoTodaySumList);
		model.put("cnt", cnt_all);
		model.put("sum_money", sum_money_all);
		model.put("sum_ye", sum_ye_all);
		model.put("sum_pay_money", sum_pay_money_all);
		model.put("sum_welfare_pay_money", sum_welfare_pay_money_all);
		model.put("price_money_1002", price_money_1002_all);
		model.put("price_money_1003", price_money_1003_all);
		model.put("price_money_1004", price_money_1004_all);
		model.put("price_money_1005", price_money_1005_all);
		model.put("title", "订单报表导出_日期" + sdFormat_ymd.format(new Date()));

		String content = getFacade().getTemplateService().getContent("OrderInfoReport/list.ftl", model);
		String fname = EncryptUtilsV2.encodingFileName("订单导出_日期" + sdFormat_ymd.format(new Date()) + ".xls");

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
}
