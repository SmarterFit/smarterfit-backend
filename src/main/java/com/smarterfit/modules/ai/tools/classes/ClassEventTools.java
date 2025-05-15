package com.smarterfit.modules.ai.tools.classes;


import com.smarterfit.modules.classgroup.dto.response.ClassEventResponseDTO;
import com.smarterfit.modules.classgroup.service.ClassEventService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClassEventTools {

    private final ClassEventService classEventService;

    public ClassEventTools(ClassEventService classEventService) {
        this.classEventService = classEventService;
    }

    @Tool(description = "Buscar eventos gratuitos. Use isso quando o usuário quiser saber " +
            "sobre aulas avulsas, eventos futuros ou gratuitos.")
    public List<ClassEventResponseDTO> searchUnfinishedClassEvents() {
        return classEventService.getAllClassEvents();
    }
}