package com.example.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.example.constant.AppConstants;

public class DateUtil {
	
	/**
	 * Function to get Next Day Date
	 * @return
	 */
	public static String getNextDate() {
		  final SimpleDateFormat format = new SimpleDateFormat(AppConstants.DATE_FORMAT);
		  final Calendar calendar = Calendar.getInstance();
		  calendar.setTime(new Date());
		  calendar.add(Calendar.DAY_OF_YEAR, 1);
		  return format.format(calendar.getTime()); 
	}
	
	/**
	 * Function to get Current DateTime
	 * @return
	 */
	public static String getCurrentDateTime() {
		  final SimpleDateFormat format = new SimpleDateFormat(AppConstants.DATETIME_FORMAT);
		  final Calendar calendar = Calendar.getInstance();
		  calendar.setTime(new Date());
		  return format.format(calendar.getTime()); 
	}

}
