package com.samcancode.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.samcancode.security.SecurityUser;

public interface SecurityUserRepository extends JpaRepository<SecurityUser, Integer> {
	Optional<SecurityUser> findUserByUsername(String username);
}
