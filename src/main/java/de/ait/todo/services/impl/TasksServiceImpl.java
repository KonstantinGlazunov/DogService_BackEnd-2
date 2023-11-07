package de.ait.todo.services.impl;

import de.ait.todo.dto.NewTaskDto;
import de.ait.todo.dto.TaskDto;
import de.ait.todo.dto.TasksPage;
import de.ait.todo.exceptions.RestException;
import de.ait.todo.models.Task;
import de.ait.todo.models.User;
import de.ait.todo.repositories.TasksRepository;
import de.ait.todo.repositories.UsersRepository;
import de.ait.todo.services.TasksService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static de.ait.todo.dto.TaskDto.from;

/**
 * 6/11/2023
 * backend-demo
 *
 * @author Marsel Sidikov (AIT TR)
 */
@RequiredArgsConstructor
@Service
public class TasksServiceImpl implements TasksService {

    private final TasksRepository tasksRepository;

    private final UsersRepository usersRepository;

    @Override
    public TasksPage getAll() {
        return TasksPage.builder()
                .tasks(from(tasksRepository.findAll()))
                .build();
    }

    @Override
    public TaskDto getById(Long taskId) {
        Task task = tasksRepository.findById(taskId).orElseThrow(
                () -> new RestException(HttpStatus.NOT_FOUND, "Задача <" + taskId + "> не найдена"));

        return from(task);
    }

    @Override
    public void deleteTask(Long taskId) {
        if (tasksRepository.existsById(taskId)) {
            tasksRepository.deleteById(taskId);
        } else {
            throw new RestException(HttpStatus.NOT_FOUND, "Задача <" + taskId + "> не найдена");
        }
    }

    @Override
    public TaskDto addTask(Long currentUserId, NewTaskDto task) {
        User user = usersRepository.findById(currentUserId)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Пользователь <" + currentUserId + "> не найден"));

        Task newTask = Task.builder()
                .name(task.getName())
                .description(task.getDescription())
                .user(user)
                .build();

        tasksRepository.save(newTask);

        return from(newTask);
    }
}
