package com.ebiz.webapp.web.struts.m;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.SysOperLog;
import com.ebiz.webapp.domain.UserBiRecord;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;

/**
 * @author Wu,Yang
 * @version 2010-8-24
 */
public class IndexXianxiaAction extends MBaseWebAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfoFromSession(request);

		if (null == ui) {
			return this.list(mapping, form, request, response);
		} else {
			// 如果用户已经登陆，查询是否有红包
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

			UserBiRecord entity = new UserBiRecord();
			entity.setAdd_user_id(ui.getId());
			entity.setBi_chu_or_ru(Keys.BI_CHU_OR_RU.BASE_DATA_ID_1.getIndex());

			SysOperLog sLog = new SysOperLog();
			sLog.setOper_uid(ui.getId());
			sLog.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
			List<SysOperLog> sList = getFacade().getSysOperLogService().getSysOperLogList(sLog);
			if (sList != null && sList.size() > 0) {
				entity.getMap().put("begin_date", sList.get(0).getOper_time());
			} else {
				entity.getMap().put("begin_date", df.format(new Date()));
			}

			// entity.getMap().put("oper_date_le", new Date());

			List<UserBiRecord> list = getFacade().getUserBiRecordService().getUserBiRecordList(entity);

			return this.list(mapping, form, request, response);
		}

	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		boolean is_app = super.isApp(request);
		if (is_app) {
			super.setIsAppToCookie(request, response);
		}

		// 轮播图
		request.setAttribute("mBaseLink20List", super.getMBaseLinkList(200, 5, "no_null_image_path"));
		// 9宫格
		request.setAttribute("mBaseLink30List", super.getMBaseLinkList(300, 10, "no_null_image_path"));

		request.setAttribute("mBaseLink50List", super.getMBaseLinkList(500, 1, "no_null_image_path"));

		request.setAttribute("mBaseLink60List", super.getMBaseLinkList(600, 2, "no_null_image_path"));

		// 取商品 先取10个展示 然后后面自动加载

		String p_index = "";

		Cookie current_p_index = WebUtils.getCookie(request, Keys.COOKIE_P_INDEX);
		if (null != current_p_index) {
			p_index = URLDecoder.decode(current_p_index.getValue(), "UTF-8");
		}
		if (StringUtils.isNotBlank(p_index)) {
			if (p_index.equals(Keys.QUANGUO_P_INDEX.toString())) {
				p_index = null;
			}
		}
		DynaBean dynaBean = (DynaBean) form;

		// EntpInfo a = new EntpInfo();
		// a.setIs_del(0);
		// a.setAudit_state(Keys.Audit_Status.Audit_Status_2.getIndex());
		// a.getRow().setCount(2);
		// List<EntpInfo> entpInfoList = getFacade().getEntpInfoService().getEntpInfoList(a);
		// request.setAttribute("entpInfoList", entpInfoList);

		return mapping.findForward("list");
	}

	// 附近商家
	public ActionForward getNearbyEntpList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("====进入附近商家方法=====");
		DynaBean dynaBean = (DynaBean) form;
		String x = (String) dynaBean.get("x");
		String y = (String) dynaBean.get("y");
		String keyword = (String) dynaBean.get("keyword");
		String root_cls_id = (String) dynaBean.get("root_cls_id");
		String par_cls_id = (String) dynaBean.get("par_cls_id");
		Pager pager = (Pager) dynaBean.get("pager");
		String startPage = (String) dynaBean.get("startPage");
		String pageSize = (String) dynaBean.get("pageSize");
		String newPageSize = (String) dynaBean.get("newPageSize");

		JSONObject datas = new JSONObject();
		JSONArray dataLoadList = new JSONArray();

		String msg = "", code = "0";
		if (StringUtils.isBlank(x)) {
			msg = "经度参数有误！";
			super.ajaxReturnInfo(response, code, msg, datas);
			return null;
		}
		if (StringUtils.isBlank(y)) {
			msg = "纬度参数有误！";
			super.ajaxReturnInfo(response, code, msg, datas);
			return null;
		}

		if (StringUtils.isBlank(pageSize)) {
			pageSize = "10";
		}
		if (StringUtils.isBlank(startPage)) {
			startPage = "0";
		}

		EntpInfo entity = new EntpInfo();
		entity.setIs_del(0);
		entity.setAudit_state(Keys.audit_state.audit_state_2.getIndex());

		if (StringUtils.isNotBlank(x) && StringUtils.isNotBlank(y)) {

			// Point point = getXY(x, y);
			//
			// if (StringUtils.isNotBlank(String.valueOf(point.getLat()))) {
			// logger.info("xxxxx:" + point.getLat());
			// entity.getMap().put("x", point.getLat());
			// }
			// if (StringUtils.isNotBlank(String.valueOf(point.getLng()))) {
			// logger.info("yyyyy:" + point.getLng());
			// entity.getMap().put("y", point.getLng());
			// }
			entity.getMap().put("x", x);
			entity.getMap().put("y", y);
		}

		if (null != newPageSize)
			pageSize = newPageSize;

		Integer recordCount = getFacade().getEntpInfoService().getEntpInfoCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf(pageSize), pager.getRequestPage());
		if (null != startPage) {
			entity.getRow().setFirst(Integer.valueOf(startPage) * (Integer.valueOf(pageSize)));
		} else {
			entity.getRow().setFirst(pager.getFirstRow());
		}
		entity.getRow().setCount(pager.getRowCount());
		entity.getMap().put("orderBydistanceDesc", true);

		List<EntpInfo> entpInfoList = new ArrayList<EntpInfo>();
		if (StringUtils.isNotBlank(x) && StringUtils.isNotBlank(y)) {
			entpInfoList = getFacade().getEntpInfoService().getEntpInfoDistance(entity);
		} else {
			entpInfoList = getFacade().getEntpInfoService().getEntpInfoPaginatedList(entity);
		}
		String ctx = super.getCtxPath(request);
		if ((null != entpInfoList) && (entpInfoList.size() > 0)) {
			code = "1";
			for (EntpInfo b : entpInfoList) {
				JSONObject map = new JSONObject();
				map.put("id", b.getId());
				String entp_logo = b.getEntp_logo();
				map.put("entp_name", b.getEntp_name());
				map.put("sum_sale_money", b.getSum_sale_money());
				map.put("entp_addr", b.getEntp_addr());
				map.put("entp_tel", b.getEntp_tel());
				map.put("entp_latlng", b.getEntp_latlng());

				BigDecimal big_distance = super.getEntpDistance(b);
				map.put("distance", big_distance);

				if (StringUtils.isBlank(entp_logo)) {
					map.put("entp_logo", "/styles/imagesPublic/no_image.jpg");
					map.put("entp_logo_400", "/styles/imagesPublic/no_image.jpg");
				} else {
					String min_img = StringUtils.substringBeforeLast(entp_logo, ".") + "_400."
							+ FilenameUtils.getExtension(entp_logo);
					map.put("entp_logo", entp_logo);
					map.put("entp_logo_400", min_img);
				}
				String fanxian_rule_name = "";
				map.put("fanxian_rule_name", fanxian_rule_name);
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
