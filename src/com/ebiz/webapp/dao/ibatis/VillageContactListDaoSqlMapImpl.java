package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.VillageContactListDao;
import com.ebiz.webapp.domain.VillageContactList;
import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;

/**
 * @author Wu,Yang
 * @version 2018-01-26 15:45
 */
@Service
public class VillageContactListDaoSqlMapImpl extends EntityDaoSqlMapImpl<VillageContactList> implements VillageContactListDao {

}
