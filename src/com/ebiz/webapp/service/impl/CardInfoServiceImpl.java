package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.ebiz.webapp.dao.CardGenHisDao;
import com.ebiz.webapp.dao.CardInfoDao;
import com.ebiz.webapp.domain.CardInfo;
import com.ebiz.webapp.service.CardInfoService;
import com.ebiz.webapp.web.util.dysms.DySmsUtils;
import com.ebiz.webapp.web.util.dysms.SmsTemplate;

/**
 * @author Wu,Yang
 * @version 2019-03-19 14:37
 */
@Service
public class CardInfoServiceImpl implements CardInfoService {

	@Resource
	private CardInfoDao cardInfoDao;

	@Resource
	private CardGenHisDao cardGenHisDao;

	public Integer createCardInfo(CardInfo t) {
		return this.cardInfoDao.insertEntity(t);
	}

	public Integer createCardInfoByList(CardInfo t) {
		return this.cardInfoDao.insertCardInfoByList(t);
	}

	public CardInfo getCardInfo(CardInfo t) {
		return this.cardInfoDao.selectEntity(t);
	}

	public Integer getCardInfoCount(CardInfo t) {
		return this.cardInfoDao.selectEntityCount(t);
	}

	public List<CardInfo> getCardInfoList(CardInfo t) {
		return this.cardInfoDao.selectEntityList(t);
	}

	public int modifyCardInfo(CardInfo t) {
		int id = this.cardInfoDao.updateEntity(t);
		if (null != t.getMap().get("send_msg")) {
			StringBuffer message = (StringBuffer) t.getMap().get("send_msg");
			String template_code = SmsTemplate.sms_26;
			SendSmsResponse sendSmsResponse = DySmsUtils.sendMessage(message.toString(), t.getReceived_mobile(),
					template_code);
			if (sendSmsResponse == null || !"OK".equals(sendSmsResponse.getCode())) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return -1;
			}
		}
		return id;
	}

	public int removeCardInfo(CardInfo t) {
		return this.cardInfoDao.deleteEntity(t);
	}

	public List<CardInfo> getCardInfoPaginatedList(CardInfo t) {
		return this.cardInfoDao.selectEntityPaginatedList(t);
	}

}
