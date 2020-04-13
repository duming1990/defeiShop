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

import com.alibaba.fastjson.JSONObject;
import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.BaseImgs;
import com.ebiz.webapp.domain.ServiceCenterInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.VillageDynamic;
import com.ebiz.webapp.domain.VillageDynamicComment;
import com.ebiz.webapp.web.Keys;

public class DynamicManagerAction extends BaseCustomerAction {
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
		String add_user_name_like = (String) dynaBean.get("add_user_name_like");
		String audit_state = (String) dynaBean.get("audit_state");

		VillageDynamic entity = new VillageDynamic();
		if (StringUtils.isNotBlank(audit_state)) {
			entity.setAudit_state(Integer.valueOf(audit_state));
		}
		if (ui.getIs_village() != null || ui.getIs_village() == 1) {
			entity.setVillage_id(ui.getOwn_village_id());
		}
		entity.setType(Keys.DynamicType.dynamic_type_1.getIndex());
		if (null != ui.getIs_fuwu() && 1 == ui.getIs_fuwu()) {// 县域合伙人角色
			ServiceCenterInfo serviceCenterInfo = new ServiceCenterInfo();
			serviceCenterInfo.setAdd_user_id(ui.getId());
			serviceCenterInfo.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
			serviceCenterInfo.setAudit_state(1);
			List<ServiceCenterInfo> serviceCenterInfoList = super.getFacade().getServiceCenterInfoService()
					.getServiceCenterInfoList(serviceCenterInfo);
			if (null != serviceCenterInfoList && serviceCenterInfoList.size() == 1) {
				entity.getMap().put("p_index", serviceCenterInfoList.get(0).getP_index().toString());
			} else {
				entity.getMap().put("p_index", "999999");
			}

		}

		entity.setIs_del(0);
		entity.getMap().put("add_user_name_like", add_user_name_like);

		Integer recordCount = getFacade().getVillageDynamicService().getVillageDynamicCount(entity);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<VillageDynamic> list = getFacade().getVillageDynamicService().getVillageDynamicPaginatedList(entity);

		if (list != null && list.size() > 0) {
			for (VillageDynamic item : list) {// 该动态下的评论回复
				super.setMapDynamicImgs(item);// 查询图片
				VillageDynamicComment vComment = new VillageDynamicComment();
				vComment.setLink_id(item.getId());
				vComment.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
				vComment.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
				List<VillageDynamicComment> vCommentList = getFacade().getVillageDynamicCommentService()
						.getVillageDynamicCommentList(vComment);
				if (null != vCommentList && vCommentList.size() > 0) {
					item.getMap().put("vCommentList", vCommentList);
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

		VillageDynamic entity = new VillageDynamic();
		entity.setId(Integer.valueOf(id));
		entity.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		entity = getFacade().getVillageDynamicService().getVillageDynamic(entity);
		if (entity == null) {
			String msg = "参数不正确！";
			super.showMsgForCustomer(request, response, msg);
			return null;
		}

		BaseImgs img = new BaseImgs();
		img.getMap().put("img_type_in",
				Keys.BaseImgsType.Base_Imgs_TYPE_40.getIndex() + "," + Keys.BaseImgsType.Base_Imgs_TYPE_50.getIndex());
		img.setLink_id(entity.getId());
		List<BaseImgs> imgList = getFacade().getBaseImgsService().getBaseImgsList(img);
		request.setAttribute("imgList", imgList);

		entity.setQueryString(super.serialize(request, "id", "method"));
		// end
		super.copyProperties(form, entity);
		return new ActionForward("/../manager/customer/DynamicManager/audit.jsp");

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

	public ActionForward commentList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		String dynamic_id = (String) dynaBean.get("dynamic_id");// 动态id
		UserInfo ui = super.getUserInfoFromSession(request);
		if (StringUtils.isBlank(dynamic_id)) {
			String msg = "参数不正确！";
			super.showMsgForCustomer(request, response, msg);
			return null;
		}
		VillageDynamicComment entity = new VillageDynamicComment();
		entity.setLink_id(Integer.valueOf(dynamic_id));
		entity.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		entity.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		List<VillageDynamicComment> entityList = getFacade().getVillageDynamicCommentService()
				.getVillageDynamicCommentList(entity);

		request.setAttribute("entityList", entityList);
		return new ActionForward("/../manager/customer/DynamicManager/comment_list.jsp");
	}

	public ActionForward deleteComment(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");// 动态id
		UserInfo ui = super.getUserInfoFromSession(request);

		JSONObject data = new JSONObject();
		String ret = "0";
		String msg = "";

		if (StringUtils.isBlank(id)) {
			msg = "参数不正确！";
			data.put("ret", ret);
			data.put("msg", msg);
			super.renderJson(response, data.toString());
			return null;
		}
		VillageDynamicComment entity = new VillageDynamicComment();
		entity.setId(Integer.valueOf(id));
		entity.setIs_del(Keys.IsDel.IS_DEL_1.getIndex());
		entity.setDel_date(new Date());
		entity.setDel_user_id(ui.getId());
		entity.setDel_user_name(ui.getUser_name());
		int flag = getFacade().getVillageDynamicCommentService().modifyVillageDynamicComment(entity);
		if (Integer.valueOf(flag) > 0) {
			msg = "已删除！";
			ret = "1";
		}
		data.put("ret", ret);
		data.put("msg", msg);
		super.renderJson(response, data.toString());
		return null;
	}
}
