package com.cart_app.user_service.controller;

import com.cart_app.user_service.dto.UserDTO;
import com.cart_app.user_service.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping
    public ResponseEntity<?> findAllUsers(){
        return ResponseEntity.ok(userService.findAllUsers());
    }
    @GetMapping("/{username}")
    public ResponseEntity<?> findAllUsers(@PathVariable String username){
        return ResponseEntity.ok(userService.searchUserByUsername(username));
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) throws URISyntaxException {
        String response = userService.createUser(userDTO);
        return ResponseEntity.created(new URI("/keycloak/user/create")).body(response);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable String userId , @RequestBody UserDTO userDTO){
        userService.updateUser(userId, userDTO);
        return ResponseEntity.ok("User updated successfully!");
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId){
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
