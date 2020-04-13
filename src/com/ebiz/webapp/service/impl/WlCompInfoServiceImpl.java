package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.WlCompInfoDao;
import com.ebiz.webapp.domain.WlCompInfo;
import com.ebiz.webapp.service.WlCompInfoService;

/**
 * @author Wu,Yang
 * @version 2014-05-25 11:54
 */
@Service
public class WlCompInfoServiceImpl implements WlCompInfoService {

	@Resource
	private WlCompInfoDao wlCompInfoDao;

	public Integer createWlCompInfo(WlCompInfo t) {
		return this.wlCompInfoDao.insertEntity(t);
	}

	public WlCompInfo getWlCompInfo(WlCompInfo t) {
		//return this.wlCompInfoDao.selectEntity(t);
		
		List<WlCompInfo> list = this.wlCompInfoDao.selectEntityList(t);
		if(list.size()>0){
			return list.get(list.size()-1);
		}else{
			return null;
		}
	}

	public Integer getWlCompInfoCount(WlCompInfo t) {
		return this.wlCompInfoDao.selectEntityCount(t);
	}

	public List<WlCompInfo> getWlCompInfoList(WlCompInfo t) {
		
		return this.wlCompInfoDao.selectEntityList(t);
	}

	public int modifyWlCompInfo(WlCompInfo t) {
		return this.wlCompInfoDao.updateEntity(t);
	}

	public int removeWlCompInfo(WlCompInfo t) {
		return this.wlCompInfoDao.deleteEntity(t);
	}

	public List<WlCompInfo> getWlCompInfoPaginatedList(WlCompInfo t) {
		return this.wlCompInfoDao.selectEntityPaginatedList(t);
	}

	public List<WlCompInfo> getWlCompInfoGroupByPalpha(WlCompInfo t) {
		return this.wlCompInfoDao.selectWlCompInfoGroupByPalpha(t);
	}

}
