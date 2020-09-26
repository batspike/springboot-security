package com.samcancode.security.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.samcancode.security.domain.JpaAuthority;
import com.samcancode.security.domain.JpaSecurityUser;

public class SecurityUserDetails implements UserDetails {
	private static final long serialVersionUID = 1L;
	
	private final JpaSecurityUser user;
	public SecurityUserDetails(JpaSecurityUser user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<JpaAuthority> authorities = user.getAuthorities();
		if(authorities != null && authorities.size() > 0) {
			return authorities.stream()
					.map(JpaAuthority::getRole)
					.map(SimpleGrantedAuthority::new)
					.collect(Collectors.toSet());
		}
		else {
			return new HashSet<>();
		}
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return user.getAccountNonExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		return user.getAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return user.getCredentialsNonExpired();
	}

	@Override
	public boolean isEnabled() {
		return user.getEnabled();
	}

}
