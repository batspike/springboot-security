package com.samcancode.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.samcancode.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findUserByUsername(String username);
}
