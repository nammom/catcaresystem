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
	 * UserInfo ��ȯ
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
	 * UserInfo ��ȯ
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
	 * user �α��� ����
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
	 * user �α��� ����
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
     * attribute ���� ���� ���� ���� method
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
     * attribute ���� method
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
     * ������ attribute ����
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
     * @return String SessionId ��
     */
    public static String getSessionId() throws Exception  {
        return RequestContextHolder.getRequestAttributes().getSessionId();
    }
}
