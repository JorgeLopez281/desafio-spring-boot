package com.tecnova.technical_test.infrastructure.adapter;

import com.tecnova.technical_test.domain.model.Task;
import com.tecnova.technical_test.infrastructure.adapter.entity.TaskEntity;
import com.tecnova.technical_test.infrastructure.adapter.exceptions.TaskException;
import com.tecnova.technical_test.infrastructure.adapter.mapper.TaskDboMapper;
import com.tecnova.technical_test.infrastructure.adapter.repository.ITaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserSpringJpaAdapterTest {

    @Mock
    private ITaskRepository taskRepository;

    @Mock
    private TaskDboMapper taskDboMapper;

    @InjectMocks
    private TaskSpringJpaAdapter taskAdapter;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    void getById_whenUserExists_shouldReturnUser() {
        Long id = 1L;
        TaskEntity task = new TaskEntity();
        Task domainTask = new Task();

        when(taskRepository.findById(id)).thenReturn(Optional.of(task));
        when(taskDboMapper.toDomain(task)).thenReturn(domainTask);

        Task result = taskAdapter.getTaskById(id);

        assertNotNull(result);
        verify(taskRepository).findById(id);
        verify(taskDboMapper).toDomain(task);
    }

    @Test
    void getById_whenUserNotFound_shouldThrowException() {
        Long id = 2L;

        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        TaskException exception = assertThrows(TaskException.class, () ->
                taskAdapter.getTaskById(id));
        assertTrue(exception.getErrorCode().is4xxClientError());

        verify(taskRepository).findById(id);
    }
}
