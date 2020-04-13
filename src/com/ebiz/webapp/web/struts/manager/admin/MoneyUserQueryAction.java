package com.ebiz.webapp.web.struts.manager.admin;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.alibaba.fastjson.JSONObject;
import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.Msg;
import com.ebiz.webapp.domain.MsgReceiver;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.UserBiRecord;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.UserMoneyApply;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.Keys.UserType;
import com.ebiz.webapp.web.util.EncryptUtilsV2;

/**
 * @author Wu,Yang
 */
public class MoneyUserQueryAction extends BaseAdminAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward pay(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "17")) {
			return super.checkPopedomInvalid(request, response);
		}
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String mod_id = (String) dynaBean.get("mod_id");
		JSONObject data = new JSONObject();
		String ret = "0", msg = "参数错误！";
		UserInfo ui = super.getUserInfoFromSession(request);

		if (!StringUtils.isBlank(id) && GenericValidator.isLong(id)) {
			UserMoneyApply entity = new UserMoneyApply();
			super.copyProperties(entity, form);
			entity.setId(new Integer(id));
			entity.setInfo_state(Keys.INFO_STATE.INFO_STATE_2.getIndex());
			entity.getMap().put("update_tixian_tongji_num", true);
			entity.getMap().put("modify_id", ui.getId());
			getFacade().getUserMoneyApplyService().modifyUserMoneyApply(entity);
			ret = "1";
			msg = "确认付款成功！";
		}
		data.put("ret", ret);
		data.put("msg", msg);
		super.renderJson(response, data.toString());
		return null;
	}

	public ActionForward payAll(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (null == super.checkUserModPopeDom(form, request, "17")) {
			return super.checkPopedomInvalid(request, response);
		}

		DynaBean dynaBean = (DynaBean) form;

		String[] pks = (String[]) dynaBean.get("pks");

		UserInfo ui = super.getUserInfoFromSession(request);

		if (ArrayUtils.isEmpty(pks)) {
			super.renderText(response, "请选择一个选项");
			return null;
		}

		UserMoneyApply entity = new UserMoneyApply();
		entity.getMap().put("pks", pks);
		entity.setInfo_state(Keys.INFO_STATE.INFO_STATE_2.getIndex());
		entity.getMap().put("update_tixian_tongji_num_pks", true);
		entity.getMap().put("modify_id", ui.getId());
		getFacade().getUserMoneyApplyService().modifyUserMoneyApply(entity);

		super.renderText(response, "批量付款已成功！");
		return null;
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;

		String id = (String) dynaBean.get("id");
		String mod_id = (String) dynaBean.get("mod_id");

		if (!StringUtils.isBlank(id) && GenericValidator.isLong(id)) {
			UserMoneyApply entity = new UserMoneyApply();
			entity.setId(new Integer(id));
			entity.setInfo_state(Keys.INFO_STATE.INFO_STATE_2.getIndex());
			getFacade().getUserMoneyApplyService().modifyUserMoneyApply(entity);
		}

		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(super.serialize(request, "id", "method")));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "0")) {
			return super.checkPopedomInvalid(request, response);
		}
		setNaviStringToRequestScope(request);// 导航

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String add_begin_date = (String) dynaBean.get("add_begin_date");
		String add_end_date = (String) dynaBean.get("add_end_date");
		String audit_begin_date = (String) dynaBean.get("audit_begin_date");
		String audit_end_date = (String) dynaBean.get("audit_end_date");
		String mobile_like = (String) dynaBean.get("mobile_like");
		String user_name_like = (String) dynaBean.get("real_name_like");

		UserMoneyApply userMoneyApply = new UserMoneyApply();
		// 查询
		super.copyProperties(userMoneyApply, form);

		userMoneyApply.getMap().put("user_name_like", user_name_like);
		userMoneyApply.getMap().put("mobile_like", mobile_like);
		userMoneyApply.getMap().put("add_begin_date", add_begin_date);
		userMoneyApply.getMap().put("add_end_date", add_end_date);
		userMoneyApply.getMap().put("audit_begin_date", audit_begin_date);
		userMoneyApply.getMap().put("audit_end_date", audit_end_date);

		// userMoneyApply.setInfo_state(Keys.INFO_STATE.INFO_STATE_0.getIndex());
		userMoneyApply.setCash_type(Keys.CASH_TYPE.CASH_TYPE_10.getIndex());
		userMoneyApply.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());

		UserMoneyApply uma = new UserMoneyApply();
		super.copyProperties(uma, userMoneyApply);
		uma = super.getFacade().getUserMoneyApplyService().getUserMoneyApplyForMoneyTongJi(userMoneyApply);
		request.setAttribute("uma", uma);

		Integer recordCount = getFacade().getUserMoneyApplyService().getUserMoneyApplyCount(userMoneyApply);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		userMoneyApply.getRow().setFirst(pager.getFirstRow());
		userMoneyApply.getRow().setCount(pager.getRowCount());

		List<UserMoneyApply> userMoneyApplyList = getFacade().getUserMoneyApplyService()
				.getUserMoneyApplyPaginatedList(userMoneyApply);
		if (userMoneyApplyList != null && userMoneyApplyList.size() > 0) {
			for (UserMoneyApply temp : userMoneyApplyList) {
				// 查询已提现金额
				BigDecimal has_tixian_money = new BigDecimal(0);
				UserBiRecord uRecord = new UserBiRecord();
				uRecord.setAdd_user_id(temp.getUser_id());
				uRecord.setBi_chu_or_ru(Keys.BI_CHU_OR_RU.BASE_DATA_ID_NEGATIVE1.getIndex());
				uRecord.setBi_type(Keys.BiType.BI_TYPE_100.getIndex());
				uRecord.setBi_get_type(Keys.BiGetType.BI_OUT_TYPE_50.getIndex());
				List<UserBiRecord> recordList = getFacade().getUserBiRecordService().getUserBiRecordList(uRecord);
				if (recordList != null && recordList.size() > 0) {
					for (UserBiRecord dm : recordList) {
						has_tixian_money = has_tixian_money.add(dm.getBi_no());
					}
				}
				temp.getMap().put("has_tixian_money", has_tixian_money);
				// 查询累计消费金额
				BigDecimal leiji_xiaofei_money = new BigDecimal(0);
				OrderInfo oInfo = new OrderInfo();
				oInfo.setAdd_user_id(temp.getUser_id());
				oInfo.setOrder_state(Keys.OrderState.ORDER_STATE_50.getIndex());
				List<OrderInfo> orderList = getFacade().getOrderInfoService().getOrderInfoList(oInfo);
				if (orderList != null && orderList.size() > 0) {
					for (OrderInfo dm : orderList) {
						leiji_xiaofei_money = leiji_xiaofei_money.add(dm.getOrder_money());
					}
				}
				temp.getMap().put("leiji_xiaofei_money", leiji_xiaofei_money);
				// 查询用户信息
				UserInfo userInfo = super.getUserInfo(temp.getUser_id());
				if (userInfo != null) {
					String user_type_name = "";
					for (UserType type_name : Keys.UserType.values()) {
						if (userInfo.getUser_type() == type_name.getIndex()) {
							user_type_name = type_name.getName();
						}
					}
					temp.getMap().put("user_type_name", user_type_name);
					temp.getMap().put("userInfo", userInfo);
				}
			}
		}

		request.setAttribute("userMoneyApplyList", userMoneyApplyList);

		return mapping.findForward("list");
	}

	public ActionForward audit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		super.setBaseDataListToSession(10, request);// 用户类型

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (GenericValidator.isLong(id)) {
			UserMoneyApply entity = new UserMoneyApply();
			entity.setId(new Integer(id));
			entity = getFacade().getUserMoneyApplyService().getUserMoneyApply(entity);

			if (null == entity) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");

			}
			UserInfo userInfo = new UserInfo();
			userInfo.setId(entity.getUser_id());
			userInfo = getFacade().getUserInfoService().getUserInfo(userInfo);
			if (null != userInfo) {
				request.setAttribute("real_name", userInfo.getReal_name());
				request.setAttribute("mobile", userInfo.getMobile());
			}

			// the line below is added for pagination
			entity.setQueryString(super.serialize(request, "id", "method"));
			// end
			super.copyProperties(form, entity);

			return new ActionForward("/../manager/admin/MoneyUserQuery/audit.jsp");
		} else {
			this.saveError(request, "errors.Integer", new String[] { id });
			return mapping.findForward("list");
		}
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		String mod_id = (String) dynaBean.get("mod_id");
		String id = (String) dynaBean.get("id");
		String info_state = (String) dynaBean.get("info_state");
		String audit_demo = (String) dynaBean.get("audit_demo");
		HttpSession session = request.getSession();
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		UserMoneyApply entity = new UserMoneyApply();
		super.copyProperties(entity, form);

		if (GenericValidator.isLong(id)) {
			UserMoneyApply userMoneyApply = new UserMoneyApply();
			userMoneyApply.setId(new Integer(id));
			// userMoneyApply.setUser_id(ui.getId());
			userMoneyApply.setIs_del(0);
			userMoneyApply = getFacade().getUserMoneyApplyService().getUserMoneyApply(userMoneyApply);
			entity.setCash_count(userMoneyApply.getCash_count());

			// entity.setInfo_state(new Integer(info_state));
			// entity.setAudit_memo(audit_demo);
			entity.setId(new Integer(id));
			entity.setCash_type(Keys.CASH_TYPE.CASH_TYPE_10.getIndex());
			entity.setInfo_state(Integer.valueOf(info_state));
			entity.setAudit_date(new Date());
			entity.setAdd_uid(ui.getId());
			entity.setAudit_uid(ui.getId());
			entity.getMap().put("getCashcount", "true");
			getFacade().getUserMoneyApplyService().modifyUserMoneyApply(entity);
			saveMessage(request, "entity.updated");
		}

		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=" + mod_id);
		// pathBuffer.append("&id" + id);
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (GenericValidator.isLong(id)) {
			Msg msg = new Msg();
			msg.setId(new Integer(id));
			Msg entity = getFacade().getMsgService().getMsg(msg);
			if (null == entity) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");
			}
			super.copyProperties(form, entity);

			Msg r_msg = new Msg();
			r_msg.setReplay_id(Integer.valueOf(id));
			r_msg.setIs_replay_msg(1);
			List<Msg> rMsgList = super.getFacade().getMsgService().getMsgList(r_msg);
			request.setAttribute("rMsgList", rMsgList);

			for (Msg msg1 : rMsgList) {
				Msg msg2 = new Msg();
				msg2.setReplay_id(msg1.getId());
				List<Msg> r_rMsgList = new ArrayList<Msg>();
				r_rMsgList = super.getFacade().getMsgService().getMsgList(msg2);
				msg1.setMsgList(r_rMsgList);
			}
			return mapping.findForward("view");
		} else {
			this.saveError(request, "errors.long", new String[] { id });
			return mapping.findForward("list");
		}
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (GenericValidator.isLong(id)) {
			Msg msg = new Msg();
			msg.setId(new Integer(id));
			Msg entity = getFacade().getMsgService().getMsg(msg);
			if (null == entity) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");
			}
			MsgReceiver msgRecever = new MsgReceiver();
			msgRecever.setMsg_id(entity.getId());
			List<MsgReceiver> msgReceiverList = getFacade().getMsgReceiverService().getMsgReceiverList(msgRecever);
			String _names = "";
			for (MsgReceiver mr : msgReceiverList) {
				UserInfo userInfo = new UserInfo();
				userInfo.setId(mr.getReceiver_user_id());
				userInfo = getFacade().getUserInfoService().getUserInfo(userInfo);
				if (null != userInfo) {
					_names += "," + userInfo.getUser_name();
				}
			}
			_names = StringUtils.substring(_names, 1);

			// the line below is added for pagination
			entity.setQueryString(super.serialize(request, "id", "method"));
			// end
			super.copyProperties(form, entity);
			dynaBean.set("isShowTree", "0");
			dynaBean.set("userNames", _names);
			return mapping.findForward("input");
		} else {
			this.saveError(request, "errors.long", new String[] { id });
			return mapping.findForward("list");
		}
	}

	public ActionForward toExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (null == super.checkUserModPopeDom(form, request, "16")) {
			return super.checkPopedomInvalid(request, response);
		}

		DynaBean dynaBean = (DynaBean) form;
		Map<String, Object> model = new HashMap<String, Object>();

		String add_begin_date = (String) dynaBean.get("add_begin_date");
		String add_end_date = (String) dynaBean.get("add_end_date");
		String audit_begin_date = (String) dynaBean.get("audit_begin_date");
		String audit_end_date = (String) dynaBean.get("audit_end_date");
		String mobile_like = (String) dynaBean.get("mobile_like");
		String real_name_like = (String) dynaBean.get("real_name_like");
		String code = (String) dynaBean.get("code");

		UserMoneyApply userMoneyApply = new UserMoneyApply();
		// 查询
		super.copyProperties(userMoneyApply, form);
		if (StringUtils.isNotBlank(real_name_like)) {
			userMoneyApply.getMap().put("real_name_like", real_name_like);
		}
		if (StringUtils.isNotBlank(mobile_like)) {
			userMoneyApply.getMap().put("mobile_like", mobile_like);
		}
		if (StringUtils.isNotBlank(add_begin_date)) {
			userMoneyApply.getMap().put("add_begin_date", add_begin_date);
		}
		if (StringUtils.isNotBlank(add_end_date)) {
			userMoneyApply.getMap().put("add_end_date", add_end_date);
		}
		if (StringUtils.isNotBlank(audit_begin_date)) {
			userMoneyApply.getMap().put("audit_begin_date", audit_begin_date);
		}
		if (StringUtils.isNotBlank(audit_end_date)) {
			userMoneyApply.getMap().put("audit_end_date", audit_end_date);
		}

		// userMoneyApply.setInfo_state(Keys.INFO_STATE.INFO_STATE_0.getIndex());
		userMoneyApply.setCash_type(Keys.CASH_TYPE.CASH_TYPE_10.getIndex());
		userMoneyApply.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());

		userMoneyApply.getMap().put("orderByCommon", " a.ADD_DATE asc, ");

		List<UserMoneyApply> userMoneyApplyList = getFacade().getUserMoneyApplyService()
				.getUserMoneyApplyPaginatedList(userMoneyApply);

		model.put("entityList", userMoneyApplyList);
		// String sum_money = super.getFacade().getHuizhuanChiDetailService().getHuizhuanChiDetailWithSumMoney(entity);
		// model.put("sum_money", sum_money);

		String content = getFacade().getTemplateService().getContent("MoneyUserQuery/list.ftl", model);
		// 下载文件出现乱码时，请参见此处
		String fname = EncryptUtilsV2.encodingFileName("用户提现审核表.xls");

		if (StringUtils.isBlank(code)) {
			code = "UTF-8";
		}
		response.setCharacterEncoding(code);
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename=" + fname);

		PrintWriter out = response.getWriter();
		out.println(content);
		out.flush();
		out.close();
		return null;
	}

	public ActionForward auditAll(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;

		String id = (String) dynaBean.get("id");
		String[] pks = (String[]) dynaBean.get("pks");
		String info_state = (String) dynaBean.get("info_state");
		String audit_memo = (String) dynaBean.get("audit_memo");
		UserInfo ui = super.getUserInfoFromSession(request);

		if (!ArrayUtils.isEmpty(pks)) {
			UserMoneyApply userMoneyApply = new UserMoneyApply();
			super.copyProperties(userMoneyApply, form);
			userMoneyApply.setInfo_state(Integer.valueOf(info_state));
			userMoneyApply.setAudit_memo(audit_memo);
			userMoneyApply.setAudit_uid(ui.getId());
			userMoneyApply.setAudit_date(new Date());
			userMoneyApply.getMap().put("getCashcount", "true");
			userMoneyApply.getMap().put("pks", pks);
			getFacade().getUserMoneyApplyService().modifyUserMoneyApply(userMoneyApply);

		}
		super.renderText(response, "批量审核已成功！");
		return null;
	}

	public ActionForward payForm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;

		String id = (String) dynaBean.get("id");
		UserInfo ui = super.getUserInfoFromSession(request);

		if (StringUtils.isBlank(id)) {
			saveMessage(request, "entity.missing");
			return mapping.findForward("list");
		}
		return new ActionForward("/../manager/admin/MoneyUserQuery/pay_form.jsp");
	}

	public ActionForward confirmTk(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String mod_id = (String) dynaBean.get("mod_id");
		String tuikuan_memo = (String) dynaBean.get("tuikuan_memo");

		if (!StringUtils.isBlank(id) && GenericValidator.isLong(id)) {

			UserMoneyApply entityQuery = new UserMoneyApply();
			entityQuery.setId(Integer.valueOf(id));
			entityQuery = super.getFacade().getUserMoneyApplyService().getUserMoneyApply(entityQuery);
			if (null == entityQuery) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");
			}

			UserMoneyApply entity = new UserMoneyApply();
			entity.setId(new Integer(id));
			entity.getMap().put("getCashcount", "true");
			entity.setInfo_state(Keys.INFO_STATE.INFO_STATE_X2.getIndex());
			entity.setCash_type(Keys.CASH_TYPE.CASH_TYPE_10.getIndex());
			entity.setCash_count(entityQuery.getCash_count());
			entity.setUser_id(entityQuery.getUser_id());

			entity.setTuikuan_date(new Date());
			if (StringUtils.isNotBlank(tuikuan_memo)) {
				entity.setTuikuan_memo(tuikuan_memo);
			}
			getFacade().getUserMoneyApplyService().modifyUserMoneyApply(entity);
		}
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(super.serialize(request, "id", "method")));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;

	}
}