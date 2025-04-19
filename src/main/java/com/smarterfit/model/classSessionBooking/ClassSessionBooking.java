package com.smarterfit.model.classSessionBooking;

import com.smarterfit.enums.Status;
import com.smarterfit.model.ClassSession;
import com.smarterfit.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"user", "classSession"})
@IdClass(ClassSessionBookingId.class)
@Entity
@Table(name = "SF_CLASS_SESSION_BOOKING")
public class ClassSessionBooking {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_session_id", nullable = false)
    private ClassSession classSession;

    @Column(nullable = false)
    private LocalDateTime bookingTime;

    @Column(nullable = false)
    private Status status;
}
