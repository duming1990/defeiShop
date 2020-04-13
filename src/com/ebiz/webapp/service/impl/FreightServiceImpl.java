package com.ebiz.webapp.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.FreightDao;
import com.ebiz.webapp.dao.FreightDetailDao;
import com.ebiz.webapp.domain.Freight;
import com.ebiz.webapp.domain.FreightDetail;
import com.ebiz.webapp.service.FreightService;

/**
 * @author Wu,Yang
 * @version 2014-05-22 19:36
 */

@Service
public class FreightServiceImpl implements FreightService {

	@Resource
	private FreightDao freightDao;

	@Resource
	private FreightDetailDao freightDetailDao;

	public Integer createFreight(Freight t) {
		return this.freightDao.insertEntity(t);
	}

	public Freight getFreight(Freight t) {
		return this.freightDao.selectEntity(t);
	}

	public Integer getFreightCount(Freight t) {
		return this.freightDao.selectEntityCount(t);
	}

	public List<Freight> getFreightList(Freight t) {
		return this.freightDao.selectEntityList(t);
	}

	public int modifyFreight(Freight f) {
		List<FreightDetail> list = f.getFreightDetailList();
		String freight_id = f.getId() == null ? "" : f.getId() + "";

		// 如果存在,则删除详细信息
		if (!StringUtils.isBlank(freight_id)) {
			// 更新
			this.freightDao.updateEntity(f);

			if (f.getMap().get("freight_detail_no_delete") == null) {

				FreightDetail df = new FreightDetail();
				df.setFre_id(Integer.valueOf(freight_id));
				// 删除详细信息
				this.freightDetailDao.deleteEntity(df);
			}
		} else {
			// 新增
			f.setAdd_date(new Date());
			Integer f_id = this.freightDao.insertEntity(f);
			freight_id = f_id + "";
		}

		for (FreightDetail fd : list) {
			fd.setFre_id(Integer.valueOf(freight_id));

			this.freightDetailDao.insertEntity(fd);

		}

		return 1;

	}

	public int removeFreight(Freight t) {
		return this.freightDao.deleteEntity(t);
	}

	public List<Freight> getFreightPaginatedList(Freight t) {
		return this.freightDao.selectEntityPaginatedList(t);
	}

}
