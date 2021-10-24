package com.back.repository;

import com.back.entity.UserRolePrivilege;
import com.back.entity.UserToRole;
import org.springframework.data.repository.CrudRepository;

public interface UserToRoleRepository extends CrudRepository<UserToRole, Long>{
}
