package com.ebiz.webapp.web.struts.manager.admin;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.BaseFiles;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.CommentInfo;
import com.ebiz.webapp.domain.CommentInfoSon;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;

/**
 * @author QinYue
 */
public class CommentInfoAction extends BaseAdminAction {

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		String comm_title_like = (String) dynaBean.get("comm_title_like");
		String comm_state = (String) dynaBean.get("comm_state");
		String comm_type = (String) dynaBean.get("comm_type");
		Pager pager = (Pager) dynaBean.get("pager");

		CommentInfo entity = new CommentInfo();

		if (StringUtils.isNotBlank(comm_title_like)) {
			entity.getMap().put("comm_title_like", comm_title_like);
		}
		if (StringUtils.isNotBlank(comm_state)) {
			entity.setComm_state(Integer.valueOf(comm_state));
		}
		if (StringUtils.isNotBlank(comm_type)) {
			entity.setComm_type(Integer.valueOf(comm_type));
		}

		Integer recordCount = super.getFacade().getCommentInfoService().getCommentInfoCount(entity);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		entity.getRow().setCount(pager.getRowCount());
		entity.getRow().setFirst(pager.getFirstRow());

		List<CommentInfo> entityList = super.getFacade().getCommentInfoService().getCommentInfoPaginatedList(entity);
		request.setAttribute("entityList", entityList);

		request.setAttribute("commentTypeList", Keys.CommentType.values());

