package com.smarterfit.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "SF_CLASS_GROUP_SCHEDULE")
public class ClassGroupSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_group_id", nullable = false)
    private ClassGroup classGroup;

    /**
     * Days of the week when this class occurs.
     * Values range from 2 to 8, where:
     * 2 = Monday, 3 = Tuesday, ..., 7 = Saturday, 8 = Sunday.
     * Stored as a collection of integers to allow multiple days (e.g., [2, 4, 6] = Mon, Wed, Fri).
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "SF_CLASS_GROUP_SCHEDULE_DAYS", joinColumns = @JoinColumn(name = "schedule_id"))
    @Column(name = "weekday", nullable = false)
    private Set<Integer> daysOfWeek;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;


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
