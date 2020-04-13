package com.ebiz.webapp.web.struts.manager.customer;

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

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.BaseProvince;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.VillageMember;
import com.ebiz.webapp.web.Keys;

public class CircleMemberManagerAction extends BaseCustomerAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "1")) {
			return super.checkPopedomInvalid(request, response);
		}

		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}

		Pager pager = (Pager) dynaBean.get("pager");
		String user_name_like = (String) dynaBean.get("user_name_like");
		String audit_state = (String) dynaBean.get("audit_state");

		VillageMember entity = new VillageMember();
		if (StringUtils.isNotBlank(audit_state)) {
			entity.setAudit_state(Integer.valueOf(audit_state));
		}
		if (ui.getIs_village() != null || ui.getIs_village() == 1) {
			entity.setVillage_id(ui.getOwn_village_id());
		}

		entity.setIs_del(0);
		entity.getMap().put("user_name_like", user_name_like);

		Integer recordCount = getFacade().getVillageMemberService().getVillageMemberCount(entity);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<VillageMember> list = getFacade().getVillageMemberService().getVillageMemberPaginatedList(entity);
		if (list != null && list.size() > 0) {
			for (VillageMember temp : list) {
				UserInfo userInfo = new UserInfo();
				userInfo.setId(temp.getUser_id());
				userInfo.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
				userInfo.setUser_type(Keys.UserType.USER_TYPE_2.getIndex());
				userInfo = getFacade().getUserInfoService().getUserInfo(userInfo);
				if (null != userInfo) {
					temp.getMap().put("userInfo", userInfo);
				}
			}
		}
		request.setAttribute("entityList", list);

		return mapping.findForward("list");
	}

	public ActionForward audit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		UserInfo ui = super.getUserInfoFromSession(request);

		if (GenericValidator.isLong(id)) {
			int limit_count = 0;
			VillageMember entity = new VillageMember();
			entity.setId(Integer.valueOf(id));
			entity.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
			entity = getFacade().getVillageMemberService().getVillageMember(entity);
			if (entity == null) {
				String msg = "参数不正确！";
				super.showMsgForCustomer(request, response, msg);
				return null;
			}

			UserInfo userInfo = new UserInfo();
			userInfo.setIs_del(0);
			userInfo.setId(entity.getUser_id());
			userInfo = getFacade().getUserInfoService().getUserInfo(userInfo);
			if (userInfo == null) {
				String msg = "用户不存在！";
				super.showMsgForCustomer(request, response, msg);
				return null;
			}
			if (userInfo.getP_index() != null) {
				BaseProvince baseProvince = new BaseProvince();
				baseProvince.setP_index(userInfo.getP_index().longValue());
				baseProvince.setIs_del(0);
				baseProvince = getFacade().getBaseProvinceService().getBaseProvince(baseProvince);
				if (baseProvince != null) {
					request.setAttribute("full_name", baseProvince.getFull_name());
				}
			}
			super.copyProperties(form, userInfo);
			entity.setQueryString(super.serialize(request, "id", "method"));
			// end
			super.copyProperties(form, entity);
			return new ActionForward("/../manager/customer/CircleMemberManager/audit.jsp");
		} else {
			this.saveError(request, "errors.Integer", new String[] { id });
			request.setAttribute("init_pwd", Keys.INIT_PWD);
			return mapping.findForward("list");
		}
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		DynaBean dynaBean = (DynaBean) form;

		String id = (String) dynaBean.get("id");
		String mod_id = (String) dynaBean.get("mod_id");

		if (StringUtils.isBlank(id)) {
			String msg = "参数错误！";
			super.showMsgForCustomer(request, response, msg);
			return null;
		}
		VillageMember entity = new VillageMember();
		entity.setId(Integer.valueOf(id));
		entity = getFacade().getVillageMemberService().getVillageMember(entity);

		if (entity == null) {
			String msg = "参数错误！";
			super.showMsgForCustomer(request, response, msg);
			return null;
		}
		super.copyProperties(entity, form);

		entity.setAudit_user_id(ui.getId());
		entity.setAudit_date(new Date());
		entity.setAudit_user_name(ui.getUser_name());
		if (entity.getAudit_state() != null) {

			if (entity.getAudit_state() == Keys.audit_state.audit_state_1.getIndex()) {
				// 审核通过村成员数加一
				entity.getMap().put("add_village_member_count", true);
			} else if (entity.getAudit_state() == Keys.audit_state.audit_state_fu_1.getIndex()) {
				// 审核通过村成员数减一
				entity.getMap().put("sub_village_member_count", true);
			}
		}
		super.getFacade().getVillageMemberService().modifyVillageMember(entity);
		saveMessage(request, "entity.audit");

		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
		pathBuffer.append("&mod_id=" + mod_id);
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String mod_id = (String) dynaBean.get("mod_id");
		String par_id = (String) dynaBean.get("par_id");

		if (StringUtils.isBlank(id)) {
			String msg = super.getMessage(request, "参数不能为空");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}

		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		VillageMember entity = new VillageMember();
		Date date = new Date();
		entity.setIs_del(1);
		entity.setDel_date(date);
		entity.setDel_user_id(ui.getId());
		entity.setId(Integer.valueOf(id));
		super.getFacade().getVillageMemberService().modifyVillageMember(entity);

		saveMessage(request, "entity.deleted");

		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&par_id=" + par_id);
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(super.serialize(request, "id", "method")));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}
}
