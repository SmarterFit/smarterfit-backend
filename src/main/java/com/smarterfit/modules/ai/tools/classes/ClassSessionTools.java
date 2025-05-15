package com.smarterfit.modules.ai.tools.classes;

import com.smarterfit.modules.classgroup.dto.response.ClassSessionResponseDTO;
import com.smarterfit.modules.classgroup.service.ClassSessionService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ClassSessionTools {

    private final ClassSessionService classSessionService;

    public ClassSessionTools(ClassSessionService classSessionService) {
        this.classSessionService = classSessionService;
    }

    @Tool(description = "Buscar todas as sessões de aula de uma turma específica")
    public List<ClassSessionResponseDTO> getSessionsByClassGroup(
            @ToolParam(description = "ID da turma (class group)") UUID classGroupId
    ) {
        return classSessionService.getAllClassSessionByGroup(classGroupId);
    }
}
