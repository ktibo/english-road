package com.shurygin.englishroad.controllers;

import com.shurygin.englishroad.Hints;
import com.shurygin.englishroad.dto.Question;
import com.shurygin.englishroad.model.User;
import com.shurygin.englishroad.repositories.UserRepository;
import com.shurygin.englishroad.services.QuestionsService;
import com.shurygin.englishroad.util.SecurityManager;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api")
public class RESTController {

    private final QuestionsService questionsService;
    private final SecurityManager securityManager;
    private final UserRepository userRepository;

    @Autowired
    public RESTController(QuestionsService questionsService, SecurityManager securityManager, UserRepository userRepository) {
        this.questionsService = questionsService;
        this.securityManager = securityManager;
        this.userRepository = userRepository;
    }

    @GetMapping(path = "/questions")
    public List<Question> getQuestions(@RequestParam(value = "level", required = true) Integer levelIndex) {
        return questionsService.getByLevelIndex(levelIndex);
    }

    @GetMapping(path = "/hints")
    public ResponseEntity<HttpStatus> getHints(HttpServletResponse response,
                                               @CookieValue(value = "hints", defaultValue = "0") String hints) {

        User currentUser = securityManager.getCurrentUser();
        if (currentUser != null) {
            hints = String.valueOf(currentUser.getHints());
        }

        addCookie(response, hints);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/hints/{hintIndex}")
    public ResponseEntity<HttpStatus> spendHint(@PathVariable(value = "hintIndex") Integer hintIndex,
                                                HttpServletResponse response,
                                                @CookieValue(value = "hints", defaultValue = "0") String hints) {

        refreshHints(response, Hints.spendHint(hintIndex, Long.parseLong(hints)));

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping(path = "/completeLevel/{levelIndex}")
    public ResponseEntity<HttpStatus> completeLevel(@PathVariable(value = "levelIndex") Integer levelIndex,
                                      HttpServletResponse response,
                                      @CookieValue(value = "hints", defaultValue = "0") String hints) {

        refreshHints(response, Hints.addHint(levelIndex, Long.parseLong(hints)));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void refreshHints(HttpServletResponse response, long newHints) {
        User currentUser = securityManager.getCurrentUser();
        if (currentUser != null) {
            currentUser.setHints(newHints);
            userRepository.save(currentUser);
        }

        addCookie(response, String.valueOf(newHints));
    }

    private static void addCookie(HttpServletResponse response, String hints) {
        Cookie cookie = new Cookie("hints", hints);
        cookie.setPath("/");
        //cookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(cookie);
    }

}
