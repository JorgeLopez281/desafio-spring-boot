package com.tecnova.technical_test.infrastructure.adapter.mapper;

import com.tecnova.technical_test.domain.model.User;
import com.tecnova.technical_test.infrastructure.adapter.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserDboMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    UserEntity toDbo(User domain);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(target = "taskList", ignore = true)
    User toDomain(UserEntity entity);
}