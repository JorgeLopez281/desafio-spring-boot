package com.tecnova.technical_test.infrastructure.adapter;

import com.tecnova.technical_test.domain.model.User;
import com.tecnova.technical_test.domain.model.constant.UserConstant;
import com.tecnova.technical_test.domain.port.IUserPort;
import com.tecnova.technical_test.infrastructure.adapter.exceptions.UserException;
import com.tecnova.technical_test.infrastructure.adapter.exceptions.UsersException;
import com.tecnova.technical_test.infrastructure.adapter.mapper.UserDboMapper;
import com.tecnova.technical_test.infrastructure.adapter.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class UserSpringJpaAdapter implements IUserPort {

    private final IUserRepository userRepository;
    private final UserDboMapper userDboMapper;

    @Autowired
    public UserSpringJpaAdapter(IUserRepository userRepository, UserDboMapper userDboMapper) {
        this.userRepository = userRepository;
        this.userDboMapper = userDboMapper;
    }

    @Override
    public User getById(Long id) {
        //Esto devuelve un objeto de Infraestructura
        var optionalUserInfo = userRepository.findById(id);

        if (optionalUserInfo.isEmpty()) {
            throw new UserException(HttpStatus.NOT_FOUND,
                    String.format(UserConstant.USER_NOT_FOUND_MESSAGE_ERROR, id));
        }

        //Se convierte el objeto de Infraestructura a Dominio
        return userDboMapper.toDomain(optionalUserInfo.get());
    }

    @Override
    public List<User> getAllUser() {
        var usersList = userRepository.findAll();

        if (usersList.isEmpty()){
            throw  new UsersException(HttpStatus.NOT_FOUND,
                    String.format(UserConstant.USERS_NOT_FOUND_MESSAGE_ERROR));
        }

        return usersList.stream().map(userDboMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public User createUser(User userRequest) {
        var userToSave = userDboMapper.toDbo(userRequest);

        var userSaved = userRepository.save(userToSave);

        return userDboMapper.toDomain(userSaved);
    }

    @Override
    public User updateUser(User userRequest) {
        var userToUpdate = userDboMapper.toDbo(userRequest);
        var userUpdated = userRepository.save(userToUpdate);

        return userDboMapper.toDomain(userUpdated);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
