package com.back.controller;

import com.back.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RolesAllowed("ADMIN")
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

//    @Autowired
//    private JwtUserDetailsServiceImpl userDetailsService;
//
//    public AdminController(JwtUserDetailsServiceImpl userDetailsService) {
//        this.userDetailsService = userDetailsService;
//    }
//
//
//    @PostMapping("/register")
//    public ResponseEntity<?> saveUser(@RequestBody User user) throws Exception {
//        return ResponseEntity.ok(userDetailsService.save(user));
//    }
//
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
//    @GetMapping(value = "/getusers")
//    public List<User> getUserList() throws Exception {
//        return   userDetailsService.getUsers();
//    }
//
//    @GetMapping("/getuser/{id}")
//    public User getUserList(@RequestBody User user) throws Exception {
//        return   userDetailsService.getUser(user.getId());
//    }

}
