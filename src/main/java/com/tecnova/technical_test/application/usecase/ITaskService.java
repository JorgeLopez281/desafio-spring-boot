package com.tecnova.technical_test.application.usecase;

import com.tecnova.technical_test.domain.model.dto.TaskDto;
import com.tecnova.technical_test.domain.model.dto.UserDto;

public interface ITaskService {

    TaskDto getTaskById(Long id);
}
