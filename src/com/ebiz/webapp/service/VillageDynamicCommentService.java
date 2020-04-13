package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.VillageDynamicComment;

/**
 * @author Wu,Yang
 * @version 2018-01-26 15:45
 */
public interface VillageDynamicCommentService {

	Integer createVillageDynamicComment(VillageDynamicComment t);

	int modifyVillageDynamicComment(VillageDynamicComment t);

	int removeVillageDynamicComment(VillageDynamicComment t);

	VillageDynamicComment getVillageDynamicComment(VillageDynamicComment t);

	List<VillageDynamicComment> getVillageDynamicCommentList(VillageDynamicComment t);

	Integer getVillageDynamicCommentCount(VillageDynamicComment t);

	List<VillageDynamicComment> getVillageDynamicCommentPaginatedList(VillageDynamicComment t);

}