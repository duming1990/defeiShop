package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.CommWelfareApplyDao;
import com.ebiz.webapp.dao.CommWelfareDetailDao;
import com.ebiz.webapp.domain.CommWelfareApply;
import com.ebiz.webapp.domain.CommWelfareDetail;
import com.ebiz.webapp.service.CommWelfareApplyService;

/**
 * @author Wu,Yang
 * @version 2019-03-19 09:14
 */
@Service
public class CommWelfareApplyServiceImpl implements CommWelfareApplyService {

	@Resource
	private CommWelfareApplyDao commWelfareApplyDao;
	@Resource
	private CommWelfareDetailDao commWelfareDetailDao;
	

	@SuppressWarnings("unchecked")
	public Integer createCommWelfareApply(CommWelfareApply t) {
		int id = this.commWelfareApplyDao.insertEntity(t);
		
		List<CommWelfareDetail> commWelfareDetailList =(List<CommWelfareDetail>) t.getMap().get("welfareDetailList");
		
		if(commWelfareDetailList != null  && commWelfareDetailList.size() > 0) {
			for(CommWelfareDetail welfareDetail:commWelfareDetailList) {
				welfareDetail.setComm_welfare_id(id);
				commWelfareDetailDao.insertEntity(welfareDetail);
			}
		}
		return id;
	}

	public CommWelfareApply getCommWelfareApply(CommWelfareApply t) {
		return this.commWelfareApplyDao.selectEntity(t);
	}

	public Integer getCommWelfareApplyCount(CommWelfareApply t) {
		return this.commWelfareApplyDao.selectEntityCount(t);
	}

	public List<CommWelfareApply> getCommWelfareApplyList(CommWelfareApply t) {
		return this.commWelfareApplyDao.selectEntityList(t);
	}

	@SuppressWarnings("unchecked")
	public int modifyCommWelfareApply(CommWelfareApply t) {
		int id=this.commWelfareApplyDao.updateEntity(t);
		if(t.getIs_del() == 1) {
			CommWelfareDetail welfareDetail = (CommWelfareDetail)t.getMap().get("welfareDetail");
			commWelfareDetailDao.updateEntity(welfareDetail);
			return id;
		}
		List<CommWelfareDetail> list=(List<CommWelfareDetail>) t.getMap().get("update_CommWelfareDetail");
		if(list!=null&&list.size()>0) {
			//先删
			CommWelfareDetail commd = new CommWelfareDetail();
			commd.getMap().put("comm_welfare_id", t.getId());
			commWelfareDetailDao.deleteEntity(commd);
			//后增
			for(CommWelfareDetail temp:list) {
				temp.setComm_welfare_id(t.getId());
				commWelfareDetailDao.insertEntity(temp);
			}
		}
		return id;
	}

	public int removeCommWelfareApply(CommWelfareApply t) {
		return this.commWelfareApplyDao.deleteEntity(t);
	}

	public List<CommWelfareApply> getCommWelfareApplyPaginatedList(CommWelfareApply t) {
		return this.commWelfareApplyDao.selectEntityPaginatedList(t);
	}

}
