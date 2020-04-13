package com.ebiz.webapp.web.struts.m;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.alibaba.fastjson.JSONObject;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;

public class MGroupBuyAction extends MBaseWebAction {
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.getGroupOrderInfo(mapping, form, request, response);
	}

	/**
	 * 查询商品可拼团主订单信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getGroupOrderInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String comm_id = (String) dynaBean.get("comm_id");

		JSONObject datas = new JSONObject();
		String msg = "", code = "0";

		if (StringUtils.isBlank(comm_id)) {
			msg = "参数有误！";
			super.ajaxReturnInfo(response, code, msg, datas);
			return null;
		}

		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setOrder_type(Keys.OrderType.ORDER_TYPE_100.getIndex());// 订单类型，拼团订单
		orderInfo.setIs_leader(1);// 是否主订单，1：是
		orderInfo.setIs_group_success(0);// 是否拼团成功，0：否
		orderInfo.setOrder_state(Keys.OrderState.ORDER_STATE_10.getIndex());
		orderInfo.getMap().put("current_time", new Date());// 当前时间
		orderInfo.getMap().put("comm_id", Integer.valueOf(comm_id));// 拼团商品id

		// 查询所有可参团的主订单
		List<OrderInfo> groupLeaderOrderInfo = super.getFacade().getOrderInfoService()
				.getGroupLeaderOrderInfo(orderInfo);
		if (groupLeaderOrderInfo == null || groupLeaderOrderInfo.size() == 0) {
			msg = "该商品还没有可拼的团";
			super.ajaxReturnInfo(response, code, msg, datas);
			return null;
		}

		CommInfo commInfo = new CommInfo();
		commInfo.setId(Integer.valueOf(comm_id));
		commInfo = super.getFacade().getCommInfoService().getCommInfo(commInfo);
		int groupCount = commInfo.getGroup_count();
		// 查询主订单下的所有子订单
		OrderInfo entity = new OrderInfo();
		for (OrderInfo o : groupLeaderOrderInfo) {
			entity.setLeader_order_id(o.getId());// 子订单所属的主订单id
			entity.setIs_leader(0);// 是否主订单，0：否
			entity.setOrder_state(Keys.OrderState.ORDER_STATE_10.getIndex());//
			List<OrderInfo> childOrderInfoList = super.getFacade().getOrderInfoService().getOrderInfoList(entity);
			o.getMap().put("childOrderInfoList", childOrderInfoList);
			int leftCount = groupCount - childOrderInfoList.size() - 1;
			o.getMap().put("leftCount", leftCount);
		}
		code = "1";
		msg = "查询成功";
		datas.put("LeaderOrderInfoList", groupLeaderOrderInfo);
		super.ajaxReturnInfo(response, code, msg, datas);
		return null;
	}

	/**
	 * 查询用户订单中有没有未完成的拼团主订单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkOrderOfUser(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String comm_id = (String) dynaBean.get("comm_id");

		JSONObject datas = new JSONObject();
		String msg = "", code = "0";
		if (StringUtils.isBlank(comm_id)) {
			code = "1";
			msg = "参数有误！";
			super.ajaxReturnInfo(response, code, msg, datas);
			return null;
		}
		UserInfo ui = super.getUserInfoFromSession(request);

		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setOrder_type(Keys.OrderType.ORDER_TYPE_100.getIndex());
		orderInfo.setAdd_user_id(ui.getId());// 创建订单的用户id
		orderInfo.setIs_leader(1);// 是否主订单，1：是
		orderInfo.setIs_group_success(0);// 是否拼团成功，0：否
		orderInfo.setOrder_state(Keys.OrderState.ORDER_STATE_10.getIndex());// 已付款
		orderInfo.getMap().put("comm_id", Integer.valueOf(comm_id));// 拼团商品id
		List<OrderInfo> groupLeaderOrderInfo = super.getFacade().getOrderInfoService()
				.getGroupLeaderOrderInfo(orderInfo);

		if (null != groupLeaderOrderInfo && groupLeaderOrderInfo.size() > 0) {
			code = "1";
			msg = "该商品您有未完成的拼团主订单";
		}
		super.ajaxReturnInfo(response, code, msg, datas);
		return null;
	}

	/**
	 * 拼团详情
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getLeaderOrderInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String leaderOrderId = (String) dynaBean.get("leaderOrderId");
		String comm_id = (String) dynaBean.get("comm_id");

		JSONObject datas = new JSONObject();
		String msg = "", code = "0";

		if (StringUtils.isBlank(comm_id)) {
			msg = "参数有误！";
			super.ajaxReturnInfo(response, code, msg, datas);
			return null;
		}

		if (StringUtils.isBlank(leaderOrderId)) {
			msg = "参数有误！";
			super.ajaxReturnInfo(response, code, msg, datas);
			return null;
		}

		CommInfo commInfo = new CommInfo();
		commInfo.setId(Integer.valueOf(comm_id));
		commInfo = super.getFacade().getCommInfoService().getCommInfo(commInfo);

		OrderInfo leaderOrderInfo = new OrderInfo();
		leaderOrderInfo.setId(Integer.valueOf(leaderOrderId));
		leaderOrderInfo = super.getFacade().getOrderInfoService().getOrderInfo(leaderOrderInfo);

		OrderInfo entity = new OrderInfo();
		entity.setOrder_type(Keys.OrderType.ORDER_TYPE_100.getIndex());
		entity.setLeader_order_id(leaderOrderInfo.getId());
		entity.setIs_leader(0);
		entity.setOrder_state(Keys.OrderState.ORDER_STATE_10.getIndex());
		List<OrderInfo> childOrderInfoList = super.getFacade().getOrderInfoService().getOrderInfoList(entity);

		leaderOrderInfo.getMap().put("leftCount", commInfo.getGroup_count() - childOrderInfoList.size() - 1);
		leaderOrderInfo.getMap().put("childOrderInfoList", childOrderInfoList);
		if (childOrderInfoList != null && childOrderInfoList.size() > 0) {
			leaderOrderInfo.getMap().put("hasChild", true);
		}

		code = "1";
		msg = "查询成功";
		datas.put("leaderOrderInfo", leaderOrderInfo);
		super.ajaxReturnInfo(response, code, msg, datas);
		return null;
	}

	/**
	 * 查询用户参加的团，是不是自己开的团
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkLeader(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String leaderOrderId = (String) dynaBean.get("leaderOrderId");
		String comm_id = (String) dynaBean.get("comm_id");

		JSONObject datas = new JSONObject();
		String msg = "", code = "0";

		if (StringUtils.isBlank(leaderOrderId) || StringUtils.isBlank(comm_id)) {
			msg = "参数有误！";
			super.ajaxReturnInfo(response, code, msg, datas);
			return null;
		}

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			msg = "您还未登录，请先登录系统！";
			super.ajaxReturnInfo(response, code, msg, datas);
			return null;
		}
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setId(Integer.valueOf(leaderOrderId));
		orderInfo = super.getFacade().getOrderInfoService().getOrderInfo(orderInfo);

		if (orderInfo.getAdd_user_id().intValue() == ui.getId().intValue()) {
			code = "1";
			msg = "不能加入自己的团！";
			super.ajaxReturnInfo(response, code, msg, datas);
			return null;
		}

		OrderInfo orderQuery = new OrderInfo();
		orderQuery.setOrder_type(Keys.OrderType.ORDER_TYPE_100.getIndex());
		orderQuery.setLeader_order_id(Integer.valueOf(leaderOrderId));
		orderQuery.setAdd_user_id(ui.getId());
		orderQuery.setOrder_state(Keys.OrderState.ORDER_STATE_10.getIndex());
		OrderInfo orderInfoResult = super.getFacade().getOrderInfoService().getOrderInfo(orderQuery);

		if (orderInfoResult != null) {
			code = "1";
			msg = "您已加入该团，请等候拼团结束！";
			super.ajaxReturnInfo(response, code, msg, datas);
			return null;
		}

		// 拼团类型是老带新，参团用户必须是主订单用户邀请的新用户
		CommInfo commInfo = new CommInfo();
		commInfo.setComm_type(Keys.CommType.COMM_TYPE_20.getIndex());
		commInfo.setId(Integer.valueOf(comm_id));
		commInfo.setIs_del(0);
		commInfo.setIs_sell(1);
		commInfo = super.getFacade().getCommInfoService().getCommInfo(commInfo);
		if (commInfo == null) {
			super.ajaxReturnInfo(response, "1", "商品已下架", null);
			return null;
		} else {
			if (commInfo.getGroup_type() != null && commInfo.getGroup_type().intValue() == 2) {
				UserInfo userInfo = super.getUserInfo(orderInfo.getAdd_user_id());// 主订单所属用户
				// 参团用户的推荐人是主订单所属用户
				boolean flag1 = ui.getYmid().equals(userInfo.getUser_name());
				// 参团用户的注册时间在拼团主订单的支付时间之后
				boolean flag2 = ui.getAdd_date().getTime() > orderInfo.getPay_date().getTime();
				if (!(flag1 && flag2)) {
					super.ajaxReturnInfo(response, "1", "该团是老带新拼团，您不符合参团要求，无法参团", null);
					return null;
				}
			}
		}
		super.ajaxReturnInfo(response, code, msg, datas);
		return null;
	}
}
