package com.ebiz.webapp.web.struts.manager.customer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.BaseAttributeSon;
import com.ebiz.webapp.domain.BaseAuditRecord;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.CommTczhAttribute;
import com.ebiz.webapp.domain.CommTczhPrice;
import com.ebiz.webapp.domain.Freight;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.Keys.CommType;

/**
 * @author minyg
 * @version 2013-09-26
 */

public class CommInfoAuditAction extends BaseCustomerAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo userInfo = super.getUserInfoFromSession(request);

		if (null == userInfo) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}

		super.setAuditCommInfo(form, request, userInfo);

		return mapping.findForward("list");

	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (isCancelled(request)) {
			return list(mapping, form, request, response);
		}
		if (!isTokenValid(request)) {
			saveError(request, "errors.token");
			return list(mapping, form, request, response);
		}
		resetToken(request);

		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		DynaBean dynaBean = (DynaBean) form;
		String mod_id = (String) dynaBean.get("mod_id");
		String par_id = (String) dynaBean.get("par_id");

		CommInfo commInfo = new CommInfo();
		super.copyProperties(commInfo, form);
		commInfo.setAudit_date(new Date());
		commInfo.setAudit_user_id(ui.getId());
		super.getFacade().getCommInfoService().modifyCommInfo(commInfo);

		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&par_id=" + par_id);
		pathBuffer.append("&mod_id=" + mod_id);
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward audit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(Keys.SESSION_USERINFO_KEY);
		if (null == userInfo) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}

		saveToken(request);
		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);

		String id = (String) dynaBean.get("id");
		if (StringUtils.isBlank(id)) {
			String msg = super.getMessage(request, "参数不能为空");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}

		if (GenericValidator.isLong(id)) {
			CommInfo entity = new CommInfo();
			entity.setId(Integer.valueOf(id));
			entity = getFacade().getCommInfoService().getCommInfo(entity);
			super.copyProperties(form, entity);
			dynaBean.set("is_freeship", 0);

			CommTczhPrice param_ctp = new CommTczhPrice();
			param_ctp.setComm_id(id);
			List<CommTczhPrice> list_CommTczhPrice = super.getFacade().getCommTczhPriceService()
					.getCommTczhPriceList(param_ctp);
			if ((list_CommTczhPrice != null) && (list_CommTczhPrice.size() > 0)) {
				for (CommTczhPrice ctp : list_CommTczhPrice) {
					CommTczhAttribute param_ctattr = new CommTczhAttribute();
					param_ctattr.setComm_tczh_id(ctp.getId());
					param_ctattr.setComm_id(id);
					param_ctattr.getMap().put("orderby_order_value_asc", "true");
					List<CommTczhAttribute> list_CommTczhAttribute = super.getFacade().getCommTczhAttributeService()
							.getCommTczhAttributeList(param_ctattr);
					List<String> attr_tczh_ids = new ArrayList<String>();
					List<String> attr_tczh_names = new ArrayList<String>();
					for (CommTczhAttribute temp_cta : list_CommTczhAttribute) {
						BaseAttributeSon param_bas = new BaseAttributeSon();
						param_bas.setId(Integer.valueOf(temp_cta.getAttr_id()));
						BaseAttributeSon bas = super.getFacade().getBaseAttributeSonService()
								.getBaseAttributeSon(param_bas);
						if (bas != null) {
							temp_cta.setAttr_name(bas.getAttr_name());
						}
						attr_tczh_ids.add(temp_cta.getAttr_id().toString());
						attr_tczh_names.add(temp_cta.getAttr_name());
					}

					ctp.getMap().put("attr_tczh_ids", StringUtils.join(attr_tczh_ids, ","));
					ctp.getMap().put("attr_tczh_names", StringUtils.join(attr_tczh_names, " "));

				}
				request.setAttribute("list_CommTczhPrice", list_CommTczhPrice); // 套餐价格及对应各属性
			}

			List<CommType> commTypeList = new ArrayList<Keys.CommType>();
			commTypeList.add(Keys.CommType.valueOf(Keys.CommType.COMM_TYPE_2.getSonType().toString()));
			// commTypeList.add(Keys.CommType.valueOf(Keys.CommType.COMM_TYPE_3.getSonType().toString()));

			request.setAttribute("commTypeList", commTypeList);

			if (null != entity && null != entity.getFreight_id()) {
				Freight fre = super.getFreightInfo(entity.getFreight_id());
				if (null != fre) {
					dynaBean.set("fre_title", fre.getFre_title());
				}
			}

			return new ActionForward("/../manager/customer/CommInfoAudit/audit.jsp");
		} else {
			saveError(request, "errors.Integer", id);
			return mapping.findForward("list");
		}
	}

	public List<CommInfo> getCommInfo() {
		CommInfo entity = new CommInfo();
		entity.setIs_del(0);
		return super.getFacade().getCommInfoService().getCommInfoList(entity);
	}

	public ActionForward sellList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
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
		return new ActionForward("/../manager/customer/CommInfo/sellList.jsp");
	}

	public ActionForward issell(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		saveToken(request);

		if (StringUtils.isBlank(id)) {
			String msg = super.getMessage(request, "参数不能为空");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}
		if (GenericValidator.isLong(id)) {
			CommInfo entity = new CommInfo();
			entity.setId(Integer.valueOf(id));
			entity = getFacade().getCommInfoService().getCommInfo(entity);

			entity.setQueryString(super.serialize(request, "id", "method"));
			super.copyProperties(form, entity);
			return new ActionForward("/../manager/customer/CommInfoAudit/issell.jsp");
		} else {
			saveError(request, "errors.Integer", id);
			return null;
		}
	}

	public ActionForward saveSell(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (isCancelled(request)) {
			return list(mapping, form, request, response);
		}
		if (!isTokenValid(request)) {
			saveError(request, "errors.token");
			return list(mapping, form, request, response);
		}
		resetToken(request);

		UserInfo ui = (UserInfo) request.getSession().getAttribute(Keys.SESSION_USERINFO_KEY);
		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);

		String id = (String) dynaBean.get("id");
		String mod_id = (String) dynaBean.get("mod_id");
		String par_id = (String) dynaBean.get("par_id");
		String is_sell = (String) dynaBean.get("is_sell");
		String queryString = (String) dynaBean.get("queryString");

		CommInfo entity = new CommInfo();
		super.copyProperties(entity, form);
		if (StringUtils.isNotBlank(id)) {
			super.getFacade().getCommInfoService().modifyCommInfo(entity);
			saveMessage(request, "entity.updated");
		}

		BaseAuditRecord baseAuditRecord = new BaseAuditRecord();
		if (is_sell.equals("1")) {
			baseAuditRecord.setOpt_type(Keys.OptType.OPT_TYPE_2.getIndex());
		}
		if (is_sell.equals("0")) {
			baseAuditRecord.setOpt_type(Keys.OptType.OPT_TYPE_3.getIndex());
		}
		baseAuditRecord.setLink_id(new Integer(id));
		baseAuditRecord.setLink_table("CommInfo");
		baseAuditRecord.setAdd_date(new Date());
		baseAuditRecord.setAdd_user_id(ui.getId());
		baseAuditRecord.setAdd_user_name(ui.getUser_name());
		baseAuditRecord.setAudit_state(0);
		super.getFacade().getBaseAuditRecordService().createBaseAuditRecord(baseAuditRecord);

		saveMessage(request, "entity.inerted");
		super.renderJavaScript(response, "window.parent.workspace.document.location.href='CommInfoAudit.do?mod_id="
				+ mod_id + "&par_id=" + par_id + "&" + queryString + "'");
		return null;

	}
}