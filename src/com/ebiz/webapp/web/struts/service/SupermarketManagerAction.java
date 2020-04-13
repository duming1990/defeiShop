package com.ebiz.webapp.web.struts.service;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.OrderInfoDetails;
import com.ebiz.webapp.domain.PoorInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.struts.BaseAction;
import com.ebiz.webapp.web.util.DESPlus;

public class SupermarketManagerAction extends BaseAction {

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return null;
	}

	public ActionForward addUserInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		JSONObject datas = new JSONObject();
		String code = "0", msg = "";
		JSONObject jsonObject = JSON.parseObject(URLDecoder.decode((String) dynaBean.get("jsonObject"), "utf-8"));

		String user_id = (String) jsonObject.get("user_id");
		String real_name = (String) jsonObject.get("real_name");
		String password = (String) jsonObject.get("password");

		if (StringUtils.isBlank(password) || StringUtils.isBlank(user_id)) {
			msg = "参数有误！";
			super.ajaxReturnInfo(response, code, msg, datas);
		}

		UserInfo ui1 = new UserInfo();
		ui1.setMark_user_id(Integer.valueOf(user_id));
		int countQuerry = super.getFacade().getUserInfoService().getUserInfoCount(ui1);

		if (countQuerry <= 0) {
			UserInfo entity = new UserInfo();
			entity.setUser_name(UUID.randomUUID().toString());

			entity.setUser_type(Keys.UserType.USER_TYPE_2.getIndex());
			DESPlus des = new DESPlus();
			entity.setPassword(des.encrypt(password));
			entity.setAdd_date(new Date());
			entity.setAdd_user_id(new Integer(1));
			entity.setIs_daqu(0);
			entity.setIs_entp(0);
			entity.setIs_fuwu(0);
			entity.setIs_lianmeng(0);
			entity.setIs_active(1);
			entity.setIs_has_update_pass(0);
			entity.setMark_user_id(Integer.valueOf(user_id));

			entity.setYmid(super.getUserInfo(Keys.SYS_ADMIN_ID).getUser_name());// 全部保存用户名

			if (StringUtils.isBlank(real_name)) {
				entity.getMap().put("update_user_name_and_real_name", "true");
			} else {
				entity.setReal_name(real_name);
				entity.getMap().put("update_user_name", "true");
			}

			entity.getMap().put("insert_user_realtion", "true");

			entity.setLogin_count(1);
			entity.setLast_login_time(new Date());
			entity.setLast_login_ip(this.getIpAddr(request));

			Integer count = super.getFacade().getUserInfoService().createUserInfo(entity);
			if (count > 0) {
				code = "1";
				super.ajaxReturnInfo(response, code, msg, datas);
				return null;
			}
		} else {
			ui1 = super.getFacade().getUserInfoService().getUserInfo(ui1);
			if (null != ui1) {
				UserInfo userInfoUpdate = new UserInfo();
				userInfoUpdate.setId(ui1.getId());
				userInfoUpdate.setMark_user_id(Integer.valueOf(user_id));
				super.getFacade().getUserInfoService().modifyUserInfo(userInfoUpdate);
				code = "1";
				super.ajaxReturnInfo(response, code, msg, datas);
				return null;
			}
		}
		super.ajaxReturnInfo(response, code, msg, datas);
		return null;
	}

	public ActionForward addEntpInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		JSONObject datas = new JSONObject();
		String code = "0", msg = "";

		JSONObject jsonObject = JSON.parseObject(URLDecoder.decode((String) dynaBean.get("jsonObject"), "utf-8"));
		String entp_name = (String) jsonObject.get("entp_name");// 店铺名称
		String password = (String) jsonObject.get("password");// 联系人密码
		String entp_desc = (String) jsonObject.get("entp_desc");// 店铺简介
		String entp_addr = (String) jsonObject.get("entp_addr");// 店铺地址
		String entp_linkman = (String) jsonObject.get("entp_linkman");// 店铺联系人
		String entp_tel = (String) jsonObject.get("entp_tel");// 联系人电话
		String mark_entp_id = (String) jsonObject.get("mark_entp_id");// 店铺id

		EntpInfo entpName = new EntpInfo();
		entpName.setMark_entp_id(Integer.valueOf(mark_entp_id));
		entpName.setIs_del(0);
		int count = getFacade().getEntpInfoService().getEntpInfoCount(entpName);
		if (count == 0) {
			EntpInfo entity = new EntpInfo();
			entity.setUuid(UUID.randomUUID().toString());
			entity.setIs_del(0);
			entity.setAdd_date(new Date());
			entity.setEntp_type(Keys.EntpType.ENTP_TYPE_30.getIndex());
			entity.setIs_nx_entp(Keys.EntpType.ENTP_TYPE_30.getIndex());
			entity.setEntp_name(entp_name);
			entity.setEntp_desc(entp_desc);// 店铺简介
			entity.setEntp_addr(entp_addr);// 地址
			entity.setEntp_linkman(entp_linkman);// 企业联系人
			entity.setEntp_tel(entp_tel);
			entity.setAdd_date(new Date());
			UserInfo admin = super.getUserInfo(1);
			if (null != admin) {
				entity.setAdd_user_id(admin.getId());
				entity.setAdd_user_name(admin.getUser_name());
			}
			entity.setAudit_state(Keys.audit_state.audit_state_2.getIndex());
			entity.setAudit_date(new Date());
			entity.setEntp_no("S" + super.createEntpNo(entity.getId()));// 商家编号
			entity.setAudit_user_id(1);
			entity.setMark_entp_id(Integer.valueOf(mark_entp_id));
			entity.getMap().put("Supermarket", true);
			entity.getMap().put("password", password);
			Integer id = super.getFacade().getEntpInfoService().createEntpInfo(entity);
			if (id > 0) {
				code = "1";
				super.ajaxReturnInfo(response, code, msg, datas);
				return null;
			}
		}
		super.ajaxReturnInfo(response, code, msg, datas);
		return null;
	}

	public ActionForward addCommInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		JSONObject datas = new JSONObject();
		String code = "0", msg = "";

		JSONObject jsonObject = JSON.parseObject(URLDecoder.decode((String) dynaBean.get("jsonObject"), "utf-8"));
		String comm_name = (String) jsonObject.get("comm_name");
		String comm_no = (String) jsonObject.get("comm_no");
		String comm_price = (String) jsonObject.get("comm_price");
		String own_entp_id = (String) jsonObject.get("own_entp_id");
		String mark_comm_id = (String) jsonObject.get("mark_comm_id");

		EntpInfo entpInfo = new EntpInfo();
		entpInfo.setIs_del(0);
		entpInfo.setMark_entp_id(Integer.valueOf(own_entp_id));
		entpInfo = super.getFacade().getEntpInfoService().getEntpInfo(entpInfo);
		if (null != entpInfo) {
			CommInfo entity = new CommInfo();
			entity.setComm_name(comm_name);
			entity.setComm_no(comm_no);
			entity.setComm_type(Keys.CommType.COMM_TYPE_9.getIndex());
			entity.setOwn_entp_id(entpInfo.getId());
			BigDecimal sale_price = new BigDecimal(comm_price);
			entity.setSale_price(sale_price);
			entity.setIs_sell(1);
			entity.setAdd_date(new Date());
			entity.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
			entity.setIs_ziti(0);
			entity.setIs_fapiao(0);
			entity.setMark_comm_id(Integer.valueOf(mark_comm_id));
			UserInfo admin = super.getUserInfo(1);
			if (null != admin) {
				entity.setAdd_user_id(admin.getId());
				entity.setAdd_user_name(admin.getUser_name());
				entity.setAudit_user_id(admin.getId());
			}
			Integer count = super.getFacade().getCommInfoService().createCommInfo(entity);
			if (count > 0) {
				code = "1";
				super.ajaxReturnInfo(response, code, msg, datas);
				return null;
			}
		}
		super.ajaxReturnInfo(response, code, msg, datas);
		return null;
	}

	public ActionForward addOrderInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		JSONObject datas = new JSONObject();
		String code = "0", msg = "";

		JSONObject jsonObject = JSON.parseObject(URLDecoder.decode((String) dynaBean.get("jsonObject"), "utf-8"));
		String order_id = (String) jsonObject.get("order_id");// 订单id
		OrderInfo orderInfoTemp = JSON.parseObject(jsonObject.get("orderInfoTemp").toString(), OrderInfo.class);// 订单信息
		String mobile = (String) jsonObject.get("mobile");// 订单电话
		String user_id = (String) jsonObject.get("user_id");// 下单人的id

		JSONArray jsonArray = JSON.parseArray(jsonObject.get("orderInfoDetails").toString());

		UserInfo userInfo = new UserInfo();
		userInfo.setMark_user_id(Integer.valueOf(user_id));
		userInfo.setIs_del(0);
		userInfo.setMobile(mobile);
		userInfo = super.getFacade().getUserInfoService().getUserInfo(userInfo);

		if (null == userInfo) {
			super.ajaxReturnInfo(response, code, msg, datas);
			return null;
		}
		OrderInfo orderInfo = new OrderInfo();
		List<OrderInfoDetails> orderInfoDetailsList = new ArrayList<OrderInfoDetails>();

		Integer order_sum_num = 0;
		BigDecimal order_money = new BigDecimal("0");
		EntpInfo entpInfo = new EntpInfo();

		int i = 0;
		for (Object temp : jsonArray) {

			CommInfo commInfo = new CommInfo();
			commInfo.setIs_del(0);
			commInfo.setMark_comm_id(Integer.valueOf(jsonArray.getJSONObject(i).get("comm_id").toString()));
			commInfo = super.getFacade().getCommInfoService().getCommInfo(commInfo);
			if (null == commInfo) {
				super.ajaxReturnInfo(response, code, msg, datas);
				return null;
			}

			OrderInfoDetails orderInfoDetails = new OrderInfoDetails();
			Integer good_count = Integer.valueOf(jsonArray.getJSONObject(i).get("good_count").toString());// 商品数量
			BigDecimal good_price = new BigDecimal(jsonArray.getJSONObject(i).get("good_price").toString());// 商品价格
			Double pd_price = good_price.doubleValue();
			order_sum_num += good_count;

			order_money = order_money.add(new BigDecimal(pd_price * good_count));

			orderInfoDetails.setOrder_type(Keys.OrderType.ORDER_TYPE_40.getIndex());
			orderInfoDetails.setGood_count(good_count);
			orderInfoDetails.setComm_id(commInfo.getId());
			orderInfoDetails.setComm_name(commInfo.getComm_name());

			orderInfoDetails.setGood_price(good_price);
			orderInfoDetails.setGood_sum_price(new BigDecimal(pd_price * good_count));
			orderInfoDetails.setMatflow_price(new BigDecimal(0));

			// 实际支付金额
			BigDecimal actual_money = orderInfoDetails.getGood_sum_price();

			orderInfoDetails.setActual_money(actual_money);

			orderInfoDetails.setEntp_id(commInfo.getOwn_entp_id());
			entpInfo = super.getEntpInfo(commInfo.getOwn_entp_id());

			orderInfoDetailsList.add(orderInfoDetails);
			i++;
		}

		orderInfo.setTrade_index(this.creatTradeIndex());

		orderInfo.setOrderInfoDetailsList(orderInfoDetailsList);

		orderInfo.setPay_date(orderInfoTemp.getPay_date());
		orderInfo.setPay_type(orderInfoTemp.getPay_type());
		orderInfo.setRel_phone(orderInfoTemp.getRel_phone());
		orderInfo.setRel_name(orderInfoTemp.getRel_name());
		orderInfo.setOrder_num(order_sum_num);
		orderInfo.setNo_dis_money(order_money);
		orderInfo.setOrder_money(order_money);
		orderInfo.setOrder_type(Keys.OrderType.ORDER_TYPE_40.getIndex());
		orderInfo.setOrder_state(Keys.OrderState.ORDER_STATE_50.getIndex());
		orderInfo.setOrder_date(orderInfoTemp.getOrder_date());
		orderInfo.setAdd_date(orderInfoTemp.getAdd_date());
		orderInfo.setMark_order_id(Integer.valueOf(order_id));

		// 订单默认7天后失效
		orderInfo.setEnd_date(DateUtils.addDays(new Date(), Keys.ORDER_END_DATE));
		orderInfo.setAdd_user_id(userInfo.getId());
		orderInfo.setAdd_user_name(userInfo.getUser_name());
		if (null != entpInfo) {
			orderInfo.setEntp_id(entpInfo.getId());
			orderInfo.setEntp_name(entpInfo.getEntp_name());
		}
		JSONObject result = getFacade().getOrderInfoService().createSupermarketOrderInfo(orderInfo);

		code = "1";
		datas.put("result", result);
		super.ajaxReturnInfo(response, code, msg, datas);
		return null;
	}

	public ActionForward choosePoorInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		JSONObject jsonObject = JSON.parseObject(URLDecoder.decode((String) dynaBean.get("jsonObject"), "utf-8"));

		String real_name_like = (String) jsonObject.get("real_name_like");
		String province = (String) jsonObject.get("province");
		String city = (String) jsonObject.get("city");
		String country = (String) jsonObject.get("country");
		String town = (String) jsonObject.get("town");
		String village = (String) jsonObject.get("village");
		String requestPage = (String) jsonObject.get("requestPage");
		String pageSize = (String) jsonObject.get("pageSize");

		String code = "0", msg = "";
		if (StringUtils.isBlank(requestPage) || !GenericValidator.isInt(requestPage)) {
			msg = "requestPage参数不正确 CODE:1001";
			super.returnInfo(response, code, msg, null);
			return null;
		}
		if (StringUtils.isBlank(pageSize) || !GenericValidator.isInt(pageSize)) {
			pageSize = "10";
		}
		PoorInfo entity = new PoorInfo();
		super.copyProperties(entity, form);
		entity.setAudit_state(1);
		entity.setIs_del(0);
		entity.getMap().put("real_name_like", real_name_like);
		if (GenericValidator.isLong(village)) {
			entity.setP_index(Long.valueOf(village));
		} else if (GenericValidator.isLong(town)) {
			entity.getMap().put("town", town.substring(0, 9));
		} else if (GenericValidator.isLong(country)) {
			entity.getMap().put("country", country.substring(0, 6));
		} else if (GenericValidator.isLong(city)) {
			entity.getMap().put("city", city.substring(0, 4));
		} else if (GenericValidator.isLong(province)) {
			entity.getMap().put("province", province.substring(0, 2));
		}
		Pager pager = new Pager();
		Integer recordCount = getFacade().getPoorInfoService().getPoorInfoCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf(pageSize), requestPage);
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());
		List<PoorInfo> entityList = super.getFacade().getPoorInfoService().getPoorInfoPaginatedList(entity);

		JSONObject jsonObjectResult = new JSONObject();
		jsonObjectResult.put("entityList", entityList);
		jsonObjectResult.put("recordCount", recordCount);

		code = "1";
		super.returnInfo(response, code, msg, URLEncoder.encode(jsonObjectResult.toJSONString(), "utf-8"));
		return null;

	}

	public ActionForward getPoorInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		JSONObject jsonObject = JSON.parseObject(URLDecoder.decode((String) dynaBean.get("jsonObject"), "utf-8"));

		String id = (String) jsonObject.get("id");

		String code = "0", msg = "";
		if (StringUtils.isBlank(id)) {
			msg = "参数不正确 CODE:1001";
			super.returnInfo(response, code, msg, null);
			return null;
		}
		PoorInfo entityQuery = new PoorInfo();
		entityQuery.setId(Integer.valueOf(id));
		entityQuery = super.getFacade().getPoorInfoService().getPoorInfo(entityQuery);
		if (null == entityQuery) {
			msg = "未查询到该信息";
			super.returnInfo(response, code, msg, null);
			return null;
		}

		JSONObject jsonObjectResult = new JSONObject();
		jsonObjectResult.put("poorInfo", entityQuery);
		jsonObjectResult.put("p_name", super.getProvinceName(entityQuery.getP_index()));

		code = "1";
		super.returnInfo(response, code, msg, URLEncoder.encode(jsonObjectResult.toJSONString(), "utf-8"));
		return null;

	}

	// 添加扶贫记录
	public ActionForward addUserBiRecordForAid(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		JSONObject datas = new JSONObject();
		String code = "0", msg = "";

		JSONObject jsonObject = JSON.parseObject((String) dynaBean.get("jsonObject"));
		String poor_id = (String) jsonObject.get("poor_id");// 贫困户id
		String bi_get_type = (String) jsonObject.get("bi_get_type");// 获取来源：3001-九个挑夫生鲜扶贫
		String money = (String) jsonObject.get("money");// 扶贫金额
		String entp_name = (String) jsonObject.get("entp_name");// 帮扶单位名称
		String add_user_name = (String) jsonObject.get("add_user_name");// 帮扶人

		if (StringUtils.isBlank(poor_id) || !GenericValidator.isInt(poor_id)) {
			msg = "poor_id参数不正确 CODE:1001";
			super.returnInfo(response, code, msg, null);
			return null;
		}
		if (StringUtils.isBlank(bi_get_type) || !GenericValidator.isInt(bi_get_type)) {
			msg = "bi_get_type参数不正确 CODE:1002";
			super.returnInfo(response, code, msg, null);
			return null;
		}
		if (StringUtils.isBlank(money) || !GenericValidator.isDouble(money)) {
			msg = "money参数不正确 CODE:1003";
			super.returnInfo(response, code, msg, null);
			return null;
		}
		if (StringUtils.isBlank(entp_name) || StringUtils.isBlank(add_user_name)) {
			msg = "entp_name或add_user_name参数不正确 CODE:1004";
			super.returnInfo(response, code, msg, null);
			return null;
		}
		UserInfo userInfo = new UserInfo();
		userInfo.setUser_type(2);
		userInfo.setIs_poor(1);
		userInfo.setPoor_id(Integer.valueOf(poor_id));
		userInfo.setIs_del(0);
		userInfo = super.getFacade().getUserInfoService().getUserInfo(userInfo);

		if (null == userInfo) {
			msg = "贫困户不存在 CODE:1004";
			super.returnInfo(response, code, msg, null);
			return null;
		}

		int count = super
				.getFacade()
				.getPoorInfoService()
				.createPoorInfoAid(userInfo.getId(), Integer.valueOf(bi_get_type), new BigDecimal(money), entp_name,
						add_user_name);
		if (count == 0) {
			msg = "扶贫金发放失败 CODE:1004";
			super.returnInfo(response, code, msg, null);
			return null;
		}
		code = "1";
		msg = "扶贫金发放成功";
		super.returnInfo(response, code, msg, datas);
		return null;
	}

	public ActionForward addSxOrderInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {// 同步柜子订单

		DynaBean dynaBean = (DynaBean) form;
		JSONObject datas = new JSONObject();
		String code = "0", msg = "";

		JSONObject jsonObject = JSON.parseObject(URLDecoder.decode((String) dynaBean.get("jsonObject"), "utf-8"));
		OrderInfo orderInfoTemp = JSON.parseObject(jsonObject.get("orderInfoTemp").toString(), OrderInfo.class);// 订单信息

		JSONArray jsonArray = JSON.parseArray(jsonObject.get("orderInfoDetails").toString());

		OrderInfo orderInfo = new OrderInfo();
		List<OrderInfoDetails> orderInfoDetailsList = new ArrayList<OrderInfoDetails>();

		Integer order_sum_num = 0;
		BigDecimal order_money = new BigDecimal("0");

		int i = 0;
		for (Object temp : jsonArray) {
			OrderInfoDetails orderInfoDetails = new OrderInfoDetails();
			Integer good_count = Integer.valueOf(jsonArray.getJSONObject(i).get("good_count").toString());// 商品数量
			BigDecimal good_price = new BigDecimal(jsonArray.getJSONObject(i).get("good_price").toString());// 商品价格
			Double pd_price = good_price.doubleValue();
			order_sum_num += good_count;

			order_money = order_money.add(new BigDecimal(pd_price * good_count));

			orderInfoDetails.setOrder_type(Keys.OrderType.ORDER_TYPE_50.getIndex());
			orderInfoDetails.setGood_count(good_count);
			orderInfoDetails.setComm_id(Integer.valueOf(jsonArray.getJSONObject(i).get("comm_id").toString()));
			orderInfoDetails.setComm_name(jsonArray.getJSONObject(i).get("comm_name").toString());

			orderInfoDetails.setGood_price(good_price);
			orderInfoDetails.setGood_sum_price(new BigDecimal(pd_price * good_count));
			orderInfoDetails.setMatflow_price(new BigDecimal(0));

			// 实际支付金额
			BigDecimal actual_money = orderInfoDetails.getGood_sum_price();

			orderInfoDetails.setActual_money(actual_money);

			orderInfoDetails.setEntp_id(Integer.valueOf(jsonArray.getJSONObject(i).get("entp_id").toString()));
			orderInfoDetailsList.add(orderInfoDetails);
			i++;
		}

		orderInfo.setTrade_index("SX_" + orderInfoTemp.getTrade_index());

		orderInfo.setOrderInfoDetailsList(orderInfoDetailsList);

		orderInfo.setPay_date(orderInfoTemp.getPay_date());
		orderInfo.setPay_type(orderInfoTemp.getPay_type());
		orderInfo.setRel_phone(orderInfoTemp.getRel_phone());
		orderInfo.setRel_name(orderInfoTemp.getRel_name());
		orderInfo.setOrder_num(order_sum_num);
		orderInfo.setNo_dis_money(order_money);
		orderInfo.setOrder_money(order_money);
		orderInfo.setOrder_type(Keys.OrderType.ORDER_TYPE_50.getIndex());
		orderInfo.setOrder_state(Keys.OrderState.ORDER_STATE_50.getIndex());
		orderInfo.setOrder_date(orderInfoTemp.getOrder_date());
		orderInfo.setAdd_date(orderInfoTemp.getAdd_date());
		// orderInfo.setMark_order_id(Integer.valueOf(order_id));

		// 订单默认7天后失效
		orderInfo.setEnd_date(DateUtils.addDays(new Date(), Keys.ORDER_END_DATE));
		orderInfo.setAdd_user_id(orderInfoTemp.getAdd_user_id());
		orderInfo.setAdd_user_name(orderInfoTemp.getAdd_user_name());
		orderInfo.setEntp_id(orderInfoTemp.getEntp_id());
		orderInfo.setEntp_name(orderInfoTemp.getEntp_name());
		JSONObject result = getFacade().getOrderInfoService().createSupermarketOrderInfo(orderInfo);

		code = "1";
		datas.put("result", result);
		super.ajaxReturnInfo(response, code, msg, datas);
		return null;
	}
}
