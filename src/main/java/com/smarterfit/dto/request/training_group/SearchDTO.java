package com.smarterfit.dto.request.training_group;

import java.util.List;
import java.util.UUID;

import com.smarterfit.enums.GroupType;

public record SearchDTO(
            String nameTerm,
            UUID userId,
            List<GroupType> groupTypes,
            Boolean includeEnded,
            Boolean includeNotStarted) {
}
