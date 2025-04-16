package com.smarterfit.util.mapper;

import com.smarterfit.dto.request.ClassGroupRequestDTO;
import com.smarterfit.dto.request.ModalityRequestDTO;
import com.smarterfit.dto.response.ClassGroupResponseDTO;
import com.smarterfit.dto.response.ModalityResponseDTO;
import com.smarterfit.model.ClassGroup;
import com.smarterfit.model.Modality;

public class ClassGroupMapper {

    public static ClassGroup toEntity(ClassGroupRequestDTO dto, ClassGroup classGroup) {
        if (dto == null) {
            return null;
        }

        classGroup.setName(dto.name());
        classGroup.setCapacity(dto.capacity());
        classGroup.setTotalPresent(dto.totalPresent());
        return classGroup;
    }

    public static ClassGroup toEntity(ClassGroupRequestDTO dto) {
        return toEntity(dto, new ClassGroup());
    }

    public static ClassGroupResponseDTO toResponse(ClassGroup classGroup){
        return new ClassGroupResponseDTO(
                classGroup.getId(),
                classGroup.getName(),
                classGroup.getCapacity(),
                classGroup.getTotalPresent()
        );
    }



}
