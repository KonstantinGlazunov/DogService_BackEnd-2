package de.ait.todo.services;

import de.ait.todo.dto.ClinicDto;
import de.ait.todo.dto.KennelDto;
import de.ait.todo.dto.NewKennelDto;
import de.ait.todo.dto.UpdateKennelDto;

import java.util.List;

public interface KennelsService {
    KennelDto addKennel(NewKennelDto newKennel);

    List<KennelDto> getKennels();

    KennelDto getKennel(Long kennelId);

    List<KennelDto> getKennelsByCity(String kennelCity);

    KennelDto deleteKennel(Long kennelId);

    KennelDto updateKennel(Long kennelID, UpdateKennelDto updateKennel);
}
