package com.samcancode.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.samcancode.security.domain.JpaAuthority;

public interface JpaAuthorityRepository extends JpaRepository<JpaAuthority, Integer> {

}
