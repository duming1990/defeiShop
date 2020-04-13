package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.ScEntpComm;

/**
 * @author Wu,Yang
 * @version 2014-05-27 18:15
 */
public interface ScEntpCommService {

	Integer createScEntpComm(ScEntpComm t);

	int modifyScEntpComm(ScEntpComm t);

	int removeScEntpComm(ScEntpComm t);

	ScEntpComm getScEntpComm(ScEntpComm t);

	List<ScEntpComm> getScEntpCommList(ScEntpComm t);

	Integer getScEntpCommCount(ScEntpComm t);

	List<ScEntpComm> getScEntpCommPaginatedList(ScEntpComm t);

}