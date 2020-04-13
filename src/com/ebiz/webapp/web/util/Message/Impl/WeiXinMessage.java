package com.ebiz.webapp.web.util.Message.Impl;

import com.ebiz.webapp.web.util.Message.Domain.MessageInfo;
import com.ebiz.webapp.web.util.Message.Interface.IMessage;

public class WeiXinMessage implements IMessage {

	@Override
	public boolean sendMessage(MessageInfo message) {
		return false;
	}

	@Override
	public boolean sendMessage(String message, String phone) {
		return false;
	}

}
