package com.ebiz.webapp.web.struts.manager.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.webapp.domain.SysModule;
import com.ebiz.webapp.domain.UserInfo;

/**
 * @author Wu,Yang
 * @version 2011-04-20
 */
public class FramesAction extends BaseAdminAction {

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.index(mapping, form, request, response);
	}

	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo userInfo = super.getUserInfoFromSession(request);

		SysModule sysAll = new SysModule();
		if (userInfo.getUser_type().intValue() == 1) {
			sysAll.getMap().put("is_admin", "true");
		}
		if (userInfo.getUser_type().intValue() >= 6 && userInfo.getUser_type().intValue() <= 20) {
			sysAll.getMap().put("is_manager", "true");
		}
		sysAll.getMap().put("user_id", userInfo.getId());

		List<SysModule> sysAllList = getFacade().getSysModuleService().getSysModuleList(sysAll);

		request.setAttribute("sysModuleParList", super.getManagerSysModuleList(sysAllList));

		return mapping.findForward("indexFrame");
	}

}
