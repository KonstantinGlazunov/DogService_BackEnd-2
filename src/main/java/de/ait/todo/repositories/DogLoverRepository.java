package de.ait.todo.repositories;

import de.ait.todo.models.DogLover;
import de.ait.todo.models.DogSitter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DogLoverRepository extends JpaRepository<DogLover, Long> {
}
