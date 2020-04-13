package com.ebiz.webapp.web.struts.manager.admin;

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
import com.ebiz.webapp.domain.OrderInfoDetails;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.DateTools;
import com.ebiz.webapp.web.util.EncryptUtilsV2;

/**
 * @author LIUJIA
 * @version 2016-07-29
 */

public class CommSaleTopAction extends BaseAdminAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;

		Pager pager = (Pager) dynaBean.get("pager");
		String comm_name_like = (String) dynaBean.get("comm_name_like");
		String orderDay = (String) dynaBean.get("orderDay");
		String own_entp_id = (String) dynaBean.get("own_entp_id");
		String saleCount = (String) dynaBean.get("saleCount");
		String orderType = (String) dynaBean.get("orderType");

		if (StringUtils.isBlank(saleCount)) {
			saleCount = "0";
		}
		if (saleCount.equals("0")) {// 证明查询销量不为0的
			OrderInfoDetails entity = new OrderInfoDetails();

			if (StringUtils.isNotBlank(comm_name_like)) {
				entity.getMap().put("comm_name_like", comm_name_like);
			}

			if (StringUtils.isNotBlank(own_entp_id)) {
				entity.setEntp_id(Integer.valueOf(own_entp_id));
			}

			entity.getMap().put("orderBySaleDesc", true);
			// String order_type_in = Keys.OrderType.ORDER_TYPE_10.getIndex() + "";
			// entity.getMap().put("order_type_in", order_type_in);
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
			if (StringUtils.isNotBlank(orderType)
					&& Integer.valueOf(orderType) == Keys.OrderType.ORDER_TYPE_90.getIndex()) {
				entity.getMap().put("orderType", Integer.valueOf(orderType));
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
		} else if (saleCount.equals("1")) {// 证明查询销量为0的商品
			CommInfo entity = new CommInfo();
			if (StringUtils.isNotBlank(comm_name_like)) {
				entity.getMap().put("comm_name_like", comm_name_like);
			}
			if (StringUtils.isNotBlank(own_entp_id)) {
				entity.setOwn_entp_id(Integer.valueOf(own_entp_id));
			}
			entity.setSale_count(0);
			entity.setIs_del(0);

			Integer recordCount = getFacade().getCommInfoService().getCommInfoCount(entity);
			pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
			entity.getRow().setFirst(pager.getFirstRow());
			entity.getRow().setCount(pager.getRowCount());

			List<CommInfo> entityList = super.getFacade().getCommInfoService().getCommInfoPaginatedList(entity);
			request.setAttribute("entityList", entityList);
		}
		dynaBean.set("saleCount", saleCount);
		return mapping.findForward("list");
	}

	public ActionForward toExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		DynaBean dynaBean = (DynaBean) form;

		String comm_name_like = (String) dynaBean.get("comm_name_like");
		String orderDay = (String) dynaBean.get("orderDay");
		String code = (String) dynaBean.get("code");
		String own_entp_id = (String) dynaBean.get("own_entp_id");
		String saleCount = (String) dynaBean.get("saleCount");

		if (StringUtils.isBlank(saleCount)) {
			saleCount = "0";
		}

		if (saleCount.equals("0")) {
			OrderInfoDetails entity = new OrderInfoDetails();

			if (StringUtils.isNotBlank(comm_name_like)) {
				entity.getMap().put("comm_name_like", comm_name_like);
			}

			if (StringUtils.isNotBlank(own_entp_id)) {
				entity.setEntp_id(Integer.valueOf(own_entp_id));
			}
			entity.getMap().put("orderBySaleDesc", true);
			// String order_type_in = Keys.OrderType.ORDER_TYPE_10.getIndex() + "";
			// entity.getMap().put("order_type_in", order_type_in);
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
					temp.getMap().put("commInfo", super.getCommInfoOnlyById(temp.getComm_id()));
				}
			}
			model.put("entityList", entityList);

			model.put("title", "商品排名" + sdFormat_ymd.format(new Date()));
			model.put("saleCount", Integer.valueOf(saleCount));

			String content = getFacade().getTemplateService().getContent("CommSaleTop/list.ftl", model);
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

		} else if (saleCount.equals("1")) {// 证明查询销量为0的商品
			CommInfo entity = new CommInfo();
			if (StringUtils.isNotBlank(comm_name_like)) {
				entity.getMap().put("comm_name_like", comm_name_like);
			}
			if (StringUtils.isNotBlank(own_entp_id)) {
				entity.setOwn_entp_id(Integer.valueOf(own_entp_id));
			}
			entity.setSale_count(0);
			entity.setIs_del(0);

			List<CommInfo> entityList = super.getFacade().getCommInfoService().getCommInfoList(entity);
			model.put("entityList", entityList);
			model.put("saleCount", Integer.valueOf(saleCount));
			model.put("title", "商品销售为零" + sdFormat_ymd.format(new Date()));

			String content = getFacade().getTemplateService().getContent("CommSaleTop/list.ftl", model);
			// 下载文件出现乱码时，请参见此处
			String fname = EncryptUtilsV2.encodingFileName("商品销售为零" + sdFormat_ymd.format(new Date()) + ".xls");

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
		return null;
	}
}