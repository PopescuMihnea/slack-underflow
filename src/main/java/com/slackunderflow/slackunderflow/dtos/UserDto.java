package com.slackunderflow.slackunderflow.dtos;

import com.slackunderflow.slackunderflow.enums.BadgeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserDto {


    @NotEmpty
    private String username;

    @NotEmpty
    private String password;


}
