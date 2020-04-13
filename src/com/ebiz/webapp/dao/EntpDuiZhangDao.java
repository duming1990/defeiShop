package com.ebiz.webapp.dao;

import com.ebiz.webapp.domain.EntpDuiZhang;

import java.util.List;

import com.ebiz.ssi.dao.EntityDao;

/**
 * @author Wu,Yang
 * @version 2018-06-12 12:25
 */
public interface EntpDuiZhangDao extends EntityDao<EntpDuiZhang> {

	List<EntpDuiZhang> selectSettlementReport(EntpDuiZhang t);
}
