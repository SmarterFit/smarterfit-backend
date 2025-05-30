/*
 * 
 * Created by Gabriel Henrique
 */
package com.smarterfit.modules.checkin.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import com.smarterfit.modules.billing.validation.SubscriptionValidation;
import com.smarterfit.modules.checkin.domain.GymPoints;
import com.smarterfit.modules.checkin.event.CalculatePointsUserEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smarterfit.modules.checkin.dto.request.FilterGymCheckInRequestDTO;
import com.smarterfit.modules.checkin.dto.request.GymCheckInAndCheckOutRequestDTO;
import com.smarterfit.modules.checkin.dto.response.GymCheckInResponseDTO;
import com.smarterfit.modules.checkin.entity.GymCheckIn;
import com.smarterfit.modules.checkin.mapper.GymCheckInMapper;
import com.smarterfit.modules.checkin.repository.GymCheckInRepository;
import com.smarterfit.modules.checkin.util.SensitiveCheckInDataDecryptor;
import com.smarterfit.modules.checkin.validation.GymCheckInValidation;
import com.smarterfit.modules.useraccess.entity.User;
import com.smarterfit.modules.useraccess.validation.UserValidation;

@Service
public class GymCheckInService {
    private final GymCheckInRepository gymCheckInRepository;
    private final GymCheckInValidation gymCheckInValidation;
    private final UserValidation userValidation;
    private final SubscriptionValidation subscriptionValidation;
    private final SensitiveCheckInDataDecryptor sensitiveCheckInDataDecryptor;
    private final ApplicationEventPublisher publisher;
    private final GymPoints gymPoints;

    public GymCheckInService(GymCheckInRepository gymCheckInRepository, GymCheckInValidation gymCheckInValidation,
            UserValidation userValidation, SubscriptionValidation subscriptionValidation,
            SensitiveCheckInDataDecryptor sensitiveCheckInDataDecryptor, GymPoints gymPoints,
            ApplicationEventPublisher publisher) {
        this.gymCheckInRepository = gymCheckInRepository;
        this.gymCheckInValidation = gymCheckInValidation;
        this.userValidation = userValidation;
        this.subscriptionValidation = subscriptionValidation;
        this.sensitiveCheckInDataDecryptor = sensitiveCheckInDataDecryptor;
        this.gymPoints = gymPoints;
        this.publisher = publisher;
    }

    @Transactional
    public GymCheckInResponseDTO doCheckIn(GymCheckInAndCheckOutRequestDTO requestDTO) {
        UUID userId = requestDTO.getUserId();
        User user = userValidation.validateUserById(userId);

        gymCheckInValidation.validateIsCommercialTime();
        subscriptionValidation.validateHasCurrentSubscription(userId);
        gymCheckInValidation.validateOpenCheckInNotExists(userId);

        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

        boolean isFirstCheckInToday = !gymCheckInRepository
                .existsByUserIdAndCheckInTimeBetween(userId, startOfDay, endOfDay);

        GymCheckIn gymCheckIn = GymCheckInMapper.toEntity(requestDTO, user);
        gymCheckIn = gymCheckInRepository.save(gymCheckIn);

        if (isFirstCheckInToday) {
            Integer points = gymPoints.calculateDailyConsecutivePoints(userId);
            publisher.publishEvent(new CalculatePointsUserEvent(userId, points));
        }

        return sensitiveCheckInDataDecryptor.decrypt(
                GymCheckInMapper.toResponse(gymCheckIn));
    }

    @Transactional
    public GymCheckInResponseDTO doCheckOut(GymCheckInAndCheckOutRequestDTO requestDTO) {
        GymCheckIn gymCheckIn = gymCheckInValidation.validateOpenGymCheckInByUserId(requestDTO.getUserId());

        gymCheckIn.setCheckOutTime(LocalDateTime.now());
        gymCheckIn = gymCheckInRepository.save(gymCheckIn);

        return sensitiveCheckInDataDecryptor.decrypt(GymCheckInMapper.toResponse(gymCheckIn));
    }

    @Transactional
    public void doCheckOutInAll() {
        gymCheckInRepository.updateAllCheckOutTime(LocalDateTime.now());
    }

    @Transactional(readOnly = true)
    public Boolean hasOpenCheckInByUserId(UUID userId) {
        return gymCheckInRepository.existsByUserIdAndCheckOutTimeIsNull(userId);
    }

    @Transactional(readOnly = true)
    public List<GymCheckInResponseDTO> getAllByUserId(UUID userId) {
        List<GymCheckIn> gymCheckIns = gymCheckInRepository.findByUserId(userId);

        return gymCheckIns.stream()
                .map(gymCheckIn -> sensitiveCheckInDataDecryptor.decrypt(GymCheckInMapper.toResponse(gymCheckIn)))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<GymCheckInResponseDTO> filterByUserIdAndDate(FilterGymCheckInRequestDTO requestDTO) {
        List<GymCheckIn> gymCheckIns = gymCheckInRepository.findByUserIdAndDateBetween(
                requestDTO.getUserId(),
                requestDTO.getStartDate(),
                requestDTO.getEndDate());

        return gymCheckIns.stream()
                .map(gymCheckIn -> sensitiveCheckInDataDecryptor.decrypt(GymCheckInMapper.toResponse(gymCheckIn)))
                .toList();
    }
}