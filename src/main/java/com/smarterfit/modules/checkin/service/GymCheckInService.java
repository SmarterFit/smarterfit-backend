/*
 * 
 * Created by Gabriel Henrique
 */
package com.smarterfit.modules.checkin.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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
    private GymCheckInRepository gymCheckInRepository;
    private GymCheckInValidation gymCheckInValidation;
    private UserValidation userValidation;

    @Autowired
    public GymCheckInService(GymCheckInRepository gymCheckInRepository, GymCheckInValidation gymCheckInValidation,
            UserValidation userValidation) {
        this.gymCheckInRepository = gymCheckInRepository;
        this.gymCheckInValidation = gymCheckInValidation;
        this.userValidation = userValidation;
    }

    @Transactional
    public GymCheckInResponseDTO doCheckIn(GymCheckInAndCheckOutRequestDTO requestDTO) {
        User user = userValidation.validateUserById(requestDTO.getUserId());
        gymCheckInValidation.validateOpenCheckInNotExists(requestDTO.getUserId());

        GymCheckIn gymCheckIn = GymCheckInMapper.toEntity(requestDTO, user);
        gymCheckIn = gymCheckInRepository.save(gymCheckIn);

        /// TODO: Lançar evento de check-in realizado com sucesso

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