package com.ebiz.webapp.web.struts.manager.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.OrderInfoDetails;
import com.ebiz.webapp.web.Keys;

public class CommRankingAction extends BaseAdminAction {
	@Override
	protected ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;

		Pager pager = (Pager) dynaBean.get("pager");
		String province = (String) dynaBean.get("province");
		String city = (String) dynaBean.get("city");
		String country = (String) dynaBean.get("country");
		OrderInfoDetails entity = new OrderInfoDetails();
		if (StringUtils.isNotBlank(country)) {
			entity.getMap().put("p_index_like", country);
		} else if (StringUtils.isNotBlank(city)) {
			entity.getMap().put("p_index_like", city.substring(0, 4));
		} else if (StringUtils.isNotBlank(province)) {
			entity.getMap().put("p_index_like", province.substring(0, 2));
		}
		entity.getMap().put("order_state_ge", Keys.OrderState.ORDER_STATE_10.getIndex());
		entity.getMap().put("order_state_le", Keys.OrderState.ORDER_STATE_90.getIndex());

		Integer recordCount = getFacade().getOrderInfoDetailsService().getCommRankingCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("5"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());
		List<OrderInfoDetails> entityList = getFacade().getOrderInfoDetailsService().getCommRankingList(entity);
		request.setAttribute("entityList", entityList);
		return mapping.findForward("list");
	}
}
