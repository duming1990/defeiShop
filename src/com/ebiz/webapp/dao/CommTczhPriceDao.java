package com.ebiz.webapp.dao;

import java.util.List;

import com.ebiz.ssi.dao.EntityDao;
import com.ebiz.webapp.domain.CommTczhPrice;

/**
 * @author Wu,Yang
 * @version 2014-05-21 10:41
 */
public interface CommTczhPriceDao extends EntityDao<CommTczhPrice> {

	int updateCommTczhPriceInventory(CommTczhPrice t);

	public List<CommTczhPrice> selectCommTczhJoinCommInfoList(CommTczhPrice t);

}
