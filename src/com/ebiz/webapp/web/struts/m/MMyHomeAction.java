package com.ebiz.webapp.web.struts.m;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aiisen.weixin.ApiUrlConstants;
import com.aiisen.weixin.CommonApi;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.BaseData;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.MsgReceiver;
import com.ebiz.webapp.domain.OrderReturnInfo;
import com.ebiz.webapp.domain.ScEntpComm;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.UserRelationPar;
import com.ebiz.webapp.domain.VillageContactList;
import com.ebiz.webapp.domain.VillageMember;
import com.ebiz.webapp.web.Keys;

/**
 * @author Wu,Yang
 * @version 2011-12-14
 */
public class MMyHomeAction extends MBaseWebAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// 这个地方判断如果是微信扫描过来的，如果该没有登录的账号直接生成一个账号
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			if (super.isWeixin(request)) {
				HttpSession session = request.getSession();
				String ymid = (String) session.getAttribute("ymid");
				String ctx = super.getCtxPath(request, false);
				String return_url = ctx + "/m/MMyHome.do";

				StringBuilder link = new StringBuilder();
				String scope = "snsapi_userinfo";
				String state = "";

				StringBuffer server = new StringBuffer();
				server.append(request.getHeader("host")).append(request.getContextPath());
				request.setAttribute("server_domain", server.toString());
				String redirectUri = ctx.concat("/weixin/WeixinLogin.do?method=onlyCreateUserAndRedirect");
				redirectUri += "&return_url=" + URLEncoder.encode(return_url, "UTF-8");
				if (StringUtils.isNotBlank(ymid)) {
					redirectUri += "&ymid=" + ymid;
				}
				link.append(ApiUrlConstants.OAUTH2_LINK + "?appid=" + CommonApi.APP_ID)
						.append("&redirect_uri=" + URLEncoder.encode(redirectUri, "UTF-8"))
						.append("&response_type=code").append("&scope=" + scope).append("&state=" + state)
						.append("#wechat_redirect");
				response.sendRedirect(link.toString());
			}
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}
		return this.index(mapping, form, request, response);
	}

	public ActionForward getAjaxData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = "1", msg = "";
		JSONObject datas = new JSONObject();
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			code = "-1";
			msg = "您还未登录，请先登录系统！";
			super.returnInfo(response, code, msg, datas);
			return null;
		}

		// 用户信息
		UserInfo userInfo = new UserInfo();
		userInfo.setId(ui.getId());
		userInfo = getFacade().getUserInfoService().getUserInfo(userInfo);
		if (null == userInfo) {
			msg = "用户不存在";
			return showTipNotLogin(mapping, form, request, response, msg);
		}
		datas.put("userInfo", userInfo);

		// 收藏数量
		ScEntpComm scEntpComm = new ScEntpComm();
		scEntpComm.setAdd_user_id(ui.getId());
		scEntpComm.setIs_del(0);
		int count = getFacade().getScEntpCommService().getScEntpCommCount(scEntpComm);
		datas.put("sc_count", count);
		// 关注数量
		int guanzhu_count = getGuanZhuCount(userInfo);
		datas.put("guanzhu_count", guanzhu_count);

		datas.put("upLevelNeedPayMoney", super.getSysSetting(Keys.upLevelNeedPayMoney));

		// 订单类型数量
		datas.put("dai_fukuan_num",
				super.getOrderInfoNum(Keys.OrderState.ORDER_STATE_0.getIndex(), userInfo.getId(), null, null));
		datas.put("dai_fahuo_num",
				super.getOrderInfoNum(Keys.OrderState.ORDER_STATE_10.getIndex(), userInfo.getId(), null, null));
		datas.put("dai_shouhuo_num",
				super.getOrderInfoNum(Keys.OrderState.ORDER_STATE_20.getIndex(), userInfo.getId(), null, null));
		datas.put("dai_pingjia_num",
				super.getOrderInfoNum(Keys.OrderState.ORDER_STATE_40.getIndex(), userInfo.getId(), null, null, 0));
		datas.put("tuihuo_num",
				super.getOrderInfoNum(Keys.OrderState.ORDER_STATE_15.getIndex(), userInfo.getId(), null, null));

		// 消息数量
		MsgReceiver entity = new MsgReceiver();
		entity.setReceiver_user_id(ui.getId());
		entity.setIs_read(0);
		int msg_count = getFacade().getMsgReceiverService().getMsgReceiverCount(entity);
		datas.put("msg_count", msg_count);

		// 我的粉丝
		VillageContactList a = new VillageContactList();
		a.setContact_user_id(userInfo.getId());
		a.setIs_del(0);
		int fensi_count = getFacade().getVillageContactListService().getVillageContactListCount(a);
		datas.put("fensi_count", fensi_count);

		// 加入的村
		VillageMember myJoinVillage = new VillageMember();
		myJoinVillage.setAdd_user_id(ui.getId());
		myJoinVillage.getMap().put("audit_state_in", "1,2");
		myJoinVillage.setIs_del(0);
		Integer myJoinVillageCount = getFacade().getVillageMemberService().getVillageMemberCount(myJoinVillage);
		datas.put("myJoinVillageCount", myJoinVillageCount);
		List<VillageMember> myJoinVillageList = getFacade().getVillageMemberService().getVillageMemberList(
				myJoinVillage);
		datas.put("myJoinVillageList", myJoinVillageList);

		super.returnInfo(response, code, msg, datas);
		return null;

	}

	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// 积分 余额

		DynaBean dynaBean = (DynaBean) form;
		boolean is_app = super.isApp(request);
		if (is_app) {
			super.setIsAppToCookie(request, response);
		} else {
			// return super.toAppDownload(mapping, form, request, response);
		}

		request.setAttribute("header_title", "我的账号");
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			// String msg = "您还未登录，请先登录系统！";
			// return showTipNotLogin(mapping, form, request, response, msg);
			request.setAttribute("tianxiehaoma", -1);
			return mapping.findForward("list");
		} else {
			// 手机是否绑定
			UserInfo userInfo = new UserInfo();
			userInfo.setId(ui.getId());
			userInfo = getFacade().getUserInfoService().getUserInfo(userInfo);

			if (null == userInfo) {
				request.setAttribute("tianxiehaoma", -1);
				return mapping.findForward("list");
			}

			if (null != userInfo.getMobile() && StringUtils.isNotBlank(userInfo.getMobile())) {
				request.setAttribute("tianxiehaoma", 1);
			} else {
				request.setAttribute("tianxiehaoma", 0);
			}
		}

		if (!is_app) {
			request.setAttribute("titleSideName", "<i class=\"fa fa-tv\"></i> " + Keys.TopBtns.INDEX.getName());
		}

		// 用户信息
		UserInfo userInfo = new UserInfo();
		userInfo.setId(ui.getId());
		userInfo = getFacade().getUserInfoService().getUserInfo(userInfo);
		if (null == userInfo) {
			String msg = "用户不存在";
			return showTipNotLogin(mapping, form, request, response, msg);
		}
		// 安全等级
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
		request.setAttribute("userInfo", userInfo);

		request.setAttribute("percent", percent);
		UserRelationPar entity = new UserRelationPar();
		entity.getMap().put("my_lower_by_user_par_id", ui.getId());

		Integer recordCountAll = getFacade().getUserRelationParService().getUserRelationParCount(entity);

		entity = new UserRelationPar();
		entity.getMap().put("my_lower_by_user_par_id", ui.getId());
		entity.getMap().put("my_lower_by_level", 1);

		Integer recordCount1 = getFacade().getUserRelationParService().getUserRelationParCount(entity);

		entity = new UserRelationPar();
		entity.getMap().put("my_lower_by_user_par_id", ui.getId());
		entity.getMap().put("my_lower_by_level", 2);

		Integer recordCount2 = getFacade().getUserRelationParService().getUserRelationParCount(entity);

		entity = new UserRelationPar();
		entity.getMap().put("my_lower_by_user_par_id", ui.getId());
		entity.getMap().put("my_lower_by_level", 3);

		Integer recordCount3 = getFacade().getUserRelationParService().getUserRelationParCount(entity);

		request.setAttribute("recordCountAll", recordCountAll);
		request.setAttribute("recordCount1", recordCount1);
		request.setAttribute("recordCount2", recordCount2);
		request.setAttribute("recordCount3", recordCount3);

		// 用户等级
		BaseData uiBaseData = new BaseData();
		uiBaseData.setType(Keys.BASE_DATA_ID.BASE_DATA_ID_200.getIndex());
		uiBaseData.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		uiBaseData.setId(userInfo.getUser_level());
		uiBaseData = getFacade().getBaseDataService().getBaseData(uiBaseData);

		int user_level = uiBaseData.getPre_number3();
		request.setAttribute("user_level", user_level);

		// 订单类型数量
		request.setAttribute("dai_fukuan_num",
				super.getOrderInfoNum(Keys.OrderState.ORDER_STATE_0.getIndex(), ui.getId(), null, null));
		request.setAttribute("dai_fahuo_num",
				super.getOrderInfoNum(Keys.OrderState.ORDER_STATE_10.getIndex(), ui.getId(), null, null));
		request.setAttribute("dai_shouhuo_num",
				super.getOrderInfoNum(Keys.OrderState.ORDER_STATE_20.getIndex(), ui.getId(), null, null));
		request.setAttribute("quanbu_num", super.getOrderInfoNum(null, null, null, ui.getOwn_entp_id()));

		// 判断是否有未审核通过的申请
		super.setApplyEntpIsAudit(request, ui);
		return new ActionForward("/../m/MMyHome/MMyHome_index.jsp");
		// return mapping.findForward("list");

	}

	public ActionForward memberCenter(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		boolean is_app = super.isApp(request);
		if (is_app) {
			super.setIsAppToCookie(request, response);
		} else {
			// return super.toAppDownload(mapping, form, request, response);
		}

		request.setAttribute("header_title", "会员中心");
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			// String msg = "您还未登录，请先登录系统！";
			// return showTipNotLogin(mapping, form, request, response, msg);
			request.setAttribute("tianxiehaoma", -1);
			return mapping.findForward("list");
		} else {
			// 手机是否绑定
			UserInfo userInfo = new UserInfo();
			userInfo.setId(ui.getId());
			userInfo = getFacade().getUserInfoService().getUserInfo(userInfo);

			if (null == userInfo) {
				request.setAttribute("tianxiehaoma", -1);
				return mapping.findForward("list");
			}

			if (null != userInfo.getMobile() && StringUtils.isNotBlank(userInfo.getMobile())) {
				request.setAttribute("tianxiehaoma", 1);
			} else {
				request.setAttribute("tianxiehaoma", 0);
			}
		}

		if (!is_app) {
			request.setAttribute("titleSideName", "<i class=\"fa fa-tv\"></i> " + Keys.TopBtns.INDEX.getName());
		}

		// 用户信息
		UserInfo userInfo = new UserInfo();
		userInfo.setId(ui.getId());
		userInfo = getFacade().getUserInfoService().getUserInfo(userInfo);
		if (null == userInfo) {
			String msg = "用户不存在";
			return showTipNotLogin(mapping, form, request, response, msg);
		}
		// 安全等级
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
		request.setAttribute("userInfo", userInfo);

		request.setAttribute("percent", percent);

		// 用户等级
		BaseData uiBaseData = new BaseData();
		uiBaseData.setType(Keys.BASE_DATA_ID.BASE_DATA_ID_200.getIndex());
		uiBaseData.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		uiBaseData.setId(userInfo.getUser_level());
		uiBaseData = getFacade().getBaseDataService().getBaseData(uiBaseData);

		int user_level = uiBaseData.getPre_number3();
		request.setAttribute("user_level", user_level);

		return new ActionForward("/MMyHome/memberCenter.jsp");

	}

	public ActionForward entpCenter(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		boolean is_app = super.isApp(request);
		if (is_app) {
			super.setIsAppToCookie(request, response);
		} else {
			// return super.toAppDownload(mapping, form, request, response);
		}

		request.setAttribute("header_title", "商家中心");
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			// String msg = "您还未登录，请先登录系统！";
			// return showTipNotLogin(mapping, form, request, response, msg);
			request.setAttribute("tianxiehaoma", -1);
			return mapping.findForward("list");
		} else {
			// 手机是否绑定
			UserInfo userInfo = new UserInfo();
			userInfo.setId(ui.getId());
			userInfo = getFacade().getUserInfoService().getUserInfo(userInfo);

			if (null == userInfo) {
				request.setAttribute("tianxiehaoma", -1);
				return mapping.findForward("list");
			}

			if (null != userInfo.getMobile() && StringUtils.isNotBlank(userInfo.getMobile())) {
				request.setAttribute("tianxiehaoma", 1);
			} else {
				request.setAttribute("tianxiehaoma", 0);
			}
		}

		if (!is_app) {
			request.setAttribute("titleSideName", "<i class=\"fa fa-tv\"></i> " + Keys.TopBtns.INDEX.getName());
		}

		// 用户信息
		UserInfo userInfo = new UserInfo();
		userInfo.setId(ui.getId());
		userInfo = getFacade().getUserInfoService().getUserInfo(userInfo);
		if (null == userInfo) {
			String msg = "用户不存在";
			return showTipNotLogin(mapping, form, request, response, msg);
		}
		// 安全等级
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
		request.setAttribute("userInfo", userInfo);

		request.setAttribute("percent", percent);

		// 用户等级
		BaseData uiBaseData = new BaseData();
		uiBaseData.setType(Keys.BASE_DATA_ID.BASE_DATA_ID_200.getIndex());
		uiBaseData.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		uiBaseData.setId(userInfo.getUser_level());
		uiBaseData = getFacade().getBaseDataService().getBaseData(uiBaseData);

		int user_level = uiBaseData.getPre_number3();
		request.setAttribute("user_level", user_level);
		// request.setAttribute("shiwu_num",
		// this.getOrderInfoNum(null, null, Keys.OrderType.ORDER_TYPE_11.getIndex(), ui.getOwn_entp_id()));
		// request.setAttribute("xuni_num",
		// this.getOrderInfoNum(null, null, Keys.OrderType.ORDER_TYPE_10.getIndex(), ui.getOwn_entp_id()));
		request.setAttribute("tui_huan_num", this.getOrderReturnInfoCount(ui.getOwn_entp_id()));

		// request.setAttribute("saoma_num",
		// this.getOrderInfoNum(null, null, Keys.OrderType.ORDER_TYPE_20.getIndex(), ui.getOwn_entp_id()));
		// request.setAttribute("fanxian_num",
		// this.getOrderInfoNum(null, null, Keys.OrderType.ORDER_TYPE_40.getIndex(), ui.getOwn_entp_id()));
		// request.setAttribute("tuangou_num",
		// this.getOrderInfoNum(null, null, Keys.OrderType.ORDER_TYPE_140.getIndex(), ui.getOwn_entp_id()));

		return new ActionForward("/MMyHome/entpCenter.jsp");

	}

	public Integer getOrderReturnInfoCount(Integer entp_id) {
		OrderReturnInfo a = new OrderReturnInfo();
		a.setAudit_state(0);
		a.setEntp_id(entp_id);
		a.setIs_del(0);
		Integer count = getFacade().getOrderReturnInfoService().getOrderReturnInfoCount(a);
		System.out.println("===count:" + count);
		return count;
	}

	public ActionForward operationCenter(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		boolean is_app = super.isApp(request);
		if (is_app) {
			super.setIsAppToCookie(request, response);
		} else {
			// return super.toAppDownload(mapping, form, request, response);
		}

		request.setAttribute("header_title", "运营中心");
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			// String msg = "您还未登录，请先登录系统！";
			// return showTipNotLogin(mapping, form, request, response, msg);
			request.setAttribute("tianxiehaoma", -1);
			return mapping.findForward("list");
		} else {
			// 手机是否绑定
			UserInfo userInfo = new UserInfo();
			userInfo.setId(ui.getId());
			userInfo = getFacade().getUserInfoService().getUserInfo(userInfo);

			if (null == userInfo) {
				request.setAttribute("tianxiehaoma", -1);
				return mapping.findForward("list");
			}

			if (null != userInfo.getMobile() && StringUtils.isNotBlank(userInfo.getMobile())) {
				request.setAttribute("tianxiehaoma", 1);
			} else {
				request.setAttribute("tianxiehaoma", 0);
			}
		}

		if (!is_app) {
			request.setAttribute("titleSideName", "<i class=\"fa fa-tv\"></i> " + Keys.TopBtns.INDEX.getName());
		}

		// 用户信息
		UserInfo userInfo = new UserInfo();
		userInfo.setId(ui.getId());
		userInfo = getFacade().getUserInfoService().getUserInfo(userInfo);
		if (null == userInfo) {
			String msg = "用户不存在";
			return showTipNotLogin(mapping, form, request, response, msg);
		}
		// 安全等级
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
		request.setAttribute("userInfo", userInfo);

		request.setAttribute("percent", percent);

		// 用户等级
		BaseData uiBaseData = new BaseData();
		uiBaseData.setType(Keys.BASE_DATA_ID.BASE_DATA_ID_200.getIndex());
		uiBaseData.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		uiBaseData.setId(userInfo.getUser_level());
		uiBaseData = getFacade().getBaseDataService().getBaseData(uiBaseData);

		int user_level = uiBaseData.getPre_number3();
		request.setAttribute("user_level", user_level);

		return new ActionForward("/MMyHome/operationCenter.jsp");

	}

	public ActionForward listScJson(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}

		Pager pager = (Pager) dynaBean.get("pager");
		String startRow = (String) dynaBean.get("startRow");
		String sc_type = (String) dynaBean.get("sc_type");

		JSONObject datas = new JSONObject();
		JSONArray dataLoadList = new JSONArray();

		if (StringUtils.isNotBlank(sc_type)) {

			datas.put("sc_type", sc_type);

			ScEntpComm scEntpComm = new ScEntpComm();
			scEntpComm.setIs_del(0);
			scEntpComm.setAdd_user_id(ui.getId());
			scEntpComm.setSc_type(Integer.valueOf(sc_type));
			Integer recordCount = super.getFacade().getScEntpCommService().getScEntpCommCount(scEntpComm);
			pager.init(recordCount.longValue(), 4, pager.getRequestPage());
			scEntpComm.getRow().setFirst(pager.getFirstRow());
			if (StringUtils.isNotBlank(startRow)) {
				scEntpComm.getRow().setFirst(Integer.valueOf(startRow));
			}
			scEntpComm.getRow().setCount(pager.getRowCount());
			List<ScEntpComm> scCommlist = super.getFacade().getScEntpCommService()
					.getScEntpCommPaginatedList(scEntpComm);

			if ((null != scCommlist) && (scCommlist.size() > 0)) {
				for (ScEntpComm sec : scCommlist) {

					JSONObject map = new JSONObject();
					map.put("id", sec.getId());
					if (sc_type.equals("1")) {

						EntpInfo entpInfo = new EntpInfo();
						// entpInfo.setIs_del(0);
						entpInfo.setId(sec.getLink_id());
						entpInfo = super.getFacade().getEntpInfoService().getEntpInfo(entpInfo);
						map.put("entp_logo", entpInfo.getEntp_logo());
						map.put("entp_id", sec.getLink_id());

					} else {
						CommInfo commInfo = new CommInfo();
						// commInfo.setIs_del(0);
						commInfo.setId(sec.getLink_id());
						commInfo = super.getFacade().getCommInfoService().getCommInfo(commInfo);
						if (null != commInfo) {
							map.put("main_pic", commInfo.getMain_pic());
							map.put("min_price", dfFormat.format(commInfo.getSale_price()));
							map.put("comm_id", commInfo.getId());
						}
					}

					map.put("title_name", sec.getTitle_name());
					dataLoadList.add(map);
				}
				datas.put("ret", "1");
				datas.put("dataLoadList", dataLoadList);

				// logger.info("====startRow=====" + startRow);
				// logger.info("====recordCount=====" + recordCount);

				if ((Integer.valueOf(startRow) + 4) < recordCount) {
					datas.put("loadMore", "1");
				} else {
					datas.put("loadMore", "0");
				}
			} else {
				datas.put("ret", "0");
				datas.put("loadMore", "0");
			}
		}

		logger.info("===datas:{}", datas.toString());
		super.renderJson(response, datas.toString());

		return null;

	}

	public ActionForward reqHistoryJson(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}

		String startRow = (String) dynaBean.get("startRow");

		JSONObject datas = new JSONObject();
		JSONArray dataLoadList = new JSONArray();

		if (StringUtils.isNotBlank(startRow)) {
			String separatorChar = "卍卐"; // 分隔符
			String cookieName = "browseCommInfos";
			String latestCookieValue = "";
			Cookie[] cookies = request.getCookies();

			List<CommInfo> cookiesCommInfoList = null;
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
						cookiesCommInfoList = new ArrayList<CommInfo>();
						for (String latestCookieValue2 : latestCookieValues) {
							String[] cookiesList = StringUtils.splitByWholeSeparator(latestCookieValue2, separatorChar);

							if (cookiesList.length == 4) {
								CommInfo commInfo2 = new CommInfo();
								for (String element : cookiesList) {
									commInfo2.setId(Integer.valueOf(cookiesList[0]));
									commInfo2.setComm_name(cookiesList[1]);
									commInfo2.setMain_pic(cookiesList[2]);
									commInfo2.setPrice_ref(new BigDecimal(cookiesList[3]));
									// commInfo2.getMap().put("price_ref", new BigDecimal(cookiesList[3]));
								}
								cookiesCommInfoList.add(commInfo2);
							}
						}
						request.setAttribute("cookiesCommList", cookiesCommInfoList);
					}
				}
			}

			if ((null != cookiesCommInfoList) && (cookiesCommInfoList.size() > 0)) {
				int start = Integer.valueOf(startRow);
				int end = start + 4;
				int size = cookiesCommInfoList.size();
				if (end > size) {
					end = size;
				}
				for (int i = 0; i <= size; i++) {
					if ((start <= i) && (i < end)) {
						CommInfo comm = cookiesCommInfoList.get(i);
						JSONObject map = new JSONObject();
						map.put("main_pic", comm.getMain_pic());
						map.put("comm_id", comm.getId());
						map.put("comm_name", comm.getComm_name());
						map.put("price_ref", dfFormat.format(comm.getPrice_ref()));
						dataLoadList.add(map);
					}
				}
				datas.put("ret", "1");
				datas.put("dataLoadList", dataLoadList);
				if (size <= (start + 4)) {
					datas.put("loadMore", "0");
				} else {
					datas.put("loadMore", "1");
				}
			} else {
				datas.put("ret", "0");
				datas.put("loadMore", "0");
			}
		}

		logger.info("===datas:{}", datas.toString());
		super.renderJson(response, datas.toString());

		return null;

	}

	public ActionForward delSc(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}

		String id = (String) dynaBean.get("id");

		JSONObject datas = new JSONObject();
		int ret = 0;

		if (StringUtils.isNotBlank(id)) {
			ScEntpComm scEntpComm = new ScEntpComm();
			scEntpComm.setIs_del(1);
			scEntpComm.setId(Integer.valueOf(id));
			ret = super.getFacade().getScEntpCommService().modifyScEntpComm(scEntpComm);
		}

		datas.put("ret", ret);

		return null;

	}
}
