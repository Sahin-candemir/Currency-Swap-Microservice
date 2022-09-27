package com.patikadev.account.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.patikadev.account.service.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByUsername(String username);

	Boolean existsByUsername(String username);
}
