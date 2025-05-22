package com.smarterfit.modules.training.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smarterfit.common.enums.ExperienceLevel;
import com.smarterfit.common.enums.Goal;
import com.smarterfit.modules.useraccess.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "SF_TRAINING_GOAL")
public class TrainingGoal {

    @Id
    private UUID id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    @Column(name = "goal", nullable = false, length = 100)
    private Goal goal;

    @Column(name = "experience_level", nullable = false, length = 50)
    private ExperienceLevel experienceLevel;

    @Column(name = "weekly_frequency", nullable = false)
    private Integer weeklyFrequency;

    @Column(name = "dt_created_at", nullable = false, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @Column(name = "dt_updated_at", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    @PrePersist
    public void onPrePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onPreUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
