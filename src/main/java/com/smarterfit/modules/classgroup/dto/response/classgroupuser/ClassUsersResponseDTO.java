package com.smarterfit.modules.classgroup.dto.response.classgroupuser;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ClassUsersResponseDTO {
    private UUID userId;
    private String name;
    private String email;
    private boolean isTeacher;
}
