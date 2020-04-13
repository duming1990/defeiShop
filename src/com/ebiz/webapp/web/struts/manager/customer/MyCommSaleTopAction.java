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
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.OrderInfoDetails;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.DateTools;
import com.ebiz.webapp.web.util.EncryptUtilsV2;

/**
 * @author LIUJIA
 * @version 2016-07-29
 */

public class MyCommSaleTopAction extends BaseCustomerAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		UserInfo userInfo = super.getUserInfoFromSession(request);
		getsonSysModuleList(request, dynaBean);

		Pager pager = (Pager) dynaBean.get("pager");
		String comm_name_like = (String) dynaBean.get("comm_name_like");
		String orderDay = (String) dynaBean.get("orderDay");
		String own_entp_id = (String) dynaBean.get("own_entp_id");

		OrderInfoDetails entity = new OrderInfoDetails();

		if (StringUtils.isNotBlank(comm_name_like)) {
			entity.getMap().put("comm_name_like", comm_name_like);
		}

		Integer entp_id = userInfo.getOwn_entp_id();
		String entp_id_in = "";

		if (userInfo.getIs_fuwu().intValue() == 1) {
			if (StringUtils.isBlank(own_entp_id)) {// 如果為空 需要查詢合伙人所有店铺排名
				entp_id = null;

				List<EntpInfo> entpInfoList = super.getEntpInfoListByServiceId(entity.getId());
				if (null != entpInfoList && entpInfoList.size() > 0) {
					String[] entp_ids = new String[entpInfoList.size()];
					for (int i = 0; i < entpInfoList.size(); i++) {
						entp_ids[i] = entpInfoList.get(i).getId().toString();
					}
					entp_id_in = StringUtils.join(entp_ids, ",");
				}

			} else {
				entp_id = Integer.valueOf(own_entp_id);
			}
		}

		entity.setEntp_id(entp_id);
		entity.getMap().put("entp_id_in", entp_id_in);
		entity.getMap().put("orderBySaleDesc", true);
		entity.getMap().put("is_test_eq", 0);
		String order_type_in = Keys.OrderType.ORDER_TYPE_10.getIndex() + "," + Keys.OrderType.ORDER_TYPE_100.getIndex();
		entity.getMap().put("order_type_in", order_type_in);
		if (StringUtils.isNotBlank(orderDay)) {
			if (orderDay.equals("1")) {// 查询本月
				entity.getMap().put("st_add_date", DateTools.getFirstDayThisMonth());
				entity.getMap().put("en_add_date", DateTools.getLastDayThisMonth());
			} else if (orderDay.equals("2")) {// 查询昨日排名
				entity.getMap().put("st_add_date", DateTools.getLastDay(1));
				entity.getMap().put("en_add_date", DateTools.getLastDay(1));
			} else if (orderDay.equals("3")) {// 查询今日排名
				entity.getMap().put("st_add_date", sdFormat_ymd.format(new Date()));
				entity.getMap().put("en_add_date", sdFormat_ymd.format(new Date()));
			}
		}

		Integer recordCount = getFacade().getOrderInfoDetailsService().getOrderInfoDetailsEntpCommSaleCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<OrderInfoDetails> entityList = super.getFacade().getOrderInfoDetailsService()
				.getOrderInfoDetailsEntpCommSalePaginatedList(entity);
		if (null != entityList && entityList.size() > 0) {
			for (OrderInfoDetails temp : entityList) {
				temp.getMap().put("commInfo", super.getCommInfoOnlyById(temp.getComm_id()));
			}
		}
		request.setAttribute("entityList", entityList);

		return mapping.findForward("list");
	}

	public ActionForward toExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(Keys.SESSION_USERINFO_KEY);

		Map<String, Object> model = new HashMap<String, Object>();
		DynaBean dynaBean = (DynaBean) form;

		String comm_name_like = (String) dynaBean.get("comm_name_like");
		String orderDay = (String) dynaBean.get("orderDay");
		String code = (String) dynaBean.get("code");

		OrderInfoDetails entity = new OrderInfoDetails();

		if (StringUtils.isNotBlank(comm_name_like)) {
			entity.getMap().put("comm_name_like", comm_name_like);
		}

		entity.setEntp_id(userInfo.getOwn_entp_id());
		entity.getMap().put("orderBySaleDesc", true);
		entity.getMap().put("orderBySaleDesc", true);
		String order_type_in = Keys.OrderType.ORDER_TYPE_10.getIndex() + "," + Keys.OrderType.ORDER_TYPE_100.getIndex();
		entity.getMap().put("order_type_in", order_type_in);
		if (StringUtils.isNotBlank(orderDay)) {
			if (orderDay.equals("1")) {// 查询本月
				entity.getMap().put("st_add_date", DateTools.getFirstDayThisMonth());
				entity.getMap().put("en_add_date", DateTools.getLastDayThisMonth());
			} else if (orderDay.equals("2")) {// 查询昨日排名
				entity.getMap().put("st_add_date", DateTools.getLastDay(1));
				entity.getMap().put("en_add_date", DateTools.getLastDay(1));
			} else if (orderDay.equals("3")) {// 查询今日排名
				entity.getMap().put("st_add_date", sdFormat_ymd.format(new Date()));
				entity.getMap().put("en_add_date", sdFormat_ymd.format(new Date()));
			}
		}

		List<OrderInfoDetails> entityList = super.getFacade().getOrderInfoDetailsService()
				.getOrderInfoDetailsEntpCommSalePaginatedList(entity);
		if (null != entityList && entityList.size() > 0) {
			for (OrderInfoDetails temp : entityList) {
				// 查询利润率 以及利润
				CommInfo commInfo = super.getCommInfoOnlyById(temp.getComm_id());
				temp.getMap().put("commInfo", commInfo);
			}
		}
		model.put("entityList", entityList);
		model.put("title", "商品排名" + sdFormat_ymd.format(new Date()));

		String content = getFacade().getTemplateService().getContent("MyCommSaleTop/list.ftl", model);
		// 下载文件出现乱码时，请参见此处
		String fname = EncryptUtilsV2.encodingFileName("商品排名" + sdFormat_ymd.format(new Date()) + ".xls");

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
}