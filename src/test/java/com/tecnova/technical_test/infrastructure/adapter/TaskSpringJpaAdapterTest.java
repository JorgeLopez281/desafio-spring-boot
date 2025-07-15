package com.tecnova.technical_test.infrastructure.adapter;

import com.tecnova.technical_test.domain.model.User;
import com.tecnova.technical_test.infrastructure.adapter.entity.UserEntity;
import com.tecnova.technical_test.infrastructure.adapter.exceptions.UserException;
import com.tecnova.technical_test.infrastructure.adapter.exceptions.UsersException;
import com.tecnova.technical_test.infrastructure.adapter.mapper.UserDboMapper;
import com.tecnova.technical_test.infrastructure.adapter.repository.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class TaskSpringJpaAdapterTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private UserDboMapper userDboMapper;

    @InjectMocks
    private UserSpringJpaAdapter userAdapter;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    void getById_whenUserExists_shouldReturnUser() {
        Long id = 1L;
        UserEntity user = new UserEntity();
        User domainUser = new User();

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userDboMapper.toDomain(user)).thenReturn(domainUser);

        User result = userAdapter.getById(id);

        assertNotNull(result);
        verify(userRepository).findById(id);
        verify(userDboMapper).toDomain(user);
    }

    @Test
    void getById_whenUserNotFound_shouldThrowException() {
        Long id = 2L;

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        UserException exception = assertThrows(UserException.class, () ->
                userAdapter.getById(id));
        assertTrue(exception.getErrorCode().is4xxClientError());

        verify(userRepository).findById(id);
    }

    @Test
    void getAllUser_whenUsersExist_shouldReturnList() {
        UserEntity userEntityInt = new UserEntity();
        User userInt = new User();

        List<UserEntity> userEntityList = new ArrayList<>();
        userEntityList.add(userEntityInt);

        List<User> userList = new ArrayList<>();
        userList.add(userInt);

        when(userRepository.findAll()).thenReturn(userEntityList);
        when(userDboMapper.toDomain(userEntityList.get(0))).thenReturn(userList.get(0));

        List<User> result = userAdapter.getAllUser();

        assertEquals(1, result.size());
        verify(userRepository).findAll();
        verify(userDboMapper).toDomain(userEntityList.get(0));
    }

    @Test
    void getAllUser_whenNoUsers_shouldThrowException() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(UsersException.class, () -> userAdapter.getAllUser());
        verify(userRepository).findAll();
    }

    @Test
    void createUser_shouldSaveAndReturnUser() {
        User user = new User();
        UserEntity entity = new UserEntity();

        when(userDboMapper.toDbo(user)).thenReturn(entity);
        when(userRepository.save(entity)).thenReturn(entity);
        when(userDboMapper.toDomain(entity)).thenReturn(user);

        User result = userAdapter.createUser(user);

        assertNotNull(result);
        verify(userDboMapper).toDbo(user);
        verify(userRepository).save(entity);
        verify(userDboMapper).toDomain(entity);
    }

    @Test
    void updateUser_shouldUpdateAndReturnUser() {
        User user = new User();
        UserEntity entity = new UserEntity();

        when(userDboMapper.toDbo(user)).thenReturn(entity);
        when(userRepository.save(entity)).thenReturn(entity);
        when(userDboMapper.toDomain(entity)).thenReturn(user);

        User result = userAdapter.updateUser(user);

        assertNotNull(result);
        verify(userDboMapper).toDbo(user);
        verify(userRepository).save(entity);
        verify(userDboMapper).toDomain(entity);
    }

    @Test
    void deleteUserById_shouldCallRepositoryDelete() {
        Long id = 3L;

        userAdapter.deleteUserById(id);

        verify(userRepository).deleteById(id);
    }
}
