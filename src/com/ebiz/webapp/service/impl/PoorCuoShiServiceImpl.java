package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.PoorCuoShiDao;
import com.ebiz.webapp.domain.PoorCuoShi;
import com.ebiz.webapp.service.PoorCuoShiService;

/**
 * @author Wu,Yang
 * @version 2018-03-01 15:13
 */
@Service
public class PoorCuoShiServiceImpl implements PoorCuoShiService {

	@Resource
	private PoorCuoShiDao poorCuoShiDao;

	public Integer createPoorCuoShi(PoorCuoShi t) {

		if (null != t.getMap().get("add_poor_cuo_shi")) {// 村站添加多条措施记录
			List<PoorCuoShi> cuoshiList = t.getCuoShiList();
			int insert_flag = 0;
			if (cuoshiList != null && cuoshiList.size() > 0) {
				// 先删
				List<PoorCuoShi> cuoShiOldList = this.poorCuoShiDao.selectEntityList(t);
				if (cuoShiOldList != null && cuoShiOldList.size() > 0) {
					for (PoorCuoShi temp : cuoShiOldList) {
						this.poorCuoShiDao.deleteEntity(temp);
					}
				}
				// 后增
				for (PoorCuoShi temp : cuoshiList) {
					Integer poor_cuoshi_id = this.poorCuoShiDao.insertEntity(temp);
					insert_flag = poor_cuoshi_id;
				}
			}
			return insert_flag;
		} else {// 正常新增
			return this.poorCuoShiDao.insertEntity(t);
		}
	}

	public PoorCuoShi getPoorCuoShi(PoorCuoShi t) {
		return this.poorCuoShiDao.selectEntity(t);
	}

	public Integer getPoorCuoShiCount(PoorCuoShi t) {
		return this.poorCuoShiDao.selectEntityCount(t);
	}

	public List<PoorCuoShi> getPoorCuoShiList(PoorCuoShi t) {
		return this.poorCuoShiDao.selectEntityList(t);
	}

	public int modifyPoorCuoShi(PoorCuoShi t) {
		return this.poorCuoShiDao.updateEntity(t);
	}

	public int removePoorCuoShi(PoorCuoShi t) {
		return this.poorCuoShiDao.deleteEntity(t);
	}

	public List<PoorCuoShi> getPoorCuoShiPaginatedList(PoorCuoShi t) {
		return this.poorCuoShiDao.selectEntityPaginatedList(t);
	}

}
