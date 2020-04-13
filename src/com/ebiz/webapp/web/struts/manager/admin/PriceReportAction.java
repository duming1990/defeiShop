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

import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.web.util.DateTools;
import com.ebiz.webapp.web.util.EncryptUtilsV2;

public class PriceReportAction extends BaseAdminAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		String p_index = (String) dynaBean.get("p_index");
		String province = (String) dynaBean.get("province");
		String city = (String) dynaBean.get("city");
		String country = (String) dynaBean.get("country");
		String st_add_date_fmt = (String) dynaBean.get("st_add_date_fmt");
		String en_add_date_fmt = (String) dynaBean.get("en_add_date_fmt");

		OrderInfo orderInfoPriceSum = new OrderInfo();
		if (StringUtils.isNotBlank(p_index)) {
			if (p_index.substring(p_index.length() - 4, p_index.length()).equals("0000")) {
				orderInfoPriceSum.getMap().put("p_index_city", true);
				orderInfoPriceSum.getMap().put("p_level", 2);
				orderInfoPriceSum.getMap().put("par_index", p_index);
			} else if (p_index.substring(p_index.length() - 2, p_index.length()).equals("00")) {
				orderInfoPriceSum.getMap().put("p_index_country", true);
				orderInfoPriceSum.getMap().put("p_level", 3);
				orderInfoPriceSum.getMap().put("par_index", p_index);
			} else {
				orderInfoPriceSum.getMap().put("p_index_country", true);
				orderInfoPriceSum.getMap().put("p_level", 3);
				orderInfoPriceSum.getMap().put("par_index", p_index.substring(0, 4) + "00");
			}
		}

		if (StringUtils.isNotBlank(country)) {
			orderInfoPriceSum.getMap().put("p_index_country", true);
			orderInfoPriceSum.getMap().put("p_level", 3);
			orderInfoPriceSum.getMap().put("par_index", country.substring(0, 4) + "00");
		} else if (StringUtils.isNotBlank(city)) {
			orderInfoPriceSum.getMap().put("p_index_country", true);
			orderInfoPriceSum.getMap().put("p_level", 3);
			orderInfoPriceSum.getMap().put("par_index", city);
		} else if (StringUtils.isNotBlank(province)) {
			orderInfoPriceSum.getMap().put("p_index_city", true);
			orderInfoPriceSum.getMap().put("p_level", 2);
			orderInfoPriceSum.getMap().put("par_index", province);
		} else {
			if (StringUtils.isBlank(p_index)) {
				orderInfoPriceSum.getMap().put("p_index_province", true);
				orderInfoPriceSum.getMap().put("p_level", 1);
			}
		}

		if (StringUtils.isNotBlank(st_add_date_fmt)) {
			orderInfoPriceSum.getMap().put("st_add_date_fmt", st_add_date_fmt + ":00");
		}
		if (StringUtils.isNotBlank(en_add_date_fmt)) {
			orderInfoPriceSum.getMap().put("en_add_date_fmt", en_add_date_fmt + ":59");
		}
		List<OrderInfo> entityList = super.getFacade().getOrderInfoService().getRangPriceSum(orderInfoPriceSum);

		request.setAttribute("entityList", entityList);

		return mapping.findForward("list");
	}

	public ActionForward villageUserInvitReport(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		String p_index = (String) dynaBean.get("p_index");
		String province = (String) dynaBean.get("province");
		String city = (String) dynaBean.get("city");
		String country = (String) dynaBean.get("country");
		String town = (String) dynaBean.get("town");
		String village = (String) dynaBean.get("village");

		String st_date = (String) dynaBean.get("st_date");
		String en_date = (String) dynaBean.get("en_date");

		OrderInfo orderInfoPriceSum = new OrderInfo();
		if (StringUtils.isNotBlank(p_index)) {
			if (p_index.length() == 6) {
				// 省
				if (p_index.substring(p_index.length() - 4, p_index.length()).equals("0000")) {
					orderInfoPriceSum.getMap().put("p_index_city", true);
					orderInfoPriceSum.getMap().put("p_level", 2);
					orderInfoPriceSum.getMap().put("group_by", "b.P_INDEX");
					orderInfoPriceSum.getMap().put("p_index_like", p_index.substring(0, 2));
				} else if (p_index.substring(p_index.length() - 2, p_index.length()).equals("00")) {
					// 市
					orderInfoPriceSum.getMap().put("p_index_country", true);
					orderInfoPriceSum.getMap().put("p_level", 3);
					orderInfoPriceSum.getMap().put("group_by", "b.P_INDEX");
					orderInfoPriceSum.getMap().put("p_index_like", p_index.substring(0, 4));
				} else {
					// 县
					orderInfoPriceSum.getMap().put("p_index_town", true);
					orderInfoPriceSum.getMap().put("p_level", 4);
					orderInfoPriceSum.getMap().put("group_by", "b.P_INDEX");
					orderInfoPriceSum.getMap().put("p_index_like", p_index.substring(0, 6));
				}
			} else {
				if (p_index.substring(p_index.length() - 3, p_index.length()).equals("000")) {
					// 镇
					orderInfoPriceSum.getMap().put("p_index_village", true);
					orderInfoPriceSum.getMap().put("p_level", 5);
					orderInfoPriceSum.getMap().put("group_by", "b.P_INDEX");
					orderInfoPriceSum.getMap().put("p_index_like", p_index.substring(0, 9));
					request.setAttribute("show_village", true);
				} else {
					// 村
					orderInfoPriceSum.getMap().put("p_index_village", true);
					orderInfoPriceSum.getMap().put("p_level", 5);
					orderInfoPriceSum.getMap().put("group_by", "b.P_INDEX");
					orderInfoPriceSum.getMap().put("p_index_like", p_index);
					request.setAttribute("show_village", true);
				}
			}
		}
		if (StringUtils.isNotBlank(village)) {
			orderInfoPriceSum.getMap().put("p_index_village", true);
			orderInfoPriceSum.getMap().put("p_level", 5);
			orderInfoPriceSum.getMap().put("group_by", "b.P_INDEX");
			orderInfoPriceSum.getMap().put("p_index_like", village);
			request.setAttribute("show_village", true);
		} else if (StringUtils.isNotBlank(town)) {
			orderInfoPriceSum.getMap().put("p_index_village", true);
			orderInfoPriceSum.getMap().put("p_level", 5);
			orderInfoPriceSum.getMap().put("group_by", "b.P_INDEX");
			orderInfoPriceSum.getMap().put("p_index_like", town.substring(0, 9));
		} else if (StringUtils.isNotBlank(country)) {
			orderInfoPriceSum.getMap().put("p_index_town", true);
			orderInfoPriceSum.getMap().put("p_level", 4);
			orderInfoPriceSum.getMap().put("group_by", "b.P_INDEX");
			orderInfoPriceSum.getMap().put("p_index_like", country.substring(0, 6));
		} else if (StringUtils.isNotBlank(city)) {
			orderInfoPriceSum.getMap().put("p_index_country", true);
			orderInfoPriceSum.getMap().put("p_level", 3);
			orderInfoPriceSum.getMap().put("group_by", "b.P_INDEX");
			orderInfoPriceSum.getMap().put("p_index_like", city.substring(0, 4));
		} else if (StringUtils.isNotBlank(province)) {
			orderInfoPriceSum.getMap().put("p_index_city", true);
			orderInfoPriceSum.getMap().put("p_level", 2);
			orderInfoPriceSum.getMap().put("group_by", "b.P_INDEX");
			orderInfoPriceSum.getMap().put("p_index_like", province.substring(0, 2));
		} else {
			if (StringUtils.isBlank(p_index)) {
				orderInfoPriceSum.getMap().put("p_index_province", true);
				orderInfoPriceSum.getMap().put("p_level", 1);
				orderInfoPriceSum.getMap().put("group_by", "SUBSTR(a.P_INDEX, 1, 2)");
			}
		}

		if (StringUtils.isBlank(st_date) && StringUtils.isBlank(en_date)) {
			st_date = DateTools.getFirstDayThisMonth(new Date()) + " 00:00";
			en_date = DateTools.getStringDate(new Date(), "yyyy-MM-dd HH:mm");
			dynaBean.set("st_date", st_date);
			dynaBean.set("en_date", en_date);
			orderInfoPriceSum.getMap().put("st_date", st_date + ":00");
			orderInfoPriceSum.getMap().put("en_date", en_date + ":59");
		} else if (StringUtils.isNotBlank(st_date)) {
			orderInfoPriceSum.getMap().put("st_date", st_date + ":00");
		} else if (StringUtils.isNotBlank(en_date)) {
			orderInfoPriceSum.getMap().put("en_date", en_date + ":59");
		}
		List<OrderInfo> dyPriceList = super.getFacade().getOrderInfoService()
				.getVillageInviteUserSum(orderInfoPriceSum);
		request.setAttribute("list", dyPriceList);

		return new ActionForward("/../manager/admin/PriceReport/invit_list.jsp");
	}

	public ActionForward toExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		Map<String, Object> model = new HashMap<String, Object>();

		String code = (String) dynaBean.get("code");
		String p_index = (String) dynaBean.get("p_index");
		String province = (String) dynaBean.get("province");
		String city = (String) dynaBean.get("city");
		String country = (String) dynaBean.get("country");
		String st_add_date_fmt = (String) dynaBean.get("st_add_date_fmt");
		String en_add_date_fmt = (String) dynaBean.get("en_add_date_fmt");

		String user_num = (String) dynaBean.get("user_num");
		String price_money_1002 = (String) dynaBean.get("price_money_1002");
		String price_money_1003 = (String) dynaBean.get("price_money_1003");
		String price_money_1004 = (String) dynaBean.get("price_money_1004");
		String price_money_1005 = (String) dynaBean.get("price_money_1005");

		OrderInfo orderInfoPriceSum = new OrderInfo();
		if (StringUtils.isNotBlank(p_index)) {
			if (p_index.substring(p_index.length() - 4, p_index.length()).equals("0000")) {
				orderInfoPriceSum.getMap().put("p_index_city", true);
				orderInfoPriceSum.getMap().put("p_level", 2);
				orderInfoPriceSum.getMap().put("par_index", p_index);
			} else if (p_index.substring(p_index.length() - 2, p_index.length()).equals("00")) {
				orderInfoPriceSum.getMap().put("p_index_country", true);
				orderInfoPriceSum.getMap().put("p_level", 3);
				orderInfoPriceSum.getMap().put("par_index", p_index);
			} else {
				orderInfoPriceSum.getMap().put("p_index_country", true);
				orderInfoPriceSum.getMap().put("p_level", 3);
				orderInfoPriceSum.getMap().put("par_index", p_index.substring(0, 4) + "00");
			}
		}

		if (StringUtils.isNotBlank(country)) {
			orderInfoPriceSum.getMap().put("p_index_country", true);
			orderInfoPriceSum.getMap().put("p_level", 3);
			orderInfoPriceSum.getMap().put("par_index", country.substring(0, 4) + "00");
		} else if (StringUtils.isNotBlank(city)) {
			orderInfoPriceSum.getMap().put("p_index_country", true);
			orderInfoPriceSum.getMap().put("p_level", 3);
			orderInfoPriceSum.getMap().put("par_index", city);
		} else if (StringUtils.isNotBlank(province)) {
			orderInfoPriceSum.getMap().put("p_index_city", true);
			orderInfoPriceSum.getMap().put("p_level", 2);
			orderInfoPriceSum.getMap().put("par_index", province);
		} else {
			if (StringUtils.isBlank(p_index)) {
				orderInfoPriceSum.getMap().put("p_index_province", true);
				orderInfoPriceSum.getMap().put("p_level", 1);
			}
		}

		if (StringUtils.isNotBlank(st_add_date_fmt)) {
			orderInfoPriceSum.getMap().put("st_add_date_fmt", st_add_date_fmt + ":00");
		}
		if (StringUtils.isNotBlank(en_add_date_fmt)) {
			orderInfoPriceSum.getMap().put("en_add_date_fmt", en_add_date_fmt + ":59");
		}
		List<OrderInfo> entityList = super.getFacade().getOrderInfoService().getRangPriceSum(orderInfoPriceSum);

		model.put("entityList", entityList);
		model.put("title", "奖励报表导出_日期" + sdFormat_ymd.format(new Date()));
		model.put("user_num", user_num);
		model.put("price_money_1002", price_money_1002);
		model.put("price_money_1003", price_money_1003);
		model.put("price_money_1004", price_money_1004);
		model.put("price_money_1005", price_money_1005);
		String content = getFacade().getTemplateService().getContent("PriceReport/List.ftl", model);
		String fname = EncryptUtilsV2.encodingFileName("奖励报表导出_日期" + sdFormat_ymd.format(new Date()) + ".xls");

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

	public ActionForward toInvitExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		Map<String, Object> model = new HashMap<String, Object>();

		String code = (String) dynaBean.get("code");
		String p_index = (String) dynaBean.get("p_index");
		String province = (String) dynaBean.get("province");
		String city = (String) dynaBean.get("city");
		String country = (String) dynaBean.get("country");
		String town = (String) dynaBean.get("town");
		String village = (String) dynaBean.get("village");

		String st_date = (String) dynaBean.get("st_date");
		String en_date = (String) dynaBean.get("en_date");
		String user_num = (String) dynaBean.get("user_num");
		OrderInfo orderInfoPriceSum = new OrderInfo();
		if (StringUtils.isNotBlank(p_index)) {
			if (p_index.length() == 6) {
				// 省
				if (p_index.substring(p_index.length() - 4, p_index.length()).equals("0000")) {
					orderInfoPriceSum.getMap().put("p_index_city", true);
					orderInfoPriceSum.getMap().put("p_level", 2);
					orderInfoPriceSum.getMap().put("group_by", "b.P_INDEX");
					orderInfoPriceSum.getMap().put("p_index_like", p_index.substring(0, 2));
				} else if (p_index.substring(p_index.length() - 2, p_index.length()).equals("00")) {
					// 市
					orderInfoPriceSum.getMap().put("p_index_country", true);
					orderInfoPriceSum.getMap().put("p_level", 3);
					orderInfoPriceSum.getMap().put("group_by", "b.P_INDEX");
					orderInfoPriceSum.getMap().put("p_index_like", p_index.substring(0, 4));
				} else {
					// 县
					orderInfoPriceSum.getMap().put("p_index_town", true);
					orderInfoPriceSum.getMap().put("p_level", 4);
					orderInfoPriceSum.getMap().put("group_by", "b.P_INDEX");
					orderInfoPriceSum.getMap().put("p_index_like", p_index.substring(0, 6));
				}
			} else {
				if (p_index.substring(p_index.length() - 3, p_index.length()).equals("000")) {
					// 镇
					orderInfoPriceSum.getMap().put("p_index_village", true);
					orderInfoPriceSum.getMap().put("p_level", 5);
					orderInfoPriceSum.getMap().put("group_by", "b.P_INDEX");
					orderInfoPriceSum.getMap().put("p_index_like", p_index.substring(0, 9));
				} else {
					// 村
					orderInfoPriceSum.getMap().put("p_index_village", true);
					orderInfoPriceSum.getMap().put("p_level", 5);
					orderInfoPriceSum.getMap().put("group_by", "b.P_INDEX");
					orderInfoPriceSum.getMap().put("p_index_like", p_index);
				}
			}
		}
		if (StringUtils.isNotBlank(village)) {
			orderInfoPriceSum.getMap().put("p_index_village", true);
			orderInfoPriceSum.getMap().put("p_level", 5);
			orderInfoPriceSum.getMap().put("group_by", "b.P_INDEX");
			orderInfoPriceSum.getMap().put("p_index_like", village);
		} else if (StringUtils.isNotBlank(town)) {
			orderInfoPriceSum.getMap().put("p_index_village", true);
			orderInfoPriceSum.getMap().put("p_level", 5);
			orderInfoPriceSum.getMap().put("group_by", "b.P_INDEX");
			orderInfoPriceSum.getMap().put("p_index_like", town.substring(0, 9));
		} else if (StringUtils.isNotBlank(country)) {
			orderInfoPriceSum.getMap().put("p_index_town", true);
			orderInfoPriceSum.getMap().put("p_level", 4);
			orderInfoPriceSum.getMap().put("group_by", "b.P_INDEX");
			orderInfoPriceSum.getMap().put("p_index_like", country.substring(0, 6));
		} else if (StringUtils.isNotBlank(city)) {
			orderInfoPriceSum.getMap().put("p_index_country", true);
			orderInfoPriceSum.getMap().put("p_level", 3);
			orderInfoPriceSum.getMap().put("group_by", "b.P_INDEX");
			orderInfoPriceSum.getMap().put("p_index_like", city.substring(0, 4));
		} else if (StringUtils.isNotBlank(province)) {
			orderInfoPriceSum.getMap().put("p_index_city", true);
			orderInfoPriceSum.getMap().put("p_level", 2);
			orderInfoPriceSum.getMap().put("group_by", "b.P_INDEX");
			orderInfoPriceSum.getMap().put("p_index_like", province.substring(0, 2));
		} else {
			if (StringUtils.isBlank(p_index)) {
				orderInfoPriceSum.getMap().put("p_index_province", true);
				orderInfoPriceSum.getMap().put("p_level", 1);
				orderInfoPriceSum.getMap().put("group_by", "SUBSTR(a.P_INDEX, 1, 2)");
			}
		}

		if (StringUtils.isBlank(st_date) && StringUtils.isBlank(en_date)) {
			st_date = DateTools.getFirstDayThisMonth(new Date()) + " 00:00";
			en_date = DateTools.getStringDate(new Date(), "yyyy-MM-dd HH:mm");
			dynaBean.set("st_date", st_date);
			dynaBean.set("en_date", en_date);
			orderInfoPriceSum.getMap().put("st_date", st_date + ":00");
			orderInfoPriceSum.getMap().put("en_date", en_date + ":59");
		} else if (StringUtils.isNotBlank(st_date)) {
			orderInfoPriceSum.getMap().put("st_date", st_date + ":00");
		} else if (StringUtils.isNotBlank(en_date)) {
			orderInfoPriceSum.getMap().put("en_date", en_date + ":59");
		}
		List<OrderInfo> dyPriceList = super.getFacade().getOrderInfoService()
				.getVillageInviteUserSum(orderInfoPriceSum);
		model.put("entityList", dyPriceList);
		model.put("title", "会员邀请报表导出_日期" + sdFormat_ymd.format(new Date()));
		model.put("user_num", user_num);
		String content = getFacade().getTemplateService().getContent("PriceReport/List_invit.ftl", model);
		String fname = EncryptUtilsV2.encodingFileName("会员邀请报表导出_日期" + sdFormat_ymd.format(new Date()) + ".xls");

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
