package com.ebiz.webapp.web.filter;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ebiz.webapp.domain.UserInfo;

/**
 * @author Jin,QingHua
 */
public class SimpleAuthenticateFilter extends OncePerRequestFilter {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private String sessionKey;

	private String loginPage;

	private String userType;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		if (StringUtils.isBlank(this.sessionKey)) {
			logger.warn("sessionKey is empty, it will redirect to welcome page.");
		}
		if (StringUtils.isBlank(this.loginPage)) {
			logger.warn("loginPage is empty, it will redirect to welcome page.");
		}
		if (StringUtils.isBlank(this.userType)) {
			logger.warn("user_type is empty, it will redirect to welcome page.");
		}
		if (!this.isAuthenticated(request, this.sessionKey)) {
			String contextPath = request.getContextPath();
			if (contextPath.endsWith("/")) {
				contextPath = contextPath.substring(0, contextPath.length() - 1);
			}
			// response.sendRedirect(contextPath + this.loginPage);

			StringBuffer return_url = request.getRequestURL();
			String queryString = request.getQueryString();
			logger.info("==getRequestURL:{}", return_url);
			logger.info("==queryString:{}", queryString);
			// logger.info("null == redirectUrl:{}", (null == redirectUrl));
			if (null == return_url) {
				response.sendRedirect(contextPath + this.loginPage);
			} else {
				String paras = "";
				if (StringUtils.isNotBlank(queryString)) {
					paras = "?" + queryString;
				}

				String return_url_full = URLEncoder.encode(return_url.append(paras).toString(), "UTF-8");
				logger.info("==return_url:{}", return_url.toString());
				logger.info("==return_url_full:{}", return_url_full);
				logger.info(contextPath + this.loginPage + "?return_url=" + return_url_full);
				response.sendRedirect(contextPath + this.loginPage + "?return_url=" + return_url_full);
			}
			return;
		}
		chain.doFilter(request, response);
	}

	private boolean isAuthenticated(HttpServletRequest request, String sessionKey) {

		logger.info("=====user_type1:{}", this.userType);
		logger.info("=====sessionKey:{}", sessionKey);

		if (StringUtils.isBlank(sessionKey)) {
			logger.info("session key is empty!");
			return false;
		}
		if (StringUtils.isBlank(this.userType)) {
			logger.info("user_type is empty!");
			return false;
		}
		HttpSession session = request.getSession(false);
		if (null == session) {
			logger.info("=====session is null");
			return false;
		}
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(sessionKey);
		if (null == userInfo) {
			logger.info("=====userInfo is null");
			return false;
		}
		logger.info("=====user_type2:{}", userInfo.getUser_type());
		String[] userTypes = this.getUserTypes();
		if (null == userTypes) {
			return false;
		}

		for (String ut : userTypes) {
			if (ut.equals(userInfo.getUser_type().toString())) {
				return true;
			}
		}
		return false;
	}

	public String getLoginPage() {
		return loginPage;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public String getUserType() {
		return userType;
	}

	public String[] getUserTypes() {
		if (null != this.userType) {
			return StringUtils.split(this.userType, ',');
		}
		return null;
	}

	public void setLoginPage(String loginPage) {
		this.loginPage = loginPage;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
}
