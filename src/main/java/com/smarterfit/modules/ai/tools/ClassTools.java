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

    @Tool(description = "Buscar turmas com base em múltiplos filtros.")
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
            @ToolParam(required = false, description = "Buscar apenas turmas que são eventos") Boolean isEvent
    ) {
        // Log para verificar os parâmetros recebidos
        System.out.println("Iniciando a busca com os seguintes parâmetros:");
        System.out.println("titleTerm: " + titleTerm);
        System.out.println("minCapacity: " + minCapacity);
        System.out.println("maxCapacity: " + maxCapacity);
        System.out.println("modality: " + modality);
        System.out.println("minMembers: " + minMembers);
        System.out.println("maxMembers: " + maxMembers);
        System.out.println("daysOfWeek: " + daysOfWeek);
        System.out.println("startFrom: " + startFrom);
        System.out.println("startTo: " + startTo);
        System.out.println("endFrom: " + endFrom);
        System.out.println("endTo: " + endTo);
        System.out.println("isEvent: " + isEvent);

        SearchClassGroupRequestDTO request = new SearchClassGroupRequestDTO();

        if (titleTerm != null) request.setTitleTerm(titleTerm);
        if (minCapacity != null) request.setMinCapacity(minCapacity);
        if (maxCapacity != null) request.setMaxCapacity(maxCapacity);
        if (modality != null) request.setModality(modality);
        if (minMembers != null) request.setMinTotalMembers(minMembers);
        if (maxMembers != null) request.setMaxTotalMembers(maxMembers);
        if (daysOfWeek != null) request.setDaysOfWeek(daysOfWeek);
        if (startFrom != null) request.setStartDateFrom(startFrom);
        if (startTo != null) request.setStartDateTo(startTo);
        if (endFrom != null) request.setEndDateFrom(endFrom);
        if (endTo != null) request.setEndDateTo(endTo);
        if (isEvent != null) request.setIsEvent(isEvent);

        // Log para verificar os parâmetros antes da busca
        System.out.println("Parametros de busca após a preparação do request:");
        System.out.println(request);

        List<ClassGroupResponseDTO> result = search(request);

        // Log para verificar o resultado da busca
        System.out.println("Resultados encontrados:");
        if (result != null && !result.isEmpty()) {
            for (ClassGroupResponseDTO classGroup : result) {
                System.out.println("Turma encontrada: " + classGroup.getTitle());
            }
        } else {
            System.out.println("Nenhuma turma encontrada.");
        }

        return result;
    }

    private List<ClassGroupResponseDTO> search(SearchClassGroupRequestDTO request) {
        // Log de entrada no método de busca
        System.out.println("Iniciando a busca no serviço com os parâmetros: " + request);
        return classGroupService.searchClass(request, Pageable.unpaged()).getContent();
    }
}
