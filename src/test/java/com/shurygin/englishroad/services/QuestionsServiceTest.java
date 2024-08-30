package com.shurygin.englishroad.services;

import com.shurygin.englishroad.dto.Question;
import com.shurygin.englishroad.model.Word;
import com.shurygin.englishroad.util.QuestionsCreator;
import com.shurygin.englishroad.util.SecurityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
        List<Word> mockWords = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            mockWords.add(new Word("example"+i, i, "trnscr"+i, "trnsl"+i));
        }

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