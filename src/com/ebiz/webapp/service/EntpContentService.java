package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.EntpContent;

/**
 * @author Wu,Yang
 * @version 2016-03-28 15:00
 */
public interface EntpContentService {

	Integer createEntpContent(EntpContent t);

	int modifyEntpContent(EntpContent t);

	int removeEntpContent(EntpContent t);

	EntpContent getEntpContent(EntpContent t);

	List<EntpContent> getEntpContentList(EntpContent t);

	Integer getEntpContentCount(EntpContent t);

	List<EntpContent> getEntpContentPaginatedList(EntpContent t);

}