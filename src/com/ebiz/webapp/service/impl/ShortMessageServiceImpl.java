package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.ShortMessageDao;
import com.ebiz.webapp.dao.ShortMessageReceiverDao;
import com.ebiz.webapp.domain.ShortMessage;
import com.ebiz.webapp.domain.ShortMessageReceiver;
import com.ebiz.webapp.service.ShortMessageService;
import com.ebiz.webapp.web.util.dysms.DySmsUtils;
import com.ebiz.webapp.web.util.dysms.SmsTemplate;

/**
 * @author Wu,Yang
 * @version 2018-07-02 10:24
 */
@Service
public class ShortMessageServiceImpl implements ShortMessageService {

	@Resource
	private ShortMessageDao shortMessageDao;

	@Resource
	private ShortMessageReceiverDao shortMessageReceiverDao;

	public Integer createShortMessage(ShortMessage t) {
		int id = shortMessageDao.insertEntity(t);
		List<ShortMessageReceiver> shortMessageReceiverList = t.getShortMessageReceiver();
		if (shortMessageReceiverList != null && shortMessageReceiverList.size() > 0) {
			for (ShortMessageReceiver shortMessageReceiver : shortMessageReceiverList) {
				shortMessageReceiver.setMsg_id(id);
				shortMessageReceiverDao.insertEntity(shortMessageReceiver);
				StringBuffer message = new StringBuffer("{\"mgs1\":\"" + t.getMessage() + "\"");
				// message.append(",\"mgs2\":\"" + t.getMessage1() + "\"");
				// message.append(",\"mgs3\":\"" + t.getMessage2() + "\"");
				// message.append(",\"mgs4\":\"" + t.getMessage3() + "\"");
				// message.append(",\"mgs5\":\"" + t.getMessage4() + "\"");
				message.append("}");
				// 发送短信(记得改webapp.configproperties阿里云数据)
				String mobile = (String) shortMessageReceiver.getMap().get("mobile");
				if (StringUtils.isNotBlank(mobile)) {
					System.out.println("==============mobile============" + mobile);
					DySmsUtils.sendMessage(message.toString(), mobile, SmsTemplate.sms_22);
				}
			}
		}
		return id;
	}

	public ShortMessage getShortMessage(ShortMessage t) {
		return this.shortMessageDao.selectEntity(t);
	}

	public Integer getShortMessageCount(ShortMessage t) {
		return this.shortMessageDao.selectEntityCount(t);
	}

	public List<ShortMessage> getShortMessageList(ShortMessage t) {
		return this.shortMessageDao.selectEntityList(t);
	}

	public int modifyShortMessage(ShortMessage t) {
		return this.shortMessageDao.updateEntity(t);
	}

	public int removeShortMessage(ShortMessage t) {
		return this.shortMessageDao.deleteEntity(t);
	}

	public List<ShortMessage> getShortMessagePaginatedList(ShortMessage t) {
		return this.shortMessageDao.selectEntityPaginatedList(t);
	}

}
