package com.ebiz.webapp.web.struts.m;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.ZxingUtils;

/**
 * @author Liu,Jia
 * @version 2016-07-13
 */
public class MMyShopQrCodeAction extends MBaseWebAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		request.setAttribute("header_title", "我的收款码");

		UserInfo ui = super.getUserInfoFromSession(request);

		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}

		UserInfo ui1 = super.getUserInfo(ui.getId());
		if (ui1.getIs_entp() != 1) {
			String msg = "您还不是商家";
			return showTipMsg(mapping, form, request, response, msg);
		}
		if (null == ui.getOwn_entp_id()) {
			String msg = "您还不是商家";
			return showTipMsg(mapping, form, request, response, msg);
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

		String img200Path = ctxDir + uploadDir + File.separator + entp_id + "-"
				+ Keys.FanXianTypeEntp.FanXianType_200.getIndex() + ".png";

		String share200_url = ctx + "/shoukuan-" + entp_id + "-" + Keys.FanXianTypeEntp.FanXianType_200.getIndex()
				+ ".html";
		File img200File = new File(img200Path);
		if (!img200File.exists()) {
			ZxingUtils.encodeQrcode(share200_url, img200Path, 500, 500, null);
		}
		request.setAttribute("fileTruePath", uploadDir_share + "/" + entp_id + ".png");
		request.setAttribute("fileTruePath200", uploadDir_share + "/" + entp_id + "-"
				+ Keys.FanXianTypeEntp.FanXianType_200.getIndex() + ".png");
		return mapping.findForward("list");
	}

}
