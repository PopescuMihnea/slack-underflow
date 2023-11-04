package com.slackunderflow.slackunderflow.dtos;


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

    private String email;


    private Integer points = 0;

    private BadgeEnum badge = BadgeEnum.SLAVE;
}
