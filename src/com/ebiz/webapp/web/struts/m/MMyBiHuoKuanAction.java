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
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.UserBiRecord;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.Keys.BiGetType;
import com.ebiz.webapp.web.util.DateTools;

/**
 * @author Li,Yuan
 * @version 2012-02-22
 */
public class MMyBiHuoKuanAction extends MBaseWebAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.view(mapping, form, request, response);
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.getModNameForMobile(request);
		saveToken(request);

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}

		request.setAttribute("titleSideName", Keys.TopBtns.VIEW.getName());
		UserInfo entity = new UserInfo();
		entity.setId(ui.getId());
		entity = getFacade().getUserInfoService().getUserInfo(entity);
		if (null == entity) {
			String msg = "用户名不存在或者已经被删除！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}
		super.copyProperties(form, entity);

		return mapping.findForward("view");
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}
		super.getModNameForMobile(request);
		Pager pager = (Pager) dynaBean.get("pager");

		UserBiRecord entity = new UserBiRecord();
		super.copyProperties(entity, form);
		// if (StringUtils.isBlank(bi_chu_or_ru)) {
		// entity.setBi_chu_or_ru(1);
		// }
		// 页面显示条件
		entity.setAdd_user_id(ui.getId());
		entity.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		entity.setBi_type(Keys.BiType.BI_TYPE_300.getIndex());

		String bi_get_types = (String) dynaBean.get("bi_get_types");
		entity.getMap().put("bi_get_types", bi_get_types);// 余额转让记录

		String begin_date = (String) dynaBean.get("begin_date");
		String end_date = (String) dynaBean.get("end_date");
		entity.getMap().put("begin_date", begin_date);
		entity.getMap().put("end_date", end_date);

		entity.getMap().put("order_by_info", "add_date desc,");
		// 分页
		Integer pageSize = 10;
		Integer recordCount = getFacade().getUserBiRecordService().getUserBiRecordCount(entity);
		pager.init(recordCount.longValue(), pageSize, pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());
		// 查询集合
		List<UserBiRecord> userBiRecordlList = getFacade().getUserBiRecordService()
				.getUserBiRecordPaginatedList(entity);
		if (userBiRecordlList.size() == Integer.valueOf(pageSize)) {
			request.setAttribute("appendMore", 1);
		}
		dynaBean.set("pageSize", pageSize);
		if (userBiRecordlList.size() > 0) {
			for (UserBiRecord temp : userBiRecordlList) {
				if (null != temp.getLink_id()) {
					OrderInfo orderInfo = new OrderInfo();
					orderInfo.setId(temp.getLink_id());
					orderInfo = super.getFacade().getOrderInfoService().getOrderInfo(orderInfo);
					if (null != orderInfo) {
						temp.getMap().put("buy_name", orderInfo.getAdd_user_name());
						temp.getMap().put("trade_index", orderInfo.getTrade_index());
					}
				}
			}
		}

		request.setAttribute("biGetTypes", Keys.BiGetType.values());

		request.setAttribute("userBiRecordlList", userBiRecordlList);
		return mapping.findForward("list");
	}

	public ActionForward getListJson(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}
		Pager pager = (Pager) dynaBean.get("pager");
		String startPage = (String) dynaBean.get("startPage");
		String pageSize = (String) dynaBean.get("pageSize");
		if (StringUtils.isBlank(pageSize)) {
			pageSize = "10";
		}
		if (StringUtils.isBlank(startPage)) {
			startPage = "0";
		}

		UserBiRecord entity = new UserBiRecord();
		super.copyProperties(entity, form);
		// 页面显示条件
		entity.setAdd_user_id(ui.getId());
		entity.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		entity.setBi_type(Keys.BiType.BI_TYPE_300.getIndex());

		String bi_get_types = (String) dynaBean.get("bi_get_types");
		entity.getMap().put("bi_get_types", bi_get_types);// 余额转让记录

		String begin_date = (String) dynaBean.get("begin_date");
		String end_date = (String) dynaBean.get("end_date");
		entity.getMap().put("begin_date", begin_date);
		entity.getMap().put("end_date", end_date);

		entity.getMap().put("order_by_info", "add_date desc,");
		// 分页
		Integer recordCount = getFacade().getUserBiRecordService().getUserBiRecordCount(entity);
		pager.init(recordCount.longValue(), new Integer(pageSize), pager.getRequestPage());
		entity.getRow().setFirst(Integer.valueOf(startPage) * Integer.valueOf(pageSize));
		entity.getRow().setCount(pager.getRowCount());
		// 查询集合
		List<UserBiRecord> userBiRecordlList = getFacade().getUserBiRecordService()
				.getUserBiRecordPaginatedList(entity);
		if (userBiRecordlList.size() == Integer.valueOf(pageSize)) {
			request.setAttribute("appendMore", 1);
		}
		dynaBean.set("pageSize", pageSize);
		JSONObject datas = new JSONObject();
		JSONArray dataLoadList = new JSONArray();
		if (userBiRecordlList.size() > 0) {
			for (UserBiRecord temp : userBiRecordlList) {
				JSONObject map = new JSONObject();
				String desc_name = "";
				for (BiGetType tempBiGetType : Keys.BiGetType.values()) {
					if (temp.getBi_get_type() == tempBiGetType.getIndex()) {
						desc_name = tempBiGetType.getName();
					}
				}
				map.put("desc_name", desc_name);
				map.put("bi_chu_or_ru", temp.getBi_chu_or_ru());
				map.put("bi_get_type", temp.getBi_get_type());
				map.put("bi_no", temp.getBi_no());
				map.put("bi_no_before", temp.getBi_no_before());
				map.put("add_date", DateTools.getStringDate(temp.getAdd_date(), "yyyy-MM-dd HH:mm"));
				map.put("bi_no_after", temp.getBi_no_after());

				if (null != temp.getLink_id()) {
					OrderInfo orderInfo = new OrderInfo();
					orderInfo.setId(temp.getLink_id());
					orderInfo = super.getFacade().getOrderInfoService().getOrderInfo(orderInfo);
					if (null != orderInfo) {
						map.put("buy_name", orderInfo.getAdd_user_name());
						map.put("trade_index", orderInfo.getTrade_index());
					}
				}

				dataLoadList.add(map);
			}
			datas.put("ret", "1");
			datas.put("msg", "查询成功");
			datas.put("appendMore", "0");
			if (userBiRecordlList.size() == Integer.valueOf(pageSize)) {
				datas.put("appendMore", "1");
			}
			datas.put("dataList", dataLoadList.toString());
		} else {
			datas.put("ret", "0");
			datas.put("msg", "已全部加载");
		}

		super.renderJson(response, datas.toString());
		return null;
	}

}
