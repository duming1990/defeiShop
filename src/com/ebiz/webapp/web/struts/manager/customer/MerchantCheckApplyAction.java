package com.ebiz.webapp.web.struts.manager.customer;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.BaseData;
import com.ebiz.webapp.domain.EntpDuiZhang;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.UserBiRecord;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.DateTools;

public class MerchantCheckApplyAction extends BaseCustomerAction {

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("=======list====");
		saveToken(request);

		setNaviStringToRequestScope(request);
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		OrderInfo entity = new OrderInfo();
		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		// String st_finish_date = (String) dynaBean.get("st_finish_date");
		String st_finish_date = this.getDate(ui);
		String en_finish_date = (String) dynaBean.get("en_finish_date");
		String order_type = (String) dynaBean.get("order_type");

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());

		int y, m, d, h, mi, s;
		y = cal.get(Calendar.YEAR);
		m = cal.get(Calendar.MONTH) + 1;
		d = cal.get(Calendar.DATE);
		h = 23;
		mi = 59;
		s = 59;

		Date date2 = DateTools.changeString2Date(y + "-" + m + "-" + d + " " + h + ":" + mi + ":" + s);

		if (en_finish_date != null && !"".equals(en_finish_date)) {
			Date date1 = DateTools.changeString2Date(en_finish_date);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// Date date = entpDZList.get(0).getEnd_check_date();
			String date = sdf.format(date2);
			if (DateTools.compareDate(date1, date2) >= 0) {
				String msg = "结束时间必须在" + date + "之前！";
				return super.showMsgForManager(request, response, msg);

			}

		}

		dynaBean.set("order_type", order_type);

		if (StringUtils.isBlank(st_finish_date)) {
			return mapping.findForward("list");
		}
		if (StringUtils.isBlank(en_finish_date)) {
			return mapping.findForward("list");
		}

		entity = this.pulibcWhere(order_type, entity);

		entity.setEntp_id(ui.getOwn_entp_id());
		entity.getMap().put("st_finish_date", st_finish_date);
		entity.getMap().put("en_finish_date", en_finish_date);
		Integer recordCount = getFacade().getOrderInfoService().getselectCheckCount(entity);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());
		// if (StringUtils.isBlank(st_finish_date) || StringUtils.isBlank(en_finish_date)) {
		// request.setAttribute("show", null);
		// return mapping.findForward("list");
		//
		// }
		if (StringUtils.isBlank(en_finish_date)) {
			request.setAttribute("show", null);
			return mapping.findForward("list");

		}
		request.setAttribute("show", 1);

		logger.info("===selec_order");
		List<OrderInfo> entityList = super.getFacade().getOrderInfoService().getselectCheckList(entity);
		for (OrderInfo oif : entityList) {
			oif.getMap().put("bi_huoKuang", ui.getBi_huokuan());
		}
		request.setAttribute("entityList", entityList);
		return mapping.findForward("list");
	}

	public ActionForward listDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		setNaviStringToRequestScope(request);

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		OrderInfo entity = new OrderInfo();
		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		// String st_finish_date = (String) dynaBean.get("st_finish_date");
		String st_finish_date = this.getDate(ui);
		String en_finish_date = (String) dynaBean.get("en_finish_date");
		String order_type = (String) dynaBean.get("order_type");
		entity.setEntp_id(ui.getOwn_entp_id());

		entity = this.pulibcWhere(order_type, entity);
		if (StringUtils.isNotBlank(st_finish_date)) {
			entity.getMap().put("st_finish_date", st_finish_date);
		}
		if (StringUtils.isNotBlank(en_finish_date)) {
			entity.getMap().put("en_finish_date", en_finish_date);
		}

		Integer recordCount = getFacade().getOrderInfoService().getOrderInfoCount(entity);
		pager.init(recordCount.longValue(), 10, pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());
		List<OrderInfo> entityList = super.getFacade().getOrderInfoService().getOrderInfoPaginatedList(entity);
		request.setAttribute("entityList", entityList);
		return new ActionForward("/../manager/customer/MerchantCheckApply/list_details.jsp");
	}

	public static Date getDate() {
		return null;
	}

	// 发起结算申请
	public ActionForward startCheck(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		resetToken(request);

		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return super.showMsgForManager(request, response, msg);
		}

		//
		// log.info("time==" + st_finish_date);
		// String sum_huokuan = (String) dynaBean.get("sum_huokuan");
		String st_finish_date = this.getDate(ui);
		String en_finish_date = (String) dynaBean.get("en_finish_date");
		String order_type = (String) dynaBean.get("order_type");
		if (null == ui.getOwn_entp_id()) {
			String msg = "非商家无操作权限！";
			return super.showMsgForManager(request, response, msg);
		}
		if (StringUtils.isBlank(st_finish_date)) {
			String msg = "开始时间不能为空！";
			return super.showMsgForManager(request, response, msg);
		}
		if (StringUtils.isBlank(en_finish_date)) {
			String msg = "结束时间不能为空！";
			return super.showMsgForManager(request, response, msg);
		}

		OrderInfo oi = new OrderInfo();
		oi.setEntp_id(ui.getOwn_entp_id());
		oi = this.pulibcWhere(order_type, oi);
		// oi.getMap().put("st_finish_date", st_finish_date);
		// oi.getMap().put("en_finish_date", en_finish_date);
		List<OrderInfo> entityList = super.getFacade().getOrderInfoService().getselectCheckList(oi);
		if (null == entityList || entityList.size() == 0) {
			String msg = "查询结算申请失败，请反馈！";
			return super.showMsgForManager(request, response, msg);
		}
		oi = entityList.get(0);
		if (null == oi) {
			String msg = "订单异常，无法结算！";
			return super.showMsgForManager(request, response, msg);
		}
		BigDecimal sum_huokuan = (BigDecimal) oi.getMap().get("sum_huokuan");// 订单货款
		BigDecimal sum_order_money = (BigDecimal) oi.getMap().get("sum_order_money");// 订单金额

		// 查看商家对账记录，有未对账的则无法对账
		EntpDuiZhang entpDZ = new EntpDuiZhang();
		entpDZ.setEntp_id(ui.getOwn_entp_id());
		entpDZ.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		entpDZ.setIs_check(Keys.IsCleck.IS_CLECK_0.getIndex());
		int count = super.getFacade().getEntpDuiZhangService().getEntpDuiZhangCount(entpDZ);
		if (count > 0) {
			String msg = "你有" + count + "条对账在审核，无法新添对账！";
			return super.showMsgForManager(request, response, msg);
		}

		// 得到商家货款币
		UserInfo uInfo = new UserInfo();
		uInfo.setId(ui.getId());
		// uInfo.setIs_entp(1);
		uInfo.setIs_del(0);
		uInfo = super.getFacade().getUserInfoService().getUserInfo(uInfo);
		if (null == uInfo) {
			String msg = "没有得到商家信息，请联系管理员查看商家是否为空！";
			return super.showMsgForManager(request, response, msg);
		}
		BigDecimal bi_huokuan = uInfo.getBi_huokuan();
		if (bi_huokuan.compareTo(sum_huokuan) == -1) {
			String msg = "货款币不足，无法结算！";
			return super.showMsgForManager(request, response, msg);
		}
		// 商家货款减少，修改商家货款币
		UserInfo uInfoSub = new UserInfo();
		uInfoSub.setId(uInfo.getId());
		uInfoSub.getMap().put("sub_bi_huokuan", sum_huokuan);
		// 在UserBiRecord添加一条减少记录
		UserBiRecord ubr = new UserBiRecord();
		ubr.setBi_chu_or_ru(Keys.BI_CHU_OR_RU.BASE_DATA_ID_NEGATIVE1.getIndex());
		ubr.setBi_type(Keys.BiType.BI_TYPE_300.getIndex());
		ubr.setBi_no_before(uInfo.getBi_huokuan());
		ubr.setBi_no(sum_huokuan);
		ubr.setBi_no_after(uInfo.getBi_huokuan().subtract(sum_huokuan));
		ubr.setBi_get_type(Keys.BiGetType.BI_GET_TYPE_71.getIndex());
		ubr.setBi_get_memo(Keys.BiGetType.BI_GET_TYPE_71.getName());
		ubr.setAdd_user_id(uInfo.getId());
		ubr.setAdd_date(new Date());
		ubr.setAdd_uname(uInfo.getUser_name());

		BaseData baseData = super.getBaseData(Keys.BASE_DATA_ID.BASE_DATA_ID_607.getIndex());
		BigDecimal cash_rate = new BigDecimal(0);
		BigDecimal cash_pay = sum_huokuan;
		if (null != baseData) {
			cash_rate = new BigDecimal(baseData.getPre_number());// 手续费比例
			cash_rate = sum_huokuan.multiply(cash_rate.divide(new BigDecimal(100)));// 手续费
			cash_pay = sum_huokuan.subtract(cash_rate);
		}
		// 插入对账申请记录
		EntpDuiZhang edz = new EntpDuiZhang();
		if (StringUtils.isNotBlank(order_type)
				&& Keys.OrderType.ORDER_TYPE_90.getIndex() == Integer.valueOf(order_type)) {
			edz.setOrder_type(Keys.OrderType.ORDER_TYPE_90.getIndex());
		}
		edz.setSum_money(sum_huokuan);
		edz.setCash_pay(cash_pay);// 扣除手续费后金额
		edz.setCash_rate(cash_rate);// 手续费
		edz.setSum_order_money(sum_order_money);
		edz.setEntp_id(uInfo.getOwn_entp_id());
		edz.setEntp_name(uInfo.getOwn_entp_name() + "<br/>开户行："
				+ (null == uInfo.getBank_name() ? "" : uInfo.getBank_name()) + "<br/>开户账号："
				+ (null == uInfo.getBank_account() ? "" : uInfo.getBank_account())
				+ (null == uInfo.getBank_branch_name() ? "" : uInfo.getBank_branch_name()) + "<br/>开户名："
				+ (null == uInfo.getBank_account_name() ? "" : uInfo.getBank_account_name()));
		edz.setAdd_user_id(uInfo.getId());
		edz.setAdd_user_name(uInfo.getUser_name());
		edz.setAdd_date(new Date());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			edz.setAdd_check_date(sdf.parse(st_finish_date + " 00:00:00"));
			edz.setEnd_check_date(sdf.parse(en_finish_date + " 23:59:59"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 更改orderinfo表中的
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setIs_check(Keys.AddIsCleck.ADD_IS_CLECK_1.getIndex());
		orderInfo.getMap().put("opt_is_test", "0");// 过滤测试订单

		if (StringUtils.isNotBlank(order_type)
				&& Keys.OrderType.ORDER_TYPE_90.getIndex() == Integer.valueOf(order_type)) {
			orderInfo.setOrder_type(Keys.OrderType.ORDER_TYPE_90.getIndex());
			// 支付时间一天前
			orderInfo.getMap().put("en_pay_date", getNewDateGoOverDay(-1));

		} else {
			orderInfo.getMap().put(
					"order_type_in",
					Keys.OrderType.ORDER_TYPE_10.getIndex() + "," + Keys.OrderType.ORDER_TYPE_7.getIndex() + ","
							+ Keys.OrderType.ORDER_TYPE_20.getIndex());
		}

		orderInfo.getMap().put("check_entp_id", uInfo.getOwn_entp_id());
		orderInfo.getMap().put("st_finish_date", st_finish_date);
		orderInfo.getMap().put("en_finish_date", en_finish_date);
		orderInfo.getMap().put("opt_order_state", Keys.OrderState.ORDER_STATE_50.getIndex());
		orderInfo.getMap().put("not_check", 0);// 没有被结算过得 防止一天内两次结算 上一次 link_check_id会被更新
		orderInfo.getMap().put("is_null_link_check_id", true);// 没有被结算过得 防止一天内两次结算 上一次 link_check_id会被更新

		edz.getMap().put("update_uInfo", uInfoSub);
		edz.getMap().put("insert_ubr", ubr);
		edz.getMap().put("update_orderInfo", orderInfo);

		super.getFacade().getEntpDuiZhangService().createEntpDuiZhang(edz);
		super.renderJavaScript(response, "alert('".concat("添加成功").concat("');location.href='MerchantCheckApply.do?';"));
		return null;

	}
}