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
import com.ebiz.webapp.domain.PoorInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.VillageInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.EncryptUtilsV2;

public class PoorMoneyReportAction extends BaseAdminAction {
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

		PoorInfo orderInfoPriceSum = new PoorInfo();
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

		List<PoorInfo> entityList = super.getFacade().getPoorInfoService().getPoorMoneyReport(orderInfoPriceSum);

		request.setAttribute("entityList", entityList);

		return mapping.findForward("list");
	}

	public ActionForward poorList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "1")) {
			return super.checkPopedomInvalid(request, response);
		}
		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response,
					"window.onload=function(){alert('" + msg + "');location.href='login.shtml'}");
			return null;
		}

		Pager pager = (Pager) dynaBean.get("pager");
		String real_name_like = (String) dynaBean.get("real_name_like");
		String user_name_like = (String) dynaBean.get("user_name_like");
		String st_add_date = (String) dynaBean.get("st_add_date");
		String en_add_date = (String) dynaBean.get("en_add_date");
		String province = (String) dynaBean.get("province");
		String city = (String) dynaBean.get("city");
		String country = (String) dynaBean.get("country");
		String town = (String) dynaBean.get("town");
		String village = (String) dynaBean.get("village");
		String id_card_like = (String) dynaBean.get("id_card_like");
		String is_band_bank = (String) dynaBean.get("is_band_bank");
		String bi_aid_ge = (String) dynaBean.get("bi_aid_ge");

		PoorInfo entity = new PoorInfo();
		super.copyProperties(entity, form);

		if (StringUtils.isNotBlank(id_card_like)) {
			entity.getMap().put("id_card_like", id_card_like.toUpperCase().trim());
		}
		if (StringUtils.isNotBlank(village)) {
			entity.getMap().put("p_index_like", village);
		} else if (StringUtils.isNotBlank(town)) {
			entity.getMap().put("p_index_like", town.substring(0, 9));
		} else if (StringUtils.isNotBlank(country)) {
			entity.getMap().put("p_index_like", country.substring(0, 6));
		} else if (StringUtils.isNotBlank(city)) {
			entity.getMap().put("p_index_like", city.substring(0, 4));
		} else if (StringUtils.isNotBlank(province)) {
			entity.getMap().put("p_index_like", province.substring(0, 2));
		}
		entity.setIs_del(0);
		entity.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		// entity.setReport_step(4);

		entity.getMap().put("real_name_like", real_name_like);
		entity.getMap().put("user_name_like", user_name_like);
		entity.getMap().put("order_by", " c.bi_aid desc, a.p_index asc");
		entity.getMap().put("bi_aid_gt", bi_aid_ge);
		if (StringUtils.isNotBlank(is_band_bank)) {
			if (is_band_bank.equals("0")) {
				entity.getMap().put("is_band_bank_is_null", "true");
			} else {
				entity.getMap().put("is_band_bank_not_null", "true");
			}
		}

		entity.getMap().put("st_add_date", st_add_date);
		entity.getMap().put("en_add_date", en_add_date);

		Integer recordCount = getFacade().getPoorInfoService().getPoorInfoCount(entity);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<PoorInfo> list = getFacade().getPoorInfoService().getPoorInfoPaginatedList(entity);
		if (list != null && list.size() > 0) {
			for (PoorInfo temp : list) {
				if (null != temp.getVillage_id()) {
					VillageInfo villageInfo = new VillageInfo();
					villageInfo.setId(temp.getVillage_id());
					villageInfo = super.getFacade().getVillageInfoService().getVillageInfo(villageInfo);
					temp.getMap().put("villageInfo", villageInfo);
				}
				// 获取用户的银行信息，性能比较低，后期进行优化
				// TODO 性能比较低，后期进行优化
				UserInfo user = new UserInfo();
				user.setIs_poor(1);
				user.setPoor_id(temp.getId());
				user.setIs_del(0);
				user = getFacade().getUserInfoService().getUserInfo(user);
				temp.getMap().put("userInfo", user);

			}
		}
		;
		request.setAttribute("limit_money", super.getBaseData(Keys.fp_money_limit_id).getPre_number());
		request.setAttribute("entityList", list);
		return new ActionForward("/../manager/admin/PoorMoneyReport/poor_list.jsp");
	}

	public ActionForward toPoorListExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		Map<String, Object> model = new HashMap<String, Object>();

		String real_name_like = (String) dynaBean.get("real_name_like");
		String user_name_like = (String) dynaBean.get("user_name_like");
		String st_add_date = (String) dynaBean.get("st_add_date");
		String en_add_date = (String) dynaBean.get("en_add_date");
		String province = (String) dynaBean.get("province");
		String city = (String) dynaBean.get("city");
		String country = (String) dynaBean.get("country");
		String town = (String) dynaBean.get("town");
		String village = (String) dynaBean.get("village");
		String code = (String) dynaBean.get("code");
		String id_card_like = (String) dynaBean.get("id_card_like");
		String bi_aid_ge = (String) dynaBean.get("bi_aid_ge");

		PoorInfo entity = new PoorInfo();
		super.copyProperties(entity, form);

		if (StringUtils.isNotBlank(village)) {
			entity.getMap().put("p_index_like", village);
		} else if (StringUtils.isNotBlank(town)) {
			entity.getMap().put("p_index_like", town.substring(0, 9));
		} else if (StringUtils.isNotBlank(country)) {
			entity.getMap().put("p_index_like", country.substring(0, 6));
		} else if (StringUtils.isNotBlank(city)) {
			entity.getMap().put("p_index_like", city.substring(0, 4));
		} else if (StringUtils.isNotBlank(province)) {
			entity.getMap().put("p_index_like", province.substring(0, 2));
		}
		entity.setIs_del(0);
		entity.setAudit_state(Keys.audit_state.audit_state_1.getIndex());

		entity.getMap().put("real_name_like", real_name_like);
		entity.getMap().put("user_name_like", user_name_like);
		entity.getMap().put("order_by", " c.bi_aid desc, a.p_index asc");
		entity.getMap().put("bi_aid_gt", bi_aid_ge);
		entity.getMap().put("st_add_date", st_add_date);
		entity.getMap().put("en_add_date", en_add_date);
		if (StringUtils.isNotBlank(id_card_like)) {
			entity.getMap().put("id_card_like", id_card_like.toUpperCase().trim());
		}

		List<PoorInfo> entityList = getFacade().getPoorInfoService().getPoorInfoPaginatedList(entity);
		if (null != entityList && entityList.size() > 0) {
			for (PoorInfo temp : entityList) {
				if (null != temp.getVillage_id()) {
					VillageInfo villageInfo = new VillageInfo();
					villageInfo.setId(temp.getVillage_id());
					villageInfo = super.getFacade().getVillageInfoService().getVillageInfo(villageInfo);
					temp.getMap().put("villageInfo", villageInfo);
				}
				UserInfo uInfo = new UserInfo();
				uInfo.setIs_poor(1);
				uInfo.setIs_del(0);
				uInfo.setPoor_id(temp.getId());
				uInfo = getFacade().getUserInfoService().getUserInfo(uInfo);
				temp.getMap().put("userInfo", uInfo);
			}
		}

		model.put("entityList", entityList);
		model.put("limit_money", super.getBaseData(Keys.fp_money_limit_id).getPre_number());
		String content = getFacade().getTemplateService().getContent("PoorMoneyReport/poorlist.ftl", model);
		// 下载文件出现乱码时，请参见此处
		String fname = EncryptUtilsV2.encodingFileName("贫困户信息.xls");
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

	public ActionForward toExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		Map<String, Object> model = new HashMap<String, Object>();

		String code = (String) dynaBean.get("code");
		String p_index = (String) dynaBean.get("p_index");
		String province = (String) dynaBean.get("province");
		String city = (String) dynaBean.get("city");
		String country = (String) dynaBean.get("country");

		String user_num = (String) dynaBean.get("user_num");
		String sum_bi_aid = (String) dynaBean.get("sum_bi_aid");
		String sum_bi_aid_sended = (String) dynaBean.get("sum_bi_aid_sended");
		String sum_money = (String) dynaBean.get("sum_money");

		PoorInfo orderInfoPriceSum = new PoorInfo();
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

		List<PoorInfo> entityList = super.getFacade().getPoorInfoService().getPoorMoneyReport(orderInfoPriceSum);

		model.put("entityList", entityList);
		model.put("title", "奖励报表导出_日期" + sdFormat_ymd.format(new Date()));
		model.put("user_num", user_num);
		model.put("sum_bi_aid", sum_bi_aid);
		model.put("sum_bi_aid_sended", sum_bi_aid_sended);
		model.put("sum_money", sum_money);
		String content = getFacade().getTemplateService().getContent("PoorMoneyReport/list.ftl", model);
		String fname = EncryptUtilsV2.encodingFileName("扶贫金报表导出_日期" + sdFormat_ymd.format(new Date()) + ".xls");

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
