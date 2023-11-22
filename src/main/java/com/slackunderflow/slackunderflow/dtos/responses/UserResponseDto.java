package com.slackunderflow.slackunderflow.dtos.responses;


import com.slackunderflow.slackunderflow.enums.BadgeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {

    private String username;


    private Integer points = 0;

    private BadgeEnum badge = BadgeEnum.SLAVE;

    private String jwt;
}
