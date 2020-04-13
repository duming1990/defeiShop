package com.ebiz.webapp.web.struts.manager.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.BaseClass;
import com.ebiz.webapp.domain.SysOperLog;
import com.ebiz.webapp.web.Keys;

/**
 * @author Wu,Yang
 * @version 2010-08-25
 */
public class SysOperLogAction extends BaseAdminAction {

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");

		String oper_uname = (String) dynaBean.get("oper_uname");
		String s_date = (String) dynaBean.get("s_date");
		String e_date = (String) dynaBean.get("e_date");
		String ip = (String) dynaBean.get("ip");
		String oper_type = (String) dynaBean.get("oper_type");
		String link_id = (String) dynaBean.get("link_id");
		String flag = (String) dynaBean.get("flag");

		SysOperLog sysOperLog = new SysOperLog();
		sysOperLog.getMap().put("oper_uname", oper_uname);
		sysOperLog.getMap().put("s_date", s_date);
		sysOperLog.getMap().put("e_date", e_date);
		sysOperLog.getMap().put("ip", ip);

		if (StringUtils.isNotBlank(link_id)) {
			sysOperLog.setLink_id(Integer.valueOf(link_id));
		}

		if (StringUtils.isNotBlank(oper_type)) {
			sysOperLog.setOper_type(Integer.valueOf(oper_type));
		}

		BaseClass baseClass = new BaseClass();
		baseClass.setIs_del(0);
		baseClass.setCls_scope(10);
		List<BaseClass> baseClassTreeList = super.getFacade().getBaseClassService().getBaseClassList(baseClass);
		request.setAttribute("baseClassTreeList", baseClassTreeList);

		Integer recordCount = super.getFacade().getSysOperLogService().getSysOperLogCount(sysOperLog);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		sysOperLog.getRow().setFirst(pager.getFirstRow());
		sysOperLog.getRow().setCount(pager.getRowCount());
		List<SysOperLog> sysOperLogList = super.getFacade().getSysOperLogService()
				.getSysOperLogPaginatedList(sysOperLog);
		request.setAttribute("entityList", sysOperLogList);
		request.setAttribute("flag", flag);

		request.setAttribute("sysOperTypeList", Keys.SysOperType.values());

		return mapping.findForward("list");

	}
}
