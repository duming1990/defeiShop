package com.ebiz.webapp.web.struts.manager.admin;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.alibaba.fastjson.JSONObject;
import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.RwYhqRule;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;

/**
 * @author Qin,Yue
 * @version 2011-04-22
 */
public class YHQRuleAction extends BaseAdminAction {

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String is_del = (String) dynaBean.get("is_del");
		String title_like = (String) dynaBean.get("title_like");

		RwYhqRule rwYhqRule = new RwYhqRule();
		if (null == is_del) {
			rwYhqRule.setIs_del(0);
			dynaBean.set("is_del", "0");
		}
		super.copyProperties(rwYhqRule, form);

		rwYhqRule.getMap().put("title_like", title_like);
		Integer recordCount = getFacade().getRwYhqRuleService().getRwYhqRuleCount(rwYhqRule);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		rwYhqRule.getRow().setFirst(pager.getFirstRow());
		rwYhqRule.getRow().setCount(pager.getRowCount());

		List<RwYhqRule> rwYhqRuleList = getFacade().getRwYhqRuleService().getRwYhqRulePaginatedList(rwYhqRule);
		request.setAttribute("rwYhqRuleList", rwYhqRuleList);

		request.setAttribute("ruleTypes", Keys.RuleType.values());

		return mapping.findForward("list");
	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		saveToken(request);
		DynaBean dynaBean = (DynaBean) form;
		request.setAttribute("ruleTypes", Keys.RuleType.values());
		return mapping.findForward("input");
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;

		String id = (String) dynaBean.get("id");
		String[] pks = (String[]) dynaBean.get("pks");
		String mod_id = (String) dynaBean.get("mod_id");
		UserInfo ui = super.getUserInfoFromSession(request);

		RwYhqRule rwYhqRule = new RwYhqRule();
		rwYhqRule.setIs_del(Keys.IsDel.IS_DEL_1.getIndex());
		rwYhqRule.setDel_date(new Date());
		rwYhqRule.setDel_uid(ui.getId());
		if (!StringUtils.isBlank(id) && GenericValidator.isLong(id)) {
			rwYhqRule.setId(new Integer(id));
			getFacade().getRwYhqRuleService().modifyRwYhqRule(rwYhqRule);
			saveMessage(request, "entity.deleted");
		} else if (!ArrayUtils.isEmpty(pks)) {
			rwYhqRule.getMap().put("pks", pks);
			getFacade().getRwYhqRuleService().modifyRwYhqRule(rwYhqRule);
			saveMessage(request, "entity.deleted");
		}
		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=").append(mod_id);
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(super.serialize(request, "id", "ids", "method")));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		saveToken(request);
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		RwYhqRule rwYhqRule = new RwYhqRule();
		rwYhqRule.setId(Integer.valueOf(id));
		rwYhqRule = super.getFacade().getRwYhqRuleService().getRwYhqRule(rwYhqRule);

		super.copyProperties(form, rwYhqRule);

		request.setAttribute("ruleTypes", Keys.RuleType.values());
		return mapping.findForward("input");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);

		if (isCancelled(request)) {
			return list(mapping, form, request, response);
		}
		if (!isTokenValid(request)) {
			saveError(request, "errors.token");
			return list(mapping, form, request, response);
		}

		UserInfo ui = super.getUserInfoFromSession(request);
		DynaBean dynaBean = (DynaBean) form;
		String mod_id = (String) dynaBean.get("mod_id");
		String id = (String) dynaBean.get("id");

		RwYhqRule rwYhqRule = new RwYhqRule();
		super.copyProperties(rwYhqRule, dynaBean);

		if (null == rwYhqRule.getId()) {
			rwYhqRule.setAdd_date(new Date());
			rwYhqRule.setAdd_uid(ui.getId());
			int rwYhqRule_id = super.getFacade().getRwYhqRuleService().createRwYhqRule(rwYhqRule);

			saveMessage(request, "entity.inerted");
		} else {
			rwYhqRule.setModify_date(new Date());
			rwYhqRule.setModify_uid(ui.getId());
			getFacade().getRwYhqRuleService().modifyRwYhqRule(rwYhqRule);

			saveMessage(request, "entity.updated");
		}
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=").append(mod_id);
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(rwYhqRule.getQueryString()));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		return forward;
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		super.setBaseDataListToSession(10, request);// 用户类型
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		RwYhqRule rwYhqRule = new RwYhqRule();
		rwYhqRule.setId(Integer.valueOf(id));
		rwYhqRule = super.getFacade().getRwYhqRuleService().getRwYhqRule(rwYhqRule);

		super.copyProperties(form, rwYhqRule);

		request.setAttribute("ruleTypes", Keys.RuleType.values());

		return mapping.findForward("view");
	}

	public ActionForward validateYHQamount(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;

		String amount = (String) dynaBean.get("amount");

		JSONObject data = new JSONObject();
		RwYhqRule rwYhqRule = new RwYhqRule();
		rwYhqRule.setAmount(Integer.valueOf(amount));
		rwYhqRule.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		int recordCount = super.getFacade().getRwYhqRuleService().getRwYhqRuleCount(rwYhqRule);
		String ret = "0";
		if (recordCount > 0) { // 不可用
			ret = "1";
		}
		data.put("ret", ret);
		super.renderJson(response, data.toString());
		return null;
	}
}