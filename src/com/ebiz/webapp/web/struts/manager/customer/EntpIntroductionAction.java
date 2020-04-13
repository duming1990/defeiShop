package com.ebiz.webapp.web.struts.manager.customer;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.webapp.domain.EntpBaseLink;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.UserInfo;

/**
 * @author 戴诗学
 * @version 2018-05-17
 */

public class EntpIntroductionAction extends BaseCustomerAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return super.showMsgForCustomer(request, response, msg);
		}
		EntpInfo entpInfo = new EntpInfo();
		entpInfo.setId(ui.getOwn_entp_id());
		entpInfo.setIs_del(0);
		entpInfo.setAudit_state(2);
		entpInfo = super.getFacade().getEntpInfoService().getEntpInfo(entpInfo);
		request.setAttribute("entpInfo", entpInfo);
		List<EntpBaseLink> baseLink60List = this.getentpBaseLinkList(60, null, entpInfo.getId(), 10,
				"no_null_image_path");
		request.setAttribute("baseLink60List", baseLink60List);
		return mapping.findForward("list");
	}
}