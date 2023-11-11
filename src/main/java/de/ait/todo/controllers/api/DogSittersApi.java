package de.ait.todo.controllers.api;

import de.ait.todo.dto.DogSitterDto;
import de.ait.todo.dto.StandardResponseDto;
import de.ait.todo.models.DogSitter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tags(
        @Tag(name = "DogSitters", description = "Work with Dog sitters")
)
@ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                description = "Successfully request",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = DogSitterDto.class))
        ),
        @ApiResponse(responseCode = "404",
                description = "Dog sitters not found",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = StandardResponseDto.class)))
})
@RequestMapping("/api/dog-sitters")
public interface DogSittersApi {

    @Operation(summary = "Getting list of Dog sitters", description = "Available to all")
    @GetMapping
    List<DogSitterDto> getDogSitters();


    @Operation(summary = "Getting list of Dog sitters by city or size or zip",
            description = "Available to all. All parameters are optional. " +
            "A query without parameters will return all dog sitters. " +
            "Spaces in ZIP and City are not counted."+
            " In a ZIP request, only the first three characters apply. For example, 37778 " +

            "will return all sitters whose ZIP starts with 377** .")
    @GetMapping("/search")
    List<DogSitterDto> getDogSittersByCityAndDogSize(@RequestParam(value = "city", required = false) String city,
                                                     @RequestParam(value = "dog-size", required = false) DogSitter.DogSize dogSize,
                                                     @RequestParam(value = "zip", required = false) String zip);
}
