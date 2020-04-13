package com.ebiz.webapp.web.struts.m;

import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.BaseComminfoTags;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.web.Keys;

/**
 * @author Wu,Yang
 * @version 2011-12-14
 */
public class MTagsAction extends MBaseWebAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.index(mapping, form, request, response);
	}

	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return mapping.findForward("list");
	}

	public ActionForward getAjaxData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject datas = new JSONObject();
		JSONArray dataLoadList = new JSONArray();

		DynaBean dynaBean = (DynaBean) form;
		String msg = "", code = "0";
		String p_index = (String) dynaBean.get("p_index");
		String tag_id = (String) dynaBean.get("tag_id");// 标签（频道）id

		Pager pager = (Pager) dynaBean.get("pager");
		String startPage = (String) dynaBean.get("startPage");
		String pageSize = (String) dynaBean.get("pageSize");
		logger.info("=startPage={}", startPage);
		logger.info("=pageSize={}", pageSize);

		Cookie current_p_index = WebUtils.getCookie(request, Keys.COOKIE_P_INDEX);
		if (null == current_p_index) {
			setPindexCookies(response, Keys.QUANGUO_P_INDEX, Keys.QUANGUO_P_NAME);
		} else {
			if (StringUtils.isBlank(p_index)) {
				p_index = URLDecoder.decode(current_p_index.getValue(), "UTF-8");
			}
		}
		if (StringUtils.isNotBlank(p_index)) {
			if (p_index.equals(Keys.QUANGUO_P_INDEX.toString())) {
				p_index = null;
			}
		}

		if (StringUtils.isBlank(pageSize)) {
			pageSize = "10";
		}
		if (StringUtils.isBlank(startPage)) {
			startPage = "0";
		}
		// 标签
		BaseComminfoTags tags = new BaseComminfoTags();
		tags.setId(Integer.valueOf(tag_id));
		tags = getFacade().getBaseComminfoTagsService().getBaseComminfoTags(tags);
		if (null == tags) {
			code = "-1";
			msg = "频道不存在";
			super.returnInfo(response, code, msg, datas);
			return null;
		}
		datas.put("baseCommInfoTags", tags);

		// 标签商品
		CommInfo entity = new CommInfo();
		entity.setIs_sell(1);
		entity.setIs_del(0);
		entity.getMap().put("is_select", true);
		entity.getMap().put("tag_id", tag_id);
		entity.getMap().put("comm_info_tags_order_value", true);
		entity.getMap().put("not_out_sell_time", true);// 在上架期间
		entity.getMap().put("comm_type_in", Keys.CommType.COMM_TYPE_2.getIndex());
		entity.setAudit_state(Keys.audit_state.audit_state_1.getIndex());

		Integer recordCount = getFacade().getCommInfoService().getCommInfoCount(entity);
		pager.init(recordCount.longValue(), pager.getRowCount(), pager.getRequestPage());
		entity.getRow().setFirst(Integer.valueOf(startPage) * Integer.valueOf(pageSize));
		log.info("pager.getRowCount():" + pager.getRowCount());
		entity.getRow().setCount(pager.getRowCount());

		List<CommInfo> commInfoList = super.getFacade().getCommInfoService().getCommInfoPaginatedList(entity);

		datas.put("commInfoList", commInfoList);

		msg = "加载完成";

		if (commInfoList.size() < Integer.valueOf(pageSize) || recordCount == commInfoList.size()) {
			code = "2";
			msg = "没有更多数据";
		}
		log.info("code:" + code);
		super.returnInfo(response, code, msg, datas);
		return null;

	}
}
