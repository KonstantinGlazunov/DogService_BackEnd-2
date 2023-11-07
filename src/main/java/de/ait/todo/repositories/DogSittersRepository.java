package de.ait.todo.repositories;

import de.ait.todo.models.DogSitter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DogSittersRepository extends JpaRepository<DogSitter, Long> {
    Optional<DogSitter> findByEmail(String email);
    boolean existsById(Long id);
}
