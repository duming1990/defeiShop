package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.SensitiveWord;

/**
 * @author Wu,Yang
 * @version 2018-01-30 10:26
 */
public interface SensitiveWordService {

	Integer createSensitiveWord(SensitiveWord t);

	int modifySensitiveWord(SensitiveWord t);

	int removeSensitiveWord(SensitiveWord t);

	SensitiveWord getSensitiveWord(SensitiveWord t);

	List<SensitiveWord> getSensitiveWordList(SensitiveWord t);

	Integer getSensitiveWordCount(SensitiveWord t);

	List<SensitiveWord> getSensitiveWordPaginatedList(SensitiveWord t);

}