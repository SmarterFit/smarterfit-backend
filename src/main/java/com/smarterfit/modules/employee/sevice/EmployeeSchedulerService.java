package com.smarterfit.modules.employee.sevice;


import com.smarterfit.common.enums.RoleType;
import com.smarterfit.modules.employee.dto.request.schedule.EmployeeSchedulerRequestDTO;
import com.smarterfit.modules.employee.dto.response.EmployeeScheduleResponseDTO;
import com.smarterfit.modules.employee.entity.EmployeeSchedule;
import com.smarterfit.modules.employee.mapper.EmployeeSchedulerMapper;
import com.smarterfit.modules.employee.repository.EmployeeSchedulerRepository;
import com.smarterfit.modules.employee.validation.EmployeeSchedulerValidation;
import com.smarterfit.modules.useraccess.entity.User;
import com.smarterfit.modules.useraccess.validation.RolesValidation;
import com.smarterfit.modules.useraccess.validation.UserValidation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EmployeeSchedulerService {

    private final EmployeeSchedulerRepository employeeSchedulerRepository;
    private final EmployeeSchedulerValidation employeeSchedulerValidation;
    private final UserValidation userValidation;

    public EmployeeSchedulerService(EmployeeSchedulerRepository employeeSchedulerRepository,
                                    EmployeeSchedulerValidation employeeSchedulerValidation,
                                    UserValidation userValidation) {
        this.employeeSchedulerRepository = employeeSchedulerRepository;
        this.employeeSchedulerValidation = employeeSchedulerValidation;
        this.userValidation = userValidation;
    }

    @Transactional
    public EmployeeScheduleResponseDTO createEmployeeSchedule(EmployeeSchedulerRequestDTO employeeScheduleRequestDTO, UUID requesterId) {
        User user = userValidation.validateUserById(employeeScheduleRequestDTO.getUserId());
        User creator = userValidation.validateUserById(requesterId);

        RolesValidation.validateUserRole(RoleType.ADMIN, creator.getRoles());
        RolesValidation.validateUserRole(RoleType.EMPLOYEE, user.getRoles());

        employeeSchedulerValidation.validateNoScheduleConflict(user.getId(),employeeScheduleRequestDTO);


        EmployeeSchedule employeeSchedule = EmployeeSchedulerMapper.toEntity(employeeScheduleRequestDTO);
        return EmployeeSchedulerMapper.toResponse(employeeSchedulerRepository.save(employeeSchedule));
    }


    @Transactional(readOnly = true)
    public List<EmployeeScheduleResponseDTO> getAllEmployeeScheduleByUserId(UUID userId) {
        User user = userValidation.validateUserById(userId);

        return employeeSchedulerRepository.findAllByUserId(user.getId())
                .stream()
                .map(EmployeeSchedulerMapper::toResponse).collect(Collectors.toList());
    }

    @Transactional
    public EmployeeScheduleResponseDTO updateEmployeeScheduleById(UUID id, EmployeeSchedulerRequestDTO employeeScheduleRequestDTO, UUID requesterId) {
        User user = userValidation.validateUserById(employeeScheduleRequestDTO.getUserId());
        User creator = userValidation.validateUserById(requesterId);

        RolesValidation.validateUserRole(RoleType.ADMIN, creator.getRoles());

        EmployeeSchedule employeeSchedule = employeeSchedulerValidation.validateEmployeeScheduleById(id);


        employeeSchedulerValidation.validateNoScheduleConflict(user.getId(), employeeScheduleRequestDTO);

        employeeSchedule = EmployeeSchedulerMapper.toEntity(employeeScheduleRequestDTO, employeeSchedule);

        return EmployeeSchedulerMapper.toResponse(employeeSchedulerRepository.save(employeeSchedule));
    }

    @Transactional
    public void deleteEmployeeScheduleById(UUID id, UUID requesterId) {
        User creator = userValidation.validateUserById(requesterId);
        RolesValidation.validateUserRole(RoleType.ADMIN, creator.getRoles());

        EmployeeSchedule employeeSchedule = employeeSchedulerValidation.validateEmployeeScheduleById(id);
        employeeSchedulerRepository.delete(employeeSchedule);
    }
}
