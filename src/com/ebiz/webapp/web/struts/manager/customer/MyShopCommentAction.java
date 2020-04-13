package com.ebiz.webapp.web.struts.manager.customer;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.BaseFiles;
import com.ebiz.webapp.domain.CommentInfo;
import com.ebiz.webapp.domain.UserInfo;

/**
 * @author Li,Yuan
 * @version 2012-02-22
 */
public class MyShopCommentAction extends BaseCustomerAction {
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
		String comm_type = (String) dynaBean.get("comm_type");

		CommentInfo entity = new CommentInfo();
		entity.setLink_f_id(ui.getOwn_entp_id());
		entity.setComm_type(new Integer(comm_type));
		entity.setComm_state(1);

		Integer recordCount = getFacade().getCommentInfoService().getCommentInfoCount(entity);
		pager.init(recordCount.longValue(), 10, pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<CommentInfo> commentInfoList = super.getFacade().getCommentInfoService()
				.getCommentInfoPaginatedList(entity);
		if (null != commentInfoList && commentInfoList.size() > 0) {
			request.setAttribute("commentInfoList", commentInfoList);
		}

		saveToken(request);
		return mapping.findForward("list");
	}

	public ActionForward reply(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		saveToken(request);
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);
		String id = (String) dynaBean.get("id");
		// String comm_type = (String) dynaBean.get("comm_type");

		CommentInfo entity = new CommentInfo();
		entity.setId(new Integer(id));
		entity = super.getFacade().getCommentInfoService().getCommentInfo(entity);
		if (null != entity) {
			BaseFiles baseFiles = new BaseFiles();
			baseFiles.setLink_id(new Integer(id));
			baseFiles.setLink_tab("CommentInfo");
			baseFiles.setIs_del(0);
			List<BaseFiles> baseFilesList = super.getFacade().getBaseFilesService().getBaseFilesList(baseFiles);
			if (null != baseFilesList) {
				request.setAttribute("baseFilesList", baseFilesList);
			}
		}

		super.copyProperties(form, entity);
		return new ActionForward("/../manager/customer/MyShopComment/reply.jsp");
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

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		resetToken(request);

		DynaBean dynaBean = (DynaBean) form;

		String par_id = (String) dynaBean.get("par_id");
		String mod_id = (String) dynaBean.get("mod_id");
		String id = (String) dynaBean.get("id");
		String comm_type = (String) dynaBean.get("comm_type");

		CommentInfo entity = new CommentInfo();
		super.copyProperties(entity, dynaBean);
		entity.setId(new Integer(id));
		entity.setReply_date(new Date());
		entity.setReply_uid(ui.getId());
		super.getFacade().getCommentInfoService().modifyCommentInfo(entity);
		saveMessage(request, "entity.updated");

		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&par_id=" + par_id);
		pathBuffer.append("&comm_type=" + comm_type);
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		return forward;
	}

}
