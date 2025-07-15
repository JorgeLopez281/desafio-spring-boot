package com.tecnova.technical_test.infrastructure.rest.controller;

import com.tecnova.technical_test.application.usecase.IUserService;
import com.tecnova.technical_test.domain.model.dto.UserDto;
import com.tecnova.technical_test.domain.model.dto.request.UserRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    @Mock
    private IUserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getByIdUser_ShouldReturnUser() {
        Long userId = 1L;
        UserDto userDto = new UserDto(userId, "Jorge Lopez", "jlopez@test.co");
        when(userService.getById(userId)).thenReturn(userDto);

        ResponseEntity<UserDto> response = userController.getByIdUser(userId);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(userDto, response.getBody());
        verify(userService, times(1)).getById(userId);
    }

    @Test
    void getAllUser_ShouldReturnListOfUsers() {
        List<UserDto> users = List.of(
                new UserDto(1L, "John Doe", "john@example.com"),
                new UserDto(2L, "Jane Smith", "jane@example.com")
        );
        when(userService.getAllUser()).thenReturn(users);

        ResponseEntity<List<UserDto>> response = userController.getAllUser();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(users, response.getBody());
    }

    @Test
    void createUser_ShouldReturnCreatedUser() {
        UserRequest request = new UserRequest("John", "john@example.com");
        UserDto createdUser = new UserDto(1L, "John", "john@example.com");
        when(userService.createUser(request)).thenReturn(createdUser);

        ResponseEntity<UserDto> response = userController.createUser(request);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(createdUser, response.getBody());
    }

    @Test
    void editUser_ShouldReturnUpdatedUser() {
        Long userId = 1L;
        UserRequest request = new UserRequest("Updated Name", "updated@example.com");
        UserDto updatedUser = new UserDto(userId, "Updated Name", "updated@example.com");
        when(userService.updateUser(request, userId)).thenReturn(updatedUser);

        ResponseEntity<UserDto> response = userController.editUser(request, userId);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(updatedUser, response.getBody());
    }

    @Test
    void deleteUser_ShouldReturnNoContent() {
        Long userId = 1L;

        ResponseEntity<Void> response = userController.deleteUser(userId);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService, times(1)).deleteUserById(userId);
    }

}
