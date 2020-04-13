package com.ebiz.webapp.web.struts.manager.admin;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import com.alibaba.fastjson.JSONObject;
import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.ssi.web.struts.bean.UploadFile;
import com.ebiz.webapp.domain.NewsAttachment;
import com.ebiz.webapp.domain.NewsInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.FileTools;

import push.AppPush;


/**
 * @author Wu,Yang
 * @version 2016-5-01
 */
public class PushInfoAction extends BaseAdminAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "1")) {
			return super.checkPopedomInvalid(request, response);
		}
		saveToken(request);
		if (!StringUtils.equalsIgnoreCase(request.getMethod(), "POST")) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;

		String mod_id = (String) dynaBean.get("mod_id");
		NewsInfo entity = new NewsInfo();
		entity.setMod_id(mod_id);
		entity.setOrder_value(0);
		entity.setIs_use_direct_uri(0);
		entity.setIs_use_invalid_date(0);
		entity.setInfo_state(0);
		entity.setPub_time(new Date());

		super.copyProperties(form, entity);

		return mapping.findForward("input");
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "0")) {
			return super.checkPopedomInvalid(request, response);
		}
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		String is_del = (String) dynaBean.get("is_del");

		String pub_state = (String) dynaBean.get("pub_state");
		String mod_id = (String) dynaBean.get("mod_id");
		String st_pub_date = (String) dynaBean.get("st_pub_date");
		String en_pub_date = (String) dynaBean.get("en_pub_date");
		String title_like = (String) dynaBean.get("title_like");

		Pager pager = (Pager) dynaBean.get("pager");

		NewsInfo entity = new NewsInfo();
		entity.setMod_id(mod_id);
		entity.getMap().put("title_like", title_like);

		if (null == is_del) {
			entity.setIs_del(Integer.valueOf("0"));
			dynaBean.set("is_del", "0");
		}

		if (null != pub_state) {
			if ("0".equalsIgnoreCase(pub_state)) {
				entity.getMap().put("no_pub", "0");
			} else if ("1".equalsIgnoreCase(pub_state)) {
				entity.getMap().put("is_pub", "0");
			}
		}
		// else {
		// entity.getMap().put("is_pub", "0");
		// dynaBean.set("pub_state", "1");
		// }

		entity.getMap().put("st_pub_date", st_pub_date);
		entity.getMap().put("en_pub_date", en_pub_date);
		// entity.getMap().put("invalid_date", "invalid_date");
		super.copyProperties(entity, form);

		Integer recordCount = getFacade().getNewsInfoService().getNewsInfoCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<NewsInfo> newsInfoList = getFacade().getNewsInfoService().getNewsInfoPaginatedList(entity);

		request.setAttribute("entityList", newsInfoList);
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

			// 如果摘要为空，则从新闻内容里面截取
			if (StringUtils.isBlank(entity.getSummary())) {
				if (StringUtils.isNotBlank(entity.getContent())) {
					Whitelist user_content_filter = Whitelist.none();
					String summary = Jsoup.clean(entity.getContent(), user_content_filter);
					summary = StringUtils.substring(Jsoup.clean(entity.getContent(), user_content_filter), 0, 50);
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

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "4")) {
			return super.checkPopedomInvalid(request, response);
		}
		if (!StringUtils.equalsIgnoreCase(request.getMethod(), "POST")) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String[] pks = (String[]) dynaBean.get("pks");
		String mod_id = (String) dynaBean.get("mod_id");

		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		NewsInfo entity = new NewsInfo();
		entity.setUpdate_time(new Date());
		entity.setUpdate_uid(new Integer(ui.getId()));
		entity.setDel_time(new Date());
		entity.setDel_uid(new Integer(ui.getId()));
		// entity.setIs_del(1);

		if (!StringUtils.isBlank(id) && GenericValidator.isLong(id)) {

			entity.setId(Integer.valueOf(id));
			// entity.setInfo_state(-1);
			getFacade().getNewsInfoService().removeNewsInfo(entity);

			saveMessage(request, "entity.deleted");

		} else if (!ArrayUtils.isEmpty(pks)) {
			entity.getMap().put("pks", pks);
			// entity.setInfo_state(-1);
			getFacade().getNewsInfoService().removeNewsInfo(entity);
			saveMessage(request, "entity.deleted");

		}
		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(super.serialize(request, "id", "method")));
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
		if (!StringUtils.equalsIgnoreCase(request.getMethod(), "POST")) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
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
			return mapping.findForward("input");
		} else {
			saveError(request, "errors.Integer", id);
			return mapping.findForward("list");
		}
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "0")) {
			return super.checkPopedomInvalid(request, response);
		}
		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		if (GenericValidator.isInt(id)) {
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
			super.copyProperties(form, newsInfo);
			return mapping.findForward("view");
		} else {
			saveError(request, "errors.Integer", id);
			return mapping.findForward("list");
		}
	}

	@Override
	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if ((ip == null) || (ip.length() == 0) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if ((ip == null) || (ip.length() == 0) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if ((ip == null) || (ip.length() == 0) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public String formatDate(Date date) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
		return f.format(date);
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

	public ActionForward appPush(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String mod_id = (String) dynaBean.get("mod_id");
		String platform = (String) dynaBean.get("platform");
		JSONObject json = new JSONObject();
		if (StringUtils.isBlank(id) || StringUtils.isBlank(mod_id)) {
			json.put("code", 0);
			json.put("msg", "推送失败，参数有误，请联系管理员！");
			super.renderJson(response, json.toJSONString());
			return null;
		}
		logger.info("id:{}", id);
		logger.info("mod_id:{}", mod_id);

		NewsInfo newsInfo = new NewsInfo();
		newsInfo.setId(Integer.valueOf(id));
		newsInfo = getFacade().getNewsInfoService().getNewsInfo(newsInfo);
		if (null == newsInfo) {
			json.put("code", 0);
			json.put("msg", "推送失败，新闻不存在！");
			super.renderJson(response, json.toJSONString());
			return null;
		}
		String ctx = super.getCtxPath(request);
		String newsUrl = ctx + "/m/MNewsInfo.do?method=view&uuid=" + newsInfo.getUuid();

		boolean is_product = true;// TODO 正式发布的时候改成ture
		UserInfo ui = new UserInfo();
		if (StringUtils.isNotBlank(platform)) {
			ui.setDevice_token_app(platform);
		}
		ui.setIs_del(0);
		if (!is_product) {
			ui.setId(858);// 只推送给wuyang
		}
		ui.getMap().put("device_token_not_empty", "true");
		List<UserInfo> uiList = getFacade().getUserInfoService().getUserInfoList(ui);
		try {
			for (UserInfo uitemp : uiList) {
				if (uitemp.getDevice_token_app().equals("android")) {
					AppPush.sendAndroidUnicastForNews(uitemp.getDevice_token(), newsInfo.getTitle(),
							newsInfo.getSummary(), newsUrl, is_product);
				} else if (uitemp.getDevice_token_app().equals("ios")) {
					AppPush.sendIOSUnicastForNews(uitemp.getDevice_token(), newsInfo.getTitle(), newsUrl, is_product);
				}
			}
			NewsInfo newsInfoUpdatePush = new NewsInfo();
			newsInfoUpdatePush.setId(Integer.valueOf(id));
			newsInfoUpdatePush.setIs_push(1);
			getFacade().getNewsInfoService().modifyNewsInfo(newsInfoUpdatePush);

		} catch (Exception e) {
			json.put("code", 0);
			json.put("msg", "推送失败:" + e.getMessage());
			super.renderJson(response, json.toJSONString());
			return null;
		}

		json.put("code", 1);
		json.put("msg", "推送成功！");
		super.renderJson(response, json.toJSONString());
		return null;
	}
}