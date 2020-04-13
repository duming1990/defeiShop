package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.ImportBiSon;

/**
 * @author Wu,Yang
 * @version 2019-02-22 17:51
 */
public interface ImportBiSonService {

	Integer createImportBiSon(ImportBiSon t);

	int modifyImportBiSon(ImportBiSon t);

	int removeImportBiSon(ImportBiSon t);

	ImportBiSon getImportBiSon(ImportBiSon t);

	List<ImportBiSon> getImportBiSonList(ImportBiSon t);

	Integer getImportBiSonCount(ImportBiSon t);

	List<ImportBiSon> getImportBiSonPaginatedList(ImportBiSon t);

	List<ImportBiSon> selectImportBiSonLeftUserList(ImportBiSon t);

}