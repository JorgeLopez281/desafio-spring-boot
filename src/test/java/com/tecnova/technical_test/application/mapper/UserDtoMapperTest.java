package com.tecnova.technical_test.application.mapper;

import com.tecnova.technical_test.domain.model.Task;
import com.tecnova.technical_test.domain.model.User;
import com.tecnova.technical_test.domain.model.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserDtoMapperTest {

    private final UserDtoMapper userDtoMapper = Mappers.getMapper(UserDtoMapper.class);

    @Test
    void toDto_shouldMapAllFieldsCorrectly() {
        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task());

        User domainUser = new User();
        domainUser.setId(10L);
        domainUser.setName("testuser");
        domainUser.setEmail("hashedpassword123");
        domainUser.setTaskList(taskList);

        UserDto userDto = userDtoMapper.toDto(domainUser);

        assertNotNull(userDto, "El DTO mapeado no debería ser nulo");
        assertEquals(domainUser.getId(), userDto.getId(), "El ID del usuario debería coincidir");
        assertEquals(domainUser.getName(), userDto.getName(), "El nombre de usuario debería coincidir");
        assertEquals(domainUser.getName(), userDto.getName(), "El nombre del rol debería coincidir");
    }

    @Test
    void toDto_shouldHandleNullDomainObject() {
        User domainUser = null;

        UserDto userDto = userDtoMapper.toDto(domainUser);

        assertEquals(null, userDto, "Mapear un objeto de dominio nulo debería resultar en un DTO nulo");
    }

    @Test
    void toDto_shouldHandlePartialDomainObject() {
        User domainUser = new User();
        domainUser.setId(20L);
        domainUser.setName("partialuser");

        UserDto userDto = userDtoMapper.toDto(domainUser);

        assertNotNull(userDto, "El DTO mapeado no debería ser nulo");
        assertEquals(domainUser.getId(), userDto.getId(), "El ID del usuario debería coincidir");
        assertEquals(domainUser.getName(), userDto.getName(), "El nombre de usuario debería coincidir");
    }
}
