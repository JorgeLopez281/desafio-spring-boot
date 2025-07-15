package com.tecnova.technical_test.infrastructure.rest.controller;

import com.tecnova.technical_test.application.usecase.ITaskService;
import com.tecnova.technical_test.application.usecase.IUserService;
import com.tecnova.technical_test.domain.model.dto.TaskDto;
import com.tecnova.technical_test.domain.model.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/task")
@Tag(name = "Task Controller", description = "Controller to management all operation related with Task")
public class TaskController {

    private final ITaskService taskService;

    @Autowired
    public TaskController( ITaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get One Task By Id",
            description = "Returns an existing Task in DB by Id")
    public ResponseEntity<TaskDto> getByIdUser(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }
}
