package com.smarterfit.modules.classgroup.dto.request.classgroupuser;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class MemberClassGroupUserDTO {
    @NotNull(message = "ClassGroup ID is required")
    UUID classGroupId;

    @NotNull(message = "User ID is required")
    UUID userId;

    @NotNull(message = "Subscription ID is required")
    UUID subscriptionId;
}
