package com.tecnova.technical_test.infrastructure.adapter.mapper;

import com.tecnova.technical_test.domain.model.Task;
import com.tecnova.technical_test.domain.model.User;
import com.tecnova.technical_test.infrastructure.adapter.entity.TaskEntity;
import com.tecnova.technical_test.infrastructure.adapter.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserDboMapperTest {

    private final UserDboMapper mapper = Mappers.getMapper(UserDboMapper.class);

    @Test
    void shouldMapDomainToDbo() {
        User user = new User();
        user.setName("Linda");
        user.setEmail("linda@example.com");

        UserEntity entity = mapper.toDbo(user);

        assertNotNull(entity);
        assertEquals("Linda", entity.getName());
        assertEquals("linda@example.com", entity.getEmail());
    }

    @Test
    void shouldMapDomainToDbo_WithTaskListNotNull() {
        User user = new User();
        user.setName("Linda");
        user.setEmail("linda@example.com");

        Task task = new Task();
        List<Task> taskList = new ArrayList<>();
        taskList.add(task);

        user.setTaskList(taskList);

        UserEntity entity = mapper.toDbo(user);

        assertNotNull(entity);
        assertEquals("Linda", entity.getName());
        assertEquals("linda@example.com", entity.getEmail());
    }

    @Test
    void shouldMapDboToDomain() {
        UserEntity entity = new UserEntity();
        entity.setId(1L);
        entity.setName("Pedro");
        entity.setEmail("pedro@example.com");

        User user = mapper.toDomain(entity);

        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("Pedro", user.getName());
        assertEquals("pedro@example.com", user.getEmail());
        assertNull(user.getTaskList()); // Porque se ignora el mapeo de taskList
    }

    @Test
    void shouldMapDboToDomain_WithTaskListNotNull() {
        UserEntity entity = new UserEntity();
        entity.setId(1L);
        entity.setName("Pedro");
        entity.setEmail("pedro@example.com");

        TaskEntity task = new TaskEntity();
        List<TaskEntity> taskList = new ArrayList<>();
        taskList.add(task);

        entity.setTaskList(taskList);

        User user = mapper.toDomain(entity);

        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("Pedro", user.getName());
        assertEquals("pedro@example.com", user.getEmail());
        assertNull(user.getTaskList());
    }
}
