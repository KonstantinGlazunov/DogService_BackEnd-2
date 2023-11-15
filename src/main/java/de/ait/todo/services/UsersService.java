package de.ait.todo.services;


import de.ait.todo.dto.ProfileDto;
import de.ait.todo.dto.TasksPage;
import de.ait.todo.models.User;

public interface UsersService {

    ProfileDto getProfile(Long currentUserId);

    TasksPage getTasksByUser(Long currentUserId);

    ProfileDto confirm(String confirmCode);

}
