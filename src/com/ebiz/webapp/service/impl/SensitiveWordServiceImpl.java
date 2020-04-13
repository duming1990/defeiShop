package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.SensitiveWordDao;
import com.ebiz.webapp.domain.SensitiveWord;
import com.ebiz.webapp.service.SensitiveWordService;

/**
 * @author Wu,Yang
 * @version 2018-01-30 10:26
 */
@Service
public class SensitiveWordServiceImpl implements SensitiveWordService {

	@Resource
	private SensitiveWordDao sensitiveWordDao;
	

	public Integer createSensitiveWord(SensitiveWord t) {
		return this.sensitiveWordDao.insertEntity(t);
	}

	public SensitiveWord getSensitiveWord(SensitiveWord t) {
		return this.sensitiveWordDao.selectEntity(t);
	}

	public Integer getSensitiveWordCount(SensitiveWord t) {
		return this.sensitiveWordDao.selectEntityCount(t);
	}

	public List<SensitiveWord> getSensitiveWordList(SensitiveWord t) {
		return this.sensitiveWordDao.selectEntityList(t);
	}

	public int modifySensitiveWord(SensitiveWord t) {
		return this.sensitiveWordDao.updateEntity(t);
	}

	public int removeSensitiveWord(SensitiveWord t) {
		return this.sensitiveWordDao.deleteEntity(t);
	}

	public List<SensitiveWord> getSensitiveWordPaginatedList(SensitiveWord t) {
		return this.sensitiveWordDao.selectEntityPaginatedList(t);
	}

}
