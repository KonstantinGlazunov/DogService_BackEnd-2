package de.ait.todo.controllers.api;

import de.ait.todo.dto.NewDogLoverDto;
import de.ait.todo.dto.DogLoverDto;
import de.ait.todo.validation.dto.ValidationErrorsDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * 6/12/2023
 * spring-security-demo
 *
 * @author Marsel Sidikov (AIT TR)
 */
@Tags(value = {
        @Tag(name = "Users")
})
@RequestMapping("/api/registerUser")
public interface SignUpDogLover {

    @Operation(summary = "Регистрация пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Зарегистрированный пользователь",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = DogLoverDto.class))
                    }
            ),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorsDto.class))
                    })
    })

    @PostMapping
    ResponseEntity<DogLoverDto> signUp(@RequestBody @Valid NewDogLoverDto newUser);
}

