package com.smarterfit.modules.classgroup.service;

import com.smarterfit.modules.classgroup.dto.request.modality.CreateModalityRequestDTO;
import com.smarterfit.modules.classgroup.dto.response.ModalityResponseDTO;
import com.smarterfit.modules.classgroup.entity.Modality;
import com.smarterfit.modules.classgroup.mapper.ModalityMapper;
import com.smarterfit.modules.classgroup.repository.ModalityRepository;
import com.smarterfit.modules.classgroup.validation.ClassGroupValidation;
import com.smarterfit.modules.classgroup.validation.ModalityValidation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ModalityService {

    private final ModalityRepository modalityRepository;
    private final ModalityValidation modalityValidation;
    private final ClassGroupValidation classGroupValidation;

    public ModalityService(ModalityRepository modalityRepository, ModalityValidation modalityValidation,
                           ClassGroupValidation classGroupValidation) {
        this.modalityRepository = modalityRepository;
        this.modalityValidation = modalityValidation;
        this.classGroupValidation = classGroupValidation;
    }

    @Transactional
    public ModalityResponseDTO createModality(CreateModalityRequestDTO requestDTO) {
        modalityValidation.existsModalityByName(requestDTO.getName());

        Modality modality = ModalityMapper.toEntity(requestDTO);
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
    public ModalityResponseDTO updateModalityById(UUID id, CreateModalityRequestDTO requestDTO) {

        Modality modality = modalityValidation.validateModalityById(id);

        modality = ModalityMapper.toEntity(requestDTO, modality);
        modalityRepository.save(modality);
        return ModalityMapper.toResponse(modality);
    }

    @Transactional
    public void deleteModalityById(UUID id) {
        Modality modality = modalityValidation.validateModalityById(id);
        classGroupValidation.validateModalityNotInUse(id);
        modalityRepository.delete(modality);

    }
}
