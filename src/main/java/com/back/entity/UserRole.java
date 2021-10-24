package com.back.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name ="user_role")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="role_name")
    private String roleName;
    @OneToMany(mappedBy = "role")
    private List<UserRolePrivilege> userRolePrivileges;

    public UserRole() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<UserRolePrivilege> getUserRolePrivileges() {
        return userRolePrivileges;
    }

    public void setUserRolePrivileges(List<UserRolePrivilege> userRolePrivileges) {
        this.userRolePrivileges = userRolePrivileges;
    }
}
