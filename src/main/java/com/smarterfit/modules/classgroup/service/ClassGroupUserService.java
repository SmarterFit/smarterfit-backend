package com.smarterfit.modules.classgroup.service;

import com.smarterfit.common.enums.SubscriptionTypeEvent;
import com.smarterfit.common.util.SensitiveDataDecryptor;
import com.smarterfit.modules.billing.entity.Subscription;
import com.smarterfit.modules.billing.event.SubscriptionEvent;
import com.smarterfit.modules.billing.validation.SubscriptionValidation;
import com.smarterfit.modules.classgroup.dto.request.classgroupuser.EmployeeClassGroupUserDTO;
import com.smarterfit.modules.classgroup.dto.request.classgroupuser.MemberClassGroupUserDTO;
import com.smarterfit.modules.classgroup.dto.response.ClassGroupResponseDTO;
import com.smarterfit.modules.classgroup.dto.response.classgroupuser.ClassUsersResponseDTO;
import com.smarterfit.modules.classgroup.entity.ClassGroup;
import com.smarterfit.modules.classgroup.entity.ClassGroupUser;
import com.smarterfit.modules.classgroup.mapper.ClassGroupMapper;
import com.smarterfit.modules.classgroup.mapper.ClassGroupUserMapper;
import com.smarterfit.modules.classgroup.repository.ClassGroupUserRepository;
import com.smarterfit.modules.classgroup.validation.ValidationFaced;
import com.smarterfit.modules.useraccess.dto.response.UserResponseDTO;
import com.smarterfit.modules.useraccess.entity.User;
import com.smarterfit.modules.useraccess.mapper.UserMapper;
import groovy.transform.Undefined;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ClassGroupUserService {
    private final ClassGroupUserRepository classGroupUserRepository;
    private final ApplicationEventPublisher publisher;
    private final SubscriptionValidation subscriptionValidation;
    private final SensitiveDataDecryptor sensitiveDataDecryptor;
    private final ValidationFaced validationFaced;

    public ClassGroupUserService(ClassGroupUserRepository classGroupUserRepository,
            ValidationFaced validationFaced,
            SubscriptionValidation subscriptionValidation, SensitiveDataDecryptor sensitiveDataDecryptor,
            ApplicationEventPublisher publisher) {
        this.classGroupUserRepository = classGroupUserRepository;
        this.validationFaced = validationFaced;
        this.subscriptionValidation = subscriptionValidation;
        this.sensitiveDataDecryptor = sensitiveDataDecryptor;
        this.publisher = publisher;
    }

    @Transactional
    public void addMemberToClassGroup(MemberClassGroupUserDTO requestDTO) {
        validationFaced.classGroupUserValidation.validateClassGroupUserExists(requestDTO.getClassGroupId(),
                requestDTO.getUserId());

        ClassGroup classGroup = validationFaced.classGroupValidation
                .validateClassGroupById(requestDTO.getClassGroupId());
        validationFaced.classGroupValidation.validateClassGroupsIsActive(classGroup);

        User user = validationFaced.userValidation.validateUserById(requestDTO.getUserId());
        validationFaced.classGroupValidation.isGroupFull(classGroup);

        Subscription subscription = subscriptionValidation.validateSubscriptionById(requestDTO.getSubscriptionId());

        validationFaced.classGroupValidation.validateUserAccessToClassGroupBySubscription(
                requestDTO.getClassGroupId(),
                requestDTO.getUserId(),
                requestDTO.getSubscriptionId());

        validationFaced.classGroupPlanValidation.validateClassGroupPlanAndSubscription(
                classGroup.getId(),
                subscription.getPlan().getId());

        publisher.publishEvent(new SubscriptionEvent(
                SubscriptionTypeEvent.DECREMENT_AVAILABLE_CLASSES,
                subscription));

        incrementGroupMembers(classGroup);
        saveUserToGroup(classGroup, user, false);
    }

    @Transactional
    public void addEmployeeToClassGroup(EmployeeClassGroupUserDTO requestDTO, UUID userId) {
        validationFaced.classGroupUserValidation.validateClassGroupUserExists(requestDTO.getClassGroupId(), userId);

        ClassGroup classGroup = validationFaced.classGroupValidation
                .validateClassGroupById(requestDTO.getClassGroupId());
        validationFaced.classGroupValidation.validateClassGroupsIsActive(classGroup);

        User user = validationFaced.userValidation.validateUserById(userId);

        saveUserToGroup(classGroup, user, true);
    }

    @Transactional(readOnly = true)
    public List<ClassUsersResponseDTO> getStudentsByClassGroupId(UUID classGroupId) {
        return classGroupUserRepository.findStudentsByClassGroupId(classGroupId).stream()
                .map(ClassGroupUserMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<ClassUsersResponseDTO> getTeacherByClassGroupId(UUID classGroupId) {
        return classGroupUserRepository.findTeachersByClassGroupId(classGroupId).stream()
                .map(ClassGroupUserMapper::toResponse).toList();


    }


    @Transactional(readOnly = true)
    public List<ClassGroupResponseDTO> getClassGroupsByUserId(UUID userId) {
        return classGroupUserRepository.findClassGroupsByUserId(userId).stream().map(classGroup -> ClassGroupMapper
                .toResponse(classGroup, classGroup.getCreatedByUser().getProfile().getFullName())).toList();
    }

    @Transactional
    public void removeUserFromClassGroup(UUID classGroupId, UUID userId) {
        ClassGroupUser classGroupUser = validationFaced.classGroupUserValidation.validateClassGroupUserId(classGroupId,
                userId);

        decrementGroupMembers(classGroupUser.getClassGroup());
        publisher.publishEvent(new SubscriptionEvent(SubscriptionTypeEvent.INCREMENT_AVAILABLE_CLASSES,
                classGroupUser.getSubscription()));

        classGroupUserRepository.delete(classGroupUser);
    }

    public void removeSubscriptionByClassGroup(ClassGroup classGroup) {
        List<ClassGroupUser> classGroupUsers = classGroupUserRepository.findAllByClassGroupId(classGroup.getId());

        for (ClassGroupUser classGroupUser : classGroupUsers) {
            publisher.publishEvent(new SubscriptionEvent(SubscriptionTypeEvent.DECREMENT_AVAILABLE_CLASSES,
                    classGroupUser.getSubscription()));
        }
    }

    private void incrementGroupMembers(ClassGroup classGroup) {
        classGroup.setTotalMembers(classGroup.getTotalMembers() + 1);
    }

    private void decrementGroupMembers(ClassGroup classGroup) {
        classGroup.setTotalMembers(classGroup.getTotalMembers() - 1);
    }

    private void saveUserToGroup(ClassGroup classGroup, User user, boolean isTeacher) {
        ClassGroupUser classGroupUser = new ClassGroupUser();
        classGroupUser.setClassGroup(classGroup);
        classGroupUser.setUser(user);
        classGroupUser.setTeacher(isTeacher);
        classGroupUserRepository.save(classGroupUser);
    }
}
