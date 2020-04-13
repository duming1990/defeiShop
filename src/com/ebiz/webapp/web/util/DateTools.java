package com.ebiz.webapp.web.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import com.ebiz.webapp.web.struts.BaseAction;

/**
 * @author Jin, QingHua
 */
public class DateTools extends BaseAction {

	public static Date getFirstSatudayOfMonth(int year, int month) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.set(year, month, 1);

		cal.setFirstDayOfWeek(Calendar.SATURDAY);
		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		return cal.getTime();
	}

	/**
	 * @author Wu,Yang
	 */
	public static Date getFirstSatudayOfWeek(Date date) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		cal.setFirstDayOfWeek(Calendar.SATURDAY);
		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		return cal.getTime();
	}

	/**
	 * @author Wu,Yang
	 */
	public static Date getFirstMondayOfWeek(Date date) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		return cal.getTime();
	}

	public static Date getNextWeekMonday(Date date) {
		Date a = DateUtils.addDays(date, -1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(a);
		cal.add(Calendar.WEEK_OF_YEAR, 1);// 一周
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

		return cal.getTime();
	}

	/**
	 * @author Liu,ZhiXiang
	 * @desc 上个月第一天
	 */
	public static Date getFirstDayOfLastMonday(Date date) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONDAY, -1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	/**
	 * @author Liu,ZhiXiang
	 * @desc 上个月最后一天
	 */
	public static Date getLastDayOfLastMonday(Date date) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DATE, -1);
		return cal.getTime();
	}

	/**
	 * @author Li,Ka
	 */
	public static Date changeString2Date(String parm) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		if (parm == null) {
			return null;
		}
		try {
			date = fmt.parse(parm);
		} catch (ParseException e) {
			return null;
		}
		return date;
	}

	/**
	 * @author Li,Ka
	 * @date 2011-05-08
	 * @desc 格式化日期为字符串
	 */
	public static String getStringDate(Date date) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = fmt.format(date);
		return dateStr;
	}

	public static String getStringDate(Date date, String Patten) {
		SimpleDateFormat fmt = new SimpleDateFormat(Patten);
		String dateStr = fmt.format(date);
		return dateStr;
	}

	/**
	 * 日期转换为date类型
	 * @author Li,Ka 
	 */
	public static Date changeString2DateTime(String parm) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		if (parm == null) {
			return null;
		}
		try {
			date = fmt.parse(parm);
		} catch (ParseException e) {
			return null;
		}
		return date;
	}

	/**
	 * 获取当前日期是星期几
	 * 
	 * @param dt
	 * @return 当前日期是星期几
	 */
	public static String getWeekOfDate(Date dt) {
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);

		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	/**
	 * 获取当前日期是星期几,返回序号
	 * 
	 * @param dt
	 * @return 当前日期是星期几
	 */
	public static int getWeekOfDateIndex(Date dt) {
		int[] weekDays = { 7, 1, 2, 3, 4, 5, 6 };
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);

		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	/**
	 * 获取两个时间之间相差的天数
	 * 
	 * @author gagaLiu
	 * @param startDay 开始天数
	 * @param endDay 结束天数
	 * @return
	 */
	/**
	 * 也可以使用 DurationFormatUtils.formatDuration(new Date().getTime() - oi.getOrder_date().getTime(), "d"); 参数 y years M
	 * months d days H hours m minutes s seconds S milliseconds
	 */

	public static int getXcDaysBetweenTwoDay(Date startDay, Date endDay) {

		Calendar startCal = Calendar.getInstance();
		startCal.setTime(startDay);

		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endDay);

		int days = ((int) (startCal.getTime().getTime() / 1000) - (int) (endCal.getTime().getTime() / 1000)) / 3600 / 24;

		return days;
	}

	public static boolean isDate(String parm) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		if (StringUtils.isBlank(parm)) {
			return false;
		}
		try {
			fmt.parse(parm);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	/**
	 * 时间加法 calendar.add(1,-1)表示年份减一. calendar.add(2,-1)表示月份减一. calendar.add(3.-1)表示周减一. calendar.add(5,-1)表示天减一.
	 */
	public static String addDay(Date date, int year_monteh_week_day, int day_count) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(year_monteh_week_day, day_count);
		Date tasktime = calendar.getTime();
		return df.format(tasktime);
	}

	/**
	 * 获取这个月的第一天
	 */
	public static String getFirstDayThisMonth() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);
		String firstDay = sdFormat_ymd.format(c.getTime());
		return firstDay;
	}

	/**
	 * 获取这个月的最后一天
	 */
	public static String getLastDayThisMonth() {
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		String lastDay = sdFormat_ymd.format(ca.getTime());
		return lastDay;
	}

	/**
	 * 获取这个月的第一天
	 */
	public static String getFirstDayThisMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);
		String firstDay = sdFormat_ymd.format(c.getTime());
		return firstDay;
	}

	/**
	 * 获取这个月的最后一天
	 */
	public static String getLastDayThisMonth(Date date) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		String lastDay = sdFormat_ymd.format(ca.getTime());
		return lastDay;
	}

	/**
	 * 获取昨天 大昨天 lastDays 往前推几天
	 */
	public static String getLastDay(int lastDays) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -lastDays);
		String yesterday = sdFormat_ymd.format(cal.getTime());
		return yesterday;
	}

	/**
	 * 获取固定时间 往前推几天
	 */
	public static String getTheDayLastDay(Date date, int lastDays) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -lastDays);
		String yesterday = sdFormat_ymd.format(cal.getTime());
		return yesterday;
	}

	/**
	 * 判断时间是否在时间段内
	 * 
	 * @param date 当前时间 yyyy-MM-dd HH:mm:ss
	 * @param strDateBegin 开始时间 00:00:00
	 * @param strDateEnd 结束时间 00:05:00
	 * @return
	 */
	public static boolean isInDate(Date date, String strDateBegin, String strDateEnd) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate = sdf.format(date);

		// 截取当前时间时分秒
		int strDateH = Integer.parseInt(strDate.substring(11, 13));
		int strDateM = Integer.parseInt(strDate.substring(14, 16));
		int strDateS = Integer.parseInt(strDate.substring(17, 19));
		// 截取开始时间时分秒
		int strDateBeginH = Integer.parseInt(strDateBegin.substring(0, 2));
		int strDateBeginM = Integer.parseInt(strDateBegin.substring(3, 5));
		int strDateBeginS = Integer.parseInt(strDateBegin.substring(6, 8));
		// 截取结束时间时分秒
		int strDateEndH = Integer.parseInt(strDateEnd.substring(0, 2));
		int strDateEndM = Integer.parseInt(strDateEnd.substring(3, 5));
		int strDateEndS = Integer.parseInt(strDateEnd.substring(6, 8));

		if ((strDateH >= strDateBeginH && strDateH <= strDateEndH)) {
			// 当前时间小时数在开始时间和结束时间小时数之间
			if (strDateH > strDateBeginH && strDateH < strDateEndH) {
				return true;
				// 当前时间小时数等于开始时间小时数，分钟数在开始和结束之间
			} else if (strDateH == strDateBeginH && strDateM >= strDateBeginM && strDateM <= strDateEndM) {
				return true;
				// 当前时间小时数等于开始时间小时数，分钟数等于开始时间分钟数，秒数在开始和结束之间
			} else if (strDateH == strDateBeginH && strDateM == strDateBeginM && strDateS >= strDateBeginS
					&& strDateS <= strDateEndS) {
				return true;
			}
			// 当前时间小时数大等于开始时间小时数，等于结束时间小时数，分钟数小等于结束时间分钟数
			else if (strDateH >= strDateBeginH && strDateH == strDateEndH && strDateM <= strDateEndM) {
				return true;
				// 当前时间小时数大等于开始时间小时数，等于结束时间小时数，分钟数等于结束时间分钟数，秒数小等于结束时间秒数
			} else if (strDateH >= strDateBeginH && strDateH == strDateEndH && strDateM == strDateEndM
					&& strDateS <= strDateEndS) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 日期比较
	 * @param dt1
	 * @param dt2
	 * @return 1表示dt1大于dt2,0表示相等，-1表示d1小于d2
	 */
	public static int compareDate(Date dt1, Date dt2) {

		try {
			if (dt1.getTime() > dt2.getTime()) {
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

}
