package de.ait.todo.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 6/11/2023
 * backend-demo
 *
 * @author Marsel Sidikov (AIT TR)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "dog_sitters")
public class DogSitter {

    public enum DogSize {
        A_MINI, B_SMALL, C_MIDDLE, D_BIG, E_GREAT
    }

    public enum Role {
        USER, DOGSITTER
    }

    @Id
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

    @Column(nullable = false, unique = true)
    private String email;

    @Transient
    private String hashPassword;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Task> tasks;

    @Enumerated(value = EnumType.STRING)
    private DogSize dogSize;

}

