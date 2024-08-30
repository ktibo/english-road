package com.shurygin.englishroad.services;

import com.shurygin.englishroad.model.Word;
import com.shurygin.englishroad.repositories.WordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WordServiceTest {

    @Mock
    private WordRepository wordRepository;

    @InjectMocks
    private WordService wordService;

    @Test
    void findByLevelIndex() {

        // Настраиваем мок репозитория
        Integer levelIndex = 1;
        Word word1 = new Word("example1", 1, "tr1", null);
        Word word2 = new Word("example2", 2, "tr2", null);
        List<Word> mockWords = List.of(word1, word2);
        when(wordRepository.findByLevelIndex(levelIndex)).thenReturn(mockWords);

        // Тест
        List<Word> result = wordService.findByLevelIndex(levelIndex);

        // Проверяем
        assertEquals(2, result.size());
        assertTrue(result.containsAll(mockWords));

        verify(wordRepository, times(1)).findByLevelIndex(levelIndex);

    }

    @Test
    public void testFindByLevelIndex_NoResults() {

        // Настраиваем мок репозитория
        Integer levelIndex = 1;
        when(wordRepository.findByLevelIndex(levelIndex)).thenReturn(List.of());

        // Тест
        List<Word> result = wordService.findByLevelIndex(levelIndex);

        // Проверяем
        assertTrue(result.isEmpty());

        verify(wordRepository, times(1)).findByLevelIndex(levelIndex);

    }

}