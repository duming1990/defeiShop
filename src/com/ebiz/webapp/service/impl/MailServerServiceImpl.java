package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.MailServerDao;
import com.ebiz.webapp.domain.MailServer;
import com.ebiz.webapp.service.MailServerService;

/**
 * @author Wu,Yang
 * @version 2012-03-30 15:43
 */
@Service
public class MailServerServiceImpl implements MailServerService {

	@Resource
	private MailServerDao mailServerDao;

	public Integer createMailServer(MailServer t) {
		return this.mailServerDao.insertEntity(t);
	}

	public MailServer getMailServer(MailServer t) {
		return this.mailServerDao.selectEntity(t);
	}

	public Integer getMailServerCount(MailServer t) {
		return this.mailServerDao.selectEntityCount(t);
	}

	public List<MailServer> getMailServerList(MailServer t) {
		return this.mailServerDao.selectEntityList(t);
	}

	public int modifyMailServer(MailServer t) {
		return this.mailServerDao.updateEntity(t);
	}

	public int removeMailServer(MailServer t) {
		return this.mailServerDao.deleteEntity(t);
	}

	public List<MailServer> getMailServerPaginatedList(MailServer t) {
		return this.mailServerDao.selectEntityPaginatedList(t);
	}

}
