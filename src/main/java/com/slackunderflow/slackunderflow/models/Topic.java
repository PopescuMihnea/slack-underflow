package com.slackunderflow.slackunderflow.models;

import com.slackunderflow.slackunderflow.enums.TopicEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Topic {

    @Id
    @Column(name = "topic_id")
    private TopicEnum topic;

    @ManyToMany(mappedBy = "topics")
    private Set<Question> questions;
}
