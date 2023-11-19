package de.ait.todo.services.impl;

import de.ait.todo.dto.*;
import de.ait.todo.exceptions.RestException;
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
import de.ait.todo.services.SignUpDogLoverService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static de.ait.todo.dto.DogLoverDto.from;
import static de.ait.todo.dto.DogSitterDto.from;
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
    private final DogSittersRepository sittersRepository;
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

    @Override
    public List<DogSitterDto> addDogSitterToDogLover(Long dogLoverId, DogSitterToDogLoverDto dogSitterData) {

        DogLover dogLover = loverRepository.findById(dogLoverId).orElseThrow(() ->
                new RestException(HttpStatus.NOT_FOUND, "Course with id <" + dogLoverId + "> not found"));

        DogSitter dogSitter = sittersRepository.findById(dogSitterData.getDogSitterId()).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND,
                       "User with id <" + dogSitterData.getDogSitterId() + "> not found"));

        if (!dogSitter.getDogLovers().add(dogLover)) {
            throw new RestException(HttpStatus.BAD_REQUEST, "User with id <"
                    + dogSitter.getId() + "> already in course with id <" + dogLover.getId() + ">");
        }
        sittersRepository.save(dogSitter);
        Set<DogSitter> dogSitterOfDogLover = sittersRepository.findAllByDogLoversContainsOrderById(dogLover);

        return from(dogSitterOfDogLover);

    }

    @Override
    public List<DogSitterDto> getDogSittersOfDogLover(Long dogLoverId) {
        DogLover dogLover = getDogLoverOrThrow(dogLoverId);
        Set<DogSitter> dogSitterOfDogLover = sittersRepository.findAllByDogLoversContainsOrderById(dogLover);
        return from(dogSitterOfDogLover);
    }

    private DogLover getDogLoverOrThrow(Long dogLoverId) {
        return loverRepository.findById(dogLoverId)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Course with id <" + dogLoverId + "> not found"));
    }


}
