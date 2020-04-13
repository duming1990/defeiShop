package com.ebiz.webapp.web.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author 刘佳
 * @date: 2018年10月7日 上午10:48:49
 */
public class EmojiFilterUtils {

	/**
	 * 将emoji表情替换成*
	 * 
	 * @param source
	 * @return 过滤后的字符串
	 */
	public static String filterEmoji(String source, String replaceChar) {
		if (StringUtils.isBlank(replaceChar)) {
			replaceChar = "*";
		}
		if (StringUtils.isNotBlank(source)) {
			return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", replaceChar);
		} else {
			return source;
		}
	}
}
