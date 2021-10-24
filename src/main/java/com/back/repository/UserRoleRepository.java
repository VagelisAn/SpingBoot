package com.back.repository;

import com.back.entity.UserRole;
import org.springframework.data.repository.CrudRepository;

public interface UserRoleRepository  extends CrudRepository<UserRole, Long> {
}
