package com.ebiz.webapp.web.struts.manager.customer;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.OrderInfoDetails;
import com.ebiz.webapp.domain.ServiceCenterInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.DateTools;
import com.ebiz.webapp.web.util.EncryptUtilsV2;

/**
 * @author renqiang
 * @version 2017-11-15
 */

public class StatisticsOrderInfoDetailsAction extends BaseCustomerAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(Keys.SESSION_USERINFO_KEY);

		if (null == userInfo) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}

		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);

		// 商品列表
		Pager pager = (Pager) dynaBean.get("pager");
		String orderDay = (String) dynaBean.get("orderDay");
		String comm_name_like = (String) dynaBean.get("comm_name_like");
		String st_date = (String) dynaBean.get("st_date");
		String en_date = (String) dynaBean.get("en_date");
		String own_entp_id = (String) dynaBean.get("own_entp_id");
		String orderType = (String) dynaBean.get("orderType");

		if (StringUtils.isNotBlank(orderDay)) {
			if (orderDay.equals("1")) {// 查询上月
				st_date = sdFormat_ymd.format(DateTools.getFirstDayOfLastMonday(new Date()));
				en_date = sdFormat_ymd.format(DateTools.getLastDayOfLastMonday(new Date()));
			} else if (orderDay.equals("2")) {// 查询上周
				int today = DateTools.getWeekOfDateIndex(new Date());
				st_date = DateTools.getLastDay(today + 6);
				en_date = DateTools.getLastDay(today);
			} else if (orderDay.equals("3")) {// 查询昨日
				st_date = DateTools.getLastDay(1);
				en_date = DateTools.getLastDay(1);
			}
		}

		OrderInfoDetails entity = new OrderInfoDetails();
		super.copyProperties(entity, form);

		if (StringUtils.isNotBlank(orderType) && Integer.valueOf(orderType) == Keys.OrderType.ORDER_TYPE_90.getIndex()) {
			entity.getMap().put("orderType", orderType);
		}

		if (StringUtils.isNotBlank(comm_name_like)) {
			entity.getMap().put("comm_name_like", comm_name_like);
		}
		if (StringUtils.isNotBlank(st_date)) {
			entity.getMap().put("st_pay_date", st_date);
		}
		if (StringUtils.isNotBlank(en_date)) {
			entity.getMap().put("en_pay_date", en_date);
		}
		// 商家
		if (null != userInfo.getOwn_entp_id()) {
			entity.getMap().put("own_entp_id", userInfo.getOwn_entp_id());

		}
		// 合伙人
		else {
			String entp_id_in = "";
			if (userInfo.getIs_fuwu().intValue() == 1) {
				request.setAttribute("is_fuwu", userInfo.getIs_fuwu().intValue());
				ServiceCenterInfo serviceCenterInfo = super.getUserLinkServiceInfo(userInfo.getId());
				List<EntpInfo> entpInfoList = super.getEntpInfoListByServiceId(serviceCenterInfo.getId());
				if (null != entpInfoList && entpInfoList.size() > 0) {
					String[] entp_ids = new String[entpInfoList.size()];
					for (int i = 0; i < entpInfoList.size(); i++) {
						entp_ids[i] = entpInfoList.get(i).getId().toString();
					}
					entp_id_in = StringUtils.join(entp_ids, ",");
				}
				request.setAttribute("entpInfoList", entpInfoList);
			}
			// 合伙人查询下面的单个店铺
			if (StringUtils.isNotBlank(own_entp_id)) {
				entity.setEntp_id(Integer.valueOf(own_entp_id));
			} else {
				entity.getMap().put("entp_id_in", entp_id_in);
			}
		}
		Integer recordCount = getFacade().getOrderInfoDetailsService().getOrderInfoDetailsLinkOrderInfoCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<OrderInfoDetails> entityList = super.getFacade().getOrderInfoDetailsService()
				.getOrderInfoDetailsLinkOrderInfoPaginatedList(entity);

		request.setAttribute("orderStateList", Keys.OrderState.values());
		request.setAttribute("entityList", entityList);

		dynaBean.set("st_date", st_date);
		dynaBean.set("en_date", en_date);

		return mapping.findForward("list");
	}

	public ActionForward toExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(Keys.SESSION_USERINFO_KEY);
		Map<String, Object> model = new HashMap<String, Object>();
		if (null == userInfo) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}

		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);

		// 商品列表
		String orderDay = (String) dynaBean.get("orderDay");
		String comm_name_like = (String) dynaBean.get("comm_name_like");
		String st_date = (String) dynaBean.get("st_date");
		String en_date = (String) dynaBean.get("en_date");
		String own_entp_id = (String) dynaBean.get("own_entp_id");
		String code = (String) dynaBean.get("code");
		if (StringUtils.isNotBlank(orderDay)) {
			if (orderDay.equals("1")) {// 查询上月
				st_date = sdFormat_ymd.format(DateTools.getFirstDayOfLastMonday(new Date()));
				en_date = sdFormat_ymd.format(DateTools.getLastDayOfLastMonday(new Date()));
			} else if (orderDay.equals("2")) {// 查询上周
				int today = DateTools.getWeekOfDateIndex(new Date());
				st_date = DateTools.getLastDay(today + 6);
				en_date = DateTools.getLastDay(today);
			} else if (orderDay.equals("3")) {// 查询昨日
				st_date = DateTools.getLastDay(1);
				en_date = DateTools.getLastDay(1);
			}
		} else {
			st_date = "";
			en_date = "";
		}

		OrderInfoDetails entity = new OrderInfoDetails();
		super.copyProperties(entity, form);

		if (StringUtils.isNotBlank(comm_name_like)) {
			entity.getMap().put("comm_name_like", comm_name_like);
		}
		if (StringUtils.isNotBlank(st_date)) {
			entity.getMap().put("st_pay_date", st_date);
		}
		if (StringUtils.isNotBlank(en_date)) {
			entity.getMap().put("en_pay_date", en_date);
		}
		// 商家
		if (null != userInfo.getOwn_entp_id()) {
			entity.getMap().put("own_entp_id", userInfo.getOwn_entp_id());

		}
		// 合伙人
		else {
			String entp_id_in = "";
			if (userInfo.getIs_fuwu().intValue() == 1) {
				model.put("is_fuwu", userInfo.getIs_fuwu().intValue());
				ServiceCenterInfo serviceCenterInfo = super.getUserLinkServiceInfo(userInfo.getId());
				List<EntpInfo> entpInfoList = super.getEntpInfoListByServiceId(serviceCenterInfo.getId());
				if (null != entpInfoList && entpInfoList.size() > 0) {
					String[] entp_ids = new String[entpInfoList.size()];
					for (int i = 0; i < entpInfoList.size(); i++) {
						entp_ids[i] = entpInfoList.get(i).getId().toString();
					}
					entp_id_in = StringUtils.join(entp_ids, ",");
				}
				request.setAttribute("entpInfoList", entpInfoList);
			}
			// 合伙人查询下面的单个店铺
			if (StringUtils.isNotBlank(own_entp_id)) {
				entity.setEntp_id(Integer.valueOf(own_entp_id));
			} else {
				entity.getMap().put("entp_id_in", entp_id_in);
			}

			List<OrderInfoDetails> entityList = super.getFacade().getOrderInfoDetailsService()
					.getOrderInfoDetailsLinkOrderInfoPaginatedList(entity);

			model.put("entityList", entityList);

			String content = getFacade().getTemplateService().getContent("StatisticsOrderInfoDetails/list.ftl", model);
			// 下载文件出现乱码时，请参见此处
			String fname = EncryptUtilsV2.encodingFileName("销售明细.xls");
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
		}
		return null;
	}

}