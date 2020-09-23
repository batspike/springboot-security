package com.samcancode.security;

import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class SecurityUser implements UserDetails {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String username;
	private String password;
	private String fullname;
	private String email;
	private String phoneNumber;
	private Boolean accountExpired = false;
	private Boolean accountLocked = false;
	private Boolean credentialsExpired = false;
	private Boolean accountEnabled = true;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(
			() -> "ROLE_USER", () -> "ROLE_ADMIN", () -> "ROLE_CUSTOMER"
		);
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return !getAccountExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		return !getAccountLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return !getCredentialsExpired();
	}

	@Override
	public boolean isEnabled() {
		return getAccountEnabled();
	}

}
