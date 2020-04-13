package com.ebiz.webapp.dao;

import java.util.List;

import com.ebiz.ssi.dao.EntityDao;
import com.ebiz.webapp.domain.VillageContactGroup;

/**
 * @author Wu,Yang
 * @version 2018-01-26 15:45
 */
public interface VillageContactGroupDao extends EntityDao<VillageContactGroup> {
	public List<VillageContactGroup> selectVillageContactGroupNameAndCount(VillageContactGroup t);
}
