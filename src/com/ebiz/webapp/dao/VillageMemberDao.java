package com.ebiz.webapp.dao;

import com.ebiz.webapp.domain.VillageMember;
import com.ebiz.ssi.dao.EntityDao;

/**
 * @author Wu,Yang
 * @version 2018-01-26 15:45
 */
public interface VillageMemberDao extends EntityDao<VillageMember> {
	Integer selectVillageMemberCountByPIndex(VillageMember t);
}
