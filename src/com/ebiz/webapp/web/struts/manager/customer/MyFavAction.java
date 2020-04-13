package com.ebiz.webapp.web.struts.manager.customer;

import java.util.Date;
import java.util.List;

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

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.ScEntpComm;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;

/**
 * @author Liu,Jia
 * @version 2014-05-28
 */
public class MyFavAction extends BaseCustomerAction {
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		super.setPublicInfoListWithEntpAndCustomer(request);
		UserInfo ui = (UserInfo) super.getUserInfoFromSession(request);
		DynaBean dynaBean = (DynaBean) form;

		super.getsonSysModuleList(request, dynaBean);

		Pager pager = (Pager) dynaBean.get("pager");
		String title_name_like = (String) dynaBean.get("title_name_like");
		ScEntpComm entity = new ScEntpComm();
		super.copyProperties(entity, form);
		entity.setAdd_user_id(ui.getId());
		entity.setIs_del(0);
		entity.getMap().put("title_name_like", title_name_like);

		Integer recordCount = getFacade().getScEntpCommService().getScEntpCommCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<ScEntpComm> entityList = super.getFacade().getScEntpCommService().getScEntpCommPaginatedList(entity);
		if (null != entityList && entityList.size() > 0) {
			for (ScEntpComm sec : entityList) {
				if (sec.getSc_type() == 1) {
					EntpInfo entpInfo = new EntpInfo();
					entpInfo.setId(sec.getLink_id());
					entpInfo.setIs_del(0);
					entpInfo = super.getFacade().getEntpInfoService().getEntpInfo(entpInfo);
					if (null != entpInfo) {
						sec.getMap().put("custom_url", entpInfo.getCustom_url());
					}
				}
			}
		}
		request.setAttribute("entityList", entityList);
		return mapping.findForward("list");
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;

		super.setPublicInfoListWithEntpAndCustomer(request);

		String id = (String) dynaBean.get("id");
		if (StringUtils.isBlank(id)) {
			String msg = super.getMessage(request, "参数不能为空");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}
		if (GenericValidator.isLong(id)) {
			ScEntpComm entity = new ScEntpComm();
			entity.setId(Integer.valueOf(id));
			entity = getFacade().getScEntpCommService().getScEntpComm(entity);
			super.copyProperties(form, entity);

			return mapping.findForward("view");
		} else {
			saveError(request, "errors.Integer", id);
			return mapping.findForward("list");
		}
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String[] pks = (String[]) dynaBean.get("pks");

		if (StringUtils.isBlank(id) && ArrayUtils.isEmpty(pks)) {
			String msg = super.getMessage(request, "参数不能为空");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}

		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		ScEntpComm entity = new ScEntpComm();
		Date date = new Date();
		entity.setIs_del(1);
		entity.setDel_date(date);
		entity.setDel_user_id(ui.getId());
		if (StringUtils.isNotBlank(id)) {
			entity.setId(Integer.valueOf(id));
			super.getFacade().getScEntpCommService().modifyScEntpComm(entity);

			saveMessage(request, "entity.deleted");

		} else if (ArrayUtils.isNotEmpty(pks)) {
			entity.getMap().put("pks", pks);
			super.getFacade().getScEntpCommService().modifyScEntpComm(entity);

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
