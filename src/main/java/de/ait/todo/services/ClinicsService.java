package de.ait.todo.services;

import de.ait.todo.dto.ClinicDto;
import de.ait.todo.dto.NewClinicDto;

import java.util.List;


public interface ClinicsService {

    ClinicDto addClinic(NewClinicDto newClinic);

    List<ClinicDto> getClinics();

    ClinicDto getClinic(Long clinicId);


    ClinicDto deleteClinic(Long clinicId);

    List<ClinicDto> getClinicsByCity(String clinicCity);

}
