package ccs.framework.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ccs.framework.util.StringUtility;

public class UserInfo  implements java.io.Serializable {

	private String USERID;
	 
	private Map<String,Object> USER_PROPERTIES = new HashMap<String,Object>();
	
	private String USERPWD;

	
	
	private String USERNM;
	
	
	private String AUTHOR ;
	
	
	private String ORGN_NM;
	
	
	private String ORGN_ID;
	
	
	private String LAST_LGN_YMD;
	
	
	private String LAST_LGN_DT;
	
	private String BRTHDY;
	
	public String getAUTHOR() {
		return AUTHOR;
	}


	public void setAUTHOR(String AUTHOR) {
		this.AUTHOR = AUTHOR;
	}
	 /**
     * user가 가지고 있는 권한 집합
     */
    private List<UserPermission> USER_PERMISSIONS = new ArrayList<UserPermission>();
	
    public List<UserPermission> getUSER_PERMISSIONS() {
		if(USER_PERMISSIONS == null) USER_PERMISSIONS = new ArrayList<UserPermission>();
		return USER_PERMISSIONS;
	}


	public void setUSER_PERMISSIONS(List<UserPermission> uSER_PERMISSIONS) {
		USER_PERMISSIONS = uSER_PERMISSIONS;
	}
	
	public UserInfo ClearPermission(){
		this.getUSER_PERMISSIONS().clear();
		return this;
	}
	
	public UserInfo AddPermission(UserPermission permission){
		this.getUSER_PERMISSIONS().add(permission);
		return this;
	}
	
	public UserPermission getPermission(String POW){
		if(USER_PERMISSIONS == null) return null;
		for(UserPermission p : USER_PERMISSIONS){
			if(StringUtility.isEqual(p.getAUTHOR(), POW)){
				return p;
			}
		}
		return null;
	}
	
	public UserPermission getCurrentPermission(){
		if(USER_PERMISSIONS == null) return null;
		for(UserPermission p : USER_PERMISSIONS){
			if(StringUtility.isEqual(p.getAUTHOR(), this.AUTHOR)){
				return p;
			}
		}
		return null;
	}
	
	public UserInfo addUSER_PROPERTIES(String key, Object value){
		this.USER_PROPERTIES.put(key, value);
		return this;
	}

	public Object getUSER_PROPERTIES(String key){
		return this.USER_PROPERTIES.get(key);
	}


	public String getUSERID() {
		return USERID;
	}


	public void setUSERID(String uSERID) {
		USERID = uSERID;
	}


	public String getUSERPWD() {
		return USERPWD;
	}


	public void setUSERPWD(String uSERPWD) {
		USERPWD = uSERPWD;
	}


	public String getUSERNM() {
		return USERNM;
	}


	public void setUSERNM(String uSERNM) {
		USERNM = uSERNM;
	}


	public String getORGN_NM() {
		return ORGN_NM;
	}


	public void setORGN_NM(String oRGN_NM) {
		ORGN_NM = oRGN_NM;
	}


	public String getORGN_ID() {
		return ORGN_ID;
	}


	public void setORGN_ID(String oRGN_ID) {
		ORGN_ID = oRGN_ID;
	}


	public String getLAST_LGN_YMD() {
		return LAST_LGN_YMD;
	}


	public void setLAST_LGN_YMD(String lAST_LGN_YMD) {
		LAST_LGN_YMD = lAST_LGN_YMD;
	}


	public String getLAST_LGN_DT() {
		return LAST_LGN_DT;
	}


	public void setLAST_LGN_DT(String lAST_LGN_DT) {
		LAST_LGN_DT = lAST_LGN_DT;
	}


	public String getBRTHDY() {
		return BRTHDY;
	}

	public void setBRTHDY(String bRTHDY) {
		BRTHDY = bRTHDY;
	}
}
