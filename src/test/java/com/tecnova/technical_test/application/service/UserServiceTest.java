package com.tecnova.technical_test.application.service;

import com.tecnova.technical_test.application.mapper.UserDtoMapper;
import com.tecnova.technical_test.application.mapper.UserRequestMapper;
import com.tecnova.technical_test.domain.model.User;
import com.tecnova.technical_test.domain.model.dto.UserDto;
import com.tecnova.technical_test.domain.model.dto.request.UserRequest;
import com.tecnova.technical_test.domain.port.IUserPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class UserServiceTest {

    @Mock
    private IUserPort userPort;

    @Mock
    private UserDtoMapper userDtoMapper;

    @Mock
    private UserRequestMapper userRequestMapper;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetById() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        UserDto userDto = new UserDto();
        userDto.setId(userId);

        when(userPort.getById(userId)).thenReturn(user);
        when(userDtoMapper.toDto(user)).thenReturn(userDto);

        UserDto result = userService.getById(userId);

        assertNotNull(result);
        assertEquals(userId, result.getId());
        verify(userPort).getById(userId);
        verify(userDtoMapper).toDto(user);
    }

    @Test
    void testGetAllUser() {
        User user1 = new User();
        user1.setId(1L);
        User user2 = new User();
        user2.setId(2L);

        UserDto dto1 = new UserDto();
        dto1.setId(1L);
        UserDto dto2 = new UserDto();
        dto2.setId(2L);

        when(userPort.getAllUser()).thenReturn(List.of(user1, user2));
        when(userDtoMapper.toDto(user1)).thenReturn(dto1);
        when(userDtoMapper.toDto(user2)).thenReturn(dto2);

        List<UserDto> result = userService.getAllUser();

        assertEquals(2, result.size());
        verify(userPort).getAllUser();
    }

    @Test
    void testCreateUser() {
        UserRequest request = new UserRequest();
        request.setName("John");

        User domainUser = new User();
        domainUser.setName("John");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setName("John");

        UserDto dto = new UserDto();
        dto.setId(1L);
        dto.setName("John");

        when(userRequestMapper.toDomain(request)).thenReturn(domainUser);
        when(userPort.createUser(domainUser)).thenReturn(savedUser);
        when(userDtoMapper.toDto(savedUser)).thenReturn(dto);

        UserDto result = userService.createUser(request);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(userRequestMapper).toDomain(request);
        verify(userPort).createUser(domainUser);
        verify(userDtoMapper).toDto(savedUser);
    }

    @Test
    void testUpdateUser() {
        Long userId = 1L;
        UserRequest request = new UserRequest();
        request.setName("Updated");

        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setName("Old");

        User updatedUser = new User();
        updatedUser.setId(userId);
        updatedUser.setName("Updated");

        UserDto updatedDto = new UserDto();
        updatedDto.setId(userId);
        updatedDto.setName("Updated");

        when(userPort.getById(userId)).thenReturn(existingUser);
        when(userRequestMapper.toDomain(request)).thenReturn(updatedUser);
        when(userPort.updateUser(updatedUser)).thenReturn(updatedUser);
        when(userDtoMapper.toDto(updatedUser)).thenReturn(updatedDto);

        UserDto result = userService.updateUser(request, userId);

        assertEquals("Updated", result.getName());
        verify(userPort).getById(userId);
        verify(userPort).updateUser(updatedUser);
    }

    @Test
    void testDeleteUserById() {
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setId(userId);

        when(userPort.getById(userId)).thenReturn(existingUser);

        userService.deleteUserById(userId);

        verify(userPort).getById(userId);
        verify(userPort).deleteUserById(userId);
    }
}
