package com.ebiz.webapp.web.util;

import java.net.URLEncoder;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ebiz.webapp.domain.NewsInfo;

/**
 * @author Jin,QingHua
 */
public class ScriptUtils {

	private static final Logger logger = LoggerFactory.getLogger(ScriptUtils.class);

	// private static final Logger logger = LoggerFactory.getLogger(ScriptUtils.class);

	/**
	 * CascadeSelect's nodes is contains with javascript arrays with strutcture like this: [[parent, text,
	 * value],[],...[]]
	 * 
	 * @param c Collection
	 * @param parentProperty
	 * @param textProperty
	 * @param valueProperty
	 * @return String
	 */
	// public static String getCascadeSelectString(List<NewsInfo> newsInfoList, String parentProperty,
	// String textProperty, String valueProperty) {
	// StringBuffer buffer = new StringBuffer();
	// for (NewsInfo o : newsInfoList) {
	// String parent = "";
	// String text = "";
	// String value = "";
	// try {
	// parent = BeanUtils.getProperty(o, parentProperty);
	// text = BeanUtils.getProperty(o, textProperty);
	// value = BeanUtils.getProperty(o, valueProperty);
	// } catch (Exception ex) {
	// logger.error(ex.getMessage());
	// }
	// buffer.append("['").append(parent).append("','").append(StringUtils.replace(text, "\'", "\\\'")).append(
	// "','").append(value).append("'],");
	// }
	// buffer.append("{}");
	// return StringEscapeUtils.escapeJavaScript((new StringBuffer("[").append(buffer).append("]")).toString());
	// }
	//
	// /**
	// * @param c
	// * @param imgProperty
	// * @param txtProperty
	// * @param lnkProperty
	// * @return
	// * @see #getPptJsonString(Collection, String, String, String,String, String)
	// */
	// public static String getPptJsonString(List<NewsInfo> newsInfoList, String imgProperty, String txtProperty,
	// String lnkProperty) {
	// return ScriptUtils.getPptJsonString(newsInfoList, imgProperty, txtProperty, lnkProperty, "news/view.tdb", null);
	// }
	//
	// /**
	// * @param c
	// * @param imgProperty
	// * @param txtProperty
	// * @param lnkProperty
	// * @param linkURI
	// * @return
	// * @see #getPptJsonString(Collection, String, String, String,String, String)
	// */
	// public static String getPptJsonString(List<NewsInfo> newsInfoList, String imgProperty, String txtProperty,
	// String lnkProperty, String linkURI) {
	// return ScriptUtils.getPptJsonString(newsInfoList, imgProperty, txtProperty, lnkProperty, linkURI, null);
	// }
	//
	// /**
	// * @param c Collection
	// * @param imgProperty
	// * @param txtProperty
	// * @param lnkProperty
	// * @param linkURI default is "news/view.tdb"
	// * @param siteURI default is "" link to other or absoult uri e.g. http://photo.163.com/
	// * @return String
	// */
	// public static String getPptJsonString(List<NewsInfo> newsInfoList, String imgProperty, String txtProperty,
	// String lnkProperty, String linkURI, String siteURI) {
	//
	// linkURI = StringUtils.isNotBlank(linkURI) ? linkURI : "news/view.tdb";
	// siteURI = StringUtils.isNotBlank(siteURI) ? siteURI : "";
	//
	// linkURI = linkURI.indexOf("?") == -1 ? linkURI + "?id=" : linkURI + "&id=";
	//
	// StringBuffer buffer = new StringBuffer();
	// for (NewsInfo o : newsInfoList) {
	// String img = "";
	// String txt = "";
	// String lnk = "";
	// try {
	// img = BeanUtils.getProperty(o, imgProperty);
	// txt = BeanUtils.getProperty(o, txtProperty);
	// lnk = BeanUtils.getProperty(o, lnkProperty);
	// } catch (Exception ex) {
	// logger.error(ex.getMessage());
	// }
	// buffer.append("{");
	// buffer.append("img:'").append(siteURI).append(img).append("',");
	// buffer.append("txt:'").append(StringUtils.replace(txt, "\'", "\\\'")).append("',");
	// buffer.append("lnk:escape('").append(linkURI).append(lnk).append("')},");
	// }
	// buffer.append("{}");
	// return buffer.toString();
	// }
	//
	// public static String getPptJsonStringWithUrlRewrite(List<NewsInfo> newsInfoList, String imgProperty,
	// String txtProperty, String lnkProperty, String linkURI, String siteURI) {
	//
	// linkURI = StringUtils.isNotBlank(linkURI) ? linkURI : "news/view.tdb";
	// siteURI = StringUtils.isNotBlank(siteURI) ? siteURI : "";
	//
	// // linkURI = linkURI.indexOf("?") == -1 ? linkURI + "?id=" : linkURI +
	// // "&id=";
	//
	// StringBuffer buffer = new StringBuffer();
	// for (Object o : newsInfoList) {
	// String img = "";
	// String txt = "";
	// String lnk = "";
	// try {
	// img = BeanUtils.getProperty(o, imgProperty);
	// txt = BeanUtils.getProperty(o, txtProperty);
	// lnk = BeanUtils.getProperty(o, lnkProperty);
	// } catch (Exception ex) {
	// logger.error(ex.getMessage());
	// }
	// buffer.append("{");
	// buffer.append("img:'").append(siteURI).append(img).append("',");
	// buffer.append("txt:'").append(StringUtils.replace(txt, "\'", "\\\'")).append("',");
	// buffer.append("lnk:escape('").append(linkURI).append(lnk).append(".xhtml')},");
	// }
	// buffer.append("{}");
	// return buffer.toString();
	// }

	/**
	 * @param c Collection
	 * @param imgProperty
	 * @param txtProperty
	 * @param lnkProperty
	 * @param linkURI default is "news/view.tdb"
	 * @param siteURI default is "" link to other or absoult uri e.g. http://photo.163.com/
	 * @return String
	 * @throws Exception
	 */
	public static String getPptJsonString(List<NewsInfo> newsInfoList, String imgProperty, String txtProperty,
			String lnkProperty, String linkURI, String siteURI) throws Exception {

		linkURI = StringUtils.isNotBlank(linkURI) ? linkURI : "news/view.tdb";
		siteURI = StringUtils.isNotBlank(siteURI) ? siteURI : "";

		linkURI = linkURI.indexOf("?") == -1 ? linkURI + "?uuid=" : linkURI + "&uuid=";
		if (null != siteURI) {
			linkURI = siteURI + "/" + linkURI;
		}
		StringBuffer buffer = new StringBuffer();
		for (NewsInfo o : newsInfoList) {
			String img = "";
			String txt = "";
			String lnk = "";
			String templnk = linkURI;
			try {
				img = BeanUtils.getProperty(o, imgProperty);
				txt = BeanUtils.getProperty(o, txtProperty);
				lnk = BeanUtils.getProperty(o, lnkProperty);
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}

			buffer.append("{");
			buffer.append("img:'").append(img).append("',");
			txt = StringUtils.substring(txt, 0, 13).concat("...");
			buffer.append("txt:'").append(URLEncoder.encode(StringUtils.replace(txt, "\'", "\\\'"), "UTF-8"))
					.append("',");
			if (StringUtils.isNotBlank(o.getDirect_uri())) {
				templnk = o.getDirect_uri();
				lnk = "";
			}
			buffer.append("lnk:escape('").append(templnk).append(lnk).append("')},");
		}
		buffer.append("{}");
		return buffer.toString();
	}

}
