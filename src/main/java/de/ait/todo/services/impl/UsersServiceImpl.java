package de.ait.todo.services.impl;

import de.ait.todo.dto.ProfileDto;
import de.ait.todo.dto.TasksPage;
import de.ait.todo.exceptions.RestException;
import de.ait.todo.models.ConfirmationCode;
import de.ait.todo.models.Task;
import de.ait.todo.models.User;
import de.ait.todo.repositories.ConfirmationCodesRepository;
import de.ait.todo.repositories.TasksRepository;
import de.ait.todo.repositories.UsersRepository;
import de.ait.todo.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static de.ait.todo.dto.TaskDto.from;

import static de.ait.todo.dto.ProfileDto.from;

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
    private final ConfirmationCodesRepository confirmationCodesRepository;
    private final TasksRepository tasksRepository;

    @Override
    @Transactional
    public ProfileDto confirm(String confirmCode) {
        ConfirmationCode code = confirmationCodesRepository
                .findByCodeAndExpiredDateTimeAfter(confirmCode, LocalDateTime.now())
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Code not found or is expired"));

        User user = usersRepository
                .findFirstByCodesContains(code)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "User by code not found"));

        user.setState(User.State.CONFIRMED);

        usersRepository.save(user);

        return ProfileDto.from(user);
    }


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
