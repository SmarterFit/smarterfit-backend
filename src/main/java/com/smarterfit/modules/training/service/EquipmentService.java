package com.smarterfit.modules.training.service;


import com.smarterfit.modules.training.dto.request.EquipmentRequestDTO;
import com.smarterfit.modules.training.dto.response.EquipmentResponseDTO;
import com.smarterfit.modules.training.entity.Equipment;
import com.smarterfit.modules.training.mapper.EquipmentMapper;
import com.smarterfit.modules.training.repository.EquipmentRepository;
import com.smarterfit.modules.training.validation.EquipmentValidation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class EquipmentService {


    private final EquipmentRepository equipmentRepository;
    private final EquipmentValidation equipmentValidation;

    public EquipmentService(EquipmentRepository equipmentRepository, EquipmentValidation equipmentValidation) {
        this.equipmentRepository = equipmentRepository;
        this.equipmentValidation = equipmentValidation;
    }

    @Transactional
    public EquipmentResponseDTO createEquipment(EquipmentRequestDTO requestDTO) {
        equipmentValidation.existsEquipmentByName(requestDTO.getName());

        Equipment equipment = EquipmentMapper.toEntity(requestDTO);
        equipmentRepository.save(equipment);
        return EquipmentMapper.toResponse(equipment);
    }

    @Transactional(readOnly = true)
    public EquipmentResponseDTO getEquipmentById(UUID id) {
        Equipment equipment = equipmentValidation.validateEquipmentById(id);
        return EquipmentMapper.toResponse(equipment);
    }

    @Transactional(readOnly = true)
    public List<EquipmentResponseDTO> getAllEquipmentByName(String name) {
        return equipmentRepository.findAllByNameContaining(name).stream()
                .map(EquipmentMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<EquipmentResponseDTO> getAllEquipment() {
        return equipmentRepository.findAll().stream()
                .map(EquipmentMapper::toResponse)
                .toList();
    }

    @Transactional
    public EquipmentResponseDTO updateEquipmentById(UUID id, EquipmentRequestDTO requestDTO) {

        Equipment equipment = equipmentValidation.validateEquipmentById(id);

        equipment = EquipmentMapper.toEntity(requestDTO, equipment);
        equipmentRepository.save(equipment);
        return EquipmentMapper.toResponse(equipment);
    }

    @Transactional
    public void deleteEquipmentById(UUID id) {
        Equipment equipment = equipmentValidation.validateEquipmentById(id);
        equipmentRepository.delete(equipment);

    }


}
