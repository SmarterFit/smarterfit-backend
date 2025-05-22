/*
 * 
 * Created by Gabriel Henrique
 */
package com.smarterfit.modules.checkin.service;

import java.time.LocalDateTime;
import java.util.*;

import com.smarterfit.modules.billing.validation.SubscriptionValidation;
import com.smarterfit.modules.checkin.domain.GymPoints;
import com.smarterfit.modules.checkin.event.CalculatePointsUserEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smarterfit.modules.checkin.dto.request.GymCheckInAndCheckOutRequestDTO;
import com.smarterfit.modules.checkin.dto.response.GymCheckInResponseDTO;
import com.smarterfit.modules.checkin.entity.GymCheckIn;
import com.smarterfit.modules.checkin.mapper.GymCheckInMapper;
import com.smarterfit.modules.checkin.repository.GymCheckInRepository;
import com.smarterfit.modules.checkin.validation.GymCheckInValidation;
import com.smarterfit.modules.useraccess.entity.User;
import com.smarterfit.modules.useraccess.validation.UserValidation;

@Service
public class GymCheckInService {
    private final GymCheckInRepository gymCheckInRepository;
    private final GymCheckInValidation gymCheckInValidation;
    private final UserValidation userValidation;
    private final SubscriptionValidation subscriptionValidation;
    private final ApplicationEventPublisher publisher;
    private final GymPoints gymPoints;

    public GymCheckInService(GymCheckInRepository gymCheckInRepository, GymCheckInValidation gymCheckInValidation,
            UserValidation userValidation, SubscriptionValidation subscriptionValidation, GymPoints gymPoints,
            ApplicationEventPublisher publisher) {
        this.gymCheckInRepository = gymCheckInRepository;
        this.gymCheckInValidation = gymCheckInValidation;
        this.userValidation = userValidation;
        this.subscriptionValidation = subscriptionValidation;
        this.gymPoints = gymPoints;
        this.publisher = publisher;
    }

    @Transactional
    public GymCheckInResponseDTO doCheckIn(GymCheckInAndCheckOutRequestDTO requestDTO) {
        User user = userValidation.validateUserById(requestDTO.getUserId());

        subscriptionValidation.validateHasCurrentSubscription(user.getId());
        gymCheckInValidation.validateOpenCheckInNotExists(requestDTO.getUserId());

        GymCheckIn gymCheckIn = GymCheckInMapper.toEntity(requestDTO, user);
        gymCheckIn = gymCheckInRepository.save(gymCheckIn);

        Integer points = gymPoints.calculateDailyConsecutivePoints(user.getId());
        publisher.publishEvent(new CalculatePointsUserEvent(user.getId(), points));

        return GymCheckInMapper.toResponse(gymCheckIn);
    }

    @Transactional
    public GymCheckInResponseDTO doCheckOut(GymCheckInAndCheckOutRequestDTO requestDTO) {
        GymCheckIn gymCheckIn = gymCheckInValidation.validateOpenGymCheckInByUserId(requestDTO.getUserId());

        gymCheckIn.setCheckOutTime(LocalDateTime.now());
        gymCheckIn = gymCheckInRepository.save(gymCheckIn);

        return GymCheckInMapper.toResponse(gymCheckIn);
    }

    @Transactional
    public void doCheckOutInAll() {
        gymCheckInRepository.updateAllCheckOutTime(LocalDateTime.now());
    }

    /// TODO: Implemente filtros de data
    @Transactional(readOnly = true)
    public List<GymCheckInResponseDTO> getAllByUserId(UUID userId) {
        List<GymCheckIn> gymCheckIns = gymCheckInRepository.findByUserId(userId);

        return gymCheckIns.stream()
                .map(GymCheckInMapper::toResponse)
                .toList();
    }

}