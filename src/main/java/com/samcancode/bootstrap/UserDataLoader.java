package com.samcancode.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.samcancode.security.domain.JpaAuthority;
import com.samcancode.security.domain.JpaSecurityUser;
import com.samcancode.security.repository.JpaAuthorityRepository;
import com.samcancode.security.repository.JpaSecurityUserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class UserDataLoader implements CommandLineRunner {
	
	private final JpaAuthorityRepository authorityRepo;
	private final JpaSecurityUserRepository	userRepo;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public void run(String... args) throws Exception {
		JpaAuthority user = authorityRepo.save(JpaAuthority.builder().role("ROLE_USER").build());
		JpaAuthority admin = authorityRepo.save(JpaAuthority.builder().role("ROLE_ADMIN").build());
		JpaAuthority cust = authorityRepo.save(JpaAuthority.builder().role("ROLE_CUSTOMER").build());
		
		userRepo.save(
					JpaSecurityUser.builder()
					.username("user")
					.password(passwordEncoder.encode("user"))
					.authority(user)
					.build()
				);
		
		userRepo.save(
				JpaSecurityUser.builder()
				.username("admin")
				.password(passwordEncoder.encode("admin"))
				.authority(admin)
				.build()
				);
		
		userRepo.save(
				JpaSecurityUser.builder()
				.username("cust")
				.password(passwordEncoder.encode("cust"))
				.authority(cust)
				.build()
				);
		
	}

}
