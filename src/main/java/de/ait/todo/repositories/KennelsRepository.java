package de.ait.todo.repositories;

import de.ait.todo.models.Kennel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface KennelsRepository extends JpaRepository<Kennel, Long> {

   @Query(value = "select kennel from Kennel kennel where kennel.kennelCity = :kennelCity")
    List<Kennel> getKennelsByCity(@Param("kennelCity") String kennelCity);
}


