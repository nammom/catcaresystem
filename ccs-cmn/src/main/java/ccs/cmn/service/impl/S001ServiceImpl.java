package ccs.cmn.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ccs.cmn.dto.UserDetailsDto;
import ccs.cmn.mapper.S001Mapper;

@Service(value="loginService")
public class S001ServiceImpl implements UserDetailsService {

	@Resource(name="S001Mapper")
	private S001Mapper s001mapper; 

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<String> authList = new ArrayList<String>();

		
		UserDetailsDto userDetailsDto = s001mapper.selectUsersByUserName(username);
		
		authList = s001mapper.selectAuthoritiesByUserName(username);
		
		if (userDetailsDto == null) { //User을 찾지 못했을 경우
			throw new UsernameNotFoundException(username);
		}
		else {
			userDetailsDto.setAuthority(authList);
		}
		
		return userDetailsDto; //완전한 UserDetails 객체
	}

}
