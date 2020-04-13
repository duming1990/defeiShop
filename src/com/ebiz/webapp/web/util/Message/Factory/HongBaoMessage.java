package com.ebiz.webapp.web.util.Message.Factory;

import com.ebiz.webapp.web.util.Message.Domain.MessageInfo;
import com.ebiz.webapp.web.util.Message.Impl.PushMessage;
import com.ebiz.webapp.web.util.Message.Impl.SysOperMessage;
import com.ebiz.webapp.web.util.Message.Interface.IMessage;
import com.ebiz.webapp.web.util.Message.Interface.IMessageFactory;

/**
 * 红包提醒
 * @author jiaorg
 *
 */
public class HongBaoMessage  implements IMessageFactory{


	@Override
	public boolean sendMessage(MessageInfo info) {
		 new SysOperMessage().sendMessage(info);
		 new PushMessage().sendMessage(info);
 		return true;
	}

	@Override
	public boolean sendMessage(int[] messageType,MessageInfo info) {
		// TODO Auto-generated method stub
		return false;
	}
}
