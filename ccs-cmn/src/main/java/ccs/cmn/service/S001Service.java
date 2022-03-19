package ccs.cmn.service;

import java.util.List;
import java.util.Map;

public interface S001Service {

	public List<Map<String, Object>> selectUsersByUserName(Map<String, Object> map);
	
	public List<Map<String, Object>> selectAuthoritiesByUserName(Map<String, Object> map);
	
	public List<Map<String, Object>> selectGroupAuthoritiesByUserName(Map<String, Object> map);
}
