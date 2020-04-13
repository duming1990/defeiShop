package com.ebiz.webapp.web.struts;

import java.io.File;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.util.WebUtils;

import push.AppPush;

import com.aiisen.weixin.httpclient.HttpClientUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.BaseAttribute;
import com.ebiz.webapp.domain.BaseAttributeSon;
import com.ebiz.webapp.domain.BaseAuditRecord;
import com.ebiz.webapp.domain.BaseClass;
import com.ebiz.webapp.domain.BaseData;
import com.ebiz.webapp.domain.BaseFiles;
import com.ebiz.webapp.domain.BaseLink;
import com.ebiz.webapp.domain.BaseProvince;
import com.ebiz.webapp.domain.CardInfo;
import com.ebiz.webapp.domain.CartInfo;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.CommTczhPrice;
import com.ebiz.webapp.domain.CommentInfo;
import com.ebiz.webapp.domain.CommentInfoSon;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.Freight;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.OrderInfoDetails;
import com.ebiz.webapp.domain.OrderReturnInfo;
import com.ebiz.webapp.domain.PdImgs;
import com.ebiz.webapp.domain.ServiceCenterInfo;
import com.ebiz.webapp.domain.ShippingAddress;
import com.ebiz.webapp.domain.SysOperLog;
import com.ebiz.webapp.domain.UserBiRecord;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.UserScoreRecord;
import com.ebiz.webapp.domain.VillageInfo;
import com.ebiz.webapp.domain.WlCompInfo;
import com.ebiz.webapp.domain.WlOrderInfo;
import com.ebiz.webapp.domain.YhqInfo;
import com.ebiz.webapp.domain.YhqInfoSon;
import com.ebiz.webapp.util.DeliveryUtils;
import com.ebiz.webapp.util.GetApiUtils;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.SMS;
import com.ebiz.webapp.web.util.DateTools;
import com.ebiz.webapp.web.util.EmailUtils;
import com.ebiz.webapp.web.util.FileTools;
import com.ebiz.webapp.web.util.SmsUtils;
import com.ebiz.webapp.web.util.StringHelper;
import com.ebiz.webapp.web.util.dysms.DySmsUtils;
import com.ebiz.webapp.web.util.dysms.SmsTemplate;

/**
 * @author Wu,Yang
 * @version 2011-04-27
 */