		return mapping.findForward("list");
	}

	public ActionForward viewCommentSon(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		String content_like = (String) dynaBean.get("content_like");
		String user_name_like = (String) dynaBean.get("user_name_like");
		String to_user_name_like = (String) dynaBean.get("to_user_name_like");
		String st_add_date = (String) dynaBean.get("st_add_date");
		String en_add_date = (String) dynaBean.get("en_add_date");
		String par_id = (String) dynaBean.get("par_id");
		Pager pager = (Pager) dynaBean.get("pager");

		if (!GenericValidator.isInt(par_id)) {
			saveMessage(request, "entity.missing");
			return mapping.findForward("list");
		}
		CommentInfo commentInfo = new CommentInfo();
		commentInfo.setId(new Integer(par_id));
		commentInfo = getFacade().getCommentInfoService().getCommentInfo(commentInfo);
		request.setAttribute("commentInfo", commentInfo);

		CommentInfoSon entity = new CommentInfoSon();
		entity.setIs_del(0);
		entity.setPar_id(Integer.valueOf(par_id));
		entity.getMap().put("content_like", content_like);
		entity.getMap().put("user_name_like", user_name_like);
		entity.getMap().put("to_user_name_like", to_user_name_like);
		entity.getMap().put("st_add_date", st_add_date);
		entity.getMap().put("en_add_date", en_add_date);

		Integer recordCount = super.getFacade().getCommentInfoSonService().getCommentInfoSonCount(entity);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		entity.getRow().setCount(pager.getRowCount());
		entity.getRow().setFirst(pager.getFirstRow());

		List<CommentInfoSon> entityList = super.getFacade().getCommentInfoSonService()
				.getCommentInfoSonPaginatedList(entity);
		for (CommentInfoSon ci : entityList) {
		}
		request.setAttribute("entityList", entityList);

		return new ActionForward("/../manager/admin/CommentInfo/comment_son_list.jsp");
	}

	public ActionForward audit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		saveToken(request);
		super.getModPopeDom(form, request);
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (GenericValidator.isLong(id)) {
			CommentInfo entity = new CommentInfo();
			entity.setId(new Integer(id));
			entity = super.getFacade().getCommentInfoService().getCommentInfo(entity);

			if (null != entity) {
				BaseFiles baseFiles = new BaseFiles();
				baseFiles.setLink_id(new Integer(id));
				baseFiles.setLink_tab("CommentInfo");
				baseFiles.setIs_del(0);
				List<BaseFiles> baseFilesList = super.getFacade().getBaseFilesService().getBaseFilesList(baseFiles);
				if (null != baseFilesList && baseFilesList.size() > 0) {
					request.setAttribute("baseFilesList", baseFilesList);
				}
			}

			CommInfo commInfo = new CommInfo();
			commInfo.setId(entity.getLink_id());
			commInfo.setIs_del(0);
			commInfo = super.getFacade().getCommInfoService().getCommInfo(commInfo);
			if (null != commInfo) {
				request.setAttribute("pdInfoName", commInfo.getComm_name());
			}

			entity.setQueryString(super.serialize(request, "id", "method"));
			super.copyProperties(form, entity);

			return new ActionForward("/../manager/admin/CommentInfo/audit.jsp");
		} else {
			saveError(request, "errors.Integer", id);
			return mapping.findForward("list");
		}
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

		DynaBean dynaBean = (DynaBean) form;

		String link_id = (String) dynaBean.get("link_id");
		String mod_id = (String) dynaBean.get("mod_id");

		CommentInfo entity = new CommentInfo();

		super.copyProperties(entity, dynaBean);

		super.getFacade().getCommentInfoService().modifyCommentInfo(entity);
		saveMessage(request, "entity.updated");

		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&link_id=" + link_id);
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		return forward;
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;

		String id = (String) dynaBean.get("id");
		String[] pks = (String[]) dynaBean.get("pks");

		CommentInfo entity = new CommentInfo();
		if (!StringUtils.isBlank(id) && GenericValidator.isLong(id)) {
			entity.setId(new Integer(id));
			super.getFacade().getCommentInfoService().removeCommentInfo(entity);
			saveMessage(request, "entity.deleted");
		} else if (!ArrayUtils.isEmpty(pks)) {
			entity.getMap().put("pks", pks);
			getFacade().getCommentInfoService().removeCommentInfo(entity);
			saveMessage(request, "entity.deleted");
		}
		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(super.serialize(request, "id", "ids", "method")));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward deleteCommentSon(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;

		String id = (String) dynaBean.get("id");
		String par_id = (String) dynaBean.get("par_id");
		String[] pks = (String[]) dynaBean.get("pks");
		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		CommentInfoSon entity = new CommentInfoSon();
		entity.setIs_del(1);
		entity.setDel_date(new Date());
		entity.setDel_user_id(ui.getId());
		entity.setDel_user_name(ui.getUser_name());
		if (!StringUtils.isBlank(id) && GenericValidator.isLong(id)) {
			entity.setId(new Integer(id));
			super.getFacade().getCommentInfoSonService().modifyCommentInfoSon(entity);
			saveMessage(request, "entity.deleted");
		} else if (!ArrayUtils.isEmpty(pks)) {
			entity.getMap().put("pks", pks);
			getFacade().getCommentInfoSonService().modifyCommentInfoSon(entity);
			saveMessage(request, "entity.deleted");
		}
		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		// pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("/admin/CommentInfo.do?method=viewCommentSon");
		pathBuffer.append("&par_id=" + par_id);
		System.out.println("==pathBuffer:" + pathBuffer);
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);

		// end
		return forward;
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (GenericValidator.isLong(id)) {
			CommentInfo entity = new CommentInfo();
			entity.setId(new Integer(id));
			entity = getFacade().getCommentInfoService().getCommentInfo(entity);
			if (null == entity) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");
			}
			super.copyProperties(form, entity);
			return mapping.findForward("view");
		} else {
			this.saveError(request, "errors.Integer", new String[] { id });
			return mapping.findForward("list");
		}
	}

	public ActionForward unameInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		super.setBaseDataListToSession(10, request);// 用户类型
		super.setBaseDataListToSession(200, request);// 会员等级

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (GenericValidator.isLong(id)) {
			UserInfo userInfo = new UserInfo();
			userInfo.setId(new Integer(id));
			UserInfo entity = getFacade().getUserInfoService().getUserInfo(userInfo);
			if (null == entity) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");
			}

			if (null != entity.getOwn_entp_id()) {
				EntpInfo ei = new EntpInfo();
				ei.setId(entity.getOwn_entp_id());
				ei = super.getFacade().getEntpInfoService().getEntpInfo(ei);
				if (null != ei) {
					request.setAttribute("entpInfo", ei);
				}
			}
			super.copyProperties(form, entity);
			return new ActionForward("/admin/CommentInfo/view_uname_info.jsp");
		} else {
			this.saveError(request, "errors.Integer", new String[] { id });
			return mapping.findForward("list");
		}
	}
}
