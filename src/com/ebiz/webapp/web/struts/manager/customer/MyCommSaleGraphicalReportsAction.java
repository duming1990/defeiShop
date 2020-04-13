package com.ebiz.webapp.web.struts.manager.customer;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.webapp.web.util.DateTools;

/**
 * @author LIUJIA
 * @version 2016-07-29
 */

public class MyCommSaleGraphicalReportsAction extends BaseCustomerAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);
		String orderDay = (String) dynaBean.get("orderDay");

		if (StringUtils.isBlank(orderDay)) {
			dynaBean.set("orderDay", "2");
			dynaBean.set("st_date", DateTools.getLastDay(7));
			dynaBean.set("en_date", DateTools.getLastDay(0));
		} else if (orderDay.equals("1")) {// 查询最近一个月
			dynaBean.set("st_date", sdFormat_ymd.format(DateTools.getFirstDayOfLastMonday(new Date())));
			dynaBean.set("en_date", sdFormat_ymd.format(DateTools.getLastDayOfLastMonday(new Date())));
		}

		return mapping.findForward("list");
	}

}