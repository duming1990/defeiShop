package com.ebiz.webapp.web.struts.manager.admin;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.UserInfo;

public class SpokesmanRankingAction extends BaseAdminAction {
	@Override
	protected ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;

		Pager pager = (Pager) dynaBean.get("pager");

		String user_num = (String) dynaBean.get("user_num");
		String sum_money = (String) dynaBean.get("sum_money");
		String province = (String) dynaBean.get("province");
		String city = (String) dynaBean.get("city");
		String country = (String) dynaBean.get("country");

		UserInfo entity = new UserInfo();
		if (StringUtils.isNotBlank(country)) {
			entity.getMap().put("p_index_like", country);
		} else if (StringUtils.isNotBlank(city)) {
			entity.getMap().put("p_index_like", city.substring(0, 4));
		} else if (StringUtils.isNotBlank(province)) {
			entity.getMap().put("p_index_like", province.substring(0, 2));
		}
		if ("1".equals(user_num)) {
			entity.getMap().put("user_num", new BigDecimal(0));
		}
		if ("1".equals(sum_money)) {
			entity.getMap().put("sum_money", new BigDecimal(0));
		}

		Integer recordCount = getFacade().getUserInfoService().getSpokesmanRankingListCount(entity);

		pager.init(recordCount.longValue(), Integer.valueOf("5"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());
		List<UserInfo> entityList = getFacade().getUserInfoService().getSpokesmanRankingList(entity);
		request.setAttribute("entityList", entityList);

		return mapping.findForward("list");
	}
}
