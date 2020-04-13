package com.ebiz.webapp.web.struts.manager.customer;

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
import com.ebiz.webapp.domain.BaseFiles;
import com.ebiz.webapp.domain.CommentInfo;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.struts.BaseAction;

/**
 * @author Wu,Yang
 * @version 2013-06-22
 */
public class MyCommentAction extends BaseAction {
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
		getsonSysModuleList(request, dynaBean);

		Pager pager = (Pager) dynaBean.get("pager");
		CommentInfo commentInfo = new CommentInfo();
		commentInfo.setComm_uid(ui.getId());

		Integer recordCount = super.getFacade().getCommentInfoService().getCommentInfoCount(commentInfo);

		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		commentInfo.getRow().setCount(pager.getRowCount());
		commentInfo.getRow().setFirst(pager.getFirstRow());

		List<CommentInfo> commentInfoList = super.getFacade().getCommentInfoService()
				.getCommentInfoPaginatedList(commentInfo);
		if (null != commentInfoList && commentInfoList.size() > 0) {
			request.setAttribute("commentInfoList", commentInfoList);
		}

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
		getFacade().getCommentInfoService().modifyCommentInfo(entity);

		saveMessage(request, "entity.updated");

		super.renderJavaScript(response, "window.onload=function(){window.parent.location.reload();}");

		return null;
	}

	public ActionForward editComment(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (StringUtils.isBlank(id) || StringUtils.isBlank(id)) {
			String msg = "参数有误！";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		saveToken(request);

		CommentInfo commentInfo = new CommentInfo();
		commentInfo.setId(Integer.valueOf(id));
		commentInfo = super.getFacade().getCommentInfoService().getCommentInfo(commentInfo);
		super.copyProperties(form, commentInfo);
		if (null != commentInfo) {
			BaseFiles baseFiles = new BaseFiles();
			baseFiles.setLink_id(commentInfo.getId());
			baseFiles.setLink_tab("CommentInfo");
			baseFiles.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
			baseFiles.setType(Keys.BaseFilesType.Base_Files_TYPE_40.getIndex());
			List<BaseFiles> baseFilesList = super.getFacade().getBaseFilesService().getBaseFilesList(baseFiles);
			Integer size = 5 - baseFilesList.size();
			for (int i = 0; i < size; i++) {
				baseFilesList.add(new BaseFiles());
			}
			request.setAttribute("baseFilesList", baseFilesList);
		}
		return new ActionForward("/../manager/customer/MyComment/editComment.jsp");
	}

}
