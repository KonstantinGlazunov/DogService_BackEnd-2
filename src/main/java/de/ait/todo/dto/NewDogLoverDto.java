package de.ait.todo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

/**
 * 6/12/2023
 * spring-security-demo
 *
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewDogLoverDto {
//
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

    @Schema(description = "Email пользователя", example = "simple@mail.com")
    @Email
    @NotNull
    @NotBlank
    private String email;

    @Schema(description = "Пароль пользователя", example = "Qwerty007!")
    @NotBlank
    @Size(min = 7, max = 1000)
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$", message = "Weak password")
    private String password;
}
