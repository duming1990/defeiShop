package com.ebiz.webapp.web.struts.manager.admin;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.EntpDuiZhang;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.EncryptUtilsV2;

/**
 * 商家结算报表和会员费报表
 * 
 * @author liuzhixiang
 */
public class SettlementReportAction extends BaseAdminAction {
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		String st_add_date = (String) dynaBean.get("st_add_date");
		String en_add_date = (String) dynaBean.get("en_add_date");

		EntpDuiZhang entity = new EntpDuiZhang();
		if (StringUtils.isNotBlank(st_add_date)) {
			entity.getMap().put("st_add_date_fmt", st_add_date + ":00");
		}
		if (StringUtils.isNotBlank(en_add_date)) {
			entity.getMap().put("en_add_date_fmt", en_add_date + ":59");
		}
		entity.setIs_check(1);
		entity.setIs_del(0);

		List<EntpDuiZhang> entityList = super.getFacade().getEntpDuiZhangService().getSettlementReport(entity);
		request.setAttribute("entityList", entityList);

		return mapping.findForward("list");
	}

	public ActionForward listDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String st_add_date = (String) dynaBean.get("st_add_date");
		String en_add_date = (String) dynaBean.get("en_add_date");
		// String entp_id = (String) dynaBean.get("entp_id");
		String id = (String) dynaBean.get("id");

		if (StringUtils.isBlank(id) || !GenericValidator.isLong(id)) {
			String msg = "参数错误";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');}");
			return null;
		}

		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setLink_check_id(Integer.valueOf(id));
		orderInfo.setIs_check(1);
		orderInfo.getMap().put("settlement_tag", "true");
		// orderInfo.getMap().put("settlement_entp_id", entp_id);
		if (StringUtils.isNotBlank(st_add_date)) {
			orderInfo.getMap().put("settlement_st_add_date", st_add_date + ":00");
		}
		if (StringUtils.isNotBlank(en_add_date)) {
			orderInfo.getMap().put("settlement_en_add_date", en_add_date + ":59");
		}

		Integer recordCount = super.getFacade().getOrderInfoService().getOrderInfoCount(orderInfo);
		pager.init(recordCount.longValue(), 10, pager.getRequestPage());
		orderInfo.getRow().setFirst(pager.getFirstRow());
		orderInfo.getRow().setCount(pager.getRowCount());
		List<OrderInfo> entityList = getFacade().getOrderInfoService().getOrderInfoPaginatedList(orderInfo);
		request.setAttribute("entityList", entityList);

		return new ActionForward("/../manager/admin/SettlementReport/list_details.jsp");

	}

	public ActionForward listMembershipFee(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String st_pay_date = (String) dynaBean.get("st_pay_date");
		String en_pay_date = (String) dynaBean.get("en_pay_date");
		String pay_type = (String) dynaBean.get("pay_type");

		OrderInfo entity = new OrderInfo();
		if (StringUtils.isNotBlank(st_pay_date)) {
			entity.getMap().put("st_pay_date_fmt", st_pay_date + ":00");
		}
		if (StringUtils.isNotBlank(en_pay_date)) {
			entity.getMap().put("en_pay_date_fmt", en_pay_date + ":59");
		}
		if (StringUtils.isNotBlank(pay_type) && !"-9".equals(pay_type)) {
			entity.setPay_type(Integer.valueOf(pay_type));
		} else if (StringUtils.isNotBlank(pay_type) && "-9".equals(pay_type)) {
			entity.getMap().put("fee_pay_type_is_null", "-9");
		}
		// entity.setOrder_type(Keys.OrderType.ORDER_TYPE_20.getIndex());
		// entity.setOrder_state(Keys.OrderState.ORDER_STATE_50.getIndex());

		Integer recordCount = super.getFacade().getOrderInfoService().getMembershipFeeCount(entity);
		pager.init(recordCount.longValue(), 10, pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());
		List<OrderInfo> entityList = getFacade().getOrderInfoService().getMembershipFeePaginatedList(entity);
		request.setAttribute("entityList", entityList);

		request.setAttribute("payTypeList", Keys.PayType.values());

		return new ActionForward("/../manager/admin/SettlementReport/list_membership_fee.jsp");
	}

	public ActionForward toExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		Map<String, Object> model = new HashMap<String, Object>();

		String code = (String) dynaBean.get("code");
		String st_add_date = (String) dynaBean.get("st_add_date");
		String en_add_date = (String) dynaBean.get("en_add_date");

		String order_count = (String) dynaBean.get("order_count");
		String sum_order_money = (String) dynaBean.get("sum_order_money");
		String sum_money = (String) dynaBean.get("sum_money");

		EntpDuiZhang entity = new EntpDuiZhang();
		if (StringUtils.isNotBlank(st_add_date)) {
			entity.getMap().put("st_add_date_fmt", st_add_date + ":00");
		}
		if (StringUtils.isNotBlank(en_add_date)) {
			entity.getMap().put("en_add_date_fmt", en_add_date + ":59");
		}
		entity.setIs_check(1);
		entity.setIs_del(0);

		List<EntpDuiZhang> entityList = super.getFacade().getEntpDuiZhangService().getSettlementReport(entity);

		model.put("entityList", entityList);
		model.put("title", "结算报表导出_日期" + sdFormat_ymd.format(new Date()));
		model.put("order_count", order_count);
		model.put("sum_order_money", sum_order_money);
		model.put("sum_money", sum_money);
		String content = getFacade().getTemplateService().getContent("SettlementReport/List.ftl", model);
		String fname = EncryptUtilsV2.encodingFileName("结算报表导出_日期" + sdFormat_ymd.format(new Date()) + ".xls");

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

	public ActionForward toMemerExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		Map<String, Object> model = new HashMap<String, Object>();

		String code = (String) dynaBean.get("code");
		String st_pay_date = (String) dynaBean.get("st_pay_date");
		String en_pay_date = (String) dynaBean.get("en_pay_date");
		String pay_type = (String) dynaBean.get("pay_type");

		OrderInfo entity = new OrderInfo();
		if (StringUtils.isNotBlank(st_pay_date)) {
			entity.getMap().put("st_pay_date_fmt", st_pay_date + ":00");
		}
		if (StringUtils.isNotBlank(en_pay_date)) {
			entity.getMap().put("en_pay_date_fmt", en_pay_date + ":59");
		}
		if (StringUtils.isNotBlank(pay_type) && !"-9".equals(pay_type)) {
			entity.setPay_type(Integer.valueOf(pay_type));
		} else if (StringUtils.isNotBlank(pay_type) && "-9".equals(pay_type)) {
			entity.getMap().put("fee_pay_type_is_null", "-9");
		}
		// entity.setOrder_type(Keys.OrderType.ORDER_TYPE_20.getIndex());
		// entity.setOrder_state(Keys.OrderState.ORDER_STATE_50.getIndex());

		List<OrderInfo> entityList = getFacade().getOrderInfoService().getMembershipFeePaginatedList(entity);
		model.put("entityList", entityList);
		model.put("title", "会员费报表导出_日期" + sdFormat_ymd.format(new Date()));

		String content = getFacade().getTemplateService().getContent("SettlementReport/List_membership.ftl", model);
		String fname = EncryptUtilsV2.encodingFileName("会员费报表导出_日期" + sdFormat_ymd.format(new Date()) + ".xls");

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