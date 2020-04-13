package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.ImportBi;

/**
 * @author Wu,Yang
 * @version 2019-02-22 17:51
 */
public interface ImportBiService {

	Integer createImportBi(ImportBi t);

	int modifyImportBi(ImportBi t);

	int removeImportBi(ImportBi t);

	ImportBi getImportBi(ImportBi t);

	List<ImportBi> getImportBiList(ImportBi t);

	Integer getImportBiCount(ImportBi t);

	List<ImportBi> getImportBiPaginatedList(ImportBi t);

}