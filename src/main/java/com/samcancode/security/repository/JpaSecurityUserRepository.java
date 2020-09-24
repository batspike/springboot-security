package com.samcancode.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.samcancode.security.domain.JpaSecurityUser;

public interface JpaSecurityUserRepository extends JpaRepository<JpaSecurityUser, Integer> {
	Optional<JpaSecurityUser> findByUsername(String username);
}
