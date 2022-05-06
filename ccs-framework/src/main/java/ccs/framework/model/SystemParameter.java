package ccs.framework.model;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import ccs.framework.dataservice.ServiceCall;
import ccs.framework.util.SessionUtility;
import ccs.framework.util.StringUtility;


public class SystemParameter {
private Map<String,Object> map = new HashMap<String,Object>();
	public SystemParameter(){
		this(null);
	}
	
	
	public List<Map<String,Object>> executeForList(String sql, Map<String,Object> parameters) throws Exception{
		ServiceCall callService = new ServiceCall("DBService","executeForList");
		callService.callMethod(sql,parameters,false);
		return (List<Map<String,Object>>)callService.getData();
		//DBService
	}
	
	public SystemParameter(Map<String,Object> map){
		if(map != null)
			this.putAll(map);
		initializeSystem();
		initializeSession();
	}
	
	public SystemParameter add(String key, Object val){
		map.put(key, val);
		return this;
	}
	
	public SystemParameter add(Map<String,Object> m){
		map.putAll(m);
		return this;
	}
	
	
	/**
	 * 세션 및 기본 시스템 정보
	 */
	private void initializeSession(){
		
		
		/**
		 * 현재 시간 정보
		 */
		
		
		UserDetailsDto user =null;
		try {
			user = SessionUtility.getUserDetails();
		} catch (Exception e) {
		}
		if(user != null){
			/**
			 * 로그인 사용자 정보 추가
			 */
			map.put("_SESSION_USER_ID_", 	 user.getUsername());				
			map.put("_SESSION_USER_NM_",	 user.getNickname());				
			map.put("_SESSION_USER_BRTHDY_", user.getBirthDate());
			map.put("_SESSION_AUTHOR_",		 user.getAuthorities());
			
			/**
			 * 권한 정보 추가
			 */
			
		}
	}
	
	private void initializeSystem(){

		map.put("_CURRENT_DATE_", LocalDate.now());
	}
	

	
	public void put(String key, Object value){
		map.put(key, value);
	}
	
	public void putAll(Map<String,Object> map){
		this.map.putAll(map);
	}
	
	public Map<String,Object> toMap(){
		
		return map;
	}
	
	public HashMap<String,Object> toHashMap(){
		
		return (HashMap<String,Object>)map;
	}
	
	public static SystemParameter create(){
		return new SystemParameter();
	}
}

