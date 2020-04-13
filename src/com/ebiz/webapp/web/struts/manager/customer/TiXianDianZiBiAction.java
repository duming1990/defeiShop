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
public class TiXianDianZiBiAction extends BaseCustomerAction {
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
		UserInfo user = new UserInfo();
		user.setId(ui.getId());
		user = getFacade().getUserInfoService().getUserInfo(user);
		request.setAttribute("bi_dianzi", user.getBi_dianzi());

		UserMoneyApply entity = new UserMoneyApply();

		Pager pager = (Pager) dynaBean.get("pager");
		String info_state = (String) dynaBean.get("info_state");
		super.copyProperties(entity, form);
		entity.setUser_id(ui.getId());
		entity.setCash_type(Keys.CASH_TYPE.CASH_TYPE_10.getIndex());
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
		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);
		UserInfo ui = super.getUserInfoFromSession(request);

		UserMoneyApply uma = new UserMoneyApply();
		uma.setInfo_state(0);
		uma.setUser_id(ui.getId());
		uma.setCash_type(Keys.CASH_TYPE.CASH_TYPE_10.getIndex());
		uma.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		int count = getFacade().getUserMoneyApplyService().getUserMoneyApplyCount(uma);
		if (count > 0) {
			String msg = "您有" + count + "个待审核的提现申请，不能进行申请提现操作！";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		UserMoneyApply umaQueryDate = new UserMoneyApply();
		umaQueryDate.setUser_id(ui.getId());
		umaQueryDate.setCash_type(Keys.CASH_TYPE.CASH_TYPE_10.getIndex());
		umaQueryDate.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		umaQueryDate.getMap().put("add_begin_date", sdFormat_ymd.format(new Date()));
		umaQueryDate.getMap().put("add_end_date", sdFormat_ymd.format(new Date()));

		int countDate = getFacade().getUserMoneyApplyService().getUserMoneyApplyCount(umaQueryDate);
		if (countDate > 0) {
			String msg = "一天只能进行一笔余额提现操作！";
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

		BaseData baseDate = new BaseData();
		baseDate.setId(Keys.BASE_DATA_ID.BASE_DATA_ID_602.getIndex());
		baseDate = getFacade().getBaseDataService().getBaseData(baseDate);
		request.setAttribute("pre_number", baseDate.getPre_number());// 余额提现手续费

		BigDecimal dianzibi_2_rmb = super
				.BiToBi(userInfo.getBi_dianzi(), Keys.BASE_DATA_ID.BASE_DATA_ID_904.getIndex()).subtract(
						userInfo.getBi_welfare());
		request.setAttribute("dianzi_2_rmb", dianzibi_2_rmb);

		super.copyProperties(form, userInfo);

		if (StringUtils.isBlank(userInfo.getBank_account()) || StringUtils.isBlank(userInfo.getBank_branch_name())) {
			dynaBean.set("bank_account_is_empty", "true");
		}
		if (null == userInfo.getIs_renzheng() || userInfo.getIs_renzheng().intValue() == 0) {
			dynaBean.set("renzheng_is_empty", "true");
		}
		// 当前用户已提现金额
		// BigDecimal has_tixian_money = super.getHasTiXianMoney(request, userInfo);
		// request.setAttribute("has_tixian_money", has_tixian_money);
		int cash_Min = super.getBaseData(Keys.BASE_DATA_ID.BASE_DATA_ID_604.getIndex()).getPre_number();// 最低提现金额
		request.setAttribute("cash_Min", cash_Min);
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
		String cash_count1 = (String) dynaBean.get("cash_count");
		BigDecimal cash_count = new BigDecimal(cash_count1);
		String add_memo = (String) dynaBean.get("add_memo");

		// if (!GenericValidator.isInt(cash_count)) {
		// String msg = "请正确填写提现金额，且只能是" + Keys.bi_dianzi_tixian_min + "的倍数";
		// super.showMsgForManager(request, response, msg);
		// return null;
		// }
		int cash_Min1 = super.getBaseData(Keys.BASE_DATA_ID.BASE_DATA_ID_604.getIndex()).getPre_number();// 最低提现金额
		BigDecimal cash_Min = new BigDecimal(cash_Min1);
		if (cash_count.compareTo(cash_Min) == -1) {
			String msg = "请正确填写提现金额，最低提现金额为" + cash_Min + "元!";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		UserMoneyApply entity = new UserMoneyApply();

		// 当前余额数量
		BigDecimal bi_dianzi = userInfo.getBi_dianzi().subtract(userInfo.getBi_welfare());// 当前余额数量
		BigDecimal rmb_2_dianzi = super.BiToBi2(cash_count, Keys.BASE_DATA_ID.BASE_DATA_ID_904.getIndex());
		if (bi_dianzi.compareTo(rmb_2_dianzi) == -1) {
			String msg = "申请金额大于最大限额，请重新选择金额。";
			return super.showTipMsg(mapping, form, request, response, msg);
		}

		BaseData baseData = new BaseData();
		baseData.setId(Keys.BASE_DATA_ID.BASE_DATA_ID_602.getIndex());
		baseData = getFacade().getBaseDataService().getBaseData(baseData);
		BigDecimal pre_number = BigDecimal.valueOf(baseData.getPre_number());
		BigDecimal num2 = new BigDecimal("1000");
		BigDecimal rate = pre_number.divide(num2);// 货款提现手续费比例

		BigDecimal shouxufei = cash_count.multiply(rate).setScale(2, BigDecimal.ROUND_HALF_UP);
		BaseData baseDataMTMin = super.getBaseData(Keys.BASE_DATA_ID.BASE_DATA_ID_605.getIndex());
		BaseData baseDataMTMax = super.getBaseData(Keys.BASE_DATA_ID.BASE_DATA_ID_606.getIndex());
		BigDecimal MTmoney = cash_count;// 申请提现金额
		BigDecimal cash_pay;// 实际支付金额
		BigDecimal MTMin = null;// 提现最低手续费
		BigDecimal MTMax = null;// 提现最高手续费
		if (null == baseDataMTMin && null == baseDataMTMax) {
			cash_pay = MTmoney.subtract(shouxufei);
		}
		if (null != baseDataMTMin) {
			MTMin = new BigDecimal(baseDataMTMin.getPre_number().intValue());
		}
		if (null != baseDataMTMax) {
			MTMax = new BigDecimal(baseDataMTMax.getPre_number());
		}
		// 如果手续费》提现最高手续费 手续费就等于提现最高手续费
		// 如果手续费《提现最小手续费 手续费就等于提现最小手续费
		// 如果手续费在两者之间 手续费就计算出来的数值
		if (shouxufei.compareTo(MTMin) == -1) {
			// 实际支付金额 = 申请金额 - 申请金额 *手续费比例
			cash_pay = MTmoney.subtract(MTMin);
		} else if (shouxufei.compareTo(MTMax) == 1) {
			cash_pay = MTmoney.subtract(MTMax);
		} else {
			cash_pay = MTmoney.subtract(shouxufei);
		}
		// super.copyProperties(entity, form);
		entity.setUser_id(userInfo.getId());
		entity.setCash_type(Keys.CASH_TYPE.CASH_TYPE_10.getIndex());
		entity.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		BigDecimal MTSxf = MTmoney.subtract(cash_pay);// 手续费= 申请提现金额-实际支付金额
		entity.setUser_id(ui.getId());
		entity.setAdd_uid(ui.getId());
		entity.setAdd_date(new Date());
		entity.setCash_count(cash_count);
		entity.setCash_rate(MTSxf);
		entity.setCash_pay(cash_pay);
		entity.setAdd_memo(add_memo);
		entity.setInfo_state(0);
		entity.setId(null);

		entity.setReal_name(userInfo.getUser_name());
		entity.setMobile(userInfo.getMobile());
		entity.setBank_name(userInfo.getBank_name() + userInfo.getBank_pname() + userInfo.getBank_branch_name());
		entity.setBank_account(userInfo.getBank_account());
		entity.setBank_account_name(userInfo.getBank_account_name());
		entity.setCash_count_before(userInfo.getBi_dianzi());
		entity.setCash_count_after(userInfo.getBi_dianzi().subtract(cash_count));
		entity.setCash_count_lock(userInfo.getBi_dianzi_lock());
		entity.getMap().put("user_id", ui.getId());

		super.getFacade().getUserMoneyApplyService().createUserMoneyApply(entity);

		resetToken(request);
		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&par_id=" + par_id);
		pathBuffer.append("&");
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;

	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		saveToken(request);
		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);
		String id = (String) dynaBean.get("id");

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