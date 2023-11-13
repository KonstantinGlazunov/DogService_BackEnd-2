package de.ait.todo.services;


import de.ait.todo.dto.NewDogLoverDto;
import de.ait.todo.dto.DogLoverDto;

public interface SignUpDogLoverService {
    DogLoverDto signUp(NewDogLoverDto newUser);
    void signUpp(NewDogLoverDto newUser);
}
