package de.ait.todo.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "dogSitters")
@EqualsAndHashCode(exclude = "dogLovers")
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

//    @OneToMany(mappedBy = "user")
//    private List<Task> tasks;

    @Enumerated(value = EnumType.STRING)
    private DogSize dogSize;

    @ManyToMany
    @JoinTable(
            name = "DogLovers_DogSitters",
            joinColumns =
            @JoinColumn(name = "dogSitter_id", nullable = false, referencedColumnName = "id"),
            inverseJoinColumns =
            @JoinColumn(name = "dogLover_id", nullable = false, referencedColumnName = "id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"dogSitter_id", "dogLover_id"})
    )
    @ToString.Exclude
    private Set<DogLover> dogLovers;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        DogSitter dogSitter = (DogSitter) o;
        return getId() != null && Objects.equals(getId(), dogSitter.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}

