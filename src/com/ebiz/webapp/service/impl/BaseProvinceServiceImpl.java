package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.BaseProvinceDao;
import com.ebiz.webapp.domain.BaseProvince;
import com.ebiz.webapp.service.BaseProvinceService;

/**
 * @author Wu,Yang
 * @version 2011-04-20 02:13:04
 */
@Service
public class BaseProvinceServiceImpl extends BaseImpl implements BaseProvinceService {

	@Resource
	private BaseProvinceDao baseProvinceDao;

	public Integer createBaseProvince(BaseProvince t) {
		return this.baseProvinceDao.insertEntity(t);
	}

	public int modifyBaseProvince(BaseProvince t) {
		return this.baseProvinceDao.updateEntity(t);
	}

	public int removeBaseProvince(BaseProvince t) {
		return this.baseProvinceDao.deleteEntity(t);
	}

	public BaseProvince getBaseProvince(BaseProvince t) {
		return this.baseProvinceDao.selectEntity(t);
	}

	public Integer getBaseProvinceCount(BaseProvince t) {
		return this.baseProvinceDao.selectEntityCount(t);
	}

	public List<BaseProvince> getBaseProvinceList(BaseProvince t) {
		return this.baseProvinceDao.selectEntityList(t);
	}

	public List<BaseProvince> getBaseProvincePaginatedList(BaseProvince t) {
		return this.baseProvinceDao.selectEntityPaginatedList(t);
	}

	public List<BaseProvince> getBaseProvinceResultForGroupByPalpha(BaseProvince t) {
		return this.baseProvinceDao.selectBaseProvinceResultForGroupByPalpha(t);
	}

	public int modifyBaseProvinceForFuwuCount(BaseProvince t) {
		super.updateBasePro(t.getP_index().intValue(), 1, baseProvinceDao);
		return 0;
	}

	@Override
	public List<BaseProvince> getBaseProvinceHasWeiHuList(BaseProvince t) {
		return this.baseProvinceDao.selectBaseProvinceHasWeiHuList(t);
	}
}
