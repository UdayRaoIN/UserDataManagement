package com.user.controller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.user.entity.UserEntity;
import com.user.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Test
    public void savePerson_Success() throws Exception {
        String username = "Uday";
        String department = "IT";
        String manager = "Rao";
        when(userService.saveUser(username, department, manager)).thenReturn("User saved successfully");

        ResponseEntity<String> response = userController.saveUser(username, department, manager);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("User saved successfully", response.getBody());
    }

    @Test
    public void savePerson_EmptyUsername_BadRequest() throws Exception {
        String department = "IT";
        String manager = "Rao";

        when(userController.saveUser("", department, manager)).thenThrow(IllegalArgumentException.class);

        assertDoesNotThrow(() -> userController.saveUser("", department, manager));

    }

    @Test
    public void updatePerson_Success() throws Exception {
        int id = 1;
        String username = "UpdatedUday";
        String department = "UpdatedIT";
        String manager = "UpdatedRao";
        when(userService.updateUser(id, username, department, manager)).thenReturn("User updated successfully");

        ResponseEntity<String> response = userController.updateUser(id, username, department, manager);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User updated successfully", response.getBody());
    }

    @Test
    public void updatePerson_InvalidId_NotFound() throws Exception {
        int invalidId = -1;
        String username = "UpdatedUday";
        String department = "UpdatedIT";
        String manager = "UpdatedRao";

        doThrow(new EntityNotFoundException("User with ID " + invalidId + " not found"))
                .when(userService).updateUser(invalidId, username, department, manager);

        ResponseEntity<String> response = userController.updateUser(invalidId, username, department, manager);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An error occurred while updating the User: User with ID "+invalidId+" not found", response.getBody());
    }
    


    @Test
    public void deletePerson_Success() throws Exception {
        int id = 1;
        when(userService.deleteUser(id)).thenReturn("User deleted successfully");

        ResponseEntity<String> response = userController.deleteUser(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User deleted successfully", response.getBody());
    }

    @Test
    public void deletePerson_InvalidId_NotFound() throws Exception {
        int invalidId = -1;

        doThrow(new EntityNotFoundException("User with ID " + invalidId + " not found"))
                .when(userService).deleteUser(invalidId);

        ResponseEntity<String> response = userController.deleteUser(invalidId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An error occurred while deleting the User: User with ID "+invalidId+" not found", response.getBody());
    }

    @Test
    public void retrievePerson_Success() throws Exception {
        String username = "Uday";
        String department = "IT";
        String manager = "Rao";
        UserEntity expectedPerson = new UserEntity();
        expectedPerson.setId(1);
        expectedPerson.setUsername(username);
        expectedPerson.setDepartment(department);
        expectedPerson.setManager(manager);

        when(userService.retrieveUser(username, department, manager)).thenReturn(expectedPerson);

        ResponseEntity<?> response = userController.retrieveUser(username, department, manager);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedPerson, response.getBody());
    }

    @Test
    public void retrievePerson_NotFound() throws Exception {
        String username = "Uday";
        String department = "IT";
        String manager = "Rao";

        doThrow(new EntityNotFoundException("User not found"))
                .when(userService).retrieveUser(username, department, manager);

        ResponseEntity<?> response = userController.retrieveUser(username, department, manager);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An error occurred while retrieving the User: User not found", response.getBody());
    }

}