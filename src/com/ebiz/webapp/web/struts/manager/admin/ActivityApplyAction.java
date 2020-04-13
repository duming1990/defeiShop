package com.ebiz.webapp.web.struts.manager.admin;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipOutputStream;

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
import com.ebiz.webapp.domain.ActivityApply;
import com.ebiz.webapp.domain.ActivityApplyComm;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.NewsAttachment;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.EncryptUtilsV2;
import com.ebiz.webapp.web.util.ZipUtils;

public class ActivityApplyAction extends BaseAdminAction {
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
		ActivityApply entity = new ActivityApply();

		super.copyProperties(form, entity);

		return mapping.findForward("input");
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		request.setAttribute("thisAction", "ActivityApply");
		if (null == super.checkUserModPopeDom(form, request, "0")) {
			return super.checkPopedomInvalid(request, response);
		}
		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;

		String st_start_date = (String) dynaBean.get("st_pub_date");
		String en_end_date = (String) dynaBean.get("en_end_date");
		String title_like = (String) dynaBean.get("title_like_");

		Pager pager = (Pager) dynaBean.get("pager");

		ActivityApply entity = new ActivityApply();
		entity.getMap().put("title_like_1", title_like);

		entity.getMap().put("st_start_date", st_start_date);
		entity.getMap().put("en_end_date", en_end_date);
		super.copyProperties(entity, form);

