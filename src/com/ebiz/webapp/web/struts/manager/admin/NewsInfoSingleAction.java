package com.ebiz.webapp.web.struts.manager.admin;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.ssi.web.struts.bean.UploadFile;
import com.ebiz.webapp.domain.NewsAttachment;
import com.ebiz.webapp.domain.NewsInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.FileTools;

/**
 * @author Wu,Yang
 * @version 2012-04-06
 */
public class NewsInfoSingleAction extends BaseAdminAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.add(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;

		Pager pager = (Pager) dynaBean.get("pager");

		NewsInfo entity = new NewsInfo();
		entity.setMod_id(Keys.lun_bo_ads_mod_id);
		entity.setIs_del(Integer.valueOf("0"));

		Integer recordCount = getFacade().getNewsInfoService().getNewsInfoCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<NewsInfo> newsInfoList = getFacade().getNewsInfoService().getNewsInfoPaginatedList(entity);

		request.setAttribute("entityList", newsInfoList);
		return mapping.findForward("list");

	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "1")) {
			return super.checkPopedomInvalid(request, response);
		}
		saveToken(request);
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		String mod_id = (String) dynaBean.get("mod_id");

		if (StringUtils.isNotBlank(mod_id)) {
			NewsInfo newsInfoentity = new NewsInfo();
			newsInfoentity.setMod_id((mod_id));
			newsInfoentity.setIs_del(0);
			newsInfoentity = getFacade().getNewsInfoService().getNewsInfo(newsInfoentity);
			if (null != newsInfoentity) {
				StringBuffer pathBuffer = new StringBuffer();
				pathBuffer.append("/admin/NewsInfoSingle.do?method=edit");
				pathBuffer.append("&mod_id=" + mod_id);
				pathBuffer.append("&id=" + newsInfoentity.getId());
				ActionForward forward = new ActionForward(pathBuffer.toString(), true);
				return forward;
			}
		}

		NewsInfo entity = new NewsInfo();
		entity.setMod_id(mod_id);
		entity.setOrder_value(0);
		entity.setIs_use_direct_uri(0);
		entity.setIs_use_invalid_date(0);
		entity.setInfo_state(0);
		entity.setPub_time(new Date());

		super.copyProperties(form, entity);
		if (StringUtils.isNotBlank(mod_id)
				&& (StringUtils.equals(mod_id, Keys.ads_mod_id) || StringUtils.equals(mod_id, Keys.lun_bo_ads_mod_id))) {
			return new ActionForward("/../manager/admin/NewsInfoSingle/form_ads.jsp");
		}
		return mapping.findForward("input");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (isCancelled(request)) {
			return mapping.findForward("input");
		}
		if (!isTokenValid(request)) {
			saveError(request, "errors.token");
			return mapping.findForward("input");
		}
		resetToken(request);

		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		DynaBean dynaBean = (DynaBean) form;
		String mod_id = (String) dynaBean.get("mod_id");

		// 修改、添加
		NewsInfo entity = new NewsInfo();
		entity.setMod_id(mod_id);
		super.copyProperties(entity, form);

		// 添加和修改
		if (null == entity.getTitle_is_strong()) {
			entity.setTitle_is_strong(0);
		}
		entity.setImage_path(null);

		List<UploadFile> uploadFileList = super.uploadFile(form, true, false, Keys.NEWS_INFO_IMAGE_SIZE);
		List<NewsAttachment> newsAttachmentList = new ArrayList<NewsAttachment>();
		NewsAttachment newsAttachment = null;
		for (UploadFile uploadFile : uploadFileList) {

			newsAttachment = new NewsAttachment();
			newsAttachment.setFile_name(uploadFile.getFileName());
			newsAttachment.setFile_type(uploadFile.getContentType());
			newsAttachment.setFile_size(Integer.valueOf(uploadFile.getFileSize()));
			newsAttachment.setSave_path(uploadFile.getFileSavePath());
			newsAttachment.setSave_name(uploadFile.getFileSaveName());
			newsAttachment.setIs_del(0);
			newsAttachment.setLink_tab(uploadFile.getFormName());
			if ("image_path".equalsIgnoreCase(uploadFile.getFormName())) {
				entity.setImage_path(uploadFile.getFileSavePath());
			} else {
				newsAttachmentList.add(newsAttachment);
			}

		}
		entity.setNewsAttachmentList(newsAttachmentList);

		if (null == entity.getId()) {
			Date sysDate = new Date();
			entity.setView_count(Integer.valueOf("0"));
			entity.setIs_del(0);
			entity.setAdd_time(sysDate);
			entity.setAdd_uid(new Integer(new Integer(ui.getId())));
			entity.setUpdate_time(sysDate);
			entity.setUpdate_uid(new Integer(ui.getId()));
			entity.setUuid(UUID.randomUUID().toString());
			getFacade().getNewsInfoService().createNewsInfo(entity);
			saveMessage(request, "entity.inerted");

		} else {
			NewsInfo newsInfo = new NewsInfo();
			newsInfo.setId(entity.getId());
			entity.setUpdate_time(new Date());
			entity.setUpdate_uid(new Integer(ui.getId()));
			newsInfo = getFacade().getNewsInfoService().getNewsInfo(newsInfo);

			entity.getMap().put("update_content", "true");
			entity.getMap().put("update_attachment", "true");
			getFacade().getNewsInfoService().modifyNewsInfo(entity);
			saveMessage(request, "entity.updated");

		}
		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append("/admin/NewsInfoSingle.do?method=edit");
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&id=" + entity.getId());
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
		logger.info("======={}", pathBuffer.toString());
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "2")) {
			return super.checkPopedomInvalid(request, response);
		}
		saveToken(request);
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String mod_id = (String) dynaBean.get("mod_id");
		if (GenericValidator.isLong(id)) {
			NewsInfo newsInfo = new NewsInfo();
			newsInfo.setId(Integer.valueOf(id));
			newsInfo = getFacade().getNewsInfoService().getNewsInfo(newsInfo);

			NewsAttachment attachment = new NewsAttachment();
			attachment.setLink_id(Integer.valueOf(id));
			request.setAttribute("attachmentList",
					getFacade().getNewsAttachmentService().getNewsAttachmentList(attachment));
			if (null == newsInfo) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");
			}
			// the line below is added for pagination
			newsInfo.setQueryString(super.serialize(request, "id", "method"));
			// end
			super.copyProperties(form, newsInfo);

			if (StringUtils.isNotBlank(mod_id)
					&& (StringUtils.equals(mod_id, Keys.ads_mod_id) || StringUtils.equals(mod_id,
							Keys.lun_bo_ads_mod_id))) {
				return new ActionForward("/../manager/admin/NewsInfoSingle/form_ads.jsp");
			}
			return mapping.findForward("input");
		} else {
			saveError(request, "errors.Integer", id);
			return mapping.findForward("list");
		}
	}

	public ActionForward deleteFile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String file_path = (String) dynaBean.get("file_path");

		if (StringUtils.isBlank(id) || StringUtils.isBlank(file_path)) {
			super.renderText(response, "fail");
			return null;
		}
		logger.info("id:{}", id);
		logger.info("file_path:{}", file_path);

		NewsAttachment entity = new NewsAttachment();
		entity.setId(Integer.valueOf(id));
		getFacade().getNewsAttachmentService().removeNewsAttachment(entity);

		String ctx = request.getSession().getServletContext().getRealPath(String.valueOf(File.separatorChar));
		String realFilePath = ctx + file_path;
		FileTools.deleteFile(realFilePath);

		super.renderText(response, "success");
		return null;
	}

	public ActionForward saveLunbo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
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

		String is_audit = (String) dynaBean.get("is_audit");// 区别审核(只需要修改一个状态) 和 修改、添加
		NewsInfo entity = new NewsInfo();
		entity.setMod_id(mod_id);
		super.copyProperties(entity, form);
		if (StringUtils.isBlank(is_audit)) {
			// 添加和修改
			if (null == entity.getTitle_is_strong()) {
				entity.setTitle_is_strong(0);
			}
			entity.setImage_path(null);

			List<UploadFile> uploadFileList = super.uploadFile(form, true, true, Keys.NEWS_INFO_IMAGE_SIZE);
			List<NewsAttachment> newsAttachmentList = new ArrayList<NewsAttachment>();
			NewsAttachment newsAttachment = null;
			for (UploadFile uploadFile : uploadFileList) {

				newsAttachment = new NewsAttachment();
				newsAttachment.setFile_name(uploadFile.getFileName());
				newsAttachment.setFile_type(uploadFile.getContentType());
				newsAttachment.setFile_size(Integer.valueOf(uploadFile.getFileSize()));
				newsAttachment.setSave_path(uploadFile.getFileSavePath());
				newsAttachment.setSave_name(uploadFile.getFileSaveName());
				newsAttachment.setIs_del(0);
				newsAttachment.setLink_tab(uploadFile.getFormName());
				if ("image_path".equalsIgnoreCase(uploadFile.getFormName())) {
					entity.setImage_path(uploadFile.getFileSavePath());
				} else {
					newsAttachmentList.add(newsAttachment);
				}

			}
			entity.setNewsAttachmentList(newsAttachmentList);

			if (StringUtils.isBlank(entity.getSummary())) {
				if (StringUtils.isNotBlank(entity.getContent())) {
					Whitelist user_content_filter = Whitelist.none();
					String summary = Jsoup.clean(entity.getContent(), user_content_filter);
					summary = StringUtils.substring(Jsoup.clean(entity.getContent(), user_content_filter), 0, 100);
					summary = StringUtils.replace(summary, "&nbsp;", " ");
					entity.setSummary(summary);
				}
			}

			if (null == entity.getId()) {
				Date sysDate = new Date();
				entity.setView_count(Integer.valueOf("0"));
				entity.setIs_del(0);
				entity.setAdd_time(sysDate);
				entity.setAdd_uid(new Integer(new Integer(ui.getId())));
				entity.setUpdate_time(sysDate);
				entity.setUpdate_uid(new Integer(ui.getId()));
				entity.setUuid(UUID.randomUUID().toString());
				getFacade().getNewsInfoService().createNewsInfo(entity);
				saveMessage(request, "entity.inerted");

			} else {
				entity.setUpdate_time(new Date());
				entity.setUpdate_uid(new Integer(ui.getId()));

				entity.getMap().put("update_content", "true");
				entity.getMap().put("update_attachment", "true");
				getFacade().getNewsInfoService().modifyNewsInfo(entity);
				saveMessage(request, "entity.updated");

			}
		} else {
			// 审核，只修改状态
			entity.setUpdate_time(new Date());
			entity.setUpdate_uid(new Integer(ui.getId()));

			getFacade().getNewsInfoService().modifyNewsInfo(entity);
			saveMessage(request, "entity.updated");
		}
		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}
}