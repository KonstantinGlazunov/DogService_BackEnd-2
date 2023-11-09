package de.ait.todo.repositories;

import de.ait.todo.models.DogSitter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DogSittersRepository extends JpaRepository<DogSitter, Long> {
    Optional<DogSitter> findByEmail(String email);
    boolean existsById(Long id);


    @Query("SELECT dogSitter FROM DogSitter dogSitter " +
            "WHERE (:city IS NULL OR dogSitter.city = :city)")
    List<DogSitter> findDogSittersByCity(@Param("city") String city);


}
