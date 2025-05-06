package com.smarterfit.modules.classgroup.dto.request.classgroupplan;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CreateClassGroupPlanDTO {

    @NotNull(message = "ClassGroup ID is required")
    UUID classGroupId;

    @NotNull(message = "Plan ID is required")
    UUID planId;


}
