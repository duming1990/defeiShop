package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.NewsAttachment;

/**
 * @author Wu,Yang
 * @version 2011-11-22 16:51
 */
public interface NewsAttachmentService {

	Integer createNewsAttachment(NewsAttachment t);

	int modifyNewsAttachment(NewsAttachment t);

	int removeNewsAttachment(NewsAttachment t);

	NewsAttachment getNewsAttachment(NewsAttachment t);

	List<NewsAttachment> getNewsAttachmentList(NewsAttachment t);

	Integer getNewsAttachmentCount(NewsAttachment t);

	List<NewsAttachment> getNewsAttachmentPaginatedList(NewsAttachment t);

}