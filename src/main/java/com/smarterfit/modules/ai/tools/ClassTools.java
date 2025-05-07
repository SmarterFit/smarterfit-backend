package com.smarterfit.modules.ai.tools;

import com.smarterfit.modules.classgroup.dto.request.classgroup.SearchClassGroupRequestDTO;
import com.smarterfit.modules.classgroup.dto.response.ClassGroupResponseDTO;
import com.smarterfit.modules.classgroup.service.ClassGroupService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Component
public class ClassTools {
    private final ClassGroupService classGroupService;

    public ClassTools(ClassGroupService classGroupService) {
        this.classGroupService = classGroupService;
    }

    @Tool(description = "Buscar turmas disponíveis com base em filtros.")
    public List<ClassGroupResponseDTO> searchClassGroup(
            @ToolParam(description = "Termo de título ou descrição") String titleTerm,
            @ToolParam(description = "Capacidade mínima da turma") Integer minCapacity,
            @ToolParam(description = "Capacidade máxima da turma") Integer maxCapacity,
            @ToolParam(description = "Nome da modalidade") String modality,
            @ToolParam(description = "Número mínimo de alunos") Integer minTotalMembers,
            @ToolParam(description = "Número máximo de alunos") Integer maxTotalMembers,
            @ToolParam(description = "Dias da semana desejados") List<DayOfWeek> daysOfWeek,
            @ToolParam(description = "Data inicial da turma (de)") LocalDate startDateFrom,
            @ToolParam(description = "Data inicial da turma (até)") LocalDate startDateTo,
            @ToolParam(description = "Data final da turma (de)") LocalDate endDateFrom,
            @ToolParam(description = "Data final da turma (até)") LocalDate endDateTo,
            @ToolParam(description = "É uma turma de evento") Boolean isEvent
    ) {
        SearchClassGroupRequestDTO request = new SearchClassGroupRequestDTO();
        request.setTitleTerm(titleTerm);
        request.setMinCapacity(minCapacity);
        request.setMaxCapacity(maxCapacity);
        request.setModality(modality);
        request.setMinTotalMembers(minTotalMembers);
        request.setMaxTotalMembers(maxTotalMembers);
        request.setDaysOfWeek(daysOfWeek);
        request.setStartDateFrom(startDateFrom);
        request.setStartDateTo(startDateTo);
        request.setEndDateFrom(endDateFrom);
        request.setEndDateTo(endDateTo);
        request.setIsEvent(isEvent);

        return classGroupService.searchClass(request, Pageable.unpaged()).getContent();
    }
}
