package com.back.repository;

import com.back.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserRoleRepository  extends JpaRepository<UserRole, Long> {

    UserRole findByRoleName(String roleName);
}
