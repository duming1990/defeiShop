package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.PdImgs;

/**
 * @author Wu,Yang
 * @version 2012-03-21 17:16
 */
public interface PdImgsService {

	Integer createPdImgs(PdImgs t);

	int modifyPdImgs(PdImgs t);

	int removePdImgs(PdImgs t);

	PdImgs getPdImgs(PdImgs t);

	List<PdImgs> getPdImgsList(PdImgs t);

	Integer getPdImgsCount(PdImgs t);

	List<PdImgs> getPdImgsPaginatedList(PdImgs t);

}