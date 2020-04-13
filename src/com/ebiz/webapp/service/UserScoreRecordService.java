package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.UserScoreRecord;

/**
 * @author Wu,Yang
 * @version 2014-05-27 16:43
 */
public interface UserScoreRecordService {

	Integer createUserScoreRecord(UserScoreRecord t);

	int modifyUserScoreRecord(UserScoreRecord t);

	int removeUserScoreRecord(UserScoreRecord t);

	UserScoreRecord getUserScoreRecord(UserScoreRecord t);

	List<UserScoreRecord> getUserScoreRecordList(UserScoreRecord t);

	Integer getUserScoreRecordCount(UserScoreRecord t);

	List<UserScoreRecord> getUserScoreRecordPaginatedList(UserScoreRecord t);

}