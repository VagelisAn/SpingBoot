package com.back.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class UserDTO  {
	private long id;
	private String username;
	private boolean activate;
	private String firstName;
	private String lastName;
	private String department;
	private String address;
	private String phone;
	private List<RoleDTO> roles;

	public UserDTO() {
	}

	public UserDTO(long id, String username, boolean activate, String firstName, String lastName, String department, String address, String phone) {
		this.id = id;
		this.username = username;
		this.activate = activate;
		this.firstName = firstName;
		this.lastName = lastName;
		this.department = department;
		this.address = address;
		this.phone = phone;
	}


	public List<RoleDTO> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleDTO> roles) {
		this.roles = roles;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isActivate() {
		return activate;
	}

	public void setActivate(boolean activate) {
		this.activate = activate;
	}

	public String getUsername() {
		return username;
	}


}