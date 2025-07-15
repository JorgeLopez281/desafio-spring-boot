package com.tecnova.technical_test.infrastructure.rest.controller;

import com.tecnova.technical_test.application.usecase.ITaskService;
import com.tecnova.technical_test.domain.model.dto.TaskDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TaskControllerTest {

    @Mock
    private ITaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getByIdTask_ShouldReturnUser() {
        Long taskId = 1L;
        LocalDate limitDate = LocalDate.of(2023, 5, 15);
        TaskDto taskDto = new TaskDto(
              taskId,
              "Task Example",
              "Task Description",
                limitDate,
              1L,
              "jlopez",
              1L,
                "2"
        );

        Mockito.when(taskService.getTaskById(taskId)).thenReturn(taskDto);

        ResponseEntity<TaskDto> response = taskController.getByIdUser(taskId);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(taskDto, response.getBody());
        verify(taskService, times(1)).getTaskById(taskId);
    }
}
