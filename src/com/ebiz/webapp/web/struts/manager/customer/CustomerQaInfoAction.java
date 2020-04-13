package com.ebiz.webapp.web.struts.manager.customer;

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
import com.ebiz.webapp.domain.QaInfo;
import com.ebiz.webapp.domain.UserInfo;

/**
 * 注册用户后台---企业投诉
 * 
 * @author Zhang,Xufeng
 * @version 2012-03-30
 */
public class CustomerQaInfoAction extends BaseCustomerAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		setNaviStringToRequestScope(request);

		UserInfo ui = super.getUserInfoFromSession(request);

		DynaBean dynaBean = (DynaBean) form;

		Pager pager = (Pager) dynaBean.get("pager");
		String q_type = (String) dynaBean.get("q_type");// list页面快速搜索 “问题类型”
		String st_q_date = (String) dynaBean.get("st_q_date");
		String en_q_date = (String) dynaBean.get("en_q_date");
		String st_a_date = (String) dynaBean.get("st_a_date");
		String en_a_date = (String) dynaBean.get("en_a_date");
		String q_title_like = (String) dynaBean.get("q_title_like");

		QaInfo entity = new QaInfo();
		super.copyProperties(entity, form);

		entity.getMap().put("st_q_date", st_q_date);
		entity.getMap().put("en_q_date", en_q_date);
		entity.getMap().put("st_a_date", st_a_date);
		entity.getMap().put("en_a_date", en_a_date);

		entity.getMap().put("q_title_like", q_title_like);
		// entity.getMap().put("qa_state", "qa_state");// list页面不显示已删除记录

		if (StringUtils.isNotBlank(q_type)) {
			entity.setQ_type(Integer.valueOf(q_type)); // list页面快速搜索 “问题类型”
		}
		entity.setQ_user_id(ui.getId());// 投诉人

		Integer recordCount = getFacade().getQaInfoService().getQaInfoCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<QaInfo> qaInfoList = getFacade().getQaInfoService().getQaInfoPaginatedList(entity);
		request.setAttribute("entityList", qaInfoList);
		dynaBean.set("q_type", q_type);

		return mapping.findForward("list");

	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		saveToken(request);
		setNaviStringToRequestScope(request);

		super.setPublicInfoListWithEntpAndCustomer(request);

		DynaBean dynaBean = (DynaBean) form;
		String q_type = (String) dynaBean.get("q_type");

		QaInfo entity = new QaInfo();
		entity.setQ_type(Integer.valueOf(q_type));
		entity.setOrder_value(0);
		entity.setQa_state(0);
		super.copyProperties(form, entity);
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
		resetToken(request);

		UserInfo ui = super.getUserInfoFromSession(request);

		DynaBean dynaBean = (DynaBean) form;
		String mod_id = (String) dynaBean.get("mod_id");
		String q_type = (String) dynaBean.get("q_type");

		QaInfo entity = new QaInfo();
		super.copyProperties(entity, form);

		// 提问人信息
		entity.setQ_user_id(ui.getId());
		entity.setQ_name(ui.getUser_name());
		if (null != ui.getOffice_tel()) {
			entity.setQ_tel(ui.getOffice_tel());
		} else {
			entity.setQ_tel(ui.getMobile());
		}
		entity.setQ_email(ui.getEmail());
		entity.setQ_ip(request.getRemoteAddr());

		if (null == entity.getId()) {
			entity.setQ_type(Integer.valueOf(q_type));

			entity.setQ_date(new Date());

			getFacade().getQaInfoService().createQaInfo(entity);

			saveMessage(request, "entity.inerted");

		} else {

			getFacade().getQaInfoService().modifyQaInfo(entity);

			saveMessage(request, "entity.updated");
		}

		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&q_type=" + q_type);
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end

		return forward;
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		saveToken(request);
		setNaviStringToRequestScope(request);

		super.setPublicInfoListWithEntpAndCustomer(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (GenericValidator.isLong(id)) {
			QaInfo qaInfo = new QaInfo();
			qaInfo.setId(Integer.valueOf(id));
			qaInfo = getFacade().getQaInfoService().getQaInfo(qaInfo);

			if (null == qaInfo) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");
			}

			// the line below is added for pagination
			qaInfo.setQueryString(super.serialize(request, "id", "method"));
			// end
			super.copyProperties(form, qaInfo);
			return mapping.findForward("input");
		} else {
			saveError(request, "errors.Integer", id);
			return mapping.findForward("list");

		}

	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		saveToken(request);
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String[] pks = (String[]) dynaBean.get("pks");
		String mod_id = (String) dynaBean.get("mod_id");

		QaInfo entity = new QaInfo();

		if (!StringUtils.isBlank(id) && GenericValidator.isLong(id)) {
			entity.setId(Integer.valueOf(id));
			entity.setQa_state(-1);
			getFacade().getQaInfoService().modifyQaInfo(entity);
			saveMessage(request, "entity.deleted");
		} else if (!ArrayUtils.isEmpty(pks)) {
			entity.getMap().put("pks", pks);
			entity.setQa_state(-1);
			getFacade().getQaInfoService().modifyQaInfo(entity);
			saveMessage(request, "entity.deleted");
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

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);

		super.setPublicInfoListWithEntpAndCustomer(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		if (GenericValidator.isInt(id)) {
			QaInfo qaInfo = new QaInfo();
			qaInfo.setId(Integer.valueOf(id));
			qaInfo = getFacade().getQaInfoService().getQaInfo(qaInfo);

			if (null == qaInfo) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");
			}

			super.copyProperties(form, qaInfo);
			return mapping.findForward("view");
		} else {
			saveError(request, "errors.Integer", id);
			return mapping.findForward("list");
		}
	}
}
