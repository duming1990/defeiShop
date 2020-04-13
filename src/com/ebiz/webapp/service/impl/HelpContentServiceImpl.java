package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.HelpContentDao;
import com.ebiz.webapp.domain.HelpContent;
import com.ebiz.webapp.service.HelpContentService;

/**
 * @author Jin,QingHua
 */
@Service
public class HelpContentServiceImpl implements HelpContentService {

	@Resource
	private HelpContentDao helpContentDao;

	public Integer createHelpContent(HelpContent t) {
		return this.helpContentDao.insertEntity(t);
	}

	public HelpContent getHelpContent(HelpContent t) {
		return this.helpContentDao.selectEntity(t);
	}

	public Integer getHelpContentCount(HelpContent t) {
		return this.helpContentDao.selectEntityCount(t);
	}

	public List<HelpContent> getHelpContentList(HelpContent t) {
		return this.helpContentDao.selectEntityList(t);
	}

	public int modifyHelpContent(HelpContent t) {
		return this.helpContentDao.updateEntity(t);
	}

	public int removeHelpContent(HelpContent t) {
		return this.helpContentDao.deleteEntity(t);
	}

	public List<HelpContent> getHelpContentPaginatedList(HelpContent t) {
		return this.helpContentDao.selectEntityPaginatedList(t);
	}

}
