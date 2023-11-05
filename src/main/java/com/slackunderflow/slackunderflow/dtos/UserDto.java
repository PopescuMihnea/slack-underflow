package com.slackunderflow.slackunderflow.dtos;

import com.slackunderflow.slackunderflow.enums.BadgeEnum;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserDto {

    private String username;


    private String password;


}
