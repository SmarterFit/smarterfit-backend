package com.smarterfit.common.security;

import com.smarterfit.common.enums.RoleType;
import com.smarterfit.common.exceptions.PermissionDeniedException;
import com.smarterfit.modules.useraccess.entity.User;
import com.smarterfit.modules.useraccess.validation.RolesValidation;
import com.smarterfit.modules.useraccess.validation.UserValidation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Component
public class RoleValidationInterceptor implements HandlerInterceptor {

    private final UserValidation userValidation;

    public  RoleValidationInterceptor(UserValidation userValidation){
        this.userValidation = userValidation;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        RequireRole requireRole = handlerMethod.getMethodAnnotation(RequireRole.class);

        // Se não tem anotação, não precisa validar
        if (requireRole == null) {
            return true;
        }

        // 1. Read UUID from header
        String userIdHeader = request.getHeader("X-User-Id");
        if (userIdHeader == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Missing X-User-Id header");
            return false;
        }

        UUID userId;
        try {
            userId = UUID.fromString(userIdHeader);
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid UUID in X-User-Id");
            return false;
        }

        // 2. Validar usuário
        User user;
        try {
            user = userValidation.validateUserById(userId);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid User: " + e.getMessage());
            return false;
        }

        // 3. Validar o papel do usuário
        try {
            RolesValidation.validateUserRole(requireRole.value(), user.getRoles());
        } catch (PermissionDeniedException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Access denied: " + e.getMessage());
            return false;
        }

        return true;
    }
}
