package de.ait.todo.dto;

import de.ait.todo.models.Clinic;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "Clinic", description = "Clinic information")
public class ClinicDto {

    @Schema(description = "clinic ID", example = "1")
    private Long id;

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

    @Schema(description = "clinic telephone number", example = "+4917211111111")
    private String telephoneNumber;

    public static ClinicDto from (Clinic clinic) {
        return ClinicDto.builder()
                .id(clinic.getId())
                .name(clinic.getName())
                .description(clinic.getDescription())
                .webSite(clinic.getWebSite())
                .country(clinic.getCountry())
                .clinicCity(clinic.getClinicCity())
                .postCode(clinic.getPostCode())
                .address(clinic.getAddress())
                .telephoneNumber(clinic.getTelephoneNumber())
                .build();
    }

    public static List<ClinicDto> from(Collection<Clinic> clinics) {
        return clinics.stream()
                .map(ClinicDto::from)
                .collect(Collectors.toList());
    }
}
