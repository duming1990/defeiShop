package com.ebiz.webapp.web.util.Message.Interface;

import com.ebiz.webapp.web.util.Message.Domain.MessageInfo;

public interface IMessageFactory {
	boolean sendMessage(MessageInfo info);
	boolean sendMessage(int[] messageType,MessageInfo info);
}
