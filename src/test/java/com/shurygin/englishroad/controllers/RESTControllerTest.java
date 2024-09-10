package com.shurygin.englishroad.controllers;

import com.shurygin.englishroad.UtilClassForTests;
import com.shurygin.englishroad.dto.Question;
import com.shurygin.englishroad.repositories.UserRepository;
import com.shurygin.englishroad.services.QuestionsService;
import com.shurygin.englishroad.util.SecurityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RESTController.class)
@AutoConfigureMockMvc(addFilters = false) // Отрубаем security
class RESTControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionsService questionsService;
    @MockBean
    private SecurityManager securityManager;
    @MockBean
    private UserRepository userRepository;

    @Test
    void getQuestions() throws Exception {

        // Настраиваем моки
        int levelIndex = 1;
        List<Question> questions = UtilClassForTests.generateQuestionsList(20);
        when(questionsService.getByLevelIndex(levelIndex)).thenReturn(questions);

        // Проверяем
        mockMvc.perform(get("/api/questions")
            .param("level", String.valueOf(levelIndex)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].word.name").value("word1"))
            .andExpect(jsonPath("$[1].options[1]").value("option2"));

    }
}