package de.ait.todo.services;

import de.ait.todo.dto.NewDogLoverDto;
import de.ait.todo.dto.NewDogSitterDto;
import de.ait.todo.dto.DogSitterDto;


public interface SignUpSitterService {
    DogSitterDto signUp(NewDogSitterDto newSetter);
    void signUpp(NewDogSitterDto newSetter);
}
