package com.slackunderflow.slackunderflow.dtos;

import com.slackunderflow.slackunderflow.enums.BadgeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserDto {

    public interface RegisteringUser {
    }

    @NotEmpty(message = "You need to enter a username")
    @NotEmpty(message = "You need to enter a username", groups = {RegisteringUser.class})
    private String username;

    @NotEmpty(message = "You need to enter a password")
    @Size(min = 5, message = "Password must have at least 5 chr", groups = {RegisteringUser.class})
    @Size(max = 50, message = "Invalid password", groups = {RegisteringUser.class})
    private String password;


}
