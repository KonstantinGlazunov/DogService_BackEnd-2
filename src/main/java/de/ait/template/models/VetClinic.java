package de.ait.template.models;

import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
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
@Table(name = "clinic")
public class VetClinic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String title;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false,length = 20)
    private String city;

    @Column(nullable = false, length = 100)
    private String address;

    @Column(nullable = false, length = 1000)
    private String purposeOfVisit;

    private LocalDate AppointmentDate;

    private LocalTime AppointmentTime;

    @Enumerated(value = EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @Column(nullable = false)
    private Double price;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        VetClinic vetClinic = (VetClinic) o;
        return getId() != null && Objects.equals(getId(), vetClinic.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @ManyToMany
    @JoinTable(
            name = "clinic_dog",
            joinColumns =
            @JoinColumn(name = "clinic_id", nullable = false, referencedColumnName = "id"),
            inverseJoinColumns =
            @JoinColumn(name = "dog_id", nullable = false, referencedColumnName = "id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"clinic_id", "dog_id"})
    )
    @ToString.Exclude
    private Set<Dog> dogs;
}
