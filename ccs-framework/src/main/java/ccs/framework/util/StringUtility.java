package ccs.framework.util;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.web.util.HtmlUtils;




/**
 * ���ڿ��� ó���ϴ� ��ƿ��Ƽ Ŭ����
 * <p><b>NOTE:</b>
 * 
 * @author ���缺
 * @since 2015.01.05
 * @version 1.0
 *
 * <pre>
 * << �����̷�(Modification Information) >>
 *   
 *       ������                     ������                            ��������
 *  ------------    --------    ---------------------------
 *   2015.01.05       ���缺                ���� ����
 *
 * </pre>
 */
public class StringUtility {
	/**
	 * Random string template
	 */
	private static final String RANDOM_CHAR_LIST = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	
	
	/**
	 * ���ڿ��� null �̰ų� blank ���� üũ
	 * @param param ���ڿ�
	 * @return Boolean true/false
	 */
	public static boolean isNullOrBlank(String arg) { 
	    return arg == null || arg.trim().length() == 0;
	}
	
	/**
	 *  ���ڿ��� null �̰ų� blank ���� üũ
	 * @param arg
	 * @return
	 */
	public static boolean isNotEmpty(String arg) { 
	    return !isNullOrBlank(arg);
	}
	
	/**
	 * ���ڿ��� empty �� �ƴ��� üũ
	 * @param arg
	 * @return
	 */
	public static boolean isNotEmpty(Object arg) { 
	    return !isNullOrBlank(arg);
	}
	
	
	/**
	 * ���ڿ��� null �̰ų� blank ���� üũ
	 * @param param ���ڿ�
	 * @return Boolean true/false
	 */
	public static boolean isNullOrBlank(Object arg)
	{
		return ( (arg == null) ? true: isNullOrBlank(String.valueOf(arg)));
	}
	
	/**
	 * ��� ���� �� null or blank�̸� true
	 * @param arg
	 * @return
	 */
	public static boolean isNullOrBlankAll(Object ... arg){
		if(arg == null) return true;
		for(Object obj : arg){
			boolean a = isNullOrBlank(obj);
			if(!a) return false;
		}
		return true;
	}
	
	/**
	 * ������ �� �߿� �ϳ��� null or blank�̸� true
	 * @param arg
	 * @return
	 */
	public static boolean isNullOrBlankAny(Object ... arg){
		if(arg == null) return true;
		for(Object obj : arg){
			boolean a = isNullOrBlank(obj);
			if(a) return true;
		}
		return false;
	}
	
	/**
	 * source�� member�� ���ڿ��� ���ԵǾ� ���� ��� true ����
	 * @param source �˻縦 ������ ���
	 * @param member �˻��� ���� ���
	 * @return
	 */
	public static boolean contains(Object source, Object member){
		if(source == null || member == null) return false;
		
		String strSource = toString(source);
		String strMember = toString(member);
		
		if(strSource.contains(strMember)) return true;
		
		return false;
	}
	
	/**
	 * null ���� "" ���� ���ڷ� �ٲ��ش�.
	 * 
	 * @param String
	 * @return String
	 */
	public static String nullToEmpty(String str) {
		String strVal = str;
		if (strVal == null) {
			return "";
		}
		return strVal;
	}
	
	/**
	 * Object null ���� "" ���� ���ڷ� �ٲ��ش�.
	 * 
	 * @param String
	 * @return String
	 */
	public static String nullToEmpty(Object obj) {
		String strVal = "";
		if (obj == null) {
			strVal = "";
		} else {
			strVal = obj.toString();
		}
		return strVal;
	}
	
	/**
	 * ���ڿ��� html �±׸� ������ �� 1000 ���ھ� ArrayList�� ��� �����Ѵ�.
	 * @param s
	 * @return ArrayList<String>
	 */
	public static ArrayList<String> filterData(String s)
	{
		ArrayList<String> al = new ArrayList<String>();
		if(s == null) return al;
		int iSplitSize = 1000;
		String sHtmlRemove = filterAllTag(s).trim();
		
		int iMaxLoop = sHtmlRemove.length() / iSplitSize;
		for(int i=0; i <= iMaxLoop; i++){
			al.add(sHtmlRemove.substring((i*iSplitSize), (i!=iMaxLoop ? (i+1)*iSplitSize : sHtmlRemove.length())));
		}
		
		return al;
	}
	
	
	/**
	 * javascript, css, HTMLTag�� ������ ���ڿ��� �����Ѵ�.
	 * ��, <title>�� title ������� ���Ÿ� �Ѵ�.
	 * @param s ���ڿ�
	 * @return String
	 */
	public static String filterAllTag(String s)
	{
		if(s == null) return null;
		
		// title ����
		Pattern ptitle = Pattern.compile("<title[^>]*>.*</title>", Pattern.DOTALL|Pattern.MULTILINE);
		Matcher mtitle = ptitle.matcher(s);
		s = mtitle.replaceAll("");
		
		// css ����
		Pattern pstyle = Pattern.compile("<style[^>]*>.*</style>", Pattern.DOTALL|Pattern.MULTILINE);
		Matcher mstyle = pstyle.matcher(s);
		s = mstyle.replaceAll("");
		
		// script ����
		s = filterJavascript(s);
		
		// tag ����
		Document d =Jsoup.parse(s); 
		return Jsoup.parse(s).text();
/*		Pattern ptag = Pattern.compile("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>");
	    Matcher mtag = ptag.matcher(s);
	    return mtag.replaceAll("");*/
	}
	
