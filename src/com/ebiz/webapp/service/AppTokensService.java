package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.AppTokens;

/**
 * @author Wu,Yang
 * @version 2018-03-26 10:36
 */
public interface AppTokensService {

	Integer createAppTokens(AppTokens t);

	int modifyAppTokens(AppTokens t);

	int removeAppTokens(AppTokens t);

	AppTokens getAppTokens(AppTokens t);

	List<AppTokens> getAppTokensList(AppTokens t);

	Integer getAppTokensCount(AppTokens t);

	List<AppTokens> getAppTokensPaginatedList(AppTokens t);

}