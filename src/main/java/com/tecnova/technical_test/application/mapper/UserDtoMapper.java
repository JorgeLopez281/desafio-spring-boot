package com.tecnova.technical_test.application.mapper;

import com.tecnova.technical_test.domain.model.User;
import com.tecnova.technical_test.domain.model.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring") 
public interface UserDtoMapper {

    UserDto toDto(User domain);
}
