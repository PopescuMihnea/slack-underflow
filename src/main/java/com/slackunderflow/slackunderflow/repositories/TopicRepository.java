package com.slackunderflow.slackunderflow.repositories;

import com.slackunderflow.slackunderflow.enums.TopicEnum;
import com.slackunderflow.slackunderflow.models.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface TopicRepository extends JpaRepository<Topic, TopicEnum> {

}
