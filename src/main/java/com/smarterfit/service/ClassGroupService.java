package com.smarterfit.service;

import com.smarterfit.dto.request.ClassGroupRequestDTO;
import com.smarterfit.dto.response.ClassGroupResponseDTO;
import com.smarterfit.model.ClassGroup;
import com.smarterfit.repository.ClassGroupRepository;
import com.smarterfit.util.mapper.ClassGroupMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ClassGroupService {

    private final ClassGroupRepository classGroupRepository;

    public ClassGroupService(ClassGroupRepository classGroupRepository) {
        this.classGroupRepository = classGroupRepository;
    }

    @Transactional
    public ClassGroupResponseDTO createClassGroup(ClassGroupRequestDTO classGroupRequest) {
        ClassGroup classGroup = ClassGroupMapper.toEntity(classGroupRequest);
        classGroupRepository.save(classGroup);
        return ClassGroupMapper.toResponse(classGroup);
    }

    @Transactional(readOnly = true)
    public ClassGroupResponseDTO getClassGroupById(UUID id) {
        ClassGroup classGroup = findById(id);
        return ClassGroupMapper.toResponse(classGroup);
    }

    @Transactional
    public ClassGroupResponseDTO updateClassGroupById(UUID id, ClassGroupRequestDTO classGroupRequest) {
        ClassGroup classGroup = findById(id);

        classGroup = ClassGroupMapper.toEntity(classGroupRequest, classGroup);
        classGroupRepository.save(classGroup);
        return ClassGroupMapper.toResponse(classGroup);
    }

    @Transactional
    public void deleteClassGroupById(UUID id) {
        ClassGroup classGroup = findById(id);
        classGroupRepository.delete(classGroup);
    }

    private ClassGroup findById(UUID id) {
        return classGroupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Class group not found"));
    }
}
