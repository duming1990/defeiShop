package com.ebiz.webapp.web.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Wu,Yang
 * @version 2010-08-24
 * @desc 临时运行必要的语句需要的action
 */
public class IndexPublicAction extends BaseAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 临时运行，从阿里巴巴网站页抓取属性,并保存到平台的数据库中
		// String url = "http://search.china.alibaba.com/selloffer/--1032497.html";
		// GetHtmlTools.getAlibabaAttrInfoInsertIntoSw(getFacade(), url, "3121");
		return null;
	}

	public ActionForward viewQQCode(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 临时运行，从阿里巴巴网站页抓取属性,并保存到平台的数据库中
		// String url = "http://search.china.alibaba.com/selloffer/--1032497.html";
		// GetHtmlTools.getAlibabaAttrInfoInsertIntoSw(getFacade(), url, "3121");
		return new ActionForward("/index/IndexPublic/viewQQCode.jsp");
	}

}