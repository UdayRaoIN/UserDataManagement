package com.user.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import javax.persistence.EntityNotFoundException;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.user.entity.UserEntity;
import com.user.repository.UserRepository;



@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @org.junit.jupiter.api.Test
    public void testUserPerson_Success() {
        doNothing().when(userRepository).saveUser("Uday", "IT", "Rao");
        String response = userService.saveUser("Uday", "IT", "Rao");
        assertEquals("User saved successfully", response);
    }

    @org.junit.jupiter.api.Test
    public void testUpdatePerson_Success() {
        int id = 1;
        doNothing().when(userRepository).updateUser(id, "UpdatedUday", "UpdatedIT", "UpdatedRao");
        String response = userService.updateUser(id, "UpdatedUday", "UpdatedIT", "UpdatedRao");
        assertEquals("User updated successfully", response);
    }

    @org.junit.jupiter.api.Test
    public void testDeletePerson_Success() {
        int id = 1;
        doNothing().when(userRepository).deleteUser(id);
        String response = userService.deleteUser(id);
        assertEquals("User deleted successfully", response);
    }

    @org.junit.jupiter.api.Test
    public void testRetrievePerson_Success() {
        String username = "Uday";
        String department = "IT";
        String manager = "Rao";
        UserEntity expectedPerson = new UserEntity();
        expectedPerson.setId(1);
        expectedPerson.setUsername(username);
        expectedPerson.setDepartment(department);
        expectedPerson.setManager(manager);

        when(userRepository.retrieveUser(username, department, manager)).thenReturn(expectedPerson);

        UserEntity retrievedPerson = userService.retrieveUser(username, department, manager);
        assertEquals(expectedPerson, retrievedPerson);
    }

    // Negative test cases

    @org.junit.jupiter.api.Test
    public void testUserPerson_EmptyUsername() {
        try {
            userService.saveUser("", "IT", "Rao");
        } catch (IllegalArgumentException e) {
            assertEquals("An error occurred while saving the User: Username cannot be empty", e.getMessage());
        }
    }
    
    @org.junit.jupiter.api.Test
    public void testDeleteUser_InvalidId() {
        int invalidId = -1;
        try {
            userService.deleteUser(invalidId);
        } catch (EntityNotFoundException e) {
            assertEquals("User with ID " + invalidId + " not found", e.getMessage());
        }
    }
    
    @org.junit.jupiter.api.Test
    public void testUpdateUser_InvalidId() {
        int invalidId = -1;
        try {
            userService.updateUser(invalidId, "UpdatedUday", "UpdatedIT", "UpdatedRao");
        } catch (EntityNotFoundException e) {
            assertEquals("User with ID " + invalidId + " not found", e.getMessage());
        }
    }

}