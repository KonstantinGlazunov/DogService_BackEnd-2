package de.ait.todo.controllers.api;

import de.ait.todo.dto.*;
import de.ait.todo.validation.dto.ValidationErrorsDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/kennels")
@Tags(value = {
        @Tag(name = "Kennels")
})
@ApiResponses({
        @ApiResponse(responseCode = "403",
                description = "Forbidden", content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = StandardResponseDto.class)))

})
public interface KennelsApi {
    @Operation(summary = "Create kennel", description = "Admin access")
    @ApiResponses({
            @ApiResponse(responseCode = "201",
                    description = "Kennel was successfully created ",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = KennelDto.class))),
            @ApiResponse(responseCode = "400",
                    description = "Validation error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrorsDto.class)))

    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    KennelDto addKennel(@RequestBody @Valid NewKennelDto newKennel);


    @Operation(summary = "Get kennels list", description = "All users access")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Request successfully processed",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClinicDto.class))),
    })
    @GetMapping
    List<KennelDto> getKennels();

    @Operation(summary = "Get kennel", description = "All users access")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Request successfully processed",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = KennelDto.class))),
            @ApiResponse(responseCode = "404",
                    description = "Kennel not found ",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class)))
    })
    @GetMapping("/{kennel-id}")
    KennelDto getKennel(@Parameter(description = "kennel ID", example = "1")
                        @PathVariable("kennel-id") Long kennelId);

    @Operation(summary = "Get kennel by city", description = "All users access")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Request successfully processed",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClinicDto.class))),
            @ApiResponse(responseCode = "404",
                    description = "Kennel not found ",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class)))
    })
    @GetMapping("/byCities")
    List<KennelDto> getKennelsByCity(@RequestParam("city") String kennelCity);

    @Operation(summary = "Delete kennel", description = "Admin access")
    @ApiResponses({
            @ApiResponse(responseCode = "204",
                    description = "Kennel was successfully deleted",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = KennelDto.class))),
            @ApiResponse(responseCode = "404",
                    description = "Kennel not found ",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class)))
    })
    @DeleteMapping("/{kennel-id}")
    KennelDto deleteKennel(@Parameter(description = "kennel ID", example = "1")
                           @PathVariable("kennel-id") Long kennelId);

    @Operation(summary = "Update information about kennel", description = "Admin access")
    @ApiResponses({
            @ApiResponse(responseCode = "202",
                    description = "Information about kennel was successfully updated ",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClinicDto.class))),
            @ApiResponse(responseCode = "400",
                    description = "Validation error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrorsDto.class)))

    })
    @PutMapping("/{kennel-id}")
    KennelDto updateKennel(@Parameter(description = "kennel ID", example = "1")
                           @PathVariable("kennel-id") Long kennelID,
                           @RequestBody @Valid UpdateKennelDto updateKennel);



}
