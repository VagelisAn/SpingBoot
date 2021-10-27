package com.back.service;

import com.back.dto.JwtResponse;
import com.back.dto.request.LoginDTO;
import com.back.dto.response.MessageResponse;
import com.back.entity.User;

public interface AuthenticationServices {

    JwtResponse logIn(LoginDTO loginRequest);

    MessageResponse register(User signUpRequest);

}