	/**
	 * javascript ���� ����
	 * @param s ���ڿ�
	 * @return String
	 */
	public static String filterJavascript(String s)
	{
		if(s == null) return null;
		// script ����
		//"<script[^>]*>.*</script>"
		Pattern pscript = Pattern.compile("<(no)?script[^>]*>.*<\\/(no)?script>", Pattern.DOTALL|Pattern.MULTILINE);
		Matcher mscript = pscript.matcher(s);
		return mscript.replaceAll("");
	}
	
	/**
     * ���ڿ��� html�±׸� Ư�����ڷ� ��ȯ�Ѵ�.
     *
     * @param str ��ȯ�� ���ڿ�
     * @return String ��ȯ�� ���ڿ�
     */
    public static String charToHtml(String str) {
    	
    	if(str == null) return null;
        String convert = "";

        if (str != null) {
        	convert = str.replaceAll("&", "&amp;");
            convert = convert.replaceAll("<", "&lt;");
            convert = convert.replaceAll(">", "&gt;");
            convert = convert.replaceAll("\"", "&quot;");
            convert = convert.replaceAll("\'", "&#39;");
            convert = convert.replaceAll("%2E", "&#46;");
            convert = convert.replaceAll("%2F", "&#47;");
            convert = convert.replaceAll("&nbsp;", "&amp;nbsp;");
        }

        return convert;
    }

    /**
     * ���ڿ��� Ư�����ڸ� html�±׷� ��ȯ�Ѵ�.
     *
     * @param str ��ȯ�� ���ڿ�
     * @return ��ȯ�� ���ڿ�
     */
    public static String htmlToChar(String str) {
    	if(str == null) return null;
        String convert = "";

        if (str != null) {
            convert = str.replaceAll("&lt;", "<");
            convert = convert.replaceAll("&gt;", ">");
            convert = convert.replaceAll("&quot;", "\"");
            convert = convert.replaceAll("&#39;", "\'");
            convert = convert.replaceAll("&amp;nbsp;", "&nbsp;");
        }

        return convert;
    }
    
    /**
     * �����̳� null�� �ƴ� ��� ���ڿ� ����, �׿ܿ��� null ����
     * @param val
     * @return
     */
    public static String toString(Object val){
    	String result = null;
    	if(val != null && !isNullOrBlank(val.toString())){
    		/*result = val.toString();*/
    		result =String.valueOf(val);
    	}
    	return result;
    }
    
    public static String toString2(Object val){
    	String result = "";
    	if(val != null && !isNullOrBlank(val.toString())){
    		/*result = val.toString();*/
    		result =String.valueOf(val);
    	}
    	return result;
    }
    
    /**
     * �� Object(String)�� ���ڿ��� ���Ͽ� ��ġ���θ� �����Ѵ�.
     * @param val1 �� ���ڿ� 1
     * @param val2 �� ���ڿ� 2
     * @return true(��ġ), true(�� ��ġ)
     */
    public static boolean isEqual(Object val1, Object val2){
    	if(StringUtility.isNullOrBlank(val1))return false;
    	if(StringUtility.isNullOrBlank(val2)) return false;
    	if(String.valueOf(val1).equals(String.valueOf(val2))) return true;
    	
    	return false;
    }
    
    public static boolean isNotEqual(Object val1, Object val2){
    	boolean isNull_1 = StringUtility.isNullOrBlank(val1);
    	boolean isNull_2 = StringUtility.isNullOrBlank(val2);
    	
    	if(isNull_1 && isNull_2) return false;
    	if(isNull_1 && !isNull_2) return true;
    	if(!isNull_1 && isNull_2) return true;
    	
    	return isEqual(val1,val2);
    }
    
