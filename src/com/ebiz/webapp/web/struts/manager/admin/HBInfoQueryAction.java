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

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.RwHbInfo;
import com.ebiz.webapp.domain.RwHbRule;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;

/**
 * @author Qin,Yue
 * @version 2011-04-22
 */
public class HBInfoQueryAction extends BaseAdminAction {

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
		String is_end = (String) dynaBean.get("is_end");
		String title_like = (String) dynaBean.get("title_like");

		RwHbInfo rwHbInfo = new RwHbInfo();
		if (null == is_del) {
			rwHbInfo.setIs_del(0);
			dynaBean.set("is_del", "0");
		}
		if (null == is_end) {
			rwHbInfo.setIs_end(0);
			dynaBean.set("is_end", "0");
		}
		super.copyProperties(rwHbInfo, form);

		rwHbInfo.getMap().put("title_like", title_like);
		Integer recordCount = getFacade().getRwHbInfoService().getRwHbInfoCount(rwHbInfo);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		rwHbInfo.getRow().setFirst(pager.getFirstRow());
		rwHbInfo.getRow().setCount(pager.getRowCount());

		List<RwHbInfo> rwHbInfoList = getFacade().getRwHbInfoService().getRwHbInfoPaginatedList(rwHbInfo);
		request.setAttribute("rwHbInfoList", rwHbInfoList);

		request.setAttribute("hbClass", Keys.HbClass.values());
		request.setAttribute("hbTypes", Keys.HbType.values());

		return mapping.findForward("list");
	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		saveToken(request);
		return mapping.findForward("input");
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;

		String id = (String) dynaBean.get("id");
		String[] pks = (String[]) dynaBean.get("pks");
		String mod_id = (String) dynaBean.get("mod_id");
		UserInfo ui = super.getUserInfoFromSession(request);

		RwHbInfo rwHbInfo = new RwHbInfo();
		rwHbInfo.setIs_del(Keys.IsDel.IS_DEL_1.getIndex());
		rwHbInfo.setDel_date(new Date());
		rwHbInfo.setDel_uid(ui.getId());
		if (!StringUtils.isBlank(id) && GenericValidator.isLong(id)) {
			rwHbInfo.setId(new Integer(id));
			getFacade().getRwHbInfoService().modifyRwHbInfo(rwHbInfo);
			saveMessage(request, "entity.deleted");
		} else if (!ArrayUtils.isEmpty(pks)) {
			rwHbInfo.getMap().put("pks", pks);
			getFacade().getRwHbInfoService().modifyRwHbInfo(rwHbInfo);
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
		super.setBaseDataListToSession(10, request);// 用户类型
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		RwHbInfo rwHbInfo = new RwHbInfo();
		rwHbInfo.setId(Integer.valueOf(id));
		rwHbInfo = super.getFacade().getRwHbInfoService().getRwHbInfo(rwHbInfo);

		super.copyProperties(form, rwHbInfo);

		request.setAttribute("hbClass", Keys.HbClass.values());
		request.setAttribute("hbTypes", Keys.HbType.values());

		return mapping.findForward("view");
	}

}