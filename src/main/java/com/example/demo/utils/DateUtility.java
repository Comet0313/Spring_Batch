package com.example.demo.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 共通
 */
public class DateUtility {

    /**
     * 文件做成时加入做成时间
     * 
     * @return String
     */
    public static String getSystemDateToFileName() {

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmssSSS");

        String systemDate = dateFormat.format(calendar.getTime());

        return systemDate;
    }

    /**
     * 系统日期取得
     * 
     * @return Utile Date
     */
    public static Date getSystemDate() {

        Date dt = new Date();
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        
        String dateString = formatter.format(dt);
        
        Date newDate = null;
        
        try {
            newDate = formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDate;
    }

    /**
     * 系统时间取得
     * 
     * @return Timestamp Date
     */
    public static Date getSystemTimestampDate() {
        
        Date dt = new Date();
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        
        String dateString = formatter.format(dt);

        Date newDate = null;
        try {
            newDate = formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDate;
    }

    /**
     * 时间格式转换
     */
    public static String getYmd(String str) {
        try {
            java.sql.Date dt = java.sql.Date.valueOf(str);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(dt);

        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 时间文字列转换Timestamp
     */
    public static java.sql.Timestamp strToTimestamp(String strTimestamp) {

        SimpleDateFormat df = new SimpleDateFormat();
        Date dt;
        df.setLenient(false);
        df.applyPattern("yyyy-MM-dd HH:mm:ss");

        try {
            dt = df.parse(strTimestamp);
        } catch (java.text.ParseException e) {
            return null;
        }

        Timestamp ts = new Timestamp(dt.getTime());

        return ts;
    }

}
