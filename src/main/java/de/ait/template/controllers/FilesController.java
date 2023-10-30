package de.ait.template.controllers;

import de.ait.template.controllers.api.FilesApi;
import de.ait.template.dto.StandardResponseDto;
import de.ait.template.services.FilesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class FilesController implements FilesApi {

    private final FilesService filesService;

    public StandardResponseDto upload(MultipartFile file) {
        return filesService.upload(file);
    }
}
