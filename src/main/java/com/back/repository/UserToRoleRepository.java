package com.back.repository;

import com.back.entity.UserRolePrivilege;
import com.back.entity.UserToRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserToRoleRepository extends JpaRepository<UserToRole, Long> {

}
