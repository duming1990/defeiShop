package com.ebiz.webapp.util;

public class MapUtils {

	private static double EARTH_RADIUS = 6371.393;

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// http://www.gpsspg.com/maps.htm 查看经纬度网站
		// 软件园31.8557568331, 117.2093592096
		// 元一柏庄 31.8488978695, 117.3236935382
		// 邮政31.8587678738, 117.2092426312
		//
		// System.out.println(MapUtils.GetDistance(31.85165, 117.197505, 31.8587678738, 117.2092426312));
		System.out.println(MapUtils.GetDistance(31.8557568331, 117.2093592096, 31.8587678738, 117.2092426312));

	}

	/**
	 * 计算两个经纬度之间的距离
	 * 
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * @return
	 */
	public static double GetDistance(double lat1, double lng1, double lat2, double lng2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;// -0.0030110407
		double b = rad(lng1) - rad(lng2);// 0.0001165784
		System.out.println("经度相减radLat1 - radLat2:" + a);
		System.out.println("纬度相减rad(lng1) - rad(lng2):" + a);
		System.out.println("(Math.pow(Math.sin(a / 2), 2):" + (Math.pow(Math.sin(a / 2), 2)));
		System.out.println("(Math.cos(radLat1):" + Math.cos(radLat1));
		System.out.println("Math.cos(radLat2):" + Math.cos(radLat2));
		System.out.println("Math.pow(Math.sin(b / 2), 2)):" + Math.pow(Math.sin(b / 2), 2));

		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));

		// Math.pow(Math.sin(a / 2), 2 :-0.0030110407
		// Math.cos(radLat1) 0.999999998619
		s = s * EARTH_RADIUS;
		s = Math.round(s * 1000);
		return s;
	}
}
