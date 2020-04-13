package com.ebiz.webapp.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.ServiceBaseLinkDao;
import com.ebiz.webapp.domain.ServiceBaseLink;
import com.ebiz.webapp.service.ServiceBaseLinkService;

/**
 * @author Wu,Yang
 * @version 2018-04-17 15:24
 */
@Service
public class ServiceBaseLinkServiceImpl implements ServiceBaseLinkService {

	@Resource
	private ServiceBaseLinkDao serviceBaseLinkDao;

	public Integer createServiceBaseLink(ServiceBaseLink t) {
		return this.serviceBaseLinkDao.insertEntity(t);
	}

	public ServiceBaseLink getServiceBaseLink(ServiceBaseLink t) {
		return this.serviceBaseLinkDao.selectEntity(t);
	}

	public Integer getServiceBaseLinkCount(ServiceBaseLink t) {
		return this.serviceBaseLinkDao.selectEntityCount(t);
	}

	public List<ServiceBaseLink> getServiceBaseLinkList(ServiceBaseLink t) {
		return this.serviceBaseLinkDao.selectEntityList(t);
	}

	public int modifyServiceBaseLink(ServiceBaseLink t) {
		int count = this.serviceBaseLinkDao.updateEntity(t);
		if (null != t.getMap().get("del_id")) {
			int del_id = (Integer) t.getMap().get("del_id");
			if (count > 0) {
				ServiceBaseLink link = new ServiceBaseLink();
				link.setUpdate_time(new Date());
				link.setUpdate_uid(new Integer(del_id));
				link.setDel_time(new Date());
				link.setDel_uid(new Integer(del_id));
				link.setIs_del(1);
				if (null != t.getMap().get("pks")) {
					link.getMap().put("par_ids", t.getMap().get("pks"));
				} else {
					link.getMap().put("par_id", t.getId());
				}
				this.serviceBaseLinkDao.updateEntity(link);
			}
		}
		return count;
	}

	public int removeServiceBaseLink(ServiceBaseLink t) {
		return this.serviceBaseLinkDao.deleteEntity(t);
	}

	public List<ServiceBaseLink> getServiceBaseLinkPaginatedList(ServiceBaseLink t) {
		return this.serviceBaseLinkDao.selectEntityPaginatedList(t);
	}

}
