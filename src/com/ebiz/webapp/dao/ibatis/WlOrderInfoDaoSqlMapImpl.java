package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.WlOrderInfoDao;
import com.ebiz.webapp.domain.WlOrderInfo;

/**
 * @author Wu,Yang
 * @version 2014-05-25 11:54
 */
@Service
public class WlOrderInfoDaoSqlMapImpl extends EntityDaoSqlMapImpl<WlOrderInfo> implements WlOrderInfoDao {

}
