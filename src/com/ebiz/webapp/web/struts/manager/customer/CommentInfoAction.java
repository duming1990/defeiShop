package com.ebiz.webapp.web.struts.manager.customer;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.webapp.domain.CommentInfo;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.OrderInfoDetails;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.struts.BaseAction;

/**
 * @author Wu,Yang
 * @version 2013-06-22
 */
public class CommentInfoAction extends BaseAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		DynaBean dynaBean = (DynaBean) form;
		String link_id = (String) dynaBean.get("link_id");
		String order_id = (String) dynaBean.get("order_id");
		if (StringUtils.isBlank(link_id) || StringUtils.isBlank(order_id)) {
			String msg = "参数有误！";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		saveToken(request);
		return mapping.findForward("list");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		if (isCancelled(request)) {
			return list(mapping, form, request, response);
		}
		if (!isTokenValid(request)) {
			saveError(request, "errors.token");
			return list(mapping, form, request, response);
		}
		resetToken(request);
		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);
		String order_id = (String) dynaBean.get("order_id");
		String link_id = (String) dynaBean.get("link_id");
		String base_files1 = (String) dynaBean.get("base_files1");
		String base_files2 = (String) dynaBean.get("base_files2");
		String base_files3 = (String) dynaBean.get("base_files3");
		String base_files4 = (String) dynaBean.get("base_files4");
		String base_files5 = (String) dynaBean.get("base_files5");
		String[] basefiles = { base_files1, base_files2, base_files3, base_files4, base_files5 };

		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setId(new Integer(order_id));
		orderInfo = super.getFacade().getOrderInfoService().getOrderInfo(orderInfo);

		CommentInfo entity = new CommentInfo();
		super.copyProperties(entity, dynaBean);
		if (orderInfo.getOrder_type().equals(Keys.OrderType.ORDER_TYPE_10.getIndex())) {
			entity.setComm_type(Keys.CommentType.COMMENT_TYPE_10.getIndex());
		}
		entity.setLink_id(new Integer(link_id));
		entity.setComm_ip(this.getIpAddr(request));
		entity.setComm_time(new Date());
		entity.setComm_state(1);// 发布
		entity.setLink_f_id(orderInfo.getEntp_id());
		if (StringUtils.isBlank(base_files1) && StringUtils.isBlank(base_files2) && StringUtils.isBlank(base_files3)
				&& StringUtils.isBlank(base_files4) && StringUtils.isBlank(base_files5)) {
			entity.setHas_pic(0);
		} else {
			entity.setHas_pic(1);
		}
		entity.setOrder_value(0);
		entity.setComm_uid(ui.getId());
		entity.setComm_uname(ui.getUser_name());

		entity.getMap().put("basefiles", basefiles);

		entity.setEntp_id(orderInfo.getEntp_id());

		OrderInfoDetails ods = super.getOrderInfoDetails(entity);
		if (null != ods) {
			entity.setComm_name(ods.getComm_name());
			entity.setComm_tczh_name(ods.getComm_tczh_name());
		}

		getFacade().getCommentInfoService().createCommentInfo(entity);

		// 更新评论数
		if (null != orderInfo.getEntp_id()) {
			EntpInfo ei = new EntpInfo();
			ei.setId(orderInfo.getEntp_id());
			ei.getMap().put("add_comment_count", 1);// 评论数+1
			super.getFacade().getEntpInfoService().modifyEntpInfo(ei);
		}

		saveMessage(request, "entity.inerted");

		super.renderJavaScript(response, "window.onload=function(){window.parent.location.reload();}");

		return null;
	}

}
