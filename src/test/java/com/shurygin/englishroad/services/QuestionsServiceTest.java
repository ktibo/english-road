package com.shurygin.englishroad.services;

import com.shurygin.englishroad.UtilClassForTests;
import com.shurygin.englishroad.dto.Question;
import com.shurygin.englishroad.model.Word;
import com.shurygin.englishroad.util.QuestionsCreator;
import com.shurygin.englishroad.util.SecurityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestionsServiceTest {

    @Mock
    private WordService wordService;

    @Spy
    private QuestionsCreator questionsCreator;

    @Mock
    private SecurityManager securityManager;

    @InjectMocks
    private QuestionsService questionsService;

    @Test
    void getByLevelIndex() {

        // Настраиваем моки
        Integer levelIndex = 1;
        List<Word> mockWords = UtilClassForTests.generateWordsList(100);

        when(securityManager.isLevelAllowed(levelIndex)).thenReturn(true);
        when(wordService.findByLevelIndex(levelIndex)).thenReturn(mockWords);

        // Тест
        List<Question> result = questionsService.getByLevelIndex(levelIndex);

        // Проверяем
        assertNotNull(result);
        assertFalse(result.isEmpty());

        verify(securityManager).isLevelAllowed(levelIndex);
        verify(wordService).findByLevelIndex(levelIndex);
        verify(questionsCreator).createQuestions(mockWords);
    }

    @Test
    void testGetByLevelIndex_LevelIsNotAllowed() {

        // Настраиваем моки
        Integer levelIndex = 100;
        when(securityManager.isLevelAllowed(levelIndex)).thenReturn(false);

        // Тест
        List<Question> result = questionsService.getByLevelIndex(levelIndex);

        // Проверяем
        assertNull(result);
        verifyNoMoreInteractions(wordService);
    }

}