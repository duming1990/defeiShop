package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.PdImgsDao;
import com.ebiz.webapp.domain.PdImgs;
import com.ebiz.webapp.service.PdImgsService;

/**
 * @author Wu,Yang
 * @version 2012-03-21 17:16
 */
@Service
public class PdImgsServiceImpl implements PdImgsService {

	@Resource
	private PdImgsDao pdImgsDao;

	public Integer createPdImgs(PdImgs t) {
		return this.pdImgsDao.insertEntity(t);
	}

	public PdImgs getPdImgs(PdImgs t) {
		return this.pdImgsDao.selectEntity(t);
	}

	public Integer getPdImgsCount(PdImgs t) {
		return this.pdImgsDao.selectEntityCount(t);
	}

	public List<PdImgs> getPdImgsList(PdImgs t) {
		return this.pdImgsDao.selectEntityList(t);
	}

	public int modifyPdImgs(PdImgs t) {
		return this.pdImgsDao.updateEntity(t);
	}

	public int removePdImgs(PdImgs t) {
		return this.pdImgsDao.deleteEntity(t);
	}

	public List<PdImgs> getPdImgsPaginatedList(PdImgs t) {
		return this.pdImgsDao.selectEntityPaginatedList(t);
	}

}
