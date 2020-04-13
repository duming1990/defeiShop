package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.JdAreas;

/**
 * @author Wu,Yang
 * @version 2018-03-04 16:08
 */
public interface JdAreasService {

	Integer createJdAreas(JdAreas t);

	int modifyJdAreas(JdAreas t);

	int removeJdAreas(JdAreas t);

	JdAreas getJdAreas(JdAreas t);

	List<JdAreas> getJdAreasList(JdAreas t);

	Integer getJdAreasCount(JdAreas t);

	List<JdAreas> getJdAreasPaginatedList(JdAreas t);

}