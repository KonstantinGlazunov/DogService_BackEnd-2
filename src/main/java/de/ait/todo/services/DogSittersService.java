package de.ait.todo.services;


import de.ait.todo.dto.DogSitterDto;
import de.ait.todo.models.DogSitter;

import java.util.List;

public interface DogSittersService {


    List<DogSitterDto> getDogSitters();

    DogSitterDto getDogSitterById (Long id);

    List<DogSitterDto> getDogSittersByCityAndDogSize(String city, DogSitter.DogSize dogSize, String zip);

    DogSitterDto deleteDogSitter(Long id);

}

