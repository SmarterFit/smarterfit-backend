package com.smarterfit.service;

import com.smarterfit.dto.request.ClassGroupRequestDTO;
import com.smarterfit.dto.response.ClassGroupResponseDTO;
import com.smarterfit.model.ClassGroup;
import com.smarterfit.model.Modality;
import com.smarterfit.model.Profile;
import com.smarterfit.model.User;
import com.smarterfit.repository.ClassGroupRepository;
import com.smarterfit.util.mapper.ClassGroupMapper;
import com.smarterfit.util.validation.ClassGroupValidation;
import com.smarterfit.util.validation.ModalityValidation;
import com.smarterfit.util.validation.ProfileValidation;
import com.smarterfit.util.validation.UserValidation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ClassGroupService {

    private final ClassGroupRepository classGroupRepository;
    private final ClassGroupValidation classGroupValidation;
    private final ModalityValidation modalityValidation;
    private final UserValidation userValidation;


    public ClassGroupService(ClassGroupRepository classGroupRepository, ClassGroupValidation classGroupValidation,
                             ModalityValidation modalityValidation, UserValidation userValidation) {
        this.classGroupRepository = classGroupRepository;
        this.classGroupValidation = classGroupValidation;
        this.modalityValidation = modalityValidation;
        this.userValidation = userValidation;
    }

    @Transactional
    public ClassGroupResponseDTO createClassGroup(ClassGroupRequestDTO classGroupRequest) {
        classGroupValidation.validateClassGroupAvailability(classGroupRequest.name(), null);
        Modality modality = modalityValidation.validateModalityById(classGroupRequest.modalityId());
        User user = userValidation.validateUserById(classGroupRequest.userId());

        ClassGroup classGroup = ClassGroupMapper.toEntity(classGroupRequest, modality, user);
        classGroupRepository.save(classGroup);

        return ClassGroupMapper.toResponse(classGroup, user.getProfile().getFullName());
    }

    @Transactional(readOnly = true)
    public ClassGroupResponseDTO getClassGroupById(UUID id) {
        ClassGroup classGroup = classGroupValidation.validateClassGroupById(id);
        User user = userValidation.validateUserById(classGroup.getUser().getId());

        return ClassGroupMapper.toResponse(classGroup, user.getProfile().getFullName());
    }

    @Transactional
    public ClassGroupResponseDTO updateClassGroupById(UUID id, ClassGroupRequestDTO classGroupRequest) {
        ClassGroup classGroup = classGroupValidation.validateClassGroupById(id);
        User user = userValidation.validateUserById(classGroup.getUser().getId());

        Modality modality = modalityValidation.validateModalityById(classGroupRequest.modalityId());

        classGroup = ClassGroupMapper.toEntity(classGroupRequest, classGroup, modality, user);
        classGroupRepository.save(classGroup);
        return ClassGroupMapper.toResponse(classGroup, user.getProfile().getFullName());
    }

    @Transactional
    public void deleteClassGroupById(UUID id) {
        ClassGroup classGroup = classGroupValidation.validateClassGroupById(id);
        classGroupRepository.delete(classGroup);
    }
}
