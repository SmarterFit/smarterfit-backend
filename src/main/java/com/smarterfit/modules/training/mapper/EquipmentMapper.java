package com.smarterfit.modules.training.mapper;

import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.common.mapper.GenericMapper;
import com.smarterfit.modules.training.dto.request.EquipmentRequestDTO;
import com.smarterfit.modules.training.dto.response.EquipmentResponseDTO;
import com.smarterfit.modules.training.entity.Equipment;


public class EquipmentMapper {

    private EquipmentMapper() {
        // Private constructor to prevent instantiation
    }

    public static Equipment toEntity(EquipmentRequestDTO dto) {
        return toEntity(dto, new Equipment());
    }

    public static Equipment toEntity(EquipmentRequestDTO dto, Equipment equipment) {
        if (equipment == null) {
            throw new ResourceNotFoundException("Equipment not found");
        }

        equipment = GenericMapper.map(dto, equipment);

        return equipment;
    }

    public static EquipmentResponseDTO toResponse(Equipment equipment) {
        if (equipment == null) {
            throw new ResourceNotFoundException("Equipment not found");
        }

        return GenericMapper.map(equipment, EquipmentResponseDTO.class);
    }

}
