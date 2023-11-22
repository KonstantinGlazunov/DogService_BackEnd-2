package de.ait.todo.services;


import de.ait.todo.dto.ClinicDto;
import de.ait.todo.dto.ProfileDto;
import de.ait.todo.dto.TasksPage;
import de.ait.todo.dto.UserDto;
import de.ait.todo.models.User;

import java.util.List;

public interface UsersService {

    ProfileDto getProfile(Long currentUserId);

//    TasksPage getTasksByUser(Long currentUserId);

    ProfileDto confirm(String confirmCode);

    User getByEmailOrThrow(String email);

    List<UserDto> getUsers();

    UserDto deleteUser(Long userId);

    UserDto getUserByID(Long userId);

}
