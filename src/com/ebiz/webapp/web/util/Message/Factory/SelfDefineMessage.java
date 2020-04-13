package com.ebiz.webapp.web.util.Message.Factory;

import com.ebiz.webapp.web.util.Message.Domain.MessageInfo;
import com.ebiz.webapp.web.util.Message.Impl.PushMessage;
import com.ebiz.webapp.web.util.Message.Impl.SMSMessage;
import com.ebiz.webapp.web.util.Message.Impl.SiteMessage;
import com.ebiz.webapp.web.util.Message.Impl.SysOperMessage;
import com.ebiz.webapp.web.util.Message.Impl.WeiXinMessage;
import com.ebiz.webapp.web.util.Message.Interface.IMessageFactory;


public class SelfDefineMessage  implements IMessageFactory{




	@Override
	public boolean sendMessage(MessageInfo info) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean sendMessage(int[] messageType, MessageInfo info) {
		for(int i:messageType){
			if(i == 0){
				new SysOperMessage().sendMessage(info);
			}
			if(i == 1){
				new PushMessage().sendMessage(info);
			}
			if(i == 2){
				new SMSMessage().sendMessage(info);
			}
			if(i == 3){
				new SiteMessage().sendMessage(info);
			}
			if(i == 4){
				new WeiXinMessage().sendMessage(info);
			}
		}
		return false;
	}
}
