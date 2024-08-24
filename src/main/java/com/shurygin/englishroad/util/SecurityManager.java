package com.shurygin.englishroad.util;

import com.shurygin.englishroad.model.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:game.properties")
public class SecurityManager {

    @Value("${security.max_level_no_auth}")
    private int max_level_no_auth;

    Authentication authentication;

    public boolean isAuthenticated() {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken);
    }

    public boolean isLevelAllowed(Level level) {
        return level.getId() <= max_level_no_auth || isAuthenticated();
    }

    public boolean isLevelAllowed(Integer levelIndex) {
        return levelIndex <= max_level_no_auth || isAuthenticated();
    }

}
