package com.back.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.back.config.jwt.JwtUtils;
import com.back.dto.JwtResponse;
import com.back.dto.request.LoginDTO;
import com.back.dto.request.SignUpDTO;
import com.back.dto.response.MessageResponse;
import com.back.entity.*;
import com.back.repository.UserInfoRepository;
import com.back.repository.UserRepository;
import com.back.service.impl.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDTO  loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword())
                );
        user = userRepository.save(user);

        UserInfo userInfo = new UserInfo(
                signUpRequest.getUserInfo().getFirstName(),
                signUpRequest.getUserInfo().getLastName(),
                signUpRequest.getUserInfo().getDepartment(),
                signUpRequest.getUserInfo().getAddress(),
                signUpRequest.getUserInfo().getPhone()
                );
        userInfo.setUser(user);
//        userInfo = userInfoRepository.save(userInfo);

        List<UserToRole> userToRoles = signUpRequest.getUserToRoles();

        userToRoles.forEach(role -> {
            List<UserRolePrivilege> userRolePrivileges =  null;
                switch (role.getRole().getRoleName()) {
                    case "admin":
                        userRolePrivileges = role.getRole().getUserRolePrivileges();
                        System.out.println(role.getRole().getRoleName());

                        break;
                    case "user":
                        userRolePrivileges = role.getRole().getUserRolePrivileges();
                        System.out.println(role.getRole().getRoleName());

                        break;
                    default:

                }
            });

        user.setUserInfo(userInfo);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
