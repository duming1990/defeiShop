package com.ebiz.webapp.web.struts.manager.customer;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.struts.BaseWebAction;

/**
 * @author Wu,Yang
 * @version 2011-04-22
 */
public class WelcomeAction extends BaseWebAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfoFromSession(request);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String ndate = sdf.format(date);
		request.setAttribute("ndate", ndate);

		// 产品总数
		CommInfo commInfo = new CommInfo();
		commInfo.setOwn_entp_id(ui.getOwn_entp_id());
		commInfo.setIs_del(0);
		commInfo.setComm_type(Keys.CommType.COMM_TYPE_2.getIndex());
		Integer commInfoCount = super.getFacade().getCommInfoService().getCommInfoCount(commInfo);
		request.setAttribute("commInfoCount", commInfoCount);

		// 今天新增加产品数量
		CommInfo commInfoZj = new CommInfo();
		commInfo.setOwn_entp_id(ui.getOwn_entp_id());
		commInfoZj.getMap().put("today_date", ndate);
		commInfoZj.setIs_del(0);
		commInfoZj.setComm_type(Keys.CommType.COMM_TYPE_2.getIndex());
		Integer commInfoZjCount = super.getFacade().getCommInfoService().getCommInfoCount(commInfoZj);
		request.setAttribute("commInfoZjCount", commInfoZjCount);

		// 待审核的商品数量
		CommInfo commInfoDsh = new CommInfo();
		commInfoDsh.setOwn_entp_id(ui.getOwn_entp_id());
		commInfoDsh.setIs_del(0);
		commInfoDsh.setAudit_state(0);
		commInfoDsh.setComm_type(Keys.CommType.COMM_TYPE_2.getIndex());
		Integer commInfoDshCount = super.getFacade().getCommInfoService().getCommInfoCount(commInfoDsh);
		request.setAttribute("commInfoDshCount", commInfoDshCount);

		// 订单总数
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setEntp_id(ui.getOwn_entp_id());
		orderInfo.getMap().put("order_state_ne", Keys.OrderState.ORDER_STATE_90.getIndex());
		Integer orderInfoCount = super.getFacade().getOrderInfoService().getOrderInfoCount(orderInfo);
		request.setAttribute("orderInfoCount", orderInfoCount);

		// 今日订单数
		OrderInfo todayOrder = new OrderInfo();
		todayOrder.setEntp_id(ui.getOwn_entp_id());
		todayOrder.getMap().put("st_add_date", sdFormat_ymd.format(new Date()));
		todayOrder.getMap().put("en_add_date", sdFormat_ymd.format(new Date()));
		todayOrder.getMap().put("order_state_ne", Keys.OrderState.ORDER_STATE_90.getIndex());
		Integer todayOrderCount = super.getFacade().getOrderInfoService().getOrderInfoCount(todayOrder);
		request.setAttribute("todayOrderCount", todayOrderCount);

		return new ActionForward("/customer/Welcome/index.jsp");
	}

}
