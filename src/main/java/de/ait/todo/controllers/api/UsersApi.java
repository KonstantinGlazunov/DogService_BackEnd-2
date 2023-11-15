package de.ait.todo.controllers.api;

import de.ait.todo.dto.ProfileDto;
import de.ait.todo.dto.StandardResponseDto;
import de.ait.todo.dto.TasksPage;
import de.ait.todo.dto.UserDto;
import de.ait.todo.models.User;
import de.ait.todo.security.details.AuthenticatedUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Tags(value = {
        @Tag(name = "Users")
})
@RequestMapping("/api/users")
public interface UsersApi {

    @Operation(summary = "Получение своего профиля", description = "Доступно только аутентифицированному пользователю")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информацию о профиле",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProfileDto.class))
                    }
            ),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "StandardResponseDto"))
                    }
            ),
            @ApiResponse(responseCode = "403", description = "Запрещено",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "StandardResponseDto"))
                    }
            )
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/my/profile")
    ResponseEntity<ProfileDto> getProfile(@Parameter(hidden = true)
                                          @AuthenticationPrincipal AuthenticatedUser currentUser);

    @Operation(summary = "Получение списка своих задач", description = "Доступно только пользователю")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список задач",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TasksPage.class))
                    }
            ),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "StandardResponseDto"))
                    }
            ),
            @ApiResponse(responseCode = "403", description = "Запрещено",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "StandardResponseDto"))
                    }
            )
    })
    @GetMapping("/my/tasks")
    ResponseEntity<TasksPage> getMyTasks(@Parameter(hidden = true)
                                          @AuthenticationPrincipal AuthenticatedUser currentUser);
    @GetMapping("/confirm/{confirm-code}")
//    String getConfirmation(@PathVariable("confirm-code") String confirmCode);
    ResponseEntity<ProfileDto> getConfirmation( @PathVariable("confirm-code") String confirmCode);
}