package ccs.framework.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import ccs.framework.dataservice.ApplicationContextProvider;

public class SessionUtility {
	
	
	private final static String userSessionName = "UserInfo";
	
	/**
	 * UserInfo 반환
	 * @return UserInfo
	 */
/*	public static UserInfo getUserInfo(){
		try {
			return (UserInfo) getAttribute(userSessionName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}*/
	
	/**
	 * UserInfo 반환
	 * @return UserInfo
	 */
	public static <T extends UserDetails> T getUserDetails(){
		try {
			T user =  (T) getAttribute(userSessionName);
			
			if(user != null && StringUtility.isNotEmpty(user.getUsername())) return user;
			return null;
			
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * user 로그인 수행
	 * @param userInfo
	 */
	public static <T extends UserDetails> void setUserDetails(T userDetail){
		try {
			
			System.out.println("===============================> SESSION setUserDetails================================================================");
			
			
			setAttribute(userSessionName,userDetail);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * user 로그인 수행
	 * @param userInfo
	 */
/*	public static void setUserInfo(UserInfo userInfo){
		try {
			setAttribute(userSessionName,userInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
	
	/**
     * attribute 값을 가져 오기 위한 method
     *
     * @param String  attribute key name
     * @return Object attribute obj
     */
    public static Object getAttribute(String name) throws Exception {
    	ServletRequestAttributes servletRequestAttribute = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession httpSession = servletRequestAttribute.getRequest().getSession(true);
        return (Object)httpSession.getAttribute(name);
    }
 
    /**
     * attribute 설정 method
     *
     * @param String  attribute key name
     * @param Object  attribute obj
     * @return void
     */
    public static void setAttribute(String name, Object object) throws Exception {
    	ServletRequestAttributes servletRequestAttribute = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession httpSession = servletRequestAttribute.getRequest().getSession(true);
        
        httpSession.setAttribute(name, object);
    }
 
    /**
     * 설정한 attribute 삭제
     *
     * @param String  attribute key name
     * @return void
     */
    public static void removeAttribute(String name) throws Exception {
    	ServletRequestAttributes servletRequestAttribute = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession httpSession = servletRequestAttribute.getRequest().getSession(true);
        
        httpSession.removeAttribute(name);
    }
 
    /**
     * session id
     *
     * @param void
     * @return String SessionId 값
     */
    public static String getSessionId() throws Exception  {
        return RequestContextHolder.getRequestAttributes().getSessionId();
    }
}
