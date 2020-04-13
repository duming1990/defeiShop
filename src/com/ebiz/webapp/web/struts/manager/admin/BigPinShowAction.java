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
import com.ebiz.webapp.domain.BaseFiles;
import com.ebiz.webapp.domain.BaseProvince;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;

/**
 * @author戴诗学
 */
public class BigPinShowAction extends BaseAdminAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		saveToken(request);
		return mapping.findForward("input");
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo userInfo = super.getUserInfoFromSession(request);
		if (null == userInfo) {
			String msg = "您还未登录，请先登录系统！";
			return super.showMsgForManager(request, response, msg);
		}

		DynaBean dynaBean = (DynaBean) form;
		String province = (String) dynaBean.get("province");
		String city = (String) dynaBean.get("city");
		String country = (String) dynaBean.get("country");
		Pager pager = (Pager) dynaBean.get("pager");

		BaseFiles entity = new BaseFiles();
		super.copyProperties(entity, dynaBean);
		entity.setIs_del(0);

		if (StringUtils.isNotBlank(country)) {
			entity.getMap().put("p_index_like", country);
		} else if (StringUtils.isNotBlank(city)) {
			entity.getMap().put("p_index_like", city.substring(0, 4));
		} else if (StringUtils.isNotBlank(province)) {
			entity.getMap().put("p_index_like", province.substring(0, 2));
		}
		entity.setType(Keys.BaseFilesType.BASE_FILES_TYPE_DAPINSHOW100.getIndex());
		Integer recordCount = getFacade().getBaseFilesService().getBaseFilesCount(entity);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<BaseFiles> entityList = getFacade().getBaseFilesService().getBaseFilesPaginatedList(entity);
		if (null != entityList && entityList.size() > 0) {
			for (BaseFiles baseFiles : entityList) {
				BaseProvince baseProvince = super.getBaseProvince(baseFiles.getLink_id().longValue());
				baseFiles.getMap().put("fullname", baseProvince.getFull_name());
			}
		}
		request.setAttribute("entityList", entityList);
		return mapping.findForward("list");
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		if (GenericValidator.isLong(id)) {
			BaseFiles entity = new BaseFiles();
			entity.setId(new Integer(id));
			entity = getFacade().getBaseFilesService().getBaseFiles(entity);
			if (null == entity) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");
			}
			// the line below is added for pagination
			entity.setQueryString(super.serialize(request, "id", "method"));
			// end
			super.copyProperties(form, entity);

			if (null != entity.getLink_id()) {
				setprovinceAndcityAndcountryToFrom(dynaBean, Integer.valueOf(entity.getLink_id()));
			}

			return mapping.findForward("input");
		} else {
			this.saveError(request, "errors.Integer", new String[] { id });
			return mapping.findForward("list");
		}
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

		UserInfo userInfo = super.getUserInfoFromSession(request);
		DynaBean dynaBean = (DynaBean) form;
		String mod_id = (String) dynaBean.get("mod_id");
		String link_id = (String) dynaBean.get("link_id");

		BaseFiles entity = new BaseFiles();
		super.copyProperties(entity, form);

		BaseFiles baseFilesQuery = new BaseFiles();
		baseFilesQuery.setLink_id(Integer.valueOf(link_id));
		if (null != entity.getId()) {
			baseFilesQuery.getMap().put("id_not_in", entity.getId());
		}
		baseFilesQuery.setIs_del(0);
		int count = super.getFacade().getBaseFilesService().getBaseFilesCount(baseFilesQuery);
		if (count > 0) {
			String msg = "该县名已存在，请重新选择或者修改该县大屏显示内容";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		resetToken(request);

		if (null != entity.getId()) {// update
			entity.setUpdate_date(new Date());
			entity.setUpdate_user_id(userInfo.getId());
			getFacade().getBaseFilesService().modifyBaseFiles(entity);
			saveMessage(request, "entity.updated");
		} else {// insert
			entity.setIs_del(0);
			entity.setAdd_date(new Date());
			entity.setAdd_user_id(userInfo.getId());
			entity.setType(Keys.BaseFilesType.BASE_FILES_TYPE_DAPINSHOW100.getIndex());
			getFacade().getBaseFilesService().createBaseFiles(entity);
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
		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String[] pks = (String[]) dynaBean.get("pks");

		UserInfo userInfo = super.getUserInfoFromSession(request);

		if (!StringUtils.isBlank(id) && GenericValidator.isLong(id)) {
			BaseFiles entity = new BaseFiles();
			entity.setIs_del(1);
			entity.setId(new Integer(id));
			entity.setDel_date(new Date());
			entity.setDel_user_id(userInfo.getId());
			getFacade().getBaseFilesService().modifyBaseFiles(entity);
			saveMessage(request, "entity.deleted");
		} else if (!ArrayUtils.isEmpty(pks)) {

			for (String cur_id : pks) {
				BaseFiles entity = new BaseFiles();
				entity.setIs_del(1);
				entity.setId(new Integer(cur_id));
				entity.setDel_date(new Date());
				entity.setDel_user_id(userInfo.getId());
				getFacade().getBaseFilesService().modifyBaseFiles(entity);
			}
			saveMessage(request, "entity.deleted");
		}
		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(super.serialize(request, "id", "method")));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}
}