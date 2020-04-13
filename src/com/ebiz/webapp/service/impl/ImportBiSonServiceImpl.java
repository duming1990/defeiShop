package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.ImportBiSonDao;
import com.ebiz.webapp.domain.ImportBiSon;
import com.ebiz.webapp.service.ImportBiSonService;

/**
 * @author Wu,Yang
 * @version 2019-02-22 17:51
 */
@Service
public class ImportBiSonServiceImpl implements ImportBiSonService {

	@Resource
	private ImportBiSonDao importBiSonDao;

	public Integer createImportBiSon(ImportBiSon t) {
		return this.importBiSonDao.insertEntity(t);
	}

	public ImportBiSon getImportBiSon(ImportBiSon t) {
		return this.importBiSonDao.selectEntity(t);
	}

	public Integer getImportBiSonCount(ImportBiSon t) {
		return this.importBiSonDao.selectEntityCount(t);
	}

	public List<ImportBiSon> getImportBiSonList(ImportBiSon t) {
		return this.importBiSonDao.selectEntityList(t);
	}

	public int modifyImportBiSon(ImportBiSon t) {
		return this.importBiSonDao.updateEntity(t);
	}

	public int removeImportBiSon(ImportBiSon t) {
		return this.importBiSonDao.deleteEntity(t);
	}

	public List<ImportBiSon> getImportBiSonPaginatedList(ImportBiSon t) {
		return this.importBiSonDao.selectEntityPaginatedList(t);
	}

	public List<ImportBiSon> selectImportBiSonLeftUserList(ImportBiSon t) {
		return this.importBiSonDao.selectImportBiSonLeftUserList(t);
	}

}
