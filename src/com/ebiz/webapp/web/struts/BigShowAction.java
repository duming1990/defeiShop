package com.ebiz.webapp.web.struts;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
import com.ebiz.webapp.domain.Activity;
import com.ebiz.webapp.domain.BaseFiles;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.OrderInfoDetails;
import com.ebiz.webapp.domain.UserBiRecord;
import com.ebiz.webapp.web.Keys;

/**
 * @author 戴诗学
 * @version 2018-4-12
 */
public class BigShowAction extends BaseWebAction {

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.index(mapping, form, request, response);
	}

	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String orderDate = (String) dynaBean.get("orderDate");

		BaseFiles baseFiles = new BaseFiles();
		baseFiles.setId(Integer.valueOf(id));
		baseFiles = super.getFacade().getBaseFilesService().getBaseFiles(baseFiles);
		if (null != baseFiles) {
			request.setAttribute("bigShow", baseFiles);
		}
		if (orderDate == null || "".equals(orderDate)) {
			request.setAttribute("orderDate", orderDate);
		}
		return new ActionForward("/index/BigShow/index.jsp");
	}

	public ActionForward getAjaxDataPoorSalesRanking(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = "-1", msg = "";
		JSONObject data = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		BaseFiles baseFiles = new BaseFiles();
		baseFiles.setId(Integer.valueOf(id));
		baseFiles = super.getFacade().getBaseFilesService().getBaseFiles(baseFiles);
		if (null != baseFiles) {
			// 销售总额排行
			OrderInfoDetails ods = new OrderInfoDetails();
			ods.getMap().put("p_index_like", StringUtils.substring(baseFiles.getLink_id().toString(), 0, 6));
			ods.getMap().put("order_type", Keys.OrderType.ORDER_TYPE_7.getIndex());
			ods.getRow().setFirst(0);
			ods.getRow().setCount(10);
			List<OrderInfoDetails> poorSaleRankList = getFacade().getOrderInfoDetailsService()
					.getPoorSalesRankingList(ods);
			data.put("poorSaleRankList", poorSaleRankList);
		}
		code = "1";
		super.returnInfo(response, code, msg, data);
		return null;
	}

	public ActionForward getAjaxDataCorporateHelp(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = "-1", msg = "";
		JSONObject data = new JSONObject();
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		BaseFiles baseFiles = new BaseFiles();
		baseFiles.setId(Integer.valueOf(id));
		baseFiles = super.getFacade().getBaseFilesService().getBaseFiles(baseFiles);
		if (null != baseFiles) {
			// 企业帮扶榜
			UserBiRecord ods = new UserBiRecord();
			ods.setBi_type(Keys.BiType.BI_TYPE_600.getIndex());
			ods.setBi_chu_or_ru(Keys.BI_CHU_OR_RU.BASE_DATA_ID_1.getIndex());
			ods.getMap().put("p_index_like", StringUtils.substring(baseFiles.getLink_id().toString(), 0, 6));
			ods.getRow().setFirst(0);
			ods.getRow().setCount(10);
			List<UserBiRecord> corporateHelpList = getFacade().getUserBiRecordService().getCorporateHelpList(ods);
			data.put("corporateHelpList", corporateHelpList);
		}
		code = "1";
		super.returnInfo(response, code, msg, data);
		return null;
	}

	public ActionForward getAjaxDataPoorSalesRealtime(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		String code = "-1", msg = "";
		JSONObject data = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String last_id = (String) dynaBean.get("last_id");
		boolean flag = false;
		if (StringUtils.isNotEmpty(last_id) && GenericValidator.isLong(last_id)) {
			flag = true;
		}
		BaseFiles baseFiles = new BaseFiles();
		baseFiles.setId(Integer.valueOf(id));
		baseFiles = super.getFacade().getBaseFilesService().getBaseFiles(baseFiles);
		if (null != baseFiles) {
			// 实时销售榜
			OrderInfoDetails ods = new OrderInfoDetails();
			ods.getMap().put("p_index_like", StringUtils.substring(baseFiles.getLink_id().toString(), 0, 6));
			ods.getMap().put("order_type", Keys.OrderType.ORDER_TYPE_7.getIndex());
			if (flag == false) {
				ods.getRow().setFirst(0);
				ods.getRow().setCount(11);
			} else {
				ods.getMap().put("last_id", last_id);
			}
			List<OrderInfoDetails> poorSalesRealtimeList = getFacade().getOrderInfoDetailsService()
					.getPoorSalesRealtimeList(ods);
			data.put("poorSalesRealtimeList", poorSalesRealtimeList);
		}
		code = "1";
		super.returnInfo(response, code, msg, data);
		return null;
	}

	public ActionForward getAjaxDataCorporateHelpDynamic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		String code = "-1", msg = "";
		JSONObject data = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String last_id = (String) dynaBean.get("last_id");
		boolean flag = false;
		if (StringUtils.isNotEmpty(last_id) && GenericValidator.isLong(last_id)) {
			flag = true;
		}
		BaseFiles baseFiles = new BaseFiles();
		baseFiles.setId(Integer.valueOf(id));
		baseFiles = super.getFacade().getBaseFilesService().getBaseFiles(baseFiles);
		if (null != baseFiles) {
			// 企业帮扶动态
			UserBiRecord ods = new UserBiRecord();
			ods.setBi_type(Keys.BiType.BI_TYPE_600.getIndex());
			ods.setBi_chu_or_ru(Keys.BI_CHU_OR_RU.BASE_DATA_ID_1.getIndex());
			ods.getMap().put("p_index_like", StringUtils.substring(baseFiles.getLink_id().toString(), 0, 6));
			if (flag == false) {
				ods.getRow().setFirst(0);
				ods.getRow().setCount(7);
			} else {
				ods.getMap().put("last_id", last_id);
			}
			List<UserBiRecord> corporateHelpDynamicList = getFacade().getUserBiRecordService()
					.getCorporateHelpDynamicList(ods);
			data.put("corporateHelpDynamicList", corporateHelpDynamicList);
		}
		code = "1";
		super.returnInfo(response, code, msg, data);
		return null;
	}

	public ActionForward getHasNewOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JSONObject jsonObject = new JSONObject();
		String ret = "0";
		String msg = "";

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String last_id = (String) dynaBean.get("last_id");
		boolean flag = false;
		if (StringUtils.isNotEmpty(last_id) && GenericValidator.isLong(last_id)) {
			flag = true;
		}
		BaseFiles baseFiles = new BaseFiles();
		baseFiles.setId(Integer.valueOf(id));
		baseFiles = super.getFacade().getBaseFilesService().getBaseFiles(baseFiles);
		if (null != baseFiles) {
			// 实时销售榜声音提醒
			OrderInfoDetails ods = new OrderInfoDetails();
			ods.getMap().put("p_index_like", StringUtils.substring(baseFiles.getLink_id().toString(), 0, 6));
			ods.getMap().put("order_type", Keys.OrderType.ORDER_TYPE_7.getIndex());
			if (flag == false) {
				ods.getRow().setFirst(0);
				ods.getRow().setCount(11);
			} else {
				ods.getMap().put("last_id", last_id);
				List<OrderInfoDetails> poorSalesRealtimeList = getFacade().getOrderInfoDetailsService()
						.getPoorSalesRealtimeList(ods);
				if (poorSalesRealtimeList.size() > 0) {
					ret = "1";
					msg = "有一个新的订单！";
				}
			}
		}
		jsonObject.put("ret", ret);
		jsonObject.put("msg", msg);
		super.renderJson(response, jsonObject.toString());

		return null;
	}

	public ActionForward getEntpHasNewOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JSONObject jsonObject = new JSONObject();
		String ret = "0";
		String msg = "";

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String last_id = (String) dynaBean.get("last_id");
		boolean flag = false;
		if (StringUtils.isNotEmpty(last_id) && GenericValidator.isLong(last_id)) {
			flag = true;
		}
		BaseFiles baseFiles = new BaseFiles();
		baseFiles.setId(Integer.valueOf(id));
		baseFiles = super.getFacade().getBaseFilesService().getBaseFiles(baseFiles);
		if (null != baseFiles) {
			// 企业帮扶动态
			UserBiRecord ods = new UserBiRecord();
			ods.setBi_type(Keys.BiType.BI_TYPE_600.getIndex());
			ods.setBi_chu_or_ru(Keys.BI_CHU_OR_RU.BASE_DATA_ID_1.getIndex());
			ods.getMap().put("p_index_like", StringUtils.substring(baseFiles.getLink_id().toString(), 0, 6));
			if (flag == false) {
				ods.getRow().setFirst(0);
				ods.getRow().setCount(7);
			} else {
				ods.getMap().put("last_id", last_id);
			}
			List<UserBiRecord> corporateHelpDynamicList = getFacade().getUserBiRecordService()
					.getCorporateHelpDynamicList(ods);
			if (corporateHelpDynamicList.size() > 0) {
				ret = "1";
				msg = "有一个新的订单！";
			}
		}
		jsonObject.put("ret", ret);
		jsonObject.put("msg", msg);
		super.renderJson(response, jsonObject.toString());

		return null;
	}

	/**
	 * 活动订单统计，大屏显示
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward openBigShowPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String pageSize = (String) dynaBean.get("pageSize");

		Activity activity = new Activity();
		activity.setId(Integer.valueOf(id));
		activity = super.getFacade().getActivityService().getActivity(activity);

		dynaBean.set("activity", activity);
		return new ActionForward("/index/BigShow/bigView.jsp");
	}

	public ActionForward getAjaxDataBigShow(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String code = "-1", msg = "";
		JSONObject datas = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String last_pay_date = (String) dynaBean.get("last_pay_date");
		String orderDate = (String) dynaBean.get("orderDate");

		if (id == null || id.length() == 0) {
			code = "0";
			msg = "参数错误";
			super.returnInfo(response, code, msg, datas);
			return null;
		}

		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setActivity_id(Integer.valueOf(id));
		String order_state_in = Keys.OrderState.ORDER_STATE_10.getIndex() + ","
				+ Keys.OrderState.ORDER_STATE_20.getIndex() + "," + Keys.OrderState.ORDER_STATE_40.getIndex() + ","
				+ Keys.OrderState.ORDER_STATE_50.getIndex();
		orderInfo.getMap().put("order_state_in", order_state_in);
		if (orderDate == null || "".equals(orderDate)) {// 如果没有传递，则默认从当天开始
			// orderInfo.getMap().put("orderDate", new Date());
		} else {
			orderInfo.getMap().put("orderDate", orderDate);
		}

		if (last_pay_date != null && last_pay_date.length() > 0) {
			Date last_date = new Date(Long.valueOf(last_pay_date));
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			last_pay_date = format.format(last_date);
			orderInfo.getMap().put("last_pay_date", last_pay_date);
		}

		List<OrderInfo> orderInfoList = super.getFacade().getOrderInfoService().getOrderInfoListNew(orderInfo);
		Integer orderInfoCount = super.getFacade().getOrderInfoService().getBigShowOrderInfoCount(orderInfo);
		BigDecimal orderInfoSumMoney = super.getFacade().getOrderInfoService().getOrderInfoListNewSumMoney(orderInfo);
		for (OrderInfo orderInfo2 : orderInfoList) {
			OrderInfoDetails orderInfoDetails = new OrderInfoDetails();
			orderInfoDetails.setOrder_id(orderInfo2.getId());
			List<OrderInfoDetails> orderInfoDetailsList = super.getFacade().getOrderInfoDetailsService()
					.getOrderInfoDetailsList(orderInfoDetails);
			orderInfo2.setOrderInfoDetailsList(orderInfoDetailsList);
		}
		code = "1";
		msg = "查询成功";
		datas.put("orderInfoList", orderInfoList);
		datas.put("orderCount", orderInfoCount);
		datas.put("orderSumMoney", orderInfoSumMoney);
		super.returnInfo(response, code, msg, datas);
		return null;
	}
}