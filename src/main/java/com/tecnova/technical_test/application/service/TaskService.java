package com.tecnova.technical_test.application.service;

import com.tecnova.technical_test.application.mapper.TaskDtoMapper;
import com.tecnova.technical_test.application.mapper.UserDtoMapper;
import com.tecnova.technical_test.application.usecase.ITaskService;
import com.tecnova.technical_test.application.usecase.IUserService;
import com.tecnova.technical_test.domain.model.dto.TaskDto;
import com.tecnova.technical_test.domain.model.dto.UserDto;
import com.tecnova.technical_test.domain.port.ITaskPort;
import com.tecnova.technical_test.domain.port.IUserPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService implements ITaskService {

    private final ITaskPort taskPort;
    private final TaskDtoMapper taskDtoMapper;

    @Autowired
    public TaskService(final ITaskPort taskPort,
                       final TaskDtoMapper taskDtoMapper) {
        this.taskPort = taskPort;
        this.taskDtoMapper = taskDtoMapper;
    }

    @Override
    public TaskDto getTaskById(Long id) {
        var taskInfo = taskPort.getTaskById(id);

        return taskDtoMapper.toDto(taskInfo);
    }
}
