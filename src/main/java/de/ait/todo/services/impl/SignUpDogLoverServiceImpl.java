package de.ait.todo.services.impl;

import de.ait.todo.dto.NewDogLoverDto;
import de.ait.todo.dto.DogLoverDto;
import de.ait.todo.dto.NewUserDto;
import de.ait.todo.dto.UserDto;
import de.ait.todo.mail.MailTemplatesUtil;
import de.ait.todo.mail.TemplateProjectMailSender;
import de.ait.todo.models.ConfirmationCode;
import de.ait.todo.models.DogLover;
import de.ait.todo.models.User;
import de.ait.todo.repositories.ConfirmationCodesRepository;
import de.ait.todo.repositories.DogLoverRepository;
import de.ait.todo.repositories.UsersRepository;
import de.ait.todo.services.SignUpDogLoverService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

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
    private final ConfirmationCodesRepository confirmationCodesRepository;
    private final PasswordEncoder passwordEncoder;
    private final TemplateProjectMailSender mailSender;
    private final MailTemplatesUtil mailTemplatesUtil;

    @Value("${base.url}")
    private String baseUrl;
    @Override
    public DogLoverDto registerDogLover(NewDogLoverDto newDogLover) {

        String codeValue = UUID.randomUUID().toString();

        User user = saveUser(newDogLover);

        DogLover dogLover = saveNewDogLover(newDogLover, user);

        saveConfirmCode(user, codeValue);

        String link = createLinkForConfirmation(codeValue);
        String html = mailTemplatesUtil.createConfirmationMail(newDogLover.getFirstName(), newDogLover.getLastName(), link);

        mailSender.send(newDogLover.getEmail(), "Registration", html);

        return from(dogLover);
  }

    private DogLover saveNewDogLover(NewDogLoverDto newDogLover, User user) {

        DogLover dogLover = DogLover.builder()
                .id(user.getId())
                .firstName(newDogLover.getFirstName())
                .lastName(newDogLover.getLastName())
                .userName(newDogLover.getUserName())
                .city(newDogLover.getCity())
                .zip(newDogLover.getZip())
                .email(newDogLover.getEmail())
                .role(DogLover.Role.DOGLOVER)
                .build();

        loverRepository.save(dogLover);

        return dogLover;
    }

    private void saveConfirmCode(User user, String codeValue) {
        ConfirmationCode code = ConfirmationCode.builder()
                .code(codeValue)
                .user(user)
                .expiredDateTime(LocalDateTime.now().plusMinutes(360))
                .build();

        confirmationCodesRepository.save(code);
    }

    private String createLinkForConfirmation(String codeValue) {
        return baseUrl + "confirm?id=" + codeValue;
    }

    public User saveUser(NewDogLoverDto newDogLover) {
        User user = User.builder()
                .email(newDogLover.getEmail())
                .hashPassword(passwordEncoder.encode(newDogLover.getPassword()))
                .role(User.Role.DOGLOVER)
                .userName(newDogLover.getUserName())
                .state(User.State.NOT_CONFIRMED)
                .build();

        usersRepository.save(user);

        return user;

    }
}
