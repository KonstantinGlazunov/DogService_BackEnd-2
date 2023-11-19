package de.ait.todo.services.impl;

import de.ait.todo.dto.ClinicDto;
import de.ait.todo.dto.ProfileDto;
import de.ait.todo.dto.TasksPage;
import de.ait.todo.dto.UserDto;
import de.ait.todo.exceptions.RestException;
import de.ait.todo.models.*;
import de.ait.todo.repositories.*;
import de.ait.todo.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static de.ait.todo.dto.TaskDto.from;

import static de.ait.todo.dto.ProfileDto.from;

import static de.ait.todo.dto.UserDto.from;


@RequiredArgsConstructor
@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final ConfirmationCodesRepository confirmationCodesRepository;
    //private final TasksRepository tasksRepository;
    private final DogSittersRepository sittersRepository;
    private final DogLoverRepository loverRepository;

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

//    @Override
//    public TasksPage getTasksByUser(Long currentUserId) {
//        List<Task> tasks = tasksRepository.findAllByUser_Id(currentUserId);
//
//        return TasksPage.builder()
//                .tasks(from(tasks))
//                .build();
//
//    }

    public User getByEmailOrThrow(String email){
        return usersRepository.findByEmail(email)
                .orElseThrow(()-> new RestException(HttpStatus.NOT_FOUND, "User with email <" + email + "> not found"));
    }

    @Override
    public List<UserDto> getUsers() {
        List<User> users = usersRepository.findAll();
        return UserDto.from(users);
    }

    @Override
    public UserDto deleteUser(Long userId) {
        User user = getUserOrThrow(userId);
        DogSitter dogSitter = getDogSitterOrThrow(userId);
        DogLover dogLover = getDogLoverOrThrow(userId);
        usersRepository.delete(user);
        //loverRepository.delete(dogLover);
        sittersRepository.delete(dogSitter);
        return UserDto.from(user);
    }

    private User getUserOrThrow(Long userId) {
        return usersRepository.findById(userId)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Course with id <" + userId + "> not found"));
    }

    private DogLover getDogLoverOrThrow(Long dogLoverId) {
        return loverRepository.findById(dogLoverId)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Course with id <" + dogLoverId + "> not found"));
    }

    private DogSitter getDogSitterOrThrow(Long dogSitterId) {
        return sittersRepository.findById(dogSitterId)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Course with id <" + dogSitterId + "> not found"));
    }

}
