package com.shurygin.englishroad.controllers;

import com.shurygin.englishroad.util.SecurityManager;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    private final SecurityManager securityManager;

    @Autowired
    public GlobalControllerAdvice(SecurityManager securityManager) {
        this.securityManager = securityManager;
    }

    @ModelAttribute("isAuthenticated")
    public boolean addIsAuthenticated() {
        return securityManager.isAuthenticated();
    }

    @ModelAttribute("hasAuthPath")
    public boolean addHasAuthPath(HttpServletRequest request) {
        String currentPath = request.getRequestURI();
        return currentPath.contains("/auth");
    }
}
