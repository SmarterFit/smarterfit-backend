package com.smarterfit.modules.classgroup.service;

import com.smarterfit.common.enums.RoleType;
import com.smarterfit.common.exceptions.BusinessException;
import com.smarterfit.modules.billing.entity.Plan;
import com.smarterfit.modules.billing.entity.Subscription;
import com.smarterfit.modules.billing.service.SubscriptionService;
import com.smarterfit.modules.billing.validation.SubscriptionValidation;
import com.smarterfit.modules.classgroup.dto.request.classgroup.CreateClassGroupRequestDTO;
import com.smarterfit.modules.classgroup.dto.response.ClassGroupResponseDTO;
import com.smarterfit.modules.classgroup.entity.ClassGroup;
import com.smarterfit.modules.classgroup.entity.ClassGroupPlan;
import com.smarterfit.modules.classgroup.entity.ClassGroupUser;
import com.smarterfit.modules.classgroup.entity.Modality;
import com.smarterfit.modules.classgroup.mapper.ClassGroupMapper;
import com.smarterfit.modules.classgroup.repository.ClassGroupPlanRepository;
import com.smarterfit.modules.classgroup.repository.ClassGroupRepository;
import com.smarterfit.modules.classgroup.repository.ClassGroupUserRepository;
import com.smarterfit.modules.classgroup.validation.ValidationFaced;
import com.smarterfit.modules.useraccess.dto.response.UserResponseDTO;
import com.smarterfit.modules.useraccess.entity.User;
import com.smarterfit.modules.useraccess.mapper.UserMapper;
import com.smarterfit.modules.useraccess.validation.RolesValidation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/// TODO: Criar ClassGroupUserService, com controller, mapper, dto e validation separados.

@Service
public class ClassGroupService {

    private final ClassGroupRepository classGroupRepository;
    private final ClassGroupUserRepository classGroupUserRepository;
    private final ClassGroupPlanRepository classGroupPlanRepository;
    private final ValidationFaced validationFaced;
    private final SubscriptionService subscriptionService;
    private final SubscriptionValidation subscriptionValidation;

    public ClassGroupService(ClassGroupRepository classGroupRepository,
            ClassGroupUserRepository classGroupUserRepository,
            ClassGroupPlanRepository classGroupPlanRepository,
            ValidationFaced validationFaced,
            SubscriptionService subscriptionService,
            SubscriptionValidation subscriptionValidation) {
        this.classGroupRepository = classGroupRepository;
        this.classGroupUserRepository = classGroupUserRepository;
        this.classGroupPlanRepository = classGroupPlanRepository;
        this.validationFaced = validationFaced;
        this.subscriptionService = subscriptionService;
        this.subscriptionValidation = subscriptionValidation;
    }

    @Transactional
    public ClassGroupResponseDTO createClassGroup(CreateClassGroupRequestDTO requestDTO) {
        User user = validationFaced.userValidation.validateUserById(requestDTO.userCreatorId());
        RolesValidation.validateUserRole(RoleType.EMPLOYEE, user.getRoles());

        validationFaced.classGroupValidation.validateClassGroupDates(requestDTO.startDate(), requestDTO.endDate());

        validationFaced.classGroupValidation.validateClassGroupExists(requestDTO.title(), null);
        Modality modality = validationFaced.modalityValidation.validateModalityById(requestDTO.modalityId());

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
        List<ClassGroup> classGroups = classGroupRepository.findAll();
        return classGroups.stream()
                .map(classGroup -> ClassGroupMapper.toResponse(classGroup,
                        classGroup.getCreatedByUser().getProfile().getFullName()))
                .toList();
    }

    @Transactional
    public ClassGroupResponseDTO updateClassGroupById(UUID classGroupId, CreateClassGroupRequestDTO requestDTO) {
        validationFaced.classGroupValidation.validateClassGroupDates(requestDTO.startDate(),
                requestDTO.endDate());
        ClassGroup classGroup = validationFaced.classGroupValidation.validateClassGroupById(classGroupId);

        Modality modality = validationFaced.modalityValidation.validateModalityById(requestDTO.modalityId());

        classGroup = ClassGroupMapper.toEntity(requestDTO, modality, classGroup.getCreatedByUser(), classGroup);
        classGroupRepository.save(classGroup);

        return ClassGroupMapper.toResponse(classGroup, classGroup.getCreatedByUser().getProfile().getFullName());
    }

    @Transactional
    public void addPlanToClassGroup(UUID planId, UUID classGroupId) {
        validationFaced.classGroupPlanValidation.validateClassGroupPlanExists(planId, classGroupId);
        ClassGroup classGroup = validationFaced.classGroupValidation.validateClassGroupById(classGroupId);

        Plan plan = validationFaced.planValidation.validatePlanById(planId);
        ClassGroupPlan classGroupPlan = new ClassGroupPlan(classGroup, plan);

        classGroupPlanRepository.save(classGroupPlan);
    }

    @Transactional
    public void addUserToClassGroup(UUID classGroupId, UUID userId, UUID subscriptionId) {
        validationFaced.classGroupUserValidation.validateClassGroupUserExists(classGroupId, userId);

        ClassGroup classGroup = validationFaced.classGroupValidation.validateClassGroupById(classGroupId);

        if (isGroupFull(classGroup)) {
            throw new BusinessException("Class group is full");
        }

        User user = validationFaced.userValidation.validateUserById(userId);
        Subscription subscription = subscriptionValidation.validateSubscriptionById(subscriptionId);

        validationFaced.classGroupValidation
                .validateUserAccessToClassGroupBySubscription(classGroupId, userId, subscriptionId);

        subscriptionService.decrementAvailableClasses(subscription);

        incrementGroupMembers(classGroup);
        saveUserToGroup(classGroup, user);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> getUsersByClassGroupId(UUID classGroupId) {
        return classGroupUserRepository.findAllUsersByClassGroupId(classGroupId).stream().map(UserMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ClassGroupResponseDTO> getClassGroupByUserId(UUID userId) {
        return classGroupUserRepository.findClassGroupsByUserId(userId).stream().map(classGroup -> ClassGroupMapper
                .toResponse(classGroup, classGroup.getCreatedByUser().getProfile().getFullName())).toList();
    }

    @Transactional
    public void removeUserFromClassGroup(UUID classGroupId, UUID userId) {
        ClassGroupUser classGroupUser = validationFaced.classGroupUserValidation.validateClassGroupUserId(classGroupId,
                userId);

        decrementGroupMembers(classGroupUser.getClassGroup());
        subscriptionService.incrementAvailableClasses(classGroupUser.getSubscription());

        classGroupUserRepository.delete(classGroupUser);
    }

    @Transactional
    public void deleteClassGroupById(UUID id) {
        ClassGroup classGroup = validationFaced.classGroupValidation.validateClassGroupById(id);

        // TODO: incrementar a quantidade de turmas disponiveis
        // THINK: Pensar se é necessário os contadores ou se o custo não é tão alto para
        // contar em cada interação.
        // THINK: Pode ser interessante apenas um softdelete (permite rastrear dados
        // antigos)

        classGroupRepository.delete(classGroup);
    }

    /// TODO: Jogar para validação
    private boolean isGroupFull(ClassGroup classGroup) {
        return classGroup.getTotalMembers() >= classGroup.getCapacity();
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
