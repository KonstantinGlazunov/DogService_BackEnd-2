package de.ait.todo.controllers.api;

import de.ait.todo.dto.ClinicDto;
import de.ait.todo.dto.NewClinicDto;
import de.ait.todo.dto.StandardResponseDto;
import de.ait.todo.dto.UpdateClinicDto;
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

@RequestMapping("/api/clinics")
@Tags(value = {
        @Tag(name = "Clinics")
})
@ApiResponses({
        @ApiResponse(responseCode = "401",
                description = "User is not authorized ",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = StandardResponseDto.class))),
        @ApiResponse(responseCode = "403",
               description = "Forbidden", content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = StandardResponseDto.class)))

})
public interface ClinicsApi {
    @Operation(summary = "Create clinic", description = "Manager access")
    @ApiResponses({
            @ApiResponse(responseCode = "201",
                    description = "Clinic was successfully created ",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClinicDto.class))),
            @ApiResponse(responseCode = "400",
                    description = "Validation error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrorsDto.class)))

    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    ClinicDto addClinic(@RequestBody @Valid NewClinicDto newClinic);

    @Operation(summary = "Get clinics list", description = "All users access")
    @GetMapping
    List<ClinicDto> getClinics();

    @Operation(summary = "Get clinic", description = "All users access")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Request successfully processed",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClinicDto.class))),
            @ApiResponse(responseCode = "404",
                    description = "Clinic not found ",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class)))
    })
    @GetMapping("/{clinic-id}")
    ClinicDto getClinic(@Parameter(description = "clinic ID", example = "1")
                        @PathVariable("clinic-id") Long clinicId);

    @GetMapping("/byCities")
    List<ClinicDto> getClinicsByCity(@RequestParam("city") String clinicCity);

    @Operation(summary = "Delete clinic", description = "Manager access")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Clinic was successfully deleted",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ClinicDto.class))),
            @ApiResponse(responseCode = "404",
                    description = "Clinic not found ",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StandardResponseDto.class)))
    })
    @DeleteMapping("/{clinic-id}")
    ClinicDto deleteClinic(@Parameter(description = "clinic ID", example = "1")
                           @PathVariable("clinic-id") Long clinicId);

    @Operation(summary = "Update information about clinic", description = "Manager access")
    @PutMapping("/{clinic-id}")
    ClinicDto updateClinic(@Parameter(description = "clinic ID", example = "1")
                           @PathVariable("clinic-id") Long clinicId,
                           @RequestBody @Valid UpdateClinicDto updateClinic);




}
