package com.ebiz.webapp.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;

/**
 * @author Jin,QingHua
 */
public class JspFilter extends GenericFilterBean {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * The pages that you can access, default value is "/default.jsp"<br />
	 * You can config it in the web.xml by "init-param" <br />
	 * <ul>
	 * <li>&lt;init-param&gt;</li>
	 * <li>&lt;param-name&gt;allowedJsp&lt;/param-name&gt;</li>
	 * <li>&lt;param-value&gt;/default.jsp&lt;/param-value&gt;</li>
	 * <li>&lt;/init-param&gt;</li>
	 * </ul>
	 */
	private String allowedJsp = "/default.jsp";

	public String getAllowedJsp() {
		return allowedJsp;
	}

	public void setAllowedJsp(String allowedJsp) {
		logger.debug("allowedJsp is {}", allowedJsp);
		this.allowedJsp = allowedJsp;
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		String requestURI = request.getRequestURI();
		logger.debug("requestURI is {}", requestURI);

		if (!this.isAllowed(requestURI)) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		chain.doFilter(request, response);

	}

	private String[] getAllowedJsps() {
		if (null != this.allowedJsp) {
			return StringUtils.split(this.allowedJsp, ",");
		}
		return null;
	}

	private boolean isAllowed(String uri) {
		String[] allowedJsps = this.getAllowedJsps();
		if (null != uri && null != allowedJsps) {
			if (uri.endsWith("/")) {
				return true;
			}
			for (String allowedJsp : allowedJsps) {
				logger.debug("allowedJsp is {}", allowedJsp);
				logger.debug("uri.endsWith(allowedJsp) -> {}", uri.endsWith(allowedJsp));
				if (uri.toLowerCase().endsWith(allowedJsp)) {
					return true;
				}
			}
		}
		return false;
	}

}
