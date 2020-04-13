package com.ebiz.webapp.web.struts.indexEntp;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.NewsInfo;
import com.ebiz.webapp.domain.SysModule;
import com.ebiz.webapp.web.struts.BaseWebAction;

/**
 * @author Wu,Yang
 * @version 2010-12-16
 */
public class IndexNewsInfoAction extends BaseWebAction {
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String mod_id = request.getParameter("mod_id");
		dynaBean.set("mod_id", mod_id);

		NewsInfo entity = new NewsInfo();
		if (StringUtils.isNotBlank(mod_id)) {
			entity.setMod_id(mod_id);// 商务资讯
		}
		entity.setIs_del(0);
		entity.setInfo_state(3);// 默认

		request.setAttribute("baseLink10070List", super.common(10070).get("baseLinkList"));
		request.setAttribute("baseLink10090List", super.common(10090).get("baseLinkList"));
		request.setAttribute("baseLink10071", super.common(10071).get("baseLinkList").get(0));
		request.setAttribute("baseLink10091", super.common(10091).get("baseLinkList").get(0));
		Integer recordCount = getFacade().getNewsInfoService().getNewsInfoCount(entity);
		pager.init(recordCount.longValue(), 10, pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());
		List<NewsInfo> newsInfoList = getFacade().getNewsInfoService().getNewsInfoPaginatedList(entity);
		request.setAttribute("newsInfoList", newsInfoList);
		request.setAttribute("pager", pager);

