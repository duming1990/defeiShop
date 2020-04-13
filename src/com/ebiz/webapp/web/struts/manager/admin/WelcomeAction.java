package com.ebiz.webapp.web.struts.manager.admin;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.alibaba.fastjson.JSONObject;
import com.ebiz.webapp.domain.AppInfo;
import com.ebiz.webapp.domain.AppTokens;
import com.ebiz.webapp.domain.BaseAuditRecord;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.EntpDuiZhang;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.PoorInfo;
import com.ebiz.webapp.domain.ServiceCenterInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.UserMoneyApply;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.struts.BaseWebAction;
import com.ebiz.webapp.web.util.EncryptUtilsV2;

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
		// 企业个数
		EntpInfo entpInfo = new EntpInfo();
		entpInfo.setIs_del(0);
		Integer entpInfoCount = super.getFacade().getEntpInfoService().getEntpInfoCount(entpInfo);
		request.setAttribute("entpInfoCount", entpInfoCount);

		// 今天入驻企业个数
		EntpInfo entpInfoRz = new EntpInfo();
		entpInfoRz.getMap().put("today_date", ndate);
		entpInfoRz.setIs_del(0);
		Integer entpInfoRzCount = super.getFacade().getEntpInfoService().getEntpInfoCount(entpInfoRz);
		request.setAttribute("entpInfoRzCount", entpInfoRzCount);

		// 待审核企业个数
		EntpInfo entpInfoSh = new EntpInfo();
		entpInfoSh.setAudit_state(0);
		entpInfoSh.setIs_del(0);
		Integer entpInfoShCount = super.getFacade().getEntpInfoService().getEntpInfoCount(entpInfoSh);
		request.setAttribute("entpInfoShCount", entpInfoShCount);

		// 注册会员总数
		UserInfo userInfo = new UserInfo();
		userInfo.setIs_active(1);
		userInfo.setIs_del(0);
		userInfo.getMap().put("user_type_ne", Keys.UserType.USER_TYPE_1.getIndex());
		Integer userInfoCount = super.getFacade().getUserInfoService().getUserInfoCount(userInfo);
		request.setAttribute("userInfoCount", userInfoCount);

		// 今天注册个人会员
		UserInfo userInfoZcgr = new UserInfo();
		userInfoZcgr.getMap().put("today_date", ndate);
		userInfoZcgr.setUser_type(Keys.UserType.USER_TYPE_2.getIndex());
		userInfoZcgr.setIs_del(0);
		Integer userInfoZcgrCount = super.getFacade().getUserInfoService().getUserInfoCount(userInfoZcgr);
		request.setAttribute("userInfoZcgrCount", userInfoZcgrCount);

		// 今天注册企业会员
		UserInfo userInfoZcqy = new UserInfo();
		userInfoZcqy.getMap().put("today_date", ndate);
		userInfoZcqy.setIs_entp(1);
		userInfoZcqy.setIs_del(0);
		Integer userInfoZcqyCount = super.getFacade().getUserInfoService().getUserInfoCount(userInfoZcqy);
		request.setAttribute("userInfoZcqyCount", userInfoZcqyCount);

		// 产品总数
		CommInfo commInfo = new CommInfo();
		commInfo.setIs_del(0);
		commInfo.setComm_type(Keys.CommType.COMM_TYPE_2.getIndex());
		Integer commInfoCount = super.getFacade().getCommInfoService().getCommInfoCount(commInfo);
		request.setAttribute("commInfoCount", commInfoCount);

		// 今天新增加产品数量
		CommInfo commInfoZj = new CommInfo();
		commInfoZj.getMap().put("today_date", ndate);
		commInfoZj.setIs_del(0);
		commInfoZj.setComm_type(Keys.CommType.COMM_TYPE_2.getIndex());
		Integer commInfoZjCount = super.getFacade().getCommInfoService().getCommInfoCount(commInfoZj);
		request.setAttribute("commInfoZjCount", commInfoZjCount);

		// 待审核的商品数量
		CommInfo commInfoDsh = new CommInfo();
		commInfoDsh.setIs_del(0);
		commInfoDsh.setAudit_state(0);
		commInfoDsh.setComm_type(Keys.CommType.COMM_TYPE_2.getIndex());
		Integer commInfoDshCount = super.getFacade().getCommInfoService().getCommInfoCount(commInfoDsh);
		request.setAttribute("commInfoDshCount", commInfoDshCount);

		// 订单总数

		OrderInfo orderInfoTodaySum = new OrderInfo();
		orderInfoTodaySum.getMap().put("order_state_ne", Keys.OrderState.ORDER_STATE_90.getIndex());
		orderInfoTodaySum.getMap().put("order_state_ge", Keys.OrderState.ORDER_STATE_10.getIndex());
		orderInfoTodaySum = super.getFacade().getOrderInfoService().getOrderInfoStatisticaSum(orderInfoTodaySum);
		request.setAttribute("orderInfoTodaySum", orderInfoTodaySum);

		// 用户提现申请
		UserMoneyApply userMoneyApply = new UserMoneyApply();
		userMoneyApply.setCash_type(Keys.CASH_TYPE.CASH_TYPE_10.getIndex());
		userMoneyApply.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		userMoneyApply.setInfo_state(new Integer(0));
		Integer userMoneyApplyCount = super.getFacade().getUserMoneyApplyService()
				.getUserMoneyApplyCount(userMoneyApply);
		request.setAttribute("userMoneyApplyCount", userMoneyApplyCount);

		// 商家对账
		EntpDuiZhang entpDuiZhang = new EntpDuiZhang();
		entpDuiZhang.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		entpDuiZhang.setIs_check(0);
		Integer enptMoneyApplyCount = super.getFacade().getEntpDuiZhangService().getEntpDuiZhangCount(entpDuiZhang);
		request.setAttribute("enptMoneyApplyCount", enptMoneyApplyCount);

		// 扶贫金提现申请
		UserMoneyApply fupinMoneyApply = new UserMoneyApply();
		fupinMoneyApply.setCash_type(Keys.CASH_TYPE.CASH_TYPE_20.getIndex());
		fupinMoneyApply.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		fupinMoneyApply.setInfo_state(new Integer(0));
		Integer fupinMoneyApplyCount = super.getFacade().getUserMoneyApplyService()
				.getUserMoneyApplyCount(fupinMoneyApply);
		request.setAttribute("fupinMoneyApplyCount", fupinMoneyApplyCount);

		// 今日订单数
		OrderInfo todayOrder = new OrderInfo();
		todayOrder.getMap().put("st_add_date", sdFormat_ymd.format(new Date()));
		todayOrder.getMap().put("en_add_date", sdFormat_ymd.format(new Date()));
		todayOrder.getMap().put("order_state_ne", Keys.OrderState.ORDER_STATE_90.getIndex());
		todayOrder.getMap().put("order_state_ge", Keys.OrderState.ORDER_STATE_10.getIndex());
		Integer todayOrderCount = super.getFacade().getOrderInfoService().getOrderInfoCount(todayOrder);
		request.setAttribute("todayOrderCount", todayOrderCount);

		// 全部
		ServiceCenterInfo serviceCenterInfoSum = new ServiceCenterInfo();
		serviceCenterInfoSum.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		Integer serviceCenterInfoSumCount = super.getFacade().getServiceCenterInfoService()
				.getServiceCenterInfoCount(serviceCenterInfoSum);
		request.setAttribute("serviceCenterInfoSumCount", serviceCenterInfoSumCount);
		// 待审核
		ServiceCenterInfo serviceCenterInfo = new ServiceCenterInfo();
		serviceCenterInfo.setAudit_state(Keys.INFO_STATE.INFO_STATE_0.getIndex());
		serviceCenterInfo.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		Integer serviceCenterInfoCount = super.getFacade().getServiceCenterInfoService()
				.getServiceCenterInfoCount(serviceCenterInfo);
		request.setAttribute("serviceCenterInfoCount", serviceCenterInfoCount);

		// 实名认证 全部
		BaseAuditRecord baseAuditRecord = new BaseAuditRecord();
		baseAuditRecord.setOpt_type(Keys.OptType.OPT_TYPE_10.getIndex());
		Integer baseAuditRecordCount = getFacade().getBaseAuditRecordService().getBaseAuditRecordCount(baseAuditRecord);
		request.setAttribute("baseAuditRecordCount", baseAuditRecordCount);

		// 实名认证 待审核
		BaseAuditRecord baseAuditRecord0 = new BaseAuditRecord();
		baseAuditRecord0.setOpt_type(Keys.OptType.OPT_TYPE_10.getIndex());
		baseAuditRecord0.setAudit_state(Keys.audit_state.audit_state_0.getIndex());
		Integer baseAuditRecord0Count = getFacade().getBaseAuditRecordService().getBaseAuditRecordCount(
				baseAuditRecord0);
		request.setAttribute("baseAuditRecord0Count", baseAuditRecord0Count);

		// 贫困户总数
		PoorInfo poorInfo = new PoorInfo();
		// poorInfo.setReport_step(4);
		poorInfo.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		poorInfo.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		Integer poorInfoCount = getFacade().getPoorInfoService().getVillageManagerPoorInfoCount(poorInfo);
		request.setAttribute("poorInfoCount", poorInfoCount);

		// 贫困户待审核
		PoorInfo poorInfo0 = new PoorInfo();
		poorInfo0.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		poorInfo0.setAudit_state(Keys.audit_state.audit_state_0.getIndex());
		Integer poorInfo0Count = getFacade().getPoorInfoService().getVillageManagerPoorInfoCount(poorInfo0);
		request.setAttribute("poorInfo0Count", poorInfo0Count);

		return new ActionForward("/admin/Welcome/index.jsp");
	}

	public ActionForward toOthersSystem(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='../../login.shtml'}");
			return null;
		}
		JSONObject data = new JSONObject();
		String ret = "0";
		String msg = "";
		DynaBean dynaBean = (DynaBean) form;
		AppInfo appInfo = new AppInfo();

		appInfo.setApp_key(Keys.nineporters_app_key);
		appInfo.setApp_secret(Keys.nineporters_app_secret);
		appInfo = super.getFacade().getAppInfoService().getAppInfo(appInfo);
		if (null == appInfo) {
			StringBuffer result = new StringBuffer();
			result.append("{\"code\":\"102\",\"msg\":\"应用编码或应用密钥错误，认证失败\"}");
			super.renderJson(response, result.toString());
			return null;
		}

		String timestamp = String.valueOf(new Date().getTime());
		String token = EncryptUtilsV2.MD5Encode(
				"app_key=" + Keys.nineporters_app_key + "&app_secret=" + Keys.nineporters_app_secret + "&timestamp="
						+ timestamp + "#").toLowerCase();

		// 生成数字签名证书，注意证书只能使用一次，使用后立即删除
		AppTokens appTokens = new AppTokens();
		appTokens.setApp_key(Keys.nineporters_app_key);
		appTokens.setToken(token);
		appTokens.setAdd_date(new Date());
		super.getFacade().getAppTokensService().createAppTokens(appTokens);

		String type = (String) dynaBean.get("type");

		if (type.equals("1")) {
			ret = "1";
			msg = Keys.nineporters_supermarket + "&app_key=" + Keys.nineporters_app_key + "&token=" + token;
		}
		if (type.equals("2")) {
			ret = "1";
			msg = Keys.nineporters_sx + "&app_key=" + Keys.nineporters_app_key + "&token=" + token;
		}
		if (type.equals("3")) {
			ret = "1";
			msg = Keys.nineporters_jxc + "&app_key=" + Keys.nineporters_app_key + "&token=" + token;
		}
		data.put("ret", ret);
		data.put("msg", msg);
		super.renderJson(response, data.toJSONString());
		return null;
	}
}
