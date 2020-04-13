package com.ebiz.webapp.dao;

import java.util.List;

import com.ebiz.ssi.dao.EntityDao;
import com.ebiz.webapp.domain.ImportBiSon;

/**
 * @author Wu,Yang
 * @version 2019-02-22 17:51
 */
public interface ImportBiSonDao extends EntityDao<ImportBiSon> {

	List<ImportBiSon> selectImportBiSonLeftUserList(ImportBiSon t);

}
