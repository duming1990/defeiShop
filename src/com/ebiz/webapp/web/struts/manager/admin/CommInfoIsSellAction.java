package com.ebiz.webapp.web.struts.manager.admin;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

public class CommInfoIsSellAction extends BaseAdminAction {

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		UserInfo userInfo = super.getUserInfoFromSession(request);

		if (null == userInfo) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}

		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);

		String opt_type = (String) dynaBean.get("opt_type");
		Pager pager = (Pager) dynaBean.get("pager");

		dynaBean.set("opt_type", "2");
		BaseAuditRecord baseAuditRecord = new BaseAuditRecord();
		baseAuditRecord.setOpt_type(Keys.OptType.OPT_TYPE_2.getIndex());
		if (StringUtils.isNotBlank(opt_type) && GenericValidator.isInt(opt_type)) {
			baseAuditRecord.setOpt_type(new Integer(opt_type));
			dynaBean.set("opt_type", opt_type);
		}

		Integer recordCount = getFacade().getBaseAuditRecordService().getBaseAuditRecordCount(baseAuditRecord);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		baseAuditRecord.getRow().setFirst(pager.getFirstRow());
		baseAuditRecord.getRow().setCount(pager.getRowCount());

		List<BaseAuditRecord> baseAuditRecordList = super.getFacade().getBaseAuditRecordService()
				.getBaseAuditRecordPaginatedList(baseAuditRecord);
		if (null != baseAuditRecordList && baseAuditRecordList.size() > 0) {
			for (BaseAuditRecord temp : baseAuditRecordList) {
				CommInfo commInfo = new CommInfo();
				commInfo.setId(temp.getLink_id());
				commInfo = super.getFacade().getCommInfoService().getCommInfo(commInfo);
				if (null != commInfo) {
					temp.getMap().put("commInfo", commInfo);
				}
			}
			request.setAttribute("baseAuditRecordList", baseAuditRecordList);
		}

		request.setAttribute("optTypes", Keys.OptType.values());
		return mapping.findForward("list");

	}

	public ActionForward audit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		setNaviStringToRequestScope(request);
		saveToken(request);
		DynaBean dynaBean = (DynaBean) form;

		String id = (String) dynaBean.get("id");
		String opt_type = (String) dynaBean.get("opt_type");
		if (!GenericValidator.isLong(id)) {
			String msg = super.getMessage(request, "参数不合法！");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}

		BaseAuditRecord baseAuditRecord = new BaseAuditRecord();
		baseAuditRecord.setId(Integer.valueOf(id));
		baseAuditRecord = super.getFacade().getBaseAuditRecordService().getBaseAuditRecord(baseAuditRecord);

		if (null != baseAuditRecord) {
			CommInfo entity = new CommInfo();
			entity.setId(baseAuditRecord.getLink_id());
			entity = getFacade().getCommInfoService().getCommInfo(entity);
			if (null != entity) {
				if (entity.getFreight_id() != null) {

					Freight f = new Freight();
					f.setId(entity.getFreight_id());
					f.setIs_del(0);
					f = super.getFacade().getFreightService().getFreight(f);
					entity.getMap().put("freight", f);
				}

				CommTczhPrice param_ctp = new CommTczhPrice();
				param_ctp.setComm_id(baseAuditRecord.getLink_id().toString());
				List<CommTczhPrice> list_CommTczhPrice = super.getFacade().getCommTczhPriceService()
						.getCommTczhPriceList(param_ctp);
				if ((list_CommTczhPrice != null) && (list_CommTczhPrice.size() > 0)) {
					for (CommTczhPrice ctp : list_CommTczhPrice) {
						CommTczhAttribute param_ctattr = new CommTczhAttribute();
						param_ctattr.setComm_tczh_id(ctp.getId());
						param_ctattr.setComm_id(baseAuditRecord.getLink_id().toString());
						param_ctattr.getMap().put("orderby_order_value_asc", "true");
						List<CommTczhAttribute> list_CommTczhAttribute = super.getFacade()
								.getCommTczhAttributeService().getCommTczhAttributeList(param_ctattr);
						for (CommTczhAttribute temp_cta : list_CommTczhAttribute) {
							BaseAttributeSon param_bas = new BaseAttributeSon();
							param_bas.setId(Integer.valueOf(temp_cta.getAttr_id()));
							BaseAttributeSon bas = super.getFacade().getBaseAttributeSonService()
									.getBaseAttributeSon(param_bas);
							if (bas != null) {
								temp_cta.setAttr_name(bas.getAttr_name());
							}
						}
						ctp.getMap().put("list_CommTczhAttribute", list_CommTczhAttribute);
					}
					request.setAttribute("list_CommTczhPrice", list_CommTczhPrice); // 套餐价格及对应各属性
				}

				entity.setQueryString(super.serialize(request, "id", "method"));
				super.copyProperties(form, entity);

			}
		}
		return new ActionForward("/../manager/admin/CommInfoIsSell/audit.jsp");
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
		String id = (String) dynaBean.get("id");
		String mod_id = (String) dynaBean.get("mod_id");
		String audit_state = (String) dynaBean.get("audit_state");
		String opt_type = (String) dynaBean.get("opt_type");
		String audit_desc = (String) dynaBean.get("audit_desc");

		BaseAuditRecord baseAuditRecord = null;
		if (StringUtils.isNotBlank(id) && GenericValidator.isInt(id)) {
			baseAuditRecord = new BaseAuditRecord();
			baseAuditRecord.setId(new Integer(id));
			baseAuditRecord.setAudit_state(new Integer(audit_state));
			baseAuditRecord.setAudit_date(new Date());
			baseAuditRecord.setAudit_user_id(ui.getId());
			baseAuditRecord.setAudit_user_name(ui.getUser_name());
			baseAuditRecord.setAudit_note(audit_desc);
			baseAuditRecord.getMap().put("is_sell_audit", true);
			super.getFacade().getBaseAuditRecordService().modifyBaseAuditRecord(baseAuditRecord);
		}

		saveMessage(request, "entity.updated");

		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&opt_type=" + opt_type);
		// pathBuffer.append("&");
		// pathBuffer.append(super.encodeSerializedQueryString(baseAuditRecord.getQueryString()));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;

	}
}
