package com.tecnova.technical_test.application.usecase;

import com.tecnova.technical_test.domain.model.dto.UserDto;
import com.tecnova.technical_test.domain.model.dto.request.UserRequest;
import org.mapstruct.control.MappingControl;

import java.util.List;

public interface IUserService {

    UserDto getById(Long id);
    List<UserDto> getAllUser();
    UserDto createUser(UserRequest userRequest);
    UserDto updateUser(UserRequest userRequest, Long id);
    void deleteUserById(Long id);
}
