package com.back.repository;

import com.back.entity.UserRole;
import com.back.entity.UserRolePrivilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserRolePrivilegeRepository extends JpaRepository<UserRolePrivilege, Long> {
}
