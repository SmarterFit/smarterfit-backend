package com.smarterfit.modules.classgroup.mapper;

import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.common.mapper.GenericMapper;
import com.smarterfit.modules.classgroup.dto.request.modality.CreateModalityRequestDTO;
import com.smarterfit.modules.classgroup.dto.response.ModalityResponseDTO;
import com.smarterfit.modules.classgroup.entity.Modality;

public class ModalityMapper {

    private ModalityMapper() {
        // Private constructor to prevent instantiation
    }

    public static Modality toEntity(CreateModalityRequestDTO dto) {
        return toEntity(dto, new Modality());
    }

    public static Modality toEntity(CreateModalityRequestDTO dto, Modality modality) {
        if (modality == null) {
            throw new ResourceNotFoundException("Modality not found");
        }

        modality = GenericMapper.map(dto, modality);

        return modality;
    }

    public static ModalityResponseDTO toResponse(Modality modality) {
        if (modality == null) {
            throw new ResourceNotFoundException("Modality not found");
        }

        return GenericMapper.map(modality, ModalityResponseDTO.class);
    }

}
