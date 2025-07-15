package com.tecnova.technical_test.infrastructure.rest.controller;

import com.tecnova.technical_test.application.service.AuthService;
import com.tecnova.technical_test.domain.model.dto.LoginUserDto;
import com.tecnova.technical_test.domain.model.dto.NewUserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.security.auth.message.AuthException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/app/auth")
@Tag(name = "Authentication Controller",
        description = "Controller to management all operation related with Authentication")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(summary = "Get Token By User", description = "Login is performed to generate the corresponding token")
    public ResponseEntity<String> login(@Valid @RequestBody LoginUserDto loginUserDto,
                                        BindingResult bindingResult) throws AuthException {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Check your credentials");
        }
        String jwt = authService.authenticate(loginUserDto.getUserName(), loginUserDto.getPassword());
        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/register")
    @Operation(summary = "Register User in DB",
            description = "API to register a user in the Table where users who can interact with the APIs are stored")
    public ResponseEntity<String> register(@Valid @RequestBody NewUserDto newUserDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Check the fields");
        }
        authService.registerUser(newUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Registered");
    }

    @GetMapping("/check-auth")
    @Operation(summary = "Verify Token", description = "Check if the token sent in the Authorization header is correct")
    public ResponseEntity<String> checkAuth() {
        return ResponseEntity.ok().body("Authenticated");
    }
}
