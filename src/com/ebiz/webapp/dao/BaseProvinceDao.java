package com.ebiz.webapp.dao;

import java.util.List;

import com.ebiz.ssi.dao.EntityDao;
import com.ebiz.webapp.domain.BaseProvince;

/**
 * @author Wu,Yang
 * @version 2011-04-20 15:36
 */
public interface BaseProvinceDao extends EntityDao<BaseProvince> {

	List<BaseProvince> selectBaseProvinceResultForGroupByPalpha(BaseProvince t);

	List<BaseProvince> selectBaseProvinceHasWeiHuList(BaseProvince t);

}
