package com.ebiz.webapp.util;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.time.DateFormatUtils;

import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.web.util.ZxingUtils;

public class BarCodeUtil {

	public static void createBarCode(HttpServletRequest request, OrderInfo orderInfo) {
		try {

			HttpSession session = request.getSession();
			String ctxDir = session.getServletContext().getRealPath(String.valueOf(File.separatorChar));
			String autoCreatedDateDirByParttern = "yyyy" + File.separatorChar + "MM" + File.separatorChar + "dd"
					+ File.separatorChar;
			String autoCreatedDateDir = DateFormatUtils.format(orderInfo.getOrder_date(), autoCreatedDateDirByParttern);
			String uploadDir = "files" + File.separatorChar + "barcode" + File.separatorChar;
			String savePath = ctxDir + uploadDir + autoCreatedDateDir;
			String fileName = orderInfo.getTrade_index() + ".png";
			File file = new File(savePath);
			if (!file.exists()) {
				file.mkdirs();
			}
			ZxingUtils.encodeBarcode(orderInfo.getTrade_index(), savePath + fileName);

			// 前台显示用的
			String fileTruePath = uploadDir + autoCreatedDateDir + fileName;
			request.setAttribute("fileTruePath", fileTruePath);

		} catch (Exception localException) {
			localException.printStackTrace();
		}
	}

}