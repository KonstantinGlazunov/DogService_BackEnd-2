package de.ait.todo.repositories;

import de.ait.todo.models.DogLover;
import de.ait.todo.models.DogSitter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface DogSittersRepository extends JpaRepository<DogSitter, Long> {
    Optional<DogSitter> findByEmail(String email);

    Set<DogSitter> findAllByDogLoversContainsOrderById(DogLover dogLover);


    boolean existsById(Long id);


    @Query("SELECT dogSitter FROM DogSitter dogSitter " +
            "WHERE (:city IS NULL OR LOWER(dogSitter.city) = TRIM(LOWER(:city)))" +
    "AND (:dogSize IS NULL OR dogSitter.dogSize <= :dogSize)" +
            "AND (:zip IS NULL OR dogSitter.zip LIKE CONCAT(SUBSTRING(TRIM(:zip),1,3),'%'))"+
            "ORDER BY dogSitter.dogSize")
    List<DogSitter> findDogSittersByCityAndDogSizeAndZip(@Param("city") String city,
                                                    @Param("dogSize")DogSitter.DogSize dogSize,
                                                   @Param("zip")String zip);


}

