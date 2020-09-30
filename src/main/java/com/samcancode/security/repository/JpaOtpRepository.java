package com.samcancode.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.samcancode.security.domain.JpaOTP;

public interface JpaOtpRepository extends JpaRepository<JpaOTP, Integer> {
	Optional<JpaOTP> findByUsername(String username);
}