public class CsAjaxAction extends BaseCsAjaxAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return null;
	}

	/**
	 * Ajax asynchronous request to get BaseProvince List
	 * 
	 * @return json: [[key, value],[key, value]..]
	 */
	public ActionForward getBaseProvinceList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String par_index = (String) dynaBean.get("p_index");

		if (!GenericValidator.isLong(par_index)) {
			return null;
		}

		BaseProvince baseProvince = new BaseProvince(); // 省
		baseProvince.setPar_index(Long.valueOf(par_index));
		baseProvince.setIs_del(new Integer("0"));

		List<BaseProvince> baseProvinceList = getFacade().getBaseProvinceService().getBaseProvinceList(baseProvince);
		List<String> dataList = new ArrayList<String>();

		for (BaseProvince entity : baseProvinceList) {
			dataList.add(StringUtils.join(new String[] { "[\"", entity.getP_name(), "\",\"",
					String.valueOf(entity.getP_index()), "\"]" }));
		}

		super.renderJson(response, StringUtils.join(new String[] { "[", StringUtils.join(dataList, ","), "]" }));
		return null;
	}

	/**
	 * @author Zhang,Xufeng
	 * @version 2011-05-11
	 * @desc 取得始发地的省市县信息
	 */
	public ActionForward getSrcBaseProvinceList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String par_index = (String) dynaBean.get("src_p_index");

		if (!GenericValidator.isLong(par_index)) {
			return null;
		}

		BaseProvince baseProvince = new BaseProvince();
		baseProvince.setPar_index(Long.valueOf(par_index));
		baseProvince.setIs_del(new Integer("0"));

		List<BaseProvince> baseProvinceList = getFacade().getBaseProvinceService().getBaseProvinceList(baseProvince);
		List<String> dataList = new ArrayList<String>();

		for (BaseProvince entity : baseProvinceList) {
			dataList.add(StringUtils.join(new String[] { "[\"", entity.getP_name(), "\",\"",
					String.valueOf(entity.getP_index()), "\"]" }));
		}

		super.renderJson(response, StringUtils.join(new String[] { "[", StringUtils.join(dataList, ","), "]" }));
		return null;
	}

	/**
	 * @author Zhang,Xufeng
	 * @version 2011-05-11
	 * @desc 取得目的地的省市县信息
	 */
	public ActionForward getDestBaseProvinceList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String par_index = (String) dynaBean.get("dest_p_index");

		if (!GenericValidator.isLong(par_index)) {
			return null;
		}

		BaseProvince baseProvince = new BaseProvince();
		baseProvince.setPar_index(Long.valueOf(par_index));
		baseProvince.setIs_del(new Integer("0"));

		List<BaseProvince> baseProvinceList = getFacade().getBaseProvinceService().getBaseProvinceList(baseProvince);
		List<String> dataList = new ArrayList<String>();

		for (BaseProvince entity : baseProvinceList) {
			dataList.add(StringUtils.join(new String[] { "[\"", entity.getP_name(), "\",\"",
					String.valueOf(entity.getP_index()), "\"]" }));
		}

		super.renderJson(response, StringUtils.join(new String[] { "[", StringUtils.join(dataList, ","), "]" }));
		return null;
	}

	public ActionForward saveCommentInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommentInfo entity = new CommentInfo();
		super.copyProperties(entity, form);
		entity.setComm_ip(this.getIpAddr(request));
		entity.setComm_time(new Date());
		entity.setComm_state(0);// 未发布
		entity.setOrder_value(0);
		Integer count = getFacade().getCommentInfoService().createCommentInfo(entity);
		StringBuffer result = new StringBuffer();
		result.append("{\"result\":");
		if (null != count) {
			result.append("true");
		} else {
			result.append("false");
		}
		result.append("}");
		super.renderJson(response, result.toString());
		return null;
	}

	public ActionForward saveCartInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String gm_pd_count = (String) dynaBean.get("gm_pd_count");
		String isPt = (String) dynaBean.get("isPt");

		CartInfo entity = new CartInfo();
		super.copyProperties(entity, form);
		UserInfo ui = super.getUserInfoFromSession(request);
		StringBuffer result = new StringBuffer();
		result.append("{\"result\":");
		Integer cart_id = 0;
		if (null == ui) {
			result.append("false");
			result.append("}");
			super.renderJson(response, result.toString());
			return null;
		}

		// 查询是不是自己企业的商品
		if (null != ui.getIs_entp() && ui.getIs_entp().intValue() == 1 && null != ui.getOwn_entp_id()) {
			if (ui.getUser_type() == Keys.UserType.USER_TYPE_1.getIndex()
					|| ui.getOwn_entp_id().intValue() == entity.getEntp_id().intValue()) {
				result.append("false,");
				String msg = "\"用户不能购买自己店铺商品！\"";
				result.append("\"msg\":").append(msg);
				result.append("}");
				super.renderJson(response, result.toString());
				return null;
			}
		}

		// 非付款会员，不能购买
		// if (ui.getUser_level().intValue() == Keys.USER_LEVEL_FX.intValue()) {
		// result.append("false,");
		// String msg = "\"非付款会员，不能购买，请升级成付费会员！\",";
		// result.append("\"msg\":").append(msg);
		// result.append("\"flag\":").append(1);
		// result.append("}");
		// super.renderJson(response, result.toString());
		// return null;
		// }
		entity.setUser_id(ui.getId());

		/********** 删除该用户预售类型的购物车 ***********/
		CartInfo cartInfo_remove = new CartInfo();
		cartInfo_remove.setUser_id(ui.getId());
		Map<String, Object> map = cartInfo_remove.getMap();
		map.put("cart_type", Keys.CartType.CART_TYPE_30.getIndex());
		cartInfo_remove.setMap(map);
		getFacade().getCartInfoService().removeCartInfo(cartInfo_remove);
		/********** 删除该用户预售类型的购物车 ***********/

		/********** 删除该用户拼团类型的购物车 ***********/
		map.put("cart_type", Keys.CartType.CART_TYPE_100.getIndex());
		cartInfo_remove.setMap(map);
		getFacade().getCartInfoService().removeCartInfo(cartInfo_remove);
		/********** 删除该用户拼团类型的购物车 ***********/
		CartInfo cartInfoTemp = getFacade().getCartInfoService().getCartInfo(entity);

		CommInfo commInfo = super.getCommInfo(entity.getComm_id());
		if (null == commInfo) {
			result.append("false,");
			String msg = "\"不存在该商品！\"";
			result.append("\"msg\":").append(msg);
			result.append("}");
			super.renderJson(response, result.toString());
			return null;
		}

		entity.setCls_id(commInfo.getCls_id());
		entity.setCls_name(commInfo.getCls_name());
		entity.setComm_name(commInfo.getComm_name());
		entity.setEntp_id(commInfo.getOwn_entp_id());
		entity.setFre_id(commInfo.getFreight_id());

		if (null == entity.getComm_tczh_id()) {
			result.append("false,");
			String msg = "\"套餐不存在！\"";
			result.append("\"msg\":").append(msg);
			result.append("}");
			super.renderJson(response, result.toString());
			return null;
		}
		CommTczhPrice commTczhPrice = new CommTczhPrice();
		commTczhPrice.setId(entity.getComm_tczh_id());
		commTczhPrice = super.getFacade().getCommTczhPriceService().getCommTczhPrice(commTczhPrice);
		if (null == commTczhPrice) {
			result.append("false,");
			String msg = "\"套餐不存在！\"";
			result.append("\"msg\":").append(msg);
			result.append("}");
			super.renderJson(response, result.toString());
			return null;
		}

		if (null != cartInfoTemp) {

			CartInfo cartInfo = new CartInfo();
			cartInfo.setId(cartInfoTemp.getId());
			if (StringUtils.isNotBlank(gm_pd_count)) {
				cartInfo.setPd_count(cartInfoTemp.getPd_count() + Integer.valueOf(gm_pd_count));
				cartInfo.setComm_weight(commTczhPrice.getComm_weight().multiply(new BigDecimal(gm_pd_count))
						.add(cartInfoTemp.getComm_weight()));
			} else {
				cartInfo.setPd_count(cartInfoTemp.getPd_count() + 1);
				cartInfo.setComm_weight(cartInfoTemp.getComm_weight().add(commTczhPrice.getComm_weight()));
			}
			getFacade().getCartInfoService().modifyCartInfo(cartInfo);
			cart_id = cartInfoTemp.getId();
		} else {// 插入
			// 是拼团购买，设置为拼团价
			if (null != isPt && "1".equals(isPt)) {
				entity.setPd_price(commTczhPrice.getGroup_price());
			} else {
				entity.setPd_price(commTczhPrice.getComm_price());
			}
			entity.setComm_tczh_name(commTczhPrice.getTczh_name());

			if (commInfo.getComm_type() == Keys.CommType.COMM_TYPE_10.getIndex()) {
				entity.setCart_type(Keys.CartType.CART_TYPE_30.getIndex());// 预售商品
			}

			if (StringUtils.isNotBlank(gm_pd_count)) {
				entity.setPd_count(Integer.valueOf(gm_pd_count));
				entity.setComm_weight(commTczhPrice.getComm_weight().multiply(new BigDecimal(entity.getPd_count())));
			} else {
				entity.setPd_count(1);
				entity.setComm_weight(commTczhPrice.getComm_weight().multiply(new BigDecimal(entity.getPd_count())));
			}
			entity.setUser_name(ui.getUser_name());
			if (null != entity.getEntp_id()) {
				EntpInfo entpInfo = new EntpInfo();
				entpInfo.setId(entity.getEntp_id());
				entpInfo.setIs_del(0);
				entpInfo = getFacade().getEntpInfoService().getEntpInfo(entpInfo);
				if (null != entpInfo) {
					entity.setEntp_name(entpInfo.getEntp_name());
				}
			}

			cart_id = getFacade().getCartInfoService().createCartInfo(entity);
		}

		if (null != cart_id) {
			result.append(cart_id);
		} else {
			result.append("false");
		}

		result.append("}");
		super.renderJson(response, result.toString());
		return null;

	}

	public ActionForward orderInfoAddCart(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JSONObject data = new JSONObject();
		Integer cart_id = 0;
		int ret = 0;
		String msg = "加入购物车失败";
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		UserInfo ui = super.getUserInfoFromSession(request);

		if (null == ui) {
			msg = "当前用户为空";
			return super.returnJson(ret, msg, data, response);
		}

		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setId(Integer.valueOf(id));
		orderInfo = getFacade().getOrderInfoService().getOrderInfo(orderInfo);
		if (null == orderInfo) {
			msg = "订单不存在";
			return super.returnJson(ret, msg, data, response);
		}

		OrderInfoDetails ods = new OrderInfoDetails();
		ods.setOrder_id(orderInfo.getId());
		// ods.setIs_tuihuo(0);
		List<OrderInfoDetails> odsList = getFacade().getOrderInfoDetailsService().getOrderInfoDetailsList(ods);
		if (null == odsList || odsList.size() == 0) {
			msg = "订单明细不存在";
			return super.returnJson(ret, msg, data, response);
		}
		for (OrderInfoDetails temp : odsList) {
			CommInfo commInfo = new CommInfo();
			commInfo.setId(temp.getComm_id());
			commInfo.setIs_del(0);
			commInfo = getFacade().getCommInfoService().getCommInfo(commInfo);
			data.put("order_type", orderInfo.getOrder_type());
			if (orderInfo.getOrder_type() == Keys.OrderType.ORDER_TYPE_10.getIndex()) {
				CartInfo entity = new CartInfo();
				entity.setUser_id(ui.getId());
				entity.setCls_id(temp.getCls_id());
				entity.setCls_name(temp.getCls_name());
				entity.setEntp_id(temp.getEntp_id());
				entity.setComm_id(temp.getComm_id());
				entity.setComm_name(temp.getComm_name());
				entity.setComm_tczh_id(temp.getComm_tczh_id());
				entity.setComm_tczh_name(temp.getComm_tczh_name());
				entity.setPd_id(temp.getPd_id());
				entity.setPd_name(temp.getPd_name());
				entity.setCart_type(Keys.CartType.CART_TYPE_10.getIndex());
				CartInfo cartInfoTemp = getFacade().getCartInfoService().getCartInfo(entity);
				if (null != cartInfoTemp) {// 购物车中已有该商品
					CartInfo cartInfo = new CartInfo();
					cartInfo.setId(cartInfoTemp.getId());
					cartInfo.setPd_count(cartInfoTemp.getPd_count() + temp.getGood_count());
					cartInfo.setComm_weight(cartInfoTemp.getComm_weight().add(
							commInfo.getComm_weight().multiply(new BigDecimal(temp.getGood_count()))));
					getFacade().getCartInfoService().modifyCartInfo(cartInfo);
					cart_id = cartInfoTemp.getId();
				} else {// 插入
					entity.setFre_id(commInfo.getFreight_id());

					entity.setPd_price(temp.getGood_price());
					entity.setDelivery_p_index(null);
					entity.setComm_weight(null);

					entity.setPd_count(temp.getGood_count());
					entity.setComm_weight(commInfo.getComm_weight().multiply(new BigDecimal(entity.getPd_count())));
					entity.setUser_name(ui.getUser_name());
					if (null != entity.getEntp_id()) {
						EntpInfo entpInfo = new EntpInfo();
						entpInfo.setId(entity.getEntp_id());
						entpInfo.setIs_del(0);
						entpInfo = getFacade().getEntpInfoService().getEntpInfo(entpInfo);
						if (null != entpInfo) {
							entity.setEntp_name(entpInfo.getEntp_name());
						}
					}
					cart_id = getFacade().getCartInfoService().createCartInfo(entity);
				}
			}
		}
		if (cart_id == 0) {
			return super.returnJson(ret, msg, data, response);
		}
		// 加入购物车成功
		ret = 1;
		msg = "加入购物车成功";
		return super.returnJson(ret, msg, data, response);

	}

	public ActionForward delCart(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String cart_id = (String) dynaBean.get("cart_id");
		String isClearAll = (String) dynaBean.get("isClearAll");
		UserInfo ui = super.getUserInfoFromSession(request);
		StringBuffer result = new StringBuffer();
		result.append("{\"result\":");
		if (null == ui) {
			result.append("false").append("}");
			super.renderJson(response, result.toString());
			return null;
		}
		if (StringUtils.isNotBlank(isClearAll)) {
			CartInfo entity = new CartInfo();
			entity.setUser_id(ui.getId());
			getFacade().getCartInfoService().removeCartInfo(entity);
			result.append("true").append("}");
			super.renderJson(response, result.toString());
			return null;
		}
		if (StringUtils.isBlank(cart_id)) {
			result.append("false");
		} else {
			CartInfo entity = new CartInfo();
			entity.setId(Integer.valueOf(cart_id));
			getFacade().getCartInfoService().removeCartInfo(entity);
			result.append("true");
			CartInfo cartInfo2 = new CartInfo();
			cartInfo2.setUser_id(ui.getId());
			List<CartInfo> CartInfoList = super.getFacade().getCartInfoService().getCartInfoList(cartInfo2);
			Integer count = 0;
			BigDecimal pd_price_count = new BigDecimal("0");
			for (CartInfo cartinfo_ : CartInfoList) {
				count += cartinfo_.getPd_count();
				pd_price_count = pd_price_count.add((cartinfo_.getPd_price().multiply(new BigDecimal(cartinfo_
						.getPd_count()))));
			}
			result.append(",\"cartCount\":");
			result.append(count);
			result.append(",\"pd_price_count\":");
			result.append(pd_price_count);
		}
		result.append("}");
		super.renderJson(response, result.toString());
		return null;

	}

	public ActionForward updateCartInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CartInfo entity = new CartInfo();
		super.copyProperties(entity, form);
		UserInfo ui = super.getUserInfoFromSession(request);
		StringBuffer result = new StringBuffer();
		result.append("{\"result\":");
		if (null == ui) {
			result.append("false");
			result.append("}");
			super.renderJson(response, result.toString());
			return null;
		}

		CartInfo cartInfoTemp = new CartInfo();
		cartInfoTemp.setId(entity.getId());
		cartInfoTemp = super.getFacade().getCartInfoService().getCartInfo(cartInfoTemp);
		if (null == cartInfoTemp) {
			result.append("false");
			result.append("}");
			super.renderJson(response, result.toString());
			return null;
		}

		CommInfo commInfo = super.getCommInfo(cartInfoTemp.getComm_id());

		entity.getMap().put("modify_remove_pd_count_le_0", true);
		entity.getMap().put("modify_comm_type_gt_one", true);
		entity.getMap().put("user_id", ui.getId());

		CommTczhPrice cPrice = new CommTczhPrice();
		cPrice.setId(cartInfoTemp.getComm_tczh_id());
		cPrice = getFacade().getCommTczhPriceService().getCommTczhPrice(cPrice);
		if (null == cPrice) {
			result.append("false");
			result.append("}");
			super.renderJson(response, result.toString());
			return null;
		}
		entity.setComm_weight(cPrice.getComm_weight().multiply(new BigDecimal(entity.getPd_count())));
		int rows = getFacade().getCartInfoService().modifyCartInfo(entity);
		if (rows >= 1) {
			result.append("true");
		} else {
			result.append("false");
		}

		entity = super.getFacade().getCartInfoService().getCartInfo(entity);
		if (null != entity) {
			result.append(",");
			result.append("\"add_count_money\":");
			BigDecimal add_count_money = new BigDecimal("0");
			Double pd_price = entity.getPd_price().doubleValue();
			add_count_money = add_count_money.add(new BigDecimal(pd_price * entity.getPd_count()));
			result.append(add_count_money);
		}
		result.append("}");
		super.renderJson(response, result.toString());
		return null;
	}

	public ActionForward saveShippingAddress(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ShippingAddress entity = new ShippingAddress();
		super.copyProperties(entity, form);

		UserInfo ui = super.getUserInfoFromSession(request);
		StringBuffer result = new StringBuffer();
		result.append("{\"result\":");
		Integer id = 0;
		if (null == ui) {
			result.append("false");
			result.append("}");
			super.renderJson(response, result.toString());
			return null;
		}

		entity.setAdd_user_id(ui.getId());
		entity.setAdd_date(new Date());
		id = getFacade().getShippingAddressService().createShippingAddress(entity);
		if (null != id) {
			result.append(id);
		} else {
			result.append("false");
		}

		result.append("}");
		super.renderJson(response, result.toString());
		return null;

	}

	public ActionForward delShippingAddress(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		UserInfo ui = super.getUserInfoFromSession(request);
		StringBuffer result = new StringBuffer();
		result.append("{\"result\":");
		if (null == ui) {
			result.append("false").append("}");
			super.renderJson(response, result.toString());
			return null;
		}
		if (StringUtils.isBlank(id)) {
			result.append("false");
		} else {
			ShippingAddress entity = new ShippingAddress();
			entity.setId(Integer.valueOf(id));
			entity.setIs_del(1);
			getFacade().getShippingAddressService().modifyShippingAddress(entity);
			result.append("true");
		}
		result.append("}");
		super.renderJson(response, result.toString());
		return null;

	}

	public ActionForward getServerTime(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Calendar cal = Calendar.getInstance();
		String sysDate = DateFormatUtils.format(cal.getTime(), "yyyy,MM,dd,HH,mm,ss");
		logger.info("sysDate:{}", sysDate);
		String[] dates = StringUtils.split(sysDate, ",");
		Integer month = Integer.valueOf(dates[1]) - 1;
		dates[1] = String.valueOf(month);
		logger.info("sysDate_now:{}", StringUtils.join(dates, ","));
		super.renderText(response, StringUtils.join(dates, ","));
		return null;
	}

	/**
	 * @author Liu Jia
	 * @since 2014-07-08
	 * @desc 二级页面ajax动态去类别导航数据的构造方法
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getHeaderNavNew(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StringBuffer b = new StringBuffer();

		String webUrl = "http://".concat(Keys.app_domain);

		List<BaseLink> baseLink20List = super.getBaseLinkList(20, 9, null);
		if ((null != baseLink20List) && (baseLink20List.size() > 0)) {
			int j = 0;
			for (BaseLink bi : baseLink20List) {
				String divClass = "";
				if (j == 0) {
					divClass = "nav-level1--first";
				}

				b.append("<div class=\"J-nav-item\">");
				b.append("<div class=\"cate-nav__item J-cate-nav__item--121 cate-nav__item--121 cate-nav__item--has-l2\">");
				b.append(" <div class=\"nav-level1 " + divClass + "\">");
				b.append("<dl><dt>");
				b.append("<a class=\"nav-level1__label\" href=\"" + bi.getLink_url() + "\" title=\"" + bi.getTitle()
						+ "\" target=\"_blank\">" + bi.getTitle() + "</a></dt>");

				BaseLink blk = new BaseLink();
				blk.setLink_type(Integer.valueOf(30));
				blk.setPre_number(bi.getId());
				blk.setIs_del(0);
				blk.getRow().setCount(2);
				List<BaseLink> baseLinkList = getFacade().getBaseLinkService().getBaseLinkList(blk);
				if ((null != baseLinkList) && (baseLinkList.size() > 0)) {
					for (BaseLink bi2 : baseLinkList) {
						b.append("<dd class=\"nav-level1__item\">");
						b.append("<a href=\"" + bi2.getLink_url() + "\" title=\"" + bi2.getTitle()
								+ "\" target=\"_blank\">" + bi2.getTitle() + "</a></dd>");
					}
				}
				b.append("</dl><i class=\"nav-level2-indication F-glob F-glob-caret-right-thin\"></i></div>");
				b.append("<div class=\"nav-level2 J-nav-level2 nav-waterfall\" style=\"visibility: visible;display:none;top:-"
						+ ((j - 1) * 51 + 50) + "px;\">");

				if (null != bi.getContent()) {
					String[] cls_id = StringUtils.splitByWholeSeparator(bi.getContent(), ",");
					if (null != cls_id && cls_id.length > 0) {
						for (int i = 0; i < cls_id.length; i++) {

							BaseClass baseClass = new BaseClass();
							baseClass.setCls_id(Integer.valueOf(cls_id[i]));
							baseClass.setIs_del(0);
							baseClass = super.getFacade().getBaseClassService().getBaseClass(baseClass);
							if (null != baseClass) {
								b.append("<div class=\"nav-level2-item nav-level2-keywords\">");
								b.append("<div class=\"nav-level2-keywords--title\">");
								b.append("<a href=\"" + webUrl + "/search-" + baseClass.getCls_id()
										+ ".shtml\" title=\"" + baseClass.getCls_name() + "\" target=\"_blank\">"
										+ baseClass.getCls_name() + "</a></div>");
								// 先查询名称
								BaseClass baseClassSon = new BaseClass();
								baseClassSon.setPar_id(Integer.valueOf(cls_id[i]));
								baseClassSon.setIs_del(0);
								List<BaseClass> baseClassSonList = super.getFacade().getBaseClassService()
										.getBaseClassList(baseClassSon);
								if (null != baseClassSonList && baseClassSonList.size() > 0) {
									b.append("<ul class=\"nav-level2-keywords--content\">");
									for (BaseClass temp3 : baseClassSonList) {
										b.append("<li><a class=\"keywords__item\" href=\"" + webUrl + "/search-"
												+ temp3.getPar_id() + "-" + temp3.getCls_id() + ".shtml\" title=\""
												+ temp3.getCls_name() + "\" target=\"_blank\">" + temp3.getCls_name()
												+ "</a></li>");

									}
									b.append("</ul>");
								}
								b.append("</div>");
							}
						}
					}
				}
				b.append("</div>");
				b.append("</div>");
				b.append("</div>");
				j++;
			}

		}

		super.renderText(response, b.toString());
		return null;
	}

	/**
	 * @author Liu Jia
	 * @since 2016-08-22
	 * @desc 二级页面ajax动态去类别导航数据的构造方法
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getHeaderNavNewV2(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StringBuffer b = new StringBuffer();

		String webUrl = "http://".concat(Keys.app_domain);

		List<BaseLink> baseLink20List = (List<BaseLink>) request.getSession().getServletContext()
				.getAttribute(Keys.slideNavList);
		if ((null != baseLink20List) && (baseLink20List.size() > 0)) {
			int j = 1;
			for (BaseLink bi : baseLink20List) {

				b.append("<div class=\"item fore" + j + "\">");
				b.append("<div class=\"item-left\">");
				b.append("<div class=\"cata-nav-name\">");
				b.append("<h3>");
				b.append("<a href=\"" + bi.getLink_url() + "\" title=\"" + bi.getTitle() + "\" target=\"_blank\">"
						+ bi.getTitle() + "</a></h3></div><b>&gt;</b> </div>");
				b.append("<div class=\"cata-nav-layer\" style=\"display:none;\">");
				b.append("<div class=\"cata-nav-left\">");
				b.append("<div id='nav-Parent-div' class=\"subitems\">");

				List<BaseClass> baseClassList = bi.getBaseClassList();
				if ((null != baseClassList) && (baseClassList.size() > 0)) {
					int x = 1;
					for (BaseClass temp : baseClassList) {
						String parUrl = webUrl + "/search-" + bi.getContent() + "-" + temp.getCls_id() + ".shtml";
						b.append("<dl class=\"dl_fore" + x + "\">");
						b.append("<dt>");
						b.append("<a href=\"" + parUrl + "\" title=\"" + temp.getCls_name() + "\" target=\"_blank\">"
								+ temp.getCls_name() + "<i>&gt;</i></a></dt><dd>");

						List<BaseClass> baseClassSonList = (List<BaseClass>) temp.getMap().get("baseClassSonSonList");
						if ((null != baseClassSonList) && (baseClassSonList.size() > 0)) {
							for (BaseClass temp2 : baseClassSonList) {
								String sonUrl = webUrl + "/search-" + bi.getContent() + "-" + temp.getCls_id() + "-"
										+ temp2.getCls_id() + ".shtml";
								b.append("<a href=\"" + sonUrl + "\" target=\"_blank\">" + temp2.getCls_name() + "</a>");
							}
						}
						b.append("</dd></dl>");
						x++;
					}
				}
				b.append(" <div class=\"item-promotions\"></div></div></div></div></div>");
				j++;
			}

		}

		super.renderText(response, b.toString());
		return null;
	}

	public void specialGood(CartInfo cartInfo) {
		/**
		 * 10斤 200元、20斤 360元、100斤 1500元、200斤 2200元、500斤 5000元
		 */
		if (cartInfo.getPd_count() < 10) {
			cartInfo.setPd_count(10);
		}
		if ((cartInfo.getPd_count() >= 10) && (cartInfo.getPd_count() < 20)) {
			cartInfo.setPd_price(BigDecimal.valueOf(20L));
		} else if ((cartInfo.getPd_count() >= 20) && (cartInfo.getPd_count() < 100)) {
			cartInfo.setPd_price(BigDecimal.valueOf(18L));
		} else if ((cartInfo.getPd_count() >= 100) && (cartInfo.getPd_count() < 200)) {
			cartInfo.setPd_price(BigDecimal.valueOf(15L));
		} else if ((cartInfo.getPd_count() >= 200) && (cartInfo.getPd_count() < 500)) {
			cartInfo.setPd_price(BigDecimal.valueOf(11L));
		} else if (cartInfo.getPd_count() >= 500) {
			cartInfo.setPd_price(BigDecimal.valueOf(10L));
		}
	}

	public ActionForward saveDeliveryWay(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String cart_id = (String) dynaBean.get("cart_id");
		String delivery_way = (String) dynaBean.get("delivery_way");

		UserInfo ui = super.getUserInfoFromSession(request);
		CartInfo cartInfo = new CartInfo();
		cartInfo.setId(Integer.valueOf(cart_id));
		cartInfo.setDelivery_way(new Integer(delivery_way));
		super.getFacade().getCartInfoService().modifyCartInfo(cartInfo);
		super.renderText(response, "1");
		return null;
	}

	public ActionForward saveQdyh(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String entp_id = (String) dynaBean.get("entp_id");
		String qdyh_id = (String) dynaBean.get("qdyh_id");
		String discount_tj = (String) dynaBean.get("data_flag_tj");
		String discount_method = (String) dynaBean.get("data_flag_method");
		String discount_type_content = (String) dynaBean.get("discount_type_content");
		String discount_tj_content = (String) dynaBean.get("discount_tj_content");

		UserInfo ui = super.getUserInfoFromSession(request);
		if ((null == ui) || StringUtils.isBlank(entp_id)) {
			return null;
		} else {
			if (StringUtils.isNotBlank(qdyh_id)) {// 如果选择了全店优惠选项
				CartInfo cartInfo = new CartInfo();
				cartInfo.getMap().put("entp_id", entp_id);
				cartInfo.getMap().put("user_id", ui.getId());
				cartInfo.setQdyh_id(Integer.valueOf(qdyh_id));
				cartInfo.setDiscount_tj(Integer.valueOf(discount_tj));
				cartInfo.setDiscount_method(Integer.valueOf(discount_method));
				cartInfo.setDiscount_type_content(discount_type_content);
				cartInfo.setDiscount_tj_content(discount_tj_content);
				cartInfo.setFlag_qdyh(1);

				super.getFacade().getCartInfoService().modifyCartInfo(cartInfo);

				StringBuffer result = new StringBuffer();
				result.append("{\"result\":");
				result.append(1);
				result.append("}");
				super.renderJson(response, result.toString());
				return null;
			} else {// 没选择全店优惠的话
				CartInfo cartInfo = new CartInfo();
				cartInfo.getMap().put("entp_id", entp_id);
				cartInfo.getMap().put("user_id", ui.getId());
				cartInfo.getMap().put("set_qdyh_id_null", true);
				cartInfo.getMap().put("set_discount_tj_null", true);
				cartInfo.getMap().put("set_discount_method_null", true);
				cartInfo.getMap().put("set_discount_type_content_null", true);
				cartInfo.getMap().put("set_discount_tj_content_null", true);
				cartInfo.setFlag_qdyh(2);
				super.getFacade().getCartInfoService().modifyCartInfo(cartInfo);

				StringBuffer result = new StringBuffer();
				result.append("{\"result\":");
				result.append(2);
				result.append("}");
				super.renderJson(response, result.toString());
				return null;
			}
		}
	}

	public ActionForward saveYhq(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String entp_id = (String) dynaBean.get("entp_id");
		String yhq_id = (String) dynaBean.get("yhq_id");
		String yhq_tj = (String) dynaBean.get("data_flag_yhq_tj");
		String yhq_tj_money = (String) dynaBean.get("discount_yhq_tj_content");
		String yhq_money = (String) dynaBean.get("data_flag_yhq_money");

		UserInfo ui = super.getUserInfoFromSession(request);
		if ((null == ui) || StringUtils.isBlank(entp_id)) {
			return null;
		} else {
			if (StringUtils.isNotBlank(yhq_id)) {// 如果选择了优惠券选项
				CartInfo cartInfo = new CartInfo();
				cartInfo.getMap().put("entp_id", entp_id);
				cartInfo.getMap().put("user_id", ui.getId());
				cartInfo.setYhq_id(Integer.valueOf(yhq_id));
				cartInfo.setYhq_tj(Integer.valueOf(yhq_tj));
				cartInfo.setYhq_tj_money(yhq_tj_money);
				cartInfo.setYhq_money(yhq_money);
				cartInfo.setFlag_yhq(1);
				super.getFacade().getCartInfoService().modifyCartInfo(cartInfo);

				StringBuffer result = new StringBuffer();
				result.append("{\"result\":");
				result.append(1);
				result.append("}");
				super.renderJson(response, result.toString());
				return null;
			} else {// 没选择优惠券的话
				CartInfo cartInfo = new CartInfo();
				cartInfo.getMap().put("entp_id", entp_id);
				cartInfo.getMap().put("user_id", ui.getId());
				cartInfo.getMap().put("set_yhq_id_null", true);
				cartInfo.getMap().put("set_yhq_tj_null", true);
				cartInfo.getMap().put("set_yhq_tj_money_null", true);
				cartInfo.getMap().put("set_yhq_money_null", true);
				cartInfo.setFlag_yhq(2);
				super.getFacade().getCartInfoService().modifyCartInfo(cartInfo);

				StringBuffer result = new StringBuffer();
				result.append("{\"result\":");
				result.append(2);
				result.append("}");
				super.renderJson(response, result.toString());
				return null;
			}
		}
	}

	public ActionForward ajaxCalculateFreightRates(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		/**
		 * @desc 估算运费
		 */
		StringBuffer result = new StringBuffer();
		DynaBean dynaBean = (DynaBean) form;
		String num = (String) dynaBean.get("num"); // 购买数量
		String weight = (String) dynaBean.get("comm_weight"); // 重量
		String volume = (String) dynaBean.get("comm_volume"); // 体积
		String fre_id = (String) dynaBean.get("fre_id"); // 运费模板ID
		String p_index = (String) dynaBean.get("p_index");
		String pd_stock = (String) dynaBean.get("pd_stock");

		if (StringUtils.isNotBlank(pd_stock) && Integer.valueOf(pd_stock) > 0) {
			if (StringUtils.isNotBlank(fre_id)) {
				Freight f = super.getFreight(Integer.valueOf(fre_id), Integer.valueOf(p_index), Integer.valueOf(num),
						new BigDecimal(weight), new BigDecimal(volume), null);
				/*
				 * 1快递,2EMS,3平邮
				 */
				if (null != f) {
					result.append("{\"is_send\":\"" + f.getMap().get("is_send") + "\"");
					if ("1".equals(f.getMap().get("delivery_way1"))) {
						result.append(", \"kd\":\"" + f.getMap().get("fre_price1") + "\"");
					}
					if ("1".equals(f.getMap().get("delivery_way2"))) {
						result.append(", \"ems\":\"" + f.getMap().get("fre_price2") + "\"");
					}
					if ("1".equals(f.getMap().get("delivery_way3"))) {
						result.append(", \"py\":\"" + f.getMap().get("fre_price3") + "\"");
					}
					result.append(", \"delivery_way1\":\"" + f.getMap().get("delivery_way1") + "\"");
					result.append(", \"delivery_way2\":\"" + f.getMap().get("delivery_way2") + "\"");
					result.append(", \"delivery_way3\":\"" + f.getMap().get("delivery_way3") + "\"");
					result.append(", \"is_delivery1\":\"" + f.getMap().get("is_delivery1") + "\"");
					result.append(", \"is_delivery2\":\"" + f.getMap().get("is_delivery2") + "\"");
					result.append(", \"is_delivery3\":\"" + f.getMap().get("is_delivery3") + "\"");
					result.append(", \"msg\":\"" + f.getIs_freeshipping() + "\"}");
				}
				super.renderJson(response, result.toString());
				return null;
			} else {
				super.renderJson(response, result.toString());
				return null;
			}
		} else {
			super.renderJson(response, result.toString());
			return null;
		}
	}

	public ActionForward getDeliveryInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception { // 查询物流信息,现在使用的是爱查快递
		DynaBean dynaBean = (DynaBean) form;

		String order_id = (String) dynaBean.get("order_id");
		JSONObject jsonObject = new JSONObject();
		String ret = "0";
		String msg = "";
		if (StringUtils.isBlank(order_id)) {
			msg = "参数有误！";
			jsonObject.put("ret", ret);
			jsonObject.put("msg", msg);
			super.renderJson(response, jsonObject.toString());
			return null;
		}

		WlOrderInfo entity = new WlOrderInfo();
		entity.setOrder_id(Integer.valueOf(order_id));
		entity = super.getFacade().getWlOrderInfoService().getWlOrderInfo(entity);
		if (null == entity) {
			msg = "查询有误！";
			jsonObject.put("ret", ret);
			jsonObject.put("msg", msg);
			super.renderJson(response, jsonObject.toString());
			return null;
		}

		WlCompInfo wlCompInfo = new WlCompInfo();
		wlCompInfo.setId(entity.getWl_comp_id());
		wlCompInfo.setIs_del(0);
		wlCompInfo = super.getFacade().getWlCompInfoService().getWlCompInfo(wlCompInfo);
		if (null == wlCompInfo) {
			msg = "物流公司不存在";
			jsonObject.put("ret", ret);
			jsonObject.put("msg", msg);
			super.renderJson(response, jsonObject.toString());
			return null;
		}
		if (wlCompInfo.getId().intValue() == 1) {
			msg = "自提订单，没有物流信息";
			jsonObject.put("ret", ret);
			jsonObject.put("msg", msg);
			super.renderJson(response, jsonObject.toString());
			return null;
		}

		ret = "1";
		msg = DeliveryUtils.getKuaiDi100InfoForUrl(wlCompInfo.getWl_code(), entity.getWaybill_no());
		// msg = DeliveryUtils.getKuaiDi100Info1(wlCompInfo.getWl_code(),
		// entity.getWaybill_no());
		jsonObject.put("ret", ret);
		jsonObject.put("msg", msg);
		logger.info("================" + jsonObject.toString());
		super.renderJson(response, jsonObject.toString());
		return null;
	}

	public ActionForward getDeliveryInfoByServiceId(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception { // 查询物流信息,现在使用的是爱查快递
		DynaBean dynaBean = (DynaBean) form;

		String service_id = (String) dynaBean.get("service_id");
		JSONObject jsonObject = new JSONObject();
		String ret = "0";
		String msg = "";
		if (StringUtils.isBlank(service_id) || !GenericValidator.isInt(service_id)) {
			msg = "参数有误！";
			jsonObject.put("ret", ret);
			jsonObject.put("msg", msg);
			super.renderJson(response, jsonObject.toString());
			return null;
		}

		ServiceCenterInfo entity = new ServiceCenterInfo();
		entity.setId(Integer.valueOf(service_id));
		entity = super.getFacade().getServiceCenterInfoService().getServiceCenterInfo(entity);
		if (null == entity) {
			msg = "查询有误！";
			jsonObject.put("ret", ret);
			jsonObject.put("msg", msg);
			super.renderJson(response, jsonObject.toString());
			return null;
		}

		WlCompInfo wlCompInfo = new WlCompInfo();
		wlCompInfo.setId(entity.getWl_comp_id());
		wlCompInfo.setIs_del(0);
		wlCompInfo = super.getFacade().getWlCompInfoService().getWlCompInfo(wlCompInfo);
		if (null == wlCompInfo) {
			msg = "物流公司不存在";
			jsonObject.put("ret", ret);
			jsonObject.put("msg", msg);
			super.renderJson(response, jsonObject.toString());
			return null;
		}
		if (wlCompInfo.getId().intValue() == 1) {
			msg = "自提订单，没有物流信息";
			jsonObject.put("ret", ret);
			jsonObject.put("msg", msg);
			super.renderJson(response, jsonObject.toString());
			return null;
		}

		ret = "1";
		msg = DeliveryUtils.getKuaiDi100InfoForUrl(wlCompInfo.getWl_code(), entity.getWl_waybill_no());
		// msg = DeliveryUtils.getKuaiDi100Info1(wlCompInfo.getWl_code(),
		// entity.getWaybill_no());
		jsonObject.put("ret", ret);
		jsonObject.put("msg", msg);
		logger.info("================" + jsonObject.toString());
		super.renderJson(response, jsonObject.toString());
		return null;
	}

	public ActionForward ViewGwc(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfoFromSession(request);

		JSONObject result = new JSONObject();
		JSONArray list = new JSONArray();
		if (null != ui) {
			CartInfo entity = new CartInfo();
			entity.setIs_del(0);
			entity.setUser_id(ui.getId());
			entity.setCart_type(new Integer(10));
			List<CartInfo> entityList = super.getFacade().getCartInfoService().getCartInfoList(entity);

			if ((null != entityList) && (entityList.size() > 0)) {
				int pd_count = 0;// 计算数量总数
				BigDecimal pd_price_count = new BigDecimal("0");// 计算价格总数
				for (CartInfo ci : entityList) {
					CommInfo commInfo = new CommInfo();
					commInfo.setId(ci.getComm_id());
					commInfo.setIs_del(0);
					commInfo = super.getFacade().getCommInfoService().getCommInfo(commInfo);

					JSONObject data = new JSONObject();
					data.put("cart_id", ci.getId());
					data.put("comm_id", ci.getComm_id());
					data.put("comm_name", ci.getComm_name());
					if (null != commInfo) {
						data.put("comm_pic", commInfo.getMain_pic());
					}
					data.put("comm_price", ci.getPd_price());
					data.put("comm_count", ci.getPd_count());

					pd_count += ci.getPd_count();
					pd_price_count = pd_price_count.add((ci.getPd_price().multiply(new BigDecimal(ci.getPd_count()))));
					list.add(data);
				}
				result.put("pd_count", pd_count);
				result.put("pd_price_count", pd_price_count);
			}
		}

		result.put("list", list);
		super.renderJson(response, result.toString());
		return null;
	}

	public ActionForward getCommInfoCoookies(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JSONObject result = new JSONObject();
		JSONArray list = new JSONArray();

		String separatorChar = "卍卐"; // 分隔符
		String cookieName = "browseCommInfos";
		String latestCookieValue = "";
		Cookie[] cookies = request.getCookies();
		if ((null != cookies) && (cookies.length > 0)) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(cookieName)) {
					latestCookieValue = URLDecoder.decode(cookie.getValue(), "UTF-8");
					break;
				}
			}
			if (StringUtils.isNotBlank(latestCookieValue)) {
				String[] latestCookieValues = StringUtils.splitByWholeSeparator(latestCookieValue, "@#");
				if ((null != latestCookieValues) && (latestCookieValues.length > 0)) {

					for (String latestCookieValue2 : latestCookieValues) {
						String[] cookiesList = StringUtils.splitByWholeSeparator(latestCookieValue2, separatorChar);

						if (cookiesList.length == 5) {
							JSONObject data = new JSONObject();
							data.put("comm_id", cookiesList[0]);
							data.put("comm_name", cookiesList[1]);
							data.put("main_pic", cookiesList[2]);
							data.put("comm_price", cookiesList[3]);
							data.put("price_ref", cookiesList[4]);
							logger.info("====================" + cookiesList[4]);
							list.add(data);
						}
					}
				}
			}
		}
		result.put("list", list);
		super.renderJson(response, result.toString());
		return null;
	}

	public ActionForward getGwcCount(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String user_id = (String) dynaBean.get("user_id");

		Integer count = 0;
		if (StringUtils.isNotBlank(user_id)) {
			CartInfo entity = new CartInfo();
			entity.setIs_del(0);
			entity.setUser_id(Integer.valueOf(user_id));
			entity.setCart_type(new Integer(10));
			List<CartInfo> CartInfoList = super.getFacade().getCartInfoService().getCartInfoList(entity);

			for (CartInfo cartinfo_ : CartInfoList) {
				count += cartinfo_.getPd_count();
			}
		}

		StringBuffer result = new StringBuffer();
		result.append("{\"result\":");
		result.append(count);
		result.append("}");
		super.renderJson(response, result.toString());
		return null;

	}

	public ActionForward getSessionForStataicIndex(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		StringBuffer server = new StringBuffer();
		server.append(request.getHeader("host")).append(request.getContextPath());
		String webUrl = "http://".concat(server.toString());

		UserInfo userInfo = super.getUserInfoFromSession(request);
		JSONObject jsonObject = new JSONObject();
		if (null != userInfo) {
			String m_url = "";
			String pulicTitle = "";
			if ((userInfo.getUser_type() == 1)) {
				m_url = webUrl + "/manager/admin/Frames.do";
				pulicTitle = "欢迎您，点击返回系统管理员管理中心";
			} else {
				m_url = webUrl + "/manager/customer/index.shtml";
				pulicTitle = "欢迎您，点击返回注册用户管理中心";
			}

			String forTop1 = "<a class=\"username\" style=\"cursor: pointer;\" title=\"" + userInfo.getReal_name() + ""
					+ pulicTitle + "\" onclick=\"location.href='" + m_url + "'\">" + userInfo.getReal_name() + "</a>";

			forTop1 += "&nbsp;<span class=\"growth-info\"><i class=\"sp-growth-icons level-icon level-icon-"
					+ userInfo.getMap().get("user_level") + "\"></i></span>";
			jsonObject.put("forTop1", forTop1);

			String forTop2 = "<a href=\"" + webUrl
					+ "/login.shtml?method=logout\" title=\"退出\" class=\"user-info__login\">退出</a>";
			jsonObject.put("forTop2", forTop2);

			jsonObject.put("user_id", userInfo.getId());
		}
		super.renderJson(response, jsonObject.toString());
		return null;

	}

	public ActionForward getSessionForStataicMIndex(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String jsoncallback = (String) dynaBean.get("jsoncallback");

		StringBuffer server = new StringBuffer();
		server.append(request.getHeader("host")).append(request.getContextPath());
		String webUrl = "http://".concat(server.toString());

		UserInfo userInfo = super.getUserInfoFromSession(request);
		JSONObject jsonObject = new JSONObject();
		if (null != userInfo) {

			String forTop1 = "<a href=\""
					+ webUrl
					+ "/m/MMyHome.do\" class=\"menu3\" onclick=\"clickResponse(this)\" res=\"1\"><span class=\"menu3-icon\"><span></span></span> <span class=\"cate-name\">我的</span></a>";
			jsonObject.put("forTop1", forTop1);

			String forTop2 = "<a href=\""
					+ webUrl
					+ "/m/MMyHome.do\">"
					+ userInfo.getUser_name()
					+ "</a><span class=\"lg-bar\">|</span><a href=\""
					+ webUrl
					+ "/m/MIndexLogin.do?method=logout\">退出</a> <span class=\"new-fr\"><a onclick=\"toTop();\">回到顶部</a></span>";
			jsonObject.put("forTop2", forTop2);
		}
		String json = jsoncallback + "([" + jsonObject.toString() + "])";
		super.renderJson(response, json.toString());
		return null;

	}

	public ActionForward getCommInfoListForSeach(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String query = (String) dynaBean.get("query");
		String htype = (String) dynaBean.get("htype");
		String pageCount = (String) dynaBean.get("pageCount");

		StringBuffer sb = new StringBuffer();
		int count = 20;

		if (StringUtils.isNotBlank(pageCount)) {
			count = Integer.valueOf(pageCount);
		}

		if (StringUtils.isNotBlank(query)) {

			if (StringUtils.isNotBlank(htype)) {
				if (htype.equals("0")) {
					CommInfo commInfo = new CommInfo();
					commInfo.setIs_del(0);
					commInfo.setAudit_state(1);
					commInfo.setIs_sell(1);
					commInfo.getMap().put("sell_date_limit", true);
					commInfo.getMap().put("keyword", query);
					commInfo.getRow().setCount(count);// 默认取前20调符合匹配数据
					List<CommInfo> commInfoList = getFacade().getCommInfoService().getCommInfoList(commInfo);
					if ((null != commInfoList) && (commInfoList.size() > 0)) {
						sb.append("{");
						sb.append("\"query\":\"").append(query).append("\",");
						sb.append("\"suggestions\":[");

						String[] commIdsArray = new String[commInfoList.size()];
						String[] commNamesArray = new String[commInfoList.size()];
						String[] commCountArray = new String[commInfoList.size()];
						for (int i = 0; i < commInfoList.size(); i++) {
							commNamesArray[i] = "\"" + commInfoList.get(i).getComm_name() + "\"";
							commIdsArray[i] = "\"" + commInfoList.get(i).getId() + "\"";
							CommInfo commInfo2 = new CommInfo();
							commInfo2.setIs_del(0);
							commInfo2.setAudit_state(1);
							commInfo2.setIs_sell(1);
							commInfo2.getMap().put("sell_date_limit", true);
							commInfo2.setId(commInfoList.get(i).getId());
							commInfo2.getMap().put("keyword", query);
							Integer commCount = super.getFacade().getCommInfoService().getCommInfoCount(commInfo2);
							commCountArray[i] = commCount.toString();
						}

						sb.append(StringUtils.join(commNamesArray, ","));
						sb.append("],");
						sb.append("\"data\":[").append(StringUtils.join(commIdsArray, ",")).append("],");
						sb.append("\"commCount\":[").append(StringUtils.join(commCountArray, ",")).append("]");
						sb.append("}");

					}
				} else {
					EntpInfo entpInfo = new EntpInfo();
					entpInfo.setIs_del(0);
					entpInfo.setAudit_state(1);
					entpInfo.getMap().put("entp_name_like", query);
					entpInfo.getRow().setCount(count);// 默认取前20调符合匹配数据
					List<EntpInfo> entpInfoList = getFacade().getEntpInfoService().getEntpInfoList(entpInfo);
					if ((null != entpInfoList) && (entpInfoList.size() > 0)) {
						sb.append("{");
						sb.append("\"query\":\"").append(query).append("\",");
						sb.append("\"suggestions\":[");

						String[] entpIdsArray = new String[entpInfoList.size()];
						String[] entpNamesArray = new String[entpInfoList.size()];
						String[] entpCountArray = new String[entpInfoList.size()];
						for (int i = 0; i < entpInfoList.size(); i++) {
							entpNamesArray[i] = "\"" + entpInfoList.get(i).getEntp_name() + "\"";
							entpIdsArray[i] = "\"" + entpInfoList.get(i).getId() + "\"";
							EntpInfo entpInfo2 = new EntpInfo();
							entpInfo2.setIs_del(0);
							entpInfo2.setAudit_state(1);
							entpInfo2.setId(entpInfoList.get(i).getId());
							entpInfo2.getMap().put("keyword", query);
							Integer entpCount = super.getFacade().getEntpInfoService().getEntpInfoCount(entpInfo2);
							entpCountArray[i] = entpCount.toString();
						}

						sb.append(StringUtils.join(entpNamesArray, ","));
						sb.append("],");
						sb.append("\"data\":[").append(StringUtils.join(entpIdsArray, ",")).append("],");
						sb.append("\"commCount\":[").append(StringUtils.join(entpCountArray, ",")).append("]");
						sb.append("}");

					}
				}
			}
		}
		logger.info(sb.toString());
		super.renderJson(response, sb.toString());

		return null;
	}

	public ActionForward saveActivityPrice(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String cart_id = (String) dynaBean.get("cart_id");
		String activity_price_id = (String) dynaBean.get("activity_price_id");

		UserInfo ui = super.getUserInfoFromSession(request);
		CartInfo cartInfo = new CartInfo();
		cartInfo.setId(Integer.valueOf(cart_id));
		cartInfo.setActivity_price_id(new Integer(activity_price_id));
		super.getFacade().getCartInfoService().modifyCartInfo(cartInfo);
		super.renderText(response, "1");
		return null;
	}

	public ActionForward getPIndexFromIp(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String url = "http://ip.taobao.com/service/getIpInfo.php?ip=" + super.getIpAddr(request);
		logger.warn("=========getPIndexFromIp========" + url);

		String result = GetApiUtils.getApiWithUrl(url);

		JSONObject jsonObject = new JSONObject();
		JSONObject json = (JSONObject) JSONObject.parse(result.toString());
		String code = json.get("code").toString();
		if (code.equals("0")) {// 0代表成功
			JSONObject json_data = json.getJSONObject("data");
			String p_name = (String) json_data.get("city");
			String region = (String) json_data.get("region");
			BaseProvince bp = new BaseProvince();
			bp.setIs_del(0);
			if (StringUtils.isNotBlank(p_name)) {
				bp.setP_name(p_name);
			} else {
				bp.setP_name(Keys.P_INDEX_CITY_NAME);
				region = Keys.P_INDEX_CITY_NAME;
			}
			bp = getFacade().getBaseProvinceService().getBaseProvince(bp);
			if (null != bp) {
				jsonObject.put("p_name", region);
				jsonObject.put("full_name", bp.getFull_name());
				jsonObject.put("p_index", bp.getP_index());
				super.renderJson(response, jsonObject.toString());
				return null;
			} else {
				BaseProvince bpPar = new BaseProvince();
				bpPar.setIs_del(0);
				bpPar.setP_index(Long.valueOf(Keys.P_INDEX_CITY));
				bpPar = getFacade().getBaseProvinceService().getBaseProvince(bpPar);

				jsonObject.put("p_name", bpPar.getP_name());
				jsonObject.put("full_name", bpPar.getFull_name());
				jsonObject.put("p_index", Keys.P_INDEX_CITY);
				super.renderJson(response, jsonObject.toString());
				return null;
			}
		} else {
			BaseProvince bp = new BaseProvince();
			bp.setIs_del(0);
			bp.setP_name(Keys.P_INDEX_CITY_NAME);
			String region = Keys.P_INDEX_CITY_NAME;
			bp = getFacade().getBaseProvinceService().getBaseProvince(bp);
			if (null != bp) {
				jsonObject.put("p_name", region);
				jsonObject.put("full_name", bp.getFull_name());
				jsonObject.put("p_index", bp.getP_index());
				super.renderJson(response, jsonObject.toString());
				return null;
			} else {
				return null;
			}
		}
	}

	public ActionForward deleteFileForCosSwfupload(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String file_path = (String) dynaBean.get("file_path");
		String entity_id = (String) dynaBean.get("entity_id");

		if (StringUtils.isBlank(file_path)) {
			super.renderText(response, "fail");
			return null;
		}
		logger.info("file_path:{}", file_path);
		logger.info("entity_id:{}", entity_id);

		String ctx = request.getSession().getServletContext().getRealPath(String.valueOf(File.separatorChar));
		String realFilePath = ctx + file_path;
		// 先删除原图
		FileTools.deleteFile(realFilePath);

		// 删除图片不同大小的
		if (Keys.NEWS_INFO_IMAGE_SIZE.length > 0) {
			for (int image_size : Keys.NEWS_INFO_IMAGE_SIZE) {
				String _realFilePath = "";
				if (realFilePath.endsWith(".jpg")) {
					_realFilePath = realFilePath.replaceAll(".jpg", "_" + image_size + ".jpg");
				} else if (realFilePath.endsWith(".jpeg")) {
					_realFilePath = realFilePath.replaceAll(".jpeg", "_" + image_size + ".jpeg");
				} else if (realFilePath.endsWith(".gif")) {
					_realFilePath = realFilePath.replaceAll(".gif", "_" + image_size + ".gif");
				} else if (realFilePath.endsWith(".png")) {
					_realFilePath = realFilePath.replaceAll(".png", "_" + image_size + ".png");
				} else if (realFilePath.endsWith(".bmp")) {
					_realFilePath = realFilePath.replaceAll(".bmp", "_" + image_size + ".bmp");
				}
				FileTools.deleteFile(_realFilePath);
			}
		}

		if (StringUtils.isNotBlank(entity_id)) {// 删除数据库里面的信息
			PdImgs pdImgs = new PdImgs();
			pdImgs.setPd_id(Integer.valueOf(entity_id));
			pdImgs.setFile_path(file_path);
			pdImgs.getMap().put("file_path", file_path);
			super.getFacade().getPdImgsService().removePdImgs(pdImgs);
		}

		super.renderText(response, "success");
		return null;
	}

	public ActionForward dealUserScoreRecord(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 从baseDate里面取出数据6620签到
		String flag = "0";
		if (super.checkIsRegistion(request)) {
			flag = "2";
			super.renderText(response, flag);
			return null;
		}
		BaseData baseData = new BaseData();
		baseData.setIs_del(0);
		baseData.setType(6620);
		baseData = super.getFacade().getBaseDataService().getBaseData(baseData);
		if (null == baseData) {
			super.renderText(response, flag);
			return null;
		}
		UserInfo ui = super.getUserInfoFromSession(request);

		UserInfo ui2 = new UserInfo();
		ui2.setId(ui.getId());
		ui2.setIs_del(0);
		ui2 = super.getFacade().getUserInfoService().getUserInfo(ui2);

		UserScoreRecord usr_entity = new UserScoreRecord();
		usr_entity.setLink_id(ui.getId());
		usr_entity.setAdd_user_id(ui.getId());
		usr_entity.setAdd_date(new Date());
		usr_entity.setIs_del(0);
		usr_entity.setScore_type(4);
		usr_entity.setHd_score(new BigDecimal(baseData.getPre_number()));
		usr_entity.getMap().put("uiForUserScore", ui2);
		Integer insert_id = super.getFacade().getUserScoreRecordService().createUserScoreRecord(usr_entity);

		if (null != insert_id) {
			flag = "1";
		}
		super.renderText(response, flag);
		return null;
	}

	public ActionForward getCommInfoListForMobileSeach(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String keyword = (String) dynaBean.get("keyword");
		String htype = (String) dynaBean.get("htype");
		String jsoncallback = (String) dynaBean.get("jsoncallback");

		JSONObject datas = new JSONObject();
		JSONArray dataLoadList = new JSONArray();

		int count = 6;

		logger.info("=======keyword=========" + keyword);
		logger.info("=======htype=========" + htype);

		if (StringUtils.isNotBlank(keyword)) {

			if (StringUtils.isNotBlank(htype)) {
				if (htype.equals("0")) {
					CommInfo commInfo = new CommInfo();
					commInfo.setIs_del(0);
					commInfo.setAudit_state(1);
					commInfo.setIs_sell(1);
					commInfo.getMap().put("sell_date_limit", true);
					commInfo.getMap().put("keyword", keyword);
					commInfo.getRow().setCount(count);// 默认取前6调符合匹配数据
					List<CommInfo> commInfoList = getFacade().getCommInfoService().getCommInfoList(commInfo);
					if ((null != commInfoList) && (commInfoList.size() > 0)) {

						for (CommInfo ci : commInfoList) {
							JSONObject map = new JSONObject();
							map.put("comm_id", ci.getId());
							map.put("comm_name", ci.getComm_name());
							dataLoadList.add(map);
						}
						datas.put("dataLoadList", dataLoadList);
						datas.put("ret", "1");
					} else {
						datas.put("ret", "0");
					}
				}
			}
		}
		String json = jsoncallback + "([" + datas.toString() + "])";
		logger.info("===datas:{}", json.toString());
		super.renderJson(response, json.toString());

		return null;
	}

	public ActionForward getCommentInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String comm_id = (String) dynaBean.get("comm_id");
		String startRow = (String) dynaBean.get("startRow");

		CommentInfo entity = new CommentInfo();
		super.copyProperties(entity, dynaBean);
		entity.setLink_id(Integer.valueOf(comm_id));
		entity.setComm_state(1);

		JSONObject jsonObject = new JSONObject();

		Integer recordCount = getFacade().getCommentInfoService().getCommentInfoCount(entity);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		entity.getRow().setCount(15);

		entity.getRow().setFirst(pager.getFirstRow());
		if (StringUtils.isNotBlank(startRow)) {
			entity.getRow().setFirst(Integer.valueOf(startRow));
		}

		List<CommentInfo> commentInfoList = getFacade().getCommentInfoService().getCommentInfoPaginatedList(entity);
		if ((null != commentInfoList) && (commentInfoList.size() > 0)) {
			JSONArray jsonArray = new JSONArray();
			// OrderInfoDetails orderInfoDetails = null;
			CommentInfoSon son = null;
			for (CommentInfo ci : commentInfoList) {
				JSONObject jsonObject2 = new JSONObject();
				String secretString = this.setSecretUserName(ci.getComm_uname());
				jsonObject2.put("comm_experience", ci.getComm_experience());
				jsonObject2.put("comm_score", ci.getComm_score());
				jsonObject2.put("comm_time", sdFormat_ymdhms.format(ci.getComm_time()));
				jsonObject2.put("secretString", secretString);
				jsonObject2.put("reply_content", ci.getReply_content());
				jsonObject2.put("comm_name", ci.getComm_name());
				jsonObject2.put("comm_tczh_name", ci.getComm_tczh_name());

				// if (null != ci.getOrder_details_id()) {
				// orderInfoDetails = new OrderInfoDetails();
				// orderInfoDetails.setId(ci.getOrder_details_id());
				// orderInfoDetails =
				// getFacade().getOrderInfoDetailsService().getOrderInfoDetails(orderInfoDetails);
				// if (null != orderInfoDetails) {
				//
				// }
				// }

				if (1 == ci.getHas_pic()) {
					// 获取评论图片
					BaseFiles baseFiles = new BaseFiles();
					baseFiles.setLink_id(ci.getId());
					baseFiles.setLink_tab("CommentInfo");
					baseFiles.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
					List<BaseFiles> baseFilesList = super.getFacade().getBaseFilesService().getBaseFilesList(baseFiles);
					jsonObject2.put("baseFilesList", baseFilesList);
				}
				son = new CommentInfoSon();
				son.setPar_id(ci.getId());
				son.setIs_del(0);
				List<CommentInfoSon> commentInfoSonList = getFacade().getCommentInfoSonService().getCommentInfoSonList(
						son);
				if (null != commentInfoSonList && commentInfoSonList.size() > 0) {
					jsonObject2.put("commentInfoSonList", commentInfoSonList);
				}

				jsonArray.add(jsonObject2);
			}
			jsonObject.put("list", jsonArray);
		}

		super.renderJson(response, jsonObject.toString());
		return null;
	}

	public ActionForward getBaseClass(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String entp_id = (String) dynaBean.get("entp_id");

		BaseClass baseClass = new BaseClass();
		baseClass.setIs_del(0);
		baseClass.setCls_scope(1);
		baseClass.getMap().put("form_indexEntpInfo_par_cls_id", true);
		baseClass.getMap().put("own_entp_id", entp_id);
		List<BaseClass> baseClassParList = super.getFacade().getBaseClassService().getBaseClassList(baseClass);
		JSONObject jsonObject = new JSONObject();
		if ((null != baseClassParList) && (baseClassParList.size() > 0)) {
			JSONArray jsonArray = new JSONArray();
			for (BaseClass bpz : baseClassParList) {
				JSONObject jsonObject2 = new JSONObject();
				jsonObject2.put("cls_name", bpz.getCls_name());
				jsonObject2.put("cls_id", bpz.getCls_id());
				jsonArray.add(jsonObject2);
				BaseClass bpc = new BaseClass();
				bpc.setIs_del(0);
				bpc.setCls_scope(1);
				bpc.setPar_id(bpz.getCls_id());
				bpc.getMap().put("own_entp_id", entp_id);
				bpc.getMap().put("form_indexEntpInfo_cls_id", true);
				List<BaseClass> sonList = super.getFacade().getBaseClassService().getBaseClassList(bpc);
				jsonObject2.put("sonList", sonList);
			}
			jsonObject.put("list", jsonArray);
		}

		super.renderJson(response, jsonObject.toString());
		return null;
	}

	/**
	 * @author Liu,Jia
	 * @version 2014-07-24
	 * @desc 购买匿名
	 */
	public String setSecretUserName(String user_name) {
		if (StringUtils.isNotBlank(user_name)) {
			String secretString = "";
			int user_name_length = user_name.length();
			if (user_name_length == 1) {// 长度为1 就默认显示
				secretString = user_name;
			} else if (user_name_length == 2) {// 长度为2 替换最后一个
				// 截取第一位
				String user_name_first = user_name.substring(0, 1);
				// 截取最后一位
				String user_name_last = user_name.substring((user_name_length - 1));
				// 然后中间的都替换成*
				user_name_last = user_name_last.replaceAll(user_name_last, "*");
				secretString = user_name_first + user_name_last;

			} else {// 长度大于2 就替换中间的都为*
				// 截取第一位
				String user_name_first = user_name.substring(0, 1);
				// 截取最后一位
				String user_name_last = user_name.substring((user_name_length - 1));

				// 先截取2-倒数第二位
				String user_name_2_2 = user_name.substring(1, (user_name_length - 2));
				// 然后中间的都替换成*
				user_name_2_2 = StringUtils.leftPad(user_name_2_2.replaceAll(user_name_2_2, "*"),
						user_name_2_2.length() + 1, "*");

				secretString = user_name_first + user_name_2_2 + user_name_last;
			}
			return secretString;
		} else {
			return null;
		}
	}

	/**
	 * @desc 跳转到地图页面
	 */
	public ActionForward getBMap(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String vieType = (String) dynaBean.get("vieType");

		if (StringUtils.isNotBlank(vieType) && vieType.equals("m")) {
			return new ActionForward("/_public_bmap_m.jsp");
		}

		return new ActionForward("/_public_bmap.jsp");
	}

	/**
	 * @desc 跳转到高德地图页面
	 */
	public ActionForward getBMapNew(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ActionForward("/_public_map_new.jsp");
	}

	/**
	 * @desc 跳转到查看地图页面
	 */
	public ActionForward viewBMap(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ActionForward("/_public_view_bmap.jsp");
	}

	public ActionForward jugdeHasSeviceCenterByPindex(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String p_index = (String) dynaBean.get("p_index");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("ret", false);

		if (StringUtils.isNotBlank(p_index)) {
			ServiceCenterInfo entity = new ServiceCenterInfo();
			entity.setP_index(Integer.valueOf(p_index));
			entity.setIs_del(0);
			entity.setAudit_state(1);
			entity.setEffect_state(1);
			entity = super.getFacade().getServiceCenterInfoService().getServiceCenterInfo(entity);
			if (null != entity) {
				jsonObject.put("ret", true);
				jsonObject.put("s_name", entity.getServicecenter_name());
				jsonObject.put("s_mobile", entity.getServicecenter_linkman_tel());
			}
		}
		logger.info("==jsonObject:{}", jsonObject.toJSONString());
		super.renderJson(response, jsonObject.toString());

		return null;
	}

	public ActionForward getCartInfoState(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String cart_type = (String) dynaBean.get("cart_type");

		JSONObject datas = new JSONObject();
		String ret = "0";
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			ret = "1";
			datas.put("ret", ret);
			super.renderJson(response, datas.toString());
			return null;
		}
		if (StringUtils.isBlank(cart_type)) {
			cart_type = String.valueOf(Keys.CartType.CART_TYPE_10.getIndex());
		}

		CartInfo cartInfo = new CartInfo();
		cartInfo.setIs_del(0);
		cartInfo.setUser_id(ui.getId());
		cartInfo.setCart_type(Integer.valueOf(cart_type));

		Integer count = getFacade().getCartInfoService().getCartInfoCount(cartInfo);
		if (count <= 0) {
			ret = "1";
		}
		datas.put("ret", ret);
		super.renderJson(response, datas.toString());
		return null;
	}

	public ActionForward getClsId(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			return null;
		}
		DynaBean dynaBean = (DynaBean) form;
		String cls_scope = (String) dynaBean.get("cls_scope");
		String noSelectFar = (String) dynaBean.get("noSelectFar");

		if (StringUtils.isBlank(cls_scope)) {// 根目录par_id为-1
			cls_scope = "1";
		}
		String clazzTree;
		if (StringUtils.isNotBlank(noSelectFar)) {
			clazzTree = StringHelper
					.getTreeNodesFromBaseClassList(getFacade(), Integer.valueOf(cls_scope), false, true);
		} else {
			clazzTree = StringHelper.getTreeNodesFromBaseClassList(getFacade(), Integer.valueOf(cls_scope), true);
		}
		request.setAttribute("clazzTree", clazzTree);
		return new ActionForward("/index/CsAjax/choose_cls.jsp");
	}

	public ActionForward ajaxCalculateFreightRatesNew(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		JSONObject result = new JSONObject();
		DynaBean dynaBean = (DynaBean) form;
		String pd_count = (String) dynaBean.get("pd_count");
		String comm_id = (String) dynaBean.get("comm_id");
		String p_index = (String) dynaBean.get("p_index");

		String ret = "0";
		if (StringUtils.isBlank(comm_id)) {
			result.put("ret", ret);
			result.put("msg", "comm_id参数不正确");
			super.renderJson(response, result.toString());
			return null;
		}
		if (StringUtils.isBlank(p_index)) {
			result.put("ret", ret);
			result.put("msg", "p_index参数不正确");
			super.renderJson(response, result.toString());
			return null;
		}
		if (StringUtils.isBlank(pd_count)) {
			result.put("ret", ret);
			result.put("msg", "pd_count参数不正确");
			super.renderJson(response, result.toString());
			return null;
		}

		CommInfo entity = new CommInfo();
		entity.setId(Integer.valueOf(comm_id));
		entity = super.getFacade().getCommInfoService().getCommInfo(entity);
		if (null == entity || null == entity.getFreight_id()) {
			result.put("ret", ret);
			super.renderJson(response, result.toString());
			return null;
		}

		Freight f = super.getFreight(entity.getFreight_id(), Integer.valueOf(p_index), Integer.valueOf(pd_count),
				entity.getComm_weight(), entity.getComm_volume(), null);
		if (null != f) {
			result.put("is_send", f.getMap().get("is_send"));
			if ("1".equals(f.getMap().get("delivery_way1"))) {
				result.put("kd", f.getMap().get("fre_price1"));
			}
			result.put("delivery_way1", f.getMap().get("delivery_way1"));
			result.put("is_delivery1", f.getMap().get("is_delivery1"));
			result.put("msg", f.getIs_freeshipping());
			ret = "1";
		}
		result.put("ret", ret);
		result.put("msg", "运费查询成功");

		super.renderJson(response, result.toString());
		return null;
	}

	public ActionForward setSessionForFuxiao(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		JSONObject datas = new JSONObject();

		UserInfo userInfo = super.getUserInfoFromSession(request);

		if (null != userInfo) {
			if (StringUtils.isNotBlank(id)) {
				HttpSession session = request.getSession();
				session.setAttribute(userInfo.getId() + "_fuxiao_single", id);
			} else {
				HttpSession session = request.getSession();
				session.removeAttribute(userInfo.getId() + "_fuxiao_single");
			}
		}
		super.renderJson(response, datas.toString());
		return null;
	}

	public ActionForward jugdeUserIsLoginDanger(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JSONObject datas = new JSONObject();

		String ret = "0";
		String msg = "";
		UserInfo userInfo = super.getUserInfoFromSession(request);

		if (null != userInfo) {
			// 异地登录提醒
			Cookie login_danger_hide = WebUtils.getCookie(request, "login_danger_hide");
			if (null == login_danger_hide) {
				SysOperLog sol = new SysOperLog();
				sol.setOper_type(Keys.SysOperType.SysOperType_10.getIndex());
				sol.setOper_uid(userInfo.getId());
				sol.getRow().setCount(2);
				List<SysOperLog> solList = getFacade().getSysOperLogService().getSysOperLogList(sol);
				if (null != solList && solList.size() == 2) {
					String ip1 = "", ip2 = "";
					Date ip2_date = new Date();
					int i = 0;
					for (SysOperLog sol1 : solList) {
						if (i == 0) {
							ip1 = sol1.getOper_uip();
						}
						if (i == 1) {
							ip2 = sol1.getOper_uip();
							ip2_date = sol1.getOper_time();
						}
						i++;
					}
					String ip_real1 = ip1;
					if (StringUtils.contains(ip2, ",")) {
						String[] ip_reals = StringUtils.split(ip_real1, ",");
						if (ArrayUtils.isNotEmpty(ip_reals)) {
							ip_real1 = ip_reals[0];
						}
					}
					String ip_real2 = ip2;
					if (StringUtils.contains(ip2, ",")) {
						String[] ip_reals = StringUtils.split(ip_real2, ",");
						if (ArrayUtils.isNotEmpty(ip_reals)) {
							ip_real2 = ip_reals[0];
						}
					}
					if (!StringUtils.equalsIgnoreCase(ip_real1, ip_real2)) {

						request.setAttribute("login_danger", "true");

						String p_name = super.getPnamePindexFromIp(request, ip_real2);

						msg = "尊敬的用户"
								+ userInfo.getReal_name()
								+ "您好！您的账号存在异常登录行为，"
								+ "异地登录地址：<a title='点击查看异地登录地点' class='tip-danger quicktip' style='text-decoration: underline;' href='http://ip.taobao.com/ipSearch.php?ipAddr="
								+ ip_real2 + "' target='_blank'><b>" + p_name + "</b></a>，登录时间：<b class='tip-danger'>"
								+ sdFormat_ymdhms_china.format(ip2_date) + "</b>。请您及时修改帐户密码如有疑问请查看" + Keys.app_name
								+ "帮助中心或拨打" + Keys.app_tel;
						ret = "1";
					}
				}
			}
		}

		datas.put("ret", ret);
		datas.put("msg", msg);
		super.renderJson(response, datas.toString());
		return null;
	}

	public ActionForward checkDelCommSonAttr(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		JSONObject datas = new JSONObject();
		String code = "0", msg = "";
		if (StringUtils.isBlank(id)) {
			msg = "参数有误！";
			super.ajaxReturnInfo(response, code, msg, datas);
			return null;
		}
		BaseAttributeSon entity = new BaseAttributeSon();
		entity.setId(Integer.valueOf(id));
		entity = super.getFacade().getBaseAttributeSonService().getBaseAttributeSon(entity);
		if (null == entity) {
			msg = "未查询到该条记录！";
			super.ajaxReturnInfo(response, code, msg, datas);
			return null;
		}
		BaseAttributeSon entityUpdate = new BaseAttributeSon();
		entityUpdate.setId(Integer.valueOf(id));
		int count = super.getFacade().getBaseAttributeSonService().modifyBaseAttributeSon(entityUpdate);
		if (count > 0) {
			code = "1";
			msg = "操作成功！";
			super.ajaxReturnInfo(response, code, msg, datas);
			return null;
		} else {
			msg = "操作失败！";
			super.ajaxReturnInfo(response, code, msg, datas);
			return null;
		}
	}

	public ActionForward checkAttrNameIsExist(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JSONObject datas = new JSONObject();
		String code = "0", msg = "";

		DynaBean dynaBean = (DynaBean) form;
		String attr_name = (String) dynaBean.get("attr_name");
		String cls_id = (String) dynaBean.get("cls_id");
		String own_entp_id = (String) dynaBean.get("own_entp_id");

		if (StringUtils.isBlank(attr_name) || StringUtils.isBlank(cls_id) || StringUtils.isBlank(own_entp_id)) {
			msg = "参数不能为空";
			super.ajaxReturnInfo(response, code, msg, datas);
			return null;
		}

		BaseAttribute entity = new BaseAttribute();
		entity.setAttr_name(attr_name);
		entity.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		entity.setAttr_scope(0);
		entity.setCls_id(Integer.valueOf(cls_id));
		entity.setOwn_entp_id(Integer.valueOf(own_entp_id));
		int count = super.getFacade().getBaseAttributeService().getBaseAttributeCount(entity);
		if (count > 0) {
			code = "1";
			msg = "基础库已存在该规格,确定前往选择?";
		} else {
			code = "2";
		}
		super.ajaxReturnInfo(response, code, msg, datas);
		return null;
	}

	// 查询根据返现规则以及相关信息查询出对应的钱展示出来
	public ActionForward queryLinkInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JSONObject datas = new JSONObject();
		String code = "0", msg = "";

		DynaBean dynaBean = (DynaBean) form;
		String fanxian_rule = (String) dynaBean.get("fanxian_rule");
		String get_money = (String) dynaBean.get("get_money");
		if (!GenericValidator.isLong(fanxian_rule)) {
			msg = "fanxian_rule参数不正确";
			super.ajaxReturnInfo(response, code, msg, datas);
			return null;
		}
		if (StringUtils.isBlank(get_money)) {
			msg = "get_money参数不正确";
			super.ajaxReturnInfo(response, code, msg, datas);
			return null;
		}

		BaseData baseData = super.getBaseData(Integer.valueOf(fanxian_rule));
		if (null == baseData) {
			msg = "该条返现规则不存在！";
			super.ajaxReturnInfo(response, code, msg, datas);
			return null;
		}
		UserInfo ui = super.getUserInfoFromSession(request);
		UserInfo userInfo = super.getUserInfo(ui.getId());
		if (null == userInfo || null == userInfo.getOwn_entp_id()) {
			msg = "用户不存在，或者不是企业用户！";
			super.ajaxReturnInfo(response, code, msg, datas);
			return null;
		}

		code = "1";
		// 计算相应数据
		BigDecimal fanxian_money = new BigDecimal((Double.valueOf(baseData.getPre_number2()) / Double.valueOf(baseData
				.getPre_number())) * new BigDecimal(get_money).doubleValue());

		BigDecimal entp_service_money = fanxian_money.multiply((Keys.rmb_to_fanxianbi_rate)).setScale(8,
				BigDecimal.ROUND_HALF_UP);

		datas.put("fanxian_money", fanxian_money);
		datas.put("entp_service_money", entp_service_money);
		String is_engouh = "1";
		// 证明商家金额不够

		// 通过get_money 计算

		if (entp_service_money.compareTo(userInfo.getLeiji_money_entp()) > 0) {
			is_engouh = "0";
			datas.put("need_chongzhi_money", entp_service_money.subtract(userInfo.getLeiji_money_entp()));
		}
		datas.put("is_engouh", is_engouh);
		super.ajaxReturnInfo(response, code, msg, datas);
		return null;
	}

	// 查询用户是否存在
	public ActionForward queryUserHasExist(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JSONObject datas = new JSONObject();
		String code = "0", msg = "";

		DynaBean dynaBean = (DynaBean) form;
		String user_name = (String) dynaBean.get("user_name");
		if (StringUtils.isBlank(user_name)) {
			msg = "user_name参数不正确";
			super.ajaxReturnInfo(response, code, msg, datas);
			return null;
		}

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			msg = "你暂未登录！";
			super.ajaxReturnInfo(response, code, msg, datas);
			return null;
		}

		UserInfo entity = new UserInfo();
		entity.getMap().put("ym_id", user_name);
		entity.getMap().put("not_in_id", ui.getId());
		entity.setIs_del(0);
		int recordCount = super.getFacade().getUserInfoService().getUserInfoCount(entity);
		if (recordCount == 1) {
			entity = getFacade().getUserInfoService().getUserInfo(entity);
			msg = "姓名：" + entity.getReal_name();
			code = "1";
		} else {
			msg = "未查询到此人并且不能是本人，请确认输入的是否正确！";
		}

		super.ajaxReturnInfo(response, code, msg, datas);
		return null;
	}

	public ActionForward updateAttrName(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JSONObject jsonObject = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String attr_name = (String) dynaBean.get("attr_name");
		String ret = "0";
		String msg = "";
		if (StringUtils.isBlank(id) || StringUtils.isBlank(attr_name)) {
			msg = "参数有误！";
			jsonObject.put("ret", ret);
			jsonObject.put("msg", msg);
			super.renderJson(response, jsonObject.toString());
			return null;
		}

		BaseAttributeSon entity = new BaseAttributeSon();
		entity.setId(Integer.valueOf(id));
		entity = super.getFacade().getBaseAttributeSonService().getBaseAttributeSon(entity);
		if (null == entity) {
			msg = "未查询该数据！";
			jsonObject.put("ret", ret);
			jsonObject.put("msg", msg);
			super.renderJson(response, jsonObject.toString());
			return null;
		}

		BaseAttributeSon entityUpdate = new BaseAttributeSon();
		entityUpdate.setId(Integer.valueOf(id));
		entityUpdate.setAttr_name(attr_name);
		entityUpdate.setAttr_show_name(attr_name);
		int count = super.getFacade().getBaseAttributeSonService().modifyBaseAttributeSon(entityUpdate);

		if (count > 0) {
			ret = "1";
			msg = "修改成功！";
		} else {
			msg = "修改失败！";
		}
		jsonObject.put("ret", ret);
		jsonObject.put("msg", msg);
		super.renderJson(response, jsonObject.toString());

		return null;
	}

	public ActionForward getFontIcon(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			return null;
		}
		return new ActionForward("/index/CsAjax/choose_font_icon.jsp");
	}

	public ActionForward updateToCityManager(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String msg = "升级失败!";
		DynaBean dynaBean = (DynaBean) form;
		String user_id = (String) dynaBean.get("user_id");
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null != ui && StringUtils.isNotBlank(user_id) && GenericValidator.isInt(user_id)) {
			UserInfo userInfoQuery = super.getUserInfo(Integer.valueOf(user_id));
			if (null != userInfoQuery) {
				UserInfo userInfoUpdate = new UserInfo();
				userInfoUpdate.setId(userInfoQuery.getId());
				userInfoUpdate.setIs_city_manager(1);
				int count = super.getFacade().getUserInfoService().modifyUserInfo(userInfoUpdate);
				if (count > 0) {
					msg = "升级成功!";
				}
			}
		}

		super.renderText(response, msg);
		return null;
	}

	// 百度坐标to腾讯坐标
	public ActionForward baiduToTengXun(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JSONObject jsonObject = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String locations = (String) dynaBean.get("locations");
		String key = (String) dynaBean.get("key");
		String ret = "0";
		String msg = "";
		if (StringUtils.isBlank(locations) || StringUtils.isBlank(key)) {
			msg = "参数有误！";
			jsonObject.put("ret", ret);
			jsonObject.put("msg", msg);
			super.renderJson(response, jsonObject.toString());
			return null;
		}

		String url = "http://apis.map.qq.com/ws/coord/v1/translate?locations=" + locations + "&type=3&key=" + key
				+ "&output=json";

		String result = GetApiUtils.getApiWithUrl(url);

		JSONObject json = (JSONObject) JSONObject.parse(result.toString());
		String status = json.get("status").toString();
		if (status.equals("0")) {// 0代表成功
			JSONArray jsonDataArray = json.getJSONArray("locations");
			JSONObject jsonData = jsonDataArray.getJSONObject(0);
			jsonObject.put("latlng", jsonData.get("lat") + "," + jsonData.get("lng"));
		} else {
			jsonObject.put("latlng", locations);
		}

		jsonObject.put("ret", ret);
		jsonObject.put("msg", msg);
		super.renderJson(response, jsonObject.toString());

		return null;
	}

	// GPSTOBAIDU
	public ActionForward gpsToBaidu(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JSONObject jsonObject = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String locations = (String) dynaBean.get("locations");
		String key = (String) dynaBean.get("key");
		String ret = "0";
		String msg = "";
		if (StringUtils.isBlank(locations) || StringUtils.isBlank(key)) {
			msg = "参数有误！";
			jsonObject.put("ret", ret);
			jsonObject.put("msg", msg);
			super.renderJson(response, jsonObject.toString());
			return null;
		}

		String url = "http://api.map.baidu.com/geoconv/v1/?coords=" + locations + "&from=1&to=5&ak=" + key
				+ "&output=json";

		String result = GetApiUtils.getApiWithUrl(url);

		JSONObject json = (JSONObject) JSONObject.parse(result.toString());

		String status = json.get("status").toString();
		if (status.equals("0")) {// 0代表成功
			JSONArray jsonDataArray = json.getJSONArray("result");
			JSONObject jsonData = jsonDataArray.getJSONObject(0);
			jsonObject.put("latlng", jsonData.get("x") + "," + jsonData.get("y"));
		} else {
			jsonObject.put("latlng", locations);
		}

		jsonObject.put("ret", ret);
		jsonObject.put("msg", msg);
		super.renderJson(response, jsonObject.toString());

		return null;
	}

	// 百度坐标to高德坐标
	public ActionForward baiduToGaodeByApi(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JSONObject jsonObject = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String locations = (String) dynaBean.get("locations");
		String key = (String) dynaBean.get("key");
		String ret = "0";
		String msg = "";
		if (StringUtils.isBlank(locations) || StringUtils.isBlank(key)) {
			msg = "参数有误！";
			jsonObject.put("ret", ret);
			jsonObject.put("msg", msg);
			super.renderJson(response, jsonObject.toString());
			return null;
		}

		String url = "http://restapi.amap.com/v3/assistant/coordinate/convert?locations=" + locations
				+ "&coordsys=baidu&output=json&key=" + key;

		String result = GetApiUtils.getApiWithUrl(url);

		JSONObject json = (JSONObject) JSONObject.parse(result.toString());
		String status = json.get("status").toString();
		if (status.equals("1")) {// 1代表成功
			jsonObject.put("latlng", json.get("locations").toString());
		} else {
			jsonObject.put("latlng", locations);
		}

		jsonObject.put("ret", ret);
		jsonObject.put("msg", msg);
		super.renderJson(response, jsonObject.toString());

		return null;
	}

	// 百度坐标to高德坐标
	public ActionForward baiduToGaodeNoApi(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JSONObject jsonObject = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String locations = (String) dynaBean.get("locations");
		String ret = "0";
		String msg = "";
		if (StringUtils.isBlank(locations)) {
			msg = "参数有误！";
			jsonObject.put("ret", ret);
			jsonObject.put("msg", msg);
			super.renderJson(response, jsonObject.toString());
			return null;
		}

		String[] latlng = locations.split(",");

		double[] gd_lat_lon = new double[2];
		double PI = 3.14159265358979324 * 3000.0 / 180.0;
		double x = Double.valueOf(latlng[0]) - 0.0065, y = Double.valueOf(latlng[1]) - 0.006;
		double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * PI);
		double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * PI);
		gd_lat_lon[0] = z * Math.cos(theta);
		gd_lat_lon[1] = z * Math.sin(theta);

		jsonObject.put("ret", ret);
		jsonObject.put("msg", msg);
		jsonObject.put("latlng", gd_lat_lon[0] + "," + gd_lat_lon[1]);
		super.renderJson(response, jsonObject.toString());
		return null;

	}

	public ActionForward updataIsTuihuo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JSONObject jsonObject = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		Integer ret = 0;
		String msg = "";
		Double tuikuan_sum_money = 0.0;
		OrderReturnInfo orderReturnInfo = new OrderReturnInfo();
		orderReturnInfo.setAudit_state(Keys.audit_state.audit_state_2.getIndex());
		orderReturnInfo.setIs_del(0);
		List<OrderReturnInfo> orderReturnInfoList = getFacade().getOrderReturnInfoService().getOrderReturnInfoList(
				orderReturnInfo);
		if (null != orderReturnInfoList && orderReturnInfoList.size() > 0) {

			for (OrderReturnInfo cur : orderReturnInfoList) {
				OrderInfoDetails b = new OrderInfoDetails();
				b.setOrder_id(cur.getOrder_id());
				List<OrderInfoDetails> blist = getFacade().getOrderInfoDetailsService().getOrderInfoDetailsList(b);
				if (null != blist && blist.size() > 0) {

					for (OrderInfoDetails temp : blist) {
						tuikuan_sum_money += temp.getGood_sum_price().doubleValue();
						OrderInfoDetails a = temp;
						a.setIs_tuihuo(1);
						int i = getFacade().getOrderInfoDetailsService().modifyOrderInfoDetails(a);
						if (i > 0) {
							ret++;
						}

					}

				}
			}
		}

		jsonObject.put("ret", tuikuan_sum_money);
		jsonObject.put("msg", ret);
		super.renderJson(response, jsonObject.toString());
		return null;

	}

	/**
	 * 获取新闻分类
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getNewsType(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			return null;
		}
		DynaBean dynaBean = (DynaBean) form;
		String cls_scope = (String) dynaBean.get("cls_scope");
		String noSelectFar = (String) dynaBean.get("noSelectFar");

		if (StringUtils.isBlank(cls_scope)) {// 根目录par_id为-1
			cls_scope = "1";
		}
		String clazzTree;
		if (StringUtils.isNotBlank(noSelectFar)) {
			clazzTree = StringHelper.getNewsTreeNodesFromBaseClassList(getFacade(), Integer.valueOf(cls_scope), false,
					true, "1");
		} else {
			clazzTree = StringHelper.getTreeNodesFromBaseClassList(getFacade(), Integer.valueOf(cls_scope), true);
		}
		request.setAttribute("clazzTree", clazzTree);
		return new ActionForward("/index/CsAjax/choose_cls.jsp");
	}

	public ActionForward DefaultComment(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JSONObject jsonObject = new JSONObject();
		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String ret = "0";
		String msg = "";
		if (StringUtils.isBlank(id)) {
			msg = "参数有误！";
			jsonObject.put("ret", ret);
			jsonObject.put("msg", msg);
			super.renderJson(response, jsonObject.toString());
			return null;
		}
		if (null == ui) {
			msg = "用户不存在！";
			jsonObject.put("ret", ret);
			jsonObject.put("msg", msg);
			super.renderJson(response, jsonObject.toString());
			return null;
		}
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setId(Integer.valueOf(id));
		orderInfo = getFacade().getOrderInfoService().getOrderInfo(orderInfo);
		if (null == orderInfo) {
			msg = "订单不存在！";
			jsonObject.put("ret", ret);
			jsonObject.put("msg", msg);
			super.renderJson(response, jsonObject.toString());
			return null;
		}
		OrderInfoDetails a = new OrderInfoDetails();
		a.setOrder_id(orderInfo.getId());
		List<OrderInfoDetails> list = getFacade().getOrderInfoDetailsService().getOrderInfoDetailsList(a);
		if (null != list && list.size() > 0) {
			List<CommentInfo> commentInfoList = new ArrayList<CommentInfo>();
			CommentInfo entity = null;
			for (OrderInfoDetails cur : list) {
				entity = new CommentInfo();
				super.copyProperties(entity, dynaBean);
				if (orderInfo.getOrder_type().equals(Keys.OrderType.ORDER_TYPE_10.getIndex())) {
					entity.setComm_type(Keys.CommentType.COMMENT_TYPE_10.getIndex());
				}
				entity.setLink_id(cur.getComm_id());
				entity.setComm_ip(this.getIpAddr(request));
				entity.setComm_time(new Date());
				entity.setComm_state(1);// 发布
				entity.setLink_f_id(orderInfo.getEntp_id());
				entity.setComm_type(orderInfo.getOrder_type());
				entity.setHas_pic(0);
				entity.setOrder_value(0);
				entity.setComm_uid(ui.getId());
				entity.setComm_uname(ui.getUser_name());
				entity.setOrder_details_id(a.getId());
				entity.setEntp_id(orderInfo.getEntp_id());
				entity.setComm_experience(Keys.DEFAULT_COMMENT);
				entity.setComm_level(1);
				entity.setComm_score(5);
				commentInfoList.add(entity);
			}
			CommentInfo insert = new CommentInfo();
			insert.setCommentInfoList(commentInfoList);
			int i = getFacade().getCommentInfoService().createCommentInfo(insert);
			if (i > 0) {
				ret = "1";
				msg = "系统默认好评！";
			}
		}
		jsonObject.put("ret", ret);
		jsonObject.put("msg", msg);
		super.renderJson(response, jsonObject.toString());
		return null;

	}

	public ActionForward updateCommentOrderDetailsID(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		JSONObject jsonObject = new JSONObject();

		Integer ret = 0;
		String msg = "";

		CommentInfo a = new CommentInfo();
		List<CommentInfo> list = getFacade().getCommentInfoService().getCommentInfoList(a);
		for (CommentInfo temp : list) {
			OrderInfoDetails b = new OrderInfoDetails();
			b.setOrder_id(temp.getOrder_id());
			b.setComm_id(temp.getLink_id());
			b.setComm_tczh_id(temp.getComm_tczh_id());
			b = getFacade().getOrderInfoDetailsService().getOrderInfoDetails(b);
			if (null != b) {
				temp.setOrder_details_id(b.getId());
				temp.setEntp_id(b.getEntp_id());
				temp.setComm_name(b.getComm_name());
				temp.setComm_tczh_name(b.getComm_tczh_name());
				getFacade().getCommentInfoService().modifyCommentInfo(temp);
			}
		}
		jsonObject.put("ret", 0);
		jsonObject.put("msg", ret);
		super.renderJson(response, jsonObject.toString());
		return null;

	}

	public ActionForward updateCommInfoAndEntpInfoCommentCount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		JSONObject jsonObject = new JSONObject();

		Integer comm_count = 0;
		Integer entp_count = 0;

		CommentInfo a = new CommentInfo();
		List<CommentInfo> list = getFacade().getCommentInfoService().getCommentInfoList(a);
		for (CommentInfo temp : list) {
			CommInfo commInfo = new CommInfo();
			commInfo.setId(temp.getLink_id());
			commInfo = getFacade().getCommInfoService().getCommInfo(commInfo);
			if (null != commInfo) {
				CommInfo commInfoUpdate = new CommInfo();
				commInfoUpdate.setId(commInfo.getId());
				commInfoUpdate.setComment_count(commInfo.getComment_count() + 1);
				int i = getFacade().getCommInfoService().modifyCommInfo(commInfoUpdate);
				if (i > 0) {
					comm_count++;
				}

				if (null != commInfo.getOwn_entp_id()) {
					EntpInfo entpInfo = new EntpInfo();
					entpInfo.setId(commInfo.getOwn_entp_id());
					entpInfo = getFacade().getEntpInfoService().getEntpInfo(entpInfo);
					if (null != entpInfo) {
						EntpInfo entpInfoUpdate = new EntpInfo();
						entpInfoUpdate.setId(entpInfo.getId());
						entpInfoUpdate.setComment_count(entpInfo.getComment_count() + 1);
						int j = getFacade().getEntpInfoService().modifyEntpInfo(entpInfoUpdate);
						if (j > 0) {
							entp_count++;
						}
					}
				}
			}
		}
		jsonObject.put("comm_count", comm_count);
		jsonObject.put("entp_count", entp_count);
		super.renderJson(response, jsonObject.toString());
		return null;

	}

	public ActionForward updateCartInfoTG(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// DynaBean dynaBean = (DynaBean) form;
		CartInfo entity = new CartInfo();
		super.copyProperties(entity, form);
		UserInfo ui = super.getUserInfoFromSession(request);
		StringBuffer result = new StringBuffer();
		result.append("{\"result\":");
		if (null == ui) {
			result.append("false");
			result.append("}");
			super.renderJson(response, result.toString());
			return null;
		}

		int rows = getFacade().getCartInfoService().modifyCartInfo(entity);
		if (rows >= 1) {
			result.append("true");
		} else {
			result.append("false");
		}

		entity = super.getFacade().getCartInfoService().getCartInfo(entity);
		if (null != entity) {
			result.append(",");
			result.append("\"add_count_money\":");

			BigDecimal add_count_money = new BigDecimal("0");
			Double pd_price = entity.getPd_price().doubleValue();

			pd_price = super.setCommTgInfo(entity, pd_price);
			add_count_money = add_count_money.add(new BigDecimal(pd_price * entity.getPd_count()));
			result.append(add_count_money);

			BaseData baseData = new BaseData();
			baseData.setType(Integer.valueOf(5000));
			baseData.setId(Integer.valueOf(5001));
			baseData = super.getFacade().getBaseDataService().getBaseData(baseData);
			if (null != baseData) {
				result.append(",");
				result.append("\"pre_number\":");
				result.append(baseData.getPre_number());
			}
		}
		result.append("}");
		super.renderJson(response, result.toString());
		return null;
	}

	public ActionForward setXY(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String x = (String) dynaBean.get("x");
		String y = (String) dynaBean.get("y");

		JSONObject jsonObject = new JSONObject();

		Integer ret = 0;
		String msg = "";

		getXY(x, y);

		jsonObject.put("ret", 0);
		jsonObject.put("msg", ret);
		super.renderJson(response, jsonObject.toString());
		return null;

	}

	public ActionForward getNewOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String x = (String) dynaBean.get("x");
		String y = (String) dynaBean.get("y");

		JSONObject jsonObject = new JSONObject();
		String title = "";
		String content = "";
		String url = "";
		boolean is_product = true;// TODO 正式发布的时候改成ture

		OrderInfo orderInfo = new OrderInfo();
		orderInfo.getMap().put("st_add_date", getTimeByMinute(-6000));
		orderInfo.getMap().put("en_add_date", new Date());
		List<OrderInfo> list = getFacade().getOrderInfoService().getOrderInfoList(orderInfo);
		if (null != list && list.size() > 0) {
			UserInfo userInfo = null;
			for (OrderInfo cur : list) {
				userInfo = new UserInfo();
				userInfo.setOwn_entp_id(cur.getEntp_id());
				userInfo.setIs_del(0);
				userInfo = getFacade().getUserInfoService().getUserInfo(userInfo);
				if (null != userInfo) {
					title = "您有新的订单，请注意查收。";
					content = "您有新的订单，请注意查收。";
					// share.put("share_url", "http://" + Keys.app_domain +
					// "/m/MEntpInfo.do?method=index&id=" +
					url = "http://" + Keys.app_domain + "/m/MMyOrderEntp.do?method=list&order_type="
							+ cur.getOrder_type();

					if (null != userInfo.getDevice_token_app()) {
						if (userInfo.getDevice_token_app().equals("android")) {
							AppPush.sendAndroidUnicastForNews(userInfo.getDevice_token(), title, content, url,
									is_product);
						} else if (userInfo.getDevice_token_app().equals("ios")) {
							AppPush.sendIOSUnicastForNews(userInfo.getDevice_token(), title, url, is_product);
						}
					}

				}
			}
		}

		super.renderJson(response, jsonObject.toString());
		return null;

	}

	public ActionForward updateOrderReturnInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String x = (String) dynaBean.get("x");
		String y = (String) dynaBean.get("y");

		JSONObject jsonObject = new JSONObject();

		Integer ret = 0;
		String msg = "";
		Integer return_info_count = 0;

		OrderInfo a = new OrderInfo();
		a.setOrder_state(Keys.OrderState.ORDER_STATE_15.getIndex());
		List<OrderInfo> list = getFacade().getOrderInfoService().getOrderInfoList(a);
		if (null != list && list.size() > 0) {
			for (OrderInfo cur : list) {
				OrderReturnInfo c = new OrderReturnInfo();
				c.setOrder_id(cur.getId());
				c.setIs_del(0);
				c = getFacade().getOrderReturnInfoService().getOrderReturnInfo(c);
				if (null == c) {
					System.out.println("===不存在");

					UserInfo userInfo = super.getUserInfo(cur.getAdd_user_id());
					if (null != userInfo) {
						OrderInfoDetails ods = new OrderInfoDetails();
						ods.setOrder_id(cur.getId());
						List<OrderInfoDetails> odsList = getFacade().getOrderInfoDetailsService()
								.getOrderInfoDetailsList(ods);
						ods = odsList.get(0);
						if (null != ods) {
							OrderReturnInfo returnInfo = new OrderReturnInfo();
							returnInfo.setOrder_id(cur.getId());
							returnInfo.setAudit_state(Keys.audit_state.audit_state_0.getIndex());
							returnInfo.setUser_id(userInfo.getId());
							returnInfo.setUser_name(userInfo.getUser_name());
							returnInfo.setReturn_type(Keys.return_why.return_why_11630.getIndex());
							returnInfo.setExpect_return_way(Keys.return_type.return_type_4_tuikuan.getIndex());
							returnInfo.setPrice(cur.getOrder_money());
							returnInfo.setNum(cur.getOrder_num());
							returnInfo.setTk_status(0);
							returnInfo.setIs_del(0);
							returnInfo.setIs_confirm(0);
							returnInfo.setAdd_date(new Date());
							returnInfo.setEntp_id(cur.getEntp_id());
							returnInfo.setComm_name(ods.getComm_name());
							int id = getFacade().getOrderReturnInfoService().createOrderReturnInfo(returnInfo);
							if (id > 0) {
								return_info_count++;
								BaseAuditRecord baseAudit = new BaseAuditRecord();
								baseAudit.setOpt_type(Keys.OptType.OPT_TYPE_11.getIndex());
								baseAudit.setLink_id(id);
								baseAudit.setLink_table("OrderReturnInfo");
								baseAudit.setOpt_note(cur.getAdd_user_name());
								baseAudit.setAdd_user_id(cur.getAdd_user_id());
								baseAudit.setAdd_user_name(cur.getAdd_user_name());
								baseAudit.setAdd_date(new Date());
								baseAudit.setAudit_state(0);
								getFacade().getBaseAuditRecordService().createBaseAuditRecord(baseAudit);
							}

						}

					}
					ret++;
				} else {
					System.out.println("===存在");
				}

			}

		}
		OrderReturnInfo b = new OrderReturnInfo();
		int count2 = getFacade().getOrderReturnInfoService().getOrderReturnInfoCount(b);

		jsonObject.put("ret", ret);
		jsonObject.put("msg", "退款订单数量:" + list.size() + ",order_return_info_num:" + count2);
		jsonObject.put("return_info_count", return_info_count);
		super.renderJson(response, jsonObject.toString());
		return null;

	}

	public ActionForward updateCartRedMoney(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("======updateCartRedMoney======");
		JSONObject jsonObject = new JSONObject();

		Integer ret = 0;
		String msg = "";

		DynaBean dynaBean = (DynaBean) form;
		String card_ids = (String) dynaBean.get("card_ids");
		String red_moneys = (String) dynaBean.get("red_moneys");
		logger.info("====card_ids:" + card_ids);
		logger.info("====red_moneys:" + red_moneys);
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			jsonObject.put("ret", -2);
			jsonObject.put("msg", "请先登录！");
			return null;
		}
		UserInfo userInfo = super.getUserInfo(ui.getId());
		if (null == userInfo) {
			jsonObject.put("ret", -2);
			jsonObject.put("msg", "当前用户不存在！");
			return null;
		}

		if (StringUtils.isNotBlank(card_ids) && StringUtils.isNotBlank(red_moneys)) {
			String card_id[] = card_ids.split(",");
			String red_money[] = red_moneys.split(",");
			if (null != card_id && card_id.length > 0 && null != red_money && red_moneys.length() > 0) {

				BigDecimal sum_red_money = new BigDecimal(0);
				for (String cur : red_money) {
					BigDecimal cur_red_money = new BigDecimal(cur);
					sum_red_money = sum_red_money.add(cur_red_money);
				}
				if (userInfo.getBi_dianzi_lock().compareTo(sum_red_money) == -1) {// (用户余额).compareTo(当前总额)
																					// ==
																					// 1
																					// ，表示小于
					jsonObject.put("ret", -1);
					jsonObject.put("msg", "账户红包余额不足！用户红包余额:" + sum_red_money + ",当前使用:" + sum_red_money);
					return null;
				}

				for (int i = 0; i < card_id.length; i++) {
					// 查询信息--购物车
					CartInfo cartInfo = new CartInfo();
					cartInfo.setId(Integer.valueOf(card_id[i]));
					cartInfo = getFacade().getCartInfoService().getCartInfo(cartInfo);
					if (null != cartInfo) {

						CartInfo updateCartInfo = new CartInfo();
						updateCartInfo.setId(cartInfo.getId());
						updateCartInfo.setRed_money(new BigDecimal(red_money[i]));// 塞入价格
						getFacade().getCartInfoService().modifyCartInfo(updateCartInfo);

					}

				}
			}
		}

		jsonObject.put("ret", ret);
		jsonObject.put("msg", msg);
		super.renderJson(response, jsonObject.toString());
		return null;

	}

	public ActionForward sendMobileMessage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String mobile = (String) dynaBean.get("mobile");
		String veri_code = (String) dynaBean.get("veri_code");
		String type = (String) dynaBean.get("type");
		String bdType = (String) dynaBean.get("bdType"); // 绑定type
		String isBdOrSet = (String) dynaBean.get("isBdOrSet"); // 绑定type
		String isValMobile = (String) dynaBean.get("isValMobile"); // 是否验证手机号
		String ret = "0";
		String msg = "";
		JSONObject datas = new JSONObject();

		String veri_code_session = (String) request.getSession().getAttribute(Keys.VERIFICATION_CODE);
		if (!GenericValidator.isLong(veri_code)) {
			msg = "请输入验证码";
			datas.put("ret", ret);
			datas.put("msg", msg);
			super.renderJson(response, datas.toString());
			return null;
		}
		if (StringUtils.isBlank(veri_code_session)) {
			msg = "veri_code_session为空";
			datas.put("ret", ret);
			datas.put("msg", msg);
			super.renderJson(response, datas.toString());
			return null;
		}
		if (!veri_code.equals(veri_code_session)) {
			msg = "验证码输入不正确";
			datas.put("ret", ret);
			datas.put("msg", msg);
			super.renderJson(response, datas.toString());
			return null;
		}
		if (StringUtils.isBlank(mobile)) {
			msg = "请输入手机号码";
			datas.put("ret", ret);
			datas.put("msg", msg);
			super.renderJson(response, datas.toString());
			return null;
		}

		if (StringUtils.isNotBlank(isValMobile)) {
			UserInfo entity = new UserInfo();
			entity.getMap().put("ym_id", mobile);
			entity.setIs_del(0);
			Integer recordCount = getFacade().getUserInfoService().getUserInfoCount(entity);
			if (recordCount.intValue() > 0) { // 手机号重复
				msg = "该手机号码已被占用";
				datas.put("ret", ret);
				datas.put("msg", msg);
				super.renderJson(response, datas.toString());
				return null;
			}
		}

		HttpSession session = request.getSession();

		if (StringUtils.isBlank(type)) {
			type = "1";
		}

		// 随机生成6位整数
		String ranNm = SmsUtils.generateCheckPass();
		StringBuffer message = new StringBuffer("");
		String template_code = "";
		if (type.equals("1")) {
			// message = StringUtils.replace(SMS.sms_01, "{0}", ranNm);
			message.append("{\"code\":\"" + ranNm + "\"}");
			template_code = SmsTemplate.sms_01;
		} else if (type.equals("2")) {
			// message = StringUtils.replace(SMS.sms_02, "{0}", ranNm);
			message.append("{\"code\":\"" + ranNm + "\"}");
			template_code = SmsTemplate.sms_02;
		} else if (type.equals("3")) {
			// 换绑
			// message = StringUtils.replace(SMS.sms_03, "{0}", ranNm);
			message.append("{\"code\":\"" + ranNm + "\"");
			template_code = SmsTemplate.sms_03;
			// 设置
			if (StringUtils.isNotBlank(isBdOrSet) && isBdOrSet.equals("0")) {
				// message = StringUtils.replace(SMS.sms_03_2, "{0}", ranNm);
				template_code = SmsTemplate.sms_03_2;
			}

			if (StringUtils.isBlank(bdType)) {
				bdType = "1";
			}
			if (bdType.equals("1")) {
				// message = StringUtils.replace(message, "{1}", SMS.hb_01);
				// message = StringUtils.replace(message, "{2}", SMS.hb_01);
				message.append(",\"name\":\"" + SMS.hb_01 + "\"");
			} else if (bdType.equals("2")) {
				// message = StringUtils.replace(message, "{1}", SMS.hb_02);
				// message = StringUtils.replace(message, "{2}", SMS.hb_02);
				message.append(",\"name\":\"" + SMS.hb_02 + "\"");
			} else if (bdType.equals("3")) {
				// message = StringUtils.replace(message, "{1}", SMS.hb_03);
				// message = StringUtils.replace(message, "{2}", SMS.hb_03);
				message.append(",\"name\":\"" + SMS.hb_03 + "\"");
			}
			message.append("}");
		}

		boolean is_local = false;
		is_local = super.isLocal(request);
		if (StringUtils.isNotBlank(message.toString())) {
			String result = "1";
			if (!is_local) {// 不是本地，发送短信
				// result = SmsUtils.sendMessage(message, mobile);
				SendSmsResponse sendSmsResponse = DySmsUtils.sendMessage(message.toString(), mobile, template_code);
				if ("OK".equals(sendSmsResponse.getCode())) {
					result = "0";
				}
			} else {// 如果是本地，则短信验证码为111111
				result = "0";
				ranNm = "111111";
			}

			logger.warn("==send result:{}", result);
			logger.warn("==send ranNm:{}", ranNm);
			if ("0".equals(result)) {
				session.setAttribute(Keys.MOBILE_VERI_CODE, ranNm);
				session.setAttribute(Keys.MOBILE_CODE, mobile);
				datas.put("veriCode", ranNm); // 测试
				ret = "1";
				msg = "发送成功";
			} else {
				ret = "0";
				msg = "发送失败，请稍后重试";
			}
		} else {
			ret = "0";
			msg = "发送失败，请稍后重试";
		}

		datas.put("ret", ret);
		datas.put("msg", msg);
		super.renderJson(response, datas.toString());
		return null;
	}

	public ActionForward validateEmail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String email = (String) dynaBean.get("email");
		String state = "0";

		if (StringUtils.isNotBlank(email)) {
			UserInfo entity = new UserInfo();
			entity.setEmail(email);
			entity.setIs_del(0);
			Integer recordCount = getFacade().getUserInfoService().getUserInfoCount(entity);
			if (recordCount.intValue() <= 0) { // 邮箱可用
				state = "1";
			} else if (recordCount.intValue() > 0) { // 邮箱重复
				state = "2";
			}
		}
		super.renderJson(response, state);
		return null;
	}

	public ActionForward sendEmail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String email = (String) dynaBean.get("email");
		String state = "0";
		JSONObject obj = new JSONObject();

		if (StringUtils.isNotBlank(email)) {
			HttpSession session = request.getSession();

			// 随机生成6位整数
			String ranNm = SmsUtils.generateCheckPass();

			String msg = getMessage(request, "send.email", new String[] { ranNm });

			EmailUtils.sendEmail(Keys.app_name + "邮箱验证", msg, email);

			session.setAttribute(Keys.EMAIL_VERI_CODE, ranNm);
			obj.put("veriCode", ranNm);
			state = "1";
		}
		obj.put("state", state);
		super.renderJson(response, obj.toString());
		return null;
	}

	public ActionForward validateName(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;

		StringBuffer oper = new StringBuffer("{\"result\":");
		String user_name = (String) dynaBean.get("user_name");
		UserInfo entity = new UserInfo();
		// entity.setUser_name(user_name);
		entity.getMap().put("ym_id", user_name);
		entity.setIs_del(0);
		int recordCount = super.getFacade().getUserInfoService().getUserInfoCount(entity);
		if (recordCount <= 0) { // 手机号可用
			oper.append(false);
		} else if (recordCount > 0) { // 手机号重复
			oper.append(true);
		}
		oper.append("}");
		super.renderJson(response, oper.toString());
		return null;
	}

	public ActionForward validateMobile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String mobile = (String) dynaBean.get("mobile");
		String state = "0";

		if (StringUtils.isNotBlank(mobile)) {
			UserInfo entity = new UserInfo();
			// entity.setMobile(mobile);
			entity.getMap().put("ym_id", mobile);
			entity.setIs_del(0);
			Integer recordCount = getFacade().getUserInfoService().getUserInfoCount(entity);
			if (recordCount.intValue() <= 0) { // 手机号可用
				state = "1";
			} else if (recordCount.intValue() > 0) { // 手机号重复
				state = "2";
			}
		}
		super.renderJson(response, state);
		return null;
	}

	public ActionForward validateMobileForWeiXin(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String mobile = (String) dynaBean.get("mobile");
		String openId = (String) dynaBean.get("openId");
		String ret = "0";

		JSONObject data = new JSONObject();

		if (StringUtils.isNotBlank(mobile)) {
			if (StringUtils.isNotBlank(openId)) {// 如果有微信id
				ret = "1";
				UserInfo entity = new UserInfo();
				entity.getMap().put("ym_id", mobile);
				entity.setIs_del(0);
				entity = getFacade().getUserInfoService().getUserInfo(entity);
				if (null != entity) {// 用户存在
					if (null != entity.getAppid_weixin()) {
						ret = "2";
					}
				}
			} else {
				UserInfo entity = new UserInfo();
				entity.getMap().put("ym_id", mobile);
				entity.setIs_del(0);
				Integer recordCount = getFacade().getUserInfoService().getUserInfoCount(entity);
				if (recordCount.intValue() <= 0) { // 手机号可用
					ret = "1";
				} else if (recordCount.intValue() > 0) { // 手机号重复
					ret = "2";
					List<UserInfo> userInfoList = super.getFacade().getUserInfoService().getUserInfoList(entity);
					data.put("user_id", userInfoList.get(0).getId());
				}
			}
		}

		data.put("ret", ret);
		super.renderJson(response, data.toString());
		return null;
	}

	public ActionForward getCartInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String user_id = (String) dynaBean.get("user_id");

		JSONObject jsonObject = new JSONObject();
		String ret = "0", msg = "";
		if (StringUtils.isBlank(user_id)) {
			msg = "参数有误，请联系管理员!";
			jsonObject.put("ret", ret);
			jsonObject.put("msg", msg);
			super.renderJson(response, jsonObject.toString());
			return null;
		}

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			ret = "-1";
			jsonObject.put("ret", ret);
			jsonObject.put("msg", msg);
			super.renderJson(response, jsonObject.toString());
			return null;
		}

		CartInfo entity = new CartInfo();
		entity.setUser_id(ui.getId());
		// 获取当前店铺entpId和购物车商品
		Cookie current_entp_id = WebUtils.getCookie(request, Keys.COOKIE_ENTP_ID);
		String entp_id = URLDecoder.decode(current_entp_id.getValue(), "UTF-8");
		entity.setEntp_id(Integer.valueOf(entp_id));

		List<CartInfo> cartInfoList = getFacade().getCartInfoService().getCartInfoList(entity);
		Integer total_count = 0;
		BigDecimal total_price = new BigDecimal("0");
		if (cartInfoList.size() > 0) {
			for (CartInfo temp : cartInfoList) {
				total_price = total_price.add(temp.getPd_price().multiply(new BigDecimal(temp.getPd_count())));
				total_count = total_count + temp.getPd_count();
			}
			jsonObject.put("total_count", total_count);
			jsonObject.put("total_price", total_price);

			jsonObject.put("cartInfoList", cartInfoList);
		}
		ret = "1";
		jsonObject.put("ret", ret);
		jsonObject.put("msg", msg);

		super.renderJson(response, jsonObject.toString());
		return null;
	}

	public ActionForward getTcList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String comm_id = (String) dynaBean.get("comm_id");

		UserInfo ui = super.getUserInfoFromSession(request);

		JSONObject jsonObject = new JSONObject();
		String ret = "0", msg = "";
		CommInfo commInfo = super.getCommInfo(Integer.valueOf(comm_id));
		if (null == commInfo) {
			msg = "关联商品有问题，请联系管理员!";
			jsonObject.put("ret", ret);
			jsonObject.put("msg", msg);
			super.renderJson(response, jsonObject.toString());
			return null;
		}
		jsonObject.put("comm_name", commInfo.getComm_name());

		CommTczhPrice commTc = new CommTczhPrice();
		commTc.setComm_id(comm_id);
		List<CommTczhPrice> tcList = getFacade().getCommTczhPriceService().getCommTczhPriceList(commTc);
		for (CommTczhPrice temp : tcList) {
			// 获取购物车已有的
			CartInfo cartInfo = new CartInfo();
			cartInfo.setComm_id(Integer.valueOf(comm_id));
			cartInfo.setComm_tczh_id(temp.getId());
			cartInfo.setUser_id(ui.getId());
			cartInfo = getFacade().getCartInfoService().getCartInfoCountForSumCount(cartInfo);
			temp.getMap().put("cartInfo", cartInfo);
		}

		jsonObject.put("tcList", tcList);

		ret = "1";
		jsonObject.put("ret", ret);
		jsonObject.put("msg", msg);

		super.renderJson(response, jsonObject.toString());
		return null;
	}

	public ActionForward getHasNewOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JSONObject jsonObject = new JSONObject();

		String ret = "0";
		String msg = "";

		UserInfo userInfo = super.getUserInfoFromSession(request);
		if (null == userInfo) {
			msg = "您未登录！";
			jsonObject.put("ret", ret);
			jsonObject.put("msg", msg);
			super.renderJson(response, jsonObject.toString());
			return null;
		}

		OrderInfo orderInfo = new OrderInfo();

		Date date = new Date();
		orderInfo.getMap().put("st_pay_date_queryNewOrder", sdFormat_ymdhms.format(DateUtils.addSeconds(date, -30)));
		orderInfo.getMap().put("en_pay_date_queryNewOrder", sdFormat_ymdhms.format(date));
		orderInfo.setOrder_state(Keys.OrderState.ORDER_STATE_10.getIndex());
		orderInfo.setOrder_type(Keys.OrderType.ORDER_TYPE_10.getIndex());
		orderInfo.setEntp_id(userInfo.getOwn_entp_id());
		int count = super.getFacade().getOrderInfoService().getOrderInfoCount(orderInfo);
		if (count > 0) {
			ret = "1";
			msg = "你有新的订单，请立即处理！";
		}

		jsonObject.put("ret", ret);
		jsonObject.put("msg", msg);
		super.renderJson(response, jsonObject.toString());

		return null;
	}

	public ActionForward getOrderInfoSumMoneyOrCountAjaxData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		JSONObject datas = new JSONObject();
		JSONArray seriesList = new JSONArray();

		JSONArray list = new JSONArray();
		JSONArray legendd = new JSONArray();
		JSONObject pifa = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String orderDay = (String) dynaBean.get("orderDay");
		String st_date = (String) dynaBean.get("st_date");
		String en_date = (String) dynaBean.get("en_date");
		String own_entp_id = (String) dynaBean.get("own_entp_id");
		String echartsType = (String) dynaBean.get("echartsType");
		// 2 订单数量
		String type = (String) dynaBean.get("type");

		String name = "";
		if (type.equals("1")) {
			name = "交易金额";
		} else if (type.equals("2")) {
			name = "订单数量";
		}

		if (orderDay.equals("1")) {// 查询最近一个月
			st_date = sdFormat_ymd.format(DateTools.getFirstDayOfLastMonday(new Date()));
			en_date = sdFormat_ymd.format(DateTools.getLastDayOfLastMonday(new Date()));
		} else if (orderDay.equals("2")) {// 默认查询往前推七天
			st_date = DateTools.getLastDay(7);
			en_date = DateTools.getLastDay(0);
		}

		int xcDate = DateTools.getXcDaysBetweenTwoDay(sdFormat_ymd.parse(en_date), sdFormat_ymd.parse(st_date));

		String[] seriesdataCount = new String[xcDate + 1];

		for (int i = 0; i <= xcDate; i++) {

			String date = DateTools.getTheDayLastDay(sdFormat_ymd.parse(en_date), xcDate - i);

			OrderInfo orderInfoTodaySum = new OrderInfo();
			if (StringUtils.isNotBlank(own_entp_id)) {
				orderInfoTodaySum.setEntp_id(Integer.valueOf(own_entp_id));
			}
			orderInfoTodaySum.getMap().put("order_state_ne", Keys.OrderState.ORDER_STATE_90.getIndex());
			orderInfoTodaySum.getMap().put("order_state_ge", Keys.OrderState.ORDER_STATE_10.getIndex());
			orderInfoTodaySum.getMap().put("st_add_date", date);
			orderInfoTodaySum.getMap().put("en_add_date", date);
			orderInfoTodaySum = super.getFacade().getOrderInfoService().getOrderInfoStatisticaSum(orderInfoTodaySum);
			if (type.equals("1")) {
				seriesdataCount[i] = orderInfoTodaySum.getMap().get("sum_money").toString();
			} else if (type.equals("2")) {
				seriesdataCount[i] = orderInfoTodaySum.getMap().get("cnt").toString();
			}
		}

		if (StringUtils.isBlank(echartsType)) {
			echartsType = Keys.EchartsType.EchartsTypeBar.getName();
		}

		pifa = new JSONObject();
		pifa.put("name", name);
		pifa.put("type", echartsType);
		pifa.put("data", seriesdataCount);
		seriesList.add(pifa);

		legendd.add(name);

		for (int i = 0; i <= xcDate; i++) {
			String date = DateTools.getTheDayLastDay(sdFormat_ymd.parse(en_date), xcDate - i);
			list.add(date);
		}

		datas.put("series", seriesList);
		datas.put("legend", legendd);
		datas.put("xAxis", list);
		datas.put("ret", "1");

		super.renderJson(response, datas.toString());
		return null;
	}

	public ActionForward getOrderInfoSumMoneyAndCountAjaxData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		UserInfo userInfo = super.getUserInfoFromSession(request);

		String orderDay = (String) dynaBean.get("orderDay");
		String st_date = (String) dynaBean.get("st_date");
		String en_date = (String) dynaBean.get("en_date");

		if (orderDay.equals("1")) {// 查询最近一个月
			st_date = sdFormat_ymd.format(DateTools.getFirstDayOfLastMonday(new Date()));
			en_date = sdFormat_ymd.format(DateTools.getLastDayOfLastMonday(new Date()));
		} else if (orderDay.equals("2")) {// 默认查询往前推七天
			st_date = DateTools.getLastDay(7);
			en_date = DateTools.getLastDay(0);
		}

		JSONObject datas = new JSONObject();
		JSONArray seriesList = new JSONArray();
		OrderInfo orderInfoTodaySum;
		String[] sum_money;

		JSONArray list = new JSONArray();
		JSONArray legendd = new JSONArray();
		JSONObject pifa = new JSONObject();

		int xcDate = DateTools.getXcDaysBetweenTwoDay(sdFormat_ymd.parse(en_date), sdFormat_ymd.parse(st_date));

		EntpInfo entpInfo;
		if (userInfo.getIs_fuwu().intValue() == 1) {
			ServiceCenterInfo serviceCenterInfo = super.getUserLinkServiceInfo(userInfo.getId());
			List<EntpInfo> entpInfoList = super.getEntpInfoListByServiceId(serviceCenterInfo.getId());
			if (null != entpInfoList && entpInfoList.size() > 0) {
				String[] entp_names = new String[entpInfoList.size()];
				String[] entp_ids = new String[entpInfoList.size()];
				for (int i = 0; i < entpInfoList.size(); i++) {
					entpInfo = entpInfoList.get(i);
					entp_names[i] = entpInfo.getEntp_name().toString();
					entp_ids[i] = entpInfo.getId().toString();
					String name = entp_names[i];

					sum_money = new String[xcDate + 1];
					for (int d = 0; d <= xcDate; d++) {

						String date = DateTools.getTheDayLastDay(sdFormat_ymd.parse(en_date), xcDate - d);

						orderInfoTodaySum = new OrderInfo();
						orderInfoTodaySum.setEntp_id(entpInfo.getId());
						orderInfoTodaySum.getMap().put("order_state_ne", Keys.OrderState.ORDER_STATE_90.getIndex());
						orderInfoTodaySum.getMap().put("order_state_ge", Keys.OrderState.ORDER_STATE_10.getIndex());
						orderInfoTodaySum.getMap().put("st_add_date", date);
						orderInfoTodaySum.getMap().put("en_add_date", date);
						orderInfoTodaySum = super.getFacade().getOrderInfoService()
								.getOrderInfoStatisticaSum(orderInfoTodaySum);
						sum_money[d] = orderInfoTodaySum.getMap().get("sum_money").toString();
					}

					pifa = new JSONObject();
					pifa.put("name", name);
					pifa.put("type", Keys.EchartsType.EchartsTypeBar.getName());
					pifa.put("data", sum_money);
					seriesList.add(pifa);
					legendd.add(name);
				}

				for (int i = 0; i <= xcDate; i++) {
					String date = DateTools.getTheDayLastDay(sdFormat_ymd.parse(en_date), xcDate - i);
					list.add(date);
				}

				datas.put("series", seriesList);
				datas.put("legend", legendd);
				datas.put("xAxis", list);
				datas.put("ret", "1");
			}
		}
		dynaBean.set("st_date", st_date);
		dynaBean.set("en_date", en_date);
		super.renderJson(response, datas.toString());
		return null;
	}

	/**
	 * @author D&M
	 * @version 2018-01-26
	 * @desc 验证村站是否已经存在
	 */
	public ActionForward validateVillageIsExist(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String p_index = (String) dynaBean.get("p_index");
		JSONObject jsonObject = new JSONObject();

		String ret = "0";
		String msg = "";
		UserInfo userInfo = super.getUserInfoFromSession(request);
		if (StringUtils.isBlank(p_index)) {
			ret = "1";
			msg = "参数错误！";
		}

		VillageInfo villageInfo = new VillageInfo();
		villageInfo.setP_index(Long.valueOf(p_index));
		villageInfo.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		int count = super.getFacade().getVillageInfoService().getVillageInfoCount(villageInfo);
		if (count > 0) {
			ret = "1";
			msg = "村站已存在，请查看该村站！";
		}

		jsonObject.put("ret", ret);
		jsonObject.put("msg", msg);
		super.renderJson(response, jsonObject.toString());
		return null;
	}

	public ActionForward validate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String value = (String) dynaBean.get("value");
		String id = (String) dynaBean.get("id");
		String type = (String) dynaBean.get("type");
		JSONObject data = new JSONObject();

		if (StringUtils.isNotBlank(value)) {
			if (type.equals("entp_name")) {
				int count = validateEntpName(id, value);
				if (count > 0) {
					data.put("ret", -1);
					data.put("msg", "店铺名称重复");
					super.renderJson(response, data.toString());
					return null;
				}
			}
			if (type.equals("comm_name")) {
				// int count = validateCommName(id, value);
				// if (count > 0) {
				// data.put("ret", -1);
				// data.put("msg", "商品名称重复");
				// super.renderJson(response, data.toString());
				// return null;
				// }
			}
			data.put("ret", 1);
		}

		super.renderJson(response, data.toString());
		return null;
	}

	/**
	 * @author D&M
	 * @version 2018-05-03
	 * @desc 支付时使用余额抵扣修改订单金额order_money，money_bi
	 */
	public ActionForward userYuE(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String trade_index = (String) dynaBean.get("trade_index");
		String order_money = (String) dynaBean.get("order_money");
		JSONObject data = new JSONObject();

		String msg = "", ret = "0";

		UserInfo ui = super.getUserInfoFromSession(request);

		if (null == ui) {
			msg = "您还未登录，请先登录系统！";
			data.put("msg", msg);
			data.put("ret", ret);
			super.renderJson(response, data.toString());
			return null;
		}
		ui = this.getUserInfo(ui.getId());

		if (StringUtils.isBlank(trade_index) || StringUtils.isBlank(order_money)) {
			msg = "参数错误！";
			data.put("msg", msg);
			data.put("ret", ret);
			super.renderJson(response, data.toString());
			return null;
		}
		BigDecimal yu_e = ui.getBi_dianzi();
		logger.info("=========id========" + ui.getId() + "=======bi_dianzi====" + ui.getBi_dianzi());
		trade_index = trade_index.trim();
		String[] trade_indexs = trade_index.split(",");
		if (ArrayUtils.isNotEmpty(trade_indexs) && (trade_indexs.length > 0)) {
			for (String trade_index2 : trade_indexs) {
				if (StringUtils.isNotBlank(trade_index2)) {
					if (yu_e.compareTo(new BigDecimal(0)) <= 0) {
						break;
					}
					OrderInfo orderInfo = new OrderInfo();
					orderInfo.setAdd_user_id(ui.getId());
					orderInfo.setTrade_index(trade_index2);
					orderInfo = getFacade().getOrderInfoService().getOrderInfo(orderInfo);
					if (null == orderInfo) {
						msg = "订单不存在！";
						data.put("msg", msg);
						data.put("ret", ret);
						super.renderJson(response, data.toString());
						return null;
					}
					if (orderInfo.getOrder_state().intValue() != 0) {
						msg = "订单" + trade_index2 + "状态已更新！";
						data.put("msg", msg);
						data.put("ret", ret);
						super.renderJson(response, data.toString());
						return null;
					}

					if (yu_e.compareTo(orderInfo.getOrder_money()) >= 0) {// 用户余额大于订单金额
						yu_e = yu_e.subtract(orderInfo.getOrder_money());
						orderInfo.setMoney_bi(orderInfo.getOrder_money());
						orderInfo.setOrder_money(new BigDecimal(0));
						orderInfo.setReal_pay_money(orderInfo.getOrder_money());
					} else {// 用户余额小于订单金额
						orderInfo.setMoney_bi(yu_e);
						orderInfo.setOrder_money(orderInfo.getOrder_money().subtract(yu_e));
						orderInfo.setReal_pay_money(orderInfo.getOrder_money());
						yu_e = new BigDecimal(0);
					}
					int flag = getFacade().getOrderInfoService().modifyOrderInfo(orderInfo);
					if (Integer.valueOf(flag) > 0) {
						ret = "1";
					}
				}
			}
		}
		data.put("msg", msg);
		data.put("ret", ret);
		super.renderJson(response, data.toString());
		return null;
	}

	public ActionForward userCard(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String trade_index = (String) dynaBean.get("trade_index");
		String order_money = (String) dynaBean.get("order_money");
		JSONObject data = new JSONObject();

		String msg = "", ret = "0";

		UserInfo ui = super.getUserInfoFromSession(request);

		if (null == ui) {
			msg = "您还未登录，请先登录系统！";
			data.put("msg", msg);
			data.put("ret", ret);
			super.renderJson(response, data.toString());
			return null;
		}
		ui = this.getUserInfo(ui.getId());

		if (StringUtils.isBlank(trade_index) || StringUtils.isBlank(order_money)) {
			msg = "参数错误！";
			data.put("msg", msg);
			data.put("ret", ret);
			super.renderJson(response, data.toString());
			return null;
		}

		trade_index = trade_index.trim();
		trade_index = trade_index.split(",")[0];
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setAdd_user_id(ui.getId());
		orderInfo.setTrade_index(trade_index);
		orderInfo = getFacade().getOrderInfoService().getOrderInfo(orderInfo);
		if (null == orderInfo) {
			msg = "订单不存在！";
			data.put("msg", msg);
			data.put("ret", ret);
			super.renderJson(response, data.toString());
			return null;
		}
		if (orderInfo.getOrder_state().intValue() != 0) {
			msg = "订单" + trade_index + "状态已更新！";
			data.put("msg", msg);
			data.put("ret", ret);
			super.renderJson(response, data.toString());
			return null;
		}

		if (orderInfo.getCard_id() == null) {
			msg = "福利卡信息有误！";
			data.put("msg", msg);
			data.put("ret", ret);
			super.renderJson(response, data.toString());
			return null;
		}
		CardInfo card = new CardInfo();
		card.setId(orderInfo.getCard_id());
		card.setIs_del(0);
		card.setUser_id(ui.getId());
		card.getMap().put("card_state_ge", Keys.CARD_STATE.CARD_STATE_1.getIndex());
		card = getFacade().getCardInfoService().getCardInfo(card);
		if (null == card) {
			msg = "福利卡信息有误,无法抵扣！";
			data.put("msg", msg);
			data.put("ret", ret);
			super.renderJson(response, data.toString());
			return null;
		}

		if (card.getEnd_date().before(new Date())) {
			msg = "福利卡已过期,无法抵扣！";
			data.put("msg", msg);
			data.put("ret", ret);
			super.renderJson(response, data.toString());
			return null;
		}

		BigDecimal yu_e = card.getCard_cash();
		if (yu_e.compareTo(new BigDecimal(0)) < 0) {
			msg = "福利卡有余额有误！";
			data.put("msg", msg);
			data.put("ret", ret);
			super.renderJson(response, data.toString());
			return null;
		}
		if (orderInfo.getCard_pay_money().compareTo(new BigDecimal(0)) == 1) {
			orderInfo.setOrder_money(orderInfo.getCard_pay_money().add(orderInfo.getOrder_money()));
			orderInfo.setReal_pay_money(orderInfo.getOrder_money());
			orderInfo.setCard_pay_money(new BigDecimal(0));
			getFacade().getOrderInfoService().modifyOrderInfo(orderInfo);
		}

		if (yu_e.compareTo(orderInfo.getOrder_money()) >= 0) {// 用户福利卡大于订单金额
			yu_e = yu_e.subtract(orderInfo.getOrder_money());
			orderInfo.setCard_pay_money(orderInfo.getOrder_money());
			orderInfo.setOrder_money(new BigDecimal(0));
			orderInfo.setReal_pay_money(orderInfo.getOrder_money());
		} else {// 用户余额小于订单金额
			orderInfo.setCard_pay_money(yu_e);
			orderInfo.setOrder_money(orderInfo.getOrder_money().subtract(yu_e));
			orderInfo.setReal_pay_money(orderInfo.getOrder_money());
			yu_e = new BigDecimal(0);
		}
		int flag = getFacade().getOrderInfoService().modifyOrderInfo(orderInfo);
		if (Integer.valueOf(flag) > 0) {
			ret = "1";
		}
		data.put("msg", msg);
		data.put("ret", ret);
		super.renderJson(response, data.toString());
		return null;
	}

	public ActionForward notUserYuE(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String trade_index = (String) dynaBean.get("trade_index");
		JSONObject data = new JSONObject();

		String msg = "", ret = "0";

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			msg = "您还未登录，请先登录系统！";
			data.put("msg", msg);
			data.put("ret", ret);
			super.renderJson(response, data.toString());
			return null;
		}

		if (StringUtils.isBlank(trade_index)) {
			msg = "参数错误！";
			data.put("msg", msg);
			data.put("ret", ret);
			super.renderJson(response, data.toString());
			return null;
		}
		BigDecimal order_money = new BigDecimal(0);
		trade_index = trade_index.trim();
		String[] trade_indexs = trade_index.split(",");
		if (ArrayUtils.isNotEmpty(trade_indexs) && (trade_indexs.length > 0)) {
			for (String trade_index2 : trade_indexs) {
				if (StringUtils.isNotBlank(trade_index2)) {
					OrderInfo orderInfo = new OrderInfo();
					orderInfo.setAdd_user_id(ui.getId());
					orderInfo.setTrade_index(trade_index2);
					orderInfo = getFacade().getOrderInfoService().getOrderInfo(orderInfo);
					if (null == orderInfo) {
						msg = "订单不存在！";
						data.put("msg", msg);
						data.put("ret", ret);
						super.renderJson(response, data.toString());
						return null;
					}
					if (orderInfo.getOrder_state().intValue() != 0) {
						msg = "订单" + trade_index2 + "状态已更新！";
						data.put("msg", msg);
						data.put("ret", ret);
						super.renderJson(response, data.toString());
						return null;
					}

					if (orderInfo.getMoney_bi().compareTo(new BigDecimal(0)) > 0) {
						orderInfo.setOrder_money(orderInfo.getMoney_bi().add(orderInfo.getOrder_money()));
						orderInfo.setReal_pay_money(orderInfo.getOrder_money());
						orderInfo.setMoney_bi(new BigDecimal(0));
						getFacade().getOrderInfoService().modifyOrderInfo(orderInfo);
					}
					order_money = order_money.add(orderInfo.getOrder_money());
				}
			}
			ret = "1";
			data.put("order_money", order_money);
		}
		data.put("msg", msg);
		data.put("ret", ret);
		super.renderJson(response, data.toString());
		return null;
	}

	public ActionForward setCartYhq(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JSONObject data = new JSONObject();
		String ret = "-1", msg = "";

		DynaBean dynaBean = (DynaBean) form;
		String cart_id = (String) dynaBean.get("cart_id");
		String yhq_son_id = (String) dynaBean.get("yhq_son_id");
		String index = (String) dynaBean.get("index");

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			msg = "您还未登录，请先登录系统！";
			return super.returnAjaxData(response, ret, msg, data);
		}
		if (!GenericValidator.isInt(cart_id) && !GenericValidator.isInt(yhq_son_id) && !GenericValidator.isInt(index)) {
			msg = "参数不正确！";
			return super.returnAjaxData(response, ret, msg, data);
		}

		CartInfo cartInfo = new CartInfo();
		cartInfo.setId(Integer.valueOf(cart_id));
		cartInfo = getFacade().getCartInfoService().getCartInfo(cartInfo);
		if (null == cartInfo) {
			msg = "购物车不存在！";
			return super.returnAjaxData(response, ret, msg, data);
		}

		YhqInfoSon yhqson = getYhqInfoSonAvailable(yhq_son_id);
		if (null == yhqson) {
			msg = "优惠券不存在或已被使用！";
			return super.returnAjaxData(response, ret, msg, data);
		}
		YhqInfo yhq = getYhqInfo(yhqson.getLink_id());
		if (null == yhq) {
			msg = "优惠券不存在或已被使用！";
			return super.returnAjaxData(response, ret, msg, data);
		}

		CartInfo update = new CartInfo();
		update.setId(cartInfo.getId());
		if (Integer.valueOf(index) == 1) {
			update.setYhq_id(yhqson.getId());
			update.setYhq_money(yhq.getYhq_money().toString());
			// 判断当前优惠券是否被选择
			update.getMap().put("only_yhq_son_id", true);

			CartInfo is = new CartInfo();
			is.setYhq_id(yhqson.getId());
			int is_count = getFacade().getCartInfoService().getCartInfoCount(is);
			if (is_count > 0) {
				CartInfo updateCart = new CartInfo();
				updateCart.getMap().put("yhq_id", yhqson.getId());
				updateCart.getMap().put("set_yhq_id_null", "true");
				updateCart.getMap().put("set_yhq_money_null", "true");
				int i = getFacade().getCartInfoService().modifyCartInfo(updateCart);
				if (i > 0) {
					data.put("update_other_yhq", "1");
				}
			}
		} else {
			update.getMap().put("set_yhq_id_null", "true");
			update.getMap().put("set_yhq_money_null", "true");
		}

		int i = getFacade().getCartInfoService().modifyCartInfo(update);
		if (i > 0) {
			ret = "1";
		}
		return super.returnAjaxData(response, ret, msg, data);
	}

	public ActionForward validVillageName(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String village_name = (String) dynaBean.get("village_name");
		String is_virtual = (String) dynaBean.get("is_virtual");
		String ret = "1";

		JSONObject data = new JSONObject();

		if (StringUtils.isNotBlank(village_name)) {
			VillageInfo entity = new VillageInfo();
			entity.setVillage_name(village_name);
			entity.setIs_del(0);
			if (StringUtils.isNotBlank(is_virtual)) {
				entity.setIs_virtual(Integer.valueOf(is_virtual));
			}
			Integer recordCount = getFacade().getVillageInfoService().getVillageInfoCount(entity);
			if (recordCount.intValue() > 0) { // 村站名称重复
				ret = "-1";
			}
		}

		data.put("ret", ret);
		super.renderJson(response, data.toString());
		return null;
	}

	public ActionForward validServiceName(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String servicecenter_name = (String) dynaBean.get("servicecenter_name");
		String is_virtual = (String) dynaBean.get("is_virtual");
		String ret = "1";

		JSONObject data = new JSONObject();

		if (StringUtils.isNotBlank(servicecenter_name)) {
			ServiceCenterInfo entity = new ServiceCenterInfo();
			entity.setServicecenter_name(servicecenter_name);
			entity.setIs_del(0);
			if (StringUtils.isNotBlank(is_virtual)) {
				entity.setIs_virtual(Integer.valueOf(is_virtual));
			}
			Integer recordCount = getFacade().getServiceCenterInfoService().getServiceCenterInfoCount(entity);
			if (recordCount.intValue() > 0) { // 合伙人名称重复
				ret = "-1";
			}
		}

		data.put("ret", ret);
		super.renderJson(response, data.toString());
		return null;
	}

	public ActionForward getCartNum(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String cart_type = (String) dynaBean.get("cart_type");
		String ret = "0";

		JSONObject data = new JSONObject();

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null != ui) {
			ui = super.getUserInfo(ui.getId());

			CartInfo cartInfo = new CartInfo();
			cartInfo.setIs_del(0);
			cartInfo.setUser_id(ui.getId());
			if (StringUtils.isNotBlank(cart_type)) {
				cartInfo.setCart_type(Integer.valueOf(cart_type));
			} else {
				cartInfo.setCart_type(Keys.CartType.CART_TYPE_10.getIndex());
			}
			CartInfo cart = getFacade().getCartInfoService().getCartInfoCountForSumCount(cartInfo);
			data.put("sumPdCount", cart.getMap().get("sumPdCount"));
			ret = "1";
		}
		data.put("ret", ret);
		super.renderJson(response, data.toString());
		return null;
	}

	public ActionForward notUserCard(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String trade_index = (String) dynaBean.get("trade_index");
		JSONObject data = new JSONObject();

		String msg = "", ret = "0";

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			msg = "您还未登录，请先登录系统！";
			data.put("msg", msg);
			data.put("ret", ret);
			super.renderJson(response, data.toString());
			return null;
		}

		if (StringUtils.isBlank(trade_index)) {
			msg = "参数错误！";
			data.put("msg", msg);
			data.put("ret", ret);
			super.renderJson(response, data.toString());
			return null;
		}
		BigDecimal order_money = new BigDecimal(0);
		trade_index = trade_index.trim();
		String[] trade_indexs = trade_index.split(",");
		if (ArrayUtils.isNotEmpty(trade_indexs) && (trade_indexs.length > 0)) {
			for (String trade_index2 : trade_indexs) {
				if (StringUtils.isNotBlank(trade_index2)) {
					OrderInfo orderInfo = new OrderInfo();
					orderInfo.setAdd_user_id(ui.getId());
					orderInfo.setTrade_index(trade_index2);
					orderInfo = getFacade().getOrderInfoService().getOrderInfo(orderInfo);
					if (null == orderInfo) {
						msg = "订单不存在！";
						data.put("msg", msg);
						data.put("ret", ret);
						super.renderJson(response, data.toString());
						return null;
					}

					if (orderInfo.getOrder_state().intValue() != 0) {
						msg = "订单" + trade_index2 + "状态已更新！";
						data.put("msg", msg);
						data.put("ret", ret);
						super.renderJson(response, data.toString());
						return null;
					}

					if (orderInfo.getCard_id() == null) {
						msg = "福利卡信息有误！";
						data.put("msg", msg);
						data.put("ret", ret);
						super.renderJson(response, data.toString());
						return null;
					}

					CardInfo card = new CardInfo();
					card.setId(orderInfo.getCard_id());
					card.setIs_del(0);
					card.setUser_id(ui.getId());
					card.getMap().put("card_state_ge", Keys.CARD_STATE.CARD_STATE_1.getIndex());
					card = getFacade().getCardInfoService().getCardInfo(card);
					if (null == card) {
						msg = "福利卡信息有误！";
						data.put("msg", msg);
						data.put("ret", ret);
						super.renderJson(response, data.toString());
						return null;
					}
					if (orderInfo.getCard_pay_money().compareTo(new BigDecimal(0)) > 0) {
						orderInfo.setOrder_money(orderInfo.getCard_pay_money().add(orderInfo.getOrder_money()));
						orderInfo.setReal_pay_money(orderInfo.getOrder_money());
						orderInfo.setCard_pay_money(new BigDecimal(0));
						getFacade().getOrderInfoService().modifyOrderInfo(orderInfo);
					}
					order_money = order_money.add(orderInfo.getOrder_money());
				}
			}
			ret = "1";
			data.put("order_money", order_money);
		}
		data.put("msg", msg);
		data.put("ret", ret);
		super.renderJson(response, data.toString());
		return null;
	}

	public ActionForward selectOrderMoneyWhereAddUserId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String add_user_id = (String) dynaBean.get("add_user_id");
		String mobile = (String) dynaBean.get("mobile");
		String order_id = (String) dynaBean.get("order_id");
		JSONObject data = new JSONObject();

		String msg = "", ret = "0";

		// Integer add_user_id = 3044;

		BigDecimal pay_money = new BigDecimal(0);
		BigDecimal money_bi = new BigDecimal(0);
		BigDecimal welfare_pay_money = new BigDecimal(0);
		BigDecimal entp_add_huokuan = new BigDecimal(0);
		String entp_ids = "";

		// String mobiles = "18154110323,18856024138,18709832016,";
		String mobiles = "";
		if (StringUtils.isNotBlank(mobile)) {
			mobiles = mobile;
		}

		String mobileArr[] = mobiles.split(",");

		JSONArray jsonarr = new JSONArray();
		for (int i = 0; i < mobileArr.length; i++) {
			JSONObject jsonobj = new JSONObject();
			UserInfo userInfo = new UserInfo();
			userInfo.getMap().put("ym_id", mobileArr[i]);
			userInfo.setIs_del(0);
			List<UserInfo> ulist = getFacade().getUserInfoService().getUserInfoList(userInfo);
			if (null == ulist || ulist.size() == 0) {
				msg = "用户不存在";
				return returnErr(response, msg, data);
			}
			if (ulist.size() > 1) {
				msg = "查出多个：" + ulist.size() + "个";
				return returnErr(response, msg, data);
			}
			System.out.println("ulist.size():" + ulist.size());
			userInfo = ulist.get(0);

			jsonobj.put("user_id", userInfo.getId());
			jsonobj.put("user_name", userInfo.getUser_name());
			jsonobj.put("mobile", userInfo.getMobile());

			OrderInfo orderInfo = new OrderInfo();
			if (StringUtils.isNotBlank(order_id)) {
				orderInfo.setId(Integer.valueOf(order_id));
			}
			orderInfo.setAdd_user_id(userInfo.getId());
			orderInfo.setOrder_type(Keys.OrderType.ORDER_TYPE_90.getIndex());
			orderInfo.setOrder_state(Keys.OrderState.ORDER_STATE_50.getIndex());
			orderInfo.getMap().put("st_add_date", "2019-05-18 00:00:00");
			orderInfo.getMap().put("en_add_date", "2019-05-18");
			// a.getMap().put("add_user_id", add_user_id);

			List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();

			JSONArray jsonArray = new JSONArray();

			List<OrderInfo> list = getFacade().getOrderInfoService().getOrderInfoList(orderInfo);
			if (list.size() == 0) {
				continue;
			}
			for (OrderInfo cur : list) {

				pay_money = pay_money.add(cur.getMoney_bi()).add(cur.getWelfare_pay_money());
				money_bi = money_bi.add(cur.getMoney_bi());
				welfare_pay_money = welfare_pay_money.add(cur.getWelfare_pay_money());
				OrderInfoDetails b = new OrderInfoDetails();
				b.getMap().put("order_id", cur.getId());

				UserBiRecord c = new UserBiRecord();
				c.setOrder_id(cur.getId());
				c.setBi_type(Keys.BiType.BI_TYPE_300.getIndex());
				c.setBi_chu_or_ru(1);
				c.setBi_get_type(Keys.BiGetType.BI_GET_TYPE_4100.getIndex());
				c = getFacade().getUserBiRecordService().getUserBiRecord(c);

				// List<UserBiRecord> clist = getFacade().getUserBiRecordService().getUserBiRecordList(c);

				// 当前订单给商家增加的货款币
				BigDecimal add_huokuan = new BigDecimal(0);
				JSONObject jsonObj = new JSONObject();
				// for (UserBiRecord userBiRecord : clist) {
				add_huokuan = add_huokuan.add(c.getBi_no());
				// }
				OrderInfo returnOrder = new OrderInfo();
				jsonObj.put("add_huokuan", c.getBi_no());
				jsonObj.put("entp_id", cur.getEntp_id());
				jsonObj.put("entp_name", cur.getEntp_name());

				// 需要将增加的货款币减掉
				// 插入记录
				super.insertUserBiRecord(c.getAdd_user_id(), null, -1, c.getOrder_id(), c.getBi_no(),
						Keys.BiType.BI_TYPE_300.getIndex(), Keys.BiGetType.BI_GET_TYPE_X1.getIndex());
				// 更新用户货款币
				super.updateUserInfoBi(c.getAdd_user_id(), c.getBi_no(), "sub_bi_huokuan");

				jsonArray.add(jsonObj);
				entp_add_huokuan = entp_add_huokuan.add(add_huokuan);
			}

			OrderInfo updateOrder = new OrderInfo();
			updateOrder.setOrder_state(Keys.OrderState.ORDER_STATE_90.getIndex());
			updateOrder.getMap().put("add_user_id", userInfo.getId());
			updateOrder.getMap().put("order_type", Keys.OrderType.ORDER_TYPE_90.getIndex());
			updateOrder.getMap().put("order_state", Keys.OrderState.ORDER_STATE_90.getIndex());
			updateOrder.getMap().put("st_add_date", "2019-05-18 00:00:00");
			updateOrder.getMap().put("en_add_date", "2019-05-18");
			getFacade().getOrderInfoService().modifyOrderInfo(updateOrder);

			System.out.println("pay_money:" + pay_money);
			System.out.println("money_bi:" + money_bi);
			System.out.println("welfare_pay_money:" + welfare_pay_money);

			UserBiRecord q = new UserBiRecord();
			q.setAdd_user_id(userInfo.getId());
			q.setBi_get_type(Keys.BiGetType.BI_OUT_TYPE_10.getIndex());
			q.getMap().put("begin_date", "2019-05-18 00:00:00");
			q.getMap().put("end_date", "2019-05-18");
			if (StringUtils.isNotBlank(order_id)) {
				q.setOrder_id(Integer.valueOf(order_id));
			}
			List<UserBiRecord> qlist = getFacade().getUserBiRecordService().getUserBiRecordList(q);

			BigDecimal record_money = new BigDecimal(0);
			for (UserBiRecord cur : qlist) {
				record_money = record_money.add(cur.getBi_no());
			}
			if (welfare_pay_money.compareTo(new BigDecimal(0)) > 0) {

				// 增加福利金
				// 插入记录
				super.insertUserBiRecord(userInfo.getId(), null, 1, null, record_money,
						Keys.BiType.BI_TYPE_700.getIndex(), Keys.BiGetType.BI_GET_TYPE_X1.getIndex());
				super.insertUserBiRecord(userInfo.getId(), null, 1, null, record_money,
						Keys.BiType.BI_TYPE_100.getIndex(), Keys.BiGetType.BI_GET_TYPE_X1.getIndex());
				// 更新用户货款币
				super.updateUserInfoBi(userInfo.getId(), record_money, "add_bi_welfare");
				super.updateUserInfoBi(userInfo.getId(), record_money, "add_bi_dianzi");
			} else {
				// 增加余额
				// 插入记录
				super.insertUserBiRecord(userInfo.getId(), null, 1, null, record_money,
						Keys.BiType.BI_TYPE_100.getIndex(), Keys.BiGetType.BI_GET_TYPE_X1.getIndex());
				// 更新用户货款币
				super.updateUserInfoBi(userInfo.getId(), record_money, "add_bi_dianzi");
			}

			jsonobj.put("jsonArray", jsonArray);

			jsonobj.put("总共给商家增加的货款币", entp_add_huokuan);
			jsonobj.put("entp_ids", entp_ids);
			jsonobj.put("返还金额", record_money);
			jsonobj.put("支付金额", pay_money);
			jsonobj.put("电子币", money_bi);
			jsonobj.put("福利金", welfare_pay_money);
			jsonarr.add(jsonobj);
		}

		data.put("jsonarr", jsonarr);
		data.put("msg", msg);
		data.put("ret", ret);
		super.renderJson(response, data.toString());
		return null;
	}

	public ActionForward delOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String add_user_id = (String) dynaBean.get("add_user_id");
		JSONObject data = new JSONObject();
		String msg = "", ret = "0";

		if (StringUtils.isBlank(add_user_id)) {
			return returnErr(response, msg, data);
		}

		BigDecimal pay_money = new BigDecimal(0);

		OrderInfo a = new OrderInfo();
		a.setAdd_user_id(Integer.valueOf(add_user_id));
		a.setOrder_type(Keys.OrderType.ORDER_TYPE_90.getIndex());
		// a.getMap().put("add_user_id", add_user_id);
		List<OrderInfo> list = getFacade().getOrderInfoService().getOrderInfoList(a);

		for (OrderInfo cur : list) {

			// pay_money = pay_money.add(cur.getMoney_bi());
			// OrderInfoDetails b = new OrderInfoDetails();
			// b.getMap().put("order_id", cur.getId());
			// getFacade().getOrderInfoDetailsService().removeOrderInfoDetails(b);
		}

		System.out.println("pay_money:" + pay_money);

		OrderInfo del = new OrderInfo();
		del.setOrder_state(Keys.OrderState.ORDER_STATE_90.getIndex());
		del.getMap().put("order_type", Keys.OrderType.ORDER_TYPE_90.getIndex() + "");
		del.getMap().put("add_user_id", add_user_id);
		getFacade().getOrderInfoService().removeOrderInfo(del);

		data.put("order_count", list.size());
		// data.put("pay_money", pay_money);
		data.put("msg", msg);
		data.put("ret", ret);
		super.renderJson(response, data.toString());
		return null;
	}

	public ActionForward getWeixinToKen(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = "0", msg = "";
		JSONObject datas = new JSONObject();

		logger.info("====autoGetWeixinToken=====");
		// Map<String, String> params = new HashMap<String, String>();
		// params.put("appid", Keys.APP_ID);
		// params.put("secret", "ee8905decfeb5ec2d9316a6d54b2bff7");
		// params.put("grant_type", "client_credential");
		// String url = "https://api.weixin.qq.com/cgi-bin/token";
		// String returnResult = HttpUtils.sendPost(url, params);
		// System.out.println(returnResult);
		// if (StringUtils.isNotBlank(returnResult)) {
		// JSONObject data = JSONObject.parseObject(returnResult);
		// String access_token = (String) data.get("access_token");
		// System.out.println(access_token);
		// data.put("access_token", access_token);
		// Keys.weixin_token = access_token;
		// }

		getFacade().getAutoRunService().autoGetWeixinToken();

		datas.put("Keys.weixin_token", Keys.weixin_token_key);
		logger.info("====autoGetWeixinToken=====end");

		return returnAjaxData(response, code, msg, datas);

	}

	public JSONObject sendTemplateMsgGroup(String toUser, String template_id, String url_template, JSONObject data) {
		JSONObject req = new JSONObject();
		req.put("touser", toUser);
		req.put("topcolor", "#FF0000");
		req.put("template_id", template_id);
		req.put("url", url_template);
		req.put("data", data);
		String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + getWeixinToKenBase();
		JSONObject resp = HttpClientUtils.postJsonDataForJsonResult(url, req.toString());
		return resp;
	}

}
