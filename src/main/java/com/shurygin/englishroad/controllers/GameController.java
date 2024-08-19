package com.shurygin.englishroad.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/game")
public class GameController {

    @GetMapping
    public String showGamePage(Model model,
                               @RequestParam(value = "level", required = true) Integer levelIndex,
                               HttpServletResponse response,
                               @CookieValue(value = "currentQuestionIndex", defaultValue = "0") String currentQuestionIndex){

//        Cookie cookie = new Cookie("currentQuestionIndex", currentQuestionIndex);
//        cookie.setMaxAge(7 * 24 * 60 * 60);
//        response.addCookie(cookie);

        model.addAttribute("titleAppender", " · level "+levelIndex);
        return "game";
    }

}
