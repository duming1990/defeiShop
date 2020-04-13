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
import com.ebiz.webapp.domain.OrderInfoDetails;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.DateTools;
import com.ebiz.webapp.web.util.EncryptUtilsV2;

public class OrderInfoDetailsReportAction extends BaseAdminAction {
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String st_pay_date = (String) dynaBean.get("st_pay_date");
		String en_pay_date = (String) dynaBean.get("en_pay_date");
		String pay_type = (String) dynaBean.get("pay_type");
		String order_state = (String) dynaBean.get("order_state");
		String is_fuxiao = (String) dynaBean.get("is_fuxiao");
		String add_user_name_like = (String) dynaBean.get("add_user_name_like");
		String comm_name_like = (String) dynaBean.get("comm_name_like");

		OrderInfoDetails entity = new OrderInfoDetails();

		if (StringUtils.isBlank(st_pay_date) && StringUtils.isBlank(en_pay_date)) {
			st_pay_date = DateTools.getStringDate(new Date(), "yyyy-MM-dd");
			en_pay_date = DateTools.getStringDate(new Date(), "yyyy-MM-dd");
			dynaBean.set("st_pay_date", st_pay_date);
			dynaBean.set("en_pay_date", en_pay_date);
		}
		entity.getMap().put("st_pay_date", st_pay_date);
		entity.getMap().put("en_pay_date", en_pay_date);
		if (StringUtils.isNotBlank(pay_type) && GenericValidator.isInt(pay_type)) {
			entity.getMap().put("pay_type", pay_type);
		}
		if (StringUtils.isNotBlank(order_state) && GenericValidator.isInt(order_state)) {
			entity.getMap().put("order_state", order_state);
		}
		if (StringUtils.isNotBlank(is_fuxiao) && GenericValidator.isInt(is_fuxiao)) {
			entity.getMap().put("is_fuxiao", is_fuxiao);
		}
		entity.getMap().put("add_user_name_like", add_user_name_like);
		entity.getMap().put("comm_name_like", comm_name_like);

		Integer recordCount = getFacade().getOrderInfoDetailsService().getOrderInfoDetailsByReportCount(entity);
		pager.init(recordCount.longValue(), 10, pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<OrderInfoDetails> entityList = getFacade().getOrderInfoDetailsService()
				.getOrderInfoDetailsByReportPaginatedList(entity);

		request.setAttribute("entityList", entityList);
		request.setAttribute("orderStates", Keys.OrderState.values());
		request.setAttribute("payTypes", Keys.PayType.values());

		return mapping.findForward("list");
	}

	public ActionForward toExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		Map<String, Object> model = new HashMap<String, Object>();

		String st_pay_date = (String) dynaBean.get("st_pay_date");
		String en_pay_date = (String) dynaBean.get("en_pay_date");
		String pay_type = (String) dynaBean.get("pay_type");
		String order_state = (String) dynaBean.get("order_state");
		String is_fuxiao = (String) dynaBean.get("is_fuxiao");
		String add_user_name_like = (String) dynaBean.get("add_user_name_like");
		String comm_name_like = (String) dynaBean.get("comm_name_like");
		String code = (String) dynaBean.get("code");

		OrderInfoDetails entity = new OrderInfoDetails();

		if (StringUtils.isBlank(st_pay_date) && StringUtils.isBlank(en_pay_date)) {
			st_pay_date = DateTools.getStringDate(new Date(), "yyyy-MM-dd");
			en_pay_date = DateTools.getStringDate(new Date(), "yyyy-MM-dd");
			dynaBean.set("st_pay_date", st_pay_date);
			dynaBean.set("en_pay_date", en_pay_date);
		}
		entity.getMap().put("st_pay_date", st_pay_date);
		entity.getMap().put("en_pay_date", en_pay_date);
		if (StringUtils.isNotBlank(pay_type) && GenericValidator.isInt(pay_type)) {
			entity.getMap().put("pay_type", pay_type);
		}
		if (StringUtils.isNotBlank(order_state) && GenericValidator.isInt(order_state)) {
			entity.getMap().put("order_state", order_state);
		}
		if (StringUtils.isNotBlank(is_fuxiao) && GenericValidator.isInt(is_fuxiao)) {
			entity.getMap().put("is_fuxiao", is_fuxiao);
		}
		if (StringUtils.isNotBlank(add_user_name_like)) {
			entity.getMap().put("add_user_name_like", add_user_name_like);
		}
		entity.getMap().put("comm_name_like", comm_name_like);
		List<OrderInfoDetails> entityList = getFacade().getOrderInfoDetailsService()
				.getOrderInfoDetailsByReportPaginatedList(entity);

		model.put("entityList", entityList);
		model.put("title", "订单列表_导出日期" + sdFormat_ymd.format(new Date()));

		String content = getFacade().getTemplateService().getContent("OrderInfoDetailsReport/list.ftl", model);
		// 下载文件出现乱码时，请参见此处
		String fname = EncryptUtilsV2.encodingFileName("订单列表_导出日期" + sdFormat_ymd.format(new Date()) + ".xls");

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