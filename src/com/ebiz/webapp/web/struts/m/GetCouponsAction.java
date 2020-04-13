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
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.YhqInfo;
import com.ebiz.webapp.domain.YhqInfoSon;
import com.ebiz.webapp.web.Keys;

/**
 * @author Wu,Yang
 * @version 2010-8-24
 */
public class GetCouponsAction extends MBaseWebAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}
		request.setAttribute("header_title", "我的优惠券");

		return new ActionForward("/../m/MGetCoupons/list.jsp");
	}

	public ActionForward getAjaxData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String code = "0", msg = "";
		JSONObject data = new JSONObject();
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			msg = "用户不存在";
			return showTipNotLogin(mapping, form, request, response, msg);
		}
		YhqInfoSon entity = new YhqInfoSon();
		entity.setLink_user_id(ui.getId());
		List<YhqInfoSon> yhqInfoSonlist = super.getFacade().getYhqInfoSonService().getYhqInfoSonList(entity);
		if (null != yhqInfoSonlist) {
			for (YhqInfoSon temp : yhqInfoSonlist) {
				YhqInfo yhqinfo = new YhqInfo();
				yhqinfo.setId(temp.getLink_id());
				yhqinfo.setIs_del(0);
				List<YhqInfo> yhqInfolist = super.getFacade().getYhqInfoService().getYhqInfoList(yhqinfo);
				temp.getMap().put("yhqInfo", yhqInfolist);
			}
		}
		data.put("yhqInfoSonlist", yhqInfoSonlist);
		code = "1";
		super.returnInfo(response, code, msg, data);
		return null;
	}

	public ActionForward useConpons(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String yhq_id = (String) dynaBean.get("yhq_id");
		UserInfo ui = super.getUserInfoFromSession(request);

		String code = "0", msg = "";
		JSONObject datas = new JSONObject();

		if (StringUtils.isBlank(yhq_id)) {
			msg = "参数有误！";
			super.ajaxReturnInfo(response, code, msg, datas);
			return null;
		}
		YhqInfoSon coupons = new YhqInfoSon();
		coupons.setLink_id(Integer.valueOf(yhq_id));
		coupons.setLink_user_id(ui.getId());
		coupons = super.getFacade().getYhqInfoSonService().getYhqInfoSon(coupons);
		if (null != coupons) {
			datas.put("id", coupons.getOwn_entp_id());
		} else {
			msg = "找不到该优惠券信息！";
			super.ajaxReturnInfo(response, code, msg, datas);
			return null;
		}
		code = "1";
		super.returnInfo(response, code, msg, datas);
		return null;
	}

	public ActionForward getYhq(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String yhq_id = (String) dynaBean.get("yhq_id");

		JSONObject datas = new JSONObject();
		String msg = "领取失败！", ret = "-1";

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			msg = "您还未登录，请先登录系统！";
			return super.returnAjaxData(response, ret, msg, datas);
		}

		if (StringUtils.isBlank(yhq_id)) {
			msg = "参数有误！";
			return super.returnAjaxData(response, ret, msg, datas);
		}
		YhqInfo yhqInfo = new YhqInfo();
		yhqInfo.setId(Integer.valueOf(yhq_id));
		yhqInfo.setIs_del(0);
		yhqInfo = super.getFacade().getYhqInfoService().getYhqInfo(yhqInfo);
		if (null == yhqInfo) {
			msg = "优惠券不存在！";
			return super.returnAjaxData(response, ret, msg, datas);
		}
		if (yhqInfo.getIs_limited() == 1 && yhqInfo.getYhq_number_now() >= yhqInfo.getLimited_number()) {// 判断优惠券有没有领取完
			msg = "优惠券已经被领完了哦！";
			return super.returnAjaxData(response, ret, msg, datas);
		}

		YhqInfoSon son = new YhqInfoSon();
		son.setLink_id(Integer.valueOf(yhq_id));
		son.setLink_user_id(ui.getId());
		son = getFacade().getYhqInfoSonService().getYhqInfoSon(son);
		if (null != son) {
			msg = "您已经领过了哦！";
			return super.returnAjaxData(response, ret, msg, datas);
		}

		YhqInfoSon insertYhq = new YhqInfoSon();
		insertYhq.setLink_id(Integer.valueOf(yhq_id));
		insertYhq.setYhq_state(Keys.YhqState.YHQ_STATE_10.getIndex());
		insertYhq.setLink_user_id(ui.getId());
		insertYhq.setOwn_entp_id(yhqInfo.getOwn_entp_id());
		insertYhq.setGet_date(new Date());
		insertYhq.setIs_used(0);
		// insertYhq.setComm_id(commInfo.getId());
		insertYhq.setYhq_start_date(yhqInfo.getYhq_start_date());
		insertYhq.setYhq_end_date(yhqInfo.getYhq_end_date());
		insertYhq.setOwn_entp_name(yhqInfo.getOwn_entp_name());
		// int count = super.getFacade().getYhqInfoSonService().createYhqInfoSon(yhqInfoSon);
		yhqInfo.getMap().put("number_now_reduce", true);
		if (yhqInfo.getLimited_number() != 0) {
			yhqInfo.getMap().put("coupons_now", true);
		}
		yhqInfo.getMap().put("yhqInfoSon", insertYhq);
		int i = super.getFacade().getYhqInfoService().modifyYhqInfo(yhqInfo);
		if (i > 0) {
			ret = "1";
			msg = "领取成功！";
			return super.returnAjaxData(response, ret, msg, datas);
		}
		return super.returnAjaxData(response, ret, msg, datas);

	}

	public ActionForward getYhqQrcode(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String yhq_id = (String) dynaBean.get("id");

		String msg = "领取失败！";

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}
		if (null == yhq_id) {
			msg = "参数有误";
			return super.showTipMsg(mapping, form, request, response, msg, super.getCtxPath(request, true)
					+ "/m/MMyHome.do");
		}
		YhqInfo yhqInfo = new YhqInfo();
		yhqInfo.setId(Integer.valueOf(yhq_id));
		yhqInfo.setIs_del(0);
		yhqInfo = super.getFacade().getYhqInfoService().getYhqInfo(yhqInfo);
		if (null == yhqInfo) {
			msg = "优惠券不存在！";
			request.setAttribute("msg", msg);
			return super.showTipMsg(mapping, form, request, response, msg, super.getCtxPath(request, true)
					+ "/m/MMyHome.do");
		}
		if (yhqInfo.getIs_limited() == 1 && yhqInfo.getYhq_number_now() >= yhqInfo.getLimited_number()) {// 判断优惠券有没有领取完
			msg = "优惠券已经被领完了哦！";
			request.setAttribute("msg", msg);
			return super.showTipMsg(mapping, form, request, response, msg, super.getCtxPath(request, true)
					+ "/m/MMyHome.do");
		}

		YhqInfoSon son = new YhqInfoSon();
		son.setLink_id(Integer.valueOf(yhq_id));
		son.setLink_user_id(ui.getId());
		son = getFacade().getYhqInfoSonService().getYhqInfoSon(son);
		if (null != son) {
			msg = "您已经领过了哦！";
			return super.showTipMsg(mapping, form, request, response, msg, super.getCtxPath(request, true)
					+ "/m/MMyHome.do");
		}

		YhqInfoSon insertYhq = new YhqInfoSon();
		insertYhq.setLink_id(Integer.valueOf(yhq_id));
		insertYhq.setYhq_state(Keys.YhqState.YHQ_STATE_10.getIndex());
		insertYhq.setLink_user_id(ui.getId());
		insertYhq.setOwn_entp_id(yhqInfo.getOwn_entp_id());
		insertYhq.setGet_date(new Date());
		insertYhq.setIs_used(0);
		insertYhq.setYhq_start_date(yhqInfo.getYhq_start_date());
		insertYhq.setYhq_end_date(yhqInfo.getYhq_end_date());
		insertYhq.setOwn_entp_name(yhqInfo.getOwn_entp_name());
		yhqInfo.getMap().put("number_now_reduce", true);
		if (yhqInfo.getLimited_number() != 0) {
			yhqInfo.getMap().put("coupons_now", true);
		}
		yhqInfo.getMap().put("yhqInfoSon", insertYhq);
		int i = super.getFacade().getYhqInfoService().modifyYhqInfo(yhqInfo);
		if (i > 0) {
			msg = "领取成功！";
			request.setAttribute("msg", msg);
			return super.showTipMsg(mapping, form, request, response, msg, super.getCtxPath(request, true)
					+ "/m/GetCoupons.do");
		}
		return super.showTipMsg(mapping, form, request, response, msg, super.getCtxPath(request, true)
				+ "/m/MMyHome.do");
	}
}
