package de.ait.todo.services;

import de.ait.todo.dto.NewDogLoverDto;
import de.ait.todo.dto.NewDogSitterDto;
import de.ait.todo.dto.DogSitterDto;
import de.ait.todo.models.User;


public interface SignUpSitterService {
    DogSitterDto RegisterDogSitter(NewDogSitterDto newSetter);
    User saveUser(NewDogSitterDto newSitter);
}
