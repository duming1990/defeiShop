package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.CommInfoPromotion;

/**
 * @author Wu,Yang
 * @version 2017-04-21 17:10
 */
public interface CommInfoPromotionService {

	Integer createCommInfoPromotion(CommInfoPromotion t);

	int modifyCommInfoPromotion(CommInfoPromotion t);

	int removeCommInfoPromotion(CommInfoPromotion t);

	CommInfoPromotion getCommInfoPromotion(CommInfoPromotion t);

	List<CommInfoPromotion> getCommInfoPromotionList(CommInfoPromotion t);

	Integer getCommInfoPromotionCount(CommInfoPromotion t);

	List<CommInfoPromotion> getCommInfoPromotionPaginatedList(CommInfoPromotion t);

}