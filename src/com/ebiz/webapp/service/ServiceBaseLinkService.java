package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.ServiceBaseLink;

/**
 * @author Wu,Yang
 * @version 2018-04-17 15:24
 */
public interface ServiceBaseLinkService {

	Integer createServiceBaseLink(ServiceBaseLink t);

	int modifyServiceBaseLink(ServiceBaseLink t);

	int removeServiceBaseLink(ServiceBaseLink t);

	ServiceBaseLink getServiceBaseLink(ServiceBaseLink t);

	List<ServiceBaseLink> getServiceBaseLinkList(ServiceBaseLink t);

	Integer getServiceBaseLinkCount(ServiceBaseLink t);

	List<ServiceBaseLink> getServiceBaseLinkPaginatedList(ServiceBaseLink t);

}