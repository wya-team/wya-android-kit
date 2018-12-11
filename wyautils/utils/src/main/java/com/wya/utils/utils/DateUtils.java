package com.wya.utils.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * author : XuDonglin
 * e-mail : 465715784@qq.com
 * time   : 2018/12/11
 * desc   :	时间格式化工具类
 * version: 1.0
 */
@SuppressLint("SimpleDateFormat")
public class DateUtils {

	/**
	 * 时间戳转字符串时间
	 *
	 * @param stamp   时间戳
	 * @param pattern 时间样式
	 * @return 字符串类型的时间
	 */
	public static String stamp2date(long stamp, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(new Date(stamp));
	}

	/**
	 * 时间戳转字符串时间 yyyy-MM-dd HH:mm:ss
	 *
	 * @param stamp 时间戳
	 * @return
	 */
	public static String stamp2date(long stamp) {
		return stamp2date(stamp, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 格式化时间
	 *
	 * @param time    时间
	 * @param pattern 时间样式
	 * @return
	 */
	public static String formatDate(String time, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sdf.format(date);
	}

	/**
	 * 时间转时间戳
	 *
	 * @param time 时间
	 * @return 毫秒级时间戳
	 */
	public static long date2Stamp(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date.getTime();
	}


}
