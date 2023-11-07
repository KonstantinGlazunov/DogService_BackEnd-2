package de.ait.todo.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "dog_lovers")
public class DogLover {
    public enum Role {
        USER, DOGLOVER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private DogLover.Role role;

    @OneToMany(mappedBy = "user")
    private List<Task> tasks;
}
