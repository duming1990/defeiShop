package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.MailServer;

/**
 * @author Wu,Yang
 * @version 2012-03-30 15:43
 */
public interface MailServerService {

	Integer createMailServer(MailServer t);

	int modifyMailServer(MailServer t);

	int removeMailServer(MailServer t);

	MailServer getMailServer(MailServer t);

	List<MailServer> getMailServerList(MailServer t);

	Integer getMailServerCount(MailServer t);

	List<MailServer> getMailServerPaginatedList(MailServer t);

}