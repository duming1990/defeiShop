package com.ebiz.webapp.web.struts.m;

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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.BaseData;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.UserMoneyApply;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.DateTools;
import com.ebiz.webapp.web.util.EncryptUtilsV2;

public class MTiXianDianZiBiAction extends MBaseWebAction {
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.add(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}
		super.getModNameForMobile(request);

		request.setAttribute("titleSideName", Keys.TopBtns.ADD.getName());

		DynaBean dynaBean = (DynaBean) form;
		String cash_type = (String) dynaBean.get("cash_type");

		UserInfo user = new UserInfo();
		user.setId(ui.getId());
		user = getFacade().getUserInfoService().getUserInfo(user);
		request.setAttribute("bi_dianzi", user.getBi_dianzi());
		request.setAttribute("bi_dianzi_lock", user.getBi_dianzi_lock());

		UserMoneyApply entity = new UserMoneyApply();

		Pager pager = (Pager) dynaBean.get("pager");
		String info_state = (String) dynaBean.get("info_state");
		super.copyProperties(entity, form);
		entity.setUser_id(ui.getId());
		if (StringUtils.isNotBlank(cash_type)) {
			entity.setCash_type(Integer.valueOf(cash_type));
			request.setAttribute("header_title", Keys.CASH_TYPE.CASH_TYPE_40.getName());
			dynaBean.set("cash_type", cash_type);
		} else {
			entity.setCash_type(Keys.CASH_TYPE.CASH_TYPE_10.getIndex());
		}
		entity.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());

		entity.getMap().put("orderByAddDateDesc", "true");

		if (StringUtils.isNotBlank(info_state) && GenericValidator.isInt(info_state)) {
			entity.setInfo_state(Integer.valueOf(info_state));
		}
		Integer pageSize = 10;

		Integer recordCount = getFacade().getUserMoneyApplyService().getUserMoneyApplyCount(entity);
		pager.init(recordCount.longValue(), pageSize, pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<UserMoneyApply> list = getFacade().getUserMoneyApplyService().getUserMoneyApplyPaginatedList(entity);
		request.setAttribute("entityList", list);
		if (list.size() == Integer.valueOf(pageSize)) {
			request.setAttribute("appendMore", 1);
		}
		dynaBean.set("pageSize", pageSize);

		return mapping.findForward("list");
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;

		super.getModNameForMobile(request);
		String id = (String) dynaBean.get("id");

		if (StringUtils.isNotBlank(id) && GenericValidator.isLong(id)) {
			UserMoneyApply entity = new UserMoneyApply();
			entity.setId(new Integer(id));
			entity = getFacade().getUserMoneyApplyService().getUserMoneyApply(entity);

			// the line below is added for pagination
			entity.setQueryString(super.serialize(request, "id", "method"));
			// end
			super.copyProperties(form, entity);
			if (entity != null && entity.getCash_type() == Keys.CASH_TYPE.CASH_TYPE_40.getIndex()) {
				request.setAttribute("header_title", "福利金提现");
			}
		}
		return mapping.findForward("view");
	}

	public ActionForward getListJson(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}

		DynaBean dynaBean = (DynaBean) form;
		String startPage = (String) dynaBean.get("startPage");
		String pageSize = (String) dynaBean.get("pageSize");
		String cash_type = (String) dynaBean.get("cash_type");

		if (StringUtils.isBlank(pageSize)) {
			pageSize = "10";
		}
		if (StringUtils.isBlank(startPage)) {
			startPage = "0";
		}

		UserMoneyApply entity = new UserMoneyApply();

		Pager pager = (Pager) dynaBean.get("pager");
		String info_state = (String) dynaBean.get("info_state");
		super.copyProperties(entity, form);
		entity.setUser_id(ui.getId());
		if (StringUtils.isNotBlank(cash_type)) {
			entity.setCash_type(Integer.valueOf(cash_type));
		} else {
			entity.setCash_type(Keys.CASH_TYPE.CASH_TYPE_10.getIndex());
		}
		entity.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());

		entity.getMap().put("orderByAddDateDesc", "true");

		if (StringUtils.isNotBlank(info_state) && GenericValidator.isInt(info_state)) {
			entity.setInfo_state(Integer.valueOf(info_state));
		}

		Integer recordCount = getFacade().getUserMoneyApplyService().getUserMoneyApplyCount(entity);
		pager.init(recordCount.longValue(), new Integer(pageSize), pager.getRequestPage());
		entity.getRow().setFirst(Integer.valueOf(startPage) * Integer.valueOf(pageSize));
		entity.getRow().setCount(pager.getRowCount());

		List<UserMoneyApply> list = getFacade().getUserMoneyApplyService().getUserMoneyApplyPaginatedList(entity);

		JSONObject datas = new JSONObject();
		JSONArray dataLoadList = new JSONArray();
		if ((null != list) && (list.size() > 0)) {
			for (UserMoneyApply temp : list) {
				JSONObject map = new JSONObject();
				map.put("id", temp.getId());
				map.put("add_date", DateTools.getStringDate(temp.getAdd_date(), "yyyy-MM-dd HH:mm:ss"));
				map.put("cash_count", temp.getCash_count());
				map.put("bi", temp.getCash_rate());
				map.put("cash_pay", temp.getCash_pay());
				map.put("info_state", temp.getInfo_state());
				dataLoadList.add(map);
			}
			datas.put("ret", "1");
			datas.put("msg", "查询成功");
			datas.put("appendMore", "0");
			if (list.size() == Integer.valueOf(pageSize)) {
				datas.put("appendMore", "1");
			}
			datas.put("dataList", dataLoadList.toString());
		} else {
			datas.put("ret", "0");
			datas.put("msg", "已全部加载");
		}

		super.renderJson(response, datas.toString());
		return null;
	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		saveToken(request);
		DynaBean dynaBean = (DynaBean) form;
		super.getModNameForMobile(request);
		UserInfo ui = super.getUserInfoFromSession(request);

		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}

		request.setAttribute("titleSideName", Keys.TopBtns.VIEW.getName());

		UserMoneyApply uma = new UserMoneyApply();
		uma.setInfo_state(0);
		uma.setUser_id(ui.getId());
		uma.setCash_type(Keys.CASH_TYPE.CASH_TYPE_10.getIndex());
		uma.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		int count = getFacade().getUserMoneyApplyService().getUserMoneyApplyCount(uma);
		if (count > 0) {
			String msg = "您有" + count + "个待审核的提现申请，不能进行申请提现操作！";
			return super.showTipMsg(mapping, form, request, response, msg);
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
			return super.showTipMsg(mapping, form, request, response, msg);
		}

		// if (StringUtils.isBlank(userInfo.getUser_no())) {
		// String bind_msg = "您未绑定会员卡，不能进行申请提现操作！";
		// request.setAttribute("bind_msg", bind_msg);
		// return mapping.findForward("input");
		// }
		BaseData baseDate = new BaseData();
		baseDate.setId(Keys.BASE_DATA_ID.BASE_DATA_ID_602.getIndex());
		baseDate = getFacade().getBaseDataService().getBaseData(baseDate);
		request.setAttribute("pre_number", baseDate.getPre_number());// 余额提现手续费

		BigDecimal dianzibi_2_rmb = super
				.BiToBi(userInfo.getBi_dianzi(), Keys.BASE_DATA_ID.BASE_DATA_ID_904.getIndex()).subtract(
						userInfo.getBi_welfare());
		request.setAttribute("dianzi_2_rmb", dianzibi_2_rmb);

		super.copyProperties(form, userInfo);
		dynaBean.set("password_pay", "");

		if (StringUtils.isBlank(userInfo.getPassword_pay())) {
			dynaBean.set("password_pay_is_empty", "true");
		}
		if (StringUtils.isBlank(userInfo.getBank_account()) || StringUtils.isBlank(userInfo.getBank_branch_name())) {
			dynaBean.set("bank_account_is_empty", "true");
		}
		if (StringUtils.isBlank(userInfo.getBank_account()) && StringUtils.isBlank(userInfo.getPassword_pay())) {
			dynaBean.set("all_is_empty", "true");
		}
		if (null == userInfo.getIs_renzheng() || userInfo.getIs_renzheng().intValue() == 0) {
			dynaBean.set("renzheng_is_empty", "true");
		}
		// 当前用户已提现金额
		// BigDecimal has_tixian_money = super.getHasTiXianMoney(request, userInfo);
		// request.setAttribute("has_tixian_money", has_tixian_money);
		// 最低提现金额
		int cash_Min = super.getBaseData(Keys.BASE_DATA_ID.BASE_DATA_ID_604.getIndex()).getPre_number();
		request.setAttribute("cash_Min", cash_Min);

		return mapping.findForward("input");
	}

	public ActionForward addWelfare(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		saveToken(request);
		DynaBean dynaBean = (DynaBean) form;
		super.getModNameForMobile(request);
		UserInfo ui = super.getUserInfoFromSession(request);

		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}

		request.setAttribute("titleSideName", Keys.TopBtns.VIEW.getName());

		UserMoneyApply uma = new UserMoneyApply();
		uma.setInfo_state(0);
		uma.setUser_id(ui.getId());
		uma.setCash_type(Keys.CASH_TYPE.CASH_TYPE_40.getIndex());
		uma.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		int count = getFacade().getUserMoneyApplyService().getUserMoneyApplyCount(uma);
		if (count > 0) {
			String msg = "您有" + count + "个待审核的提现申请，不能进行申请提现操作！";
			return super.showTipMsg(mapping, form, request, response, msg);
		}

		UserMoneyApply umaQueryDate = new UserMoneyApply();
		umaQueryDate.setUser_id(ui.getId());
		umaQueryDate.setCash_type(Keys.CASH_TYPE.CASH_TYPE_40.getIndex());
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
			return super.showTipMsg(mapping, form, request, response, msg);
		}

		// 福利金提现手续费
		BaseData baseDate = new BaseData();
		baseDate.setId(Keys.BASE_DATA_ID.BASE_DATA_ID_608.getIndex());
		baseDate = getFacade().getBaseDataService().getBaseData(baseDate);
		request.setAttribute("pre_number", baseDate.getPre_number());

		BigDecimal dianzibi_2_rmb = super.BiToBi(userInfo.getBi_welfare(),
				Keys.BASE_DATA_ID.BASE_DATA_ID_904.getIndex());
		request.setAttribute("dianzi_2_rmb", dianzibi_2_rmb);

		super.copyProperties(form, userInfo);
		dynaBean.set("password_pay", "");

		if (StringUtils.isBlank(userInfo.getPassword_pay())) {
			dynaBean.set("password_pay_is_empty", "true");
		}
		if (StringUtils.isBlank(userInfo.getBank_account()) || StringUtils.isBlank(userInfo.getBank_branch_name())) {
			dynaBean.set("bank_account_is_empty", "true");
		}
		if (StringUtils.isBlank(userInfo.getBank_account()) && StringUtils.isBlank(userInfo.getPassword_pay())) {
			dynaBean.set("all_is_empty", "true");
		}
		if (null == userInfo.getIs_renzheng() || userInfo.getIs_renzheng().intValue() == 0) {
			dynaBean.set("renzheng_is_empty", "true");
		}
		// 最低福利金提现金额
		int cash_Min = super.getBaseData(Keys.BASE_DATA_ID.BASE_DATA_ID_609.getIndex()).getPre_number();
		request.setAttribute("cash_Min", cash_Min);
		request.setAttribute("header_title", "福利金提现");
		dynaBean.set("cash_type", "40");
		return new ActionForward("/../m/MTiXianDianZiBi/welfare_form.jsp");
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
		resetToken(request);

		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		UserInfo userInfo = new UserInfo();
		userInfo.setId(ui.getId());
		userInfo = getFacade().getUserInfoService().getUserInfo(userInfo);
		if (null == userInfo) {
			String msg = "用户名不存在或者已经被删除！";
			return super.showTipMsg(mapping, form, request, response, msg);
		}
		if (null == userInfo.getIs_renzheng() || userInfo.getIs_renzheng() == 0) {
			String msg = "你还未实名认证，请前往安全中心进行实名认证！";
			return super.showTipMsg(mapping, form, request, response, msg);
		}
		if (StringUtils.isBlank(userInfo.getPassword_pay())) {
			String msg = "你还未设置支付密码，请前往设置支付密码！";
			return super.showTipMsg(mapping, form, request, response, msg);
		}

		DynaBean dynaBean = (DynaBean) form;
		String mod_id = (String) dynaBean.get("mod_id");
		String cash_count1 = (String) dynaBean.get("cash_count");
		BigDecimal cash_count = new BigDecimal(cash_count1);
		String add_memo = (String) dynaBean.get("add_memo");
		String password_pay = (String) dynaBean.get("password_pay");

		if (StringUtils.isBlank(password_pay)) {
			String msg = "请输入支付密码";
			return super.showTipMsg(mapping, form, request, response, msg);
		}

		int cash_Min1 = super.getBaseData(Keys.BASE_DATA_ID.BASE_DATA_ID_604.getIndex()).getPre_number();// 最低提现金额
		BigDecimal cash_Min = new BigDecimal(cash_Min1);
		if (cash_count.compareTo(cash_Min) == -1) {
			String msg = "请正确填写提现金额，最低提现金额为" + cash_Min + "元!";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		if (!userInfo.getPassword_pay().equals(EncryptUtilsV2.MD5Encode(password_pay))) {
			String msg = "密码输入错误！";
			return super.showTipMsg(mapping, form, request, response, msg);
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
		// entity.setBank_name(userInfo.getBank_name());
		entity.setBank_name(userInfo.getBank_name() + userInfo.getBank_pname() + userInfo.getBank_branch_name());
		entity.setBank_account(userInfo.getBank_account());
		entity.setBank_account_name(userInfo.getBank_account_name());
		entity.setCash_count_before(userInfo.getBi_dianzi());
		entity.setCash_count_after(userInfo.getBi_dianzi().subtract(cash_count));
		entity.setCash_count_lock(userInfo.getBi_dianzi_lock());
		entity.getMap().put("user_id", ui.getId());

		super.getFacade().getUserMoneyApplyService().createUserMoneyApply(entity);
		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&");
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		return forward;
	}

	public ActionForward saveWelfare(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (isCancelled(request)) {
			return list(mapping, form, request, response);
		}
		if (!isTokenValid(request)) {
			saveError(request, "errors.token");
			return list(mapping, form, request, response);
		}
		resetToken(request);

		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		UserInfo userInfo = new UserInfo();
		userInfo.setId(ui.getId());
		userInfo = getFacade().getUserInfoService().getUserInfo(userInfo);
		if (null == userInfo) {
			String msg = "用户名不存在或者已经被删除！";
			return super.showTipMsg(mapping, form, request, response, msg);
		}
		if (null == userInfo.getIs_renzheng() || userInfo.getIs_renzheng() == 0) {
			String msg = "你还未实名认证，请前往安全中心进行实名认证！";
			return super.showTipMsg(mapping, form, request, response, msg);
		}
		if (StringUtils.isBlank(userInfo.getPassword_pay())) {
			String msg = "你还未设置支付密码，请前往设置支付密码！";
			return super.showTipMsg(mapping, form, request, response, msg);
		}

		DynaBean dynaBean = (DynaBean) form;
		String mod_id = (String) dynaBean.get("mod_id");
		String cash_count1 = (String) dynaBean.get("cash_count");
		BigDecimal cash_count = new BigDecimal(cash_count1);
		String add_memo = (String) dynaBean.get("add_memo");
		String password_pay = (String) dynaBean.get("password_pay");
		String proof_img = (String) dynaBean.get("proof_img");

		if (StringUtils.isBlank(password_pay)) {
			String msg = "请输入支付密码";
			return super.showTipMsg(mapping, form, request, response, msg);
		}

		if (StringUtils.isBlank(proof_img)) {
			String msg = "请上传凭着图片";
			return super.showTipMsg(mapping, form, request, response, msg);
		}

		int cash_Min1 = super.getBaseData(Keys.BASE_DATA_ID.BASE_DATA_ID_609.getIndex()).getPre_number();// 最低提现金额
		BigDecimal cash_Min = new BigDecimal(cash_Min1);
		if (cash_count.compareTo(cash_Min) == -1) {
			String msg = "请正确填写提现金额，最低提现金额为" + cash_Min + "元!";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		if (!userInfo.getPassword_pay().equals(EncryptUtilsV2.MD5Encode(password_pay))) {
			String msg = "密码输入错误！";
			return super.showTipMsg(mapping, form, request, response, msg);
		}

		UserMoneyApply entity = new UserMoneyApply();
		// 当前福利金
		BigDecimal bi_welfare = userInfo.getBi_welfare();
		BigDecimal rmb_2_dianzi = super.BiToBi2(cash_count, Keys.BASE_DATA_ID.BASE_DATA_ID_904.getIndex());
		if (bi_welfare.compareTo(rmb_2_dianzi) == -1) {
			String msg = "申请金额大于最大限额，请重新选择金额。";
			return super.showTipMsg(mapping, form, request, response, msg);
		}

		// 福利金提现手续费比例
		BaseData baseData = new BaseData();
		baseData.setId(Keys.BASE_DATA_ID.BASE_DATA_ID_608.getIndex());
		baseData = getFacade().getBaseDataService().getBaseData(baseData);
		BigDecimal pre_number = BigDecimal.valueOf(baseData.getPre_number());

		BigDecimal num2 = new BigDecimal("1000");
		BigDecimal rate = pre_number.divide(num2);

		BigDecimal shouxufei = cash_count.multiply(rate).setScale(2, BigDecimal.ROUND_HALF_UP);
		BaseData baseDataMTMin = super.getBaseData(Keys.BASE_DATA_ID.BASE_DATA_ID_610.getIndex());
		BaseData baseDataMTMax = super.getBaseData(Keys.BASE_DATA_ID.BASE_DATA_ID_611.getIndex());
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
		entity.setCash_type(Keys.CASH_TYPE.CASH_TYPE_40.getIndex());
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
		entity.setProof_img(proof_img);
		entity.setReal_name(userInfo.getUser_name());
		entity.setMobile(userInfo.getMobile());
		// entity.setBank_name(userInfo.getBank_name());
		entity.setBank_name(userInfo.getBank_name() + userInfo.getBank_pname() + userInfo.getBank_branch_name());
		entity.setBank_account(userInfo.getBank_account());
		entity.setBank_account_name(userInfo.getBank_account_name());
		entity.setCash_count_before(userInfo.getBi_welfare());
		entity.setCash_count_after(userInfo.getBi_welfare().subtract(cash_count));
		entity.setCash_count_lock(userInfo.getBi_dianzi_lock());
		entity.getMap().put("user_id", ui.getId());

		super.getFacade().getUserMoneyApplyService().createUserMoneyApply(entity);
		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&cash_type=40");
		pathBuffer.append("&");
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		return forward;
	}

	public ActionForward getUserMoney(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JSONObject data = new JSONObject();

		String msg = "", code = "0";

		DynaBean dynaBean = (DynaBean) form;
		String cash_count1 = (String) dynaBean.get("cash_count");
		if (null == cash_count1) {
			msg = "提现金额不能为空！";
			data.put("code", code);
			data.put("msg", msg);
			super.renderJson(response, data.toString());
			return null;
		}
		BigDecimal cash_count = new BigDecimal(cash_count1);

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
		BigDecimal MTSxf = MTmoney.subtract(cash_pay);// 手续费= 申请提现金额-实际支付金额

		code = "1";
		data.put("code", code);
		data.put("msg", msg);
		data.put("cash_pay", cash_pay);// 实际支付金额
		data.put("MTSxf", MTSxf);// 手续费
		data.put("MTmoney", MTmoney);// 申请金额
		super.renderJson(response, data.toString());
		return null;

	}

	public ActionForward getUserWelfareMoney(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JSONObject data = new JSONObject();

		String msg = "", code = "0";

		DynaBean dynaBean = (DynaBean) form;
		String cash_count1 = (String) dynaBean.get("cash_count");
		if (null == cash_count1) {
			msg = "提现金额不能为空！";
			data.put("code", code);
			data.put("msg", msg);
			super.renderJson(response, data.toString());
			return null;
		}
		BigDecimal cash_count = new BigDecimal(cash_count1);

		// 福利金提现手续费
		BaseData baseData = new BaseData();
		baseData.setId(Keys.BASE_DATA_ID.BASE_DATA_ID_608.getIndex());
		baseData = getFacade().getBaseDataService().getBaseData(baseData);
		BigDecimal pre_number = BigDecimal.valueOf(baseData.getPre_number());

		BigDecimal num2 = new BigDecimal("1000");
		BigDecimal rate = pre_number.divide(num2);// 货款提现手续费比例

		BigDecimal shouxufei = cash_count.multiply(rate).setScale(2, BigDecimal.ROUND_HALF_UP);
		BaseData baseDataMTMin = super.getBaseData(Keys.BASE_DATA_ID.BASE_DATA_ID_610.getIndex());
		BaseData baseDataMTMax = super.getBaseData(Keys.BASE_DATA_ID.BASE_DATA_ID_611.getIndex());
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
		BigDecimal MTSxf = MTmoney.subtract(cash_pay);// 手续费= 申请提现金额-实际支付金额

		code = "1";
		data.put("code", code);
		data.put("msg", msg);
		data.put("cash_pay", cash_pay);// 实际支付金额
		data.put("MTSxf", MTSxf);// 手续费
		data.put("MTmoney", MTmoney);// 申请金额
		super.renderJson(response, data.toString());
		return null;

	}

}