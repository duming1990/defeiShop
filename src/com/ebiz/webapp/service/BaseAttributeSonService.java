package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.BaseAttributeSon;

/**
 * @author Wu,Yang
 * @version 2012-05-30 08:53
 */
public interface BaseAttributeSonService {

	Integer createBaseAttributeSon(BaseAttributeSon t);

	int modifyBaseAttributeSon(BaseAttributeSon t);

	int removeBaseAttributeSon(BaseAttributeSon t);

	BaseAttributeSon getBaseAttributeSon(BaseAttributeSon t);

	List<BaseAttributeSon> getBaseAttributeSonList(BaseAttributeSon t);

	Integer getBaseAttributeSonCount(BaseAttributeSon t);

	List<BaseAttributeSon> getBaseAttributeSonPaginatedList(BaseAttributeSon t);

}