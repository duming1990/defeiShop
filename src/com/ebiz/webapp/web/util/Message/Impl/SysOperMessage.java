package com.ebiz.webapp.web.util.Message.Impl;

import java.util.Date;

import com.ebiz.webapp.dao.SysOperLogDao;
import com.ebiz.webapp.domain.SysOperLog;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.Keys.SysOperType;
import com.ebiz.webapp.web.util.Message.Domain.MessageInfo;
import com.ebiz.webapp.web.util.Message.Interface.IMessage;

/**
 * 填写日志
 * 
 * @author jiaorg
 */
public class SysOperMessage implements IMessage {

	private SysOperLogDao sysOperLogDao;

	@Override
	public boolean sendMessage(MessageInfo message) {
		int insertNum = 0;
		if (message != null && message.getMessageCotent() != null && !message.getMessageCotent().equals("")) {
			SysOperLog sysOperLog = new SysOperLog();
			sysOperLog.setOper_type(message.getMessageType());
			String operName = "";
			for (SysOperType sot : Keys.SysOperType.values()) {
				if (sot.getIndex() == message.getMessageType()) {
					operName = sot.getName();
					break;
				}
			}
			sysOperLog.setOper_name(operName);
			sysOperLog.setOper_uip(message.getIp());
			sysOperLog.setOper_time(new Date());
			sysOperLog.setOper_memo(message.getMessageCotent());
			if (message.getUserInfo() != null && message.getUserInfo().getId() != null) {
				sysOperLog.setOper_uid(message.getUserInfo().getId());

			}
			if (message.getUserInfo() != null && message.getUserInfo().getUser_name() != null) {
				sysOperLog.setOper_uname(message.getUserInfo().getUser_name());

			}
			sysOperLogDao = message.getSysOperLogDao();
			if (sysOperLogDao != null) {
				insertNum = sysOperLogDao.insertEntity(sysOperLog);
			}
			if (message.getSysOperLogService() != null) {
				insertNum = message.getSysOperLogService().createSysOperLog(sysOperLog);
			}
			// this.facade = (Facade) applicationContext.getBean("facade");
		}
		if (insertNum > 0) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean sendMessage(String message, String phone) {
		int insertNum = 0;
		if (message != null && message.equals("")) {
			SysOperLog sysOperLog = new SysOperLog();
			sysOperLog.setOper_type(Keys.SysOperType.SysOperType_1000.getIndex());
			String operName = "";
			for (SysOperType sot : Keys.SysOperType.values()) {
				if (sot.getIndex() == Keys.SysOperType.SysOperType_1000.getIndex()) {
					operName = sot.getName();
					break;
				}
			}
			sysOperLog.setOper_name(operName);
			sysOperLog.setOper_time(new Date());
			sysOperLog.setOper_memo(message);
			sysOperLog.setOper_uid(1);
			sysOperLog.setOper_uname("系统");

			if (sysOperLogDao != null) {
				insertNum = sysOperLogDao.insertEntity(sysOperLog);
			}

		}
		if (insertNum > 0) {
			return true;
		} else {
			return false;
		}
	}

}
