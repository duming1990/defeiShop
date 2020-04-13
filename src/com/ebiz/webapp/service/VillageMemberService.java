package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.VillageMember;

/**
 * @author Wu,Yang
 * @version 2018-01-26 15:45
 */
public interface VillageMemberService {

	Integer createVillageMember(VillageMember t);

	int modifyVillageMember(VillageMember t);

	int removeVillageMember(VillageMember t);

	VillageMember getVillageMember(VillageMember t);

	List<VillageMember> getVillageMemberList(VillageMember t);

	Integer getVillageMemberCount(VillageMember t);

	List<VillageMember> getVillageMemberPaginatedList(VillageMember t);

	Integer getVillageMemberCountByPIndex(VillageMember t);
}