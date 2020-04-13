package com.ebiz.webapp.web.struts.manager.customer;

import java.io.File;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.ZxingUtils;

/**
 * @author Wu,Yang
 * @version 2010-8-24
 */
public class MyQrCodeAction extends BaseCustomerAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		super.getsonSysModuleList(request, dynaBean);

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.showMsgForManager(request, response, msg);
			return null;
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

		request.setAttribute("ShareQrCodeDesc", super.getSysSetting(Keys.ShareQrCodeDesc));

		request.setAttribute("time", new Date().getTime());
		return mapping.findForward("list");
	}
}
