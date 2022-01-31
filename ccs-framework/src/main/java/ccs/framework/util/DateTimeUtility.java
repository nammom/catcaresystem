package ccs.framework.util;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTimeUtility {

	
	public static String getSysDatetime(){
		return getSysDatetime("yyyyMMddHHmmss");
	}
	
	public static String getSysDatetime(String format){
		LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		
		return dateTime.format(formatter);
	}
	
	/**
	 * 문자열 yyyy-MM-dd 포맷의 데이터를 Date 형식으로 린턴
	 * @param textDate 문자열 데이터 (yyyy-MM-dd 형식)
	 * @return
	 * @throws ParseException
	 */
	public static Date getDateFromStringFormat(String textDate, String format) throws ParseException{
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format);
		java.util.Date toDate = formatter.parse(textDate);
		return toDate;
	}
	
	/**
	 * 문자열 yyyy-MM-dd 포맷의 데이터를 Date 형식으로 린턴
	 * @param textDate 문자열 데이터 (yyyy-MM-dd 형식)
	 * @return
	 * @throws ParseException
	 */
	public static Date getDateFromStringFormat(String textDate) throws ParseException{
		return getDateFromStringFormat(textDate, "yyyyMMddHHmmss");
	}
}
