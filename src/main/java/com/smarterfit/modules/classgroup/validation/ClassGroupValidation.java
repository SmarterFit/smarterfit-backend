package com.smarterfit.modules.classgroup.validation;

import com.smarterfit.common.exceptions.BusinessException;
import com.smarterfit.common.exceptions.ResourceAlreadyExistsException;
import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.common.util.SlugUtils;
import com.smarterfit.common.validation.DateValidation;
import com.smarterfit.modules.billing.repository.SubscriptionRepository;
import com.smarterfit.modules.classgroup.entity.ClassGroup;
import com.smarterfit.modules.classgroup.repository.ClassGroupRepository;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Component
public class ClassGroupValidation {

    private final ClassGroupRepository classGroupRepository;
    private final SubscriptionRepository subscriptionRepository;

    public ClassGroupValidation(ClassGroupRepository classGroupRepository,
            SubscriptionRepository subscriptionRepository) {
        this.classGroupRepository = classGroupRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    public ClassGroup validateClassGroupById(UUID id) {
        return classGroupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Class group not found."));
    }

    public void existsClassGroupById(UUID id) {
        if(!classGroupRepository.existsById(id)) throw new ResourceNotFoundException("Class group not found.");
    }

    public void validateClassGroupExists(UUID currentClassGroupId) {
        if(classGroupRepository.existsById(currentClassGroupId))
            throw new ResourceAlreadyExistsException("Class group name is already in use.");

    }

    public void validateClassGroupDates(LocalDate startDate, LocalDate endDate) {
        DateValidation.validateDateRange(startDate, endDate, Boolean.TRUE);
    }

    public void validateUserAccessToClassGroupBySubscription(UUID classGroupId, UUID userId,
            UUID subscriptionId) {
        boolean hasAccess = subscriptionRepository
                .existsAvailableSubscriptionByClassGroupAndParticipantAndSubscription(classGroupId, userId,
                        subscriptionId);

        if (!hasAccess) {
            throw new BusinessException("User does not have access to this class group with this subscription.");
        }
    }

    public void isGroupFull(ClassGroup classGroup) {
        if(classGroup.getTotalMembers() >= classGroup.getCapacity()){
            throw new BusinessException("Class group is full");
        }
    }


    public void validateModalityNotInUse(UUID modalityId) {
        if(classGroupRepository.classGroupHasModality(modalityId)){
            throw new BusinessException("Modality is in use by class group: ");
        }
    }

    public void validateClassGroupsIsActive(ClassGroup classGroup) {
        if(!classGroup.isActive()){
            throw new BusinessException("Class group is not active");
        }
    }

    public String generateUniqueSlug(String title) {
        String baseSlug = SlugUtils.slugify(title);
        String slug = baseSlug;
        int counter = 1;

        while (classGroupRepository.existsBySlug(slug)) {
            slug = baseSlug + "-" + counter++;
        }

        return slug;
    }

}
