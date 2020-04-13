package com.ebiz.webapp.web.servlet;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URLDecoder;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.HttpServletBean;

import com.ebiz.webapp.web.util.ZxingUtils;

public class QrCodeServlet extends HttpServletBean {
	private static final long serialVersionUID = -6099394048549869091L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.addHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
		response.setHeader("Cache-Control", "pre-check=0,post-check=0");
		response.setDateHeader("Expires", 0L);
		String code_url = request.getParameter("code_url");
		// String user_id = request.getParameter("user_id");
		// if (GenericValidator.isLong(user_id)) {
		// code_url += "&user_id=" + user_id;
		// }
		logger.info("== QrCodeServlet code_url1:" + code_url);
		if (StringUtils.isNotBlank(code_url)) {
			code_url = URLDecoder.decode(code_url, "UTF-8");
			logger.info("== QrCodeServlet code_url2:" + code_url);
			BufferedImage image = ZxingUtils.encodeQrcodeBufferedImage(code_url, 300, 300);
			if (null != image) {
				ImageIO.write(image, "png", response.getOutputStream());
			}
		}
	}

}