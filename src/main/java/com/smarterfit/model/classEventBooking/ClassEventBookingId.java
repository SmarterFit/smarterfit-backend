package com.smarterfit.model.classEventBooking;


import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Embeddable
public class ClassEventBookingId implements Serializable {

    private UUID user;
    private UUID classEvent;

    public ClassEventBookingId() {
    }

    public ClassEventBookingId(UUID user, UUID classEvent) {
        this.user = user;
        this.classEvent = classEvent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassEventBookingId that = (ClassEventBookingId) o;
        return Objects.equals(user, that.user) && Objects.equals(classEvent, that.classEvent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, classEvent);
    }
}
