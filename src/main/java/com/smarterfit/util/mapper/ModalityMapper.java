package com.smarterfit.util.mapper;

import com.smarterfit.dto.request.ModalityRequestDTO;
import com.smarterfit.dto.response.ModalityResponseDTO;
import com.smarterfit.exception.ResourceNotFoundException;
import com.smarterfit.model.Modality;

public class ModalityMapper {

    private ModalityMapper() {
        // Private constructor to prevent instantiation
    }

    public static Modality toEntity(ModalityRequestDTO dto, Modality modality) {
        if (modality == null) {
            throw new ResourceNotFoundException("Modality not found");
        }

        modality.setName(dto.name());
        return modality;
    }

    public static Modality toEntity(ModalityRequestDTO dto) {
        return toEntity(dto, new Modality());
    }

    public static ModalityResponseDTO toResponse(Modality modality){
        return new ModalityResponseDTO(
                modality.getId(),
                modality.getName()
        );
    }



}
