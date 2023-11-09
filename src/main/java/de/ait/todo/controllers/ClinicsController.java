package de.ait.todo.controllers;

import de.ait.todo.controllers.api.ClinicsApi;
import de.ait.todo.dto.ClinicDto;
import de.ait.todo.dto.NewClinicDto;
import de.ait.todo.services.ClinicsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ClinicsController implements ClinicsApi {

    private final ClinicsService clinicsService;

    @Override
    public ClinicDto addClinic(NewClinicDto newClinic) {
        return clinicsService.addClinic(newClinic);
    }

    @Override
    public List<ClinicDto> getClinics() {
        return clinicsService.getClinics();
    }

    @Override
    public ClinicDto getClinic(Long clinicId) {
        return clinicsService.getClinic(clinicId);
    }

    @Override
    public List<ClinicDto> getClinicsByCity(String clinicCity) {
        return clinicsService.getClinicsByCity(clinicCity);
    }


    @Override
    public ClinicDto deleteClinic(Long clinicId) {
        return clinicsService.deleteClinic(clinicId);
    }

}
