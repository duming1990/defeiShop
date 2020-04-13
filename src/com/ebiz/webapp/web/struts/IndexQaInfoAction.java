package com.ebiz.webapp.web.struts;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.QaInfo;

public class IndexQaInfoAction extends BaseWebAction {
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.setPublicInfoWithSearchList(request);
		super.setPublicInfoList(request);
		super.setPublicInfoOtherList(request);

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String q_title_like = (String) dynaBean.get("q_title_like");

		QaInfo entity = new QaInfo();
		// entity.setQ_type(2);
		entity.setQa_state(1);
		entity.setIs_nx(0);
		entity.getMap().put("q_title_like", q_title_like);

		Integer recordCount = getFacade().getQaInfoService().getQaInfoCount(entity);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());
		List<QaInfo> qaInfoList = getFacade().getQaInfoService().getQaInfoPaginatedList(entity);
		request.setAttribute("qaInfoList", qaInfoList);

		return mapping.findForward("list");
	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.setPublicInfoWithSearchList(request);
		super.setPublicInfoList(request);
		super.setPublicInfoOtherList(request);

		return mapping.findForward("input");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// DynaBean dynaBean = (DynaBean) form;

		QaInfo entity = new QaInfo();
		super.copyProperties(entity, form);
		entity.setQ_type(2);
		entity.setIs_nx(0);
		entity.setQ_ip(this.getIpAddr(request));
		entity.setQ_date(new Date());
		entity.setQa_state(0);// 未发布
		entity.setOrder_value(0);
		getFacade().getQaInfoService().createQaInfo(entity);

		super.renderJavaScript(response, "window.onload=function(){alert('" + "您的问题已经提交，请等待管理员回复！"
				+ "');location.href='IndexQaInfo.do'}");
		return null;
	}

	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
