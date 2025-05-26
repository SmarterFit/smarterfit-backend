package com.smarterfit.modules.ai.tools.classes;

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
import java.util.UUID;

@Component
public class ClassTools {

    private final ClassGroupService classGroupService;

    public ClassTools(ClassGroupService classGroupService) {
        this.classGroupService = classGroupService;
    }

    @Tool(description = "Buscar turmas. Só preencha os parâmetros que forem explicitamente informados pelo usuário.")
    public List<ClassGroupResponseDTO> searchClasses(
            @ToolParam(required = false, description = "Termo presente no título ou descrição") String titleTerm,
            @ToolParam(required = false, description = "Capacidade mínima da turma") Integer minCapacity,
            @ToolParam(required = false, description = "Capacidade máxima da turma") Integer maxCapacity,
            @ToolParam(required = false, description = "Nome da modalidade") String modality,
            @ToolParam(required = false, description = "Número mínimo de alunos") Integer minMembers,
            @ToolParam(required = false, description = "Número máximo de alunos") Integer maxMembers,
            @ToolParam(required = false, description = "Lista de dias da semana (ex: MONDAY, TUESDAY)") List<DayOfWeek> daysOfWeek,
            @ToolParam(required = false, description = "Data de início mínima") LocalDate startFrom,
            @ToolParam(required = false, description = "Data de início máxima") LocalDate startTo,
            @ToolParam(required = false, description = "Data de término mínima") LocalDate endFrom,
            @ToolParam(required = false, description = "Data de término máxima") LocalDate endTo,
            @ToolParam(required = false, description = "Buscar apenas turmas que são eventos") Boolean isEvent) {

        SearchClassGroupRequestDTO request = new SearchClassGroupRequestDTO();

        request.setTitleTerm(titleTerm);
        request.setMinCapacity(minCapacity);
        request.setMaxCapacity(maxCapacity);
        request.setModality(modality);
        request.setMinTotalMembers(minMembers);
        request.setMaxTotalMembers(maxMembers);
        request.setDaysOfWeek(daysOfWeek);
        request.setStartDateFrom(startFrom);
        request.setStartDateTo(startTo);
        request.setEndDateFrom(endFrom);
        request.setEndDateTo(endTo);
        request.setIsEvent(isEvent);

        return classGroupService.searchClass(request, Pageable.unpaged()).getContent();
    }

    @Tool(description = "Buscar turmas em que o usuário pode se inscrever")
    public List<ClassGroupResponseDTO> getAvailableClassGroupsByUserId(
            @ToolParam(description = "ID do usuário") UUID userId) {
        return classGroupService.getAvailableClassGroupsByUserId(userId);
    }

}
