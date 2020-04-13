package com.ebiz.webapp.web.struts.m;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.BaseData;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.web.Keys;

/**
 * @author Wu,Yang
 * @version 2010-8-24
 */
public class MIndexHdAction extends MBaseWebAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String hdtype = (String) dynaBean.get("hdtype");

		if (StringUtils.isNotBlank(hdtype) && hdtype.equals("1")) {
			return new ActionForward("/MSearch.do", true);
		}

		return this.index(mapping, form, request, response);
	}

	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String keyword = (String) dynaBean.get("keyword");
		String root_cls_id = (String) dynaBean.get("root_cls_id");
		String par_cls_id = (String) dynaBean.get("par_cls_id");
		String p_index = (String) dynaBean.get("p_index");
		String orderByParam = (String) dynaBean.get("orderByParam");

		Pager pager = (Pager) dynaBean.get("pager");

		List<CommInfo> entityList = new ArrayList<CommInfo>();
		entityList = super.getCommInfoHdList(null, false, null, false, false, orderByParam, true, pager, null, keyword,
				p_index, root_cls_id, par_cls_id, null, null);

		request.setAttribute("entityList", entityList);
		Integer pageSize = 4;
		if (entityList.size() == Integer.valueOf(pageSize)) {
			request.setAttribute("appendMore", 1);
		}

		if (StringUtils.isNotBlank(keyword)) {
			request.setAttribute("keyWordNavg", "共找到" + entityList.size() + "条<span>" + "“" + keyword + "”</span>"
					+ "相关结果");
		}

		dynaBean.set("pageSize", pageSize);

		super.setSlideNavForM(request);

		List<BaseData> baseData2100List = super.getBaseDataList(Keys.BASE_DATA_ID.BASE_DATA_ID_2100.getIndex());

		BaseData nowBaseData = new BaseData();
		nowBaseData.setType(Keys.BASE_DATA_ID.BASE_DATA_ID_2100.getIndex());
		nowBaseData.setIs_del(0);
		nowBaseData.getMap().put("now_date", ymdhms.format(new Date()));
		List<BaseData> baseDataNowList = getFacade().getBaseDataService().getBaseDataList(nowBaseData);
		if (null != baseDataNowList && baseDataNowList.size() > 0) {
			nowBaseData = baseDataNowList.get(0);
		}

		if (null != baseData2100List && baseData2100List.size() > 0 && null != nowBaseData
				&& null != nowBaseData.getId()) {
			for (BaseData temp : baseData2100List) {

				if (temp.getId().intValue() == nowBaseData.getId().intValue()) {
					Calendar now = Calendar.getInstance();
					String endTime = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) + "-"
							+ now.get(Calendar.DAY_OF_MONTH) + " " + temp.getPre_varchar_2();
					request.setAttribute("endTime", sdFormat_ymdhms.parse(endTime));
					temp.getMap().put("is_current", "true");
				}
			}
		}

		request.setAttribute("baseData2100List", baseData2100List);

		request.setAttribute("header_title", "促销活动");

		return new ActionForward("/MIndexHd/indexhd.jsp");
	}

	public ActionForward getPdListJson(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String msg = "", code = "0";
		String keyword = (String) dynaBean.get("keyword");
		String root_cls_id = (String) dynaBean.get("root_cls_id");
		String par_cls_id = (String) dynaBean.get("par_cls_id");
		String orderByParam = (String) dynaBean.get("orderByParam");
		String p_index = (String) dynaBean.get("p_index");

		Pager pager = (Pager) dynaBean.get("pager");
		String startPage = (String) dynaBean.get("startPage");
		String pageSize = (String) dynaBean.get("pageSize");
		logger.info("=startPage={}", startPage);
		logger.info("=pageSize={}", pageSize);
		JSONObject datas = new JSONObject();
		JSONArray dataLoadList = new JSONArray();

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
			pageSize = "4";
		}
		if (StringUtils.isBlank(startPage)) {
			startPage = "0";
		}
		List<CommInfo> entityList = new ArrayList<CommInfo>();
		entityList = super.getCommInfoListForJson(null, false, null, false, false, orderByParam, true, pager, null,
				keyword, p_index, root_cls_id, par_cls_id, null, Integer.valueOf(startPage), true, null, null, null,
				null);

		String ctx = super.getCtxPath(request);
		if ((null != entityList) && (entityList.size() > 0)) {
			code = "1";
			for (CommInfo b : entityList) {
				JSONObject map = new JSONObject();
				map.put("id", b.getId());
				String main_pic = b.getMain_pic();
				map.put("comm_name", b.getComm_name());
				map.put("sub_title", b.getSub_title());
				if (StringUtils.isBlank(main_pic)) {
					map.put("main_pic", "/styles/imagesPublic/no_image.jpg");
					map.put("main_pic_400", "/styles/imagesPublic/no_image.jpg");
				} else {
					String min_img = StringUtils.substringBeforeLast(main_pic, ".") + "_400."
							+ FilenameUtils.getExtension(main_pic);
					map.put("main_pic", ctx.concat("/").concat(main_pic));
					map.put("main_pic_400", ctx.concat("/").concat(min_img));
				}
				map.put("sale_price", dfFormat.format(b.getSale_price()));
				map.put("org_price", dfFormat.format(b.getPrice_ref()));
				long sale_count = b.getSale_count();
				if (sale_count > 10000) {
					Double sc = Double.valueOf(sale_count) / 10000;
					map.put("sale_count", dfFormat.format(sc) + "万");
				} else {
					map.put("sale_count", sale_count);
				}
				dataLoadList.add(map);
			}
		}
		datas.put("dataList", dataLoadList.toString());
		msg = "加载完成";

		if (dataLoadList.size() < 2) {
			code = "2";
			msg = "没有更多数据";
		}

		super.ajaxReturnInfo(response, code, msg, datas);
		return null;

	}

}
