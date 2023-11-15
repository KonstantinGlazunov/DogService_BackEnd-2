package de.ait.todo.services.impl;

import de.ait.todo.dto.*;
import de.ait.todo.mail.MailTemplatesUtil;
import de.ait.todo.mail.TemplateProjectMailSender;
import de.ait.todo.models.ConfirmationCode;
import de.ait.todo.models.DogLover;
import de.ait.todo.models.DogSitter;
import de.ait.todo.models.User;
import de.ait.todo.repositories.ConfirmationCodesRepository;
import de.ait.todo.repositories.DogLoverRepository;
import de.ait.todo.repositories.DogSittersRepository;
import de.ait.todo.repositories.UsersRepository;
import de.ait.todo.services.SignUpSitterService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import static de.ait.todo.dto.DogSitterDto.from;



@RequiredArgsConstructor
@Service
public class SignUpDogSitterServiceImpl implements SignUpSitterService {

    private final DogSittersRepository sittersRepository;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationCodesRepository confirmationCodesRepository;
    private final TemplateProjectMailSender mailSender;
    private final MailTemplatesUtil mailTemplatesUtil;

    @Value("${base.url}")
    private String baseUrl;

    public DogSitterDto RegisterDogSitter(NewDogSitterDto newSitter) {
        String codeValue = UUID.randomUUID().toString();

        User user = saveUser(newSitter);

        DogSitter dogSitter = saveNewDogSitter(newSitter, user);

        saveConfirmCode(user, codeValue);

        String link = createLinkForConfirmation(codeValue);
        String html = mailTemplatesUtil.createConfirmationMail(newSitter.getFirstName(), newSitter.getLastName(), link);

        mailSender.send(newSitter.getEmail(), "Registration", html);

        return DogSitterDto.from(dogSitter);
    }
    private DogSitter saveNewDogSitter(NewDogSitterDto newSitter, User user) {

        DogSitter dogSitter = DogSitter.builder()
                .id(user.getId())
                .firstName(newSitter.getFirstName())
                .lastName(newSitter.getLastName())
                .userName(newSitter.getUserName())
                .city(newSitter.getCity())
                .zip(newSitter.getZip())
                .email(newSitter.getEmail())
                .role(DogSitter.Role.DOGSITTER)
                .dogSize(DogSitter.DogSize.valueOf(newSitter.getSize()))
                .build();

        sittersRepository.save(dogSitter);

        return dogSitter;
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

    public User saveUser(NewDogSitterDto newSitter) {
        User user = User.builder()
                .email(newSitter.getEmail())
                .hashPassword(passwordEncoder.encode(newSitter.getPassword()))
                .role(User.Role.DOGSITTER)
                .userName(newSitter.getUserName())
                .state(User.State.NOT_CONFIRMED)
                .build();

        usersRepository.save(user);

        return user;

    }
}
