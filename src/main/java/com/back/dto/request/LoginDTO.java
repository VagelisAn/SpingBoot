package com.back.dto.request;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class LoginDTO implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;
	@NotBlank
	private String username;
	@NotBlank
	private String password;

	public LoginDTO(){	}

	public LoginDTO(String username, String password) {
		this.setUsername(username);
		this.setPassword(password);
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}