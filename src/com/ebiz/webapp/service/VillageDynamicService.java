package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.VillageDynamic;

/**
 * @author Wu,Yang
 * @version 2018-01-26 15:45
 */
public interface VillageDynamicService {

	Integer createVillageDynamic(VillageDynamic t);

	int modifyVillageDynamic(VillageDynamic t);

	int removeVillageDynamic(VillageDynamic t);

	VillageDynamic getVillageDynamic(VillageDynamic t);

	List<VillageDynamic> getVillageDynamicList(VillageDynamic t);

	Integer getVillageDynamicCount(VillageDynamic t);

	List<VillageDynamic> getVillageDynamicPaginatedList(VillageDynamic t);

}