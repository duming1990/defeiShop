package com.ebiz.webapp.dao.ibatis;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.BaseClassDao;
import com.ebiz.webapp.domain.BaseClass;

/**
 * @author Wu,Yang
 * @version 2012-05-30 08:53
 */
@Service
public class BaseClassDaoSqlMapImpl extends EntityDaoSqlMapImpl<BaseClass> implements BaseClassDao {

	public BaseClass procedureUpdateClass(BaseClass t) throws DataAccessException {
		return (BaseClass) super.getSqlMapClientTemplate().queryForObject("procedureUpdateClass", t);
	}

	@SuppressWarnings("unchecked")
	public List<BaseClass> procedureGetBaseClassSonList(BaseClass t) {
		return super.getSqlMapClientTemplate().queryForList("procedureGetBaseClassSonList", t);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BaseClass> procedureGetBaseClassParentList(BaseClass t) {
		return super.getSqlMapClientTemplate().queryForList("procedureGetBaseClassParentList", t);
	}

}
