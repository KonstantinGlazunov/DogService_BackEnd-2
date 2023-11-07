package de.ait.todo.dto;

import de.ait.todo.models.DogSitter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Зарегистрированный пользователь")
public class DogSitterDto {
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
    public static DogSitterDto from(DogSitter dogSitter) {
        return DogSitterDto.builder()
                .id(dogSitter.getId())
                .email(dogSitter.getEmail())
                .build();
    }

    public static List<DogSitterDto> from(List<DogSitter> dogSitters) {
        return dogSitters.stream()
                .map(DogSitterDto::from)
                .collect(Collectors.toList());
    }
}