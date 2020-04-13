package com.ebiz.webapp.web.struts.manager.customer;

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

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.EntpDuiZhang;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;

public class MerchantCheckAction extends BaseCustomerAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		saveToken(request);

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		DynaBean dynaBean = (DynaBean) form;
		super.getsonSysModuleList(request, dynaBean);
		Pager pager = (Pager) dynaBean.get("pager");
		String st_add_date = (String) dynaBean.get("st_add_date");
		String en_add_date = (String) dynaBean.get("en_add_date");
		String confirm_state = (String) dynaBean.get("confirm_state");
		String is_check = (String) dynaBean.get("is_check");
		String order_type = (String) dynaBean.get("order_type");
		EntpDuiZhang entity = new EntpDuiZhang();
		if (StringUtils.isNotBlank(order_type)) {
			entity.setOrder_type(Integer.valueOf(order_type));
		}

		if (StringUtils.isNotBlank(st_add_date)) {
			entity.getMap().put("st_add_date", st_add_date);

		}
		if (StringUtils.isNotBlank(en_add_date)) {
			entity.getMap().put("en_add_date", en_add_date);
		}
		if (StringUtils.isNotBlank(confirm_state) && GenericValidator.isLong(confirm_state)) {
			entity.setConfirm_state(Integer.valueOf(confirm_state));
		}
		if (StringUtils.isNotBlank(is_check) && GenericValidator.isLong(is_check)) {
			entity.setIs_check(Integer.valueOf(is_check));
		}
		entity.setEntp_id(ui.getOwn_entp_id());
		Integer recordCount = getFacade().getEntpDuiZhangService().getEntpDuiZhangCount(entity);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());
		List<EntpDuiZhang> entityList = getFacade().getEntpDuiZhangService().getEntpDuiZhangPaginatedList(entity);
		request.setAttribute("entityList", entityList);
		return mapping.findForward("list");
	}

	public ActionForward listDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		Pager pager = (Pager) dynaBean.get("pager");
		EntpDuiZhang entpDZ = new EntpDuiZhang();
		entpDZ.setId(Integer.valueOf(id));
		entpDZ = getFacade().getEntpDuiZhangService().getEntpDuiZhang(entpDZ);
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setLink_check_id(Integer.valueOf(id));

		if (entpDZ.getOrder_type().intValue() == Keys.OrderType.ORDER_TYPE_90.getIndex()) {
			orderInfo.setOrder_type(entpDZ.getOrder_type());
		}
		// orderInfo.setIs_check(Keys.IsCleck.IS_CLECK_20.getIndex());
		// orderInfo.getMap().put("st_add_date", entpDZ.getAdd_check_date());
		// orderInfo.getMap().put("en_add_date", entpDZ.getEnd_check_date());
		Integer recordCount = super.getFacade().getOrderInfoService().getOrderInfoCount(orderInfo);
		pager.init(recordCount.longValue(), 10, pager.getRequestPage());
		orderInfo.getRow().setFirst(pager.getFirstRow());
		orderInfo.getRow().setCount(pager.getRowCount());
		List<OrderInfo> entityList = getFacade().getOrderInfoService().getOrderInfoPaginatedList(orderInfo);
		request.setAttribute("entityList_id", id);
		request.setAttribute("entityList", entityList);
		return new ActionForward("/../manager/customer/MerchantCheck/list_details.jsp");
	}

	public ActionForward reply(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		EntpDuiZhang entpDZ = new EntpDuiZhang();
		entpDZ.setId(Integer.valueOf(id));
		entpDZ = getFacade().getEntpDuiZhangService().getEntpDuiZhang(entpDZ);
		super.copyProperties(form, entpDZ);
		return new ActionForward("/../manager/customer/MerchantCheck/reply.jsp");

	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String confirm_state = (String) dynaBean.get("confirm_state");
		String code = "0", msg = "";

		if (StringUtils.isBlank(id) || !GenericValidator.isLong(id)) {
			msg = "参数错误！";
			super.ajaxReturnInfo(response, code, msg, null);
			return null;
		}
		EntpDuiZhang entpDZ = new EntpDuiZhang();
		entpDZ.setId(Integer.valueOf(id));
		entpDZ.setConfirm_state(Integer.valueOf(confirm_state));
		entpDZ.setUpdate_date(new Date());
		getFacade().getEntpDuiZhangService().modifyEntpDuiZhang(entpDZ);
		code = "1";
		super.renderJavaScript(response, "alert('".concat("确认成功").concat("');location.href='MerchantCheck.do?';"));
		return null;
	}
}
