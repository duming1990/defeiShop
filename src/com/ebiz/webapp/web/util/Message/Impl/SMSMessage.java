package com.ebiz.webapp.web.util.Message.Impl;

import com.ebiz.webapp.web.util.SmsUtils;
import com.ebiz.webapp.web.util.Message.Domain.MessageInfo;
import com.ebiz.webapp.web.util.Message.Interface.IMessage;

/**
 * 短信发送
 * 
 * @author jiaorg
 */
public class SMSMessage implements IMessage {

	@Override
	public boolean sendMessage(MessageInfo message) {
		int errCode = 0;// 默认成功
		if (message == null) {
			errCode = -1;
		}
		if (message.getPhoneNum() == null || message.getPhoneNum().equals("")) {
			errCode = 1;
		}
		if (message.getMessageCotent() == null || message.getMessageCotent().equals("")) {
			errCode = 2;
		}
		if (errCode != 0) {

			return false;
		} else {
			String returnMsg = SmsUtils.sendMessage(message.getMessageCotent(), message.getPhoneNum());
			if (returnMsg.equals("0")) {
				return true;
			} else {
				return false;
			}
		}

	}

	@Override
	public boolean sendMessage(String message, String phone) {
		MessageInfo mess = new MessageInfo();
		mess.setMessageCotent(message);
		mess.setPhoneNum(phone);
		return sendMessage(mess);
	}

}
