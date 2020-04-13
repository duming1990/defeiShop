package com.ebiz.webapp.web.util.Message.Interface;

import com.ebiz.webapp.web.util.Message.Domain.MessageInfo;

public interface IMessage {
	boolean sendMessage(MessageInfo message);

	boolean sendMessage(String message, String phone);
}
