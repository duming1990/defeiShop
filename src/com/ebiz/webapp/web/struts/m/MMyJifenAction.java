package com.ebiz.webapp.web.struts.m;

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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.UserJifenRecord;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.Keys.JifenTypeShow;
import com.ebiz.webapp.web.util.DateTools;

/**
 * @author Qin,Yue
 * @version 2011-04-22
 */
public class MMyJifenAction extends MBaseWebAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.index(mapping, form, request, response);
	}

	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}
		request.setAttribute("header_title", "收入明细");

		DynaBean dynaBean = (DynaBean) form;
		String par_id = (String) dynaBean.get("par_id");
		if (StringUtils.isNotBlank(par_id)) {
			getsonSysModuleListForMobile(request, dynaBean);
			return new ActionForward("/../m/MMyJifen/index.jsp");
		} else {
			return list(mapping, form, request, response);
		}
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);

		UserInfo ui = super.getUserInfoFromSession(request);

		Pager pager = (Pager) dynaBean.get("pager");
		super.getModNameForMobile(request);
		request.setAttribute("canSearch", true);
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

		Integer pageSize = 10;
		Integer recordCount = getFacade().getUserJifenRecordService().getUserJifenRecordCount(entity);
		pager.init(recordCount.longValue(), pageSize, pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<UserJifenRecord> list = getFacade().getUserJifenRecordService().getUserJifenRecordPaginatedList(entity);

		if (list.size() == Integer.valueOf(pageSize)) {
			request.setAttribute("appendMore", 1);
		}
		dynaBean.set("pageSize", pageSize);

		request.setAttribute("userJifenRecordList", list);

		request.setAttribute("jifenTypes", Keys.JifenTypeShow.values());
		return mapping.findForward("list");
	}

	public ActionForward getListJson(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}

		DynaBean dynaBean = (DynaBean) form;
		// getsonSysModuleList(request, dynaBean);

		Pager pager = (Pager) dynaBean.get("pager");
		String title_name_like = (String) dynaBean.get("title_name_like");
		String sc_type = (String) dynaBean.get("sc_type");
		String startPage = (String) dynaBean.get("startPage");
		String pageSize = (String) dynaBean.get("pageSize");
		if (StringUtils.isBlank(pageSize)) {
			pageSize = "10";
		}
		if (StringUtils.isBlank(startPage)) {
			startPage = "0";
		}

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
		pager.init(recordCount.longValue(), new Integer(pageSize), pager.getRequestPage());
		entity.getRow().setFirst(Integer.valueOf(startPage) * Integer.valueOf(pageSize));
		entity.getRow().setCount(pager.getRowCount());

		List<UserJifenRecord> list = getFacade().getUserJifenRecordService().getUserJifenRecordPaginatedList(entity);

		JSONObject datas = new JSONObject();
		JSONArray dataLoadList = new JSONArray();
		if ((null != list) && (list.size() > 0)) {
			for (UserJifenRecord ujfr : list) {
				JSONObject map = new JSONObject();

				String jifen_type = "";
				for (JifenTypeShow temp : Keys.JifenTypeShow.values()) {
					if (ujfr.getJifen_type() == temp.getIndex()) {
						jifen_type = temp.getName();
					}
				}

				map.put("jifen_type", jifen_type);
				map.put("card_no", ujfr.getCard_no());
				map.put("opt_c_score", dfFormat0.format(ujfr.getOpt_c_score()));
				map.put("opt_c_dianzibi", dfFormat.format(ujfr.getOpt_c_dianzibi()));
				map.put("card_user_name", (String) ujfr.getMap().get("card_user_name"));
				map.put("add_date", DateTools.getStringDate(ujfr.getAdd_date(), "yyyy-MM-dd"));

				dataLoadList.add(map);
			}
			datas.put("ret", "1");
			datas.put("msg", "查询成功");
			datas.put("appendMore", "0");
			if (list.size() == Integer.valueOf(pageSize)) {
				datas.put("appendMore", "1");
			}
			datas.put("dataList", dataLoadList.toString());
		} else {
			datas.put("ret", "0");
			datas.put("msg", "已全部加载");
		}

		logger.info("===datas:{}", datas.toString());
		super.renderJson(response, datas.toString());

		return null;

	}

}
