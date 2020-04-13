package com.ebiz.webapp.web.struts.m;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.BaseBrandInfo;
import com.ebiz.webapp.domain.BaseData;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.CommInfoPoors;
import com.ebiz.webapp.domain.CommInfoTags;
import com.ebiz.webapp.domain.CommTczhPrice;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.Freight;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.OrderInfoDetails;
import com.ebiz.webapp.domain.OrderReturnInfo;
import com.ebiz.webapp.domain.PdImgs;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.WlCompInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.Keys.OrderState;
import com.ebiz.webapp.web.util.DateTools;

public class MMyOrderReturnAction extends MBaseWebAction {
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.add(mapping, form, request, response);
	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);

		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}
		request.setAttribute("header_title", "我的订单");

		DynaBean dynaBean = (DynaBean) form;
		String order_detail_id = (String) dynaBean.get("id");

		OrderInfoDetails orderInfoDetails = new OrderInfoDetails();
		orderInfoDetails.setId(new Integer(order_detail_id));
		orderInfoDetails = getFacade().getOrderInfoDetailsService().getOrderInfoDetails(orderInfoDetails);
		if (null != orderInfoDetails) {
			request.setAttribute("orderInfoDetails", orderInfoDetails);
			// copyProperties(form, orderInfoDetails);
		}
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setId(orderInfoDetails.getOrder_id());
		orderInfo = getFacade().getOrderInfoService().getOrderInfo(orderInfo);
		request.setAttribute("orderInfo", orderInfo);

		BaseData baseData = new BaseData();
		baseData.setType(10000);
		List<BaseData> baseDataList = getFacade().getBaseDataService().getBaseDataList(baseData);
		request.setAttribute("baseDataList", baseDataList);

		// 物流公司
		WlCompInfo wlCompInfo = new WlCompInfo();
		wlCompInfo.setIs_del(0);
		List<WlCompInfo> wlCompInfoList = super.getFacade().getWlCompInfoService().getWlCompInfoList(wlCompInfo);
		request.setAttribute("wlCompInfoList", wlCompInfoList);

		request.setAttribute("return_link_man", ui.getReal_name());
		request.setAttribute("return_tel", ui.getMobile());
		dynaBean.set("expect_return_way", 2);

		return mapping.findForward("input");

	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		if (StringUtils.isBlank(id)) {
			String msg = super.getMessage(request, "参数不能为空");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}

		if (GenericValidator.isLong(id)) {
			CommInfo entity = new CommInfo();
			entity.setId(Integer.valueOf(id));
			entity = getFacade().getCommInfoService().getCommInfo(entity);

			if (null == entity) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");
			} else {
				List<PdImgs> CommImgsList = entity.getCommImgsList();
				for (int i = 1; i <= CommImgsList.size(); i++) {
					dynaBean.set("base_files" + i + "_file", CommImgsList.get(i - 1).getFile_path());
				}
				int CommImgsListCount = 0;
				if (CommImgsList != null) {
					CommImgsListCount = CommImgsList.size();
				}
				request.setAttribute("CommImgsListCount", CommImgsListCount);

				if (CommImgsList != null) {
					if (CommImgsList.size() < 5) {// 添加CommImgsList
						for (int i = 0; i < (5 - CommImgsListCount); i++) {
							PdImgs pdImgs = new PdImgs();
							CommImgsList.add(pdImgs);
						}
					}
				}

				// 重新赛入CommImgsList
				entity.setPdImgsList(CommImgsList);
				request.setAttribute("CommImgsList", CommImgsList);

				EntpInfo entpInfo = super.getEntpInfo(entity.getOwn_entp_id(), null, null);
				if (null != entpInfo && StringUtils.isNotBlank(entpInfo.getEntp_name())) {
					dynaBean.set("entp_name", entpInfo.getEntp_name());
				}

				// 物流
				if (null != entity.getFreight_id()) {
					Freight fre = super.getFreightInfo(entity.getFreight_id());
					if (null != fre) {
						dynaBean.set("fre_title", fre.getFre_title());
					}
				}

				// 品牌
				if (null != entity.getBrand_id()) {
					BaseBrandInfo baseBrandInfo = super.getBaseBrandInfo(entity.getBrand_id());
					if (null != baseBrandInfo) {
						dynaBean.set("brand_name", baseBrandInfo.getBrand_name());
					}
				}
				request.setAttribute("entity", entity);
				// 客服QQ
				EntpInfo a = getEntpInfo(entity.getAdd_user_id());
				if (null != a) {
					dynaBean.set("qq", a.getQq());
				}
			}
			entity.setQueryString(super.serialize(request, "id", "method"));
			super.copyProperties(form, entity);

			// 套餐管理
			CommTczhPrice param_ctp = new CommTczhPrice();
			param_ctp.setComm_id(id);
			List<CommTczhPrice> list_CommTczhPrice = super.getFacade().getCommTczhPriceService()
					.getCommTczhPriceList(param_ctp);

			request.setAttribute("reBate1001",
					super.getBaseData(Keys.REBATE_BASE_DATA_ID.REBATE_BASE_DATA_ID_1001.getIndex()).getPre_number2());
			request.setAttribute("reBate1002",
					super.getBaseData(Keys.REBATE_BASE_DATA_ID.REBATE_BASE_DATA_ID_1002.getIndex()).getPre_number2());

			request.setAttribute("list_CommTczhPrice", list_CommTczhPrice);

			// 商品频道
			CommInfoTags commInfoTags = new CommInfoTags();
			commInfoTags.setComm_id(Integer.valueOf(id));
			List<CommInfoTags> commInfoTagsList = super.getFacade().getCommInfoTagsService()
					.getCommInfoTagsList(commInfoTags);
			if (null != commInfoTagsList && commInfoTagsList.size() > 0) {
				String tag_ids_str = ",";
				for (CommInfoTags t : commInfoTagsList) {
					tag_ids_str += t.getTag_id().toString() + ",";
				}
				request.setAttribute("tag_ids_str", tag_ids_str);
			}

			// 扶贫对象
			CommInfoPoors commInfoPoors = new CommInfoPoors();
			commInfoPoors.setComm_id(Integer.valueOf(id));
			List<CommInfoPoors> commInfoPoorsList = super.getFacade().getCommInfoPoorsService()
					.getCommInfoPoorsList(commInfoPoors);
			if (null != commInfoPoorsList && commInfoPoorsList.size() > 0) {
				String temp_poor_ids = ",";
				for (CommInfoPoors temp : commInfoPoorsList) {
					temp_poor_ids += temp.getPoor_id().toString() + ",";
				}
				request.setAttribute("temp_poor_ids", temp_poor_ids);
			}
			request.setAttribute("commInfoPoorsList", commInfoPoorsList);

			// 一个商品最多扶贫对象数量
			BaseData baseData1901 = super.getBaseData(Keys.BASE_DATA_ID.BASE_DATA_ID_1901.getIndex());
			request.setAttribute("baseData1901", baseData1901);
			return mapping.findForward("input");
		} else {
			saveError(request, "errors.Integer", id);
			return mapping.findForward("list");
		}
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}

		request.setAttribute("header_title", "申请售后");
		super.getModNameForMobile(request);
		DynaBean dynaBean = (DynaBean) form;

		String id = (String) dynaBean.get("id");
		OrderInfo entity = new OrderInfo();
		entity.setId(new Integer(id));
		entity = getFacade().getOrderInfoService().getOrderInfo(entity);

		OrderInfoDetails orderInfoDetails = new OrderInfoDetails();
		orderInfoDetails.setOrder_id(entity.getId());
		List<OrderInfoDetails> orderInfoDetailsList = super.getFacade().getOrderInfoDetailsService()
				.getOrderInfoDetailsList(orderInfoDetails);
		if (null != orderInfoDetailsList && orderInfoDetailsList.size() > 0) {
			OrderReturnInfo orderReturnInfo = null;
			for (OrderInfoDetails temp : orderInfoDetailsList) {
				orderReturnInfo = new OrderReturnInfo();
				orderReturnInfo.setOrder_detail_id(temp.getId());
				List<OrderReturnInfo> orders = getFacade().getOrderReturnInfoService().getOrderReturnInfoList(
						orderReturnInfo);
				if (orders != null && orders.size() > 0) {
					temp.getMap().put("order_return_id", orders.get(0).getId());
				}
			}
		}

		request.setAttribute("entity", entity);
		request.setAttribute("orderInfoDetailsList", orderInfoDetailsList);

		return mapping.findForward("list");

	}

	public ActionForward updateState(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String state = (String) dynaBean.get("state");

		JSONObject data = new JSONObject();

		if (!GenericValidator.isLong(id)) {
			data.put("ret", "0");
			data.put("msg", "参数有误！");
			super.renderJson(response, data.toString());
			return null;
		}

		OrderInfo orderQuery = new OrderInfo();
		orderQuery.setId(Integer.valueOf(id));
		orderQuery = super.getFacade().getOrderInfoService().getOrderInfo(orderQuery);
		if (orderQuery == null) {
			data.put("ret", "0");
			data.put("msg", "订单不存在！");
			super.renderJson(response, data.toString());
			return null;
		}

		if (orderQuery.getIs_opt() == 1) {
			data.put("ret", "0");
			data.put("msg", "订单正在操作中，请稍后......！");
			super.renderJson(response, data.toString());
			return null;
		}
		updateOrderInfoOpt(1, orderQuery.getId());

		OrderInfo entity = new OrderInfo();
		entity.setId(Integer.valueOf(id));
		entity.setOrder_state(Integer.valueOf(state));

		if (StringUtils.equals("-10", state)) {
			entity.getMap().put("update_comm_info_saleCountAndInventory", true);
		}
		if (StringUtils.equals("-20", state)) {
			entity.getMap().put("tuikuan_update_link_table", true);
		}

		String msg = "操作失败或您已操作过";
		String ret = "0";
		if ("-10".equals(state) && orderQuery.getOrder_state().intValue() == Keys.OrderState.ORDER_STATE_0.getIndex()) {
			msg = "取消订单成功！";
			ret = "1";
			entity.getMap().put("opt_order_state", Keys.OrderState.ORDER_STATE_0.getIndex());// 取消订单，前一个状态0
		}
		if ("-20".equals(state) && orderQuery.getOrder_state().intValue() == Keys.OrderState.ORDER_STATE_10.getIndex()) {
			msg = "退款成功！";
			ret = "1";
			entity.getMap().put("opt_order_state", Keys.OrderState.ORDER_STATE_10.getIndex());// 退款，前一个状态：10
		}
		if ("40".equals(state) && orderQuery.getOrder_state().intValue() == Keys.OrderState.ORDER_STATE_20.getIndex()) {
			msg = "确认收货成功！";
			ret = "1";
			entity.setQrsh_date(new Date());
		}

		if (ret.equals("1")) {
			int row = super.getFacade().getOrderInfoService().modifyOrderInfo(entity);

			updateOrderInfoOpt(0, orderQuery.getId());

			if (row == 0) {
				msg = "操作失败或您已操作过";
				ret = "0";
			}
			if (row == -1) {
				msg = "系统繁忙，请稍后重试";
				ret = "0";
			}
		}
		data.put("ret", ret);
		data.put("msg", msg);
		super.renderJson(response, data.toString());
		return null;
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}
		if (!StringUtils.equalsIgnoreCase(request.getMethod(), "POST")) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String order_type = (String) dynaBean.get("order_type");
		String order_state = (String) dynaBean.get("order_state");
		String mod_id = (String) dynaBean.get("mod_id");
		if (!StringUtils.isBlank(id) && GenericValidator.isLong(id)) {
			OrderInfo orderInfo = new OrderInfo();
			orderInfo.setId(Integer.valueOf(id));
			orderInfo.setOrder_state(Keys.OrderState.ORDER_STATE_90.getIndex());
			getFacade().getOrderInfoService().modifyOrderInfo(orderInfo);
		}

		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(super.serialize(request, "id", "method")));
		pathBuffer.append("&order_type=" + order_type);
		pathBuffer.append("&order_state=" + order_state);
		pathBuffer.append("&mod_id=" + mod_id);
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward getOrderListJson(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}

		DynaBean dynaBean = (DynaBean) form;
		// getsonSysModuleList(request, dynaBean);

		String comm_name_like = (String) dynaBean.get("comm_name_like");
		String st_date = (String) dynaBean.get("st_date");
		String en_date = (String) dynaBean.get("en_date");
		String trade_index = (String) dynaBean.get("trade_index");
		String order_type = (String) dynaBean.get("order_type");
		Pager pager = (Pager) dynaBean.get("pager");
		String startPage = (String) dynaBean.get("startPage");
		String pageSize = (String) dynaBean.get("pageSize");
		if (StringUtils.isBlank(pageSize)) {
			pageSize = "10";
		}
		if (StringUtils.isBlank(startPage)) {
			startPage = "0";
		}

		OrderInfo entity = new OrderInfo();
		super.copyProperties(entity, form);

		entity.setAdd_user_id(ui.getId());
		entity.getMap().put("st_add_date", st_date);
		entity.getMap().put("en_add_date", en_date);
		entity.getMap().put("comm_name_like", comm_name_like);

		if (StringUtils.isNotBlank(trade_index)) {
			entity.setTrade_index(trade_index.trim());
		}
		if (StringUtils.isBlank(order_type)) {
			entity.setOrder_type(Keys.OrderType.ORDER_TYPE_10.getIndex());
		}
		// 已关闭的订单不显示，已关闭=已删除
		entity.getMap().put("order_state_ne", Keys.OrderState.ORDER_STATE_90.getIndex());

		Integer recordCount = getFacade().getOrderInfoService().getOrderInfoCount(entity);
		pager.init(recordCount.longValue(), new Integer(pageSize), pager.getRequestPage());
		entity.getRow().setFirst(Integer.valueOf(startPage) * Integer.valueOf(pageSize));
		entity.getRow().setCount(pager.getRowCount());

		List<OrderInfo> orderInfoList = getFacade().getOrderInfoService().getOrderInfoWithRealNamePaginatedList(entity);

		JSONObject datas = new JSONObject();
		JSONArray dataLoadList = new JSONArray();
		// String ctx = super.getCtxPath(request);
		if ((null != orderInfoList) && (orderInfoList.size() > 0)) {
			for (OrderInfo oi : orderInfoList) {
				JSONObject map = new JSONObject();
				map.put("id", oi.getId());
				map.put("trade_index", oi.getTrade_index());
				map.put("order_state", oi.getOrder_state());
				map.put("pay_type", oi.getPay_type());
				for (OrderState os : Keys.OrderState.values()) {
					if (os.getIndex() == oi.getOrder_state()) {
						map.put("order_state_name", os.getName());
						break;
					}
				}
				map.put("order_date", DateTools.getStringDate(oi.getOrder_date(), "yyyy-MM-dd HH:mm:ss"));
				map.put("order_money", dfFormat.format(oi.getOrder_money()));
				map.put("order_type", oi.getOrder_type());

				OrderInfoDetails orderInfoDetails = new OrderInfoDetails();
				orderInfoDetails.setOrder_id(oi.getId());
				List<OrderInfoDetails> orderInfoDetailsList = super.getFacade().getOrderInfoDetailsService()
						.getOrderInfoDetailsList(orderInfoDetails);
				if (null != orderInfoDetailsList && orderInfoDetailsList.size() > 0) {
					map.put("comm_name", orderInfoDetailsList.get(0).getComm_name());
				}

				int is_shixiao = 0;// 未失效
				if (null != oi.getEnd_date()) {// 判断订单是否失效
					if (oi.getEnd_date().before(new Date())) {
						is_shixiao = 1;// 已失效
					}
				}
				map.put("is_shixiao", is_shixiao);
				dataLoadList.add(map);
			}
			datas.put("ret", "1");
			datas.put("msg", "查询成功");
			datas.put("appendMore", "0");
			if (orderInfoList.size() == Integer.valueOf(pageSize)) {
				datas.put("appendMore", "1");
			}
			datas.put("dataList", dataLoadList.toString());
		} else {
			datas.put("ret", "0");
			datas.put("msg", "已全部加载");
		}

		super.renderJson(response, datas.toString());

		return null;

	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("======进入save===");
		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		DynaBean dynaBean = (DynaBean) form;
		String order_detail_id = (String) dynaBean.get("order_detail_id");
		String base_files1 = (String) dynaBean.get("base_files1");
		String base_files2 = (String) dynaBean.get("base_files2");
		String base_files3 = (String) dynaBean.get("base_files3");
		String base_files4 = (String) dynaBean.get("base_files4");
		String[] basefiles = { base_files1, base_files2, base_files3, base_files4 };

		logger.info("======order_detail_id===" + order_detail_id);

		JSONObject data = new JSONObject();
		String msg = "";
		String ret = "0";

		if (StringUtils.isBlank(order_detail_id)) {
			msg = "参数不能为空";
			super.renderJson(response, data.toJSONString());
			return null;
		}

		OrderInfoDetails orderInfoDetails = new OrderInfoDetails();
		orderInfoDetails.setId(Integer.valueOf(order_detail_id));
		orderInfoDetails = getFacade().getOrderInfoDetailsService().getOrderInfoDetails(orderInfoDetails);
		if (null == orderInfoDetails) {
			msg = "订单明细不存在";
			super.renderJson(response, data.toJSONString());
			return null;
		}
		OrderInfo orderInfo = super.getOrderInfo(orderInfoDetails.getOrder_id());

		OrderReturnInfo orderReturnInfo = new OrderReturnInfo();
		super.copyProperties(orderReturnInfo, form);
		orderReturnInfo.setAdd_date(new Date());
		orderReturnInfo.setOrder_state(orderInfo.getOrder_state());
		orderReturnInfo.setIs_del(0);
		orderReturnInfo.setOrder_detail_id(Integer.valueOf(order_detail_id));
		orderReturnInfo.setComm_id(orderInfoDetails.getComm_id());

		orderReturnInfo.setComm_name(orderInfoDetails.getComm_name());
		orderReturnInfo.setTk_status(0);
		orderReturnInfo.setUser_id(ui.getId());
		orderReturnInfo.setUser_name(ui.getUser_name());
		orderReturnInfo.setIs_confirm(0);
		orderReturnInfo.setAudit_state(Keys.audit_state.audit_state_0.getIndex());
		orderReturnInfo.setNum(orderInfoDetails.getGood_count());
		if (null == orderReturnInfo.getReturn_type()) {
			orderReturnInfo.setReturn_type(Keys.return_why.return_why_11630.getIndex());
		}
		// 默认订单实际支付金额
		orderReturnInfo.setPrice(orderInfoDetails.getActual_money());
		if (orderInfo.getOrder_state().intValue() == Keys.OrderState.ORDER_STATE_10.getIndex()) {
			// 未发货 实际支付金额+运费
			orderReturnInfo.setPrice(orderInfoDetails.getActual_money().add(orderInfoDetails.getMatflow_price()));
		}
		// 换货 价格为0
		if (orderReturnInfo.getExpect_return_way().intValue() == Keys.expectReturnWay.expectReturnWay_2.getIndex()) {
			orderReturnInfo.setPrice(new BigDecimal(0));
		}

		CommInfo commInfo = new CommInfo();
		commInfo.setId(orderInfoDetails.getComm_id());
		commInfo = getFacade().getCommInfoService().getCommInfo(commInfo);
		if (null != commInfo) {
			orderReturnInfo.setEntp_id(commInfo.getOwn_entp_id());
		}
		// orderReturnInfo.setReturn_link_man(ui.);
		String retrun_no = "th" + this.creatTradeIndex();
		orderReturnInfo.setReturn_no(retrun_no);
		if (ArrayUtils.isNotEmpty(basefiles)) {
			orderReturnInfo.getMap().put("basefiles", basefiles);
		}
		int i = this.getFacade().getOrderReturnInfoService().createOrderReturnInfo(orderReturnInfo);

		if (i > 0) {
			msg = "提交成功";
			ret = "1";
		} else {
			msg = "提交失败";
			ret = "0";
		}

		data.put("ret", ret);
		data.put("msg", msg);
		super.renderJson(response, data.toJSONString());
		return null;

	}
}