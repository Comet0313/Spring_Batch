package com.example.demo.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

//读取文件时文件内容check共通
public class CommonCheck {

	public static boolean checkSpaceNull(String str) {

		boolean ret = false;

		if ((str == null) || (str.length() == 0)) {
			ret = true;
			return ret;
		}
		return ret;
	}

	public static boolean checkMaxLength(String s, int max)
	{
		int len;
		boolean ret = false;

		len = s.length();
		if(len > max)
		{
			ret = true;
		}
		return ret;
	}


	public static boolean checkRationalInte(String strNum) {

		boolean ret = false;


		if ((strNum == null) || (strNum.length() == 0)) {
			ret = true;
			return ret;
		}


		if (!(strNum.matches("^[0-9]+$"))) {
			ret = true;
			return ret;
		}


		try {
		 	Long.parseLong(strNum);
		} catch (NumberFormatException nfe) {
			ret = true;
		}

		return ret;
	}


	public static boolean checkDateType(String str)
	{
		boolean ret = false;

		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			dtf.format(LocalDate.parse(str, dtf));
			return ret;
		} catch (Exception dtp) {
			ret = true;
			return ret;
		}
	}


	public static boolean checkTimeStampType(String str)
	{
		boolean ret = false;

		try{

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdf.setLenient(false);
			sdf.parse(str);
			
			return ret;

		}catch(Exception ex){
			ret = true;
			return ret;
		}
	}
}
