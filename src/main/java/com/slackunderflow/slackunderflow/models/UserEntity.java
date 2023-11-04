package com.slackunderflow.slackunderflow.models;

import com.slackunderflow.slackunderflow.enums.BadgeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;


import java.util.UUID;

@Entity
@Table(name = "Utilizator")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private UUID id;

    @NonNull
    private String password;

    @NonNull
    @Column(unique = true)
    private String email;

    private Integer points = 0;

    private BadgeEnum badge = BadgeEnum.SLAVE;
}
