package de.ait.todo.controllers.api;

import de.ait.todo.dto.DogSitterDto;
import de.ait.todo.dto.StandardResponseDto;
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


    @Operation(summary = "Getting list of Dog sitters by city", description = "Available to all")
    @GetMapping("/search")
    List<DogSitterDto> getDogSittersByCity(@RequestParam(value = "city", required = false) String city);
}
