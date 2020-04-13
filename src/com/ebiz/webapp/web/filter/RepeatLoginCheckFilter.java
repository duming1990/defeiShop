package com.ebiz.webapp.web.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;

/**
 * Servlet Filter implementation class RepeatLoginCheckFilter
 */
public class RepeatLoginCheckFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		// 判断重复登录
		HttpSession session = request.getSession(false);

		if (null != session && null != session.getAttribute(Keys.SESSION_USERINFO_KEY)) {
			UserInfo userInfo = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);
			HttpSession acSession = (HttpSession) this.getServletContext().getAttribute(
					"repeatLogin_" + userInfo.getId().toString());
			if (null != acSession) {
				UserInfo eu = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);
				UserInfo ac_eu = (UserInfo) acSession.getAttribute(Keys.SESSION_USERINFO_KEY);
				if ((!session.getId().equals(acSession.getId())) && eu.getId().longValue() == ac_eu.getId().longValue()) {
					Date loginDate = (Date) acSession.getAttribute("loginDate");

					request.setAttribute("loginIp", acSession.getAttribute("loginIp"));
					request.setAttribute("loginDate", DateFormatUtils.format(loginDate, "yyyy-MM-dd HH:mm:ss"));
					session.invalidate();

					// String contextPath = request.getContextPath();
					// response.sendRedirect(contextPath + "/admin/repeatLogin.jsp");

					RequestDispatcher rd = getServletContext().getRequestDispatcher("/commons/pages/repeatLogin.jsp");
					rd.forward(request, response);

					return;
				}
			}
		}

		chain.doFilter(request, response);

	}

}
