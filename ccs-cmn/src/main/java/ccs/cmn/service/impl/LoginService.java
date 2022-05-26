package ccs.cmn.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ccs.cmn.mapper.S001Mapper;
import ccs.framework.model.UserDetailsDto;
import ccs.framework.util.SessionUtility;

@Service(value="loginService")
public class LoginService implements UserDetailsService {

	@Resource(name="S001Mapper")
	private S001Mapper s001mapper; 

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<String> authList = new ArrayList<String>();

		
		UserDetailsDto userDetailsDto = s001mapper.selectUsersByUserName(username);
		
		if (userDetailsDto == null) { //User을 찾지 못했을 경우
			throw new UsernameNotFoundException(username);
		}
		else {
			authList = s001mapper.selectAuthoritiesByUserName(username);
			userDetailsDto.setAuthority(authList);
			SessionUtility.setUserDetails(userDetailsDto);
			
		}
		
		return userDetailsDto; //완전한 UserDetails 객체
	}

}
