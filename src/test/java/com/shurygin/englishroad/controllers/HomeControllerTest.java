package com.shurygin.englishroad.controllers;

import com.shurygin.englishroad.UtilClassForTests;
import com.shurygin.englishroad.model.Level;
import com.shurygin.englishroad.repositories.LevelRepository;
import com.shurygin.englishroad.util.SecurityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HomeController.class)
@AutoConfigureMockMvc(addFilters = false) // Отрубаем security
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SecurityManager securityManager;

    @MockBean
    private LevelRepository levelRepository;

    @Test
    void levels() throws Exception {

        // Настраиваем моки
        List<Level> mockLevels = UtilClassForTests.generateLevelsList(4);
        when(levelRepository.findAll(Sort.by("id"))).thenReturn(mockLevels);

        // Проверяем
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("levels", mockLevels));

    }

    @Test
    void showHomePage() throws Exception {

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attributeExists("levels"));

    }
}