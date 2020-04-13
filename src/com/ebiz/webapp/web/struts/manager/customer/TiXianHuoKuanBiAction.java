package com.ebiz.webapp.web.struts.manager.customer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.BaseData;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.UserMoneyApply;
import com.ebiz.webapp.web.Keys;

/**
 * 余额提现
 * 
 * @author Administrator
 */
public class TiXianHuoKuanBiAction extends BaseCustomerAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);
		UserInfo ui = super.getUserInfoFromSession(request);

		Pager pager = (Pager) dynaBean.get("pager");
		String info_state = (String) dynaBean.get("info_state");

		UserMoneyApply entity = new UserMoneyApply();
		super.copyProperties(entity, form);
		entity.setUser_id(ui.getId());
		entity.setCash_type(Keys.CASH_TYPE.CASH_TYPE_30.getIndex());
		entity.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		entity.getMap().put("orderByAddDateDesc", "true");
		if (StringUtils.isNotBlank(info_state) && GenericValidator.isInt(info_state)) {
			entity.setInfo_state(Integer.valueOf(info_state));
		}

		Integer recordCount = getFacade().getUserMoneyApplyService().getUserMoneyApplyCount(entity);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<UserMoneyApply> list = getFacade().getUserMoneyApplyService().getUserMoneyApplyPaginatedList(entity);
		request.setAttribute("entityList", list);
		return mapping.findForward("list");

	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		saveToken(request);
		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);
		UserInfo ui = super.getUserInfoFromSession(request);

		UserMoneyApply uma = new UserMoneyApply();
		uma.setInfo_state(0);
		uma.setUser_id(ui.getId());
		uma.setCash_type(Keys.CASH_TYPE.CASH_TYPE_30.getIndex());
		uma.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		int count = getFacade().getUserMoneyApplyService().getUserMoneyApplyCount(uma);
		if (count > 0) {
			String msg = "您有" + count + "个待审核的货款提现申请，不能进行货款提现操作！";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		UserMoneyApply umaQueryDate = new UserMoneyApply();
		umaQueryDate.setUser_id(ui.getId());
		umaQueryDate.setCash_type(Keys.CASH_TYPE.CASH_TYPE_30.getIndex());
		umaQueryDate.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		umaQueryDate.getMap().put("add_begin_date", sdFormat_ymd.format(new Date()));
		umaQueryDate.getMap().put("add_end_date", sdFormat_ymd.format(new Date()));

		int countDate = getFacade().getUserMoneyApplyService().getUserMoneyApplyCount(umaQueryDate);

		if (countDate > 0) {
			String msg = "一天只能进行一笔货款提现操作！";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		UserInfo userInfo = new UserInfo();
		userInfo.setId(ui.getId());
		userInfo = getFacade().getUserInfoService().getUserInfo(userInfo);
		if (null == userInfo) {
			String msg = "用户名不存在或者已经被删除！";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		dynaBean.set("bi_huokuan", userInfo.getBi_huokuan());

		BaseData baseDate = new BaseData();
		baseDate.setId(Keys.BASE_DATA_ID.BASE_DATA_ID_601.getIndex());
		baseDate = getFacade().getBaseDataService().getBaseData(baseDate);
		request.setAttribute("pre_number", baseDate.getPre_number());// 货款提现手续费

		BigDecimal huokuan_2_rmb = super
				.BiToBi(userInfo.getBi_huokuan(), Keys.BASE_DATA_ID.BASE_DATA_ID_905.getIndex());
		request.setAttribute("huokuan_2_rmb", huokuan_2_rmb);

		if (StringUtils.isBlank(userInfo.getBank_account()) || StringUtils.isBlank(userInfo.getBank_branch_name())) {
			dynaBean.set("bank_account_is_empty", "true");
		}
		if (StringUtils.isBlank(userInfo.getBank_account()) && StringUtils.isBlank(userInfo.getPassword_pay())) {
			dynaBean.set("all_is_empty", "true");
		}
		if (null == userInfo.getIs_renzheng() || userInfo.getIs_renzheng().intValue() == 0) {
			dynaBean.set("renzheng_is_empty", "true");
		}

		return mapping.findForward("input");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (isCancelled(request)) {
			return list(mapping, form, request, response);
		}
		if (!isTokenValid(request)) {
			saveError(request, "errors.token");
			return list(mapping, form, request, response);
		}

		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);
		UserInfo userInfo = new UserInfo();
		userInfo.setId(ui.getId());
		userInfo = getFacade().getUserInfoService().getUserInfo(userInfo);
		if (null == userInfo) {
			String msg = "用户名不存在或者已经被删除！";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		if (null == userInfo.getIs_renzheng() || userInfo.getIs_renzheng() == 0) {
			String msg = "你还未实名认证，请前往安全中心进行实名认证！";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		DynaBean dynaBean = (DynaBean) form;
		String mod_id = (String) dynaBean.get("mod_id");
		String par_id = (String) dynaBean.get("par_id");
		String cash_count = (String) dynaBean.get("cash_count");
		String add_memo = (String) dynaBean.get("add_memo");

		if (!GenericValidator.isDouble(cash_count)) {
			String msg = "请正确输入金额";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		UserMoneyApply entity = new UserMoneyApply();

		double bi_huokuan = userInfo.getBi_huokuan().intValue();// 当前货款数量
		double rmb_2_huokuan = super.BiToBi2(new BigDecimal(cash_count), Keys.BASE_DATA_ID.BASE_DATA_ID_905.getIndex())
				.doubleValue();
		if (rmb_2_huokuan > bi_huokuan) {
			String msg = "申请金额大于最大限额，请重新输入金额。";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		BaseData baseDate = new BaseData();
		baseDate.setId(Keys.BASE_DATA_ID.BASE_DATA_ID_601.getIndex());
		baseDate = getFacade().getBaseDataService().getBaseData(baseDate);
		double pre_number = baseDate.getPre_number().doubleValue();
		double rate = pre_number / 100;// 货款提现手续费比例

		// 实际支付金额 = 申请金额 - 申请金额 *手续费比例
		double cash_pay = Double.valueOf(cash_count) - Double.valueOf(cash_count) * rate;
		// double bi_huokuan_cash_pay = bi_huokuan_bili - (bi_huokuan_bili * bi_huokuan_shouxufeibili);

		resetToken(request);

		super.copyProperties(entity, form);
		entity.setUser_id(userInfo.getId());
		entity.setCash_type(Keys.CASH_TYPE.CASH_TYPE_30.getIndex());
		entity.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		entity.setUser_id(ui.getId());
		entity.setAdd_uid(ui.getId());
		entity.setAdd_date(new Date());
		entity.setCash_type(30);
		entity.setCash_count(new BigDecimal(cash_count));
		entity.setCash_rate(new BigDecimal(pre_number));
		entity.setCash_pay(new BigDecimal(cash_pay));
		entity.setAdd_memo(add_memo);
		entity.setInfo_state(0);
		entity.setReal_name(userInfo.getUser_name());
		entity.setMobile(userInfo.getMobile());
		entity.setBank_name(userInfo.getBank_name() + userInfo.getBank_pname() + userInfo.getBank_branch_name());
		entity.setBank_account(userInfo.getBank_account());
		entity.setBank_account_name(userInfo.getBank_account_name());
		entity.setCash_count_before(userInfo.getBi_huokuan());
		entity.setCash_count_after(userInfo.getBi_huokuan().subtract(new BigDecimal(cash_count)));
		entity.getMap().put("user_id", ui.getId());

		super.getFacade().getUserMoneyApplyService().createUserMoneyApply(entity);

		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&par_id=" + par_id);
		pathBuffer.append("&");
		// pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;

	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		getsonSysModuleList(request, dynaBean);

		if (StringUtils.isNotBlank(id) && GenericValidator.isLong(id)) {
			UserMoneyApply entity = new UserMoneyApply();
			entity.setId(new Integer(id));
			entity = getFacade().getUserMoneyApplyService().getUserMoneyApply(entity);

			// the line below is added for pagination
			entity.setQueryString(super.serialize(request, "id", "method"));
			// end
			super.copyProperties(form, entity);

		}
		return mapping.findForward("view");
	}
}