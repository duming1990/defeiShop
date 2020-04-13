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
import com.ebiz.webapp.domain.CardGenHis;
import com.ebiz.webapp.domain.CardInfo;
import com.ebiz.webapp.domain.CartInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.DESPlus;

public class MMyWelfareCardAction extends MBaseWebAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.index(mapping, form, request, response);
	}

	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("header_title", "我的福利卡");
		UserInfo ui = super.getUserInfoFromSession(request);
		// 清空购物车
		CartInfo cartInfo = new CartInfo();
		cartInfo.setIs_del(0);
		cartInfo.setUser_id(ui.getId());
		cartInfo.getMap().put("cart_type", Keys.CartType.CART_TYPE_20.getIndex());
		getFacade().getCartInfoService().removeCartInfo(cartInfo);
		return new ActionForward("/MMyWelfareCard/list.jsp");
	}

	public ActionForward getCardInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject data = new JSONObject();
		String msg = "", code = "0";

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			msg = "您还未登录，请先登录系统！";
			data.put("msg", msg);
			data.put("code", code);
			super.renderJson(response, data.toString());
			return null;
		}

		CardInfo card = new CardInfo();
		card.setUser_id(ui.getId());
		card.setIs_del(0);
		card.getMap().put("card_cash_gt", 0);// 可用余额大于0
		card.getMap().put("end_date_ge", new Date());// 有效期大于当前时间
		logger.info("================" + sdFormat_ymd.format(new Date()));
		List<CardInfo> cardList = getFacade().getCardInfoService().getCardInfoList(card);
		data.put("cardList", cardList);
		data.put("code", 1);
		super.renderJson(response, data.toString());
		return null;
	}

	public ActionForward activeCard(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject data = new JSONObject();
		String msg = "", code = "0";

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			msg = "您还未登录，请先登录系统！";
			data.put("msg", msg);
			data.put("code", code);
			super.renderJson(response, data.toString());
			return null;
		}

		DynaBean dynaBean = (DynaBean) form;
		String card_no = (String) dynaBean.get("card_no");
		String card_pwd = (String) dynaBean.get("card_pwd");

		if (StringUtils.isBlank(card_no)) {
			msg = "请输入卡号！";
			data.put("msg", msg);
			data.put("code", code);
			super.renderJson(response, data.toString());
			return null;
		}
		if (StringUtils.isBlank(card_pwd)) {
			msg = "请输入密码！";
			data.put("msg", msg);
			data.put("code", code);
			super.renderJson(response, data.toString());
			return null;
		}

		// 验证卡号是否存在
		CardInfo card = new CardInfo();
		card.setCard_no(card_no);
		card.setIs_del(0);
		card = getFacade().getCardInfoService().getCardInfo(card);
		if (null == card) {
			msg = "卡号不存在！";
			data.put("msg", msg);
			data.put("code", code);
			super.renderJson(response, data.toString());
			return null;
		}
		// 验证密码是否正确
		DESPlus DES = new DESPlus();
		if (!DES.decrypt(card.getCard_pwd()).equals(card_pwd)) {
			msg = "密码错误！";
			data.put("msg", msg);
			data.put("code", code);
			super.renderJson(response, data.toString());
			return null;
		}

		// 验证是否已被激活
		if (card.getCard_state().intValue() != Keys.CARD_STATE.CARD_STATE_0.getIndex()) {
			msg = "该卡已被激活或作废！";
			data.put("msg", msg);
			data.put("code", code);
			super.renderJson(response, data.toString());
			return null;
		}

		// 验证是否过期
		if (card.getEnd_date().before(new Date())) {
			msg = "该卡已过期！";
			data.put("msg", msg);
			data.put("code", code);
			super.renderJson(response, data.toString());
			return null;
		}

		// 激活卡号
		CardInfo cardInfo = new CardInfo();
		cardInfo.setId(card.getId());
		cardInfo.setUser_id(ui.getId());
		cardInfo.setCard_state(Keys.CARD_STATE.CARD_STATE_1.getIndex());
		cardInfo.setUser_date(new Date());
		int flag = getFacade().getCardInfoService().modifyCardInfo(cardInfo);
		if (flag <= 0) {
			msg = "激活失败！";
			data.put("msg", msg);
			data.put("code", code);
			super.renderJson(response, data.toString());
			return null;
		}

		CardGenHis cGenHis = new CardGenHis();
		cGenHis.setId(card.getGen_id());
		CardGenHis cHis = getFacade().getCardGenHisService().getCardGenHis(cGenHis);

		if ((cHis.getUsed_count() + 1) == cHis.getGen_count().intValue()) {

			cGenHis.setInfo_state(Keys.CARD_INFO_STATE.CARD_INFO_STATE_2.getIndex());

		} else if ((cHis.getUsed_count() + 1) > cHis.getGen_count().intValue()) {

			msg = "激活失败,福利卡超过发放数量！";
			data.put("msg", msg);
			data.put("code", code);
			super.renderJson(response, data.toString());
			return null;

		} else {
			cGenHis.setInfo_state(Keys.CARD_INFO_STATE.CARD_INFO_STATE_1.getIndex());
		}

		cGenHis.getMap().put("add_used_count", 1);
		getFacade().getCardGenHisService().modifyCardGenHis(cGenHis);

		msg = "激活成功！";
		data.put("msg", msg);
		data.put("code", 1);
		data.put("cardInfo", card);
		super.renderJson(response, data.toString());
		return null;
	}
}
