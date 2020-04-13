package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.InvoiceInfoDao;
import com.ebiz.webapp.domain.InvoiceInfo;
import com.ebiz.webapp.service.InvoiceInfoService;

/**
 * @author Wu,Yang
 * @version 2014-05-25 11:54
 */
@Service
public class InvoiceInfoServiceImpl implements InvoiceInfoService {

	@Resource
	private InvoiceInfoDao invoiceInfoDao;

	public Integer createInvoiceInfo(InvoiceInfo t) {
		return this.invoiceInfoDao.insertEntity(t);
	}

	public InvoiceInfo getInvoiceInfo(InvoiceInfo t) {
		return this.invoiceInfoDao.selectEntity(t);
	}

	public Integer getInvoiceInfoCount(InvoiceInfo t) {
		return this.invoiceInfoDao.selectEntityCount(t);
	}

	public List<InvoiceInfo> getInvoiceInfoList(InvoiceInfo t) {
		return this.invoiceInfoDao.selectEntityList(t);
	}

	public int modifyInvoiceInfo(InvoiceInfo t) {
		return this.invoiceInfoDao.updateEntity(t);
	}

	public int removeInvoiceInfo(InvoiceInfo t) {
		return this.invoiceInfoDao.deleteEntity(t);
	}

	public List<InvoiceInfo> getInvoiceInfoPaginatedList(InvoiceInfo t) {
		return this.invoiceInfoDao.selectEntityPaginatedList(t);
	}

}
