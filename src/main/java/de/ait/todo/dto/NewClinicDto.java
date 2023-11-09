package de.ait.todo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
@Schema(name = "NewClinic")
public class NewClinicDto {

    @Schema(description = "clinic name", example = "Pets clinic")
    private String name;

    @Schema(description = "clinic description", example = "Clinic for small and big pets")
    private String description;

    @Schema(description = "clinic web-site", example = "https://clinic-example.de")
    private String webSite;

    @Schema(description = "clinic country", example = "Germany")
    private String country;

    @Schema(description = "clinic city", example = "Berlin")
    private String clinicCity;

    @Schema(description = "clinic postcode", example = "01010")
    private String postCode;

    @Schema(description = "clinic address", example = "Hauptstrasse 7")
    private String address;

    @Schema(description = "clinic telephone number", example = "+49 172 111 11 111")
    private String telephoneNumber;
}
