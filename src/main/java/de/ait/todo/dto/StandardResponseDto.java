package de.ait.todo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 6/15/2023
 * spring-security-demo
 *
 * @author Marsel Sidikov (AIT TR)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "StandardResponseDto", description = "сведения о запросе")
public class StandardResponseDto {
    @Schema(description = "Текст сообщения")
    private String message;
}
