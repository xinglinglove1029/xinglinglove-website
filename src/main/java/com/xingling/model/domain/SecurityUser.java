package com.xingling.model.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


public class SecurityUser extends User implements UserDetails {

	private static final long serialVersionUID = 5800599093391505463L;

	private String userId;

	private Collection<GrantedAuthority> authorities;

	public SecurityUser(User user, Collection<GrantedAuthority> grantedAuthorities) {
		if (user != null) {
			this.setUserId(user.getId());
			this.setUserName(user.getUserName());
			this.setPassword(user.getPassword());
			this.setRealName((user.getRealName()));
			this.setBirthday(user.getBirthday());
			this.setCellPhone(user.getCellPhone());
			this.setEmail(user.getEmail());
			this.setSex(user.getSex());
			this.setLastLoginDate(user.getLastLoginDate());
			this.authorities = grantedAuthorities;
		}
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return super.getPassword();
	}

	@Override
	public String getUsername() {
		return super.getUserName();
	}


	@Override
	public boolean isAccountNonExpired() {
		return true;
	}


	@Override
	public boolean isAccountNonLocked() {
		return true;
	}


	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}


	@Override
	public boolean isEnabled() {
		return true;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
