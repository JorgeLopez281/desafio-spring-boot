package com.tecnova.technical_test.application.service;

import com.tecnova.technical_test.application.mapper.UserDtoMapper;
import com.tecnova.technical_test.application.mapper.UserRequestMapper;
import com.tecnova.technical_test.application.usecase.IUserService;
import com.tecnova.technical_test.domain.model.dto.UserDto;
import com.tecnova.technical_test.domain.model.dto.request.UserRequest;
import com.tecnova.technical_test.domain.port.IUserPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    private final IUserPort userPort;
    private final UserDtoMapper userDtoMapper;
    private final UserRequestMapper userRequestMapper;

    @Autowired
    public UserService(final IUserPort userPort,
                       final UserDtoMapper userDtoMapper, UserRequestMapper userRequestMapper) {
        this.userPort = userPort;
        this.userDtoMapper = userDtoMapper;
        this.userRequestMapper = userRequestMapper;
    }

    @Override
    public UserDto getById(Long id) {
        var userInfo = userPort.getById(id);

        return userDtoMapper.toDto(userInfo);
    }

    @Override
    public List<UserDto> getAllUser() {
        var userList = userPort.getAllUser();
        return userList.stream().map(userDtoMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public UserDto createUser(UserRequest userRequest) {
        var userToCreate = userRequestMapper.toDomain(userRequest);
        var taskCreated = userPort.createUser(userToCreate);

        return userDtoMapper.toDto(taskCreated);
    }

    @Override
    public UserDto updateUser(UserRequest userRequest, Long id) {
        var userInfo = userPort.getById(id);
        var userToUpdate = userRequestMapper.toDomain(userRequest);
        userToUpdate.setId(id);

        var userUpdated = userPort.updateUser(userToUpdate);

        return userDtoMapper.toDto(userUpdated);
    }

    @Override
    public void deleteUserById(Long id) {
        var userInfo = userPort.getById(id);

        userPort.deleteUserById(id);

    }
}
