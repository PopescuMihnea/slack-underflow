package com.slackunderflow.slackunderflow.mappers;

import com.slackunderflow.slackunderflow.dtos.requests.BodyRequest;
import com.slackunderflow.slackunderflow.dtos.responses.BodyResponse;
import com.slackunderflow.slackunderflow.models.BodyEntity;
import com.slackunderflow.slackunderflow.models.UserEntity;

public interface BodyEntityMapper<MODEL extends BodyEntity, RESP extends BodyResponse, REQ extends BodyRequest> {

    RESP fromEntityToResponse(MODEL model);

    MODEL fromRequestToEntity(REQ req, UserEntity user);


}
