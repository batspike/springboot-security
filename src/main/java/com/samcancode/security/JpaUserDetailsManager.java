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
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import com.samcancode.security.domain.JpaAuthority;
import com.samcancode.security.domain.JpaSecurityUser;
import com.samcancode.security.repository.JpaSecurityUserRepository;

@Service
public class JpaUserDetailsManager implements UserDetailsManager, UserDetailsPasswordService {
	@Autowired
	private JpaSecurityUserRepository userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

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

	@Override
	public UserDetails updatePassword(UserDetails user, String newPassword) {
		return null;//TODO
	}

	@Override
	public void createUser(UserDetails user) {
		JpaSecurityUser u = (JpaSecurityUser) user;
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		u.setPassword(encodedPassword);
		userRepo.save(u);
	}

	@Override
	public void updateUser(UserDetails user) {
		//TODO
	}

	@Override
	public void deleteUser(String username) {
		//TODO
	}

	@Override
	public void changePassword(String oldPassword, String newPassword) {
		//TODO
	}

	@Override
	public boolean userExists(String username) {
		return userRepo.findByUsername(username).isPresent();
	}

}
