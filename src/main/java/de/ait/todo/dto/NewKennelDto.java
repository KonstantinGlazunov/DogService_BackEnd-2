package de.ait.todo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Schema(name = "NewKennel")
public class NewKennelDto {

    @NotNull
    @Schema(description = "kennel name", example = "Dogs kennel")
    private String name;

    @NotNull
    @Schema(description = "kennel description", example = "Kennel for small and big dogs")
    private String description;

    @NotNull
    @Schema(description = "kennel web-site", example = "https://kennel-example.de")
    private String webSite;

    @NotNull
    @Schema(description = "kennel country", example = "Germany")
    private String country;

    @NotNull
    @Schema(description = "kennel city", example = "Berlin")
    private String kennelCity;

    @NotNull
    @Schema(description = "kennel postcode", example = "01611")
    private String postCode;

    @NotNull
    @Schema(description = "kennel address", example = "Pragerstrasse 7")
    private String address;

    @NotNull
    @Schema(description = "kennel telephone number", example = "+4917211777111")
    private String telephoneNumber;
}
