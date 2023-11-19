package de.ait.todo.controllers.api;

import de.ait.todo.dto.*;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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

    @GetMapping("/confirm/{confirm-code}")

    ResponseEntity<ProfileDto> getConfirmation( @PathVariable("confirm-code") String confirmCode);

    @Operation(summary = "Получение списка всех юзеров (DogSitters, DogLovers)", description = "Список юзеров получен")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Request successfully processed",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))),
    })
    @GetMapping("/allUsers")
    List<UserDto> getUsers();


//    @Operation(summary = "Удаление юзера по ID", description = "Юзер успешно удалён")
//    @ApiResponses({
//            @ApiResponse(responseCode = "200",
//                    description = "Request successfully processed",
//                    content = @Content(mediaType = "application/json",
//                            schema = @Schema(implementation = UserDto.class))),
//    })
//    @DeleteMapping("/{user-id}")
//    UserDto deleteUser(@Parameter(description = "user ID", example = "6")
//                           @PathVariable("user-id") Long userId);

}

