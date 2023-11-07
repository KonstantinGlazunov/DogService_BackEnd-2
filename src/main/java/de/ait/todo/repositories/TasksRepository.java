package de.ait.todo.repositories;

import de.ait.todo.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 6/11/2023
 * backend-demo
 *
 * @author Marsel Sidikov (AIT TR)
 */
public interface TasksRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByUser_Id(Long userId);
}
