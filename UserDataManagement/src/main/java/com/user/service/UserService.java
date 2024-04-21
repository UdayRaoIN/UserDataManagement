package com.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.user.entity.UserEntity;
import com.user.repository.UserRepository;

@Service
public class UserService {

	@Autowired
    UserRepository userRepository;

   @Transactional
    public String saveUser(String username, String department, String manager) {
        try {
            userRepository.saveUser(username, department, manager);
            return "User saved successfully";
        } catch (DataAccessException e) {
            throw new RuntimeException("An error occurred while saving the User: " + e.getMessage());
        }
    }

    @Transactional
    public String updateUser(int id, String username, String department, String manager) {
        try {
            userRepository.updateUser(id, username, department, manager);
            return "User updated successfully";
        } catch (DataAccessException e) {
            throw new RuntimeException("An error occurred while updating the User: " + e.getMessage());
        }
    }

    @Transactional
    public String deleteUser(int id) {
        try {
            userRepository.deleteUser(id);
            return "User deleted successfully";
        } catch (DataAccessException e) {
            throw new RuntimeException("An error occurred while deleting the User: " + e.getMessage());
        }
    }

    @Transactional
    public UserEntity retrieveUser(String username, String department, String manager) {
        try {
            return userRepository.retrieveUser(username, department, manager);
        } catch (DataAccessException e) {
            throw new RuntimeException("An error occurred while retrieving the User: " + e.getMessage());
        }
    }
}
