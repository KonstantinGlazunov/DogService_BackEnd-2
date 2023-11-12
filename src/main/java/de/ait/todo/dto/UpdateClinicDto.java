package de.ait.todo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Schema(description = "fields to update")
public class UpdateClinicDto {


    @NotBlank
    @NotEmpty
    @Schema(description = "clinic name", example = "Pets clinic")
    private String name;

    @NotNull
    @NotBlank
    @NotEmpty
    @Schema(description = "clinic description", example = "Clinic for small and big pets")
    private String description;

    @NotNull
    @NotBlank
    @NotEmpty
    @Schema(description = "clinic web-site", example = "https://clinic-example.de")
    private String webSite;

    @NotNull
    @NotBlank
    @NotEmpty
    @Schema(description = "clinic country", example = "Germany")
    private String country;

    @NotNull
    @NotBlank
    @NotEmpty
    @Schema(description = "clinic city", example = "Berlin")
    private String clinicCity;

    @NotNull
    @NotBlank
    @NotEmpty
    @Schema(description = "clinic postcode", example = "01010")
    private String postCode;

    @NotNull
    @NotBlank
    @NotEmpty
    @Schema(description = "clinic address", example = "Hauptstrasse 7")
    private String address;

    @NotNull
    @NotBlank
    @NotEmpty
    @Schema(description = "clinic telephone number", example = "+49 172 111 11 111")
    private String telephoneNumber;

}
