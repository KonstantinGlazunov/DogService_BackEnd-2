package de.ait.todo.services;

import de.ait.todo.dto.NewTaskDto;
import de.ait.todo.dto.TaskDto;
import de.ait.todo.dto.TasksPage;
import de.ait.todo.security.details.AuthenticatedUser;

/**
 * 6/11/2023
 * backend-demo
 *
 * @author Marsel Sidikov (AIT TR)
 */
public interface TasksService {
    TasksPage getAll();

    TaskDto getById(Long taskId);

    void deleteTask(Long taskId);

    TaskDto addTask(Long currentUserId, NewTaskDto task);
}
