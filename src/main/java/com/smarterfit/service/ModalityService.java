package com.smarterfit.service;

import com.smarterfit.dto.request.ModalityRequestDTO;
import com.smarterfit.dto.response.ModalityResponseDTO;
import com.smarterfit.model.Modality;
import com.smarterfit.repository.ModalityRepository;
import com.smarterfit.util.mapper.ModalityMapper;
import com.smarterfit.util.validation.entity.ModalityValidation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
        modalityValidation.existsModalityByName(modalityRequest.name());

        Modality modality = ModalityMapper.toEntity(modalityRequest);
        modalityRepository.save(modality);
        return ModalityMapper.toResponse(modality);
    }

    @Transactional(readOnly = true)
    public ModalityResponseDTO getModalityById(UUID id) {
        Modality modality = modalityValidation.validateModalityById(id);
        return ModalityMapper.toResponse(modality);
    }

    @Transactional(readOnly = true)
    public List<ModalityResponseDTO> getAllModalityByName(String name) {
        return modalityRepository.findAllByNameContaining(name).stream()
                .map(ModalityMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ModalityResponseDTO> getAllModality() {
        return modalityRepository.findAll().stream()
                .map(ModalityMapper::toResponse)
                .toList();
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
