package com.ebiz.webapp.web.struts.manager.customer;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.UserJifenRecord;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.DateTools;

/**
 * @author Qin,Yue
 * @version 2011-04-22
 */
public class MyJifenAction extends BaseCustomerAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);

		UserInfo ui = super.getUserInfoFromSession(request);

		Pager pager = (Pager) dynaBean.get("pager");

		UserJifenRecord entity = new UserJifenRecord();
		super.copyProperties(entity, form);

		entity.setUser_id(ui.getId());
		entity.setIs_del(0);

		String begin_date = (String) dynaBean.get("begin_date");
		String end_date = (String) dynaBean.get("end_date");

		if (StringUtils.isBlank(begin_date) && StringUtils.isBlank(end_date)) {
			Calendar cal = GregorianCalendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.DATE, -30);
			begin_date = DateTools.getStringDate(cal.getTime(), "yyyy-MM-dd");
			end_date = DateTools.getStringDate(new Date(), "yyyy-MM-dd");
			dynaBean.set("begin_date", begin_date);
			dynaBean.set("end_date", end_date);
		}

		entity.getMap().put("begin_date", begin_date);
		entity.getMap().put("end_date", end_date);

		// 查询合计金额
		UserJifenRecord ujrsum = getFacade().getUserJifenRecordService().getUserJifenRecordSum(entity);
		request.setAttribute("bi_jifen", ujrsum.getOpt_c_score());
		request.setAttribute("bi_dianzi", ujrsum.getOpt_c_dianzibi());

		Integer recordCount = getFacade().getUserJifenRecordService().getUserJifenRecordCount(entity);
		pager.init(recordCount.longValue(), 10, pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<UserJifenRecord> list = getFacade().getUserJifenRecordService().getUserJifenRecordPaginatedList(entity);
		request.setAttribute("userJifenRecordList", list);

		request.setAttribute("jifenTypes", Keys.JifenTypeShow.values());
		return mapping.findForward("list");
	}

}
