package com.ebiz.webapp.web.util;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Arrays;

public class DoorSocketUtils {

	/**
	 * 告诉服务器socket需要找到对应的机器开门了
	 * 
	 * @return
	 */
	public static void openDoor(String device_no, String socket_ip, Integer socket_port) throws Exception {

		Socket socket = new Socket(socket_ip, socket_port);

		// 向服务器端发送数据
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());

		out.write(openDoorSetData(device_no));
	}

	/**
	 * 告诉服务器socket提示语音
	 * 
	 * @return
	 */
	public static void sendErrorMessage(String msg, String socket_ip, Integer socket_port) throws Exception {

		Socket socket = new Socket(socket_ip, socket_port);

		// 向服务器端发送数据
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());

		byte[] byte_1 = new byte[1];
		byte_1[0] = (byte) 0x01;

		out.write(byteMerger(byte_1, msg.getBytes()));
	}

	/**
	 * 告诉服务器socket需要找到对应的机器开门了
	 * 
	 * @return
	 */
	public static byte[] openDoorSetData(String device_no) throws Exception {

		byte[] add = new byte[17];
		add[0] = (byte) 0xfa;

		add[1] = 0x00;
		add[2] = 0x00;
		add[3] = 0x00;

		add[4] = 0x00;
		add[5] = 0x09;

		// 9位

		byte[] deviceNoByte = device_no.getBytes();

		for (int i = 0; i < deviceNoByte.length; i++) {
			add[i + 6] = deviceNoByte[i];
		}

		byte[] newData = Arrays.copyOfRange(add, 1, 15);
		Integer str = CRC16.getCRC(newData);
		int low = str & 0xff;
		int high = str >>> 8;
		// crc编码
		add[15] = (byte) high;
		add[16] = (byte) low;
		return add;
	}

	public static byte[] byteMerger(byte[] byte_1, byte[] byte_2) {
		byte[] byte_3 = new byte[byte_1.length + byte_2.length];
		System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);
		System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);
		return byte_3;
	}

}
