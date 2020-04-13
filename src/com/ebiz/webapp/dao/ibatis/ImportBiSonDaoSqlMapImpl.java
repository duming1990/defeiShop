package com.ebiz.webapp.dao.ibatis;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.ImportBiSonDao;
import com.ebiz.webapp.domain.ImportBiSon;

/**
 * @author Wu,Yang
 * @version 2019-02-22 17:51
 */
@Service
public class ImportBiSonDaoSqlMapImpl extends EntityDaoSqlMapImpl<ImportBiSon> implements ImportBiSonDao {

	@Override
	@SuppressWarnings("unchecked")
	public List<ImportBiSon> selectImportBiSonLeftUserList(ImportBiSon t) {
		return super.getSqlMapClientTemplate().queryForList("selectImportBiSonLeftUserList", t);
	}
}
