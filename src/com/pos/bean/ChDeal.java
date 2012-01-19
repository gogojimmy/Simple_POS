package com.pos.bean;

import java.text.SimpleDateFormat;
import java.util.*;

//字元處理類
public class ChDeal {
public static String toChinese(String strvalue) {
	try {
		if (strvalue == null) {
			return "";
		} else {
			strvalue = new String(strvalue.getBytes("gb2312"), "GBK")
					.trim();
			return strvalue;
		}
	} catch (Exception e) {
		return "";
	}
}

public static String toISO(String strValue) {
	try {
		if (strValue == null) {
			return "";
		} else {
			strValue = new String(strValue.getBytes("GBK"), "gb2312")
					.trim();
			return strValue;
		}
	} catch (Exception e) {
		return "";
	}
}

		/**
		 * 完成從字串到String陣列的轉換
		 * 
		 * @param str
		 * @return
		 */
		public static String[] str2IntegerArr(String str){
			String[] is = null ;
			if(str != null && !str.equalsIgnoreCase("")){
				String[] ss = str.split(" ");
				if(ss != null && ss.length > 0){
					is = new String[ss.length];
					for(int i = 0 ; i < ss.length ; i ++){
						is[i] = ss[i];
					}
				}
			}
			return is ;
		}
//		編寫獲得日期和時間的方法．
public static String  getDateTime(){						//該方法返回值為String類型
	SimpleDateFormat format;                                //simpleDateFormat類使得可以選擇任何用戶定義的日期-時間格式的模式
	Date date = null;
	Calendar myDate = Calendar.getInstance();               //Calendar 的方法 getInstance，以獲得此類型的一個通用的物件
	myDate.setTime(new java.util.Date());                   //使用給定的 Date 設置此 Calendar 的時間
	date = myDate.getTime();                                //返回一個表示此 Calendar 時間值（從曆元至現在的毫秒偏移量）的 Date 物件
	format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   //設置時間格式為：年、月、日、時、分、秒
	String strRtn = format.format(date);                    //將給定的 Date 格式化為日期/時間字串，並將結果賦值給　給定的 String
	return strRtn;
}
}
