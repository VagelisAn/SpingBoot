package com.back.repository;

import com.back.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

            UserInfo findByFirstNameAndLastName(String firstName, String lastName);

    UserInfo findById(long id);

            List<UserInfo> findByDepartment(String department);

            List<UserInfo> findAll();
}