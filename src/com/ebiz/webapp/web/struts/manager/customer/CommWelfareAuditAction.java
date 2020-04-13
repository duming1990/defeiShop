package com.ebiz.webapp.web.struts.manager.customer;

import java.util.ArrayList;
import java.util.Date;
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
import com.ebiz.webapp.domain.CommWelfareApply;
import com.ebiz.webapp.domain.CommWelfareDetail;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.ServiceCenterInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;

public class CommWelfareAuditAction extends BaseCustomerAction {

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
		if (userInfo.getId() == 2) {
			request.setAttribute("is_jd", true);
		}

		Integer entpId = null;
		if (userInfo.getIs_fuwu() != 1) {
			String msg = "当前登录用户不是合伙人，无法审核福利商品！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}
		ServiceCenterInfo serviceInfo = new ServiceCenterInfo();
		serviceInfo.setAdd_user_id(userInfo.getId());
		serviceInfo = super.getFacade().getServiceCenterInfoService().getServiceCenterInfo(serviceInfo);
		entpId = serviceInfo.getId();
		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);

		Pager pager = (Pager) dynaBean.get("pager");
		String entp_name_like = (String) dynaBean.get("entp_name_like");

		CommWelfareApply entity = new CommWelfareApply();
		if (StringUtils.isNotBlank(entp_name_like)) {
			entity.getMap().put("entp_name_like", entp_name_like);
		}
		entity.setService_id(entpId);
		entity.setIs_del(0);
		List<CommWelfareApply> welfareApplyList = getFacade().getCommWelfareApplyService().getCommWelfareApplyList(
				entity);

		Integer recordCount = getFacade().getCommWelfareApplyService().getCommWelfareApplyCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());
		request.setAttribute("entityList", welfareApplyList);
		request.setAttribute("pager", pager);
		return mapping.findForward("list");
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='../../login.shtml'}");
			return null;
		}

		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);

		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(Keys.SESSION_USERINFO_KEY);

		Integer entpId = null;
		

		 ServiceCenterInfo entity = new ServiceCenterInfo();
		 entity.setAdd_user_id(userInfo.getId());
		 entity = super.getFacade().getServiceCenterInfoService().getServiceCenterInfo(entity);
		 entpId = entity.getId();
		 
		String comm_welfare_id = (String) request.getParameter("id");

		CommWelfareDetail commWelfareDetail = new CommWelfareDetail();
		commWelfareDetail.setComm_welfare_id(Integer.valueOf(comm_welfare_id));
		commWelfareDetail.setService_id(entpId);
		commWelfareDetail.setIs_del(0);
		List<CommWelfareDetail> welfareDetailList = super.getFacade().getCommWelfareDetailService()
				.getCommWelfareDetailList(commWelfareDetail);

		List<CommInfo> commList = new ArrayList<CommInfo>();

		for (CommWelfareDetail welfareDetail : welfareDetailList) {
			CommInfo commInfo = new CommInfo();
			commInfo.setId(welfareDetail.getComm_id());
			commInfo = super.getFacade().getCommInfoService().getCommInfo(commInfo);
			commList.add(commInfo);
		}

		if (null != commList && commList.size() > 0) {
			for (CommInfo ci : commList) {
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

		if (welfareDetailList.get(0).getType() == Keys.STATUS_TYPE.STATUS_TYPE_1.getIndex()) {
			EntpInfo entp = new EntpInfo();
			entp.setId(welfareDetailList.get(0).getEntp_id());
			entp = super.getFacade().getEntpInfoService().getEntpInfo(entp);
			request.setAttribute("entity", entp);
		}
		if (welfareDetailList.get(0).getType() == Keys.STATUS_TYPE.STATUS_TYPE_2.getIndex()) {
			ServiceCenterInfo serviceInfo = new ServiceCenterInfo();
			serviceInfo.setId(welfareDetailList.get(0).getEntp_id());
			serviceInfo = super.getFacade().getServiceCenterInfoService().getServiceCenterInfo(serviceInfo);
			request.setAttribute("entity", serviceInfo);
		}
		request.setAttribute("comm_welfare_id", Integer.valueOf(comm_welfare_id));
		request.setAttribute("commList", commList);
		request.setAttribute("type", welfareDetailList.get(0).getType());
		request.setAttribute("audit_state", welfareDetailList.get(0).getAudit_state());
		return mapping.findForward("view");
	}

	public ActionForward audit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='../../login.shtml'}");
			return null;
		}
		String comm_welfare_id = (String) request.getParameter("comm_welfare_id");
		String audit_state = (String) request.getParameter("audit_state");
		String audit_desc = (String) request.getParameter("audit_desc");

		if (StringUtils.isBlank(comm_welfare_id)) {
			String msg = "参数错误！";
			super.showMsgForCustomer(request, response, msg);
			return null;
		}

		CommWelfareApply welfareApply = new CommWelfareApply();
		welfareApply.setId(Integer.valueOf(comm_welfare_id));
		welfareApply.setAudit_state(Integer.valueOf(audit_state));
		welfareApply.setAudit_desc(audit_desc);
		welfareApply.setAudit_date(new Date());
		welfareApply.setAudit_user_id(ui.getId());

		
		CommWelfareDetail welfareDetail = new CommWelfareDetail();
		welfareDetail.setComm_welfare_id(Integer.valueOf(comm_welfare_id));
		welfareDetail.setAudit_state(Integer.valueOf(audit_state));
		welfareDetail.setAudit_desc(audit_desc);
		welfareDetail.setAudit_date(new Date());
		welfareDetail.setAudit_user_id(ui.getId());

		welfareDetail.getMap().put("welfareApply", welfareApply);
		super.getFacade().getCommWelfareDetailService().modifyCommWelfareDetail(welfareDetail);

		return mapping.findForward("success");
	}

}
