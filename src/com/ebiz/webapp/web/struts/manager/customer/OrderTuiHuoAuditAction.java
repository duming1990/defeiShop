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
import com.ebiz.webapp.domain.BaseData;
import com.ebiz.webapp.domain.BaseImgs;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.OrderReturnAudit;
import com.ebiz.webapp.domain.OrderReturnInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.WlCompInfo;
import com.ebiz.webapp.web.Keys;

/**
 * @author Liyuan
 * @version 2013-04-02
 */
public class OrderTuiHuoAuditAction extends BaseCustomerAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='../../login.shtml'}");
			return null;
		}

		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);

		String comm_name_like = (String) dynaBean.get("comm_name_like");
		String user_name_like = (String) dynaBean.get("user_name_like");
		String st_add_date = (String) dynaBean.get("st_add_date");
		String en_add_date = (String) dynaBean.get("en_add_date");
		String st_audit_date = (String) dynaBean.get("st_audit_date");
		String en_audit_date = (String) dynaBean.get("en_audit_date");
		String trade_index = (String) dynaBean.get("trade_index");
		String order_type = (String) dynaBean.get("order_type");
		String is_del = (String) dynaBean.get("is_del");
		Pager pager = (Pager) dynaBean.get("pager");
		String return_no = (String) dynaBean.get("return_no");
		logger.info("===st_add_date===" + st_add_date);
		logger.info("===en_add_date===" + en_add_date);

		OrderReturnInfo entity = new OrderReturnInfo();
		super.copyProperties(entity, form);
		if (StringUtils.isNotBlank(is_del)) {
			entity.setIs_del(Integer.valueOf(is_del));
		} else {
			entity.setIs_del(0);
		}

		entity.getMap().put("st_add_date", st_add_date);
		entity.getMap().put("en_add_date", en_add_date);
		entity.getMap().put("st_audit_date", st_audit_date);
		entity.getMap().put("en_audit_date", en_audit_date);
		entity.getMap().put("comm_name_like", comm_name_like);
		entity.getMap().put("user_name_like", user_name_like);

		entity.getMap().put("trade_index", trade_index);
		entity.getMap().put("order_type", order_type);
		entity.getMap().put("return_no", return_no);

		// if (StringUtils.isNotBlank(trade_index)) {
		// entity.setTrade_index(trade_index.trim());
		// }

		// if (StringUtils.isBlank(order_type)) {
		// entity.setOrder_type(Keys.OrderType.ORDER_TYPE_10.getIndex());
		// }

		Integer recordCount = getFacade().getOrderReturnInfoService().getOrderReturnInfoCount(entity);
		pager.init(recordCount.longValue(), 10, pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<OrderReturnInfo> OrderReturnInfoList = getFacade().getOrderReturnInfoService()
				.getOrderReturnInfoPaginatedList(entity);
		if ((null != OrderReturnInfoList) && (OrderReturnInfoList.size() > 0)) {
			for (OrderReturnInfo temp : OrderReturnInfoList) {
				if (temp.getReturn_type() != null) {
					BaseData returnType = new BaseData();
					returnType.setId(temp.getReturn_type());
					returnType.setIs_del(0);
					returnType = getFacade().getBaseDataService().getBaseData(returnType);
					if (null != returnType) {
						temp.getMap().put("returnTypeName", returnType.getType_name());
					}
					// OrderInfo order = new OrderInfo();
					// order.setId(temp.getOrder_id());
					// order = getFacade().getOrderInfoService().getOrderInfo(order);
					// if (order != null) {
					// temp.getMap().put("trade_index", order.getTrade_index());
					// }
				}
			}
		}
		BaseData returnType = new BaseData();
		returnType.setType(10000);
		returnType.setIs_del(0);
		List<BaseData> returnTypeList = getFacade().getBaseDataService().getBaseDataList(returnType);
		if (null != returnTypeList && returnTypeList.size() > 0) {
			request.setAttribute("returnTypeList", returnTypeList);
		}

		request.setAttribute("entityList", OrderReturnInfoList);

		// List<OrderType> orderTypeList = new ArrayList<Keys.OrderType>();
		// orderTypeList.add(Keys.OrderType.valueOf(Keys.OrderType.ORDER_TYPE_10.getSonType().toString()));
		// orderTypeList.add(Keys.OrderType.valueOf(Keys.OrderType.ORDER_TYPE_11.getSonType().toString()));
		// orderTypeList.add(Keys.OrderType.valueOf(Keys.OrderType.ORDER_TYPE_100.getSonType().toString()));
		// orderTypeList.add(Keys.OrderType.valueOf(Keys.OrderType.ORDER_TYPE_40.getSonType().toString()));
		//
		// request.setAttribute("orderTypeList", orderTypeList);
		// request.setAttribute("payTypeList", Keys.PayType.values());
		// request.setAttribute("orderStateList", Keys.OrderState.values());

		return mapping.findForward("list");

	}

	public ActionForward audit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='../../login.shtml'}");
			return null;
		}

		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);

		String id = (String) dynaBean.get("id");

		if (StringUtils.isBlank(id)) {
			String msg = "id为空";
			super.renderJavaScript(response, "alert('" + msg + "');history.back();");
			return null;
		}

		if (GenericValidator.isLong(id)) {
			OrderReturnInfo entity = new OrderReturnInfo();
			entity.setId(new Integer(id));
			entity = getFacade().getOrderReturnInfoService().getOrderReturnInfo(entity);
			if (null == entity) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");

			}

			BaseData returnType = new BaseData();
			returnType.setId(entity.getReturn_type());
			returnType.setIs_del(0);
			returnType = getFacade().getBaseDataService().getBaseData(returnType);
			if (null != returnType) {
				request.setAttribute("returnTypeName", returnType.getType_name());
			}
			OrderInfo orderInfo = new OrderInfo();
			orderInfo.setId(entity.getOrder_id());
			orderInfo = getFacade().getOrderInfoService().getOrderInfo(orderInfo);
			if (null != orderInfo) {
				request.setAttribute("trade_index", orderInfo.getTrade_index());
			}

			BaseImgs imgs = new BaseImgs();
			imgs.setLink_id(Integer.valueOf(id));
			imgs.setImg_type(Keys.BaseImgsType.Base_Imgs_TYPE_20.getIndex());
			List<BaseImgs> imgsList = getFacade().getBaseImgsService().getBaseImgsList(imgs);
			logger.info("====imgslist===" + imgsList.size());
			request.setAttribute("imgsList", imgsList);

			// the line below is added for pagination
			entity.setQueryString(super.serialize(request, "id", "method"));
			// end
			super.copyProperties(form, entity);

			// 物流公司
			WlCompInfo wlCompInfo = new WlCompInfo();
			wlCompInfo.setIs_del(0);
			List<WlCompInfo> wlCompInfoList = super.getFacade().getWlCompInfoService().getWlCompInfoList(wlCompInfo);
			request.setAttribute("wlCompInfoList", wlCompInfoList);

			if (entity.getAudit_state() == 2 || entity.getAudit_state() == 1) {
				OrderReturnAudit audit = new OrderReturnAudit();
				audit.setOrder_return_id(entity.getId());
				List<OrderReturnAudit> auditList = getFacade().getOrderReturnAuditService().getOrderReturnAuditList(
						audit);
				if (null != auditList && auditList.size() > 0) {
					dynaBean.set("remark", auditList.get(0).getRemark());
				}

			}
			dynaBean.set("return_way", entity.getExpect_return_way());

			return new ActionForward("/../manager/customer/TuiHuoAudit/audit.jsp");
		} else {
			this.saveError(request, "errors.Integer", new String[] { id });
			return mapping.findForward("list");
		}
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String is_audit = (String) dynaBean.get("is_audit");
		String mod_id = (String) dynaBean.get("mod_id");
		String remark = (String) dynaBean.get("remark");

		OrderReturnInfo entity = new OrderReturnInfo();
		entity.setId(Integer.valueOf(id));
		entity = getFacade().getOrderReturnInfoService().getOrderReturnInfo(entity);
		super.copyProperties(entity, form);

		if (StringUtils.isBlank(id) || !GenericValidator.isLong(id)) {
			String msg = "参数有误！";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		if (entity.getAudit_state() == 2) {// 【商家审核成功】

			// entity.setAudit_state(1);
			entity.setId(new Integer(id));
			entity.setAudit_date(new Date());

			entity.getMap().put("create_order_return_audit", true);
			entity.getMap().put("create_order_return_money", true);
			entity.getMap().put("remark", remark);
		}

		entity.setAudit_date(new Date());
		// entity.getMap().put("is_audit", true);
		super.getFacade().getOrderReturnInfoService().modifyOrderReturnInfo(entity);
		if (StringUtils.isNotBlank(is_audit)) {
			saveMessage(request, "entity.audit");
		} else {
			saveMessage(request, "entity.updated");
		}
		// return null;

		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
		pathBuffer.append("&mod_id=" + mod_id);
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}
}
