package com.tecnova.technical_test.domain.port;

import com.tecnova.technical_test.domain.model.Task;
import com.tecnova.technical_test.domain.model.User;

public interface ITaskPort {

    Task getTaskById(Long id);

}
