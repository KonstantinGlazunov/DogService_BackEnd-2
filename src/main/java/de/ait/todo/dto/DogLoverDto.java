package de.ait.todo.dto;

import de.ait.todo.models.DogLover;
import de.ait.todo.models.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 6/12/2023
 * spring-security-demo
 *
 * @author Marsel Sidikov (AIT TR)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Зарегистрированный пользователь")
public class DogLoverDto {
    @Schema(description = "идентификатор пользователя", example = "1")
    private Long id;

    @NotNull
    @Schema(description = "Имя пользователя", example = "Marsel")
    private String firstName;

    @NotNull
    @Schema(description = "Фамилия пользователя", example = "Sidikov")
    private String lastName;

    @NotNull
    @Schema(description = "Публичное имя  пользователя", example = "@Leonid")
    private String userName;

    @NotNull
    @Schema(description = "Город пользователя", example = "Berlin")
    private String city;

    @NotNull
    @Schema(description = "Почтовый индекс пользователя", example = "35778")
    private String zip;

    @Schema(description = "имя пользователя", example = "username")
    private String email;
    public static DogLoverDto from(DogLover dogLover) {
        return DogLoverDto.builder()
                .id(dogLover.getId())
                .email(dogLover.getEmail())
                .build();
    }

    public static List<DogLoverDto> from(List<DogLover> dogLovers) {
        return dogLovers.stream()
                .map(DogLoverDto::from)
                .collect(Collectors.toList());
    }

}
