package com.smarterfit.service;

import com.smarterfit.dto.request.ClassGroupRequestDTO;
import com.smarterfit.dto.response.ClassGroupResponseDTO;
import com.smarterfit.dto.response.UserResponseDTO;
import com.smarterfit.enums.GroupType;
import com.smarterfit.exception.BusinessException;
import com.smarterfit.exception.ResourceNotFoundException;
import com.smarterfit.model.ClassGroup;
import com.smarterfit.model.Modality;
import com.smarterfit.model.Plan;
import com.smarterfit.model.User;
import com.smarterfit.model.classGroupPlan.ClassGroupPlan;
import com.smarterfit.model.classGroupUser.ClassGroupUser;
import com.smarterfit.repository.ClassGroupPlanRepository;
import com.smarterfit.repository.ClassGroupRepository;
import com.smarterfit.repository.ClassGroupUserRepository;
import com.smarterfit.util.mapper.ClassGroupMapper;
import com.smarterfit.util.mapper.UserMapper;
import com.smarterfit.util.validation.*;
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


    public ClassGroupService(ClassGroupRepository classGroupRepository,
                             ClassGroupUserRepository classGroupUserRepository,
                             ClassGroupPlanRepository classGroupPlanRepository,
                             ValidationFaced validationFaced) {
        this.classGroupRepository = classGroupRepository;
        this.classGroupUserRepository = classGroupUserRepository;
        this.classGroupPlanRepository = classGroupPlanRepository;
        this.validationFaced = validationFaced;

    }

    @Transactional
    public ClassGroupResponseDTO createClassGroup(ClassGroupRequestDTO classGroupRequest) {

        validationFaced.classGroupValidation.validateClassGroupExists(classGroupRequest.title(), null);
        Modality modality = validationFaced.modalityValidation.validateModalityById(classGroupRequest.modalityId());
        User user = validationFaced.userValidation.validateUserById(classGroupRequest.userCreatorId());

        // todo: Validar permissão do usuario

        ClassGroup classGroup = ClassGroupMapper.toEntity(classGroupRequest, modality, user);
        classGroup.setTotalMembers(0);
        classGroupRepository.save(classGroup);

        return ClassGroupMapper.toResponse(classGroup, user.getProfile().getFullName());
    }

    @Transactional(readOnly = true)
    public ClassGroupResponseDTO getClassGroupById(UUID id) {
        ClassGroup classGroup = validationFaced.classGroupValidation.validateClassGroupById(id);
        return ClassGroupMapper.toResponse(classGroup, classGroup.getCreatedByUser().getProfile().getFullName());
    }

    @Transactional(readOnly = true)
    // todo: incluir filtros por: modalidade, tipo, data, disponibilidade
    public List<ClassGroupResponseDTO> getAllClassGroups() {
        List<ClassGroup> classGroups = classGroupRepository.findAll();
        return classGroups.stream()
                .map(classGroup -> ClassGroupMapper.toResponse(classGroup, classGroup.getCreatedByUser().getProfile().getFullName()))
                .toList();

    }



    @Transactional
    public ClassGroupResponseDTO updateClassGroupById(UUID id, ClassGroupRequestDTO classGroupRequest) {
        ClassGroup classGroup = validationFaced.classGroupValidation.validateClassGroupById(id);

        Modality modality = validationFaced.modalityValidation.validateModalityById(classGroupRequest.modalityId());

        classGroup = ClassGroupMapper.toEntity(classGroupRequest, classGroup, modality, classGroup.getCreatedByUser());
        classGroupRepository.save(classGroup);
        return ClassGroupMapper.toResponse(classGroup, classGroup.getCreatedByUser().getProfile().getFullName());
    }

    @Transactional
    public void addPlanToClassGroup(UUID planId, UUID classGroupId) {
        validationFaced.classGroupPlanValidation.validateClassGroupPlanExists(planId, classGroupId);
        ClassGroup classGroup = validationFaced.classGroupValidation.validateClassGroupById(classGroupId);
        Plan plan = validationFaced.planValidation.findPlanById(planId);

        ClassGroupPlan classGroupPlan = new ClassGroupPlan(classGroup, plan);

        classGroupPlanRepository.save(classGroupPlan);
    }

    @Transactional
    public void addUserToClassGroup(UUID classGroupId, UUID userId) {
        validationFaced.classGroupUserValidation.validateClassGroupUserExists(classGroupId, userId);
        ClassGroup classGroup = validationFaced.classGroupValidation.validateClassGroupById(classGroupId);
        User user = validationFaced.userValidation.validateUserById(userId);

        // todo: validar se tem match entre os planos do grupo e do aluno

        if (isPrivateGroupFull(classGroup)) {
            throw new BusinessException("Class group is full");
        }
        incrementGroupMembers(classGroup);
        saveUserToGroup(classGroup, user);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> getUsersByClassGroupId(UUID classGroupId) {
        return classGroupUserRepository.findAllUsersByClassGroupId(classGroupId).stream().
                map(UserMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<ClassGroupResponseDTO> getClassGroupByUserId(UUID userId) {
        return classGroupUserRepository.findClassGroupsByUserId(userId).stream().
                map(classGroup -> ClassGroupMapper.toResponse(classGroup, classGroup.getCreatedByUser().getProfile().
                        getFullName())).toList();
    }


    @Transactional
    public void removeUserFromClassGroup(UUID classGroupId, UUID userId) {
        ClassGroupUser classGroupUser = validationFaced.classGroupUserValidation.validateClassGroupUserId(classGroupId, userId);
        decrementGroupMembers(classGroupUser.getClassGroup());
        classGroupUserRepository.delete(classGroupUser);
    }

    @Transactional
    public void deleteClassGroupById(UUID id) {
        ClassGroup classGroup = validationFaced.classGroupValidation.validateClassGroupById(id);
        classGroupRepository.delete(classGroup);
    }

    private boolean isPrivateGroupFull(ClassGroup classGroup) {
        return classGroup.getGroupType() == GroupType.PRIVATE &&
                classGroup.getTotalMembers() >= classGroup.getCapacity();
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
