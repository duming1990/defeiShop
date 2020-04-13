package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.PdContent;

/**
 * @author Wu,Yang
 * @version 2012-03-21 17:16
 */
public interface PdContentService {

	Integer createPdContent(PdContent t);

	int modifyPdContent(PdContent t);

	int removePdContent(PdContent t);

	PdContent getPdContent(PdContent t);

	List<PdContent> getPdContentList(PdContent t);

	Integer getPdContentCount(PdContent t);

	List<PdContent> getPdContentPaginatedList(PdContent t);

}