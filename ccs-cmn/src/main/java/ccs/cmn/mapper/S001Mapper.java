package ccs.cmn.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;

import ccs.cmn.dto.UserDetailsDto;
import ccs.framework.annotations.Mapper;


@Mapper(value="S001Mapper")
public interface S001Mapper {

	public UserDetailsDto selectUsersByUserName(String username);
	
	public List<String> selectAuthoritiesByUserName(String username);
	
	public List<Map<String, Object>> selectGroupAuthoritiesByUserName(Map<String, Object> map);

}
