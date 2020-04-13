package com.ebiz.webapp.web.util.Message.Domain;

import java.io.Serializable;

import com.ebiz.ssi.domain.BaseDomain;
import com.ebiz.webapp.dao.SysOperLogDao;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.service.SysOperLogService;

public class MessageInfo extends BaseDomain implements Serializable {

	/**
	 * 用户信息
	 */

	private UserInfo userInfo;

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	private String ip;

	private SysOperLogService sysOperLogService;

	public SysOperLogService getSysOperLogService() {
		return sysOperLogService;
	}

	public void setSysOperLogService(SysOperLogService sysOperLogService) {
		this.sysOperLogService = sysOperLogService;
	}

	/**
	 * 操作日志dao，必须要传值，不然没法操作数据
	 */
	private SysOperLogDao sysOperLogDao;

	public SysOperLogDao getSysOperLogDao() {
		return sysOperLogDao;
	}

	public void setSysOperLogDao(SysOperLogDao sysOperLogDao) {
		this.sysOperLogDao = sysOperLogDao;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 标题
	 */
	private String messageTitle;

	/*
	 * 内容
	 */
	private String messageCotent;

	/**
	 * qq 号码
	 */
	private String QQ;

	/**
	 * 微信号码
	 */
	private String WeiXin;

	private String userName;

	private String messageLinkUrl;

	public String getMessageLinkUrl() {
		return messageLinkUrl;
	}

	public void setMessageLinkUrl(String messageLinkUrl) {
		this.messageLinkUrl = messageLinkUrl;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	private int messageType;

	// 电话类型，0表示android，1表示ios
	private int phoneType;

	// 发送的设备id
	private String deviceToken;

	/**
	 * 用户ID
	 */
	private int userId;

	/**
	 * 电话号码
	 */
	private String phoneNum;

	public String getMessageTitle() {
		return messageTitle;
	}

	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}

	public String getMessageCotent() {
		return messageCotent;
	}

	public void setMessageCotent(String messageCotent) {
		this.messageCotent = messageCotent;
	}

	public String getQQ() {
		return QQ;
	}

	public void setQQ(String qQ) {
		QQ = qQ;
	}

	public String getWeiXin() {
		return WeiXin;
	}

	public void setWeiXin(String weiXin) {
		WeiXin = weiXin;
	}

	public int getMessageType() {
		return messageType;
	}

	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}

	public int getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(int phoneType) {
		this.phoneType = phoneType;
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
