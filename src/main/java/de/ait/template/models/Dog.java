package de.ait.template.models;

import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "dog")
public class Dog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20,nullable = false)
    private String name;

    @Column(length = 50)
    private String breed;

    @Column
    private int age;

    @Column
    private double height;

    @Column
    private double  weight;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Dog dog = (Dog) o;
        return getId() != null && Objects.equals(getId(), dog.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @ManyToMany(mappedBy = "dogs")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Walker> walkers;

    @ManyToMany(mappedBy = "dogs")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Kennel> kennels;

    @ManyToMany(mappedBy = "dogs")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<VetClinic> vetClinics;

}
