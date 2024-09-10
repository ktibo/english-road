package com.shurygin.englishroad.controllers;

import com.shurygin.englishroad.Hints;
import com.shurygin.englishroad.util.SecurityManager;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/game")
public class GameController {

    private final SecurityManager securityManager;

    @Autowired
    public GameController(SecurityManager securityManager) {
        this.securityManager = securityManager;
    }

    @GetMapping
    public String showGamePage(Model model,
                               @RequestParam(value = "level", required = true) Integer levelIndex,
                               HttpServletResponse response
//                               @CookieValue(value = "currentQuestionIndex", defaultValue = "0") String currentQuestionIndex
    ){
        if (!securityManager.isLevelAllowed(levelIndex)) return "error";

//        Cookie cookie = new Cookie("hints", "1");
//        cookie.setMaxAge(7 * 24 * 60 * 60);
//        response.addCookie(cookie);

        model.addAttribute("titleAppender", " · level "+levelIndex);
        model.addAttribute("hints", Hints.values());
        return "game";
    }

}
