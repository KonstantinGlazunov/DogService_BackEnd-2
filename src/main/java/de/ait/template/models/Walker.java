package de.ait.template.models;

import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "walker")
public class Walker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 20)
    private String firstName;

    @Column(nullable = false,length = 20)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String hashPassword;

    @Column(nullable = false,length = 20)
    private String city;

    @Column(nullable = false)
    private double price;

    private LocalTime startTime;

    private LocalTime finishTime;

    @Enumerated(value = EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Walker walker = (Walker) o;
        return getId() != null && Objects.equals(getId(), walker.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @ManyToMany
    @JoinTable(
            name = "walker_dog",
            joinColumns =
            @JoinColumn(name = "walker_id", nullable = false, referencedColumnName = "id"),
            inverseJoinColumns =
            @JoinColumn(name = "dog_id", nullable = false, referencedColumnName = "id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"walker_id", "dog_id"})
    )
    @ToString.Exclude
    private Set<Dog> dogs;
}
