package com.ebiz.webapp.web.util.Message.Impl;

import javax.annotation.Resource;

import org.slf4j.LoggerFactory;

import push.AppPush;

import com.ebiz.webapp.dao.UserInfoDao;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.util.Message.Domain.MessageInfo;
import com.ebiz.webapp.web.util.Message.Interface.IMessage;

/**
 * 进行消息的推送
 * 
 * @author jiaorg
 */
public class PushMessage implements IMessage {
	protected final static org.slf4j.Logger logger = LoggerFactory.getLogger(PushMessage.class);

	@Resource
	private UserInfoDao userInfoDao;

	@Override
	public boolean sendMessage(MessageInfo message) {

		logger.info("==PushMessage==");
		if (message == null) {
			return false;
		}
		if (message.getMessageTitle() == null || "".equals(message.getMessageTitle())) {
			return false;
		}

		if (message.getUserInfo() == null && message.getUserInfo().getId() != null) {
			return false;
		}
		UserInfo userInfo = message.getUserInfo();
		String title = message.getMessageTitle();
		String content = message.getMessageCotent();
		String url = message.getMessageLinkUrl();
		boolean is_product = true;
		if (null != userInfo) {

			if (null != userInfo.getDevice_token_app()) {
				if (userInfo.getDevice_token_app().equals("android")) {
					try {
						AppPush.sendAndroidUnicastForNews(userInfo.getDevice_token(), title, content, url, is_product);
					} catch (Exception e) {
						e.printStackTrace();
						return false;
					}
				} else if (userInfo.getDevice_token_app().equals("ios")) {
					try {
						AppPush.sendIOSUnicastForNews(userInfo.getDevice_token(), title, url, is_product);
					} catch (Exception e) {
						e.printStackTrace();
						return false;
					}
				}
			}

		}
		return true;
	}

	@Override
	public boolean sendMessage(String message, String phone) {
		return false;
	}

}
