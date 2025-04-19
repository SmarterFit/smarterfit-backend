package com.smarterfit.model.classSessionBooking;


import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class ClassSessionBookingId implements Serializable {

    private UUID user;
    private UUID classSession;

    public ClassSessionBookingId() {
    }

    public ClassSessionBookingId(UUID user, UUID classSession) {
        this.user = user;
        this.classSession = classSession;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassSessionBookingId that = (ClassSessionBookingId) o;
        return Objects.equals(user, that.user) && Objects.equals(classSession, that.classSession);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, classSession);
    }
}
