package ccs.cmn.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;

import ccs.framework.annotations.Mapper;
import ccs.framework.model.UserDetailsDto;


@Mapper(value="S001Mapper")
public interface S001Mapper {

	public UserDetailsDto selectUsersByUserName(String username);
	
	public List<String> selectAuthoritiesByUserName(String username);
	
	public List<Map<String, Object>> selectGroupAuthoritiesByUserName(Map<String, Object> map);

}
