package com.smarterfit.modules.classgroup.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smarterfit.common.enums.AttendanceStatus;
import com.smarterfit.common.enums.BookingStatus;
import com.smarterfit.modules.classgroup.entity.id.ClassEventBookingId;
import com.smarterfit.modules.useraccess.entity.User;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = { "user", "classEvent" })
@IdClass(ClassEventBookingId.class)
@Entity
@Table(name = "SF_CLASS_SESSION_BOOKING")
public class ClassEventBooking {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_event_id", nullable = false)
    private ClassEvent classEvent;

    @Column(nullable = false)
    private BookingStatus bookingStatus;

    @Column(name = "attendance_status")
    private AttendanceStatus attendanceStatus;

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
