package com.ebiz.webapp.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ebiz.webapp.web.Keys;

/**
 * @author Wu,Yang
 * @version 2009-11-04
 */
public final class BaseProvinceListener implements ServletContextListener {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	public void contextDestroyed(ServletContextEvent sce) {
		ServletContext application = sce.getServletContext();
		application.removeAttribute(Keys.BASE_PROVINCE_LIST);
	}

	public void contextInitialized(ServletContextEvent sce) {

		// ServletContext servletContext = sce.getServletContext();
		// WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		// Facade facade = (Facade) wac.getBean("facade");
		//
		// logger.info("读取省市列表信息开始！");
		//
		// BaseProvince baseProvince = new BaseProvince();
		// baseProvince.setIs_del(0);
		// List<BaseProvince> baseProvinceList = facade.getBaseProvinceService().getBaseProvinceList(baseProvince);
		// if (baseProvinceList == null) {
		// logger.error("获取省市列表信息失败！！！！！");
		// }
		// servletContext.setAttribute(Keys.BASE_PROVINCE_LIST, baseProvinceList);
		//
		// logger.info("读取省市列表信息结束！");

	}
}
