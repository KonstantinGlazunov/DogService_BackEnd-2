package de.ait.todo.services;


import de.ait.todo.dto.DogSitterDto;
import de.ait.todo.models.DogSitter;

import java.util.List;

public interface DogSittersService {
    DogSitter getDogSitter(Long sitterId);

    List<DogSitterDto> getDogSitters();

    List<DogSitterDto> getDogSittersByCity (String city);

}
