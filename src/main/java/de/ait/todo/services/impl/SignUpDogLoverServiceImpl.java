package de.ait.todo.services.impl;

import de.ait.todo.dto.NewDogLoverDto;
import de.ait.todo.dto.DogLoverDto;
import de.ait.todo.dto.NewUserDto;
import de.ait.todo.dto.UserDto;
import de.ait.todo.models.DogLover;
import de.ait.todo.models.User;
import de.ait.todo.repositories.DogLoverRepository;
import de.ait.todo.repositories.UsersRepository;
import de.ait.todo.services.SignUpDogLoverService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import static de.ait.todo.dto.DogLoverDto.from;

/**
 * 6/12/2023
 * spring-security-demo
 *
 * @author Marsel Sidikov (AIT TR)
 */
@RequiredArgsConstructor
@Service
public class SignUpDogLoverServiceImpl implements SignUpDogLoverService {

    private final DogLoverRepository loverRepository;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public DogLoverDto signUp(NewDogLoverDto newDogLover) {
        DogLover dogLover = DogLover.builder()
                .firstName(newDogLover.getFirstName())
                .lastName(newDogLover.getLastName())
                .userName(newDogLover.getUserName())
                .city(newDogLover.getCity())
                .zip(newDogLover.getZip())
                .email(newDogLover.getEmail())
                .role(DogLover.Role.DOGLOVER)
                .build();

        loverRepository.save(dogLover);


        return from(dogLover);

  }
    @Override
    public void signUpp(NewDogLoverDto newDogLover) {
        User user = User.builder()
                .email(newDogLover.getEmail())
                .hashPassword(passwordEncoder.encode(newDogLover.getPassword()))
                .role(User.Role.DOGLOVER)
                .userName(newDogLover.getUserName())
                .state(User.State.NOT_CONFIRMED)
                .build();

        usersRepository.save(user);

    }
}
