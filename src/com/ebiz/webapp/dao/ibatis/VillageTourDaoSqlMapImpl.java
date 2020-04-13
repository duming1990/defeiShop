package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.VillageTourDao;
import com.ebiz.webapp.domain.VillageTour;
import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;

/**
 * @author Wu,Yang
 * @version 2018-08-22 14:47
 */
@Service
public class VillageTourDaoSqlMapImpl extends EntityDaoSqlMapImpl<VillageTour> implements VillageTourDao {

}
