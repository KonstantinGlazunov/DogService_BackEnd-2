package de.ait.todo.services;


import de.ait.todo.dto.NewDogLoverDto;
import de.ait.todo.dto.DogLoverDto;
import de.ait.todo.models.User;

public interface SignUpDogLoverService {
    DogLoverDto registerDogLover(NewDogLoverDto newUser);
    User saveUser(NewDogLoverDto newUser);
}
