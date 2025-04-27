package com.smarterfit.modules.classgroup.entity.id;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = { "user", "classEvent" })
@Embeddable
public class ClassEventBookingId implements Serializable {
    private UUID user;
    private UUID classEvent;
}
