package com.ebiz.webapp.web.struts.manager.customer;

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
import com.ebiz.webapp.domain.BaseImgs;
import com.ebiz.webapp.domain.ServiceCenterInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.VillageDynamic;
import com.ebiz.webapp.domain.VillageDynamicReport;
import com.ebiz.webapp.web.Keys;

public class DynamicReportManagerAction extends BaseCustomerAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "1")) {
			return super.checkPopedomInvalid(request, response);
		}

		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}

		Pager pager = (Pager) dynaBean.get("pager");
		String is_read = (String) dynaBean.get("is_read");

		VillageDynamicReport entity = new VillageDynamicReport();
		if (StringUtils.isNotBlank(is_read)) {
			entity.setIs_read(Integer.valueOf(is_read));
		}
		if (ui.getIs_village() != null || ui.getIs_village() == 1) {
			entity.setVillage_id(ui.getOwn_village_id());
		}
		
		if(null != ui.getIs_fuwu() && 1==ui.getIs_fuwu()){//县域合伙人角色
			ServiceCenterInfo serviceCenterInfo = new ServiceCenterInfo();
			serviceCenterInfo.setAdd_user_id(ui.getId());
			serviceCenterInfo.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
			serviceCenterInfo.setAudit_state(1);
			List<ServiceCenterInfo> serviceCenterInfoList = super.getFacade().getServiceCenterInfoService().getServiceCenterInfoList(serviceCenterInfo);
			if(null!=serviceCenterInfoList&&serviceCenterInfoList.size()==1){
				entity.getMap().put("p_index", serviceCenterInfoList.get(0).getP_index().toString());
			}else {
				entity.getMap().put("p_index", "999999");
			}
			
		}

		entity.setIs_del(0);

		Integer recordCount = getFacade().getVillageDynamicReportService().getVillageDynamicReportCount(entity);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<VillageDynamicReport> list = getFacade().getVillageDynamicReportService()
				.getVillageDynamicReportPaginatedList(entity);
		if (list != null && list.size() > 0) {
			for (VillageDynamicReport temp : list) {
				VillageDynamic vDynamic = new VillageDynamic();
				vDynamic.setId(temp.getDynamic_id());
				vDynamic.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
				vDynamic = getFacade().getVillageDynamicService().getVillageDynamic(vDynamic);
				if (null != vDynamic) {
					temp.getMap().put("vDynamic", vDynamic);

					// 动态相关图片
					BaseImgs img = new BaseImgs();
					img.getMap().put(
							"img_type_in",
							Keys.BaseImgsType.Base_Imgs_TYPE_40.getIndex() + ","
									+ Keys.BaseImgsType.Base_Imgs_TYPE_50.getIndex());
					img.setLink_id(vDynamic.getId());
					List<BaseImgs> imgList = getFacade().getBaseImgsService().getBaseImgsList(img);
					temp.getMap().put("imgList", imgList);
				}
			}
		}
		request.setAttribute("entityList", list);

		return mapping.findForward("list");
	}

	public ActionForward audit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		UserInfo ui = super.getUserInfoFromSession(request);

		if (!GenericValidator.isLong(id)) {
			this.saveError(request, "errors.Integer", new String[] { id });
			return mapping.findForward("list");
		}

		VillageDynamicReport entity = new VillageDynamicReport();
		entity.setId(Integer.valueOf(id));
		entity.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		entity = getFacade().getVillageDynamicReportService().getVillageDynamicReport(entity);
		if (entity == null) {
			String msg = "参数不正确！";
			super.showMsgForCustomer(request, response, msg);
			return null;
		}
		entity.setIs_read(1);
		super.getFacade().getVillageDynamicReportService().modifyVillageDynamicReport(entity);

		VillageDynamic vDynamic = new VillageDynamic();
		vDynamic.setId(entity.getVillage_id());
		vDynamic.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		vDynamic = getFacade().getVillageDynamicService().getVillageDynamic(vDynamic);
		if (null != vDynamic) {
			// 动态相关图片
			BaseImgs img = new BaseImgs();
			img.getMap().put(
					"img_type_in",
					Keys.BaseImgsType.Base_Imgs_TYPE_40.getIndex() + ","
							+ Keys.BaseImgsType.Base_Imgs_TYPE_50.getIndex());
			img.setLink_id(vDynamic.getId());
			List<BaseImgs> imgList = getFacade().getBaseImgsService().getBaseImgsList(img);
			request.setAttribute("imgList", imgList);
			request.setAttribute("vDynamic", vDynamic);
		}
		entity.setQueryString(super.serialize(request, "id", "method"));
		// end
		super.copyProperties(form, entity);
		return new ActionForward("/../manager/customer/DynamicReportManager/audit.jsp");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		DynaBean dynaBean = (DynaBean) form;

		String id = (String) dynaBean.get("id");
		String mod_id = (String) dynaBean.get("mod_id");

		VillageDynamic entity = new VillageDynamic();
		super.copyProperties(entity, form);
		if (StringUtils.isBlank(id)) {
			String msg = "参数错误！";
			super.showMsgForCustomer(request, response, msg);
			return null;
		}

		entity.setAudit_user_id(ui.getId());
		entity.setAudit_date(new Date());
		entity.setAudit_user_name(ui.getUser_name());

		super.getFacade().getVillageDynamicService().modifyVillageDynamic(entity);
		saveMessage(request, "entity.audit");

		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
		pathBuffer.append("&mod_id=" + mod_id);
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

}
