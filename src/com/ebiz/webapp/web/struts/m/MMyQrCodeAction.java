package com.ebiz.webapp.web.struts.m;

import java.io.File;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.struts.manager.customer.BaseCustomerAction;
import com.ebiz.webapp.web.util.ZxingUtils;

/**
 * @author Wu,Yang
 * @version 2010-8-24
 */
public class MMyQrCodeAction extends BaseCustomerAction {

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
			return super.showTipMsg(mapping, form, request, response, msg);
		}
		Integer id = ui.getId();
		String uploadDir = "files" + File.separator + "qrcode";
		String uploadDir_share = "files/qrcode";

		String ctxDir = getServlet().getServletContext().getRealPath(File.separator);
		if (!ctxDir.endsWith(File.separator)) {
			ctxDir = ctxDir + File.separator;
		}
		File savePath = new File(ctxDir + uploadDir);

		logger.info("===> save path is: {}", savePath);
		if (!savePath.exists()) {
			savePath.mkdirs();
		}
		String ctx = super.getCtxPath(request, false);

		String imgPath = ctxDir + uploadDir + File.separator + id + ".png";

		String share_url = ctx + "/share-" + id + ".html";
		// String url_qrcode = ctx + "/share/qrcode-" + id + ".html";
		File imgFile = new File(imgPath);
		logger.info("==share_url:{}", share_url);
		if (!imgFile.exists()) {
			ZxingUtils.encodeQrcode(share_url, imgPath, null);
		}
		String fileTruePath = uploadDir + "/" + id + ".png";
		fileTruePath = StringUtils.replace(fileTruePath, "\\", "/");
		request.setAttribute("fileTruePath", fileTruePath);

		request.setAttribute("share_url", share_url);

		String share_img = ctx + "/" + uploadDir_share + "/" + id + ".png";
		share_img = StringUtils.replace(share_img, "\\", "/");
		request.setAttribute("share_img", share_img);

		request.setAttribute("time", new Date().getTime());
		request.setAttribute("is_weixin", super.isWeixin(request));
		// request.setAttribute("url_qrcode", url_qrcode);
		request.setAttribute("header_title", "我的专属链接");

		// super.setJsApiTicketRetrunParamToSession(request);

		request.setAttribute("is_app", super.isApp(request));
		return mapping.findForward("list");
	}

	public ActionForward myRegisterCode(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return super.showTipMsg(mapping, form, request, response, msg);
		}
		Integer id = ui.getId();

		if (null != ui.getIs_village() && ui.getIs_village() == 1) {
			// 驿站找出user_type = 4的驿站用户账号
			UserInfo villageUser = new UserInfo();
			villageUser.setIs_village(1);
			villageUser.setUser_type(Keys.UserType.USER_TYPE_4.getIndex());
			villageUser.setIs_del(0);
			villageUser.setOwn_village_id(ui.getOwn_village_id());
			villageUser = getFacade().getUserInfoService().getUserInfo(villageUser);
			id = villageUser.getId();
		}

		String uploadDir = "files" + File.separator + "qrcode";
		String uploadDir_share = "files/qrcode";

		String ctxDir = getServlet().getServletContext().getRealPath(File.separator);
		if (!ctxDir.endsWith(File.separator)) {
			ctxDir = ctxDir + File.separator;
		}
		File savePath = new File(ctxDir + uploadDir);

		logger.info("===> save path is: {}", savePath);
		if (!savePath.exists()) {
			savePath.mkdirs();
		}
		String ctx = super.getCtxPath(request, false);

		String imgPath = ctxDir + uploadDir + File.separator + id + ".png";

		String share_url = ctx + "/share-" + id + ".html";
		// String url_qrcode = ctx + "/share/qrcode-" + id + ".html";
		File imgFile = new File(imgPath);
		logger.info("==share_url:{}", share_url);
		if (!imgFile.exists()) {
			ZxingUtils.encodeQrcode(share_url, imgPath, null);
		}
		String fileTruePath = uploadDir + "/" + id + ".png";
		fileTruePath = StringUtils.replace(fileTruePath, "\\", "/");
		request.setAttribute("fileTruePath", fileTruePath);

		request.setAttribute("share_url", share_url);

		String share_img = ctx + "/" + uploadDir_share + "/" + id + ".png";
		share_img = StringUtils.replace(share_img, "\\", "/");
		request.setAttribute("share_img", share_img);

		request.setAttribute("time", new Date().getTime());
		request.setAttribute("is_weixin", super.isWeixin(request));
		// request.setAttribute("url_qrcode", url_qrcode);
		request.setAttribute("header_title", "我的邀请码");
		request.setAttribute("ShareQrCodeDesc", super.getSysSetting(Keys.ShareQrCodeDesc));
		request.setAttribute("is_app", super.isApp(request));

		if (super.isWeixin(request)) {
			super.setJsApiTicketRetrunParamToSession(request);
		}

		request.setAttribute("userInfo", super.getUserInfo(id));

		return new ActionForward("/MMyQrCode/myResgisterCode.jsp");

	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		//
		request.setAttribute("time", new Date().getTime());
		request.setAttribute("is_weixin", super.isWeixin(request));
		request.setAttribute("header_title", "我的专属链接");
		// request.setAttribute("url_qrcode", url_qrcode);

		super.setJsApiTicketRetrunParamToSession(request);

		return mapping.findForward("input");
	}
}