		return mapping.findForward("list");

	}

	/**
	 * 跳转人才招聘
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewJob(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		NewsInfo entity = new NewsInfo();
		super.copyProperties(entity, dynaBean);
		// TODO 不做任何判断
		// TODO 状态？未审核能显示吗？

		entity = super.getFacade().getNewsInfoService().getNewsInfo(entity);

		// 如果没好到怎么办？
		request.setAttribute("entity", entity);
		// baseLink10070List 顶部菜单导航
		request.setAttribute("baseLink10070List", super.common(10070).get("baseLinkList"));

		// 底部导航
		request.setAttribute("baseLink10090List", super.common(10090).get("baseLinkList"));

		// copyright的内容
		request.setAttribute("baseLink10091List", super.common(10091).get("baseLinkList"));

		String modName = "";
		SysModule sysModule = new SysModule();
		sysModule.setIs_del(0);
		sysModule.setMod_id(Long.valueOf(entity.getMod_id()));
		sysModule = super.getFacade().getSysModuleService().getSysModule(sysModule);
		if ((null != sysModule)) {
			modName = sysModule.getMod_name();

			request.setAttribute("mod_name", sysModule.getMod_name());
			dynaBean.set("mod_code", sysModule.getMod_id().toString());
			dynaBean.set("par_code", sysModule.getPar_id().toString());
		}

		// 根据名称不同跳转不同的页面
		request.setAttribute("view_title", "联系我们");
		return new ActionForward("/./IndexEntp/IndexNewsInfo/viewJob.jsp");

	}

	/**
	 * 跳转核心业务
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewBusiness(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		NewsInfo entity = new NewsInfo();
		super.copyProperties(entity, dynaBean);
		// TODO 不做任何判断
		// TODO 状态？未审核能显示吗？

		entity = super.getFacade().getNewsInfoService().getNewsInfo(entity);

		// 如果没好到怎么办？
		request.setAttribute("entity", entity);
		// baseLink10070List 顶部菜单导航
		request.setAttribute("baseLink10070List", super.common(10070).get("baseLinkList"));

		// 底部导航
		request.setAttribute("baseLink10090List", super.common(10090).get("baseLinkList"));

		// copyright的内容
		request.setAttribute("baseLink10091List", super.common(10091).get("baseLinkList"));

		String modName = "";
		SysModule sysModule = new SysModule();
		sysModule.setIs_del(0);
		sysModule.setMod_id(Long.valueOf(entity.getMod_id()));
		sysModule = super.getFacade().getSysModuleService().getSysModule(sysModule);
		if ((null != sysModule)) {
			modName = sysModule.getMod_name();

			request.setAttribute("mod_name", sysModule.getMod_name());
			dynaBean.set("mod_code", sysModule.getMod_id().toString());
			dynaBean.set("par_code", sysModule.getPar_id().toString());
		}
		request.setAttribute("", "13212");
		// 根据名称不同跳转不同的页面
		request.setAttribute("view_title", "联系我们");
		return new ActionForward("/./IndexEntp/IndexNewsInfo/viewLinkman.jsp");

	}

	/**
	 * 跳转联系我们页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewLinkMan(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		NewsInfo entity = new NewsInfo();
		super.copyProperties(entity, dynaBean);
		// TODO 不做任何判断
		// TODO 状态？未审核能显示吗？

		entity = super.getFacade().getNewsInfoService().getNewsInfo(entity);

		// 如果没好到怎么办？
		request.setAttribute("entity", entity);
		// baseLink10070List 顶部菜单导航
		request.setAttribute("baseLink10070List", super.common(10070).get("baseLinkList"));

		// 底部导航
		request.setAttribute("baseLink10090List", super.common(10090).get("baseLinkList"));

		// copyright的内容
		request.setAttribute("baseLink10091List", super.common(10091).get("baseLinkList"));

		String modName = "";
		SysModule sysModule = new SysModule();
		sysModule.setIs_del(0);
		sysModule.setMod_id(Long.valueOf(entity.getMod_id()));
		sysModule = super.getFacade().getSysModuleService().getSysModule(sysModule);
		if ((null != sysModule)) {
			modName = sysModule.getMod_name();

			request.setAttribute("mod_name", sysModule.getMod_name());
			dynaBean.set("mod_code", sysModule.getMod_id().toString());
			dynaBean.set("par_code", sysModule.getPar_id().toString());
		}
		request.setAttribute("", "13212");
		// 根据名称不同跳转不同的页面
		request.setAttribute("view_title", "联系我们");
		return new ActionForward("/./IndexEntp/IndexNewsInfo/viewLinkman.jsp");

	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		NewsInfo entity = new NewsInfo();
		super.copyProperties(entity, dynaBean);
		// TODO 不做任何判断
		// TODO 状态？未审核能显示吗？

		entity = super.getFacade().getNewsInfoService().getNewsInfo(entity);

		// 如果没好到怎么办？
		request.setAttribute("entity", entity);
		// baseLink10070List 顶部菜单导航
		request.setAttribute("baseLink10070List", super.common(10070).get("baseLinkList"));

		// 底部导航
		request.setAttribute("baseLink10090List", super.common(10090).get("baseLinkList"));

		// copyright的内容
		request.setAttribute("baseLink10091List", super.common(10091).get("baseLinkList"));

		String modName = "";
		SysModule sysModule = new SysModule();
		sysModule.setIs_del(0);
		sysModule.setMod_id(Long.valueOf(entity.getMod_id()));
		sysModule = super.getFacade().getSysModuleService().getSysModule(sysModule);
		if ((null != sysModule)) {
			modName = sysModule.getMod_name();

			request.setAttribute("mod_name", sysModule.getMod_name());
			dynaBean.set("mod_code", sysModule.getMod_id().toString());
			dynaBean.set("par_code", sysModule.getPar_id().toString());
		}
		request.setAttribute("baseLink10071", super.common(10071).get("baseLinkList").get(0));
		// 根据名称不同跳转不同的页面
		if (modName.indexOf("联系我们") >= 0) {
			request.setAttribute("view_title", "联系我们");
			return new ActionForward("/./IndexEntp/IndexNewsInfo/viewLinkman.jsp");
		} else if (modName.indexOf("人才招聘") >= 0) {
			request.setAttribute("view_title", "人才招聘");
			return new ActionForward("/./IndexEntp/IndexNewsInfo/viewJob.jsp");
		} else if (modName.indexOf("核心业务") >= 0) {
			request.setAttribute("view_title", "核心业务");
			return new ActionForward("/./IndexEntp/IndexNewsInfo/viewBusiness.jsp");
		} else if (modName.indexOf("案例中心") >= 0) {
			request.setAttribute("view_title", "案例中心");
			return new ActionForward("/./IndexEntp/IndexNewsInfo/viewCase.jsp");
		} else if (modName.indexOf("会员体系") >= 0) {
			request.setAttribute("view_title", "会员体系");
			return new ActionForward("/./IndexEntp/IndexNewsInfo/viewMember.jsp");
		} else {
			request.setAttribute("view_title", entity.getTitle() + "-新闻中心");
			return mapping.findForward("view");
		}

	}

}