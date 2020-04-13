package com.ebiz.webapp.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;

/**
 * @author Wu,Yang
 * @version 2011-4-20
 */
public class ManagerFilter extends OncePerRequestFilter {

	private String sessionKey = Keys.SESSION_USERINFO_KEY;

	private String loginPage;

	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		if (!this.isAuthenticated(request, this.sessionKey)) {
			String contextPath = request.getContextPath();
			if (contextPath.endsWith("/")) {
				contextPath = contextPath.substring(0, contextPath.length() - 1);
			}
			response.sendRedirect(contextPath + this.loginPage);
			return;
		}
		chain.doFilter(request, response);
	}

	private boolean isAuthenticated(HttpServletRequest request, String sessionKey) {
		if (StringUtils.isBlank(sessionKey)) {
			return false;
		}
		HttpSession session = request.getSession(false);
		if (null == session) {
			return false;
		}
		if (null == request.getSession().getAttribute(sessionKey)) {
			return false;
		}
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);
		if (null == ui) {
			return false;
		}

		String requestURI = request.getRequestURI();
		String ctx = request.getContextPath();
		if (null == ctx) {
			return false;
		} else if ("/".equals(ctx)) {
			ctx = "";
		}
		if (null == requestURI) {
			return false;
		}

		return true;
	}

	public String getLoginPage() {
		return loginPage;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setLoginPage(String loginPage) {
		this.loginPage = loginPage;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}
}