    /**
     * �� Object(String)�� ���ڿ��� ���Ͽ� ��ġ���θ� �����Ѵ�.
     * @param val1 �� ���ڿ� 1
     * @param val2 �� ���ڿ� 2
     * @return true(��ġ), true(�� ��ġ)
     */
    public static boolean isEqualIgnoreCase(Object val1, Object val2){
    	if(StringUtility.isNullOrBlank(val1))return false;
    	if(StringUtility.isNullOrBlank(val2)) return false;
    	if(String.valueOf(val1).toLowerCase().equals(String.valueOf(val2).toLowerCase())) return true;
    	
    	return false;
    }
    
    /**
     * Object(String)�� ���ڿ��� ���Ͽ� ��ġ���θ� �����Ѵ�.
     * @param va1 ���� ���ڿ� ��
     * @param val2 ... Or�� ���� ���ڿ�
     * @return
     */
    public static boolean isOrEqual(Object va1, Object ... val2){
    	for(Object v1 : val2){
    		boolean a = isEqual(va1,v1);
    		if(a == true) return true;
    	}
    	return false;
    }
    
    
    
    /**
     * ���Խ��� ���� üũ
     * @param regex ���Խ�
     * @param text
     * @return
     */
    public static boolean isMatched(String regex, String text){
    	Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    	Matcher m = p.matcher(text);
    	return m.find();
    	//return Pattern.matches(regex, text);
    }
    

    
    /**
     * ���Խ��� ���� üũ
     * @param type üũ Ÿ��
     * @param text
     * @return
     */
    public static boolean isMatched(CheckType type, Object text){
    	return isMatched(type, StringUtility.toString(text));
    }
    
    /**
     * ���Խ� üũ ����
     * @author Haru
     *
     */
    public enum CheckType{
    	/**
    	 * �̸���
    	 */
    	Email,
    	/**
    	 * ������
    	 */
    	IP,
    	/**
    	 * �̹��� ���� Ȯ����
    	 */
    	ImageExtension,
    	/**
    	 * ���ε� �Ұ� Ȯ����
    	 */
    	DeniedExtension
    }
    

    /**
     * Language Version Code ���ڿ� ����
     * @return ���� ���ڿ�
     */
    public static String generateLanguageKey(){
    	int iLength = 10;
    	StringBuffer randStr = new StringBuffer();
    	for(int i = 0; i < iLength; i++){
    		int number = getRandomNumber();
    		char ch = RANDOM_CHAR_LIST.charAt(number);
    		randStr.append(ch + Integer.toString(number));
    	}
    	return randStr.toString();
    }
    
    /**
     * Random ���ڿ� ����
     * @return ���� ���ڿ�
     */
    public static String generateRandomString(int iLength)
    {
    	StringBuffer randStr = new StringBuffer();
    	for(int i = 0; i < iLength; i++){
    		int number = getRandomNumber();
    		char ch = RANDOM_CHAR_LIST.charAt(number);
    		randStr.append(ch);
    	}
    	return randStr.toString();
    }
    
    /**
	 * generates random number
	 * @return
	 */
	private static int getRandomNumber() {
        int randomInt = 0;
        java.util.Random randomGenerator = new java.util.Random();
        randomInt = randomGenerator.nextInt(RANDOM_CHAR_LIST.length());
        if (randomInt - 1 == -1) {
            return randomInt;
        } else {
            return randomInt - 1;
        }
    }
	
	/**
	 * ���ڿ� �ڸ���
	 * @param val
	 * @param maxlength
	 * @param surfix
	 * @return
	 */
	public static String getStringWithLength(String val, int maxlength , String surfix){
		String result;
		if(!StringUtility.isNullOrBlank(maxlength) && val != null ){
			// cut string
			//StringUtility.
			int nlength = NumberUtility.convertToInt(maxlength);
			if(val.length() > nlength){
				result = val.substring(0, nlength-1) + ((surfix != null) ? surfix : "...");
			} else{
				result = val;
			}
		} else{
			result = val;
		}
		return result;
	}
	
	/**
	 * ���ڿ� �ڸ���
	 * @param val
	 * @param maxlength
	 * @return
	 */
	public static String getStringWithLength(String val, int maxlength ){
		return getStringWithLength(val,maxlength,"...");
	}
	
