package com.user.controller;

import com.user.entity.UserEntity;
import com.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
    UserService userService;

    @PostMapping("/save")
    public ResponseEntity<String> saveUser(@RequestParam String username, @RequestParam String department, @RequestParam String manager) {
        try {
            userService.saveUser(username, department, manager);
            return new ResponseEntity<>("User saved successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while saving the User: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable int id, @RequestParam String username, @RequestParam String department, @RequestParam String manager) {
        try {
            userService.updateUser(id, username, department, manager);
            return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while updating the User: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while deleting the User: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/retrieve")
    public ResponseEntity<?> retrieveUser(@RequestParam String username, @RequestParam String department, @RequestParam String manager) {
        if (department.isEmpty() || manager.isEmpty()) {
            return new ResponseEntity<>("Department or Manager cannot be empty", HttpStatus.BAD_REQUEST);
        }
        try {
            UserEntity user = userService.retrieveUser(username, department, manager);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("An error occurred while retrieving the User: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


 