package ccs.framework.util;


import java.math.BigDecimal;

public class NumberUtility {

	/**
	 * ���ڿ����� ���ڷ� ��ȯ�Ͽ� ����, �� ���ڿ��� Null �Ǵ� Blanku�� ��쿣 null�� ����
	 * @param arg
	 * @return
	 */
	public static Integer convertToInt(String arg)
	{
		if(StringUtility.isNullOrBlank(arg)) return null;
		try
		{
			return Integer.valueOf(arg);
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	
	public static Double convertToDouble(String arg)
	{
		if(StringUtility.isNullOrBlank(arg)) return null;
		try
		{
			return Double.valueOf(arg);
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	public static Long convertToLong(String arg)
	{
		if(StringUtility.isNullOrBlank(arg)) return null;
		try
		{
			return Long.valueOf(arg);
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	/**
	 * ���ڿ����� ���ڷ� ��ȯ�Ͽ� ����, �� ���ڿ��� Null �Ǵ� Blanku�� ��쿣 null�� ����
	 * @param arg
	 * @return
	 */
	public static Integer convertToInt(Object arg)
	{
		return convertToInt(String.valueOf(arg));
	}
	
	public static Double convertToDouble(Object arg)
	{
		return convertToDouble(String.valueOf(arg));
	}
	
	public static Long convertToLong(Object arg)
	{
		return convertToLong(String.valueOf(arg));
	}
	
	/**
	 * BigDecimal�� ����
	 * @param arg
	 * @return
	 */
	public static BigDecimal convertToBigDecimal(Object arg){
		if(StringUtility.isNullOrBlank(arg)){
			return null;
		}
		try {
			BigDecimal rtn = new BigDecimal(String.valueOf(arg));
			return rtn;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * ������ ������� ���� 
	 * @param arg
	 * @return
	 */
	public static BigDecimal convertToPercent(Object arg){
		BigDecimal percent = null;
		
		BigDecimal val = convertToBigDecimal(arg);
		if(val != null){
			percent = BigDecimalUtility.divide(val, new BigDecimal("100"));
		}
		
		return percent;
	}
}
