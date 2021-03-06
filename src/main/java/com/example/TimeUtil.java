package com.example;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

    private final static Logger logger = LoggerFactory.getLogger(TimeUtil.class);
    /**
     * yyyyMMddHHmmss
     */
    public static String FORMAT_YMDHMS = "yyyyMMddHHmmss";

    /**
     * yyyyMMdd
     */
    public static String FORMAT_YMD = "yyyyMMdd";

    /**
     * yyyy-MM-dd HH:mm
     */
    public static String FORMAT_YMDHM = "yyyy-MM-dd HH:mm";

    /**
     * yyyy年MM月dd日
     */
    public static String FORMAT_YMD_CHI = "yyyy年MM月dd日";

    /**
     * yyyy-MM-dd
     */
    public static String FORMAT_YMD_LINE = "yyyy-MM-dd";

    /**
     * MM-dd
     */
    public static String FORMAT_M_D = "MM-dd";

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static String FORMAT_YMDHM_S = "yyyy-MM-dd HH:mm:ss";

    public static String FORMAT_HMS = "HH:mm:ss";

    /**
     * 将“yyyy-MM-dd”格式的日期字符串转换为date,加上00:00:00
     *
     * @param source 日期字符串。
     * @return Date 日期对象。
     */
    public static Date toDayStart(String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        source = source.substring(0, 10) + " 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(source, new ParsePosition(0));
    }

    /**
     * 将“yyyy-MM-dd”格式的日期字符串转换为date,加上23:59:59
     *
     * @param source 日期字符串。
     * @return Date 日期对象。
     */
    public static Date toDayEnd(String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(source + " 23:59:59", new ParsePosition(0));
    }

    /**
     * 时间格式转换
     *
     * @param date   时间
     * @param format 转换格式
     * @return 返回值
     */
    public static Date getDate(String date, String format) {
        if (StringUtils.isEmpty(date) || StringUtils.isEmpty(format) || "".equals(date)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            try {
                if (date.length() == FORMAT_YMD.length()) {
                    SimpleDateFormat sdf2 = new SimpleDateFormat(FORMAT_YMD);
                    return sdf2.parse(date);
                }
            } catch (Exception t) {
                return null;
            }

            logger.error("{} {} parse error", date, format);
        }
        return null;
    }

    /**
     * 获取时间
     *
     * @param date   时间
     * @param format 转换格式
     * @return 返回值
     */
    public static String getDateString(Date date, String format) {
        if (date == null || StringUtils.isEmpty(format)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static String transferDatestr(String dateStr, String from, String to) {
        return getDateString(getDate(dateStr, from), to);
    }

    /**
     * yyyyMMddHHmmss >> yyyyMMdd235959
     *
     * @param dateStr 时间字符串
     * @return 返回值
     */
    public static String getDateEnd(String dateStr) {
        if (StringUtils.isEmpty(dateStr)) {
            return null;
        }

        if (FORMAT_YMDHMS.length() == dateStr.length()) {
            return dateStr.substring(0, 8) + "235959";
        }

        return dateStr;
    }

    /**
     * 转让日期为**-**-** 23:59:59
     *
     * @param date 时间
     * @return 返回值
     */
    public static Date getDateEnd(Date date) {
        if (date == null) {
            return null;
        }
        return getDate(getDateEnd(getDateString(date, FORMAT_YMDHMS)), FORMAT_YMDHMS);
    }

    /**
     * yyyyMMddHHmmss >> yyyyMMdd00000
     *
     * @param dateStr 输入时间
     * @return 返回值
     */
    public static String getDateStart(String dateStr) {
        if (StringUtils.isEmpty(dateStr)) {
            return null;
        }

        if (FORMAT_YMDHMS.length() == dateStr.length()) {
            return dateStr.substring(0, 8) + "000000";
        }

        return dateStr;
    }

    /**
     * 日期转换为**-**-** 00:00:00
     *
     * @param date 时间
     * @return 返回值
     */
    public static Date getDateStart(Date date) {
        if (date == null) {
            return null;
        }
        return getDate(getDateStart(getDateString(date, FORMAT_YMDHMS)), FORMAT_YMDHMS);
    }

    /**
     * 校验date1是否在date2之后
     *
     * @param date1  起始时间
     * @param date2  结束时间
     * @param format 转换格式
     * @return 返回值
     */
    public static boolean isAfter(String date1, String date2, String format) {
        Date d1 = getDate(date1, format);
        Date d2 = getDate(date2, format);
        return isAfter(d1, d2);
    }

    /**
     * 校验date1是否在date2之后
     *
     * @param d1 起始时间
     * @param d2 结束时间
     * @return 返回值
     */
    public static boolean isAfter(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            return false;
        }
        return d1.getTime() - d2.getTime() > 0;
    }

    /**
     * 获取时分秒
     *
     * @param date 起始时间
     * @return 返回值
     */
    public static String getTimeHMS(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        return df.format(date);
    }

    /**
     * 计算日期的间隔 d2 - d1
     *
     * @param d1 yyyy-MM-dd
     * @param d2 yyyy-MM-dd
     * @return 返回值
     */
    public static int calInterval(String d1, String d2) {
        Date day1 = getDate(d1, FORMAT_YMD);
        Date day2 = getDate(d2, FORMAT_YMD);
        if (day1 == null || day2 == null) {
            return 0;
        }
        long mill = day2.getTime() - day1.getTime();
        return (int) (mill / 1000 / 3600 / 24);
    }

    /**
     * 当天剩余分钟
     *
     * @return 返回值
     */
    public static String getTimeCountDown() {
        Date now = new Date();
        String time = TimeUtil.getDateString(now, TimeUtil.FORMAT_YMD) + "235959";
        long leftTime = TimeUtil.getDate(time, TimeUtil.FORMAT_YMDHMS)
                .getTime() - now.getTime();
        return leftTime / 1000 / 60 + 1 + "";
    }

    /**
     * 获取系统时间的后一天
     *
     * @return 返回值
     */
    public static String nextDay() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 1);
        String dd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime());
        return dd;
    }

    /**
     * 日期计算
     *
     * @param date 时间
     * @param days 天数
     * @return 返回值
     */
    public static Date calDate(Date date, int days) {
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DAY_OF_MONTH, days);
            return c.getTime();
        } catch (Exception e) {
            return null;
        }

    }

}
