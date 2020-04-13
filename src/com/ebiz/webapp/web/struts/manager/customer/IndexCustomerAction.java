package com.ebiz.webapp.web.struts.manager.customer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.webapp.domain.BaseData;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.ScEntpComm;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;

/**
 * @author Wu,Yang2
 * @version 2010-09-26
 */
public class IndexCustomerAction extends BaseCustomerAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.index(mapping, form, request, response);
	}

	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);

		super.setPublicInfoWithSearchList(request);
		super.setPublicInfoList(request);

		ui = super.getUserInfo(ui.getId());

		super.setUserInfoToSession(request, ui);// 在塞到session中

		if (null != ui.getMobile() && StringUtils.isNotBlank(ui.getMobile())) {
			request.setAttribute("tianxiehaoma", 1);
		} else {
			request.setAttribute("tianxiehaoma", 0);
		}

		SimpleDateFormat formatter = new SimpleDateFormat("HHmmss");
		Integer time = Integer.parseInt(formatter.format(new Date()));
		Integer[] dateList = { 0, 30000, 60000, 80000, 110000, 130000, 170000, 190000 };
		if (time > dateList[0] && time < dateList[1]) {
			String date = "深夜好,";
			request.setAttribute("date", date);
		} else if (time >= dateList[1] && time <= dateList[2]) {
			String date = "凌晨好,";
			request.setAttribute("date", date);
		} else if (time > dateList[2] && time < dateList[3]) {
			String date = "早晨好,";
			request.setAttribute("date", date);
		} else if (time >= dateList[3] && time <= dateList[4]) {
			String date = "上午好,";
			request.setAttribute("date", date);
		} else if (time > dateList[4] && time < dateList[5]) {
			String date = "中午好,";
			request.setAttribute("date", date);
		} else if (time >= dateList[5] && time <= dateList[6]) {
			String date = "下午好,";
			request.setAttribute("date", date);
		} else if (time > dateList[6] && time <= dateList[7]) {
			String date = "傍晚好,";
			request.setAttribute("date", date);
		} else {
			String date = "晚上好,";
			request.setAttribute("date", date);
		}

		UserInfo userInfo = super.getUserInfo(ui.getId());
		// 用户升到下级所需积分
		BaseData uiBaseData = new BaseData();
		uiBaseData.setType(Keys.BASE_DATA_ID.BASE_DATA_ID_200.getIndex());
		uiBaseData.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		uiBaseData.setId(userInfo.getUser_level());
		uiBaseData = getFacade().getBaseDataService().getBaseData(uiBaseData);
		int user_level = uiBaseData.getPre_number3();
		int user_level_next = 0;
		if (user_level < 1) {
			user_level_next = user_level + 1;
		} else {
			user_level_next = user_level;
		}

		BaseData scoreBaseData = new BaseData();
		scoreBaseData.setType(Keys.BASE_DATA_ID.BASE_DATA_ID_200.getIndex());
		scoreBaseData.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		scoreBaseData.setPre_number3(user_level_next);
		scoreBaseData = getFacade().getBaseDataService().getBaseData(scoreBaseData);

		int user_level_need_score = scoreBaseData.getPre_number();
		request.setAttribute("user_level_need_score", user_level_need_score);

		request.setAttribute("upLevelNeedPayMoney", super.getSysSetting(Keys.upLevelNeedPayMoney));
		return new ActionForward("/../manager/customer/IndexCustomer/index.jsp");
	}

	public ActionForward welcome(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfoFromSession(request);

		UserInfo userInfo = super.getUserInfo(ui.getId());

		super.setUserInfoToSession(request, userInfo);

		int percent = 100;
		if (StringUtils.isBlank(userInfo.getPassword())) {
			percent -= 20;
		}
		if (StringUtils.isBlank(userInfo.getMobile())) {
			percent -= 20;
		}
		if (StringUtils.isBlank(userInfo.getEmail())) {
			percent -= 20;
		}
		if (StringUtils.isBlank(userInfo.getPassword_pay())) {
			percent -= 20;
		}
		if (userInfo.getIs_set_security() == 0) {
			percent -= 20;
		}
		super.setUserInfoBiLockDivid100(userInfo);
		request.setAttribute("percent", percent);
		request.setAttribute("is_fuwu", userInfo.getIs_fuwu());
		// 用户等级
		BaseData uiBaseData = new BaseData();
		uiBaseData.setType(Keys.BASE_DATA_ID.BASE_DATA_ID_200.getIndex());
		uiBaseData.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		uiBaseData.setId(userInfo.getUser_level());
		uiBaseData = getFacade().getBaseDataService().getBaseData(uiBaseData);

		int user_level = uiBaseData.getPre_number3();
		request.setAttribute("user_level", user_level);

		if (null != userInfo.getOwn_entp_id()) {
			// 查询今日订单
			OrderInfo orderInfoToday = new OrderInfo();
			orderInfoToday.setEntp_id(userInfo.getOwn_entp_id());
			orderInfoToday.getMap().put("order_state_ne", Keys.OrderState.ORDER_STATE_90.getIndex());
			orderInfoToday.getMap().put("order_state_ge", Keys.OrderState.ORDER_STATE_10.getIndex());
			orderInfoToday.getMap().put("st_add_date", sdFormat_ymd.format(new Date()));
			orderInfoToday.getMap().put("en_add_date", sdFormat_ymd.format(new Date()));
			int todayOrderCount = super.getFacade().getOrderInfoService().getOrderInfoCount(orderInfoToday);
			request.setAttribute("todayOrderCount", todayOrderCount);
		}

		// 销售金额
		OrderInfo orderInfoTodaySum = new OrderInfo();
		orderInfoTodaySum.getMap().put("order_state_ne", Keys.OrderState.ORDER_STATE_90.getIndex());
		orderInfoTodaySum.getMap().put("order_state_ge", Keys.OrderState.ORDER_STATE_10.getIndex());
		orderInfoTodaySum = super.getFacade().getOrderInfoService().getOrderInfoStatisticaSum(orderInfoTodaySum);
		request.setAttribute("orderInfoTodaySum", orderInfoTodaySum);

		// 待付款 order_state=0
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setAdd_user_id(userInfo.getId());
		orderInfo.getMap().put("order_type_in", Keys.OrderType.ORDER_TYPE_10.getIndex() + "");
		orderInfo.setOrder_state(Keys.OrderState.ORDER_STATE_0.getIndex());
		int count_daifukuan = getFacade().getOrderInfoService().getOrderInfoCount(orderInfo);
		request.setAttribute("count_daifukuan", count_daifukuan);

		// 待确认收货 order_state=20
		orderInfo.setOrder_state(Keys.OrderState.ORDER_STATE_20.getIndex());
		int count_daiquerenshouhuo = getFacade().getOrderInfoService().getOrderInfoCount(orderInfo);
		request.setAttribute("count_daiquerenshouhuo", count_daiquerenshouhuo);
		// 已收货 order_state=40
		orderInfo.setOrder_state(Keys.OrderState.ORDER_STATE_40.getIndex());
		int count_yiwancheng = getFacade().getOrderInfoService().getOrderInfoCount(orderInfo);
		request.setAttribute("count_yiwancheng", count_yiwancheng);

		// 近期订单
		orderInfo.setOrder_state(null);
		orderInfo.getRow().setCount(5);
		List<OrderInfo> orderInfoZuijinList = getFacade().getOrderInfoService().getOrderInfoList(orderInfo);
		request.setAttribute("orderInfoZuijinList", orderInfoZuijinList);

		// 近期收藏
		ScEntpComm scEntpComm = new ScEntpComm();
		scEntpComm.setAdd_user_id(userInfo.getId());
		scEntpComm.setIs_del(0);
		scEntpComm.setSc_type(2);
		scEntpComm.getRow().setCount(5);
		List<ScEntpComm> favList = super.getFacade().getScEntpCommService().getScEntpCommList(scEntpComm);
		if (null != favList && favList.size() > 0) {
			for (ScEntpComm sec : favList) {
				CommInfo commInfo = new CommInfo();
				commInfo.setId(sec.getLink_id());
				commInfo.setIs_del(0);
				commInfo = super.getFacade().getCommInfoService().getCommInfo(commInfo);
				if (null != commInfo) {
					sec.getMap().put("img_url", commInfo.getMain_pic());
					sec.getMap().put("price", commInfo.getSale_price());
				}
			}
		}
		request.setAttribute("favList", favList);

		// 判断是否有未审核通过的申请
		super.setApplyEntpIsAudit(request, ui);
		request.setAttribute("upLevelNeedPayMoney", super.getSysSetting(Keys.upLevelNeedPayMoney));
		return new ActionForward("/../manager/customer/IndexCustomer/welcome.jsp");
	}
}
