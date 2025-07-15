package com.tecnova.technical_test.application.mapper;

import com.tecnova.technical_test.domain.model.User;
import com.tecnova.technical_test.domain.model.dto.request.UserRequest;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserRequestMapperTest {

    private final UserRequestMapper userRequestMapper = Mappers.getMapper(UserRequestMapper.class);

    @Test
    void toDomain_shouldMapAllFieldsCorrectly() {
        UserRequest userRequest = new UserRequest();
        userRequest.setName("Jane Doe");
        userRequest.setEmail("jane.doe@example.com");

        User domainUser = userRequestMapper.toDomain(userRequest);

        assertNotNull(domainUser, "El objeto de dominio mapeado no debería ser nulo");
        assertEquals(userRequest.getName(), domainUser.getName(), "El nombre del usuario debería coincidir");
        assertEquals(userRequest.getEmail(), domainUser.getEmail(), "El email del usuario debería coincidir");
    }

    @Test
    void toDomain_shouldHandleNullUserRequest() {
        UserRequest userRequest = null;

        User domainUser = userRequestMapper.toDomain(userRequest);

        assertNull(domainUser, "Mapear un objeto UserRequest nulo debería resultar en un User de dominio nulo");
    }

    @Test
    void toDomain_shouldHandlePartialUserRequest() {
        UserRequest userRequest = new UserRequest();
        userRequest.setName("Partial User"); // Solo se establece el nombre

        User domainUser = userRequestMapper.toDomain(userRequest);

        assertNotNull(domainUser, "El objeto de dominio mapeado no debería ser nulo");
        assertEquals(userRequest.getName(), domainUser.getName(), "El nombre del usuario debería coincidir");
    }
}
