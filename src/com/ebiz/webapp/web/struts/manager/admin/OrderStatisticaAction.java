package com.ebiz.webapp.web.struts.manager.admin;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.OrderInfoDetails;
import com.ebiz.webapp.web.util.EncryptUtilsV2;

public class OrderStatisticaAction extends BaseAdminAction {

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.orderInfoList(mapping, form, request, response);
	}

	public ActionForward orderInfoList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String order_state = (String) dynaBean.get("order_state");
		String st_add_date = (String) dynaBean.get("st_add_date");
		String en_add_date = (String) dynaBean.get("en_add_date");
		String is_query = (String) dynaBean.get("is_query");
		String entp_name_like = (String) dynaBean.get("entp_name_like");
		OrderInfo entity = new OrderInfo();
		super.copyProperties(entity, form);
		entity.getMap().put("st_add_date", st_add_date);
		entity.getMap().put("en_add_date", en_add_date);
		entity.getMap().put("entp_name_like", entp_name_like);
		request.setAttribute("is_query", is_query);
		if (StringUtils.isNotEmpty(order_state)) {
			entity.setOrder_state(new Integer(order_state));
		}
		if ("1".equals(is_query)) {
			if ("2".equals(order_state)) {

				Integer recordCount = getFacade().getOrderInfoService().getOrderInfoStatisticaCount(entity);
				pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
				entity.getRow().setFirst(pager.getFirstRow());
				entity.getRow().setCount(pager.getRowCount());

				List<OrderInfo> list = getFacade().getOrderInfoService().getOrderInfoStatisticaPaginatedList(entity);
				for (OrderInfo temp : list) {
					OrderInfo entity2 = new OrderInfo();
					entity2.setOrder_state(40);
					entity2.setEntp_id(temp.getEntp_id());
					entity2 = super.getFacade().getOrderInfoService().getOrderInfoStatisticaSum(entity2);
					temp.getMap().put("entity2", entity2);
				}
				request.setAttribute("entityList", list);
			} else {
				Integer recordCount = getFacade().getOrderInfoService().getOrderInfoStatisticaCount(entity);
				pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
				entity.getRow().setFirst(pager.getFirstRow());
				entity.getRow().setCount(pager.getRowCount());
				List<OrderInfo> list = getFacade().getOrderInfoService().getOrderInfoStatisticaPaginatedList(entity);
				request.setAttribute("entityList", list);
			}

		}
		return new ActionForward("/../manager/admin/OrderStatistica/infolist.jsp");
	}

	public ActionForward orderInfoDetailsList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String st_add_date = (String) dynaBean.get("st_add_date");
		String en_add_date = (String) dynaBean.get("en_add_date");
		String order_state = (String) dynaBean.get("order_state");
		String is_query = (String) dynaBean.get("is_query");
		String entp_name_like = (String) dynaBean.get("entp_name_like");
		OrderInfoDetails entity = new OrderInfoDetails();
		super.copyProperties(entity, form);
		entity.getMap().put("st_add_date", st_add_date);
		entity.getMap().put("en_add_date", en_add_date);
		entity.getMap().put("order_state", order_state);
		entity.getMap().put("entp_name_like", entp_name_like);
		request.setAttribute("is_query", is_query);

		OrderInfoDetails entity2 = new OrderInfoDetails();
		entity2 = getFacade().getOrderInfoDetailsService().getOrderInfoDetailsStatisticaSum(entity2);
		request.setAttribute("entity2", entity2);

		if ("1".equals(is_query)) {
			Integer recordCount = getFacade().getOrderInfoDetailsService().getOrderInfoDetailsStatisticaCount(entity);
			pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
			entity.getRow().setFirst(pager.getFirstRow());
			entity.getRow().setCount(pager.getRowCount());
			List<OrderInfoDetails> entpList = getFacade().getOrderInfoDetailsService()
					.getOrderInfoDetailsStatisticaEntpPaginatedList(entity);
			if (entpList != null && entpList.size() > 0) {
				for (OrderInfoDetails temp : entpList) {
					entity.setEntp_id(temp.getEntp_id());
					List<OrderInfoDetails> entpClsList = getFacade().getOrderInfoDetailsService()
							.getOrderInfoDetailsStatisticaEntpClsList(entity);
					temp.setEntpOrderInfoDetailslist(entpClsList);
				}
			}
			request.setAttribute("entityList", entpList);
		}
		return new ActionForward("/../manager/admin/OrderStatistica/infodetailslist.jsp");
	}

	public ActionForward toExcelForOrderInfoDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		super.setBaseDataListToSession(10, request);// 用户类型
		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String st_add_date = (String) dynaBean.get("st_add_date");
		String en_add_date = (String) dynaBean.get("en_add_date");
		String order_state = (String) dynaBean.get("order_state");
		String entp_name_like = (String) dynaBean.get("entp_name_like");

		OrderInfoDetails entity = new OrderInfoDetails();
		super.copyProperties(entity, form);
		entity.getMap().put("st_add_date", st_add_date);
		entity.getMap().put("en_add_date", en_add_date);
		entity.getMap().put("order_state", order_state);
		entity.getMap().put("entp_name_like", entp_name_like);

		OrderInfoDetails orderInfoDetails = new OrderInfoDetails();
		super.copyProperties(orderInfoDetails, form);
		orderInfoDetails.getMap().put("st_add_date", st_add_date);
		orderInfoDetails.getMap().put("en_add_date", en_add_date);
		orderInfoDetails.getMap().put("order_state", order_state);
		orderInfoDetails.getMap().put("entp_name_like", entp_name_like);

		StringBuffer queryCondition = new StringBuffer();
		Map<String, Object> model = new HashMap<String, Object>();

		entity = getFacade().getOrderInfoDetailsService().getOrderInfoDetailsStatisticaSum(entity);
		model.put("entityAdmin", entity);

		List<OrderInfoDetails> entpList = getFacade().getOrderInfoDetailsService()
				.getOrderInfoDetailsStatisticaEntpPaginatedList(orderInfoDetails);
		if (entpList != null && entpList.size() > 0) {
			for (OrderInfoDetails temp : entpList) {
				orderInfoDetails.setEntp_id(temp.getEntp_id());
				List<OrderInfoDetails> entpClsList = getFacade().getOrderInfoDetailsService()
						.getOrderInfoDetailsStatisticaEntpClsList(orderInfoDetails);
				temp.setEntpOrderInfoDetailslist(entpClsList);
				model.put("entityListAdmin", entpList);
			}
		}
		model.put("title", "订单信息表");
		model.put("Sum1", "所有产品合计销售数量");
		model.put("Sum2", "，所有产品合计销售金额");
		model.put("queryCondition", queryCondition.toString());
		String content = getFacade().getTemplateService().getContent("OrderInfoDetails/orderInfoDetails_list.ftl",
				model);
		// 下载文件出现乱码时，请参见此处
		String fname = EncryptUtilsV2.encodingFileName("订单信息表.xls");

		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename=" + fname);

		PrintWriter out = response.getWriter();
		out.println(content);
		out.flush();
		out.close();
		return null;
	}
}
