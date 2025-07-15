package com.tecnova.technical_test.infrastructure.rest.controller;

import com.tecnova.technical_test.application.service.AuthService;
import com.tecnova.technical_test.domain.model.dto.LoginUserDto;
import com.tecnova.technical_test.domain.model.dto.NewUserDto;
import com.tecnova.technical_test.infrastructure.adapter.exceptions.AuthException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthService authService;

    @Mock
    private BindingResult bindingResult;

    @Test
    void login_ShouldReturnToken_WhenValidCredentials() throws AuthException, jakarta.security.auth.message.AuthException {
        LoginUserDto loginUserDto = new LoginUserDto("user", "password");
        String expectedToken = "mocked-jwt-token";

        when(bindingResult.hasErrors()).thenReturn(false);
        when(authService.authenticate("user", "password")).thenReturn(expectedToken);

        ResponseEntity<String> response = authController.login(loginUserDto, bindingResult);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(expectedToken, response.getBody());
    }

    @Test
    void login_ShouldReturnBadRequest_WhenValidationFails() throws AuthException, jakarta.security.auth.message.AuthException {
        LoginUserDto loginUserDto = new LoginUserDto("", "");
        when(bindingResult.hasErrors()).thenReturn(true);

        ResponseEntity<String> response = authController.login(loginUserDto, bindingResult);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals("Check your credentials", response.getBody());
    }

    @Test
    void register_ShouldReturnCreated_WhenUserIsValid() {
        NewUserDto newUserDto = new NewUserDto("user", "Password123", "user");
        when(bindingResult.hasErrors()).thenReturn(false);

        ResponseEntity<String> response = authController.register(newUserDto, bindingResult);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals("Registered", response.getBody());
        verify(authService).registerUser(newUserDto);
    }

    @Test
    void register_ShouldReturnBadRequest_WhenValidationFails() {
        NewUserDto newUserDto = new NewUserDto("", "", null);
        when(bindingResult.hasErrors()).thenReturn(true);

        ResponseEntity<String> response = authController.register(newUserDto, bindingResult);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals("Check the fields", response.getBody());
    }

    @Test
    void checkAuth_ShouldReturnOk() {
        ResponseEntity<String> response = authController.checkAuth();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("Authenticated", response.getBody());
    }
}
