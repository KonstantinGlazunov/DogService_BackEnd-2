package de.ait.todo.dto;

import de.ait.todo.models.Kennel;
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
@Schema(name = "Kennel", description = "Kennel information")
public class KennelDto {

    @Schema(description = "kennel ID", example = "1")
    private Long id;

    @Schema(description = "kennel name", example = "Dogs kennel")
    private String name;

    @Schema(description = "kennel description", example = "Kennel for small and big dogs")
    private String description;

    @Schema(description = "kennel web-site", example = "https://kennel-example.de")
    private String webSite;

    @Schema(description = "kennel country", example = "Germany")
    private String country;

    @Schema(description = "kennel city", example = "Berlin")
    private String kennelCity;

    @Schema(description = "kennel postcode", example = "01611")
    private String postCode;

    @Schema(description = "kennel address", example = "Pragerstrasse 7")
    private String address;

    @Schema(description = "kennel telephone number", example = "+4917211777111")
    private String telephoneNumber;

    public static KennelDto from (Kennel kennel) {
        return KennelDto.builder()
                .id(kennel.getId())
                .name(kennel.getName())
                .description(kennel.getDescription())
                .webSite(kennel.getWebSite())
                .country(kennel.getCountry())
                .kennelCity(kennel.getKennelCity())
                .postCode(kennel.getPostCode())
                .address(kennel.getAddress())
                .telephoneNumber(kennel.getTelephoneNumber())
                .build();
    }

    public static List<KennelDto> from(Collection<Kennel> kennels){
        return kennels.stream()
                .map(KennelDto::from)
                .collect(Collectors.toList());
    }
}
