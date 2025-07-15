package com.tecnova.technical_test.infrastructure.rest.controller;

import com.tecnova.technical_test.application.usecase.IUserService;
import com.tecnova.technical_test.domain.model.dto.UserDto;
import com.tecnova.technical_test.domain.model.dto.request.UserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/app/user")
@Tag(name = "User Controller", description = "Controller to management all operation related with User")
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get one user by Id", description = "Returns an existing user in DB by Id")
    public ResponseEntity<UserDto> getByIdUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @GetMapping()
    @Operation(summary = "Get all user", description = "Returns all users in DB")
    public ResponseEntity<List<UserDto>> getAllUser() {
        return ResponseEntity.ok(userService.getAllUser());
    }

    @PostMapping()
    @Operation(summary = "Create user",
            description = "API to create a user in the DB, HttpCode 201 is returned if correct")
    public ResponseEntity<UserDto> createUser(@RequestBody UserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userRequest));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Edit user",
            description = "API to edit a user in the DB, HttpCode 200 is returned if correct")
    public ResponseEntity<UserDto> editUser(@RequestBody UserRequest userRequest,
                            @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(userRequest, id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user",
            description = "API to Delete a user in the DB, HttpCode 204 is returned if correct")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
