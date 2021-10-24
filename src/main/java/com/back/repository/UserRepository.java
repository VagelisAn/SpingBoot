package com.back.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.back.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	
	User findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

	List<User> findAll();

	User findAllById(Long id);

//	List<User> findAllByRole(String role);
}