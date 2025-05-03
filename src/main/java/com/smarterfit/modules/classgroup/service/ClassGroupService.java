package com.smarterfit.modules.classgroup.service;

import com.smarterfit.common.enums.RoleType;
import com.smarterfit.modules.classgroup.dto.request.classgroup.CreateClassGroupRequestDTO;
import com.smarterfit.modules.classgroup.dto.response.ClassGroupResponseDTO;
import com.smarterfit.modules.classgroup.entity.ClassGroup;
import com.smarterfit.modules.classgroup.entity.ClassGroupUser;
import com.smarterfit.modules.classgroup.entity.Modality;
import com.smarterfit.modules.classgroup.event.ClassGroupDeactivatedEvent;
import com.smarterfit.modules.classgroup.mapper.ClassGroupMapper;
import com.smarterfit.modules.classgroup.repository.ClassGroupPlanRepository;
import com.smarterfit.modules.classgroup.repository.ClassGroupRepository;
import com.smarterfit.modules.classgroup.repository.ClassGroupUserRepository;
import com.smarterfit.modules.classgroup.validation.ValidationFaced;
import com.smarterfit.modules.useraccess.entity.User;
import com.smarterfit.modules.useraccess.validation.RolesValidation;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Service
public class ClassGroupService {

    private final ClassGroupRepository classGroupRepository;
    private final ClassGroupUserRepository classGroupUserRepository;
    private final ClassGroupPlanRepository classGroupPlanRepository;
    private final ValidationFaced validationFaced;
    private final ApplicationEventPublisher publisher;


    public ClassGroupService(ClassGroupRepository classGroupRepository,
            ClassGroupUserRepository classGroupUserRepository,
            ClassGroupPlanRepository classGroupPlanRepository,
            ValidationFaced validationFaced,
                             ApplicationEventPublisher publisher) {

        this.classGroupRepository = classGroupRepository;
        this.classGroupUserRepository = classGroupUserRepository;
        this.classGroupPlanRepository = classGroupPlanRepository;
        this.validationFaced = validationFaced;
        this.publisher = publisher;

    }

    @Transactional
    public ClassGroupResponseDTO createClassGroup(CreateClassGroupRequestDTO requestDTO) {
        User user = validationFaced.userValidation.validateUserById(requestDTO.getUserCreatorId());
        RolesValidation.validateUserRole(RoleType.EMPLOYEE, user.getRoles());

        validationFaced.classGroupValidation.validateClassGroupDates(requestDTO.getStartDate(), requestDTO.getEndDate());

        validationFaced.classGroupValidation.validateClassGroupExists(requestDTO.getTitle(), null);
        Modality modality = validationFaced.modalityValidation.validateModalityById(requestDTO.getModalityId());

        ClassGroup classGroup = ClassGroupMapper.toEntity(requestDTO, modality, user);
        classGroupRepository.save(classGroup);

        return ClassGroupMapper.toResponse(classGroup, user.getProfile().getFullName());
    }

    @Transactional(readOnly = true)
    public ClassGroupResponseDTO getClassGroupById(UUID id) {
        ClassGroup classGroup = validationFaced.classGroupValidation.validateClassGroupById(id);
        return ClassGroupMapper.toResponse(classGroup, classGroup.getCreatedByUser().getProfile().getFullName());
    }

    @Transactional(readOnly = true)
    // TODO: incluir filtros por: modalidade, tipo, data, disponibilidade
    public List<ClassGroupResponseDTO> getAllClassGroups() {
        List<ClassGroup> classGroups = classGroupRepository.findAllByIsActiveTrue();
        return classGroups.stream()
                .map(classGroup -> ClassGroupMapper.toResponse(classGroup,
                        classGroup.getCreatedByUser().getProfile().getFullName()))
                .toList();
    }

    @Transactional
    public ClassGroupResponseDTO updateClassGroupById(UUID classGroupId, CreateClassGroupRequestDTO requestDTO) {
        validationFaced.classGroupValidation.validateClassGroupDates(requestDTO.getStartDate(),
                requestDTO.getEndDate());
        ClassGroup classGroup = validationFaced.classGroupValidation.validateClassGroupById(classGroupId);

        Modality modality = validationFaced.modalityValidation.validateModalityById(requestDTO.getModalityId());

        classGroup = ClassGroupMapper.toEntity(requestDTO, modality, classGroup.getCreatedByUser(), classGroup);
        classGroupRepository.save(classGroup);

        return ClassGroupMapper.toResponse(classGroup, classGroup.getCreatedByUser().getProfile().getFullName());
    }

    @Transactional
    public void deleteClassGroupById(UUID id) {
        ClassGroup classGroup = validationFaced.classGroupValidation.validateClassGroupById(id);
        classGroup.setActive(false);
        publisher.publishEvent(new ClassGroupDeactivatedEvent(classGroup));
        classGroupRepository.save(classGroup);
    }


    private void incrementGroupMembers(ClassGroup classGroup) {
        classGroup.setTotalMembers(classGroup.getTotalMembers() + 1);
    }

    private void decrementGroupMembers(ClassGroup classGroup) {
        classGroup.setTotalMembers(classGroup.getTotalMembers() - 1);
    }

    private void saveUserToGroup(ClassGroup classGroup, User user) {
        ClassGroupUser classGroupUser = new ClassGroupUser();
        classGroupUser.setClassGroup(classGroup);
        classGroupUser.setUser(user);
        classGroupUserRepository.save(classGroupUser);
    }
}
