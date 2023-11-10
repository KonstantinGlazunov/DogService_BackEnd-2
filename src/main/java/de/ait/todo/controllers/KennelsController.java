package de.ait.todo.controllers;

import de.ait.todo.controllers.api.KennelsApi;
import de.ait.todo.dto.ClinicDto;
import de.ait.todo.dto.KennelDto;
import de.ait.todo.dto.NewKennelDto;
import de.ait.todo.services.KennelsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class KennelsController implements KennelsApi {

    private final KennelsService kennelsService;
    @Override
    public KennelDto addKennel(NewKennelDto newKennel) {
        return kennelsService.addKennel(newKennel);
    }

    @Override
    public List<KennelDto> getKennels() {
        return kennelsService.getKennels();
    }

    @Override
    public KennelDto getKennel(Long kennelId) {
        return kennelsService.getKennel(kennelId);
    }

    @Override
    public List<KennelDto> getKennelsByCity(String kennelCity) {
        return kennelsService.getKennelsByCity(kennelCity);
    }

    @Override
    public KennelDto deleteKennel(Long kennelId) {
        return kennelsService.deleteKennel(kennelId);
    }
}
