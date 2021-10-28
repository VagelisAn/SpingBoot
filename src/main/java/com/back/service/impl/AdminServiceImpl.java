package com.back.service.impl;

import com.back.dto.UserDTO;
import com.back.dto.response.MessageResponse;
import com.back.entity.*;
import com.back.repository.*;
import com.back.service.ApplicationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AdminServiceImpl implements ApplicationServices {

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

    public AdminServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, UserInfoRepository userInfoRepository, UserRolePrivilegeRepository userRolePrivilegeRepository, UserToRoleRepository userToRoleRepository, UserRoleRepository userRoleRepository, UserPrivilegeRepository userPrivilegeRepository, PasswordEncoder encoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.userInfoRepository = userInfoRepository;
        this.userRolePrivilegeRepository = userRolePrivilegeRepository;
        this.userToRoleRepository = userToRoleRepository;
        this.userRoleRepository = userRoleRepository;
        this.userPrivilegeRepository = userPrivilegeRepository;
        this.encoder = encoder;
    }

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

            List<UserRolePrivilege> userRolePrivileges = userRole.getUserRolePrivileges();
            List<UserRolePrivilege> userRolePrivilegesStore = new ArrayList<>();

            userRole = userRoleRepository.save(userRole);

            for(UserRolePrivilege privilege :userRolePrivileges) {
                UserPrivilege userPrivilege = privilege.getPrivilege();
                userPrivilege = userPrivilegeRepository.save(userPrivilege);

                privilege.setPrivilege(userPrivilege);
                privilege.setRole(userRole);
                userRolePrivilegesStore.add(userRolePrivilegeRepository.save(privilege));
            }
            role.setUser(user);
            role.setRole(userRole);

            userToRolesStore.add(userToRoleRepository.save(role));
            user.setUserToRoles(userToRolesStore);
        }

        user.setUserInfo(userInfo);
        userRepository.save(user);

        return new MessageResponse("User registered successfully!");
    }

    public UserDTO getUserById(long id){

        Set<GrantedAuthority> authorities = new HashSet<>();

        User user = userRepository.findAllById(id);

            UserDTO userDTO = new UserDTO(
                    user.getId(),
                    user.getUsername(),
                    user.isActivate(),
                    user.getUserInfo().getFirstName(),
                    user.getUserInfo().getLastName(),
                    user.getUserInfo().getDepartment(),
                    user.getUserInfo().getAddress()
                    ,user.getUserInfo().getPhone());

        for (UserToRole userToRole : user.getUserToRoles()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + userToRole.getRole().getRoleName()));
            for (UserRolePrivilege userRoleToPrivilege : userToRole.getRole().getUserRolePrivileges()) {
                authorities.add(new SimpleGrantedAuthority(userRoleToPrivilege.getPrivilege().getPrivilegeName()));
            }
        }

        userDTO.setAuthorities(authorities);

        return userDTO;
    }

    public List<UserDTO> getUsers(){
        List<User> listUser = userRepository.findAll();
        List<UserDTO> userDTOS = new ArrayList<>();

        for (User user: listUser
             ) {
            UserDTO userDTO = new UserDTO(
                    user.getId(),
                    user.getUsername(),
                    user.isActivate(),
                    user.getUserInfo().getFirstName(),
                    user.getUserInfo().getLastName(),
                    user.getUserInfo().getDepartment(),
                    user.getUserInfo().getAddress()
                    ,user.getUserInfo().getPhone());

            Set<GrantedAuthority> authorities = new HashSet<>();

            for (UserToRole userToRole : user.getUserToRoles()) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + userToRole.getRole().getRoleName()));
                for (UserRolePrivilege userRoleToPrivilege : userToRole.getRole().getUserRolePrivileges()) {
                    authorities.add(new SimpleGrantedAuthority(userRoleToPrivilege.getPrivilege().getPrivilegeName()));
                }
            }

            userDTO.setAuthorities(authorities);
            userDTOS.add(userDTO);
        }

        return userDTOS;
    }


}
