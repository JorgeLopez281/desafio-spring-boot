package com.tecnova.technical_test.infrastructure.adapter;

import com.tecnova.technical_test.domain.model.Task;
import com.tecnova.technical_test.domain.model.User;
import com.tecnova.technical_test.domain.model.constant.TaskConstant;
import com.tecnova.technical_test.domain.model.constant.UserConstant;
import com.tecnova.technical_test.domain.port.ITaskPort;
import com.tecnova.technical_test.domain.port.IUserPort;
import com.tecnova.technical_test.infrastructure.adapter.exceptions.TaskException;
import com.tecnova.technical_test.infrastructure.adapter.exceptions.UserException;
import com.tecnova.technical_test.infrastructure.adapter.mapper.TaskDboMapper;
import com.tecnova.technical_test.infrastructure.adapter.mapper.UserDboMapper;
import com.tecnova.technical_test.infrastructure.adapter.repository.ITaskRepository;
import com.tecnova.technical_test.infrastructure.adapter.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class TaskSpringJpaAdapter implements ITaskPort {

    private final ITaskRepository taskRepository;
    private final TaskDboMapper taskDboMapper;

    @Autowired
    public TaskSpringJpaAdapter(ITaskRepository taskRepository, TaskDboMapper taskDboMapper) {
        this.taskRepository = taskRepository;
        this.taskDboMapper = taskDboMapper;
    }

    @Override
    public Task getTaskById(Long id) {
        //Esto devuelve un objeto de Infraestructura
        var optionalUserInfo = taskRepository.findById(id);

        if (optionalUserInfo.isEmpty()) {
            throw new TaskException(HttpStatus.NOT_FOUND,
                    String.format(TaskConstant.TASK_NOT_FOUND_MESSAGE_ERROR, id));
        }

        //Se convierte el objeto de Infraestructura a Dominio
        return taskDboMapper.toDomain(optionalUserInfo.get());
    }
}
