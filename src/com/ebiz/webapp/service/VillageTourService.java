package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.VillageTour;

/**
 * @author Wu,Yang
 * @version 2018-08-22 14:47
 */
public interface VillageTourService {

	Integer createVillageTour(VillageTour t);

	int modifyVillageTour(VillageTour t);

	int removeVillageTour(VillageTour t);

	VillageTour getVillageTour(VillageTour t);

	List<VillageTour> getVillageTourList(VillageTour t);

	Integer getVillageTourCount(VillageTour t);

	List<VillageTour> getVillageTourPaginatedList(VillageTour t);

}