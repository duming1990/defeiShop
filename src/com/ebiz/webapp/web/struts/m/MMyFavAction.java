package com.ebiz.webapp.web.struts.m;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.ScEntpComm;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.DateTools;

/**
 * @author Liu,Jia
 * @version 2014-05-28
 */
public class MMyFavAction extends MBaseWebAction {
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}

		request.setAttribute("header_title", "我的收藏");

		DynaBean dynaBean = (DynaBean) form;

		super.getsonSysModuleList(request, dynaBean);
		super.getModNameForMobile(request);
		Pager pager = (Pager) dynaBean.get("pager");
		String title_name_like = (String) dynaBean.get("title_name_like");
		String sc_type = (String) dynaBean.get("sc_type");

		ScEntpComm entity = new ScEntpComm();
		super.copyProperties(entity, form);
		entity.setAdd_user_id(ui.getId());
		entity.setIs_del(0);
		entity.getMap().put("title_name_like", title_name_like);

		if (StringUtils.isBlank(sc_type)) {
			// entity.setSc_type(1);
		}

		Integer pageSize = 10;
		Integer recordCount = getFacade().getScEntpCommService().getScEntpCommCount(entity);
		pager.init(recordCount.longValue(), pageSize, pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<ScEntpComm> entityList = super.getFacade().getScEntpCommService().getScEntpCommPaginatedList(entity);

		if (entityList.size() == Integer.valueOf(pageSize)) {
			request.setAttribute("appendMore", 1);
		}
		dynaBean.set("pageSize", pageSize);

		if (entityList.size() > 0) {
			for (ScEntpComm sec : entityList) {
				if (sec.getSc_type() == 1) {
					EntpInfo entpInfo = new EntpInfo();
					entpInfo.setId(sec.getLink_id());
					entpInfo.setIs_del(0);
					entpInfo = super.getFacade().getEntpInfoService().getEntpInfo(entpInfo);
					if (null != entpInfo) {
						sec.getMap().put("custom_url", entpInfo.getCustom_url());
					}
				}
			}
		}
		request.setAttribute("entityList", entityList);
		return mapping.findForward("list");
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String[] pks = (String[]) dynaBean.get("pks");

		if (StringUtils.isBlank(id) && ArrayUtils.isEmpty(pks)) {
			String msg = super.getMessage(request, "参数不能为空");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}

		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		ScEntpComm entity = new ScEntpComm();
		Date date = new Date();
		entity.setIs_del(1);
		entity.setDel_date(date);
		entity.setDel_user_id(ui.getId());
		if (StringUtils.isNotBlank(id)) {
			entity.setId(Integer.valueOf(id));
			super.getFacade().getScEntpCommService().modifyScEntpComm(entity);

			saveMessage(request, "entity.deleted");

		} else if (ArrayUtils.isNotEmpty(pks)) {
			entity.getMap().put("pks", pks);
			super.getFacade().getScEntpCommService().modifyScEntpComm(entity);

			saveMessage(request, "entity.deleted");
		}

		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(super.serialize(request, "id", "method")));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward getScEntpCommListJson(ActionMapping mapping, ActionForm form, HttpServletRequest request,
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

		ScEntpComm entity = new ScEntpComm();
		super.copyProperties(entity, form);
		entity.setAdd_user_id(ui.getId());
		entity.setIs_del(0);
		entity.getMap().put("title_name_like", title_name_like);

		if (StringUtils.isBlank(sc_type)) {
			entity.setSc_type(1);
		}

		long recordCount = getFacade().getScEntpCommService().getScEntpCommCount(entity);
		pager.init(recordCount, new Integer(pageSize), pager.getRequestPage());
		entity.getRow().setFirst(Integer.valueOf(startPage) * Integer.valueOf(pageSize));
		entity.getRow().setCount(pager.getRowCount());

		List<ScEntpComm> secList = getFacade().getScEntpCommService().getScEntpCommPaginatedList(entity);

		JSONObject datas = new JSONObject();
		JSONArray dataLoadList = new JSONArray();
		// String ctx = super.getCtxPath(request);
		if ((null != secList) && (secList.size() > 0)) {
			for (ScEntpComm sec : secList) {
				JSONObject map = new JSONObject();
				map.put("id", sec.getId());
				map.put("title_name", sec.getTitle_name());
				map.put("send_time", DateTools.getStringDate(sec.getAdd_date(), "yyyy-MM-dd"));

				dataLoadList.add(map);
			}
			datas.put("ret", "1");
			datas.put("msg", "查询成功");
			datas.put("appendMore", "0");
			if (secList.size() == Integer.valueOf(pageSize)) {
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
