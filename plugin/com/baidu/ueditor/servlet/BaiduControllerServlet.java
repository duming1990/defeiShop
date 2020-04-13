package com.baidu.ueditor.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.ueditor.ActionEnter;

/**
 * @author Wu,Yang
 * @version 2010-07-02
 * @desc Cos大附件上传
 * @version 2012-04-02增加同时 ftp上传文件功能跟
 */
public class BaiduControllerServlet extends HttpServlet {

	private static final long serialVersionUID = 8039096742060013539L;

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		logger.info("=================== BEGIN ControllerServlet DOPOST=====================");

		request.setCharacterEncoding("utf-8");
		response.setHeader("Content-Type", "text/html");
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		// logger.info("======rootPath:{}", rootPath);
		// logger.info("======new ActionEnter(request, rootPath).exec():{}", new ActionEnter(request, rootPath).exec());
		response.getWriter().write(new ActionEnter(request, rootPath).exec());
		// logger.info("======mydir:{}", request.getParameter("mydir"));

		logger.info("===================END ControllerServlet DOPOST===================");

	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

}
