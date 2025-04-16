package com.smarterfit.util.mapper;

import com.smarterfit.dto.request.ModalityRequestDTO;
import com.smarterfit.dto.response.ModalityResponseDTO;
import com.smarterfit.model.Modality;

public class ModalityMapper {

    public static Modality toEntity(ModalityRequestDTO dto, Modality modality) {
        if (dto == null) {
            return null;
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
