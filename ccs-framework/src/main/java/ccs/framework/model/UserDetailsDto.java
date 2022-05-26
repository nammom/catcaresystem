package ccs.framework.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsDto implements UserDetails{

	private Long USERCD;
	private String USERID;
	private String PASSWORD;
	private String NICKNAME;
	private String BIRTHDATE;
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
	

	public String getNickname() {
		// TODO Auto-generated method stub
		return NICKNAME;
	}
	
	public String getBirthDate() {
		// TODO Auto-generated method stub
		return BIRTHDATE;
	}
	
	public Long getUserCd() {
		// TODO Auto-generated method stub
		return USERCD;
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
