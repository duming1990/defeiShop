package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.InvoiceInfo;

/**
 * @author Wu,Yang
 * @version 2014-05-25 11:54
 */
public interface InvoiceInfoService {

	Integer createInvoiceInfo(InvoiceInfo t);

	int modifyInvoiceInfo(InvoiceInfo t);

	int removeInvoiceInfo(InvoiceInfo t);

	InvoiceInfo getInvoiceInfo(InvoiceInfo t);

	List<InvoiceInfo> getInvoiceInfoList(InvoiceInfo t);

	Integer getInvoiceInfoCount(InvoiceInfo t);

	List<InvoiceInfo> getInvoiceInfoPaginatedList(InvoiceInfo t);

}