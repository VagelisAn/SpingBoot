package com.back.service.impl;

import com.back.config.jwt.JwtUtils;
import com.back.dto.JwtResponse;
import com.back.dto.RoleDTO;
import com.back.dto.UserDTO;
import com.back.dto.request.LoginDTO;
import com.back.dto.response.MessageResponse;
import com.back.entity.*;
import com.back.repository.*;
import com.back.service.ApplicationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        List<UserToRole> userToRolesStore = new ArrayList<>();

        for(UserToRole role : userToRoles){

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

        }
        user.setUserToRoles(userToRolesStore);
        user.setUserInfo(userInfo);
        userRepository.save(user);

        return new MessageResponse("User registered successfully!");
    }

    public UserDTO getUserById(long id){

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

            List<RoleDTO> roleDTOS = new ArrayList<>();

            List<UserToRole> userToRoles = user.getUserToRoles();
            List<String> privileges = new ArrayList<>();

            for(UserToRole role : userToRoles){
                UserRole userRole =  role.getRole();
                List<UserRolePrivilege> userRolePrivileges = userRole.getUserRolePrivileges();
                for(UserRolePrivilege privilege :userRolePrivileges) {
                    privileges.add(privilege.getPrivilege().getPrivilegeName());
                }
                RoleDTO roleDTO = new RoleDTO();

                roleDTO.setRole(role.getRole().getRoleName());
                roleDTO.setPrivilege(privileges);
                roleDTOS.add(roleDTO);
            }
            userDTO.setRoles(roleDTOS);

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

            List<RoleDTO> roleDTOS = new ArrayList<>();

            List<UserToRole> userToRoles = user.getUserToRoles();
            List<String> privileges = new ArrayList<>();

            for(UserToRole role : userToRoles){
                UserRole userRole =  role.getRole();
                List<UserRolePrivilege> userRolePrivileges = userRole.getUserRolePrivileges();
                for(UserRolePrivilege privilege :userRolePrivileges) {
                    privileges.add(privilege.getPrivilege().getPrivilegeName());
                }
                RoleDTO roleDTO = new RoleDTO();

                roleDTO.setRole(role.getRole().getRoleName());
                roleDTO.setPrivilege(privileges);
                roleDTOS.add(roleDTO);
            }
            userDTO.setRoles(roleDTOS);
            userDTOS.add(userDTO);
        }

        return userDTOS;
    }


}
