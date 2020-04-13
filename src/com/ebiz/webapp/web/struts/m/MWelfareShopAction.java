package com.ebiz.webapp.web.struts.m;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.alibaba.fastjson.JSONObject;
import com.ebiz.webapp.domain.CardInfo;
import com.ebiz.webapp.domain.CardPIndex;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.MBaseLink;
import com.ebiz.webapp.domain.NewsInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;

public class MWelfareShopAction extends MBaseWebAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.index(mapping, form, request, response);
	}

	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ActionForward("/MWelfareShop/index.jsp");
	}

	public ActionForward getCommList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
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
		String service_id = (String) dynaBean.get("service_id");
		String card_id = (String) dynaBean.get("card_id");

		if (StringUtils.isBlank(card_id)) {
			msg = "参数错误！";
			data.put("msg", msg);
			data.put("code", code);
			super.renderJson(response, data.toString());
			return null;
		}
		// 将福利卡信息塞入session
		HttpSession session = request.getSession();
		session.setAttribute("card_id", card_id);

		CardInfo card = new CardInfo();
		card.setId(Integer.valueOf(card_id));
		card.setIs_del(0);
		card = getFacade().getCardInfoService().getCardInfo(card);
		if (card == null) {
			msg = "该卡已停用！";
			data.put("msg", msg);
			data.put("code", code);
			super.renderJson(response, data.toString());
			return null;
		}
		CardPIndex cIndex = new CardPIndex();
		cIndex.setCard_apply_id(card.getCard_apply_id());
		List<CardPIndex> cindexList = getFacade().getCardPIndexService().getCardPIndexList(cIndex);
		if (cindexList == null || cindexList.size() <= 0) {
			msg = "该卡信息有误！";
			data.put("msg", msg);
			data.put("code", code);
			super.renderJson(response, data.toString());
			return null;
		}
		String service_id_in = "";
		for (CardPIndex temp : cindexList) {
			service_id_in += temp.getService_id() + ",";
		}

		service_id_in = service_id_in.substring(0, service_id_in.length() - 1);

		CommInfo comm = new CommInfo();
		comm.getMap().put("service_id_in", service_id_in);
		comm.setIs_del(0);
		comm.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		comm.setIs_sell(1);
		comm.setIs_has_tc(1);
		comm.getMap().put("not_out_sell_time", true);
		List<CommInfo> commList = getFacade().getCommInfoService().getWelfareCommInfoList(comm);
		data.put("commList", commList);
		data.put("code", 1);
		super.renderJson(response, data.toString());
		return null;
	}

	public ActionForward getBannerImgs(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("======getBannerImgs======");
		JSONObject data = new JSONObject();
		String msg = "", code = "0";
		// 轮播图
		List<MBaseLink> mBaseLinkList10 = this.getMBaseLinkList(10, 4, "true");
		data.put("bannerImgs", mBaseLinkList10);

		// 挑夫头条
		List<NewsInfo> newsList = super.getNewsInfoList(request, "1015012000", 6);
		data.put("newsList", newsList);
		data.put("code", 1);
		super.renderJson(response, data.toString());
		return null;
	}

	/** 商品信息 */
	public ActionForward getCommInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String ymid = (String) dynaBean.get("ymid");

		if (StringUtils.isBlank(id)) {
			String msg = "传入的商品参数为空";
			return super.showTipMsg(mapping, form, request, response, msg);
		}

		CommInfo commInfo = super.getCommInfo(Integer.valueOf(id));

		if (null == commInfo) {
			String msg = "未查询该商品，或者该商品配置有问题！";
			return super.showTipMsg(mapping, form, request, response, msg);
		}

		request.setAttribute("commInfo", commInfo);
		logger.info("==========================");
		if (null != commInfo.getOwn_entp_id()) {
			request.setAttribute("entpInfo", super.getEntpInfo(commInfo.getOwn_entp_id()));
		}
		String share_img = commInfo.getMain_pic();
		String ctx = super.getCtxPath(request, false);

		String share_url = ctx + "/m/MWelfareShop.do?id=" + id;

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null != ui) {
			share_url = share_url + "&ymid=" + ui.getUser_name();
		}
		if (StringUtils.isNotBlank(ymid)) {// 如果ymid不为空，则说明是分享的商品链接，将ymid塞入session
			HttpSession session = request.getSession();
			session.setAttribute("ymid", ymid);
		}
		request.setAttribute("share_url", share_url);

		String share_imgs[] = share_img.split("\\.");

		String imgUrl = share_imgs[0] + "_400x400." + share_imgs[1];

		request.setAttribute("share_img", "http://" + Keys.app_domain.concat("/").concat(imgUrl));
		super.setJsApiTicketRetrunParamToSession(request);
		return new ActionForward("/MWelfareShop/view.jsp");
	}
}
