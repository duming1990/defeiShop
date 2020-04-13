package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.CommTczhAttributeDao;
import com.ebiz.webapp.domain.CommTczhAttribute;

/**
 * @author Wu,Yang
 * @version 2014-05-21 10:41
 */
@Service
public class CommTczhAttributeDaoSqlMapImpl extends EntityDaoSqlMapImpl<CommTczhAttribute> implements
		CommTczhAttributeDao {

	public CommTczhAttribute selectCommTczhAttributeForGetCommTczhId(CommTczhAttribute t) {
		return (CommTczhAttribute) this.getSqlMapClientTemplate().queryForObject(
				"selectCommTczhAttributeForGetCommTczhId", t);
	}

}
