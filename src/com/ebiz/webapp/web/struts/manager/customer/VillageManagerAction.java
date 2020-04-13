package com.ebiz.webapp.web.struts.manager.customer;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.CommTczhPrice;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;

public class VillageManagerAction extends BaseCustomerAction {
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
		String comm_name_like = (String) dynaBean.get("comm_name_like");
		String is_del = (String) dynaBean.get("is_del");
		String comm_no_like = (String) dynaBean.get("comm_no_like");
		String audit_state = (String) dynaBean.get("audit_state");
		String cls_id = (String) dynaBean.get("cls_id");
		String comm_type = (String) dynaBean.get("comm_type");
		String country = (String) dynaBean.get("country");
		String city = (String) dynaBean.get("city");
		String province = (String) dynaBean.get("province");

		CommInfo entity = new CommInfo();
		super.copyProperties(entity, form);
		entity.setComm_type(Keys.CommType.COMM_TYPE_2.getIndex());

		if (StringUtils.isNotBlank(country)) {
			entity.setP_index(Integer.valueOf(country));
		} else if (StringUtils.isNotBlank(city)) {
			entity.getMap().put("p_index_city", StringUtils.substring(city, 0, 4));
		} else if (StringUtils.isNotBlank(province)) {
			entity.getMap().put("p_index_province", StringUtils.substring(province, 0, 2));
		}

		if (StringUtils.isNotBlank(comm_type)) {
			entity.setComm_type(Integer.valueOf(comm_type));
			dynaBean.set("comm_type", comm_type);
		}
		if (StringUtils.isNotBlank(audit_state)) {
			entity.setAudit_state(Integer.parseInt(audit_state));
		}
		if (StringUtils.isNotBlank(is_del)) {
			if ("-1".equals(is_del)) {
				entity.setIs_del(null);
			}
			if ("0".equals(is_del)) {
				entity.setIs_del(0);
			}
			if ("1".equals(is_del)) {
				entity.setIs_del(1);
			}
		} else {
			entity.setIs_del(0);
			dynaBean.set("is_del", "0");
		}
		if (StringUtils.isNotBlank(comm_name_like)) {
			entity.getMap().put("comm_name_like", comm_name_like);
		}
		if (StringUtils.isNotBlank(comm_no_like)) {
			entity.getMap().put("comm_no_like", comm_no_like);
		}

		if (StringUtils.isNotBlank(cls_id) && !cls_id.equals("1")) {
			entity.setCls_id(null);
			entity.getMap().put("allPd", "true");
			entity.getMap().put("par_cls_id", cls_id);
		}
		if (null != userInfo.getOwn_entp_id()) {
			entity.setOwn_entp_id(userInfo.getOwn_entp_id());
			entity.getMap().put("order_value", true);

			Integer recordCount = getFacade().getCommInfoService().getCommInfoCount(entity);
			pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
			entity.getRow().setFirst(pager.getFirstRow());
			entity.getRow().setCount(pager.getRowCount());

			List<CommInfo> entityList = super.getFacade().getCommInfoService().getCommInfoPaginatedList(entity);
			if (null != entityList && entityList.size() > 0) {
				for (CommInfo ci : entityList) {
					// 套餐管理
					CommTczhPrice param_ctp = new CommTczhPrice();
					param_ctp.setComm_id(ci.getId().toString());
					param_ctp.getMap().put("order_by_inventory_asc", "true");
					List<CommTczhPrice> CommTczhPriceList = super.getFacade().getCommTczhPriceService()
							.getCommTczhPriceList(param_ctp);
					if ((null != CommTczhPriceList) && (CommTczhPriceList.size() > 0)) {
						ci.getMap().put("count_tczh_price_inventory", CommTczhPriceList.get(0).getInventory());
					}
				}
			}
			request.setAttribute("entityList", entityList);
		}

		return mapping.findForward("list");
	}

}
