package com.ebiz.webapp.dao.ibatis;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.CommWelfareDetailDao;
import com.ebiz.webapp.domain.CommWelfareDetail;

/**
 * @author Wu,Yang
 * @version 2019-03-19 09:14
 */
@Service
public class CommWelfareDetailDaoSqlMapImpl extends EntityDaoSqlMapImpl<CommWelfareDetail> implements
		CommWelfareDetailDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<CommWelfareDetail> selectCommListWithWelfareDetail(CommWelfareDetail t) {
		return this.getSqlMapClientTemplate().queryForList("selectCommListWithWelfareDetail", t);
	}
}
