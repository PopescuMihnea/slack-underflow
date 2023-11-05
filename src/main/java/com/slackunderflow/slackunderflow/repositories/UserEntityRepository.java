package com.slackunderflow.slackunderflow.repositories;

import com.slackunderflow.slackunderflow.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserEntityRepository extends JpaRepository<UserEntity, UUID> {
    public Boolean existsByUsername(String username);

    public Optional<UserEntity> findByUsername(String email);


}
