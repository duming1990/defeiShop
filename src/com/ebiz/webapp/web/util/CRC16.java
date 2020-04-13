package com.ebiz.webapp.web.util;

import java.math.BigInteger;
import java.util.Calendar;

/*
 * crc校验,输入一个数组,返回一个数组,返回的数组比原数组
 * 多了两个字节,也就是两个校验码,低字节在前,高字节在后.
 */
public class CRC16 {
	/**
	 * 计算CRC16校验码
	 * 
	 * @param bytes 字节数组
	 * @return {@link String} 校验码
	 * @since 1.0
	 */
	public static Integer getCRC(byte[] bytes) {
		int CRC = 0x0000;
		int POLYNOMIAL = 0x0000a001;
		int i, j;
		for (i = 0; i < bytes.length; i++) {
			CRC ^= ((int) bytes[i] & 0x000000ff);
			for (j = 0; j < 8; j++) {
				if ((CRC & 0x00000001) != 0) {
					CRC >>= 1;
					CRC ^= POLYNOMIAL;
				} else {
					CRC >>= 1;
				}
			}
		}

		return CRC;
	}

	/**
	 * 将16进制单精度浮点型转换为10进制浮点型
	 * 
	 * @return float
	 * @since 1.0
	 */
	private static float parseHex2Float(String hexStr) {
		BigInteger bigInteger = new BigInteger(hexStr, 16);
		return Float.intBitsToFloat(bigInteger.intValue());
	}

	/**
	 * 将十进制浮点型转换为十六进制浮点型
	 * 
	 * @return String
	 * @since 1.0
	 */
	private String parseFloat2Hex(float data) {
		return Integer.toHexString(Float.floatToIntBits(data));
	}

	// 这个主函数用来测试用的
	public static void main(String args[]) {
		Calendar c = Calendar.getInstance();// 可以对每个时间域单独修改
		byte[] returnD = new byte[19];
		// 头
		returnD[0] = 0x0;
		returnD[1] = 0x0;
		returnD[2] = 0x0;
		returnD[3] = 0x0;
		// 有效长度
		returnD[4] = 0x14;
		// 指令
		returnD[5] = 0x50;
		// 枪号
		returnD[6] = 0x1;// 枪号
		// 思维标识钱数
		returnD[7] = 0x0;
		returnD[8] = 0x0;
		returnD[9] = 0x1;
		returnD[10] = 0x0;
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		int second = c.get(Calendar.SECOND);
		String stryear = year + "";
		String strmonth = month + "";
		String strday = day + "";
		String strhour = hour + "";
		String strminute = minute + "";
		String strhsecond = second + "";

		// 支付型号，传输标识号，可以是支付订单id，也可以是其他信息
		returnD[11] = (byte) year;
		returnD[12] = (byte) month;
		returnD[13] = (byte) day;
		returnD[14] = (byte) hour;
		returnD[15] = (byte) minute;
		returnD[16] = (byte) second;
		returnD[17] = (byte) second;
		returnD[18] = (byte) second;
		int[] data = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		int[] newData;

		// System.out.println(ByteUtils.hexStringToBytes(getCRC(returnD)).length);
		// 00 00 00 00 56 18 66 10 40 23 81 66 33 66 32
		byte[] data1 = { 0x00, 0x00, 0x00, 0x00, 0x56, 0x18, 0x66, 0x10, 0x40, 0x23, (byte) 0x81, 0x66, 0x33 };
		// byte[] crc = ByteUtils.hexStringToBytes(getCRC(data1));

		Integer str = getCRC(data1);
		int low = str & 0xff;
		int high = str >>> 8;
		System.out.println(str);
		System.out.println("crc0=" + low);
		System.out.println("crc1=" + high);
	}
}