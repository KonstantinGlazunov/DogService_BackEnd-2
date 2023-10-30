package de.ait.template.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "NewUser", description = "Данные для регистрации")
public class NewUserDto {

    @Email
    @NotNull
    @Schema(description = "Email пользователя", example = "user@mail.com")
    private String email;

    @NotNull
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$")
    @Schema(description = "Пароль пользователя", example = "Qwerty007!")
    private String password;

    @NotNull
    @Schema(description = "Имя пользователя", example = "Marsel")
    private String firstName;

    @NotNull
    @Schema(description = "Фамилия пользователя", example = "Sidikov")
    private String lastName;
}
