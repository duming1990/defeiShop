package com.ebiz.webapp.web.struts.entp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ebiz.webapp.domain.BaseProvince;
import com.ebiz.webapp.domain.CartInfo;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.CommTczhAttribute;
import com.ebiz.webapp.domain.CommTczhPrice;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.ScEntpComm;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.struts.BaseAction;

/**
 * @author Wu,Yang
 * @version 2011-04-27
 */
public class CsAjaxAction extends BaseAction {

	public ActionForward saveCartInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// DynaBean dynaBean = (DynaBean) form;
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
		entity.setUser_id(ui.getId());
		Integer commInfoCount = entity.getPd_count() != null ? entity.getPd_count() : 0; // 本次购买数量
		entity.setPd_count(null);
		CartInfo cartInfoTemp = getFacade().getCartInfoService().getCartInfo(entity);
		if (null != cartInfoTemp) {// 更新
			CartInfo cartInfo = new CartInfo();
			cartInfo.setId(cartInfoTemp.getId());
			cartInfo.setPd_count(cartInfoTemp.getPd_count() + commInfoCount);
			getFacade().getCartInfoService().modifyCartInfo(cartInfo);
			cart_id = cartInfoTemp.getId();
		} else {// 插入
			entity.setPd_count(entity.getPd_count() != null ? entity.getPd_count() : 1);
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

	public ActionForward delCart(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String cart_id = (String) dynaBean.get("cart_id");

		StringBuffer result = new StringBuffer();
		result.append("{\"result\":");
		if (StringUtils.isBlank(cart_id)) {
			result.append("false");
		} else {
			CartInfo entity = new CartInfo();
			entity.setId(Integer.valueOf(cart_id));
			getFacade().getCartInfoService().removeCartInfo(entity);
			result.append("true");
		}
		result.append("}");
		super.renderJson(response, result.toString());
		return null;

	}

	public ActionForward getPindexAndNameJsonObjectByParIndex(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String par_index = (String) dynaBean.get("par_index");
		if (!GenericValidator.isLong(par_index)) {
			super.renderJson(response, "");
			return null;
		}

		BaseProvince bplf = new BaseProvince();
		bplf.setPar_index(Long.valueOf(par_index));
		List<BaseProvince> bplfList = super.getFacade().getBaseProvinceService().getBaseProvinceList(bplf);
		if (null == bplfList || 0 == bplfList.size()) {
			super.renderJson(response, "");
			return null;
		}
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (BaseProvince t : bplfList) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("p_index", t.getP_index().toString());
			map.put("p_name", t.getP_name());
			list.add(map);
		}

		super.renderJson(response, JSON.toJSONString(list));
		return null;
	}

	public ActionForward addFav(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String sc_type = (String) dynaBean.get("sc_type");
		String link_id = (String) dynaBean.get("link_id");

		if (!GenericValidator.isLong(link_id)) {
			super.renderJson(response, "");
			return null;
		}
		if (!GenericValidator.isLong(sc_type)) {
			super.renderJson(response, "");
			return null;
		}

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			super.renderJson(response, "");
			return null;
		}

