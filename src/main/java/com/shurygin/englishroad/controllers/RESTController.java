package com.shurygin.englishroad.controllers;

import com.shurygin.englishroad.dto.Question;
import com.shurygin.englishroad.services.QuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api")
public class RESTController {

    private final QuestionsService questionsService;

    @Autowired
    public RESTController(QuestionsService questionsService) {
        this.questionsService = questionsService;
    }

    @GetMapping(path = "/questions")
    public List<Question> getQuestions(@RequestParam(value = "level", required = true) Integer levelIndex) {
        return questionsService.getByLevelIndex(levelIndex, 0);
    }

}
