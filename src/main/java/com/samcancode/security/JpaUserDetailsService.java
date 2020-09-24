package com.samcancode.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.samcancode.security.domain.JpaAuthority;
import com.samcancode.security.domain.JpaSecurityUser;
import com.samcancode.security.repository.JpaSecurityUserRepository;

@Service
public class JpaUserDetailsService implements UserDetailsService {
	@Autowired
	private JpaSecurityUserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		JpaSecurityUser jpaUser = userRepo.findByUsername(username).orElseThrow( () -> new UsernameNotFoundException(username));
		return new User(
				jpaUser.getUsername(), 
				jpaUser.getPassword(), 
				jpaUser.getEnabled(), 
				jpaUser.getAccountNonExpired(),
				jpaUser.getCredentialsNonExpired(), 
				jpaUser.getAccountNonLocked(), 
				convertToSpringAuthorities(jpaUser.getAuthorities())
		);
	}
	
	private Collection<? extends GrantedAuthority> convertToSpringAuthorities(Set<JpaAuthority> authorities) {
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

}
