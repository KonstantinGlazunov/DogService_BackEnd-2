package de.ait.todo.dto;

import de.ait.todo.models.DogSitter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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

    @Schema(description = "email", example = "DogSitter email")
    private String email;

    @Schema(description = "Maximum size of dog that the dog sitter is willing to accept", example = "SMALL")
    private String dogSize;

    public static DogSitterDto from(DogSitter dogSitter) {
        return DogSitterDto.builder()
                .id(dogSitter.getId())
                .firstName(dogSitter.getFirstName())
                .lastName(dogSitter.getLastName())
                .userName(dogSitter.getUserName())
                .city(dogSitter.getCity())
                .zip(dogSitter.getZip())
                .email(dogSitter.getEmail())
                .dogSize(dogSitter.getDogSize() != null ? dogSitter.getDogSize().toString() : null)
                .build();
    }

    public static List<DogSitterDto> from(List<DogSitter> dogSitters) {
        return dogSitters.stream()
                .map(DogSitterDto::from)
                .collect(Collectors.toList());
    }

    public static List<DogSitterDto> from(Set<DogSitter> dogSitters) {
        return dogSitters.stream()
                .map(DogSitterDto::from)
                .collect(Collectors.toList());
    }


}