package com.slackunderflow.slackunderflow.errors;

import com.slackunderflow.slackunderflow.enums.TopicEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class TopicNotFoundError extends RuntimeException {
    private final String body;

    public TopicNotFoundError(String message, TopicEnum topicEnum) {
        super(message);
        this.body = topicEnum.toString();
    }

    public TopicNotFoundError(String message, Long id) {
        super(message);
        this.body = id.toString();
    }

}
