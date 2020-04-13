package com.ebiz.webapp.web.struts.manager.customer;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.util.ZxingUtils;

/**
 * @author Wu,yang
 * @version 2012-02-22
 */
public class MyShopAction extends BaseCustomerAction {
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

		UserInfo ui1 = super.getUserInfo(ui.getId());
		if (ui1.getIs_entp() != 1) {
			String msg = "您还不是商家";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		if (null == ui.getOwn_entp_id()) {
			String msg = "您还不是商家";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		request.setAttribute("entp_id", ui1.getOwn_entp_id());

		Integer entp_id = ui.getOwn_entp_id();
		String uploadDir = "files" + File.separator + "shoukuan";
		String uploadDir_share = "files/shoukuan";

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

		String imgPath = ctxDir + uploadDir + File.separator + entp_id + ".png";

		String share_url = ctx + "/shoukuan-" + entp_id + ".html";
		File imgFile = new File(imgPath);

		if (!imgFile.exists()) {
			ZxingUtils.encodeQrcode(share_url, imgPath, 500, 500, null);
		}

		request.setAttribute("fileTruePath", uploadDir_share + "/" + entp_id + ".png");
		request.setAttribute("share_url", share_url);
		request.setAttribute("share_img", ctx + uploadDir_share + "/" + entp_id + ".png");

		return mapping.findForward("list");
	}

}