		StringBuffer result = new StringBuffer();
		result.append("{\"result\":");
		// 先查询是否已经收藏过了
		ScEntpComm scEntpComm = new ScEntpComm();
		scEntpComm.setAdd_user_id(ui.getId());
		scEntpComm.setLink_id(Integer.valueOf(link_id));
		scEntpComm.setSc_type(Integer.valueOf(sc_type));
		scEntpComm = super.getFacade().getScEntpCommService().getScEntpComm(scEntpComm);
		if (null != scEntpComm) {

			ScEntpComm sec = new ScEntpComm();
			sec.getMap().put("add_user_id", ui.getId());
			sec.getMap().put("link_id", link_id);
			sec.getMap().put("sc_type", sc_type);
			if (scEntpComm.getIs_del().intValue() == 0) {
				sec.setIs_del(1);
				result.append("2");// 取消收藏
				getFacade().getScEntpCommService().modifyScEntpComm(sec);
			} else {
				result.append("1");// 收藏
				sec.setIs_del(0);
				getFacade().getScEntpCommService().modifyScEntpComm(sec);
			}

		} else {
			ScEntpComm scEntpComm2 = new ScEntpComm();
			if (sc_type.equals("1")) {// 企业
				EntpInfo entpInfo = new EntpInfo();
				entpInfo.setId(Integer.valueOf(link_id));
				entpInfo.setIs_del(0);
				entpInfo = super.getFacade().getEntpInfoService().getEntpInfo(entpInfo);
				if (null != entpInfo) {
					scEntpComm2.setTitle_name(entpInfo.getEntp_name());
				}

			} else if (sc_type.equals("2")) {
				CommInfo commInfo = new CommInfo();
				commInfo.setId(Integer.valueOf(link_id));
				commInfo.setIs_del(0);
				commInfo = super.getFacade().getCommInfoService().getCommInfo(commInfo);
				if (null != commInfo) {
					scEntpComm2.setTitle_name(commInfo.getComm_name());
				}
			}

			scEntpComm2.setAdd_user_id(ui.getId());
			scEntpComm2.setLink_id(Integer.valueOf(link_id));
			scEntpComm2.setSc_type(Integer.valueOf(sc_type));
			scEntpComm2.setAdd_date(new Date());
			Integer id = super.getFacade().getScEntpCommService().createScEntpComm(scEntpComm2);
			if (id > 0) {
				result.append("1");
			}
		}
		result.append("}");
		super.renderJson(response, result.toString());
		return null;
	}

	public ActionForward getTcPrice(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String attr_sonIds = (String) dynaBean.get("attr_sonIds");

		JSONObject jsonObject = new JSONObject();
		String ret = "0";
		if (StringUtils.isBlank(attr_sonIds)) {
			jsonObject.put("ret", ret);
			super.renderJson(response, jsonObject.toString());
			return null;
		}

		ret = "1";
		CommTczhPrice commTczhPrice = new CommTczhPrice();
		commTczhPrice.setId(Integer.valueOf(attr_sonIds));
		commTczhPrice = super.getFacade().getCommTczhPriceService().getCommTczhPrice(commTczhPrice);
		if (null != commTczhPrice) {
			jsonObject.put("comm_price", commTczhPrice.getComm_price());
			jsonObject.put("inventory", commTczhPrice.getInventory());
			jsonObject.put("orig_price", commTczhPrice.getOrig_price());
			if (commTczhPrice.getCost_price() != null) {
				jsonObject.put("cost_price", commTczhPrice.getCost_price());
			}
			if (commTczhPrice.getBuyer_limit_num() != null) {
				jsonObject.put("buyer_limit_num", (commTczhPrice.getBuyer_limit_num()));
			}
		}

		jsonObject.put("comm_tczh_id", attr_sonIds);
		jsonObject.put("ret", ret);
		super.renderJson(response, jsonObject.toString());
		return null;
	}

	public ActionForward getTcPriceByTcId(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String tczh_id = (String) dynaBean.get("tczh_id");

		JSONObject datas = new JSONObject();
		String code = "0", msg = "";
		if (StringUtils.isBlank(tczh_id)) {
			msg = "参数有误";
			super.ajaxReturnInfo(response, code, msg, datas);
			return null;
		}
		CommTczhPrice commTczhPrice = new CommTczhPrice();
		commTczhPrice.setId(Integer.valueOf(tczh_id));
		commTczhPrice = super.getFacade().getCommTczhPriceService().getCommTczhPrice(commTczhPrice);
		if (null == commTczhPrice) {
			msg = "未查找到该套餐";
			super.ajaxReturnInfo(response, code, msg, datas);
			return null;
		}
		code = "1";
		datas.put("commTczhPrice", commTczhPrice);
		super.ajaxReturnInfo(response, code, msg, datas);
		return null;
	}
}
