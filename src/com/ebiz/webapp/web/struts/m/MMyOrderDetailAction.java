package com.ebiz.webapp.web.struts.m;

import java.math.BigDecimal;
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
import com.ebiz.webapp.domain.BaseImgs;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.OrderInfoDetails;
import com.ebiz.webapp.domain.OrderReturnInfo;
import com.ebiz.webapp.domain.UserBiRecord;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.WlCompInfo;
import com.ebiz.webapp.domain.WlOrderInfo;
import com.ebiz.webapp.web.Keys;

/**
 * @author Liu,Jia
 * @version 2013-07-02
 */
public class MMyOrderDetailAction extends MBaseWebAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.view(mapping, form, request, response);
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}

		DynaBean dynaBean = (DynaBean) form;
		String order_id = (String) dynaBean.get("order_id");
		String tuikuan_id = (String) dynaBean.get("tuikuan_id");
		String title = "订单详情";

		if (!GenericValidator.isLong(order_id)) {
			String msg = "参数有误，请联系管理员！";
			return showTipMsg(mapping, form, request, response, msg);
		}

		// 订单信息

		OrderInfo orderInfo = super.getOrderInfo(Integer.valueOf(order_id));

		if (null == orderInfo) {
			String msg = "订单信息不存在，请联系管理员！";
			return showTipMsg(mapping, form, request, response, msg);
		}
		// 支付方式
		if (orderInfo.getPay_type() != null && orderInfo.getPay_type() == Keys.PayType.PAY_TYPE_0.getIndex()) {
			UserBiRecord uRecord = new UserBiRecord();
			uRecord.setLink_id(Integer.valueOf(order_id));
			uRecord.getMap().put("bi_get_types",
					Keys.BiGetType.BI_OUT_TYPE_10.getIndex() + "," + Keys.BiGetType.BI_OUT_TYPE_150.getIndex());
			List<UserBiRecord> uList = getFacade().getUserBiRecordService().getUserBiRecordList(uRecord);
			BigDecimal fuxiao = new BigDecimal(0);
			BigDecimal yu_e = new BigDecimal(0);
			if (uList != null && uList.size() > 0) {
				fuxiao = uList.get(0).getFuxiao_no();
				yu_e = uList.get(0).getBi_no().subtract(fuxiao);
				request.setAttribute("fuxiao", fuxiao);
				request.setAttribute("yu_e", yu_e);
			}
		}

		if (null != orderInfo.getEntp_id()) {
			request.setAttribute("entpInfo", super.getEntpInfo(orderInfo.getEntp_id()));
		}

		super.showShippingAddressForOrderInfo(orderInfo);

		super.copyProperties(form, orderInfo);

		// 产品详细
		List<OrderInfoDetails> orderInfoDetailList = getOrderInfoDetailsList(orderInfo.getId());
		if (null != orderInfoDetailList && orderInfoDetailList.size() > 0) {
			for (OrderInfoDetails temp : orderInfoDetailList) {
				if (null != temp.getComm_id()) {
					temp.getMap().put("commInfo", super.getCommInfoOnlyById(temp.getComm_id()));
				}
			}
		}

		// 物流信息
		super.setWlOrderInfo(request, orderInfo);

		// title = "退款/换货审核";
		OrderReturnInfo orderReturnInfo = new OrderReturnInfo();
		orderReturnInfo.setOrder_id(orderInfo.getId());
		List<OrderReturnInfo> orderReturnInfoList = getFacade().getOrderReturnInfoService()
				.getOrderReturnInfoPaginatedList(orderReturnInfo);
		if ((null != orderReturnInfoList) && (orderReturnInfoList.size() > 0)) {
			for (OrderReturnInfo item : orderReturnInfoList) {
				List<BaseImgs> returnOrderInfoImgs = super.getBaseImgs(item.getId(),
						Keys.BaseImgsType.Base_Imgs_TYPE_20.getIndex());
				item.getMap().put("returnOrderInfoImgs", returnOrderInfoImgs);
			}
		}

		request.setAttribute("orderReturnInfoList", orderReturnInfoList);
		request.setAttribute("header_title", title);
		request.setAttribute("orderInfoDetailList", orderInfoDetailList);
		request.setAttribute("orderStateList", Keys.OrderState.values());
		request.setAttribute("payTypeList", Keys.PayType.values());
		return mapping.findForward("view");
	}

	public ActionForward updateWlOrderInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String order_id = (String) dynaBean.get("order_id");
		if (StringUtils.isBlank(order_id)) {
			String msg = "参数有误，请联系管理员！";
			return showTipMsg(mapping, form, request, response, msg);
		}

		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setId(Integer.valueOf(order_id));
		orderInfo = super.getFacade().getOrderInfoService().getOrderInfo(orderInfo);
		if (null == orderInfo) {
			String msg = "订单不存在！";
			return showTipMsg(mapping, form, request, response, msg);
		}

		WlOrderInfo wlOrderInfo = new WlOrderInfo();
		wlOrderInfo.setOrder_id(orderInfo.getId());
		wlOrderInfo = super.getFacade().getWlOrderInfoService().getWlOrderInfo(wlOrderInfo);
		super.copyProperties(form, wlOrderInfo);

		WlCompInfo wlCompInfo = new WlCompInfo();
		wlCompInfo.setIs_del(0);
		List<WlCompInfo> wlCompInfoList = super.getFacade().getWlCompInfoService().getWlCompInfoList(wlCompInfo);
		request.setAttribute("wlCompInfoList", wlCompInfoList);

		dynaBean.set("fahuo_remark", orderInfo.getFahuo_remark());

		return new ActionForward("/../m/MMyOrderDetail/updateWlOrderInfo.jsp");
	}

	public ActionForward saveWlOrderInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String order_id = (String) dynaBean.get("order_id");
		String fahuo_remark = (String) dynaBean.get("fahuo_remark");

		JSONObject data = new JSONObject();
		String ret = "0";
		String msg = "";

		if (StringUtils.isBlank(order_id)) {
			msg = "参数有误";
			data.put("ret", ret);
			data.put("msg", msg);
			super.renderJson(response, data.toJSONString());
			return null;
		}

		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setId(Integer.valueOf(order_id));
		orderInfo = super.getFacade().getOrderInfoService().getOrderInfo(orderInfo);
		if (null == orderInfo) {
			msg = "订单不存在！";
			data.put("ret", ret);
			data.put("msg", msg);
			super.renderJson(response, data.toJSONString());
			return null;
		}

		WlOrderInfo wlOrderInfoUpdate = new WlOrderInfo();
		super.copyProperties(wlOrderInfoUpdate, form);

		WlCompInfo wlCompInfo = new WlCompInfo();
		wlCompInfo.setIs_del(0);
		wlCompInfo.setId(wlOrderInfoUpdate.getWl_comp_id());
		wlCompInfo = super.getFacade().getWlCompInfoService().getWlCompInfo(wlCompInfo);
		if (null == wlCompInfo) {
			msg = "物流方式不存在！";
			data.put("ret", ret);
			data.put("msg", msg);
			super.renderJson(response, data.toString());
			return null;
		}
		wlOrderInfoUpdate.setWl_comp_name(wlCompInfo.getWl_comp_name());
		wlOrderInfoUpdate.setWl_code(wlCompInfo.getWl_code());

		orderInfo.setIs_ziti(0);
		if (wlCompInfo.getId().intValue() == 1) {// 1:如果是自提，订单类型为自提订单
			orderInfo.setIs_ziti(1);

		}

		if (StringUtils.isNotBlank(fahuo_remark)) {
			orderInfo.setFahuo_remark(fahuo_remark);
		}

		wlOrderInfoUpdate.getMap().put("update_order_info", orderInfo);
		super.getFacade().getWlOrderInfoService().modifyWlOrderInfo(wlOrderInfoUpdate);

		msg = "修改成功";
		ret = "1";
		data.put("ret", ret);
		data.put("msg", msg);
		super.renderJson(response, data.toJSONString());
		return null;

	}
}