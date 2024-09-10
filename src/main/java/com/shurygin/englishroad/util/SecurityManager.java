package com.shurygin.englishroad.util;

import com.shurygin.englishroad.model.Level;
import com.shurygin.englishroad.model.User;
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
    private int max_level_no_auth = 3;

    Authentication authentication;

    public boolean isAuthenticated() {
        refreshAuthentication();
        return authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken);
    }

    private void refreshAuthentication() {
        authentication = SecurityContextHolder.getContext().getAuthentication();
    }

    public boolean isLevelAllowed(Level level) {
        return isLevelAllowed(level.getId());
    }

    public boolean isLevelAllowed(Integer levelIndex) {
        return levelIndex <= max_level_no_auth || isAuthenticated();
    }

    public User getCurrentUser(){
        if (isAuthenticated()) {
            return (User) authentication.getPrincipal();
        }
        return null;
    }

}