		Integer recordCount = getFacade().getActivityApplyService().getActivityApplyCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<ActivityApply> newsInfoList = getFacade().getActivityApplyService().getActivityApplyPaginatedList(entity);
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
		String audit_state = (String) dynaBean.get("audit_state");// 区别审核(只需要修改一个状态) 和 修改、添加
		ActivityApply entity = new ActivityApply();
		super.copyProperties(entity, form);
		if (StringUtils.isBlank(is_audit)) {

			if (null == entity.getId()) {
				entity.setAdd_date(new Date());
				getFacade().getActivityApplyService().createActivityApply(entity);
				saveMessage(request, "entity.inerted");

			} else {
				getFacade().getActivityApplyService().modifyActivityApply(entity);
				saveMessage(request, "entity.updated");

			}
		} else {

			if (1 == Integer.valueOf(audit_state)) {// 审核通过
				logger.info("====审核通过===");
				entity.getMap().put("send_audit_msg", 1);
				ActivityApply a = new ActivityApply();
				a.setId(entity.getId());
				a = getFacade().getActivityApplyService().getActivityApply(a);

				String ctx = "";
				if (super.isLocal(request)) {
					ctx = super.getCtxPath(request, true);
				} else {
					ctx = super.getCtxPath(request, false);
				}
				logger.info("===ctx:" + ctx);
				Integer id = entity.getId();

				String Path = session.getServletContext().getRealPath(String.valueOf(File.separatorChar));// 文件绝对路径
				String LogoFile = Path + "/styles/imagesPublic/user_header.png";

				EntpInfo entpInfo = new EntpInfo();
				entpInfo.setId(a.getEntp_id());
				entpInfo = super.getFacade().getEntpInfoService().getEntpInfo(entpInfo);
				if (null != entpInfo && StringUtils.isNotBlank(entpInfo.getEntp_logo())) {
					File tempFile = new File(Path + entpInfo.getEntp_logo());
					if (tempFile.exists()) {
						LogoFile = Path + entpInfo.getEntp_logo();
					}
				}
				String Jump_path = ctx + "/m/MActivity.do?id=" + id;// 二维码跳转的路径
				String name = entpInfo.getEntp_name();// 底部添加的文字
				String uploadDir = "files" + File.separator + "Activity";// 文件夹的名称
				String ctxDir = getServlet().getServletContext().getRealPath(File.separator);
				if (!ctxDir.endsWith(File.separator)) {
					ctxDir = ctxDir + File.separator;
				}

				File savePath = new File(ctxDir + uploadDir);
				if (!savePath.exists()) {
					savePath.mkdirs();
				}

				String imgPath = ctxDir + uploadDir + File.separator + id + ".png";
				File imgFile = new File(imgPath);

				if (imgFile.exists()) {
					imgFile.delete();
				}

				super.createQrcode(Path, Jump_path, LogoFile, name, uploadDir, id);// 生成二维码
				entity.setQrcode_path(uploadDir + File.separator + id + ".png");
				entity.getMap().put("isnert_default_comm", "true");
			}
			// 审核，只修改状态
			getFacade().getActivityApplyService().modifyActivityApply(entity);
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

		ActivityApply entity = new ActivityApply();

		// entity.setIs_del(1);

		if (!StringUtils.isBlank(id) && GenericValidator.isLong(id)) {

			entity.setId(Integer.valueOf(id));
			getFacade().getActivityApplyService().removeActivityApply(entity);

			saveMessage(request, "entity.deleted");

		} else if (!ArrayUtils.isEmpty(pks)) {
			entity.getMap().put("pks", pks);
			getFacade().getActivityApplyService().removeActivityApply(entity);
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
		String mod_id = (String) dynaBean.get("mod_id");
		if (GenericValidator.isLong(id)) {
			ActivityApply newsInfo = new ActivityApply();
			newsInfo.setId(Integer.valueOf(id));
			newsInfo = getFacade().getActivityApplyService().getActivityApply(newsInfo);

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
			ActivityApply newsInfo = new ActivityApply();
			newsInfo.setId(Integer.valueOf(id));
			newsInfo = getFacade().getActivityApplyService().getActivityApply(newsInfo);

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

	public ActionForward audit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// if (null == super.checkUserModPopeDom(form, request, "8")) {
		// return super.checkPopedomInvalid(request, response);
		// }
		saveToken(request);
		if (!StringUtils.equalsIgnoreCase(request.getMethod(), "POST")) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String mod_id = (String) dynaBean.get("mod_id");
		if (!GenericValidator.isLong(id)) {
			saveError(request, "errors.Integer", id);
			return mapping.findForward("list");
		}

		ActivityApply entity = new ActivityApply();
		entity.setId(Integer.valueOf(id));
		entity = getFacade().getActivityApplyService().getActivityApply(entity);
		if (null == entity) {
			saveMessage(request, "entity.missing");
			return mapping.findForward("list");
		}

		ActivityApplyComm a = new ActivityApplyComm();
		a.setActivity_id(entity.getLink_id());
		a.setActivity_apply_id(entity.getId());
		a.getMap().put("audit", true);
		List<ActivityApplyComm> list = getFacade().getActivityApplyCommService().selectActivityApplyCommAuditList(a);
		request.setAttribute("list", list);

		// the line below is added for pagination
		entity.setQueryString(super.serialize(request, "id", "method"));
		// end
		super.copyProperties(form, entity);
		return new ActionForward("/../manager/admin/ActivityApply/audit.jsp");
	}

	public ActionForward downloadPoorInfoQrcode(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(false);
		DynaBean dynaBean = (DynaBean) form;
		String en_end_date = (String) dynaBean.get("en_end_date");
		String st_start_date = (String) dynaBean.get("st_start_date");
		String title_like = (String) dynaBean.get("title_like");

		ActivityApply entity = new ActivityApply();
		entity.getMap().put("title_like", title_like);

		entity.getMap().put("st_start_date", st_start_date);
		entity.getMap().put("en_end_date", en_end_date);
		super.copyProperties(entity, form);

		entity.setAudit_state(1);
		List<ActivityApply> entityList = super.getFacade().getActivityApplyService().getActivityApplyList(entity);
		String realPath = session.getServletContext().getRealPath(String.valueOf(File.separatorChar));
		String fname = EncryptUtilsV2.encodingFileName("线下活动商家二维码导出_日期" + sdFormat_ymd.format(new Date()) + ".zip");
		response.setContentType("APPLICATION/OCTET-STREAM");
		response.setHeader("Content-Disposition", "attachment; filename=" + fname);

		if (null != entityList && entityList.size() > 0) {
			ZipOutputStream zipout = new ZipOutputStream(response.getOutputStream());
			File[] files = new File[entityList.size()];
			int i = 0;
			for (ActivityApply temp : entityList) {
				if (null != temp.getQrcode_path()) {
					File savePath = new File(realPath + temp.getQrcode_path());
					if (savePath.exists())
						files[i] = savePath;
				}
				i++;
			}
			ZipUtils.zipFile(files, "", zipout);
			zipout.flush();
			zipout.close();
			return null;
		} else {
			return null;
		}
	}

}