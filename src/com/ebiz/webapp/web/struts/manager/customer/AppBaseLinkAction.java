package com.ebiz.webapp.web.struts.manager.customer;

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
import com.ebiz.ssi.web.struts.bean.UploadFile;
import com.ebiz.webapp.domain.BaseComminfoTags;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.MBaseLink;
import com.ebiz.webapp.domain.ServiceCenterInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;

/**
 * 轮播图管理后台
 * 
 * @author Li,Yuan
 * @version 2013-05-28
 */
public class AppBaseLinkAction extends BaseCustomerAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		saveToken(request);
		if (!StringUtils.equalsIgnoreCase(request.getMethod(), "POST")) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		BaseComminfoTags bTags = new BaseComminfoTags();
		bTags.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		List<BaseComminfoTags> bList = getFacade().getBaseComminfoTagsService().getBaseComminfoTagsList(bTags);
		if (bList != null && bList.size() > 0) {
			request.setAttribute("bList", bList);
		}
		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		String link_type = (String) dynaBean.get("link_type");
		dynaBean.set("order_value", 0);
		if (StringUtils.isNotBlank(link_type)) {
			int link_type_int = Integer.valueOf(link_type);

			if ((link_type_int == 1020) || (link_type_int == 1511) || (link_type_int == 1512)
					|| (link_type_int == 1521) || (link_type_int == 1522) || (link_type_int == 1531)
					|| (link_type_int == 1532) || (link_type_int == 1541) || (link_type_int == 1542)
					|| (link_type_int == 1551) || (link_type_int == 1552) || (link_type_int == 1561)
					|| (link_type_int == 1562) || (link_type_int == 1571) || (link_type_int == 1572)
					|| (link_type_int == 1600) || (link_type_int == 40)) {
				return new ActionForward("/customer/AppBaseLink/formPic.jsp");
			} else if ((link_type_int == 1500)) {
				return new ActionForward("/customer/AppBaseLink/formText.jsp");
			} else {
				return new ActionForward("/customer/AppBaseLink/formPic.jsp");
			}
		}

		return null;
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		String is_del = (String) dynaBean.get("is_del");

		String title_like = (String) dynaBean.get("title_like");
		String link_type = (String) dynaBean.get("link_type");

		Pager pager = (Pager) dynaBean.get("pager");

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}

		MBaseLink entity = new MBaseLink();
		entity.setLink_type(Integer.valueOf(link_type));
		entity.getMap().put("title_like", title_like);
		entity.setAdd_uid(ui.getId());

		if (null == is_del) {
			entity.setIs_del(Integer.valueOf("0"));
			dynaBean.set("is_del", "0");
		}

		super.copyProperties(entity, form);

		Integer recordCount = getFacade().getMBaseLinkService().getMBaseLinkCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<MBaseLink> MBaseLinkList = getFacade().getMBaseLinkService().getMBaseLinkPaginatedList(entity);

		// if (MBaseLinkList != null && MBaseLinkList.size() > 0) {
		// for (MBaseLink temp : MBaseLinkList) {
		// BaseComminfoTags bTags = new BaseComminfoTags();
		// bTags.setId(Integer.valueOf(temp.getPre_varchar2()));
		// bTags.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		// bTags = getFacade().getBaseComminfoTagsService().getBaseComminfoTags(bTags);
		// if (bTags != null) {
		// temp.getMap().put("tag_name", bTags.getTag_name());
		// }
		// }
		//
		// }

		request.setAttribute("entityList", MBaseLinkList);
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
		String link_type = (String) dynaBean.get("link_type");
		String cls_id = (String) dynaBean.get("cls_id");
		String pre_number = (String) dynaBean.get("pre_number");
		String link_url2 = (String) dynaBean.get("link_url2");
		String link_url3 = (String) dynaBean.get("link_url3");
		// String cls_name = (String) dynaBean.get("cls_name");
		MBaseLink entity = new MBaseLink();
		super.copyProperties(entity, form);
		// 添加和修改
		entity.setImage_path(null);

		if (link_type.equals("1000")) {// 如果是县域轮播图需要插入link_id
			ServiceCenterInfo sInfo = new ServiceCenterInfo();
			sInfo.setAdd_user_id(ui.getId());
			sInfo = super.getFacade().getServiceCenterInfoService().getServiceCenterInfo(sInfo);
			if (sInfo == null) {
				super.showMsgForCustomer(request, response, "县域不存在");
			}
			entity.setLink_id(sInfo.getId());
		}

		if (StringUtils.isNotBlank(pre_number)) {
			if (pre_number.equals("2") && StringUtils.isNotBlank(link_url2)) {
				entity.setLink_url(link_url2);
			}
			if (pre_number.equals("4") && StringUtils.isNotBlank(link_url3)) {
				entity.setLink_url("http://www.99renyi.com/m/MEntpInfo.do?method=list&tag_id=" + link_url3);
				// entity.setLink_url("http://" + Keys.app_domain + "/m/MEntpInfo.do?method=list&tag_id=" + link_url3);
				entity.setPre_varchar2(link_url3);
			}
		}

		List<UploadFile> uploadFileList = super.uploadFile(form, true, false, Keys.NEWS_INFO_IMAGE_SIZE);
		for (UploadFile uploadFile : uploadFileList) {

			if ("image_path".equalsIgnoreCase(uploadFile.getFormName())) {
				entity.setImage_path(uploadFile.getFileSavePath());
			}
		}
		if (StringUtils.isNotBlank(cls_id)) {
			entity.setContent(cls_id);
		}

		if (StringUtils.isNotBlank(link_url2) && pre_number.equals("2"))
			entity.setLink_url(link_url2);

		logger.info("======加粗======" + entity.getTitle_is_strong());
		if (null == entity.getTitle_is_strong()) {
			entity.setTitle_is_strong(0);
		}

		if (null == entity.getId()) {
			Date sysDate = new Date();
			entity.setIs_del(0);
			entity.setAdd_time(sysDate);
			entity.setAdd_uid(new Integer(new Integer(ui.getId())));
			getFacade().getMBaseLinkService().createMBaseLink(entity);
			saveMessage(request, "entity.inerted");

		} else {
			entity.setUpdate_time(new Date());
			entity.setUpdate_uid(new Integer(ui.getId()));

			getFacade().getMBaseLinkService().modifyMBaseLink(entity);
			saveMessage(request, "entity.updated");

		}

		if (link_type.equals("2000")) {
			super.renderJavaScript(response, "window.parent.location.href='Home.do?mod_id=1000900001'");
			return null;
		} else {
			// the line below is added for pagination
			StringBuffer pathBuffer = new StringBuffer();
			pathBuffer.append(mapping.findForward("success").getPath());
			pathBuffer.append("&mod_id=" + mod_id);
			pathBuffer.append("&link_type=" + link_type);
			pathBuffer.append("&");
			pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
			ActionForward forward = new ActionForward(pathBuffer.toString(), true);
			// end
			return forward;
		}
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (!StringUtils.equalsIgnoreCase(request.getMethod(), "POST")) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String[] pks = (String[]) dynaBean.get("pks");
		String mod_id = (String) dynaBean.get("mod_id");
		String link_type = (String) dynaBean.get("link_type");
		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		MBaseLink entity = new MBaseLink();
		entity.setUpdate_time(new Date());
		entity.setUpdate_uid(new Integer(ui.getId()));
		entity.setDel_time(new Date());
		entity.setDel_uid(new Integer(ui.getId()));
		entity.setIs_del(1);

		if (!StringUtils.isBlank(id) && GenericValidator.isLong(id)) {

			entity.setId(Integer.valueOf(id));
			getFacade().getMBaseLinkService().modifyMBaseLink(entity);

			saveMessage(request, "entity.deleted");

		} else if (!ArrayUtils.isEmpty(pks)) {
			entity.getMap().put("pks", pks);
			getFacade().getMBaseLinkService().modifyMBaseLink(entity);
			saveMessage(request, "entity.deleted");

		}
		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&link_type=" + link_type);
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(super.serialize(request, "id", "method")));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		saveToken(request);
		if (!StringUtils.equalsIgnoreCase(request.getMethod(), "POST")) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String link_type = (String) dynaBean.get("link_type");
		if (GenericValidator.isLong(id)) {
			BaseComminfoTags bTags = new BaseComminfoTags();
			bTags.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
			List<BaseComminfoTags> bList = getFacade().getBaseComminfoTagsService().getBaseComminfoTagsList(bTags);
			if (bList != null && bList.size() > 0) {
				request.setAttribute("bList", bList);
			}
			MBaseLink MBaseLink = new MBaseLink();
			MBaseLink.setId(Integer.valueOf(id));
			MBaseLink = getFacade().getMBaseLinkService().getMBaseLink(MBaseLink);

			if (null == MBaseLink) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");
			}

			super.copyProperties(form, MBaseLink);
			if (StringUtils.isNotBlank(link_type)) {
				int link_type_int = Integer.valueOf(link_type);

				if ((link_type_int == 1020) || (link_type_int == 1511) || (link_type_int == 1512)
						|| (link_type_int == 1521) || (link_type_int == 1522) || (link_type_int == 1531)
						|| (link_type_int == 1532) || (link_type_int == 1541) || (link_type_int == 1542)
						|| (link_type_int == 1551) || (link_type_int == 1552) || (link_type_int == 1561)
						|| (link_type_int == 1562) || (link_type_int == 1571) || (link_type_int == 1572)
						|| (link_type_int == 1600) || (link_type_int == 40)) {
					return new ActionForward("/customer/AppBaseLink/formPic.jsp");
				} else if ((link_type_int == 1500)) {
					return new ActionForward("/customer/AppBaseLink/formText.jsp");
				} else {
					return new ActionForward("/customer/AppBaseLink/formPic.jsp");
				}

			}
			return mapping.findForward("input");
		} else {
			saveError(request, "errors.Integer", id);
			return mapping.findForward("list");
		}
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		if (GenericValidator.isInt(id)) {
			MBaseLink MBaseLink = new MBaseLink();
			MBaseLink.setId(Integer.valueOf(id));
			MBaseLink = getFacade().getMBaseLinkService().getMBaseLink(MBaseLink);

			if (null == MBaseLink) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");
			}
			super.copyProperties(form, MBaseLink);
			return mapping.findForward("view");
		} else {
			saveError(request, "errors.Integer", id);
			return mapping.findForward("list");
		}
	}

	public ActionForward listCommInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String comm_name_like = (String) dynaBean.get("comm_name_like");

		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		CommInfo entity = new CommInfo();
		super.copyProperties(entity, form);
		entity.setIs_del(0);
		entity.setAudit_state(1);
		entity.setIs_sell(1);
		entity.setComm_type(Keys.CommType.COMM_TYPE_2.getIndex());
		entity.getMap().put("not_out_sell_time", "true");

		entity.getMap().put("comm_name_like", comm_name_like);

		Integer recordCount = getFacade().getCommInfoService().getCommInfoCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<CommInfo> entityList = super.getFacade().getCommInfoService().getCommInfoPaginatedList(entity);
		request.setAttribute("entityList", entityList);

		return new ActionForward("/../manager/customer/AppBaseLink/choose_commInfo.jsp");

	}

}
