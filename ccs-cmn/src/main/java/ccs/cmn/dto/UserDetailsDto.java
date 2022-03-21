package ccs.cmn.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsDto implements UserDetails{

	private String USERID;
	private String PASSWORD;
	private boolean ENABLED;
	private List<GrantedAuthority> authority;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authority;
	}
	
	public void setAuthority(List<String> authList) {
		List<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
		for(int i=0;i<authList.size();i++) {
			auth.add(new SimpleGrantedAuthority(authList.get(i)));
		}
		this.authority=auth;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return PASSWORD;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return USERID;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return ENABLED;
	}
}
