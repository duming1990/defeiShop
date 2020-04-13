package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.ModPopedom;

/**
 * @author Wu,Yang
 * @version 2011-04-20 02:13:04
 */
public interface ModPopedomService {

	Integer createModPopedom(ModPopedom t);

	int modifyModPopedom(ModPopedom t);

	int removeModPopedom(ModPopedom t);

	ModPopedom getModPopedom(ModPopedom t);

	List<ModPopedom> getModPopedomList(ModPopedom t);

	Integer getModPopedomCount(ModPopedom t);

	List<ModPopedom> getModPopedomPaginatedList(ModPopedom t);

}
