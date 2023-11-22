package de.ait.todo.controllers.api;

import de.ait.todo.dto.DogSitterDto;
import de.ait.todo.dto.DogSitterToDogLoverDto;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


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
    ResponseEntity<DogLoverDto> registerDogLover(@RequestBody @Valid NewDogLoverDto newUser);

    @Operation(summary = "Добавление DogSitter к DogLover")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "DogSitter добавлен",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = DogSitterDto.class))
                    }
            ),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorsDto.class))
                    })
    })
    @PostMapping("/{dogLover-id}/dogSitters")
    ResponseEntity<List<DogSitterDto>> addDogSitterToDogLover(@PathVariable("dogLover-id") Long dogLoverId,
                                                              @RequestBody DogSitterToDogLoverDto gogSitterData);


    @Operation(summary = "Добавление DogSitter к DogLover")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "DogSitter добавлен",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = DogSitterDto.class))
                    }
            ),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorsDto.class))
                    })
    })
//    @GetMapping("/dogLov-id/{loveId}/dogSid/{sitId}")
//    ResponseEntity<List<DogSitterDto>> addDogSittersToDogLover(@PathVariable("loveId") Long dogLoverId, @PathVariable("sitId") DogSitterToDogLoverDto gogSitterData);
    @GetMapping("/dogLov-id/{loveId}/dogSid/{sitId}")
    List<DogSitterDto> addDogSittersToDogLover(@PathVariable("loveId") Long dogLoverId, @PathVariable("sitId") Long gogSitterId);


    @Operation(summary = "Вывод списка DogSitter связанных с DogLover")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список получен",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = DogSitterDto.class))
                    }
            ),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorsDto.class))
                    })
    })
    @GetMapping("/{dogLover-id}/dogSitters")
    ResponseEntity<List<DogSitterDto>> getDogSittersOfDogLover(@PathVariable("dogLover-id") Long dogLoverId);

}

