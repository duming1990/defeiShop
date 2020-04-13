package com.ebiz.webapp.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.ebiz.ssi.dao.EntityDao;
import com.ebiz.webapp.domain.BaseClass;

/**
 * @author Wu,Yang
 * @version 2012-05-30 08:53
 */
public interface BaseClassDao extends EntityDao<BaseClass> {
	BaseClass procedureUpdateClass(BaseClass t) throws DataAccessException;

	List<BaseClass> procedureGetBaseClassParentList(BaseClass t);

	List<BaseClass> procedureGetBaseClassSonList(BaseClass t);
}
