package com.tecnova.technical_test.application.mapper;

import com.tecnova.technical_test.domain.model.User;
import com.tecnova.technical_test.domain.model.dto.request.UserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring") 
public interface UserRequestMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    User toDomain(UserRequest userRequest);
}
