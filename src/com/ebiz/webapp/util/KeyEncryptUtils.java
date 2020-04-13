package com.ebiz.webapp.util;

import java.io.UnsupportedEncodingException;

/**
 * @author Min,YongGang
 * @version 2010-09-10
 */
public class KeyEncryptUtils {

	public static final byte[] myEncode(byte[] s, int key) throws UnsupportedEncodingException {
		String str = Integer.toHexString(key);

		for (int i = 0; i < (8 - str.length()); i++) {
			str = '0' + str;
		}

		byte[] bkey = HexString2Bytes(str);
		byte[] ikey = new byte[4];
		for (int i = 0; i < 4; i++) {
			ikey[i] = (byte) (bkey[3 - i] & 0xFF);
		}

		for (int i = 0; i < s.length; i++) {
			s[i] = (byte) ((byte) (s[i] & 0xFF) ^ ikey[(i + 1) % 4]);
		}

		return s;

	}

	/**
	 * 将两个ASCII字符合成一个字节； 如："EF"--> 0xEF
	 * 
	 * @param src0 byte
	 * @param src1 byte
	 * @return byte
	 */
	public static byte uniteBytes(byte src0, byte src1) {
		byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 })).byteValue();
		_b0 = (byte) (_b0 << 4);
		byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 })).byteValue();
		byte ret = (byte) (_b0 ^ _b1);
		return ret;
	}

	/**
	 * 将指定字符串src，以每两个字符分割转换为16进制形式 如："2B44EFD9" --> byte[]{0x2B, 0x44, 0xEF, 0xD9}
	 * 
	 * @param src String
	 * @return byte[]
	 */
	public static byte[] HexString2Bytes(String src) {
		byte[] ret = new byte[4];
		byte[] tmp = src.getBytes();
		for (int i = 0; i < 4; i++) {
			ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
		}
		return ret;
	}

	public static int GetXORKey(String key) {
		int result = (int) (0x35894715);
		for (int i = 0; i < key.length(); i++) {
			result = result + (key.charAt(i) << (i + 1));
		}
		return result;
	}

	public static byte[] SplitToBytes(String str) {
		String[] strs = str.split("HITECH");
		byte[] bbs = new byte[strs.length];
		for (int i = 0; i < strs.length; i++) {// 输出结果
			bbs[i] = Byte.valueOf(strs[i]).byteValue();
		}
		return bbs;
	}
}
