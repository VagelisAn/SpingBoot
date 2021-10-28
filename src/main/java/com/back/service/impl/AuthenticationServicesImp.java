package com.back.service.impl;

import com.back.config.jwt.JwtUtils;
import com.back.dto.JwtResponse;
import com.back.dto.request.LoginDTO;
import com.back.dto.response.MessageResponse;
import com.back.entity.*;
import com.back.repository.*;
import com.back.service.AuthenticationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class AuthenticationServicesImp implements AuthenticationServices {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserRolePrivilegeRepository userRolePrivilegeRepository;

    @Autowired
    private UserToRoleRepository userToRoleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserPrivilegeRepository userPrivilegeRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    public AuthenticationServicesImp(AuthenticationManager authenticationManager, UserRepository userRepository, UserInfoRepository userInfoRepository, UserRolePrivilegeRepository userRolePrivilegeRepository, UserToRoleRepository userToRoleRepository, UserRoleRepository userRoleRepository, UserPrivilegeRepository userPrivilegeRepository, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.userInfoRepository = userInfoRepository;
        this.userRolePrivilegeRepository = userRolePrivilegeRepository;
        this.userToRoleRepository = userToRoleRepository;
        this.userRoleRepository = userRoleRepository;
        this.userPrivilegeRepository = userPrivilegeRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public JwtResponse logIn(LoginDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
    }

    @Override
    public MessageResponse register(User signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new MessageResponse("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new MessageResponse("Error: Email is already in use!");
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
        userInfo = userInfoRepository.save(userInfo);

        List<UserToRole> userToRoles = signUpRequest.getUserToRoles();


        for(UserToRole role : userToRoles){
            List<UserToRole> userToRolesStore = new ArrayList<>();
            UserRole userRole =  role.getRole();
            UserRole userRoleFromTep = userRoleRepository.findByRoleName(userRole.getRoleName().toUpperCase());
//            userRole.setRoleName(userRole.getRoleName().toUpperCase());
            List<UserRolePrivilege> userRolePrivileges = userRole.getUserRolePrivileges();
            List<UserRolePrivilege> userRolePrivilegesStore = new ArrayList<>();
//            userRole = userRoleRepository.save(userRole);
            for(UserRolePrivilege privilege :userRolePrivileges) {
//                UserPrivilege userPrivilege = privilege.getPrivilege();
//                userPrivilege = userPrivilegeRepository.save(userPrivilege);
                UserPrivilege userPrivilege = userPrivilegeRepository.findByPrivilegeName(privilege.getPrivilege().getPrivilegeName());
                privilege.setPrivilege(userPrivilege);
                privilege.setRole(userRoleFromTep);
                userRolePrivilegesStore.add(userRolePrivilegeRepository.save(privilege));
            }
            role.setUser(user);
            role.setRole(userRoleFromTep);
            userToRolesStore.add(userToRoleRepository.save(role));
            user.setUserToRoles(userToRolesStore);
        }
        user.setUserInfo(userInfo);
        userRepository.save(user);
        return new MessageResponse("User registered successfully!");
    }
}
