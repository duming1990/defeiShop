package com.ebiz.webapp.web.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EncryptUtilsV2 {
	protected static final Logger logger = LoggerFactory.getLogger(EncryptUtilsV2.class);

	public static final String MD5Encode(String s) {
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes();

			MessageDigest mdInst = MessageDigest.getInstance("MD5");

			mdInst.update(btInput);

			byte[] md = mdInst.digest();

			int j = md.length;
			char[] str = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; ++i) {
				byte byte0 = md[i];
				str[(k++)] = hexDigits[(byte0 >>> 4 & 0xF)];
				str[(k++)] = hexDigits[(byte0 & 0xF)];
			}
			return new String(str).toLowerCase();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String encodingFileName(String fileName) {
		String newFileName = "noName.xls";
		String returnFileName = "";
		try {
			if (StringUtils.isNotBlank(fileName)) {
				newFileName = fileName;
			}
			returnFileName = URLEncoder.encode(newFileName, "UTF-8");
			returnFileName = StringUtils.replace(returnFileName, "+", "%20");

			returnFileName = new String(newFileName.getBytes("GB2312"), "ISO8859-1");
			returnFileName = StringUtils.replace(returnFileName, " ", "%20");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			logger.error("Don't support this encoding ...");
		}
		logger.info("encoded file name is {}", returnFileName);
		logger.info("encoded file name is {}", Integer.valueOf(returnFileName.length()));
		return returnFileName;
	}
}