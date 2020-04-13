package com.ebiz.webapp.web.struts.manager.admin;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.OrderInfoDetails;
import com.ebiz.webapp.domain.RwHbRule;
import com.ebiz.webapp.domain.RwYhqInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;

/**
 * @author Qin,Yue
 * @version 2011-04-22
 */
public class YHQInfoQueryAction extends BaseAdminAction {

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String is_del = (String) dynaBean.get("is_del");
		String is_used = (String) dynaBean.get("is_used");
		String title_like = (String) dynaBean.get("title_like");
		String st_used_date = (String) dynaBean.get("st_used_date");
		String en_used_date = (String) dynaBean.get("en_used_date");
		String trade_index = (String) dynaBean.get("trade_index");

		RwYhqInfo rwYhqInfo = new RwYhqInfo();
		if (null == is_del) {
			rwYhqInfo.setIs_del(0);
			dynaBean.set("is_del", "0");
		}
		super.copyProperties(rwYhqInfo, form);

		rwYhqInfo.getMap().put("trade_index", trade_index);
		rwYhqInfo.getMap().put("st_used_date", st_used_date);
		rwYhqInfo.getMap().put("en_used_date", en_used_date);
		rwYhqInfo.getMap().put("title_like", title_like);
		Integer recordCount = getFacade().getRwYhqInfoService().getRwYhqInfoCount(rwYhqInfo);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		rwYhqInfo.getRow().setFirst(pager.getFirstRow());
		rwYhqInfo.getRow().setCount(pager.getRowCount());

		List<RwYhqInfo> rwYhqInfoList = getFacade().getRwYhqInfoService().getRwYhqInfoPaginatedList(rwYhqInfo);
		request.setAttribute("rwYhqInfoList", rwYhqInfoList);

		request.setAttribute("hbClass", Keys.HbClass.values());
		request.setAttribute("originTypes", Keys.OriginType.values());
		request.setAttribute("ruleTypes", Keys.RuleType.values());

		return mapping.findForward("list");
	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		saveToken(request);
		DynaBean dynaBean = (DynaBean) form;
		return mapping.findForward("input");
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;

		String id = (String) dynaBean.get("id");
		String[] pks = (String[]) dynaBean.get("pks");
		String mod_id = (String) dynaBean.get("mod_id");
		UserInfo ui = super.getUserInfoFromSession(request);

		RwYhqInfo rwYhqInfo = new RwYhqInfo();
		rwYhqInfo.setIs_del(Keys.IsDel.IS_DEL_1.getIndex());
		rwYhqInfo.setDel_date(new Date());
		rwYhqInfo.setDel_uid(ui.getId());
		if (!StringUtils.isBlank(id) && GenericValidator.isLong(id)) {
			rwYhqInfo.setId(new Integer(id));
			getFacade().getRwYhqInfoService().modifyRwYhqInfo(rwYhqInfo);
			saveMessage(request, "entity.deleted");
		} else if (!ArrayUtils.isEmpty(pks)) {
			rwYhqInfo.getMap().put("pks", pks);
			getFacade().getRwYhqInfoService().modifyRwYhqInfo(rwYhqInfo);
			saveMessage(request, "entity.deleted");
		}
		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=").append(mod_id);
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(super.serialize(request, "id", "ids", "method")));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		saveToken(request);
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		RwHbRule rwHbRule = new RwHbRule();
		rwHbRule.setId(Integer.valueOf(id));
		rwHbRule = super.getFacade().getRwHbRuleService().getRwHbRule(rwHbRule);

		super.copyProperties(form, rwHbRule);

		return mapping.findForward("input");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);

		if (isCancelled(request)) {
			return list(mapping, form, request, response);
		}
		if (!isTokenValid(request)) {
			saveError(request, "errors.token");
			return list(mapping, form, request, response);
		}

		UserInfo ui = super.getUserInfoFromSession(request);
		DynaBean dynaBean = (DynaBean) form;
		String mod_id = (String) dynaBean.get("mod_id");
		String id = (String) dynaBean.get("id");

		RwHbRule rwHbRule = new RwHbRule();
		super.copyProperties(rwHbRule, dynaBean);

		if (null == rwHbRule.getId()) {
			rwHbRule.setAdd_date(new Date());
			rwHbRule.setAdd_uid(ui.getId());
			int rwHbRule_id = super.getFacade().getRwHbRuleService().createRwHbRule(rwHbRule);

			saveMessage(request, "entity.inerted");
		} else {
			rwHbRule.setUpdate_date(new Date());
			rwHbRule.setUpdate_uid(ui.getId());
			getFacade().getRwHbRuleService().modifyRwHbRule(rwHbRule);

			saveMessage(request, "entity.updated");
		}
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=").append(mod_id);
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(rwHbRule.getQueryString()));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		return forward;
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		super.setBaseDataListToSession(10, request);// 用户类型
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		RwYhqInfo rYhqInfo = new RwYhqInfo();
		rYhqInfo.setId(Integer.valueOf(id));
		rYhqInfo = super.getFacade().getRwYhqInfoService().getRwYhqInfo(rYhqInfo);

		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setYhq_id(rYhqInfo.getId());
		List<OrderInfo> orderInfoList = super.getFacade().getOrderInfoService().getOrderInfoList(orderInfo);
		if (null != orderInfoList && orderInfoList.size() > 0) {
			orderInfo = orderInfoList.get(0);

			// 订单明细
			OrderInfoDetails ods = new OrderInfoDetails();
			ods.setOrder_id(orderInfo.getId());
			List<OrderInfoDetails> oidsList = super.getFacade().getOrderInfoDetailsService()
					.getOrderInfoDetailsList(ods);
			BigDecimal good_sum_price = new BigDecimal("0");
			if ((null != oidsList) && (oidsList.size() > 0)) {
				for (OrderInfoDetails oid : oidsList) {
					good_sum_price = good_sum_price.add(oid.getGood_sum_price());
				}
			}
			request.setAttribute("good_sum_price", good_sum_price);
			orderInfo.getMap().put("oidsList", oidsList);

			super.showShippingAddressForOrderInfoForRequest(request, orderInfo);

			request.setAttribute("order_money", orderInfo.getOrder_money());
			request.setAttribute("trade_index", orderInfo.getTrade_index());
		}
		rYhqInfo.getMap().put("orderInfo", orderInfo);

		super.copyProperties(form, rYhqInfo);

		return mapping.findForward("view");
	}

}