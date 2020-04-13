package com.ebiz.webapp.dao.ibatis;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.CommInfoDao;
import com.ebiz.webapp.domain.CommInfo;

/**
 * @author Wu,Yang
 * @version 2013-09-26 11:38
 */
@Service
public class CommInfoDaoSqlMapImpl extends EntityDaoSqlMapImpl<CommInfo> implements CommInfoDao {
	@SuppressWarnings("unchecked")
	public List<CommInfo> selectCommInfoForDistinct(CommInfo t) {
		return this.getSqlMapClientTemplate().queryForList("selectCommInfoForDistinct", t);
	}

	@SuppressWarnings("unchecked")
	public List<CommInfo> selectCommInfoPageList(CommInfo t) {
		return this.getSqlMapClientTemplate().queryForList("selectCommInfoPageList", t);
	}

	public Integer selectCommInfoForSearchAttrCount(CommInfo t) {
		return (Integer) super.getSqlMapClientTemplate().queryForObject("selectCommInfoForSearchAttrCount", t);
	}

	public Integer selectCommInfoNameCount(CommInfo t) {
		return (Integer) super.getSqlMapClientTemplate().queryForObject("selectCommInfoNameCount", t);
	}

	@SuppressWarnings("unchecked")
	public List<CommInfo> selectCommInfoForSearchAttrPaginatedList(CommInfo t) {
		return this.getSqlMapClientTemplate().queryForList("selectCommInfoForSearchAttrPaginatedList", t);
	}

	public int updateCommInfoMinPrice(CommInfo t) {
		return this.getSqlMapClientTemplate().update("updateCommInfoMinPrice", t);
	}

	public int updateCommInfoInventory(CommInfo t) {
		return this.getSqlMapClientTemplate().update("updateCommInfoInventory", t);
	}

	@SuppressWarnings("unchecked")
	public List<CommInfo> selectCommInfoListTwo(CommInfo t) {
		return super.getSqlMapClientTemplate().queryForList("selectCommInfoListTwo", t);
	}

	public Integer selectClassRankingListCount(CommInfo t) {
		return (Integer) super.getSqlMapClientTemplate().queryForObject("selectClassRankingListCount", t);
	}

	@SuppressWarnings("unchecked")
	public List<CommInfo> selectClassRankingList(CommInfo t) {
		return super.getSqlMapClientTemplate().queryForList("selectClassRankList", t);
	}

	@SuppressWarnings("unchecked")
	public List<CommInfo> selectCommInfoRootClsId(CommInfo t) {
		return super.getSqlMapClientTemplate().queryForList("selectCommInfoRootClsId", t);
	}

	public Integer selectCommInfoCountForFuPin(CommInfo t) {
		return (Integer) super.getSqlMapClientTemplate().queryForObject("selectCommInfoCountForFuPin", t);
	}

	@SuppressWarnings("unchecked")
	public List<CommInfo> selectCommInfoPaginatedListForFuPin(CommInfo t) {
		return this.getSqlMapClientTemplate().queryForList("selectCommInfoPaginatedListForFuPin", t);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CommInfo> selectWelfareCommInfoList(CommInfo t) {
		return this.getSqlMapClientTemplate().queryForList("selectWelfareCommInfoList", t);
	}

	@Override
	public CommInfo selectCommInfoByOrderId(Integer id) {
		return (CommInfo) this.getSqlMapClientTemplate().queryForObject("selectCommInfoByOrderId", id);
	}
}
