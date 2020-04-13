package com.ebiz.webapp.web.util.Message.Factory;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

import com.ebiz.webapp.web.util.Message.Domain.MessageInfo;
import com.ebiz.webapp.web.util.Message.Interface.IMessageFactory;

/**
 * 发送消息工厂，来根据策略调用不同的发送策略，发送消息
 * @author jiaorg
 *
 */
public class MessageFactory {

	protected final static org.slf4j.Logger logger = LoggerFactory.getLogger(MessageFactory.class);
	
	/**
	 * 新订单提醒
	 */
	public static String NewOrder ="neworder";
	
	/**
	 * 红包提醒
	 */
	public static String HongBao ="hongbao";

	/**
	 * 登陆
	 */
	public static String Login ="login";
	/**
	 * 用户等级提升
	 */
	public static String UserLevelUp ="userlevelup";	
	
	/**
	 * 用户自定义，必须配合messageType,
	 * 0表示系统日志
	 * 1表示推送消息
	 * 2表示短信
	 * 3表示站内短信
	 * 4表示微信消息
	 */
	public static String selfDefine = "self";
	
	public static boolean  sendMessage(String type,MessageInfo info,int[] messageType) {
		logger.info("==sendMessage==");
		if(type != null && type.equals(MessageFactory.NewOrder)){
			new NewOrderMessage().sendMessage(info);
		}
		if(type != null && type.equals(MessageFactory.HongBao)){
			new HongBaoMessage().sendMessage(info);
		}		
		if(type != null && type.equals(MessageFactory.selfDefine)){
			new SelfDefineMessage().sendMessage(messageType,info);
		}
		if(type != null && type.equals(MessageFactory.Login)){
			new LoginMessage().sendMessage(info);
		}	
		if(type != null && type.equals(MessageFactory.UserLevelUp)){
			new LoginMessage().sendMessage(info);
		}			
		return false;
	}


}
