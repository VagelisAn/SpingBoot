package com.back.repository;

import com.back.entity.UserPrivilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserPrivilegeRepository extends JpaRepository<UserPrivilege, Long> {

    UserPrivilege findByPrivilegeName(String privilegeName);
}
