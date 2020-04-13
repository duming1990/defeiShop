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
import com.ebiz.webapp.domain.BaseData;
import com.ebiz.webapp.domain.UserInfo;

/**
 * @author Qin,Yue
 * @version 2011-04-22
 */
public class BaseDataAction extends BaseAdminAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "1")) {
			return super.checkPopedomInvalid(request, response);
		}
		setNaviStringToRequestScope(request);
		saveToken(request);
		DynaBean dynaBean = (DynaBean) form;
		dynaBean.set("order_value", "0");
		dynaBean.set("is_lock", "0");

		return mapping.findForward("input");
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "0")) {
			return super.checkPopedomInvalid(request, response);
		}

		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		String is_del = (String) dynaBean.get("is_del");
		String type = (String) dynaBean.get("type");
		Pager pager = (Pager) dynaBean.get("pager");
		String type_name_like = (String) dynaBean.get("type_name_like");

		BaseData entity = new BaseData();
		super.copyProperties(entity, dynaBean);

		if (null == is_del) {
			entity.setIs_del(0);
			dynaBean.set("is_del", "0");
		}
		entity.getMap().put("type_name_like", type_name_like);

		Integer recordCount = getFacade().getBaseDataService().getBaseDataCount(entity);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<BaseData> list = getFacade().getBaseDataService().getBaseDataPaginatedList(entity);
		request.setAttribute("baseDataList", list);
		if (StringUtils.isNotBlank(type) && (list.size() > 0)) {// 判断只有一条数据 屏蔽添加按钮
			if (type.equals("6000") || type.equals("6500") || type.equals("6610") || type.equals("6620")
					|| type.equals("6630") || type.equals("15000") || type.equals("16000")) {
				request.setAttribute("only_has_one_data", "true");
			}
		}
		return mapping.findForward("list");
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "2")) {
			return super.checkPopedomInvalid(request, response);
		}

		setNaviStringToRequestScope(request);
		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		if (!GenericValidator.isLong(id)) {
			saveError(request, "errors.Integer", id);
			return mapping.findForward("list");
		}
		BaseData entity = new BaseData();
		entity.setId(Integer.valueOf(id));
		entity = getFacade().getBaseDataService().getBaseData(entity);

		// the line below is added for pagination
		entity.setQueryString(super.serialize(request, "id", "method"));
		// end

		super.copyProperties(form, entity);
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
		DynaBean dynaBean = (DynaBean) form;
		String mod_id = (String) dynaBean.get("mod_id");
		String pre_number3 = (String) dynaBean.get("pre_number3");
		UserInfo sessionUi = super.getUserInfoFromSession(request);

		BaseData entity = new BaseData();
		super.copyProperties(entity, dynaBean);

		if (StringUtils.isNotBlank(pre_number3)) {
			Double pn = Double.valueOf(pre_number3);
			entity.setPre_number(100);
			Double pn2 = Double.valueOf("100") * pn;
			entity.setPre_number2(pn2.intValue());
		}

		if (null != entity.getId()) {// update
			entity.setUpdate_date(new Date());
			entity.setUpdate_user_id(sessionUi.getId());
			if ((null != entity.getIs_del()) && (entity.getIs_del().intValue() == 0)) {
				entity.setDel_date(null);
			}
			getFacade().getBaseDataService().modifyBaseData(entity);
			saveMessage(request, "entity.updated");
		} else {// insert
			entity.setIs_del(0);
			entity.setAdd_date(new Date());
			entity.setAdd_user_id(sessionUi.getId());
			getFacade().getBaseDataService().createBaseData(entity);
			saveMessage(request, "entity.inerted");
		}

		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&type=").append(entity.getType());
		pathBuffer.append("&mod_id=").append(mod_id);
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		return forward;
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "4")) {
			return super.checkPopedomInvalid(request, response);
		}

		DynaBean dynaBean = (DynaBean) form;

		String id = (String) dynaBean.get("id");
		String[] pks = (String[]) dynaBean.get("pks");
		UserInfo sessionUi = super.getUserInfoFromSession(request);

		if (!StringUtils.isBlank(id) && GenericValidator.isLong(id)) {
			BaseData entity = new BaseData();
			entity.setIs_del(1);
			entity.setId(new Integer(id));
			entity.setDel_date(new Date());
			entity.setDel_user_id(sessionUi.getId());
			getFacade().getBaseDataService().modifyBaseData(entity);
			saveMessage(request, "entity.deleted");
		} else if (!ArrayUtils.isEmpty(pks)) {
			BaseData entity = new BaseData();
			entity.setIs_del(1);
			entity.setDel_date(new Date());
			entity.setDel_user_id(sessionUi.getId());
			entity.getMap().put("pks", pks);
			getFacade().getBaseDataService().modifyBaseData(entity);
			saveMessage(request, "entity.deleted");
		}
		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(super.serialize(request, "id", "ids", "method")));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "0")) {
			return super.checkPopedomInvalid(request, response);
		}

		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		if (GenericValidator.isLong(id)) {
			BaseData entity = new BaseData();
			entity.setId(new Integer(id));
			entity = getFacade().getBaseDataService().getBaseData(entity);
			if (null == entity) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");
			}
			super.copyProperties(form, entity);
			return mapping.findForward("view");
		} else {
			this.saveError(request, "errors.Integer", new String[] { id });
			return mapping.findForward("list");
		}
	}

	public ActionForward checkTypeName(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String type_name = (String) dynaBean.get("type_name");
		String id = (String) dynaBean.get("id");
		String type = (String) dynaBean.get("type");
		BaseData entity = new BaseData();
		entity.getMap().put("not_in_id", id);
		entity.setType_name(type_name);
		entity.setType(Integer.valueOf(type));
		entity.setIs_del(0);
		Integer recordCount = super.getFacade().getBaseDataService().getBaseDataCount(entity);
		String flag = "1";
		if (recordCount.intValue() == 0) {
			flag = "0";
		}
		super.renderJson(response, flag);
		return null;
	}
}
