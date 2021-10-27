package com.back.controller;

import com.back.dto.UserDTO;
import com.back.entity.User;
import com.back.service.impl.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RolesAllowed("USER")
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private AdminServiceImpl adminService;

    public UserController(AdminServiceImpl adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@RequestBody User user) throws Exception {
        return ResponseEntity.ok(adminService.register(user));
    }

    @GetMapping("/get")
    public ResponseEntity<?> saveUsera() throws Exception {
        return ResponseEntity.ok("adminService.register(user)");
    }

    @GetMapping("/getusers")
    public List<UserDTO> getUserList() throws Exception {
        return adminService.getUsers();
    }

    @GetMapping("/getuser/{id}")
    public UserDTO getUserList(@PathVariable Long id) throws Exception {
        return   adminService.getUserById(id);
    }

//    @DeleteMapping("/delete")
//    public ResponseEntity<?> deleteUser(@RequestBody User user) throws Exception {
//        userDetailsService.delete(user);
//        return ResponseEntity.ok(userDetailsService.getUsers());
//    }
//
//    @PutMapping("/modify/{id}")
//    public ResponseEntity<?> modifyUser(@RequestBody User user,@PathVariable Long id) throws Exception {
//        return ResponseEntity.ok(userDetailsService.update(user,id));
//    }
//


}