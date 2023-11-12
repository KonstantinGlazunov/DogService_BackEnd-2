package de.ait.todo.services.impl;

import de.ait.todo.dto.ClinicDto;
import de.ait.todo.dto.NewClinicDto;
import de.ait.todo.dto.UpdateClinicDto;
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

    @Override
    public ClinicDto updateClinic(Long clinicId, UpdateClinicDto updateClinic) {
        Clinic clinic = getClinicOrThrow(clinicId);

        clinic.setName(updateClinic.getName());

        if (clinic.getDescription() != null){
            clinic.setDescription(updateClinic.getDescription());
        } else {
            clinic.setDescription(null);
        }

        if (clinic.getWebSite() != null){
            clinic.setWebSite(updateClinic.getWebSite());
        } else {
            clinic.setWebSite(null);
        }

        if (clinic.getCountry() != null){
            clinic.setCountry(updateClinic.getCountry());
        } else {
            clinic.setCountry(null);
        }

        if (clinic.getClinicCity() != null){
            clinic.setClinicCity(updateClinic.getClinicCity());
        }  else {
            clinic.setClinicCity(null);
        }

        if (clinic.getPostCode() != null){
            clinic.setPostCode(updateClinic.getPostCode());
        } else {
            clinic.setPostCode(null);
        }

        if (clinic.getAddress() != null){
            clinic.setAddress(updateClinic.getAddress());
        } else {
            clinic.setAddress(null);
        }

        if (clinic.getTelephoneNumber() != null){
            clinic.setTelephoneNumber(updateClinic.getTelephoneNumber());
        } else {
            clinic.setTelephoneNumber(null);
        }

        clinicsRepository.save(clinic);
        return from(clinic);
    }

    private Clinic getClinicOrThrow(Long clinicId) {
        return clinicsRepository.findById(clinicId)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Clinic with id <" + clinicId + "> not found"));
    }
}
