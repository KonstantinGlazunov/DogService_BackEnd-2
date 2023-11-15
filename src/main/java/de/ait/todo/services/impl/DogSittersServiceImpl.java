package de.ait.todo.services.impl;

import de.ait.todo.dto.DogSitterDto;
import de.ait.todo.exceptions.RestException;
import de.ait.todo.models.DogSitter;
import de.ait.todo.models.User;
import de.ait.todo.repositories.DogSittersRepository;
import de.ait.todo.repositories.UsersRepository;
import de.ait.todo.services.DogSittersService;
import de.ait.todo.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    private final UsersRepository usersRepository;
    private final UsersService usersService;
    @Override
    public DogSitterDto getDogSitter(Long id) {
       DogSitter dogSitter = getDogSitterOrThrow(id);
            return DogSitterDto.from(dogSitter);
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

    @Override
    public DogSitterDto deleteDogSitter(Long id) {
        DogSitter dogSitter = getDogSitterOrThrow(id);
        String dogSitterEmail =  dogSitter.getEmail();
        User user = usersService.getByEmailOrThrow(dogSitterEmail);
        dogSittersRepository.delete(dogSitter);
        usersRepository.delete(user);
        return DogSitterDto.from(dogSitter);
    }

    private DogSitter getDogSitterOrThrow(Long id){
        return dogSittersRepository.findById(id)
                .orElseThrow(()-> new RestException(HttpStatus.NOT_FOUND, "Dog Sitter with id <" + id + "> not found"));
}

}
