package de.ait.todo.services.impl;

import de.ait.todo.dto.ClinicDto;
import de.ait.todo.dto.KennelDto;
import de.ait.todo.dto.NewKennelDto;
import de.ait.todo.dto.UpdateKennelDto;
import de.ait.todo.exceptions.RestException;
import de.ait.todo.models.Clinic;
import de.ait.todo.models.Kennel;
import de.ait.todo.repositories.KennelsRepository;
import de.ait.todo.services.KennelsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static de.ait.todo.dto.KennelDto.from;

@RequiredArgsConstructor
@Service
public class KennelsServiceImpl implements KennelsService {

    private final KennelsRepository kennelsRepository;
    @Override
    public KennelDto addKennel(NewKennelDto newKennel) {
        Kennel kennel = Kennel
                .builder()
                .name(newKennel.getName())
                .description(newKennel.getDescription())
                .webSite(newKennel.getWebSite())
                .country(newKennel.getCountry())
                .kennelCity(newKennel.getKennelCity())
                .postCode(newKennel.getPostCode())
                .address(newKennel.getAddress())
                .telephoneNumber(newKennel.getTelephoneNumber())
                .build();
       kennelsRepository.save(kennel);
       return from(kennel);
    }

    @Override
    public List<KennelDto> getKennels() {
        List<Kennel> kennels = kennelsRepository.findAll();
        return from(kennels);
    }

    @Override
    public KennelDto getKennel(Long kennelId) {
        Kennel kennel = getKennelOrThrow(kennelId);
        return from(kennel);
    }

    @Override
    public List<KennelDto> getKennelsByCity(String kennelCity) {
        List<Kennel> kennels = kennelsRepository.getKennelsByCity(kennelCity);
        if (kennels.isEmpty()){
            throw new RestException(HttpStatus.NOT_FOUND, "Kennels in <" + kennelCity + "> not found");
        }
        return from(kennels);
    }

    @Override
    public KennelDto deleteKennel(Long kennelId) {
        Kennel kennel = getKennelOrThrow(kennelId);
        kennelsRepository.deleteById(kennelId);
        return from(kennel);
    }

    @Override
    public KennelDto updateKennel(Long kennelID, UpdateKennelDto updateKennel) {
        Kennel kennel = getKennelOrThrow(kennelID);

        kennel.setName(updateKennel.getName());

        if(kennel.getDescription() != null){
            kennel.setDescription(updateKennel.getDescription());
        } else {
            kennel.setDescription(null);
        }

        if(kennel.getWebSite() != null){
            kennel.setWebSite(updateKennel.getWebSite());
        } else {
            kennel.setWebSite(null);
        }

        if (kennel.getCountry() != null){
            kennel.setCountry(updateKennel.getCountry());
        } else {
            kennel.setCountry(null);
        }

        if (kennel.getKennelCity() != null){
            kennel.setKennelCity(updateKennel.getKennelCity());
        }  else {
            kennel.setKennelCity(null);
        }

        if (kennel.getPostCode() != null){
            kennel.setPostCode(updateKennel.getPostCode());
        } else {
            kennel.setPostCode(null);
        }

        if (kennel.getAddress() != null){
            kennel.setAddress(updateKennel.getAddress());
        } else {
            kennel.setAddress(null);
        }

        if (kennel.getTelephoneNumber() != null){
            kennel.setTelephoneNumber(updateKennel.getTelephoneNumber());
        } else {
            kennel.setTelephoneNumber(null);
        }

        kennelsRepository.save(kennel);
        return from(kennel);
    }


    private Kennel getKennelOrThrow(Long kennelId) {
        return kennelsRepository.findById(kennelId)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Kennel with id <" + kennelId+ "> not found"));
    }
}
