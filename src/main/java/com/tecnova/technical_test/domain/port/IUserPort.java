package com.tecnova.technical_test.domain.port;

import com.tecnova.technical_test.domain.model.User;

import java.util.List;

public interface IUserPort {

    User getById(Long id);
    List<User> getAllUser();
    User createUser(User userRequest);
    User updateUser(User userRequest);
    void deleteUserById(Long id);


}
