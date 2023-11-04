package com.slackunderflow.slackunderflow.dtos;

import com.slackunderflow.slackunderflow.enums.BadgeEnum;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserDto {

    private String email;


    private Integer points = 0;

    private BadgeEnum badge = BadgeEnum.SLAVE;
    private String password;


}
