package com.ebiz.webapp.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author liujia
 */
public class AppFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		String isApp = request.getParameter("isApp");
		if (StringUtils.isNotBlank(isApp)) {
			HttpSession session = request.getSession();
			session.setAttribute("isApp", true);
		}
		chain.doFilter(request, response);
	}
}
