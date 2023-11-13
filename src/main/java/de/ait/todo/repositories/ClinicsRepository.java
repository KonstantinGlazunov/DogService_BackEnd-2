package de.ait.todo.repositories;

import de.ait.todo.models.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ClinicsRepository extends JpaRepository<Clinic, Long> {

    @Query(value = "select clinic from Clinic clinic where clinic.clinicCity = :clinicCity")
    List<Clinic> getClinicsByClinicCity(@Param("clinicCity") String clinicCity);


}
