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
import com.ebiz.webapp.domain.RwHbRule;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;

/**
 * @author Qin,Yue
 * @version 2011-04-22
 */
public class HBRuleAction extends BaseAdminAction {

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

		RwHbRule rwHbRule = new RwHbRule();
		if (null == is_del) {
			rwHbRule.setIs_del(0);
			dynaBean.set("is_del", "0");
		}
		super.copyProperties(rwHbRule, form);

		rwHbRule.getMap().put("title_like", title_like);
		Integer recordCount = getFacade().getRwHbRuleService().getRwHbRuleCount(rwHbRule);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		rwHbRule.getRow().setFirst(pager.getFirstRow());
		rwHbRule.getRow().setCount(pager.getRowCount());

		List<RwHbRule> rwHbRuleList = getFacade().getRwHbRuleService().getRwHbRulePaginatedList(rwHbRule);
		request.setAttribute("rwHbRuleList", rwHbRuleList);

		request.setAttribute("hbClass", Keys.HbClass.values());
		request.setAttribute("hbTypes", Keys.HbType.values());

		return mapping.findForward("list");
	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		saveToken(request);

		request.setAttribute("hbClass", Keys.HbClass.values());
		request.setAttribute("hbTypes", Keys.HbType.values());

		return mapping.findForward("input");
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;

		String id = (String) dynaBean.get("id");
		String[] pks = (String[]) dynaBean.get("pks");
		String mod_id = (String) dynaBean.get("mod_id");
		UserInfo ui = super.getUserInfoFromSession(request);

		RwHbRule rwHbRule = new RwHbRule();
		rwHbRule.setIs_del(Keys.IsDel.IS_DEL_1.getIndex());
		rwHbRule.setDel_date(new Date());
		rwHbRule.setDel_uid(ui.getId());
		if (!StringUtils.isBlank(id) && GenericValidator.isLong(id)) {
			rwHbRule.setId(new Integer(id));
			getFacade().getRwHbRuleService().modifyRwHbRule(rwHbRule);
			saveMessage(request, "entity.deleted");
		} else if (!ArrayUtils.isEmpty(pks)) {
			rwHbRule.getMap().put("pks", pks);
			getFacade().getRwHbRuleService().modifyRwHbRule(rwHbRule);
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

		RwHbRule rwHbRule = new RwHbRule();
		rwHbRule.setId(Integer.valueOf(id));
		rwHbRule = super.getFacade().getRwHbRuleService().getRwHbRule(rwHbRule);

		super.copyProperties(form, rwHbRule);

		request.setAttribute("hbClass", Keys.HbClass.values());
		request.setAttribute("hbTypes", Keys.HbType.values());

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
		resetToken(request);

		UserInfo ui = super.getUserInfoFromSession(request);
		DynaBean dynaBean = (DynaBean) form;
		String mod_id = (String) dynaBean.get("mod_id");

		RwHbRule rwHbRule = new RwHbRule();
		super.copyProperties(rwHbRule, dynaBean);

		if (null == rwHbRule.getId()) {
			rwHbRule.setAdd_date(new Date());
			rwHbRule.setAdd_uid(ui.getId());
			super.getFacade().getRwHbRuleService().createRwHbRule(rwHbRule);

			saveMessage(request, "entity.inerted");
		} else {
			rwHbRule.setUpdate_date(new Date());
			rwHbRule.setUpdate_uid(ui.getId());
			getFacade().getRwHbRuleService().modifyRwHbRule(rwHbRule);

			saveMessage(request, "entity.updated");
		}
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=").append(mod_id);
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(rwHbRule.getQueryString()));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		return forward;
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		RwHbRule rwHbRule = new RwHbRule();
		rwHbRule.setId(Integer.valueOf(id));
		rwHbRule = super.getFacade().getRwHbRuleService().getRwHbRule(rwHbRule);

		super.copyProperties(form, rwHbRule);

		request.setAttribute("hbClass", Keys.HbClass.values());
		request.setAttribute("hbTypes", Keys.HbType.values());

		return mapping.findForward("view");
	}

	public ActionForward checkYhqRoleForHBMoney(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String hb_type = (String) dynaBean.get("hb_type");
		String share_user_money = (String) dynaBean.get("share_user_money");
		String hb_money = (String) dynaBean.get("hb_money");
		String min_money = (String) dynaBean.get("min_money");
		String max_money = (String) dynaBean.get("max_money");
		String hb_class = (String) dynaBean.get("hb_class");
		JSONObject data = new JSONObject();

		if (StringUtils.isBlank(hb_type)) {
			data.put("code", "4");
			data.put("msg", "请选择红包类型！");
			super.renderJson(response, data.toString());
			return null;
		}

		if ("10".equals(hb_class) && StringUtils.isBlank(share_user_money)) {
			data.put("code", "5");
			data.put("msg", "请填写分享人获得额度！");
			super.renderJson(response, data.toString());
			return null;
		}

		if ("10".equals(hb_class)) {

			Integer shareUserMoneyCount = super.getPublicRwRuleCount(share_user_money);
			if (shareUserMoneyCount < 1) {
				data.put("code", "1");
				data.put("msg", "不存在对应分享人获得额度的优惠券规则，请先前往优惠券规则管理维护相应额度的优惠券规则！");
				super.renderJson(response, data.toString());
				return null;
			}
		}

		if (Keys.HbType.HbType_10.getIndex() == Integer.valueOf(hb_type)) {
			if (StringUtils.isBlank(hb_money)) {
				data.put("code", "6");
				data.put("msg", "请填写定额红包额度！");
				super.renderJson(response, data.toString());
				return null;
			}
			Integer hbMoneyCount = super.getPublicRwRuleCount(hb_money);
			if (hbMoneyCount < 1) {
				data.put("code", "2");
				data.put("msg", "不存在对应定额红包额度的优惠券规则，请先前往优惠券规则管理维护相应额度的优惠券规则！");
				super.renderJson(response, data.toString());
				return null;
			}
		} else if (Keys.HbType.HbType_20.getIndex() == Integer.valueOf(hb_type)) {
			if (StringUtils.isBlank(min_money) || StringUtils.isBlank(max_money)) {
				data.put("code", "7");
				data.put("msg", "请填写最小红包额度和最大红包额度！");
				super.renderJson(response, data.toString());
				return null;
			}

			for (int i = Integer.valueOf(min_money); i <= Integer.valueOf(max_money); i++) {
				Integer iCount = super.getPublicRwRuleCount(i + "");
				if (iCount < 1) {
					data.put("code", "3");
					data.put("msg", "最大最小红包额度范围内的优惠券规则不完整，请先前往优惠券规则管理维护相应额度的优惠券规则！");
					super.renderJson(response, data.toString());
					return null;
				}
			}
		}

		data.put("code", "0");
		super.renderJson(response, data.toString());
		return null;
	}

}