	/**
	 * ���ڿ� �ڸ���
	 * @param val
	 * 	�˻��� ���ڿ�
	 * @param maxlength
	 * 	�ڸ� ���ڿ� ��� ũ��
	 * @param surfix
	 * 	���ڿ� �ڸ� �� ���̾�
	 * @param escapeMode
	 * 	html �������� ��� ���� ���
	 * @return
	 */
	public static String getStringWithLength(String val, int maxlength , String surfix, StringEscapeMode escapeMode){
		String excapeHtml;
		if(escapeMode == StringEscapeMode.RemoveHtml){
			excapeHtml = HtmlUtils.htmlEscape(getStringWithLength(StringUtility.filterAllTag(val),maxlength,surfix))
    				//.replaceAll(" ", "&nbsp;")
    				.replaceAll("(\r\n|\n)", "<br />");
		} else if(escapeMode == StringEscapeMode.EscapeHtml){
			excapeHtml = getStringWithLength(HtmlUtils.htmlEscape(val),maxlength,surfix)
    				.replaceAll("(\r\n|\n)", "<br />");
		} else{
			excapeHtml = getStringWithLength(val,maxlength,surfix);
		}
		return excapeHtml;
	}
	
	public enum StringEscapeMode{
		RemoveHtml,
		EscapeHtml,
		None
	}
	
	/**
	 * ����, Ư������ ����
	 * @param str
	 * @return
	 */
	public static String getStringReplace(String str){
		String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z]"; // �ѱ�, ����, ���� ����
	    str =str.replaceAll(match, "_");
	    return str;
	}
	
	/**
	 * ���� �������� üũ
	 * @param obj
	 * @return
	 */
	public static boolean isNumeric(Object obj){
		if(isNullOrBlank(obj)) return false;
		String str = toString(obj);
		
		try{
			double d = Double.parseDouble(str);
			return true;
		}catch(NumberFormatException e){
			return false;
		}
		
		//return StringUtils.isNumeric(str);
	}
	
	
	/**
	 * ���� ���� üũ(����, �Ǽ�)
	 * @param obj
	 * @return
	 */
	public static boolean isNumeric2(Object obj){
		if(isNullOrBlank(obj)) return false;
		String str = toString(obj);
		return str.matches("-?\\d+(\\.\\d+)?");
	}
	
	/**
	 * String null üũ 
	 * @param str
	 * @return
	 */
	public static String nvl( String p1 ){
		if( p1 == null )
			p1 = "";
		return p1;
	}
	
	public static String lpad(String str, int len, String addStr) {
        String result = str;
        int templen   = len - result.length();

        for (int i = 0; i < templen; i++){
              result = addStr + result;
        }
        
        return result;
    }
	
	/*public static String rpad(String str, int len, char addChar){
		StringBuilder sb = new StringBuilder();

		for (int toPrepend=len-str.length(); toPrepend>0; toPrepend--) {
		    sb.append(addChar);
		}

		sb.append(str);
		String result = sb.toString();
		return result;
	}*/
	
	public static String rpad(String str, int len, char addChar){
		if(str.length() > len) return str;
		
		char[] out = new char[len];
		System.arraycopy(str.toCharArray(), 0, out, 0, str.length());
        Arrays.fill(out, str.length(), len, addChar);
        return new String(out);
	}
	
	public static String lpad(String str, int len, char addChar){
		if(str.length() > len) return str;
		
		char[] out = new char[len];
		int sourceOffset = len - str.length();
        System.arraycopy(str.toCharArray(), 0, out, sourceOffset, str.length());
        Arrays.fill(out, 0, sourceOffset, addChar);
        return new String(out);
	}
	
	/**
	 * inpt ���� �������� + 1 �Ͽ� ���� ���ڿ� ���� ���� ���
	 * @param inpt �Է� ����(���������� ����)
	 * @param len ���ڿ� ��ü ����
	 * @param addStr left pad ���ڿ�
	 * @return
	 */
	public static String nextNumberString(String inpt, int len, String addStr){
		String str;
		if(StringUtility.isNullOrBlank(inpt)){
			str = "1";
		} else{
			str = toString(NumberUtility.convertToInt(inpt)+1);
		}
		
		return lpad(str, len, addStr);
	}
	
	
	/**
	 * ���ڵ� ����
	 * @param str
	 * @param encoding
	 * @return
	 */
	/*public static String changeEncoding(String str, String encoding) {
		
		String result = null;
		try {
			Charset charSet = Charset.forName(encoding);
			byte[] bytes = str.getBytes(charSet);
			result = org.apache.commons.lang3.StringUtils.toEncodedString(bytes, charSet);	
		} catch(Exception ee) {
			ee.printStackTrace();
			result = null;
		}
		
		return result;
	}*/
}