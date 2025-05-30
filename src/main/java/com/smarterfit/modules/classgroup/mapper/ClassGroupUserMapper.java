package com.smarterfit.modules.classgroup.mapper;

import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.modules.classgroup.dto.response.classgroupuser.ClassUsersResponseDTO;
import com.smarterfit.modules.classgroup.entity.ClassGroupUser;

public class ClassGroupUserMapper {

    private ClassGroupUserMapper() {
        // Private constructor to prevent instantiation
    }


    public static ClassUsersResponseDTO toResponse(ClassGroupUser classGroupUser) {
        if (classGroupUser == null) {
            throw new ResourceNotFoundException("ClassGroupUser not found.");
        }

        return ClassUsersResponseDTO.builder()
                .userId(classGroupUser.getUser().getId())
                .name(classGroupUser.getUser().getProfile().getFullName())
                .email(classGroupUser.getUser().getEmail())
                .isTeacher(classGroupUser.isTeacher())
                .build();
    }
}
