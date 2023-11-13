package de.ait.todo.controllers.api;

import de.ait.todo.dto.NewDogSitterDto;
import de.ait.todo.dto.DogSitterDto;
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

@Tags(value = {
        @Tag(name = "Setters")
})
@RequestMapping("/api/registerSetter")
public interface SignUpDogSitter {
    @Operation(summary = "Регистрация сеттера")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Зарегистрированный сеттер",
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
    ResponseEntity<DogSitterDto> signUp(@RequestBody @Valid NewDogSitterDto newSetter);

}
