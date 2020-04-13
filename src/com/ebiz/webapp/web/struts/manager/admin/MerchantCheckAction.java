package com.ebiz.webapp.web.struts.manager.admin;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.EntpDuiZhang;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.UserBiRecord;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;

/**
 * 商家对账
 * 
 * @author luzr
 */
public class MerchantCheckAction extends BaseAdminAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		setNaviStringToRequestScope(request);
		OrderInfo entity = new OrderInfo();
		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String entp_name = (String) dynaBean.get("entp_name");
		String st_finish_date = (String) dynaBean.get("st_finish_date");
		String en_finish_date = (String) dynaBean.get("en_finish_date");
		if (StringUtils.isNotBlank(entp_name)) {
			entity.getMap().put("entp_name_like", entp_name);
		}
		if (StringUtils.isBlank(st_finish_date)) {
			return mapping.findForward("list");
		}
		if (StringUtils.isBlank(en_finish_date)) {
			return mapping.findForward("list");
		}

		entity.getMap().put("st_finish_date", st_finish_date);
		entity.getMap().put("en_finish_date", en_finish_date);
		entity.setOrder_state(Keys.OrderState.ORDER_STATE_50.getIndex());
		entity.setIs_check(Keys.AddIsCleck.ADD_IS_CLECK_0.getIndex());
		Integer recordCount = getFacade().getOrderInfoService().getselectCheckCount(entity);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());
		if (StringUtils.isBlank(st_finish_date) || StringUtils.isBlank(en_finish_date)) {
			request.setAttribute("show", null);
			return mapping.findForward("list");

		}
		request.setAttribute("show", 1);

		List<OrderInfo> entityList = super.getFacade().getOrderInfoService().getselectCheckList(entity);
		for (OrderInfo oif : entityList) {
			UserInfo userInfo = new UserInfo();
			userInfo.setOwn_entp_id(Integer.valueOf(oif.getMap().get("entp_id").toString()));
			userInfo.setIs_entp(1);
			userInfo.setIs_del(0);
			userInfo = getFacade().getUserInfoService().getUserInfo(userInfo);
			if (userInfo != null) {
				oif.getMap().put("bi_huoKuang", userInfo.getBi_huokuan());
			}
		}
		request.setAttribute("entityList", entityList);
		return mapping.findForward("list");
	}

	public ActionForward listDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		setNaviStringToRequestScope(request);

		OrderInfo entity = new OrderInfo();
		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String entp_id = (String) dynaBean.get("entp_id");
		String st_finish_date = (String) dynaBean.get("st_finish_date");
		String en_finish_date = (String) dynaBean.get("en_finish_date");
		if (StringUtils.isNotBlank(entp_id)) {
			entity.setEntp_id(Integer.valueOf(entp_id));
			request.setAttribute("entp_id", entp_id);
		}
		if (StringUtils.isNotBlank(st_finish_date)) {
			entity.getMap().put("st_finish_date", st_finish_date);
		}
		if (StringUtils.isNotBlank(en_finish_date)) {
			entity.getMap().put("en_finish_date", en_finish_date);
		}
		entity.setOrder_state(Keys.OrderState.ORDER_STATE_50.getIndex());
		Integer recordCount = getFacade().getOrderInfoService().getOrderInfoCount(entity);
		pager.init(recordCount.longValue(), 10, pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());
		List<OrderInfo> entityList = super.getFacade().getOrderInfoService().getOrderInfoPaginatedList(entity);
		request.setAttribute("entityList", entityList);
		return new ActionForward("/../manager/admin/MerchantCheck/list_details.jsp");
	}

	// 发起对账申请
	public ActionForward startCheck(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return super.showMsgForManager(request, response, msg);
		}

		UserInfo userInfo = new UserInfo();
		userInfo.setId(ui.getId());
		userInfo = super.getFacade().getUserInfoService().getUserInfo(userInfo);
		String entp_id = (String) dynaBean.get("entp_id");
		//String sum_huokuan = (String) dynaBean.get("sum_huokuan");
		String st_finish_date = (String) dynaBean.get("st_finish_date");
		String en_finish_date = (String) dynaBean.get("en_finish_date");
		if (StringUtils.isBlank(entp_id)) {
			String msg = "传入商家id有误！";
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
		oi.setEntp_id(Integer.valueOf(entp_id));
		oi.setOrder_state(Keys.OrderState.ORDER_STATE_50.getIndex());
		oi.setIs_check(Keys.AddIsCleck.ADD_IS_CLECK_0.getIndex());
		oi.getMap().put("st_finish_date", st_finish_date);
		oi.getMap().put("en_finish_date", en_finish_date);
		List<OrderInfo> entityList = super.getFacade().getOrderInfoService().getselectCheckList(oi);
		if (null == entityList || entityList.size() == 0) {
			String msg = "查询对账申请失败，请反馈";
			return super.showMsgForManager(request, response, msg);
		}
		oi = entityList.get(0);
		if (null == oi) {
			String msg = entp_id + "订单异常，无法对账。";
			return super.showMsgForManager(request, response, msg);
		}
		BigDecimal sum_huokuan = (BigDecimal) oi.getMap().get("sum_huokuan");//订单货款
		BigDecimal sum_order_money = (BigDecimal) oi.getMap().get("sum_order_money");//订单金额
		
		// 查看商家对账记录，有未对账的则无法对账
		EntpDuiZhang entpDZ = new EntpDuiZhang();
		entpDZ.setEntp_id(Integer.valueOf(entp_id));
		entpDZ.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		entpDZ.setIs_check(Keys.IsCleck.IS_CLECK_0.getIndex());
		int count = super.getFacade().getEntpDuiZhangService().getEntpDuiZhangCount(entpDZ);
		if (count > 0) {
			String msg = "你有" + count + "条对账在审核，无法新添对账！";
			return super.showMsgForManager(request, response, msg);
		}

		// 得到商家货款币
		UserInfo uInfo = new UserInfo();
		uInfo.setOwn_entp_id(Integer.valueOf(entp_id));
		uInfo.setIs_entp(1);
		uInfo.setIs_del(0);
		uInfo = super.getFacade().getUserInfoService().getUserInfo(uInfo);
		if (null == uInfo) {
			String msg = "没有得到商家信息，请联系管理员查看商家是否为空！";
			return super.showMsgForManager(request, response, msg);
		}
		BigDecimal bi_huokuan = uInfo.getBi_huokuan();
		if (bi_huokuan.compareTo(sum_huokuan) == -1) {
			String msg = "货款币不足，无法对账！";
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

		// 插入对账申请记录
		EntpDuiZhang edz = new EntpDuiZhang();
		edz.setSum_money(sum_huokuan);
		edz.setSum_order_money(sum_order_money);
		edz.setEntp_id(Integer.valueOf(entp_id));
		edz.setEntp_name(uInfo.getOwn_entp_name()+"<br/>开户行："+(null==uInfo.getBank_name()?"":uInfo.getBank_name())+"<br/>开户账号："+(null==uInfo.getBank_account()?"":uInfo.getBank_account())+"<br/>开户名："+(null==uInfo.getBank_account_name()?"":uInfo.getBank_account_name()));
		edz.setAdd_user_id(userInfo.getId());
		edz.setAdd_user_name(userInfo.getUser_name());
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
		if (StringUtils.isBlank(entp_id)) {
			String msg = "对账商家为空，无法进行对账";
			return super.showMsgForManager(request, response, msg);
		}
		orderInfo.getMap().put("check_entp_id", entp_id);
		orderInfo.getMap().put("st_finish_date", st_finish_date);
		orderInfo.getMap().put("en_finish_date", en_finish_date);
		orderInfo.getMap().put("opt_order_state", Keys.OrderState.ORDER_STATE_50.getIndex());

		edz.getMap().put("update_uInfo", uInfoSub);
		edz.getMap().put("insert_ubr", ubr);
		edz.getMap().put("update_orderInfo", orderInfo);

		super.getFacade().getEntpDuiZhangService().createEntpDuiZhang(edz);
		super.renderJavaScript(response, "alert('".concat("添加成功").concat("');location.href='MerchantCheck.do?';"));
		return null;

	}

	// 批量对账
	public ActionForward batchCheck(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		saveToken(request);
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return super.showMsgForManager(request, response, msg);
		}
		JSONObject data = new JSONObject();
		UserInfo userInfo = new UserInfo();
		userInfo.setId(ui.getId());
		userInfo = super.getFacade().getUserInfoService().getUserInfo(userInfo);
		String code = "0", msg = "";
		DynaBean dynaBean = (DynaBean) form;
		String st_finish_date = (String) dynaBean.get("st_finish_date");
		String en_finish_date = (String) dynaBean.get("en_finish_date");
		String[] pks = (String[]) dynaBean.get("pks");

		for (String entp_id : pks) {

			OrderInfo entity = new OrderInfo();
			entity.setEntp_id(Integer.valueOf(entp_id));
			entity.setOrder_state(Keys.OrderState.ORDER_STATE_50.getIndex());
			entity.setIs_check(Keys.AddIsCleck.ADD_IS_CLECK_0.getIndex());
			entity.getMap().put("st_finish_date", st_finish_date);
			entity.getMap().put("en_finish_date", en_finish_date);
			List<OrderInfo> entityList = super.getFacade().getOrderInfoService().getselectCheckList(entity);
			if (null == entityList || entityList.size() == 0) {
				msg = "查询对账申请失败，请反馈";
				return super.showMsgForManager(request, response, msg);
			}
			entity = entityList.get(0);
			if (null == entity) {
				msg += entp_id + "订单异常，无法对账。";
				break;
			}
			BigDecimal sum_huokuan = (BigDecimal) entity.getMap().get("sum_huokuan");//订单货款
			BigDecimal sum_order_money = (BigDecimal) entity.getMap().get("sum_order_money");//订单金额

			// 得到商家货款币
			UserInfo uInfo = new UserInfo();
			uInfo.setOwn_entp_id(Integer.valueOf(entp_id));
			uInfo.setIs_entp(1);
			uInfo.setIs_del(0);
			uInfo = getFacade().getUserInfoService().getUserInfo(uInfo);
			if (null == uInfo) {
				msg += entp_id + "商家不存在，无法对账。";
				continue;
			}
			BigDecimal bi_huokuan = uInfo.getBi_huokuan();
			if (bi_huokuan.compareTo(sum_huokuan) == -1) {
				msg += entity.getMap().get("entp_name") + "商家货款币不足，无法对账。";
				continue;
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

			// 插入对账申请记录
			EntpDuiZhang edz = new EntpDuiZhang();
			edz.setSum_money(sum_huokuan);
			edz.setSum_order_money(sum_order_money);
			edz.setEntp_id(Integer.parseInt(entp_id));
			edz.setEntp_name(uInfo.getOwn_entp_name()+"<br/>开户行："+(null==uInfo.getBank_name()?"":uInfo.getBank_name())+"<br/>开户账号："+(null==uInfo.getBank_account()?"":uInfo.getBank_account())+"<br/>开户名："+(null==uInfo.getBank_account_name()?"":uInfo.getBank_account_name()));
			edz.setAdd_user_id(userInfo.getId());
			edz.setAdd_user_name(userInfo.getUser_name());
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

			if (StringUtils.isBlank(entp_id)) {
				msg = "对账企业为空，无法进行对账";
				return super.showMsgForManager(request, response, msg);
			}
			orderInfo.getMap().put("check_entp_id", entp_id);
			orderInfo.getMap().put("st_finish_date", st_finish_date);
			orderInfo.getMap().put("en_finish_date", en_finish_date);
			orderInfo.getMap().put("opt_order_state", Keys.OrderState.ORDER_STATE_50.getIndex());

			edz.getMap().put("update_uInfo", uInfoSub);
			edz.getMap().put("insert_ubr", ubr);
			edz.getMap().put("update_orderInfo", orderInfo);
			super.getFacade().getEntpDuiZhangService().createEntpDuiZhang(edz);
		}
		msg = msg + "批量对账申请成功!";
		code = "1";
		super.ajaxReturnInfo(response, code, msg, data);
		return null;
	}
}
