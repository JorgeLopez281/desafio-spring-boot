package com.tecnova.technical_test.infrastructure.adapter.mapper;

import com.tecnova.technical_test.domain.model.Task;
import com.tecnova.technical_test.infrastructure.adapter.entity.TaskEntity;
import com.tecnova.technical_test.infrastructure.adapter.entity.TaskStatusEntity;
import com.tecnova.technical_test.infrastructure.adapter.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TaskDboMapperTest {

    private final TaskDboMapper mapper = Mappers.getMapper(TaskDboMapper.class);

    @Test
    void shouldMapTaskEntityToDomain() {
        TaskEntity entity = getTaskEntity();

        Task task = mapper.toDomain(entity);

        assertNotNull(task);
        assertEquals(1L, task.getId());
        assertEquals("Do homework", task.getTitle());
        assertEquals("Math homework", task.getDescription());
        assertEquals(LocalDate.of(2025, 7, 20), task.getLimitDate());
        assertEquals(10L, task.getIdUser());
        assertEquals("Carlos", task.getNameUser());
        assertEquals(5L, task.getIdTaskStatus());
        assertEquals("In Progress", task.getDescriptionTaskStatus());
    }

    @Test
    void shouldMapTaskEntityToTask() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setName("Linda");

        TaskStatusEntity taskStatusEntity = new TaskStatusEntity();
        taskStatusEntity.setId(2L);
        taskStatusEntity.setDescription("In progress");

        TaskEntity entity = new TaskEntity();
        entity.setId(100L);
        entity.setTitle("Write tests");
        entity.setDescription("Cover all mappers");
        entity.setLimitDate(LocalDate.of(2025, 7, 11));
        entity.setUserEntity(userEntity);
        entity.setTaskStatusEntity(taskStatusEntity);

        Task task = mapper.toDomain(entity);

        assertNotNull(task);
        assertEquals(100L, task.getId());
        assertEquals("Write tests", task.getTitle());
        assertEquals("Cover all mappers", task.getDescription());
        assertEquals(LocalDate.of(2025, 7, 11), task.getLimitDate());
        assertEquals(1L, task.getIdUser());
        assertEquals("Linda", task.getNameUser());
        assertEquals(2L, task.getIdTaskStatus());
        assertEquals("In progress", task.getDescriptionTaskStatus());
    }

    @Test
    void shouldMapTaskEntityListToTaskList() {
        List<TaskEntity> taskEntity = new ArrayList<>();
        taskEntity.add(new TaskEntity());

        TaskEntity entity1 = new TaskEntity();
        entity1.setId(1L);
        entity1.setTitle("Task 1");
        entity1.setUserEntity(new UserEntity(10L, "John", "john@example.com", null));
        entity1.setTaskStatusEntity(new TaskStatusEntity(5L, "Pending", taskEntity));

        TaskEntity entity2 = new TaskEntity();
        entity2.setId(2L);
        entity2.setTitle("Task 2");
        entity2.setUserEntity(new UserEntity(11L, "Anna", "anna@example.com", null));
        entity2.setTaskStatusEntity(new TaskStatusEntity(6L, "Completed", taskEntity));

        List<Task> result = mapper.taskEntityListToTaskList(List.of(entity1, entity2));

        assertEquals(2, result.size());
        assertEquals("Task 1", result.get(0).getTitle());
        assertEquals("Task 2", result.get(1).getTitle());
        assertEquals("John", result.get(0).getNameUser());
        assertEquals("Anna", result.get(1).getNameUser());
    }

    @Test
    void shouldMapTaskToTaskEntity() {
        Task task = new Task();
        task.setId(100L);
        task.setTitle("New Task");
        task.setDescription("Testing mapping");
        task.setLimitDate(LocalDate.of(2025, 7, 11));
        task.setIdUser(1L);
        task.setNameUser("Linda");
        task.setIdTaskStatus(2L);
        task.setDescriptionTaskStatus("Pending");

        TaskEntity entity = mapper.taskToTaskEntity(task);

        assertNotNull(entity);
        assertEquals(100L, entity.getId());
        assertEquals("New Task", entity.getTitle());
        assertEquals("Testing mapping", entity.getDescription());
        assertEquals(LocalDate.of(2025, 7, 11), entity.getLimitDate());
    }

    @Test
    void shouldMapTaskListToTaskEntityList() {
        Task task1 = new Task();
        task1.setId(1L);
        task1.setTitle("T1");

        Task task2 = new Task();
        task2.setId(2L);
        task2.setTitle("T2");

        List<TaskEntity> result = mapper.taskListToTaskEntityList(List.of(task1, task2));

        assertEquals(2, result.size());
        assertEquals("T1", result.get(0).getTitle());
        assertEquals("T2", result.get(1).getTitle());
    }

    @Test
    void shouldMapTaskToTaskEntity_withObjectNotNull() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Tarea 1");
        task.setDescription("Descripción");
        task.setLimitDate(LocalDate.of(2025, 7, 15));

        TaskEntity result = mapper.taskToTaskEntity(task);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Tarea 1", result.getTitle());
        assertEquals("Descripción", result.getDescription());
        assertEquals(LocalDate.of(2025, 7, 15), result.getLimitDate());
    }

    @Test
    void shouldReturnNullWhenTaskIsNull() {
        TaskEntity result = mapper.taskToTaskEntity(null);
        assertNull(result);
    }

    @Test
    void shouldMapTaskListToTaskEntityListOne() {
        Task task1 = new Task();
        task1.setId(1L);
        task1.setTitle("Task 1");

        Task task2 = new Task();
        task2.setId(2L);
        task2.setTitle("Task 2");

        List<Task> taskList = List.of(task1, task2);
        List<TaskEntity> result = mapper.taskListToTaskEntityList(taskList);

        assertEquals(2, result.size());
        assertEquals("Task 1", result.get(0).getTitle());
        assertEquals("Task 2", result.get(1).getTitle());
    }

    @Test
    void shouldReturnNullWhenTaskListIsNull() {
        List<TaskEntity> result = mapper.taskListToTaskEntityList(null);
        assertNull(result);
    }

    private static TaskEntity getTaskEntity() {
        UserEntity user = new UserEntity();
        user.setId(10L);
        user.setName("Carlos");

        TaskStatusEntity status = new TaskStatusEntity();
        status.setId(5L);
        status.setDescription("In Progress");

        TaskEntity entity = new TaskEntity();
        entity.setId(1L);
        entity.setTitle("Do homework");
        entity.setDescription("Math homework");
        entity.setLimitDate(LocalDate.of(2025, 7, 20));
        entity.setUserEntity(user);
        entity.setTaskStatusEntity(status);
        return entity;
    }
}
