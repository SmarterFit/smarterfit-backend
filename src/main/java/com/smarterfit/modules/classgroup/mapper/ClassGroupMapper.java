package com.smarterfit.modules.classgroup.mapper;

import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.common.mapper.GenericMapper;
import com.smarterfit.modules.classgroup.dto.request.classgroup.CreateClassGroupRequestDTO;
import com.smarterfit.modules.classgroup.dto.response.ClassGroupResponseDTO;
import com.smarterfit.modules.classgroup.entity.ClassGroup;
import com.smarterfit.modules.classgroup.entity.Modality;
import com.smarterfit.modules.useraccess.entity.User;

public class ClassGroupMapper {
    private ClassGroupMapper() {
        // Private constructor to prevent instantiation
    }

    public static ClassGroup toEntity(CreateClassGroupRequestDTO dto, Modality modality, User creator) {
        return toEntity(dto, modality, creator, new ClassGroup());
    }

    public static ClassGroup toEntity(CreateClassGroupRequestDTO dto, Modality modality,
            User creator, ClassGroup classGroup) {
        if (classGroup == null) {
            throw new ResourceNotFoundException("Class Group not found.");
        }
        if (modality == null) {
            throw new ResourceNotFoundException("Modality not found.");
        }
        if (creator == null) {
            throw new ResourceNotFoundException("Creator not found.");
        }

        classGroup = GenericMapper.map(dto, classGroup);
        classGroup.setModality(modality);
        classGroup.setCreatedByUser(creator);

        return classGroup;
    }

    public static ClassGroupResponseDTO toResponse(ClassGroup classGroup, String nameCreator) {
        if (classGroup == null) {
            throw new ResourceNotFoundException("Class Group not found.");
        }

        ClassGroupResponseDTO response = GenericMapper.map(classGroup, ClassGroupResponseDTO.class);
        response = response.toBuilder().modalityDTO(ModalityMapper.toResponse(classGroup.getModality()))
                .nameCreator(nameCreator).build();

        return response;
    }
}
