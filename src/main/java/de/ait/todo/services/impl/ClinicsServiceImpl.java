package de.ait.todo.services.impl;

import de.ait.todo.dto.ClinicDto;
import de.ait.todo.dto.NewClinicDto;
import de.ait.todo.exceptions.RestException;
import de.ait.todo.models.Clinic;
import de.ait.todo.repositories.ClinicsRepository;
import de.ait.todo.services.ClinicsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static de.ait.todo.dto.ClinicDto.from;

@RequiredArgsConstructor
@Service
public class ClinicsServiceImpl implements ClinicsService {

    private final ClinicsRepository clinicsRepository;
    @Override
    public ClinicDto addClinic(NewClinicDto newClinic) {
        Clinic clinic = Clinic.builder()
                .name(newClinic.getName())
                .description(newClinic.getDescription())
                .webSite(newClinic.getWebSite())
                .country(newClinic.getCountry())
                .clinicCity(newClinic.getClinicCity())
                .postCode(newClinic.getPostCode())
                .address(newClinic.getAddress())
                .telephoneNumber(newClinic.getTelephoneNumber())
                .build();

        clinicsRepository.save(clinic);
        return from(clinic);
    }

    @Override
    public List<ClinicDto> getClinics() {
        List<Clinic> clinics = clinicsRepository.findAll();
        return from(clinics);
    }

    @Override
    public ClinicDto getClinic(Long clinicId) {
        Clinic clinic = getClinicOrThrow(clinicId);
        return from(clinic);
    }


    @Override
    public ClinicDto deleteClinic(Long clinicId) {
        Clinic clinic = getClinicOrThrow(clinicId);
        clinicsRepository.delete(clinic);
        return from(clinic);
    }

    @Override
    public List<ClinicDto> getClinicsByCity(String clinicCity) {
        List<Clinic> clinics = clinicsRepository.getClinicsByClinicCity(clinicCity);
        if (clinics.isEmpty()){
         throw new RestException(HttpStatus.NOT_FOUND, "Clinic in <" + clinicCity + "> not found");
        }
        return from(clinics);
    }

    private Clinic getClinicOrThrow(Long clinicId) {
        return clinicsRepository.findById(clinicId)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Clinic with id <" + clinicId + "> not found"));
    }
}
