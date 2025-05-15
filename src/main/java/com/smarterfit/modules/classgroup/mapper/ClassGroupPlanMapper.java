package com.smarterfit.modules.classgroup.mapper;

import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.common.mapper.GenericMapper;
import com.smarterfit.modules.billing.dto.response.plan.PlanResponseDTO;
import com.smarterfit.modules.billing.entity.Plan;

public class ClassGroupPlanMapper {

    public static PlanResponseDTO toResponse(Plan plan) {
        if (plan == null) {
            throw new ResourceNotFoundException("Plan not found.");
        }

        return GenericMapper.map(plan, PlanResponseDTO.class);
    }
}
