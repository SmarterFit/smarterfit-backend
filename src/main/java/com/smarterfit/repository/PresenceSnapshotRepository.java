package com.smarterfit.repository;

import com.smarterfit.model.PresenceSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PresenceSnapshotRepository extends JpaRepository<PresenceSnapshot, UUID> {
}
