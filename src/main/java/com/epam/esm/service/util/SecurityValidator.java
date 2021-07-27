package com.epam.esm.service.util;

import com.epam.esm.persistence.entity.Role;
import com.epam.esm.web.security.JwtUser;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public final class SecurityValidator {
    public static void validateUserAccess(int userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
        boolean isAdmin = jwtUser.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(Role.ROLE_ADMIN.name()::equalsIgnoreCase);
        if (!isAdmin && jwtUser.getId() != (userId)) {
            throw new AccessDeniedException("Deny access");
        }
    }
}
