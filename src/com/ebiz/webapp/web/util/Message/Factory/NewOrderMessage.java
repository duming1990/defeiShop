package com.ebiz.webapp.web.util.Message.Factory;

import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.util.Message.Domain.MessageInfo;
import com.ebiz.webapp.web.util.Message.Impl.PushMessage;
import com.ebiz.webapp.web.util.Message.Impl.SMSMessage;
import com.ebiz.webapp.web.util.Message.Impl.SysOperMessage;
import com.ebiz.webapp.web.util.Message.Interface.IMessageFactory;

public class NewOrderMessage implements IMessageFactory {

	@Override
	public boolean sendMessage(MessageInfo info) {
		new SysOperMessage().sendMessage(info);// 日志
		new PushMessage().sendMessage(info);// 推送
		// 如果电话号码没有传递，则从用户表进行获取；
		if (info.getPhoneNum() == null || "".equals(info.getPhoneNum())) {
			UserInfo user = info.getUserInfo();
			if (user != null && user.getMobile() != null) {
				info.setPhoneNum(user.getMobile());
			}
		}
		new SMSMessage().sendMessage(info);// 短信

		return false;
	}

	@Override
	public boolean sendMessage(int[] messageType, MessageInfo info) {
		// TODO Auto-generated method stub
		return false;
	}
}
