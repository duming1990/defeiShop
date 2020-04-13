/**
 * 
 */
package com.ebiz.webapp.web.struts.service;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.alibaba.fastjson.JSONObject;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.struts.BaseWebAction;

/**
 * @author 王志雄
 * @date 2018年8月24日
 */
public class WeiXinAjaxAction extends BaseWebAction {

	public ActionForward reckonRebateAndAid(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String order_id = (String) dynaBean.get("order_id");

		if (StringUtils.isNotBlank(order_id) && GenericValidator.isLong(order_id)) {
			OrderInfo oi = new OrderInfo();
			oi.setId(Integer.valueOf(order_id));
			oi.setEntp_huokuan_bi(new BigDecimal(0));
			oi.setOrder_state(Keys.OrderState.ORDER_STATE_10.getIndex());
			oi = super.getFacade().getOrderInfoService().getOrderInfo(oi);
			if (null != oi) {
				super.getFacade().getAutoRunService().autoReckonRebateAndAid(Integer.valueOf(order_id));
			} else {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("ret", 0);
				jsonObject.put("msg", "订单错误");
				super.renderJson(response, jsonObject.toString());
				return null;
			}

		} else {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("ret", 0);
			jsonObject.put("msg", "参数错误");
			super.renderJson(response, jsonObject.toString());
			return null;
		}

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("ret", 1);
		jsonObject.put("msg", "成功");
		super.renderJson(response, jsonObject.toString());
		return null;
	}

	public ActionForward createCommInfoCode(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (StringUtils.isBlank(id) || !GenericValidator.isLong(id)) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("ret", 0);
			jsonObject.put("msg", "参数不正确");
			super.renderJson(response, jsonObject.toString());
			return null;
		}

		createCommInfoCode(request, Integer.valueOf(id));

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("ret", 1);
		jsonObject.put("msg", "成功");
		super.renderJson(response, jsonObject.toString());
		return null;
	}

}
