package com.ebiz.webapp.web.struts.m;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.webapp.domain.RwYhqInfo;
import com.ebiz.webapp.domain.UserInfo;

/**
 * @author Wu,Yang
 * @version 2010-8-24
 */
public class MMyYhqInfoAction extends MBaseWebAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}

		request.setAttribute("header_title", "我的红包");

		DynaBean dynaBean = (DynaBean) form;

		String is_used = (String) dynaBean.get("is_used");
		RwYhqInfo entity = new RwYhqInfo();

		entity.setAdd_uid(ui.getId());
		entity.setIs_del(0);

		if (StringUtils.isBlank(is_used)) {
			is_used = "0";
		}
		if (!is_used.equals("2")) {
			entity.setIs_used(Integer.valueOf(is_used));
			entity.getMap().put("now_date", sdFormat_ymdhms.format(new Date()));
		} else { // 查询已过期的
			entity.getMap().put("query_date_out", sdFormat_ymdhms.format(new Date()));
		}
		List<RwYhqInfo> entityList = getFacade().getRwYhqInfoService().getRwYhqInfoList(entity);
		request.setAttribute("entityList", entityList);

		dynaBean.set("is_used", is_used);

		return mapping.findForward("list");

	}
}
