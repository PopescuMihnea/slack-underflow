package com.slackunderflow.slackunderflow.dtos;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.slackunderflow.slackunderflow.models.Topic;
import lombok.*;

import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
public class AuthDto {
    UserLoginDto userLoginDto;
}
