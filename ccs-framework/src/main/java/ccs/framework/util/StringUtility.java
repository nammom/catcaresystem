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
 * 문자열을 처리하는 유틸리티 클래스
 * <p><b>NOTE:</b>
 * 
 * @author 이재성
 * @since 2015.01.05
 * @version 1.0
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *       수정일                     수정자                            수정내용
 *  ------------    --------    ---------------------------
 *   2015.01.05       이재성                최초 생성
 *
 * </pre>
 */
public class StringUtility {
	/**
	 * Random string template
	 */
	private static final String RANDOM_CHAR_LIST = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	
	
	/**
	 * 문자열이 null 이거나 blank 인지 체크
	 * @param param 문자열
	 * @return Boolean true/false
	 */
	public static boolean isNullOrBlank(String arg) { 
	    return arg == null || arg.trim().length() == 0;
	}
	
	/**
	 *  문자열이 null 이거나 blank 인지 체크
	 * @param arg
	 * @return
	 */
	public static boolean isNotEmpty(String arg) { 
	    return !isNullOrBlank(arg);
	}
	
	/**
	 * 문자열이 empty 가 아닌지 체크
	 * @param arg
	 * @return
	 */
	public static boolean isNotEmpty(Object arg) { 
	    return !isNullOrBlank(arg);
	}
	
	
	/**
	 * 문자열이 null 이거나 blank 인지 체크
	 * @param param 문자열
	 * @return Boolean true/false
	 */
	public static boolean isNullOrBlank(Object arg)
	{
		return ( (arg == null) ? true: isNullOrBlank(String.valueOf(arg)));
	}
	
	/**
	 * 모든 값이 에 null or blank이면 true
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
	 * 여러개 값 중에 하나라도 null or blank이면 true
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
	 * source에 member의 문자열이 포함되어 있을 경우 true 리턴
	 * @param source 검사를 수행할 대상
	 * @param member 검사할 문자 용소
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
	 * null 값을 "" 공백 문자로 바꿔준다.
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
	 * Object null 값을 "" 공백 문자로 바꿔준다.
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
	 * 문자열을 html 태그를 제거한 후 1000 글자씩 ArrayList를 담아 리턴한다.
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
	 * javascript, css, HTMLTag를 제거한 문자열을 리턴한다.
	 * 단, <title>은 title 내용까지 제거를 한다.
	 * @param s 문자열
	 * @return String
	 */
	public static String filterAllTag(String s)
	{
		if(s == null) return null;
		
		// title 제거
		Pattern ptitle = Pattern.compile("<title[^>]*>.*</title>", Pattern.DOTALL|Pattern.MULTILINE);
		Matcher mtitle = ptitle.matcher(s);
		s = mtitle.replaceAll("");
		
		// css 제거
		Pattern pstyle = Pattern.compile("<style[^>]*>.*</style>", Pattern.DOTALL|Pattern.MULTILINE);
		Matcher mstyle = pstyle.matcher(s);
		s = mstyle.replaceAll("");
		
		// script 제거
		s = filterJavascript(s);
		
		// tag 제거
		Document d =Jsoup.parse(s); 
		return Jsoup.parse(s).text();
/*		Pattern ptag = Pattern.compile("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>");
	    Matcher mtag = ptag.matcher(s);
	    return mtag.replaceAll("");*/
	}
	
	/**
	 * javascript 내용 제거
	 * @param s 문자열
	 * @return String
	 */
	public static String filterJavascript(String s)
	{
		if(s == null) return null;
		// script 제거
		//"<script[^>]*>.*</script>"
		Pattern pscript = Pattern.compile("<(no)?script[^>]*>.*<\\/(no)?script>", Pattern.DOTALL|Pattern.MULTILINE);
		Matcher mscript = pscript.matcher(s);
		return mscript.replaceAll("");
	}
	
	/**
     * 문자열의 html태그를 특수문자로 변환한다.
     *
     * @param str 변환할 문자열
     * @return String 변환된 문자열
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
     * 문자열의 특수문자를 html태그로 변환한다.
     *
     * @param str 변환할 문자열
     * @return 변환된 문자열
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
     * 공백이나 null이 아닐 경우 문자열 리턴, 그외에는 null 리턴
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
     * 두 Object(String)의 문자열을 비교하여 일치여부를 리턴한다.
     * @param val1 비교 문자열 1
     * @param val2 비교 문자열 2
     * @return true(일치), true(미 일치)
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
     * 두 Object(String)의 문자열을 비교하여 일치여부를 리턴한다.
     * @param val1 비교 문자열 1
     * @param val2 비교 문자열 2
     * @return true(일치), true(미 일치)
     */
    public static boolean isEqualIgnoreCase(Object val1, Object val2){
    	if(StringUtility.isNullOrBlank(val1))return false;
    	if(StringUtility.isNullOrBlank(val2)) return false;
    	if(String.valueOf(val1).toLowerCase().equals(String.valueOf(val2).toLowerCase())) return true;
    	
    	return false;
    }
    
    /**
     * Object(String)의 문자열을 비교하여 일치여부를 리턴한다.
     * @param va1 기준 문자열 값
     * @param val2 ... Or로 비교할 문자열
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
     * 정규식을 통한 체크
     * @param regex 정규식
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
     * 정규식을 통한 체크
     * @param type 체크 타입
     * @param text
     * @return
     */
    public static boolean isMatched(CheckType type, Object text){
    	return isMatched(type, StringUtility.toString(text));
    }
    
    /**
     * 정규식 체크 유형
     * @author Haru
     *
     */
    public enum CheckType{
    	/**
    	 * 이메일
    	 */
    	Email,
    	/**
    	 * 아이피
    	 */
    	IP,
    	/**
    	 * 이미지 파일 확장자
    	 */
    	ImageExtension,
    	/**
    	 * 업로드 불가 확장자
    	 */
    	DeniedExtension
    }
    

    /**
     * Language Version Code 문자열 생성
     * @return 랜덤 문자열
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
     * Random 문자열 생성
     * @return 랜던 문자열
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
	 * 준자열 자르기
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
	 * 문자열 자르기
	 * @param val
	 * @param maxlength
	 * @return
	 */
	public static String getStringWithLength(String val, int maxlength ){
		return getStringWithLength(val,maxlength,"...");
	}
	
	/**
	 * 문자열 자르기
	 * @param val
	 * 	검사할 문자열
	 * @param maxlength
	 * 	자를 문자열 대상 크기
	 * @param surfix
	 * 	문자열 자른 후 접미어
	 * @param escapeMode
	 * 	html 데이터일 경우 변경 모드
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
	 * 공백, 특수문자 제거
	 * @param str
	 * @return
	 */
	public static String getStringReplace(String str){
		String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z]"; // 한글, 영문, 숫자 제외
	    str =str.replaceAll(match, "_");
	    return str;
	}
	
	/**
	 * 숫자 형식인지 체크
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
	 * 숫자 형식 체크(정수, 실수)
	 * @param obj
	 * @return
	 */
	public static boolean isNumeric2(Object obj){
		if(isNullOrBlank(obj)) return false;
		String str = toString(obj);
		return str.matches("-?\\d+(\\.\\d+)?");
	}
	
	/**
	 * String null 체크 
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
	 * inpt 값을 기준으로 + 1 하여 다음 숫자에 대한 값을 출력
	 * @param inpt 입력 문자(숫자형식의 문자)
	 * @param len 문자열 전체 길이
	 * @param addStr left pad 문자열
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
	 * 인코딩 변경
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