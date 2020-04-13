package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.PdImgsDao;
import com.ebiz.webapp.domain.PdImgs;

/**
 * @author Wu,Yang
 * @version 2012-03-21 17:16
 */
@Service
public class PdImgsDaoSqlMapImpl extends EntityDaoSqlMapImpl<PdImgs> implements PdImgsDao {

}
