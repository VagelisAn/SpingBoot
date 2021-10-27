package com.back.dto;

import java.util.List;

public class RoleDTO {

    private String role;
    private List<String> privilege;

    public RoleDTO() {
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<String> getPrivilege() {
        return privilege;
    }

    public void setPrivilege(List<String> privilege) {
        this.privilege = privilege;
    }
}
