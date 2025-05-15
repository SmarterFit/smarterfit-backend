package com.smarterfit.modules.ai.tools.classes;

import com.smarterfit.modules.classgroup.dto.response.ClassGroupResponseDTO;
import com.smarterfit.modules.classgroup.service.ClassGroupUserService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;


@Component
public class UserClassTools {

    private final ClassGroupUserService classGroupUserService;

    public UserClassTools(ClassGroupUserService classGroupUserService) {
        this.classGroupUserService = classGroupUserService;
    }

    @Tool(description = "Buscar turmas em que o usuário está inscrito")
    public List<ClassGroupResponseDTO> getUserClasses(@ToolParam(description = "ID do usuário") UUID userId) {
        return classGroupUserService.getClassGroupsByUserId(userId);
    }
}