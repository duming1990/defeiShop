package com.ebiz.webapp.web.struts.m;

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
import com.ebiz.webapp.domain.BaseData;
import com.ebiz.webapp.domain.BaseImgs;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.OrderInfoDetails;
import com.ebiz.webapp.domain.OrderReturnAudit;
import com.ebiz.webapp.domain.OrderReturnInfo;
import com.ebiz.webapp.domain.OrderReturnMsg;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.WlCompInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.Keys.Is_Tuihuo;
import com.ebiz.webapp.web.Keys.audit_state;
import com.ebiz.webapp.web.util.DateTools;

public class MMyTuiHuoAction extends MBaseWebAction {
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}

		request.setAttribute("header_title", "我的售后");
		DynaBean dynaBean = (DynaBean) form;

		String id = (String) dynaBean.get("id");
		String audit_state = (String) dynaBean.get("audit_state");
		logger.info("===audit_state===" + audit_state);
		Pager pager = (Pager) dynaBean.get("pager");
		OrderReturnInfo entity = new OrderReturnInfo();
		super.copyProperties(entity, form);
		entity.setUser_id(ui.getId());
		entity.setIs_del(0);

		Integer pageSize = 10;
		Integer recordCount = getFacade().getOrderReturnInfoService().getOrderReturnInfoCount(entity);
		pager.init(recordCount.longValue(), pageSize, pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<OrderReturnInfo> list = getFacade().getOrderReturnInfoService().getOrderReturnInfoPaginatedList(entity);
		if (list.size() == Integer.valueOf(pageSize)) {
			request.setAttribute("appendMore", 1);
		}
		if ((list.size() > 0)) {
			for (OrderReturnInfo item : list) {
				if (item.getReturn_type() != null) {
					BaseData returnType = new BaseData();
					returnType.setId(item.getReturn_type());
					returnType.setIs_del(0);
					returnType = getFacade().getBaseDataService().getBaseData(returnType);
					if (null != returnType) {
						item.getMap().put("returnTypeName", returnType.getType_name());
					}
				}
				OrderInfo orderInfo = new OrderInfo();
				orderInfo.setId(item.getOrder_id());
				orderInfo = this.getFacade().getOrderInfoService().getOrderInfo(orderInfo);
				item.getMap().put("orderInfo", orderInfo);
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

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}

		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);

		String id = (String) dynaBean.get("id");

		if (StringUtils.isBlank(id) || !GenericValidator.isLong(id)) {
			String msg = "参数有误，请联系管理员！";
			return showTipMsg(mapping, form, request, response, msg);
		}

		OrderReturnInfo entity = new OrderReturnInfo();
		entity.setId(new Integer(id));
		entity = getFacade().getOrderReturnInfoService().getOrderReturnInfo(entity);
		if (null == entity) {
			String msg = "退货信息不存在";
			return showTipMsg(mapping, form, request, response, msg);

		}

		if (null != entity.getReturn_type()) {
			BaseData returnType = new BaseData();
			returnType.setId(entity.getReturn_type());
			returnType.setIs_del(0);
			returnType = getFacade().getBaseDataService().getBaseData(returnType);
			if (null != returnType) {
				request.setAttribute("returnTypeName", returnType.getType_name());
			}
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

		request.setAttribute("header_title", "售后详情");

		OrderReturnAudit audit = new OrderReturnAudit();
		audit.setOrder_return_id(entity.getId());
		audit = getFacade().getOrderReturnAuditService().getOrderReturnAudit(audit);
		if (null != audit) {
			request.setAttribute("remark", audit.getRemark());
		}
		CommInfo commInfo = new CommInfo();
		commInfo.setId(entity.getComm_id());
		commInfo.setIs_del(0);
		commInfo = getFacade().getCommInfoService().getCommInfo(commInfo);
		if (null != commInfo) {
			request.setAttribute("main_pic", commInfo.getMain_pic());
		}
		OrderInfoDetails orderInfoDetails = new OrderInfoDetails();
		orderInfoDetails.setId(entity.getOrder_detail_id());
		orderInfoDetails = getFacade().getOrderInfoDetailsService().getOrderInfoDetails(orderInfoDetails);
		if (null != orderInfoDetails) {
			request.setAttribute("orderInfoDetails", orderInfoDetails);
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
		return mapping.findForward("view");

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

		OrderReturnInfo orderReturnInfo = new OrderReturnInfo();
		super.copyProperties(orderReturnInfo, form);
		orderReturnInfo.setAdd_date(new Date());
		orderReturnInfo.setIs_del(0);
		orderReturnInfo.setOrder_detail_id(Integer.valueOf(order_detail_id));
		orderReturnInfo.setComm_id(orderInfoDetails.getComm_id());
		orderReturnInfo.setComm_name(orderInfoDetails.getComm_name());
		orderReturnInfo.setTk_status(0);
		orderReturnInfo.setUser_id(ui.getId());
		orderReturnInfo.setUser_name(ui.getUser_name());
		orderReturnInfo.setIs_confirm(0);
		orderReturnInfo.setAudit_state(Keys.audit_state.audit_state_0.getIndex());

		CommInfo commInfo = new CommInfo();
		commInfo.setId(orderInfoDetails.getComm_id());
		commInfo = getFacade().getCommInfoService().getCommInfo(commInfo);
		if (null != commInfo) {
			orderReturnInfo.setEntp_id(commInfo.getAdd_user_id());
		}
		// orderReturnInfo.setReturn_link_man(ui.);
		orderReturnInfo.setReturn_no(getReturnNo());

		int i = getFacade().getOrderReturnInfoService().createOrderReturnInfo(orderReturnInfo);
		if (i > 0) {
			if (ArrayUtils.isNotEmpty(basefiles)) {
				orderReturnInfo.getMap().put("basefiles", basefiles);
				for (String file_path_lbt : basefiles) {
					if (StringUtils.isNotBlank(file_path_lbt)) {
						BaseImgs baseImgs = new BaseImgs();
						baseImgs.setImg_type(Keys.BaseImgsType.Base_Imgs_TYPE_20.getIndex());
						baseImgs.setFile_path(file_path_lbt);
						baseImgs.setLink_id(Integer.valueOf(i));
						getFacade().getBaseImgsService().createBaseImgs(baseImgs);
					}
				}
			}
		}
		msg = "提交成功";
		ret = "1";
		data.put("ret", ret);
		data.put("msg", msg);
		super.renderJson(response, data.toJSONString());
		return null;

	}

	public ActionForward getTuiHuoListJson(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}

		DynaBean dynaBean = (DynaBean) form;
		// getsonSysModuleList(request, dynaBean);

		// String comm_name_like = (String) dynaBean.get("comm_name_like");
		// String st_date = (String) dynaBean.get("st_date");
		// String en_date = (String) dynaBean.get("en_date");
		// String trade_index = (String) dynaBean.get("trade_index");
		String order_type = (String) dynaBean.get("order_type");
		String audit_state = (String) dynaBean.get("audit_state");
		Pager pager = (Pager) dynaBean.get("pager");
		String startPage = (String) dynaBean.get("startPage");
		String pageSize = (String) dynaBean.get("pageSize");
		if (StringUtils.isBlank(pageSize)) {
			pageSize = "10";
		}
		if (StringUtils.isBlank(startPage)) {
			startPage = "0";
		}

		OrderReturnInfo entity = new OrderReturnInfo();
		super.copyProperties(entity, form);
		entity.setUser_id(ui.getId());
		entity.setIs_del(0);

		// entity.getMap().put("st_add_date", st_date);
		// entity.getMap().put("en_add_date", en_date);
		// entity.getMap().put("comm_name_like", comm_name_like);

		if (StringUtils.isNotBlank(order_type)) {
			entity.setOrder_type(Integer.valueOf(order_type));
		}
		if (StringUtils.isNotBlank(audit_state)) {
			entity.setAudit_state(Integer.valueOf(audit_state));
		}
		// 已关闭的订单不显示，已关闭=已删除
		// entity.getMap().put("order_state_ne", Keys.OrderState.ORDER_STATE_90.getIndex());

		Integer recordCount = getFacade().getOrderReturnInfoService().getOrderReturnInfoCount(entity);
		pager.init(recordCount.longValue(), new Integer(pageSize), pager.getRequestPage());
		entity.getRow().setFirst(Integer.valueOf(startPage) * Integer.valueOf(pageSize));
		entity.getRow().setCount(pager.getRowCount());

		List<OrderReturnInfo> orderReturnInfoList = getFacade().getOrderReturnInfoService()
				.getOrderReturnInfoPaginatedList(entity);

		JSONObject datas = new JSONObject();
		JSONArray dataLoadList = new JSONArray();
		// String ctx = super.getCtxPath(request);
		if ((null != orderReturnInfoList) && (orderReturnInfoList.size() > 0)) {
			for (OrderReturnInfo temp : orderReturnInfoList) {
				JSONObject map = new JSONObject();

				if (temp.getReturn_type() != null) {
					BaseData returnType = new BaseData();
					returnType.setId(temp.getReturn_type());
					returnType.setIs_del(0);
					returnType = getFacade().getBaseDataService().getBaseData(returnType);
					if (null != returnType) {
						map.put("returnTypeName", returnType.getType_name());
					}
				}

				for (Is_Tuihuo expect_return_way : Keys.Is_Tuihuo.values()) {
					if (expect_return_way.getIndex() == temp.getExpect_return_way()) {
						map.put("expect_return_way", expect_return_way.getName());
						break;
					}
				}

				BaseData returnType = new BaseData();
				returnType.setId(temp.getReturn_type());
				returnType.setIs_del(0);
				returnType = getFacade().getBaseDataService().getBaseData(returnType);
				if (null != returnType) {
					map.put("returnTypeName", returnType.getType_name());
					logger.info("====returnTypeName:" + returnType.getType_name());
				}

				map.put("id", temp.getId());
				map.put("trade_index", temp.getMap().get("trade_index"));
				for (audit_state audit : Keys.audit_state.values()) {
					if (audit.getIndex() == temp.getAudit_state()) {
						map.put("audit_state_name", audit.getName());
						break;
					}
				}
				map.put("add_date", DateTools.getStringDate(temp.getAdd_date(), "yyyy-MM-dd HH:mm:ss"));
				if (temp.getAudit_date() != null) {
					map.put("audit_date", DateTools.getStringDate(temp.getAudit_date(), "yyyy-MM-dd HH:mm:ss"));
				}

				map.put("price", dfFormat.format(temp.getPrice()));

				dataLoadList.add(map);
			}
			datas.put("ret", "1");
			datas.put("msg", "查询成功");
			datas.put("appendMore", "0");
			if (orderReturnInfoList.size() == Integer.valueOf(pageSize)) {
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

	public ActionForward fankui(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}

		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);

		String id = (String) dynaBean.get("id");

		if (StringUtils.isBlank(id) || !GenericValidator.isLong(id)) {
			String msg = "参数有误，请联系管理员！";
			return showTipMsg(mapping, form, request, response, msg);
		}

		OrderReturnInfo entity = new OrderReturnInfo();
		entity.setId(Integer.valueOf(id));
		entity = super.getFacade().getOrderReturnInfoService().getOrderReturnInfo(entity);
		if (null == entity) {
			String msg = "售后记录不存在！";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		super.copyProperties(form, entity);

		BaseData returnType = new BaseData();
		returnType.setId(entity.getReturn_type());
		returnType.setIs_del(0);
		returnType = getFacade().getBaseDataService().getBaseData(returnType);
		if (null != returnType) {
			request.setAttribute("returnTypeName", returnType.getType_name());
		}
		OrderInfo order = new OrderInfo();
		order.setId(entity.getOrder_id());
		order = getFacade().getOrderInfoService().getOrderInfo(order);
		request.setAttribute("order", order);

		OrderInfoDetails ods = new OrderInfoDetails();
		if (null != entity.getOrder_detail_id()) {
			ods.setId(entity.getOrder_detail_id());
		} else {
			ods.setOrder_id(order.getId());
		}
		List<OrderInfoDetails> odslist = getFacade().getOrderInfoDetailsService().getOrderInfoDetailsList(ods);
		request.setAttribute("list", odslist);

		return mapping.findForward("input");

	}

	public ActionForward insertOrderReturnMsg(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("======进入save===");
		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

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
}