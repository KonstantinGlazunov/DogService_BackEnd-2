package de.ait.todo.controllers.api;

import de.ait.todo.dto.DogSitterDto;
import de.ait.todo.dto.StandardResponseDto;
import de.ait.todo.dto.TaskDto;
import de.ait.todo.models.DogSitter;
import de.ait.todo.validation.dto.ValidationErrorsDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tags(
        @Tag(name = "DogSitters", description = "Work with Dog sitters")
)
@ApiResponses(value = {

        @ApiResponse(responseCode = "404",
                description = "Dog sitters not found",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = StandardResponseDto.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error.")
})

@RequestMapping("/back/api/dog-sitters")
public interface DogSittersApi {
    @ApiResponse(responseCode = "200",
            description = "Successfully request",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = DogSitterDto.class))
    )
    @Operation(summary = "Getting list of Dog sitters", description = "Available to all")
    @GetMapping
    List<DogSitterDto> getDogSitters();


    @Operation(summary = "Getting list of Dog sitters by city or size or zip",
            description = "Available to all. All parameters are optional. " +
                    "A query without parameters will return all dog sitters. " +
                    "Spaces at the beginning and ending City are not counted." +
                    " In a ZIP request, only the first two characters apply. For example, 37778 " +
                    "will return all sitters whose ZIP starts with 37*** . ")
    @ApiResponse(responseCode = "200",
            description = "Successfully request",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = DogSitterDto.class))
    )
    @GetMapping("/search")
    List<DogSitterDto> getDogSittersByCityAndDogSize(@RequestParam(value = "city", required = false) String city,
                                                     @RequestParam(value = "dog-size", required = false) DogSitter.DogSize dogSize,
                                                     @RequestParam(value = "zip", required = false) String zip);


    @Operation(summary = "Delete DogSitter by id",
            description = "Available to ALL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "The dog sitter has been successfully deleted.",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TaskDto.class))
                    }
            )
    })
    @DeleteMapping("/{dog-sitter-id}")
    DogSitterDto deleteDogSitter(@PathVariable("dog-sitter-id") Long id);

}
