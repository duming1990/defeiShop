package com.ebiz.webapp.web.struts.manager.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
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
import com.ebiz.webapp.web.util.EncryptUtilsV2;

public class MerchantCheckTwoAction extends BaseAdminAction {
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String entp_name = (String) dynaBean.get("entp_name");
		String st_add_date = (String) dynaBean.get("st_add_date");
		String en_add_date = (String) dynaBean.get("en_add_date");
		String confirm_state = (String) dynaBean.get("confirm_state");
		String is_check = (String) dynaBean.get("is_check");

		EntpDuiZhang entity = new EntpDuiZhang();
		super.copyProperties(entity, form);
		entity.getMap().put("entp_name_like", entp_name);
		entity.getMap().put("st_add_date", st_add_date);
		entity.getMap().put("en_add_date", en_add_date);
		if (StringUtils.isNotBlank(confirm_state) && GenericValidator.isLong(confirm_state)) {
			entity.setConfirm_state(Integer.valueOf(confirm_state));
		}

		Integer recordCount = super.getFacade().getEntpDuiZhangService().getEntpDuiZhangCount(entity);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<EntpDuiZhang> entityList = super.getFacade().getEntpDuiZhangService().getEntpDuiZhangPaginatedList(entity);
		request.setAttribute("entityList", entityList);
		return mapping.findForward("list");
	}

	public ActionForward toExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> model = new HashMap<String, Object>();

		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String entp_name = (String) dynaBean.get("entp_name");
		String st_add_date = (String) dynaBean.get("st_add_date");
		String code = (String) dynaBean.get("code");
		String en_add_date = (String) dynaBean.get("en_add_date");
		String confirm_state = (String) dynaBean.get("confirm_state");
		String is_check = (String) dynaBean.get("is_check");
		EntpDuiZhang entity = new EntpDuiZhang();
		if (StringUtils.isNotBlank(entp_name)) {
			entity.getMap().put("entp_name_like", entp_name);
		}
		if (StringUtils.isNotBlank(st_add_date)) {
			entity.getMap().put("st_add_date", st_add_date);

		}
		if (StringUtils.isNotBlank(en_add_date)) {
			entity.getMap().put("en_add_date", en_add_date);
		}
		if (StringUtils.isNotBlank(confirm_state) && GenericValidator.isLong(confirm_state)) {
			entity.setConfirm_state(Integer.valueOf(confirm_state));
		}
		if (StringUtils.isNotBlank(is_check) && GenericValidator.isLong(is_check)) {
			entity.setIs_check(Integer.valueOf(is_check));
		}

		List<EntpDuiZhang> entityList = super.getFacade().getEntpDuiZhangService().getEntpDuiZhangList(entity);
		// request.setAttribute("entityList", entityList);
		model.put("entityList", entityList);
		model.put("title", "商家结算审核_日期" + sdFormat_ymd.format(new Date()));

		String content = getFacade().getTemplateService().getContent("MerchantCheckTwo/list.ftl", model);
		String fname = EncryptUtilsV2.encodingFileName("商家结算审核_日期" + sdFormat_ymd.format(new Date()) + ".xls");

		if (StringUtils.isBlank(code)) {
			code = "UTF-8";
		}
		response.setCharacterEncoding(code);
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename=" + fname);

		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(content);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public ActionForward listDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		Pager pager = (Pager) dynaBean.get("pager");
		EntpDuiZhang entpDZ = new EntpDuiZhang();
		entpDZ.setId(Integer.valueOf(id));
		entpDZ = getFacade().getEntpDuiZhangService().getEntpDuiZhang(entpDZ);
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setLink_check_id(Integer.valueOf(id));
		// orderInfo.setIs_check(Keys.IsCleck.IS_CLECK_20.getIndex());
		// orderInfo.getMap().put("st_add_date", entpDZ.getAdd_check_date());
		// orderInfo.getMap().put("en_add_date", entpDZ.getEnd_check_date());
		Integer recordCount = super.getFacade().getOrderInfoService().getOrderInfoCount(orderInfo);
		pager.init(recordCount.longValue(), 10, pager.getRequestPage());
		orderInfo.getRow().setFirst(pager.getFirstRow());
		orderInfo.getRow().setCount(pager.getRowCount());
		List<OrderInfo> entityList = getFacade().getOrderInfoService().getOrderInfoPaginatedList(orderInfo);
		request.setAttribute("entityList_id", id);
		request.setAttribute("entityList", entityList);

		return new ActionForward("/../manager/admin/MerchantCheckTwo/list_details.jsp");
	}

	public ActionForward lastCheck(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		EntpDuiZhang entpDZ = new EntpDuiZhang();
		entpDZ.setId(Integer.parseInt(id));
		entpDZ = getFacade().getEntpDuiZhangService().getEntpDuiZhang(entpDZ);
		super.copyProperties(form, entpDZ);
		return new ActionForward("/../manager/admin/MerchantCheckTwo/view.jsp");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UserInfo ui = super.getUserInfoFromSession(request);
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String is_check = (String) dynaBean.get("is_check");
		String confirm_desc = (String) dynaBean.get("confirm_desc");
		String cash_rate = (String) dynaBean.get("cash_rate");
		String entp_id = (String) dynaBean.get("entp_id");
		if (GenericValidator.isInt(id)) {
			EntpDuiZhang entpDZ = new EntpDuiZhang();
			entpDZ.setId(Integer.valueOf(id));
			entpDZ = getFacade().getEntpDuiZhangService().getEntpDuiZhang(entpDZ);
			entpDZ.setIs_check(Integer.valueOf(is_check));
			entpDZ.setConfirm_desc(confirm_desc);
			entpDZ.setCash_rate(new BigDecimal(cash_rate));
			entpDZ.setConfirm_user_id(ui.getId());
			entpDZ.setConfirm_date(new Date());
			if ("-1".equals(is_check)) {// 对账审核没有通过，把原来扣得商家货币返回
				UserInfo userInfo = new UserInfo();
				userInfo.setOwn_entp_id(entpDZ.getEntp_id());
				userInfo = getFacade().getUserInfoService().getUserInfo(userInfo);
				// 商家货款增加，修改商家货款币
				UserInfo uInfoSub = new UserInfo();
				uInfoSub.setId(userInfo.getId());
				uInfoSub.getMap().put("add_bi_huokuan", entpDZ.getSum_money());
				// user_bi_recode表中插入货币增加记录
				UserBiRecord ubr = new UserBiRecord();
				ubr.setBi_chu_or_ru(Keys.BI_CHU_OR_RU.BASE_DATA_ID_1.getIndex());
				ubr.setBi_type(Keys.BiType.BI_TYPE_300.getIndex());
				ubr.setBi_no_before(userInfo.getBi_huokuan());
				ubr.setBi_no(entpDZ.getSum_money());
				ubr.setBi_no_after(userInfo.getBi_huokuan().add(entpDZ.getSum_money()));
				ubr.setBi_get_type(Keys.BiGetType.BI_GET_TYPE_72.getIndex());
				ubr.setBi_get_memo(Keys.BiGetType.BI_GET_TYPE_72.getName());
				ubr.setAdd_user_id(userInfo.getId());
				ubr.setAdd_date(new Date());
				ubr.setAdd_uname(userInfo.getUser_name());

				// 对账关联订单
				OrderInfo orderInfo = new OrderInfo();
				orderInfo.setLink_check_id(Integer.valueOf(id));
				List<OrderInfo> orderInfoList = super.getFacade().getOrderInfoService().getOrderInfoList(orderInfo);
				entpDZ.getMap().put("update_uInfo", uInfoSub);
				entpDZ.getMap().put("insert_ubr", ubr);
				if (null != orderInfoList && orderInfoList.size() > 0) {
					entpDZ.getMap().put("update_order_is_check_eq_0", orderInfoList);
				}
			}
			getFacade().getEntpDuiZhangService().modifyEntpDuiZhang(entpDZ);
		}
		super.renderJavaScript(response, "alert('".concat("审核成功").concat("');location.href='MerchantCheckTwo.do?';"));
		return null;
	}

	public ActionForward saveAll(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;

		String[] pks = (String[]) dynaBean.get("pks");
		String info_state = (String) dynaBean.get("info_state");
		String audit_memo = (String) dynaBean.get("audit_memo");
		UserInfo ui = super.getUserInfoFromSession(request);

		JSONObject data = new JSONObject();
		String msg = "审核成功!";
		for (String id : pks) {
			EntpDuiZhang entpDZ = new EntpDuiZhang();
			entpDZ.setId(Integer.parseInt(id));
			entpDZ = getFacade().getEntpDuiZhangService().getEntpDuiZhang(entpDZ);
			entpDZ.setIs_check(Integer.parseInt(info_state));
			entpDZ.setConfirm_desc(entpDZ.getConfirm_desc() + "<br/>" + audit_memo);
			entpDZ.setConfirm_user_id(ui.getId());
			entpDZ.setConfirm_date(new Date());

			if (Integer.valueOf(info_state).intValue() == Keys.IsCleck.IS_CLECK_X1.getIndex()) {
				UserInfo userInfo = new UserInfo();
				userInfo.setOwn_entp_id(entpDZ.getEntp_id());
				userInfo = getFacade().getUserInfoService().getUserInfo(userInfo);
				// 商家货款增加，修改商家货款币
				UserInfo uInfoSub = new UserInfo();
				uInfoSub.setId(userInfo.getId());
				uInfoSub.getMap().put("add_bi_huokuan", entpDZ.getSum_money());
				// user_bi_recode表中插入货币增加记录
				UserBiRecord ubr = new UserBiRecord();
				ubr.setBi_chu_or_ru(Keys.BI_CHU_OR_RU.BASE_DATA_ID_1.getIndex());
				ubr.setBi_type(Keys.BiType.BI_TYPE_300.getIndex());
				ubr.setBi_no_before(userInfo.getBi_huokuan());
				ubr.setBi_no(entpDZ.getSum_money());
				ubr.setBi_no_after(userInfo.getBi_huokuan().add(entpDZ.getSum_money()));
				ubr.setBi_get_type(Keys.BiGetType.BI_GET_TYPE_72.getIndex());
				ubr.setBi_get_memo(Keys.BiGetType.BI_GET_TYPE_72.getName());
				ubr.setAdd_user_id(userInfo.getId());
				ubr.setAdd_date(new Date());
				ubr.setAdd_uname(userInfo.getUser_name());

				// 对账关联订单
				OrderInfo orderInfo = new OrderInfo();
				orderInfo.setLink_check_id(Integer.valueOf(id));
				List<OrderInfo> orderInfoList = super.getFacade().getOrderInfoService().getOrderInfoList(orderInfo);
				entpDZ.getMap().put("update_uInfo", uInfoSub);
				entpDZ.getMap().put("insert_ubr", ubr);
				if (null != orderInfoList && orderInfoList.size() > 0) {
					entpDZ.getMap().put("update_order_is_check_eq_0", orderInfoList);
				}
			}
			getFacade().getEntpDuiZhangService().modifyEntpDuiZhang(entpDZ);
		}

		data.put("msg", msg);
		super.renderJson(response, data.toString());
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
		return new ActionForward("/../manager/admin/MerchantCheckTwo/pay_form.jsp");
	}

	// 付款
	public ActionForward pay(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "17")) {
			// return super.checkPopedomInvalid(request, response);
		}

		JSONObject data = new JSONObject();
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String ret = "0", msg = "参数错误！";

		if (!StringUtils.isBlank(id) && GenericValidator.isLong(id)) {
			EntpDuiZhang entpDZ = new EntpDuiZhang();
			entpDZ.setId(Integer.parseInt(id));
			entpDZ = getFacade().getEntpDuiZhangService().getEntpDuiZhang(entpDZ);

			EntpDuiZhang entity = new EntpDuiZhang();
			super.copyProperties(entity, form);
			entity.setId(new Integer(id));
			entity.setIs_check(Keys.IsCleck.IS_CLECK_15.getIndex());
			entity.setConfirm_desc(entpDZ.getConfirm_desc() + "<br/>付款成功");
			getFacade().getEntpDuiZhangService().modifyEntpDuiZhang(entity);
			ret = "1";
			msg = "确认付款成功！";
		}
		data.put("ret", ret);
		data.put("msg", msg);
		super.renderJson(response, data.toString());
		return null;
	}

	// 退款
	public ActionForward confirmTk(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String isCheck = (String) dynaBean.get("is_check");
		String mod_id = (String) dynaBean.get("mod_id");
		String refundRemarks = (String) dynaBean.get("refundRemarks");

		if (!StringUtils.isBlank(id) && GenericValidator.isLong(id)) {

			EntpDuiZhang entityQuery = new EntpDuiZhang();
			entityQuery.setId(Integer.valueOf(id));
			entityQuery = super.getFacade().getEntpDuiZhangService().getEntpDuiZhang(entityQuery);

			if (null == entityQuery) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");
			}

			EntpDuiZhang entity = new EntpDuiZhang();
			entity.setId(new Integer(id));
			entity.setRefundRemarks(refundRemarks);
			entity.setAdd_date(new Date());
			entity.setIs_check(new Integer(isCheck));
			entity.setConfirm_desc(refundRemarks);

			super.getFacade().getEntpDuiZhangService().modifyEntpDuiZhang(entity);
		}
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(super.serialize(request, "id", "method", "is_check")));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;

	}

}
