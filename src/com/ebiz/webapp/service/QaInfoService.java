package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.QaInfo;

/**
 * @author Wu,Yang
 * @version 2012-03-21 17:16
 */
public interface QaInfoService {

	Integer createQaInfo(QaInfo t);

	int modifyQaInfo(QaInfo t);

	int removeQaInfo(QaInfo t);

	QaInfo getQaInfo(QaInfo t);

	List<QaInfo> getQaInfoList(QaInfo t);

	Integer getQaInfoCount(QaInfo t);

	List<QaInfo> getQaInfoPaginatedList(QaInfo t);

}