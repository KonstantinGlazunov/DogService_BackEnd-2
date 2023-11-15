package de.ait.todo.controllers;

import de.ait.todo.controllers.api.SignUpDogSitter;
import de.ait.todo.dto.NewDogSitterDto;
import de.ait.todo.dto.DogSitterDto;
import de.ait.todo.services.SignUpSitterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SignUpDogSitterController implements SignUpDogSitter {

    private final SignUpSitterService signUpSitterService;

    @Override
    public ResponseEntity<DogSitterDto> RegisterDogSitter(NewDogSitterDto newSetter) {

        return ResponseEntity
                .status(201)
                .body(signUpSitterService.RegisterDogSitter(newSetter));
    }
}
