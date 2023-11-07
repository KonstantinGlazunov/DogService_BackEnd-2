package de.ait.todo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 8/22/2023
 * backend-demo
 *
 * @author Marsel Sidikov (AIT TR)
 */
@Data
public class NewTaskDto {

    @NotBlank
    @NotNull
    @Schema(description = "Название задачи", example = "Task")
    private String name;

    @NotBlank
    @NotNull
    @Schema(description = "Описание задачи", example = "Description of Task ")
    private String description;
}
