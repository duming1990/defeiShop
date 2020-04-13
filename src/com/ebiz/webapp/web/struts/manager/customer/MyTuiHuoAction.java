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

import com.alibaba.fastjson.JSONObject;
import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.BaseData;
import com.ebiz.webapp.domain.BaseImgs;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.OrderInfoDetails;
import com.ebiz.webapp.domain.OrderReturnAudit;
import com.ebiz.webapp.domain.OrderReturnInfo;
import com.ebiz.webapp.domain.OrderReturnMsg;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.WlCompInfo;
import com.ebiz.webapp.web.Keys;

/**
 * @author Liyuan
 * @version 2013-04-02
 */
public class MyTuiHuoAction extends BaseCustomerAction {

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
		String st_add_date = (String) dynaBean.get("st_add_date");
		String en_add_date = (String) dynaBean.get("en_add_date");
		String trade_index = (String) dynaBean.get("trade_index");
		String order_type = (String) dynaBean.get("order_type");
		String is_del = (String) dynaBean.get("is_del");
		String return_no = (String) dynaBean.get("return_no");
		Pager pager = (Pager) dynaBean.get("pager");

		OrderReturnInfo entity = new OrderReturnInfo();
		super.copyProperties(entity, form);
		if (StringUtils.isNotBlank(is_del)) {
			entity.setIs_del(Integer.valueOf(is_del));
		} else {
			entity.setIs_del(0);
		}
		entity.setUser_id(ui.getId());
		entity.getMap().put("st_add_date", st_add_date);
		entity.getMap().put("en_add_date", en_add_date);
		entity.getMap().put("comm_name_like", comm_name_like);
		entity.getMap().put("trade_index", trade_index);
		entity.getMap().put("order_type", order_type);
		entity.getMap().put("return_no", return_no);

