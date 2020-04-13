package com.ebiz.webapp.web.struts.manager.admin;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.alibaba.fastjson.JSONObject;
import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.OrderAudit;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;

/**
 * @author Liu,Jia
 * @version 2017-06-19
 */
public class OrderAuditAction extends BaseAdminAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "0")) {
			return super.checkPopedomInvalid(request, response);
		}
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String st_add_date = (String) dynaBean.get("st_add_date");
		String en_add_date = (String) dynaBean.get("en_add_date");
		String trade_index_like = (String) dynaBean.get("trade_index_like");

		OrderAudit entity = new OrderAudit();
		super.copyProperties(entity, form);

		entity.getMap().put("trade_index_like", trade_index_like);
		entity.getMap().put("st_add_date", st_add_date);
		entity.getMap().put("en_add_date", en_add_date);

		Integer recordCount = getFacade().getOrderAuditService().getOrderAuditCount(entity);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<OrderAudit> list = getFacade().getOrderAuditService().getOrderAuditPaginatedList(entity);
		request.setAttribute("entityList", list);

		return mapping.findForward("list");
	}

	public ActionForward orderAudit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		if (StringUtils.isBlank(id)) {
			String msg = "参数有误，请联系管理员！";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		OrderAudit orderAudit = new OrderAudit();
		orderAudit.setId(Integer.valueOf(id));
		orderAudit = super.getFacade().getOrderAuditService().getOrderAudit(orderAudit);
		if (null == orderAudit) {
			String msg = "信息不存在！";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		super.copyProperties(form, orderAudit);
		return new ActionForward("/../manager/admin/OrderAudit/orderAudit.jsp");
	}

	public ActionForward orderAuditSave(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfoFromSession(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String audit_state = (String) dynaBean.get("audit_state");
		JSONObject json = new JSONObject();
		int ret = 0;
		String msg = "操作失败";
		if (!GenericValidator.isLong(id) || StringUtils.isBlank(audit_state)) {
			msg = "参数不正确";
			json.put("ret", ret);
			json.put("msg", msg);
			super.renderJson(response, json.toJSONString());
			return null;
		}
		if (null == ui) {
			msg = "ui不能为空";
			json.put("ret", ret);
			json.put("msg", msg);
			super.renderJson(response, json.toJSONString());
			return null;
		}

		OrderAudit orderAudit = new OrderAudit();
		orderAudit.setId(Integer.valueOf(id));
		orderAudit = super.getFacade().getOrderAuditService().getOrderAudit(orderAudit);
		if (null == orderAudit) {
			msg = "信息不存在";
			json.put("ret", ret);
			json.put("msg", msg);
			super.renderJson(response, json.toJSONString());
			return null;
		}
		if (orderAudit.getAudit_state().intValue() != 0) {
			ret = -2;
			msg = "该信息已经操作过了，不能在进行审核操作！";
			json.put("ret", ret);
			json.put("msg", msg);

			super.renderJson(response, json.toJSONString());
			return null;
		}

		OrderAudit entity = new OrderAudit();

		entity.setId(Integer.valueOf(id));
		super.copyProperties(entity, form);
		entity.setAudit_user_id(ui.getId());
		entity.setAudit_date(new Date());
		entity.setUpdate_date(new Date());
		entity.setUpdate_user_id(ui.getId());

		// 如果审核通过 则需要发放奖励
		if (Integer.valueOf(audit_state).intValue() == Keys.audit_state.audit_state_1.getIndex()) {
			entity.getMap().put("order_pass_to_do_something", "true");
		} else if (Integer.valueOf(audit_state).intValue() == Keys.audit_state.audit_state_fu_1.getIndex()) {
			entity.getMap().put("order_fail_to_do_something", "true");
		}

		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setId(orderAudit.getOrder_id());
		orderInfo.getMap().put("orderAudit", entity);

		super.getFacade().getOrderInfoService().modifyOrderInfo(orderInfo);

		ret = 1;
		msg = "申请成功！";
		json.put("ret", ret);
		json.put("msg", msg);
		super.renderJson(response, json.toJSONString());
		return null;
	}
}
