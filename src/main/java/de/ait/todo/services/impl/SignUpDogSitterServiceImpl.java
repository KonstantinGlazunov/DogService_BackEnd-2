package de.ait.todo.services.impl;

import de.ait.todo.dto.NewDogLoverDto;
import de.ait.todo.dto.NewDogSitterDto;
import de.ait.todo.dto.DogSitterDto;
import de.ait.todo.dto.UserDto;
import de.ait.todo.models.DogSitter;
import de.ait.todo.models.User;
import de.ait.todo.repositories.DogLoverRepository;
import de.ait.todo.repositories.DogSittersRepository;
import de.ait.todo.repositories.UsersRepository;
import de.ait.todo.services.SignUpSitterService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import static de.ait.todo.dto.DogSitterDto.from;



@RequiredArgsConstructor
@Service
public class SignUpDogSitterServiceImpl implements SignUpSitterService {

    private final DogSittersRepository sittersRepository;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public DogSitterDto signUp(NewDogSitterDto newSitter) {
        DogSitter dogSitter = DogSitter.builder()
                .firstName(newSitter.getFirstName())
                .lastName(newSitter.getLastName())
                .email(newSitter.getEmail())
                .userName(newSitter.getUserName())
                .city(newSitter.getCity())
                .zip(newSitter.getZip())
                .role(DogSitter.Role.DOGSITTER)
                .build();

        sittersRepository.save(dogSitter);

        return from(dogSitter);
    }



    public void signUpp(NewDogSitterDto newSitter) {
        User user = User.builder()
                .email(newSitter.getEmail())
                .hashPassword(passwordEncoder.encode(newSitter.getPassword()))
                .role(User.Role.DOGSITTER)
                .userName(newSitter.getUserName())
                .state(User.State.NOT_CONFIRMED)
                .build();

        usersRepository.save(user);

    }


}
