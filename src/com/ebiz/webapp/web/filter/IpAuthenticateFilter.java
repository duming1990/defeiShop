package com.ebiz.webapp.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author Jin,QingHua
 */
public class IpAuthenticateFilter extends OncePerRequestFilter {
	/**
	 * You can conifg it in web.xml
	 */
	private String redirectURL = "http://www.aiecc.com/";

	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		// ServletContext servletContext = super.getFilterConfig().getServletContext();
		// WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		// Facade facade = (Facade) wac.getBean("facade");
		//
		// // logger.info("------------->:" + request.getRequestURL());
		// if (!LoginAction.isAuthorised(request, facade.getAuthorisedIpService())
		// && !request.getRequestURL().toString().endsWith("/Resume.do")) {
		// response.sendRedirect(this.redirectURL);
		// return;
		// }

		chain.doFilter(request, response);
	}

	public String getRedirectURL() {
		return redirectURL;
	}

	public void setRedirectURL(String redirectURL) {
		this.redirectURL = redirectURL;
	}

}
