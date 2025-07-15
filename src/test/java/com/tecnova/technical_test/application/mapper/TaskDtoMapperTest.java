package com.tecnova.technical_test.application.mapper;

import com.tecnova.technical_test.domain.model.Task;
import com.tecnova.technical_test.domain.model.dto.TaskDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TaskDtoMapperTest {

    private final TaskDtoMapper taskDtoMapper = Mappers.getMapper(TaskDtoMapper.class);

    @Test
    void toDto_shouldMapAllFieldsCorrectly() {
        Task domainTask = new Task();
        domainTask.setId(1L);
        domainTask.setTitle("Buy groceries");
        domainTask.setDescription("Milk, eggs, bread, cheese");
        domainTask.setLimitDate(LocalDate.of(2025, 7, 20));
        domainTask.setIdUser(101L);
        domainTask.setNameUser("John Doe");
        domainTask.setIdTaskStatus(1L);
        domainTask.setDescriptionTaskStatus("Pending");

        TaskDto taskDto = taskDtoMapper.toDto(domainTask);

        assertNotNull(taskDto,
                "El DTO mapeado no debería ser nulo");
        assertEquals(domainTask.getId(), taskDto.getId(),
                "El ID de la tarea debería coincidir");
        assertEquals(domainTask.getTitle(), taskDto.getTitle(),
                "El título de la tarea debería coincidir");
        assertEquals(domainTask.getDescription(), taskDto.getDescription(),
                "La descripción de la tarea debería coincidir");
        assertEquals(domainTask.getLimitDate(), taskDto.getLimitDate(),
                "La fecha límite de la tarea debería coincidir");
        assertEquals(domainTask.getIdUser(), taskDto.getIdUser(),
                "El ID de usuario debería coincidir");
        assertEquals(domainTask.getNameUser(), taskDto.getNameUser(),
                "El nombre de usuario debería coincidir");
        assertEquals(domainTask.getIdTaskStatus(), taskDto.getIdTaskStatus(),
                "El ID del estado de la tarea debería coincidir");
        assertEquals(domainTask.getDescriptionTaskStatus(), taskDto.getDescriptionTaskStatus(),
                "La descripción del estado de la tarea debería coincidir");
    }

    @Test
    void toDto_shouldHandleNullDomainObject() {
        Task domainTask = null;

        TaskDto taskDto = taskDtoMapper.toDto(domainTask);

        assertEquals(null, taskDto,
                "Mapear un objeto de dominio nulo debería resultar en un DTO nulo");
    }
}
