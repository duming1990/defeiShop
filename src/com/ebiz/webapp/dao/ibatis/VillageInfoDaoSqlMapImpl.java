package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.VillageInfoDao;
import com.ebiz.webapp.domain.VillageInfo;
import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;

/**
 * @author Wu,Yang
 * @version 2018-01-15 16:11
 */
@Service
public class VillageInfoDaoSqlMapImpl extends EntityDaoSqlMapImpl<VillageInfo> implements VillageInfoDao {

}
