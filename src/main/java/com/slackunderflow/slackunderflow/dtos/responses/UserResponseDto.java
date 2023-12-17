package com.slackunderflow.slackunderflow.dtos.responses;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.slackunderflow.slackunderflow.enums.BadgeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseDto {

    private String username;
    
    private Integer points = 0;

    private BadgeEnum badge = BadgeEnum.SLAVE;

    private String jwt;
}
