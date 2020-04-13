package com.ebiz.webapp.dao;

import java.util.List;

import com.ebiz.ssi.dao.EntityDao;
import com.ebiz.webapp.domain.CommWelfareDetail;

/**
 * @author Wu,Yang
 * @version 2019-03-19 09:14
 */
public interface CommWelfareDetailDao extends EntityDao<CommWelfareDetail> {

	List<CommWelfareDetail> selectCommListWithWelfareDetail(CommWelfareDetail t);

}
