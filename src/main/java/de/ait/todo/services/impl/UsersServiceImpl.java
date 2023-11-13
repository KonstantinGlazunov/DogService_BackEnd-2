package de.ait.todo.services.impl;

import de.ait.todo.dto.ProfileDto;
import de.ait.todo.dto.TasksPage;
import de.ait.todo.models.Task;
import de.ait.todo.models.User;
import de.ait.todo.repositories.TasksRepository;
import de.ait.todo.repositories.UsersRepository;
import de.ait.todo.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static de.ait.todo.dto.TaskDto.from;

/**
 * 6/13/2023
 * spring-security-demo
 *
 * @author Marsel Sidikov (AIT TR)
 */
@RequiredArgsConstructor
@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final TasksRepository tasksRepository;

    @Override
    public ProfileDto getProfile(Long currentUserId) {
        User user = usersRepository.findById(currentUserId)
                .orElseThrow(IllegalArgumentException::new);

        return ProfileDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .userName(user.getUserName())
                .role(user.getRole().name())
                .build();
    }

    @Override
    public TasksPage getTasksByUser(Long currentUserId) {
        List<Task> tasks = tasksRepository.findAllByUser_Id(currentUserId);

        return TasksPage.builder()
                .tasks(from(tasks))
                .build();

    }

}