		Integer recordCount = getFacade().getOrderReturnInfoService().getOrderReturnInfoCount(entity);
		pager.init(recordCount.longValue(), 10, pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<OrderReturnInfo> list = getFacade().getOrderReturnInfoService()
				.getOrderReturnInfoPaginatedList(entity);
		if ((null != list) && (list.size() > 0)) {
			for (OrderReturnInfo temp : list) {
				OrderInfo orderInfo = new OrderInfo();
				orderInfo.setId(temp.getOrder_id());
				orderInfo = getFacade().getOrderInfoService().getOrderInfo(orderInfo);
				if (orderInfo != null) {
					OrderInfoDetails orderInfoDetails = new OrderInfoDetails();
					orderInfoDetails.setOrder_id(orderInfo.getId());
					List<OrderInfoDetails> orderInfoDetailsList = super.getFacade().getOrderInfoDetailsService()
							.getOrderInfoDetailsList(orderInfoDetails);
					orderInfo.setOrderInfoDetailsList(orderInfoDetailsList);
					temp.getMap().put("orderInfo", orderInfo);
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

		request.setAttribute("entityList", list);

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

		String img_id_card_zm = (String) dynaBean.get("img_id_card_zm");
		String img_id_card_fm = (String) dynaBean.get("img_id_card_fm");
		String opt_note = (String) dynaBean.get("opt_note");
		String id_card = (String) dynaBean.get("id_card");
		String[] basefiles = { img_id_card_zm, img_id_card_fm };

		UserInfo userInfo = super.getUserInfoFromSession(request);

		OrderReturnInfo entity = new OrderReturnInfo();
		entity.setId(Integer.valueOf(id));
		entity = getFacade().getOrderReturnInfoService().getOrderReturnInfo(entity);
		super.copyProperties(entity, form);

		// imgs.setImg_id_card_zm(img_id_card_zm_s);
		// imgs.setImg_id_card_fm(img_id_card_fm_s);
		//
		// List<UploadFile> uploadFileList = super.uploadFile(form, true, false, Keys.NEWS_INFO_IMAGE_SIZE);
		// for (UploadFile uploadFile : uploadFileList) {
		// if (StringUtils.isNotBlank(uploadFile.getFileSavePath())) {
		// if ("img_id_card_zm".equalsIgnoreCase(uploadFile.getFormName())) {
		// entity.setImg_id_card_zm(uploadFile.getFileSavePath());
		// }
		// if ("img_id_card_fm".equalsIgnoreCase(uploadFile.getFormName())) {
		// entity.setImg_id_card_fm(uploadFile.getFileSavePath());
		// }
		// }
		// }

		if (StringUtils.isBlank(id) || !GenericValidator.isLong(id)) {
			String msg = "参数有误！";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		if (entity.getAudit_state() == 2) {// 【商家审核成功】

			if (entity.getReturn_way() == 1) {// 【商家审核成功-退货退款】

				if (entity.getFh_fee() == 1) {// 【商家审核成功-退货退款-买家承担物流费用】

					// 根据订单明细表中(order_info_details)的物流费用(matflow_price)，
					// 返还给消费者退款金额（user_info{电子币}），同时插入user_bi_record
					entity.getMap().put("return_way_1", true);
					entity.getMap().put("fh_fee_1", true);

				} else {// 【商家审核成功-退货退款-卖家承担物流费】
					// 根据订单明细表(order_info_details)中的金额{good_price}，给消费者返还电子币{bi_dianzi)}
					// 从商家货款(user_info{bi_huokuan})中扣除退货表(order_return_info)中的物流费{matflow_price}

					entity.getMap().put("return_way_1", true);
					entity.getMap().put("fh_fee_0", true);
				}
			}
			if (entity.getReturn_way() == 2) {// 【商家审核成功-换货】

				if (entity.getFh_fee() == 1) {// 【商家审核成功-换货-买家承担物流费用】
					entity.getMap().put("return_way_2", true);
					entity.getMap().put("fh_fee_1", true);

				} else {// 【商家审核成功-换货-卖家承担物流费】
					entity.getMap().put("return_way_2", true);
					entity.getMap().put("fh_fee_0", true);
				}
			}
			if (entity.getReturn_way() == 3) {// 【商家审核成功-退款】
				entity.getMap().put("return_way_3", true);
			}

			// entity.setAudit_state(1);
			entity.setId(new Integer(id));
			entity.setAudit_date(new Date());

			entity.getMap().put("create_order_return_audit", true);
			entity.getMap().put("create_order_return_money", true);
		}
		if (entity.getAudit_state() == -2) {// 商家审核不通过，创建退货反馈表
			OrderReturnMsg orderReturnMsg = new OrderReturnMsg();
			orderReturnMsg.setOrder_return_id(entity.getId());
			orderReturnMsg.setUser_id(entity.getUser_id());
			orderReturnMsg.setUser_name(entity.getUser_name());
			orderReturnMsg.setAdd_date(new Date());
			orderReturnMsg.setInfo_type(1);
			orderReturnMsg.setPar_id(entity.getOrder_detail_id());
			super.getFacade().getOrderReturnMsgService().createOrderReturnMsg(orderReturnMsg);
		}
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

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
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
				OrderInfoDetails orderInfoDetails = new OrderInfoDetails();
				orderInfoDetails.setOrder_id(orderInfo.getId());
				List<OrderInfoDetails> orderInfoDetailsList = super.getFacade().getOrderInfoDetailsService()
						.getOrderInfoDetailsList(orderInfoDetails);
				orderInfo.setOrderInfoDetailsList(orderInfoDetailsList);
				request.setAttribute("orderInfo", orderInfo);
			}

			BaseImgs imgs = new BaseImgs();
			imgs.setLink_id(Integer.valueOf(id));
			imgs.setImg_type(Keys.BaseImgsType.Base_Imgs_TYPE_20.getIndex());
			List<BaseImgs> imgsList = getFacade().getBaseImgsService().getBaseImgsList(imgs);
			logger.info("====imgslist===" + imgsList.size());
			request.setAttribute("imgsList", imgsList);

			OrderReturnAudit audit = new OrderReturnAudit();
			audit.setOrder_return_id(entity.getId());
			audit = getFacade().getOrderReturnAuditService().getOrderReturnAudit(audit);
			if (null != audit) {
				request.setAttribute("remark", audit.getRemark());
			}

			// the line below is added for pagination
			entity.setQueryString(super.serialize(request, "id", "method"));
			// end
			super.copyProperties(form, entity);

			// 物流公司
			WlCompInfo wlCompInfo = new WlCompInfo();
			wlCompInfo.setIs_del(0);
			List<WlCompInfo> wlCompInfoList = super.getFacade().getWlCompInfoService().getWlCompInfoList(wlCompInfo);
			request.setAttribute("wlCompInfoList", wlCompInfoList);

			return new ActionForward("/../manager/customer/MyTuiHuo/view.jsp");
		} else {
			this.saveError(request, "errors.Integer", new String[] { id });
			return mapping.findForward("list");
		}
	}

	public ActionForward fankui(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		OrderReturnInfo entity = new OrderReturnInfo();
		entity.setId(Integer.valueOf(id));
		entity = super.getFacade().getOrderReturnInfoService().getOrderReturnInfo(entity);
		if (null == entity) {
			String msg = "售后记录不存在！";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		BaseData returnType = new BaseData();
		returnType.setId(entity.getReturn_type());
		returnType.setIs_del(0);
		returnType = getFacade().getBaseDataService().getBaseData(returnType);
		if (null != returnType) {
			request.setAttribute("returnTypeName", returnType.getType_name());
		}

		super.copyProperties(form, entity);
		// request.setAttribute("entity", entity);
		return new ActionForward("/customer/TuiHuoAudit/fankui.jsp");
	}

	public ActionForward insertOrderReturnMsg(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String content = (String) dynaBean.get("content");

		JSONObject data = new JSONObject();

		String msg = "";
		String ret = "";

		if (!GenericValidator.isLong(id)) {
			data.put("ret", "0");
			data.put("msg", "参数有误！");
			super.renderJson(response, data.toString());
			return null;
		}

		OrderReturnInfo entity = new OrderReturnInfo();
		entity.setId(Integer.valueOf(id));
		entity = super.getFacade().getOrderReturnInfoService().getOrderReturnInfo(entity);
		if (entity == null) {
			data.put("ret", "0");
			data.put("msg", "售后记录不存在！");
			super.renderJson(response, data.toString());
			return null;
		}

		OrderReturnMsg orderReturnMsg = new OrderReturnMsg();
		orderReturnMsg.setOrder_return_id(entity.getId());
		orderReturnMsg.setUser_id(entity.getUser_id());
		orderReturnMsg.setUser_name(entity.getUser_name());
		orderReturnMsg.setAdd_date(new Date());
		orderReturnMsg.setInfo_type(1);
		orderReturnMsg.setPar_id(entity.getOrder_detail_id());
		orderReturnMsg.setContent(content);
		int i = super.getFacade().getOrderReturnMsgService().createOrderReturnMsg(orderReturnMsg);
		if (i > 0) {
			entity.setAudit_state(3);// 待平台处理

			data.put("ret", "1");
			data.put("msg", "反馈提交成功！");
			super.renderJson(response, data.toString());
			return null;
		}

		data.put("ret", ret);
		data.put("msg", msg);
		super.renderJson(response, data.toString());
		return null;
	}
	// public ActionForward updateState(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	// HttpServletResponse response) throws Exception {
	// UserInfo ui = super.getUserInfoFromSession(request);
	// if (null == ui) {
	// String msg = "您还未登录，请先登录系统！";
	// super.showMsgForManager(request, response, msg);
	// return null;
	// }
	//
	// DynaBean dynaBean = (DynaBean) form;
	// String id = (String) dynaBean.get("id");
	// String state = (String) dynaBean.get("state");
	// String order_password = (String) dynaBean.get("order_password");
	//
	// JSONObject data = new JSONObject();
	// if (!GenericValidator.isLong(id)) {
	// data.put("ret", "0");
	// data.put("msg", "参数有误！");
	// super.renderJson(response, data.toString());
	// return null;
	// }
	//
	// OrderReturnInfo orderQuery = new OrderReturnInfo();
	// orderQuery.setId(Integer.valueOf(id));
	// orderQuery = super.getFacade().getOrderReturnInfoService().getOrderReturnInfo(orderQuery);
	// if (orderQuery == null) {
	// data.put("ret", "0");
	// data.put("msg", "订单不存在！");
	// super.renderJson(response, data.toString());
	// return null;
	// }
	//
	// if (orderQuery.getIs_opt() == 1) {
	// data.put("ret", "0");
	// data.put("msg", "订单正在操作中，请稍后......！");
	// super.renderJson(response, data.toString());
	// return null;
	// }
	// updateOrderReturnInfoOpt(1, orderQuery.getId());
	//
	// OrderReturnInfo entity = new OrderReturnInfo();
	// entity.setId(Integer.valueOf(id));
	// entity.setOrder_state(Integer.valueOf(state));
	// if (StringUtils.equals("40", state)) {
	// entity.setQrsh_date(new Date());
	// }
	//
	// if (orderQuery.getOrder_type().intValue() == Keys.OrderType.ORDER_TYPE_10.getIndex() && state.equals("40")) {
	// if (StringUtils.isBlank(order_password)) {
	// data.put("ret", "0");
	// data.put("msg", "订单密码为空！");
	// super.renderJson(response, data.toString());
	// return null;
	// }
	// if (!(order_password.trim()).equals(orderQuery.getOrder_password())) {
	// data.put("ret", "0");
	// data.put("msg", "订单密码不正确！");
	// super.renderJson(response, data.toString());
	// return null;
	// }
	// entity.getMap().put("xiaofei_success_update_link_table", "true");
	// }
	//
	// String msg = "操作失败或您已操作过";
	// String ret = "0";
	//
	// if ("40".equals(state) && orderQuery.getOrder_state().intValue() == Keys.OrderState.ORDER_STATE_10.getIndex()) {
	// msg = "确认订单成功！";
	// ret = "1";
	// entity.getMap().put("opt_order_state", Keys.OrderState.ORDER_STATE_10.getIndex());// 确认消费，前一个状态：10
	// }
	// if ("20".equals(state) && orderQuery.getOrder_state().intValue() == Keys.OrderState.ORDER_STATE_10.getIndex()) {
	// msg = "发货成功！";
	// ret = "1";
	// entity.getMap().put("opt_order_state", Keys.OrderState.ORDER_STATE_10.getIndex());// 确认消费，前一个状态：10
	// }
	//
	// if (ret.equals("1")) {
	// int flag = super.getFacade().getOrderReturnInfoService().modifyOrderReturnInfo(entity);
	// updateOrderReturnInfoOpt(0, orderQuery.getId());
	//
	// if (flag == 0) {
	// data.put("ret", "0");
	// data.put("msg", "返现卡不足，请联系管理员！");
	// super.renderJson(response, data.toString());
	// return null;
	// }
	// if (flag == -1) {
	// msg = "系统繁忙，请稍后重试";
	// ret = "0";
	// super.renderJson(response, data.toString());
	// return null;
	// }
	// // 订单完成站内信
	// if ("40".equals(state)
	// && orderQuery.getOrder_state().intValue() == Keys.OrderState.ORDER_STATE_10.getIndex()) {
	// // 给用户发站内信
	// UserInfo uireal = super.getUserInfo(orderQuery.getAdd_user_id());
	// String msg_content = StringUtils.replace(SMS.sms_06, "{0}", uireal.getUser_name());
	// msg_content = StringUtils.replace(msg_content, "{1}", orderQuery.getTrade_index());
	// super.sendMsg(1, uireal.getId(), "认证会员订单完成", msg_content);
	//
	// // String msg1 = StringUtils.replace(SMS.order_success_user_sms, "{0}", uireal.getUser_name());
	// // msg1 = StringUtils.replace(msg1, "{1}", orderQuery.getTrade_index());
	// // SmsUtils.sendMessage(msg1, uireal.getMobile());
	//
	// // 给商家发站内信
	// // if (null != orderQuery.getEntp_id()) {
	// // uireal = super.getUserInfoWithEntpId(orderQuery.getEntp_id());
	// // msg_content = StringUtils.replace(SMS.order_success_entp, "{0}", uireal.getUser_name());
	// // msg_content = StringUtils.replace(msg_content, "{1}", orderQuery.getTrade_index());
	// // super.sendMsg(1, uireal.getId(), "认证商家订单完成", msg_content);
	// // }
	// }
	// }
	// data.put("ret", ret);
	// data.put("msg", msg);
	// super.renderJson(response, data.toString());
	// return null;
	// }
	//
	// public ActionForward orderConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	// HttpServletResponse response) throws Exception {
	//
	// DynaBean dynaBean = (DynaBean) form;
	// String order_id = (String) dynaBean.get("order_id");
	// OrderReturnInfo OrderReturnInfo = new OrderReturnInfo();
	// OrderReturnInfo.setId(Integer.valueOf(order_id));
	// OrderReturnInfo = super.getFacade().getOrderReturnInfoService().getOrderReturnInfo(OrderReturnInfo);
	// if (null == OrderReturnInfo) {
	// String msg = "订单不存在！";
	// super.showMsgForManager(request, response, msg);
	// return null;
	// }
	// if (OrderReturnInfo.getOrder_state() != Keys.OrderState.ORDER_STATE_10.getIndex()) {
	// String msg = "订单状态也改变！";
	// super.showMsgForManager(request, response, msg);
	// return null;
	// }
	//
	// request.setAttribute("OrderReturnInfo", OrderReturnInfo);
	// return new ActionForward("/customer/MyOrderEntp/orderConfirm.jsp");
	// }
	//
	// public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	// HttpServletResponse response) throws Exception {
	// UserInfo ui = super.getUserInfoFromSession(request);
	// if (null == ui) {
	// String msg = "您还未登录，请先登录系统！";
	// super.showMsgForManager(request, response, msg);
	// return null;
	// }
	// if (!StringUtils.equalsIgnoreCase(request.getMethod(), "POST")) {
	// response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	// }
	//
	// DynaBean dynaBean = (DynaBean) form;
	// String id = (String) dynaBean.get("id");
	// String mod_id = (String) dynaBean.get("mod_id");
	// String par_id = (String) dynaBean.get("par_id");
	// if (!StringUtils.isBlank(id) && GenericValidator.isLong(id)) {
	// OrderReturnInfo OrderReturnInfo = new OrderReturnInfo();
	// OrderReturnInfo.setId(Integer.valueOf(id));
	// OrderReturnInfo.setOrder_state(Keys.OrderState.ORDER_STATE_90.getIndex());
	// getFacade().getOrderReturnInfoService().modifyOrderReturnInfo(OrderReturnInfo);
	// }
	//
	// StringBuffer pathBuffer = new StringBuffer();
	// pathBuffer.append(mapping.findForward("success").getPath());
	// pathBuffer.append("&");
	// pathBuffer.append(super.encodeSerializedQueryString(super.serialize(request, "id", "method")));
	// pathBuffer.append("&mod_id=" + mod_id);
	// pathBuffer.append("&par_id=" + par_id);
	// ActionForward forward = new ActionForward(pathBuffer.toString(), true);
	// // end
	// return forward;
	// }
	//
	// public ActionForward orderFh(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	// HttpServletResponse response) throws Exception {
	//
	// DynaBean dynaBean = (DynaBean) form;
	// String order_id = (String) dynaBean.get("order_id");
	// OrderReturnInfo OrderReturnInfo = new OrderReturnInfo();
	// OrderReturnInfo.setId(Integer.valueOf(order_id));
	// OrderReturnInfo = super.getFacade().getOrderReturnInfoService().getOrderReturnInfo(OrderReturnInfo);
	// if (null == OrderReturnInfo) {
	// String msg = "订单不存在！";
	// super.showMsgForManager(request, response, msg);
	// return null;
	// }
	// if (OrderReturnInfo.getOrder_state() != Keys.OrderState.ORDER_STATE_10.getIndex()) {
	// String msg = "订单状态也改变！";
	// super.showMsgForManager(request, response, msg);
	// return null;
	// }
	//
	// WlOrderReturnInfo wlOrderReturnInfo = new WlOrderReturnInfo();
	// wlOrderReturnInfo.setOrder_id(Integer.valueOf(order_id));
	// wlOrderReturnInfo.setIs_del(0);
	// wlOrderReturnInfo = super.getFacade().getWlOrderReturnInfoService().getWlOrderReturnInfo(wlOrderReturnInfo);
	// if (null != wlOrderReturnInfo) {
	// // String msg = "该订单已经发货，不能再进行发货操作！";
	// // super.showMsgForManager(request, response, msg);
	// // return null;
	// super.copyProperties(form, wlOrderReturnInfo);
	// dynaBean.set("wl_order_id", wlOrderReturnInfo.getId());
	// dynaBean.set("fahuo_remark", OrderReturnInfo.getFahuo_remark());
	// }
	//
	// WlCompInfo wlCompInfo = new WlCompInfo();
	// wlCompInfo.setIs_del(0);
	// List<WlCompInfo> wlCompInfoList = super.getFacade().getWlCompInfoService().getWlCompInfoList(wlCompInfo);
	// request.setAttribute("wlCompInfoList", wlCompInfoList);
	//
	// request.setAttribute("OrderReturnInfo", OrderReturnInfo);
	// return new ActionForward("/customer/MyOrderEntp/orderFh.jsp");
	// }
	//
	// public ActionForward delayShouhuo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	// HttpServletResponse response) throws Exception {
	//
	// DynaBean dynaBean = (DynaBean) form;
	// String order_id = (String) dynaBean.get("order_id");
	// OrderReturnInfo OrderReturnInfo = new OrderReturnInfo();
	// OrderReturnInfo.setId(Integer.valueOf(order_id));
	// OrderReturnInfo = super.getFacade().getOrderReturnInfoService().getOrderReturnInfo(OrderReturnInfo);
	// if (null == OrderReturnInfo) {
	// String msg = "订单不存在！";
	// super.showMsgForManager(request, response, msg);
	// return null;
	// }
	// if (OrderReturnInfo.getOrder_state() != Keys.OrderState.ORDER_STATE_40.getIndex()) {
	// String msg = "订单状态也改变！";
	// super.showMsgForManager(request, response, msg);
	// return null;
	// }
	// super.copyProperties(form, OrderReturnInfo);
	//
	// request.setAttribute("today", new Date());
	// return new ActionForward("/customer/MyOrderEntp/delayShouhuo.jsp");
	// }
	//
	// public ActionForward saveDelay(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	// HttpServletResponse response) throws Exception {
	// UserInfo ui = super.getUserInfoFromSession(request);
	// if (null == ui) {
	// String msg = "您还未登录，请先登录系统！";
	// super.showMsgForManager(request, response, msg);
	// return null;
	// }
	// if (isCancelled(request)) {
	// return list(mapping, form, request, response);
	// }
	// if (!isTokenValid(request)) {
	// saveError(request, "errors.token");
	// return list(mapping, form, request, response);
	// }
	// resetToken(request);
	// OrderReturnInfo entity = new OrderReturnInfo();
	// super.copyProperties(entity, form);
	//
	// OrderReturnInfo OrderReturnInfo = new OrderReturnInfo();
	// OrderReturnInfo.setId(entity.getId());
	// OrderReturnInfo = super.getFacade().getOrderReturnInfoService().getOrderReturnInfo(OrderReturnInfo);
	//
	// if (OrderReturnInfo.getOrder_state() != Keys.OrderState.ORDER_STATE_40.getIndex()) {
	// String msg = "订单状态也改变！";
	// super.showMsgForManager(request, response, msg);
	// return null;
	// }
	// OrderReturnInfo = new OrderReturnInfo();
	// OrderReturnInfo.setId(entity.getId());
	// OrderReturnInfo.setFinish_date(entity.getFinish_date());
	// OrderReturnInfo.setDelay_shouhuo(new Integer(1));
	// super.getFacade().getOrderReturnInfoService().modifyOrderReturnInfo(OrderReturnInfo);
	//
	// super.renderJavaScript(response, "window.onload=function(){window.parent.location.reload();}");
	// return null;
	// }
}
