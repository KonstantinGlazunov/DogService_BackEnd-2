package de.ait.todo.services.impl;

import de.ait.todo.dto.DogSitterDto;
import de.ait.todo.exceptions.RestException;
import de.ait.todo.models.DogSitter;
import de.ait.todo.repositories.DogSittersRepository;
import de.ait.todo.services.DogSittersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 02.11.2023
 * DogsServicesBackend
 *
 * @author Konstantin Glazunov (AIT TR)
 */
@RequiredArgsConstructor
@Service
public class DogSittersServiceImpl implements DogSittersService {
    private final DogSittersRepository dogSittersRepository;
    @Override
    public DogSitter getDogSitter(Long sitterId) {
        return null;
    }

    @Override
    public List<DogSitterDto> getDogSitters() {
        List<DogSitter> dogSitters = dogSittersRepository.findAll();
        if (!dogSitters.isEmpty()) {
            return DogSitterDto.from(dogSitters);
        }else {
            throw new RestException(HttpStatus.NOT_FOUND, "Dog Sitters not found");
        }
    }

    @Override
    public List<DogSitterDto> getDogSittersByCityAndDogSize(String city, DogSitter.DogSize dogSize, String zip) {
        List<DogSitter> dogSitters = dogSittersRepository.findDogSittersByCityAndDogSizeAndZip (city, dogSize, zip);
        return DogSitterDto.from(dogSitters);

    }



}
