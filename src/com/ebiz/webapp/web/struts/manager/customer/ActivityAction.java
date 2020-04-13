package com.ebiz.webapp.web.struts.manager.customer;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.ebiz.webapp.domain.Activity;
import com.ebiz.webapp.domain.ActivityApply;
import com.ebiz.webapp.domain.ActivityApplyComm;
import com.ebiz.webapp.domain.NewsAttachment;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.struts.manager.admin.BaseAdminAction;
import com.ebiz.webapp.web.util.DateTools;
import com.ebiz.webapp.web.util.EncryptUtilsV2;

public class ActivityAction extends BaseAdminAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "1")) {
			return super.checkPopedomInvalid(request, response);
		}
		saveToken(request);

		if (!StringUtils.equalsIgnoreCase(request.getMethod(), "POST")) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;

		String mod_id = (String) dynaBean.get("mod_id");
		Activity entity = new Activity();

		super.copyProperties(form, entity);

		return mapping.findForward("input");
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "0")) {
			return super.checkPopedomInvalid(request, response);
		}
		setNaviStringToRequestScope(request);
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(Keys.SESSION_USERINFO_KEY);
		// super.setActivitiListPublic(form, request, "true");

		ActivityApply entity = new ActivityApply();
		entity.setUser_id(userInfo.getId());
		entity.setEntp_id(userInfo.getOwn_entp_id());

		List<ActivityApply> newsInfoList = getFacade().getActivityApplyService().getActivityApplyPaginatedList(entity);
		request.setAttribute("entityList", newsInfoList);
		// request.setAttribute("admin", "true");

		// return mapping.findForward("list");
		return new ActionForward("/../manager/customer/Activity/list.jsp");

	}

	public ActionForward list1(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);

		// super.setActivitiListPublic(form, request, "getData");
		DynaBean dynaBean = (DynaBean) form;

		String is_del = (String) dynaBean.get("is_del");
		String mod_id = (String) dynaBean.get("mod_id");
		String st_start_date = (String) dynaBean.get("st_start_date");
		String en_end_date = (String) dynaBean.get("en_end_date");
		String title_like = (String) dynaBean.get("title_like");
		String is_admin = (String) dynaBean.get("is_admin");

		ActivityApplyComm a = new ActivityApplyComm();
		Pager pager = (Pager) dynaBean.get("pager");

		a.getMap().put("title_like", title_like);
		a.getMap().put("st_start_date", st_start_date);
		a.getMap().put("en_end_date", en_end_date);

		Integer recordCount = getFacade().getActivityApplyCommService().selectActivityApplyCommOrderCount(a);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		a.getRow().setFirst(pager.getFirstRow());
		a.getRow().setCount(pager.getRowCount());

		List<ActivityApplyComm> activityOrderList = getFacade().getActivityApplyCommService()
				.selectActivityApplyCommOrderList(a);
		request.setAttribute("entityList", activityOrderList);
		return new ActionForward("/../manager/customer/Activity/getData.jsp");
	}

	public ActionForward toExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String comm_name_like = (String) dynaBean.get("comm_name_like");
		String orderDay = (String) dynaBean.get("orderDay");
		String id = (String) dynaBean.get("id");
		String saleCount = (String) dynaBean.get("saleCount");
		String entp_id = request.getParameter("entp_id");
		String activity_id = request.getParameter("activity_id");
		String code = (String) dynaBean.get("code");

		ActivityApplyComm a = new ActivityApplyComm();
		// a.setId(Integer.valueOf(id));
		if (null != activity_id) {
			a.setActivity_id(Integer.valueOf(activity_id));
		}
		if (null != entp_id) {
			a.setEntp_id(Integer.valueOf(entp_id));
		}
		if (StringUtils.isNotBlank(orderDay)) {
			if (orderDay.equals("1")) {// 查询本月
				a.getMap().put("st_add_date", DateTools.getFirstDayThisMonth());
				a.getMap().put("en_add_date", DateTools.getLastDayThisMonth());
			} else if (orderDay.equals("2")) {// 查询昨日排名
				a.getMap().put("st_add_date", DateTools.getLastDay(1));
				a.getMap().put("en_add_date", DateTools.getLastDay(1));
			} else if (orderDay.equals("3")) {// 查询今日排名
				a.getMap().put("st_add_date", sdFormat_ymd.format(new Date()));
				a.getMap().put("en_add_date", sdFormat_ymd.format(new Date()));
			}
		}
		a.getMap().put("comm_name_like", comm_name_like);
		Integer recordCount = getFacade().getActivityApplyCommService().selectActivityApplyCommOrderEntpCount(a);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		a.getRow().setFirst(pager.getFirstRow());
		a.getRow().setCount(pager.getRowCount());

		List<ActivityApplyComm> entityList = getFacade().getActivityApplyCommService()
				.selectActivityApplyCommOrderEntpList(a);

		if (StringUtils.isBlank(saleCount)) {
			saleCount = "0";
		}

		// dynaBean.set("comm_name_like", comm_name_like);
		// request.setAttribute("entityList", entityList);

		model.put("entityList", entityList);

		model.put("title", "商品销售排名" + sdFormat_ymd.format(new Date()));
		model.put("saleCount", Integer.valueOf(saleCount));

		String content = getFacade().getTemplateService().getContent("ActivityApplyComm/list.ftl", model);
		// 下载文件出现乱码时，请参见此处
		String fname = EncryptUtilsV2.encodingFileName("商品销售排名" + sdFormat_ymd.format(new Date()) + ".xls");

		if (StringUtils.isBlank(code)) {
			code = "UTF-8";
		}
		response.setCharacterEncoding(code);
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename=" + fname);

		PrintWriter out = response.getWriter();
		out.println(content);
		out.flush();
		out.close();
		return null;

	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (isCancelled(request)) {
			return list(mapping, form, request, response);
		}
		if (!isTokenValid(request)) {
			saveError(request, "errors.token");
			return list(mapping, form, request, response);
		}
		resetToken(request);

		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		DynaBean dynaBean = (DynaBean) form;
		String mod_id = (String) dynaBean.get("mod_id");

		String is_audit = (String) dynaBean.get("is_audit");// 区别审核(只需要修改一个状态) 和 修改、添加
		Activity entity = new Activity();
		super.copyProperties(entity, form);
		if (StringUtils.isBlank(is_audit)) {

			if (null == entity.getId()) {
				entity.setIs_del(0);
				entity.setAdd_date(new Date());
				getFacade().getActivityService().createActivity(entity);
				saveMessage(request, "entity.inerted");

			} else {
				getFacade().getActivityService().modifyActivity(entity);
				saveMessage(request, "entity.updated");

			}
		} else {
			// 审核，只修改状态
			getFacade().getActivityService().modifyActivity(entity);
			saveMessage(request, "entity.updated");
		}
		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "4")) {
			return super.checkPopedomInvalid(request, response);
		}
		if (!StringUtils.equalsIgnoreCase(request.getMethod(), "POST")) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String[] pks = (String[]) dynaBean.get("pks");
		String mod_id = (String) dynaBean.get("mod_id");

		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		Activity entity = new Activity();

		// entity.setIs_del(1);

		if (!StringUtils.isBlank(id) && GenericValidator.isLong(id)) {

			entity.setId(Integer.valueOf(id));
			getFacade().getActivityService().removeActivity(entity);

			saveMessage(request, "entity.deleted");

		} else if (!ArrayUtils.isEmpty(pks)) {
			entity.getMap().put("pks", pks);
			getFacade().getActivityService().removeActivity(entity);
			saveMessage(request, "entity.deleted");

		}
		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(super.serialize(request, "id", "method")));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "2")) {
			return super.checkPopedomInvalid(request, response);
		}
		saveToken(request);
		if (!StringUtils.equalsIgnoreCase(request.getMethod(), "POST")) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String mod_id = (String) dynaBean.get("mod_id");
		if (GenericValidator.isLong(id)) {
			Activity newsInfo = new Activity();
			newsInfo.setId(Integer.valueOf(id));
			newsInfo = getFacade().getActivityService().getActivity(newsInfo);

			NewsAttachment attachment = new NewsAttachment();
			attachment.setLink_id(Integer.valueOf(id));
			request.setAttribute("attachmentList",
					getFacade().getNewsAttachmentService().getNewsAttachmentList(attachment));

			if (null == newsInfo) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");
			}
			// the line below is added for pagination
			newsInfo.setQueryString(super.serialize(request, "id", "method"));
			// end
			super.copyProperties(form, newsInfo);
			return mapping.findForward("input");
		} else {
			saveError(request, "errors.Integer", id);
			return mapping.findForward("list");
		}
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// if (null == super.checkUserModPopeDom(form, request, "0")) {
		// return super.checkPopedomInvalid(request, response);
		// }
		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		if (GenericValidator.isInt(id)) {
			Activity entity = new Activity();
			entity.setId(Integer.valueOf(id));
			entity = getFacade().getActivityService().getActivity(entity);

			if (null == entity) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");
			}
			super.copyProperties(form, entity);
			return mapping.findForward("view");
		} else {
			saveError(request, "errors.Integer", id);
			return mapping.findForward("list");
		}
	}

	@Override
	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if ((ip == null) || (ip.length() == 0) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if ((ip == null) || (ip.length() == 0) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if ((ip == null) || (ip.length() == 0) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public String formatDate(Date date) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
		return f.format(date);
	}

	public ActionForward audit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "8")) {
			return super.checkPopedomInvalid(request, response);
		}
		saveToken(request);
		if (!StringUtils.equalsIgnoreCase(request.getMethod(), "POST")) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String mod_id = (String) dynaBean.get("mod_id");
		if (GenericValidator.isLong(id)) {
			Activity newsInfo = new Activity();
			newsInfo.setId(Integer.valueOf(id));
			newsInfo = getFacade().getActivityService().getActivity(newsInfo);

			NewsAttachment attachment = new NewsAttachment();
			attachment.setLink_id(Integer.valueOf(id));
			request.setAttribute("attachmentList",
					getFacade().getNewsAttachmentService().getNewsAttachmentList(attachment));

			if (null == newsInfo) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");
			}
			// the line below is added for pagination
			newsInfo.setQueryString(super.serialize(request, "id", "method"));
			// end
			super.copyProperties(form, newsInfo);
			return new ActionForward("/../manager/customer/Activity/audit.jsp");
		} else {
			saveError(request, "errors.Integer", id);
			return mapping.findForward("list");
		}
	}

	public ActionForward getData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(Keys.SESSION_USERINFO_KEY);
		DynaBean dynaBean = (DynaBean) form;

		String is_del = (String) dynaBean.get("is_del");
		String mod_id = (String) dynaBean.get("mod_id");
		String st_start_date = (String) dynaBean.get("st_start_date");
		String en_end_date = (String) dynaBean.get("en_end_date");
		String title_like = (String) dynaBean.get("title_like");
		String is_admin = (String) dynaBean.get("is_admin");

		ActivityApplyComm a = new ActivityApplyComm();
		Pager pager = (Pager) dynaBean.get("pager");

		a.getMap().put("title_like", title_like);
		a.getMap().put("st_start_date", st_start_date);
		a.getMap().put("en_end_date", en_end_date);
		a.setEntp_id(userInfo.getOwn_entp_id());

		Integer recordCount = getFacade().getActivityApplyCommService().selectActivityApplyCommOrderCount(a);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		a.getRow().setFirst(pager.getFirstRow());
		a.getRow().setCount(pager.getRowCount());

		List<ActivityApplyComm> activityOrderList = getFacade().getActivityApplyCommService()
				.selectActivityApplyCommOrderList(a);
		for (ActivityApplyComm ao : activityOrderList) {
			if (null != ao.getMap().get("good_sum_price")) {
				BigDecimal decimal = new BigDecimal(ao.getMap().get("good_sum_price").toString());
				ao.getMap().put("good_sum_price", decimal.setScale(2,BigDecimal.ROUND_HALF_UP));
			}
		}
		request.setAttribute("entityList", activityOrderList);
		request.setAttribute("userInfo", userInfo);
		// return new ActionForward("/../manager/admin/Activity/getData.jsp");
		dynaBean.set("title_like", title_like);
		dynaBean.set("st_start_date", st_start_date); 
		dynaBean.set("en_end_date", en_end_date);
		dynaBean.set("entp_id", userInfo.getOwn_entp_id());
		return new ActionForward("/../manager/customer/Activity/getDataView.jsp");
	}

	public ActionForward getDataView(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(Keys.SESSION_USERINFO_KEY);
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		ActivityApply a = new ActivityApply();
		a.setLink_id(Integer.valueOf(id));
		// 申请总数量
		int count_sum = getFacade().getActivityApplyService().getActivityApplyCount(a);
		request.setAttribute("count_sum", count_sum);
		a.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		// 审核通过数量
		int count_audit = getFacade().getActivityApplyService().getActivityApplyCount(a);
		request.setAttribute("count_audit", count_audit);
		request.setAttribute("count_not_audit", count_sum - count_audit);

		List<ActivityApply> list = getFacade().getActivityApplyService().selectActivityApplyOrderSum(a);
		request.setAttribute("list", list);

		return new ActionForward("/../manager/customer/Activity/getDataView.jsp");
	}

	// 商品排名
	public ActionForward getDataViewComm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(Keys.SESSION_USERINFO_KEY);
		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String comm_name_like = (String) dynaBean.get("comm_name_like");
		String orderDay = (String) dynaBean.get("orderDay");
		String id = (String) dynaBean.get("id");
		String saleCount = (String) dynaBean.get("saleCount");
		String entp_id = request.getParameter("entp_id");
		String activity_id = request.getParameter("activity_id");
		String st_start_date = (String) dynaBean.get("st_start_date");
		String en_end_date = (String) dynaBean.get("en_end_date");

		ActivityApplyComm a = new ActivityApplyComm();
		// a.setId(Integer.valueOf(id));
		if (null != activity_id) {
			a.setActivity_id(Integer.valueOf(activity_id));
		}

		a.setEntp_id(userInfo.getOwn_entp_id());

		if (StringUtils.isNotBlank(orderDay)) {
			if (orderDay.equals("1")) {// 查询本月
				a.getMap().put("st_add_date", DateTools.getFirstDayThisMonth());
				a.getMap().put("en_add_date", DateTools.getLastDayThisMonth());
			} else if (orderDay.equals("2")) {// 查询昨日排名
				a.getMap().put("st_add_date", DateTools.getLastDay(1));
				a.getMap().put("en_add_date", DateTools.getLastDay(1));
			} else if (orderDay.equals("3")) {// 查询今日排名
				a.getMap().put("st_add_date", sdFormat_ymd.format(new Date()));
				a.getMap().put("en_add_date", sdFormat_ymd.format(new Date()));
			}
		}
		a.getMap().put("st_start_date", st_start_date);
		a.getMap().put("en_end_date", en_end_date);
		a.getMap().put("comm_name_like", comm_name_like);
		Integer recordCount = getFacade().getActivityApplyCommService().selectActivityApplyCommOrderEntpCount(a);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		a.getRow().setFirst(pager.getFirstRow());
		a.getRow().setCount(pager.getRowCount());

		List<ActivityApplyComm> entityList = getFacade().getActivityApplyCommService()
				.selectActivityApplyCommOrderEntpList(a);

		if (StringUtils.isBlank(saleCount)) {
			saleCount = "0";
		}
		dynaBean.set("comm_name_like", comm_name_like);
		request.setAttribute("entityList", entityList);
		return new ActionForward("/../manager/customer/Activity/getDataViewComm.jsp");
	}
}