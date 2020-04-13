package com.ebiz.webapp.web.struts.m;

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
import com.ebiz.webapp.domain.BaseClass;
import com.ebiz.webapp.domain.BaseLink;
import com.ebiz.webapp.domain.NewsInfo;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;

/**
 * @author Wu,Yang
 * @version 2010-8-24
 */
public class MNewsInfoAction extends MBaseWebAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String mod_code = (String) dynaBean.get("mod_code");

		Pager pager = (Pager) dynaBean.get("pager");
		// dynaBean.set("mod_code", mod_code);
		//
		// if (StringUtils.isEmpty(mod_code) && !GenericValidator.isLong(mod_code)) {
		// String msg = super.getMessage(request, "errors.parm");
		// return super.showTipMsg(mapping, form, request, response, msg);
		// }

		request.setAttribute("header_title", "新闻列表");

		return mapping.findForward("list");

	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String uuid = (String) dynaBean.get("uuid");
		String news_id = (String) dynaBean.get("news_id");

		request.setAttribute("header_title", "新闻详情");

		if (StringUtils.isEmpty(uuid) || uuid.length() != 36) {
			String msg = super.getMessage(request, "errors.parm");
			return super.showTipMsg(mapping, form, request, response, msg);
		} else {
			NewsInfo newsInfo = new NewsInfo();
			newsInfo.setUuid(uuid);
			NewsInfo entity = getFacade().getNewsInfoService().getNewsInfo(newsInfo);
			if (null == entity) {
				String msg = super.getMessage(request, "entity.missing");
				return super.showTipMsg(mapping, form, request, response, msg);
			}

			if (null != entity.getView_count()) {
				newsInfo.setView_count(entity.getView_count() + 1);
			} else {
				newsInfo.setView_count(0);
			}
			newsInfo.setId(entity.getId());
			getFacade().getNewsInfoService().modifyNewsInfo(newsInfo);

			request.setAttribute("newsInfo", entity);
			if (entity.getCls_name() != null) {

				request.setAttribute("header_title", entity.getCls_name());
			}

			request.setAttribute("isApp", super.isApp(request));
			return mapping.findForward("view");
		}
	}

	public ActionForward viewNews(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.setPublicInfoList(request);
		DynaBean dynaBean = (DynaBean) form;
		String news_id = (String) dynaBean.get("news_id");

		NewsInfo newsInfo = new NewsInfo();
		newsInfo.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		newsInfo.setId(Integer.valueOf(news_id));
		NewsInfo newsInfo1 = getFacade().getNewsInfoService().getNewsInfo(newsInfo);
		request.setAttribute("newsinfo", newsInfo1);

		if (null != newsInfo.getView_count()) {
			newsInfo.setView_count(newsInfo1.getView_count() + 1);
		} else {
			newsInfo.setView_count(0);
		}
		request.setAttribute("isApp", super.isApp(request));
		super.getFacade().getNewsInfoService().modifyNewsInfo(newsInfo);

		OrderInfo orderInfo = new OrderInfo();
		orderInfo.getMap().put("order_state_ge", Keys.OrderState.ORDER_STATE_10.getIndex());
		orderInfo.setOrder_type(Keys.OrderType.ORDER_TYPE_10.getIndex());
		int orderCount = getFacade().getOrderInfoService().getOrderInfoCount(orderInfo);
		request.setAttribute("orderCount", orderCount);
		super.getBaseProvinceCityList(request, "cityList", Integer.valueOf(Keys.P_INDEX_CITY.toString()));

		List<BaseLink> base600LinkList = this.getBaseLinkList(600, 4, "no_null_image_path");
		request.setAttribute("base600LinkList", base600LinkList);
		logger.info("================base600LinkList=======================" + base600LinkList.size());

		// return mapping.findForward("view");
		return new ActionForward("/../m/MNewsInfo/view_new.jsp");
	}

	public ActionForward getNewsListJson(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;

		String mod_code = (String) dynaBean.get("mod_code");
		Pager pager = (Pager) dynaBean.get("pager");
		String startPage = (String) dynaBean.get("startPage");
		String pageSize = (String) dynaBean.get("pageSize");
		if (StringUtils.isBlank(pageSize)) {
			pageSize = "10";
		}
		if (StringUtils.isBlank(startPage)) {
			startPage = "0";
		}

		NewsInfo entity = new NewsInfo();
		super.copyProperties(entity, form);
		entity.setMod_id(mod_code);
		entity.setIs_del(0);
		entity.setInfo_state(3);

		Integer recordCount = getFacade().getNewsInfoService().getNewsInfoCount(entity);
		pager.init(recordCount.longValue(), new Integer(pageSize), pager.getRequestPage());
		entity.getRow().setFirst(Integer.valueOf(startPage) * Integer.valueOf(pageSize));
		entity.getRow().setCount(pager.getRowCount());

		JSONObject datas = new JSONObject();
		JSONArray dataLoadList = new JSONArray();
		List<NewsInfo> newsInfoList = getFacade().getNewsInfoService().getNewsInfoPaginatedList(entity);
		if ((null != newsInfoList) && (newsInfoList.size() > 0)) {
			for (NewsInfo temp : newsInfoList) {
				JSONObject map = new JSONObject();
				map.put("uuid", temp.getUuid());
				map.put("title", temp.getTitle());
				map.put("pub_time", sdFormat_ymd.format(temp.getPub_time()));
				dataLoadList.add(map);
			}
			datas.put("ret", "1");
			datas.put("msg", "查询成功");
			datas.put("appendMore", "0");
			if (newsInfoList.size() == Integer.valueOf(pageSize)) {
				datas.put("appendMore", "1");
			}
			datas.put("dataList", dataLoadList.toString());
		} else {
			datas.put("ret", "0");
			datas.put("msg", "已全部加载");
		}
		logger.info("==================" + datas.toString());
		super.renderJson(response, datas.toString());
		return null;

	}

	public ActionForward testNews(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		DynaBean dynaBean = (DynaBean) form;
		String cls_id = (String) dynaBean.get("cls_id");
		String mod_id = (String) dynaBean.get("mod_id");
		if (StringUtils.isNotBlank(cls_id)) {
			dynaBean.set("cls_id", cls_id);
		}
		// 商城快报导航分类
		BaseClass basePdClass = new BaseClass();
		basePdClass.setIs_del(0);// 0：未删除
		basePdClass.setCls_scope(3);
		basePdClass.setCls_level(1);
		basePdClass.getMap().put("no_have_self", "1");
		List<BaseClass> basePdClassList = getFacade().getBaseClassService().getBaseClassList(basePdClass);
		if (basePdClassList != null && basePdClassList.size() > 0) {
			request.setAttribute("basePdClassList", basePdClassList);
		}
		request.setAttribute("isApp", super.isApp(request));
		return new ActionForward("/../m/MNewsInfo/indexNews.jsp");
	}

	public ActionForward getListJson(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String mod_id = (String) dynaBean.get("mod_id");
		String link_id = (String) dynaBean.get("link_id");
		String add_uid = (String) dynaBean.get("add_uid");
		String startPage = (String) dynaBean.get("startPage");
		Pager pager = (Pager) dynaBean.get("pager");
		String startRow = (String) dynaBean.get("startRow");
		JSONObject data = new JSONObject();
		NewsInfo newsInfo = new NewsInfo();
		newsInfo.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		newsInfo.setMod_id(mod_id);
		if (StringUtils.isNotBlank(mod_id)) {
			newsInfo.setMod_id(mod_id);
		}
		if (StringUtils.isNotBlank(link_id)) {
			newsInfo.setLink_id(Integer.valueOf(link_id));
		}
		if (StringUtils.isNotBlank(add_uid)) {
			newsInfo.setAdd_uid(Integer.valueOf(add_uid));
		}

		if (StringUtils.isBlank(startPage)) {
			startPage = "0";
		}

		Integer pageSize = 6;
		Integer recordCount = getFacade().getNewsInfoService().getNewsInfoCount(newsInfo);
		pager.init(recordCount.longValue(), pageSize, pager.getRequestPage());
		newsInfo.getRow().setFirst(new Integer(startPage) * pageSize);
		newsInfo.getRow().setCount(pageSize);

		List<NewsInfo> newsInfoList = getFacade().getNewsInfoService().getNewsInfoPaginatedList(newsInfo);
		data.put("newsInfoList", newsInfoList);

		return super.returnAjaxData(response, "", "", data);

	}
}
