package com.tecnova.technical_test.application.service;

import com.tecnova.technical_test.application.mapper.TaskDtoMapper;
import com.tecnova.technical_test.domain.model.Task;
import com.tecnova.technical_test.domain.model.dto.TaskDto;
import com.tecnova.technical_test.domain.port.ITaskPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TaskServiceTest {

    @Mock
    private ITaskPort taskPort;

    @Mock
    private TaskDtoMapper taskDtoMapper;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getTaskById_ReturnsTaskDto() {
        Long taskId = 1L;

        Task domainTask = new Task();
        domainTask.setId(taskId);
        domainTask.setTitle("Task title");

        TaskDto expectedDto = new TaskDto();
        expectedDto.setId(taskId);
        expectedDto.setTitle("Task title");

        when(taskPort.getTaskById(taskId)).thenReturn(domainTask);
        when(taskDtoMapper.toDto(domainTask)).thenReturn(expectedDto);

        TaskDto result = taskService.getTaskById(taskId);

        assertNotNull(result);
        assertEquals(expectedDto.getId(), result.getId());
        assertEquals(expectedDto.getTitle(), result.getTitle());

        verify(taskPort, times(1)).getTaskById(taskId);
        verify(taskDtoMapper, times(1)).toDto(domainTask);
    }
}
