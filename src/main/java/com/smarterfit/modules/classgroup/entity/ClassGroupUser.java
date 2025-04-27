package com.smarterfit.modules.classgroup.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smarterfit.modules.classgroup.entity.id.ClassGroupUserId;
import com.smarterfit.modules.useraccess.entity.User;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of = {"classGroup", "user"})
@AllArgsConstructor
@NoArgsConstructor
@IdClass(ClassGroupUserId.class)

@Entity
@Table(name = "SF_CLASS_GROUP_USER")
public class ClassGroupUser{
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_group_id", nullable = false)
    private ClassGroup classGroup;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

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
