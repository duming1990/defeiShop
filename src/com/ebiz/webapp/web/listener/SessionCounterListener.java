package com.ebiz.webapp.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author Jin,QingHua
 */
public class SessionCounterListener implements HttpSessionListener {
	private static final String COUNTER_ATTR = "session_counter";

	private int[] getCounter(HttpSessionEvent httpSessionEvent) {
		HttpSession session = httpSessionEvent.getSession();
		ServletContext context = session.getServletContext();
		int[] counter = (int[]) context.getAttribute(COUNTER_ATTR);
		if (null == counter) {
			counter = new int[1];
			context.setAttribute(COUNTER_ATTR, counter);
		}
		return counter;
	}

	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
		int[] counter = this.getCounter(httpSessionEvent);
		counter[0]++;

	}

	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
		int[] counter = this.getCounter(httpSessionEvent);
		counter[0]--;

	}
}
