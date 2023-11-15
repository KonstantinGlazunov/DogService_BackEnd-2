package de.ait.todo.dto;

import de.ait.todo.models.User;
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
public class ProfileDto {

    private Long id;
    private String email;
    private String userName;
    private String role;

    public static ProfileDto from(User user) {
        return ProfileDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .userName(user.getUserName())
                .role(user.getRole().toString())
                .build();
    }
}
