package com.z2py.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

public class FormatUtil {
	
	private final static long minute = 60 * 1000;// 1分钟
	private final static long hour = 60 * minute;// 1小时
	private final static long day = 24 * hour;// 1天
	private final static long month = 31 * day;// 月
	private final static long year = 12 * month;// 年

	public static String getTimeFormatText(Date date) {
		if (date == null) {
			return null;
		}
		long diff = new Date().getTime() - date.getTime();
		long r = 0;
		if (diff > year) {
			r = (diff / year);
			return r + "年前";
		}
		if (diff > month) {
			r = (diff / month);
			return r + "月前";
		}
		if (diff > day) {
			r = (diff / day);
			return r + "天前";
		}
		if (diff > hour) {
			r = (diff / hour);
			return r + "小时前";
		}
		if (diff > minute) {
			r = (diff / minute);
			return r + "分钟前";
		}
		return "刚刚";
	}
	
	public static String urlEncode(String text) throws UnsupportedEncodingException {
		return URLEncoder.encode(text, "UTF-8");
	}
	
	/**
	 * 查询过滤参数格式化
	 * @param filter	过滤参数字符串，格式：?key1=value1&key2=value2&...
	 * @param key		欲替换的key
	 * @param value		欲替换的key
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String addFilter(String filter, String key, String value) throws UnsupportedEncodingException {
		//已经包含
		if (filter.contains(key + "=" + urlEncode(value))) {
			return filter;
		}
		String oldFilter = filter;
		if (filter.contains(key)) {
			//参数不在末尾
			filter = filter.replaceAll(key + "=(.*?)&", key + "=" + urlEncode(value) + "&");
			if (filter.equals(oldFilter)) {
				//参数在末尾
				filter = filter.replaceAll(key + "=(.*?)$", key + "=" + urlEncode(value));
			}
		} else {
			if ("".equals(filter)) {
				filter += "?" + key + "=" + urlEncode(value);
			} else {
				filter += "&" + key + "=" + urlEncode(value);
			}
		}
		return filter;
	}
	
	public static String removeFilter(String filter, String key) {
		String oldFilter = filter;
		if (filter.contains(key)) {
			//参数不在末尾
			filter = filter.replaceAll(key + "=(.*?)&", "");
			if (filter.equals(oldFilter)) {
				//参数在末尾
				filter = filter.replaceAll(key + "=(.*?)$", "");
				if (filter.endsWith("&")) {
					filter = filter.substring(0, filter.length() - 1);
				}
			}
		}
		return filter;
	}
}
