package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.CommWelfareApplyDao;
import com.ebiz.webapp.dao.CommWelfareDetailDao;
import com.ebiz.webapp.domain.CommWelfareApply;
import com.ebiz.webapp.domain.CommWelfareDetail;
import com.ebiz.webapp.service.CommWelfareDetailService;

/**
 * @author Wu,Yang
 * @version 2019-03-19 09:14
 */
@Service
public class CommWelfareDetailServiceImpl implements CommWelfareDetailService {

	@Resource
	private CommWelfareDetailDao commWelfareDetailDao;

	@Resource
	private CommWelfareApplyDao commWelfareApplyDao;

	public Integer createCommWelfareDetail(CommWelfareDetail t) {
		return this.commWelfareDetailDao.insertEntity(t);
	}

	public CommWelfareDetail getCommWelfareDetail(CommWelfareDetail t) {
		return this.commWelfareDetailDao.selectEntity(t);
	}

	public Integer getCommWelfareDetailCount(CommWelfareDetail t) {
		return this.commWelfareDetailDao.selectEntityCount(t);
	}

	public List<CommWelfareDetail> getCommWelfareDetailList(CommWelfareDetail t) {
		return this.commWelfareDetailDao.selectEntityList(t);
	}

	public int modifyCommWelfareDetail(CommWelfareDetail t) {
		int id = this.commWelfareDetailDao.updateEntity(t);

		CommWelfareApply welfareApply = (CommWelfareApply) t.getMap().get("welfareApply");
		if (welfareApply != null) {
			commWelfareApplyDao.updateEntity(welfareApply);
		}

		return id;
	}

	public int removeCommWelfareDetail(CommWelfareDetail t) {
		return this.commWelfareDetailDao.deleteEntity(t);
	}

	public List<CommWelfareDetail> getCommWelfareDetailPaginatedList(CommWelfareDetail t) {
		return this.commWelfareDetailDao.selectEntityPaginatedList(t);
	}

	@Override
	public List<CommWelfareDetail> getCommListWithWelfareDetail(CommWelfareDetail t) {
		return this.commWelfareDetailDao.selectCommListWithWelfareDetail(t);
	}

}
