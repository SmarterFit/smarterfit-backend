package com.smarterfit.service;

import com.smarterfit.dto.request.ModalityRequestDTO;
import com.smarterfit.dto.response.ModalityResponseDTO;
import com.smarterfit.model.Modality;
import com.smarterfit.repository.ModalityRepository;
import com.smarterfit.util.mapper.ModalityMapper;
import com.smarterfit.util.validation.ModalityValidation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ModalityService {

    private final ModalityRepository modalityRepository;
    private final ModalityValidation modalityValidation;

    public ModalityService(ModalityRepository modalityRepository, ModalityValidation modalityValidation) {
        this.modalityRepository = modalityRepository;
        this.modalityValidation = modalityValidation;
    }

    @Transactional
    public ModalityResponseDTO createModality(ModalityRequestDTO modalityRequest) {
        Modality modality = ModalityMapper.toEntity(modalityRequest);
        modalityRepository.save(modality);
        return ModalityMapper.toResponse(modality);
    }

    @Transactional(readOnly = true)
    public ModalityResponseDTO getModalityById(UUID id) {
        Modality modality = modalityValidation.validateModalityById(id);
        return ModalityMapper.toResponse(modality);
    }

    @Transactional
    public ModalityResponseDTO updateModalityById(UUID id, ModalityRequestDTO modalityRequest) {
        Modality modality = modalityValidation.validateModalityById(id);

        modality = ModalityMapper.toEntity(modalityRequest, modality);
        modalityRepository.save(modality);
        return ModalityMapper.toResponse(modality);
    }

    @Transactional
    public void deleteModalityById(UUID id) {
        Modality modality = modalityValidation.validateModalityById(id);
        modalityRepository.delete(modality);

    }


}
