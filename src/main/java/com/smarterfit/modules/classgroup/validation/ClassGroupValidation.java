package com.smarterfit.modules.classgroup.validation;

import com.smarterfit.common.exceptions.ResourceAlreadyExistsException;
import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.common.validation.DateValidation;
import com.smarterfit.modules.billing.entity.Subscription;
import com.smarterfit.modules.billing.repository.SubscriptionRepository;
import com.smarterfit.modules.classgroup.entity.ClassGroup;
import com.smarterfit.modules.classgroup.repository.ClassGroupRepository;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;
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

    public void validateClassGroupExists(String title, UUID currentClassGroupId) {
        classGroupRepository.findByTitle(title).ifPresent(existing -> {
            if (!existing.getId().equals(currentClassGroupId)) {
                throw new ResourceAlreadyExistsException("Class group name is already in use.");
            }
        });

    }

    public void validateClassGroupDates(LocalDate startDate, LocalDate endDate) {
        DateValidation.validateDateRange(startDate, endDate, Boolean.TRUE);
    }

    public Subscription validateUserAccessToClassGroup(UUID classGroupId, UUID userId) {
        Optional<Subscription> subscription = subscriptionRepository
                .findFirstActiveSubscriptionGivingAccessToClassGroup(classGroupId, userId);

        if (subscription.isEmpty()) {
            throw new ResourceNotFoundException("User does not have access to this class group.");
        }

        return subscription.get();
    }
}
