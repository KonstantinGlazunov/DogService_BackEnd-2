package de.ait.todo.services;


import de.ait.todo.dto.DogSitterDto;
import de.ait.todo.dto.DogSitterToDogLoverDto;
import de.ait.todo.dto.NewDogLoverDto;
import de.ait.todo.dto.DogLoverDto;
import de.ait.todo.models.User;

import java.util.List;

public interface SignUpDogLoverService {
    DogLoverDto registerDogLover(NewDogLoverDto newUser);
    User saveUser(NewDogLoverDto newUser);

    List<DogSitterDto> addDogSitterToDogLover(Long dogLoverId, DogSitterToDogLoverDto dogSitterData);

    List<DogSitterDto> getDogSittersOfDogLover(Long dogLoverId);
}
