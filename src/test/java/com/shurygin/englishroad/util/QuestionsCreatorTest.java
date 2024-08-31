package com.shurygin.englishroad.util;

import com.shurygin.englishroad.UtilClassForTests;
import com.shurygin.englishroad.dto.Question;
import com.shurygin.englishroad.model.Word;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class QuestionsCreatorTest {

    @Spy
    private QuestionsCreator questionsCreator;

    @Test
    void createQuestions() {

        // Настройки
        Word word = new Word("test", 100, "trnsc", "test_1, test_2, test_3");
        List<Word> mockWords = UtilClassForTests.generateWordsList(19);
        mockWords.add(word);

        // Тест
        List<Question> questions = questionsCreator.createQuestions(mockWords);

        // Проверяем
        List<Question> testList = questions.stream().filter(question -> question.getWord() == word).toList();

        assertEquals(1, testList.size());

        List<String> translationsList = word.getTranslationsList();
        assertTrue(translationsList.contains(testList.get(0).getCorrectOption()));

    }

    @Test
    void createQuestions_Exceptions() {

        // Настройки
        List<Word> mockWordsSmall = UtilClassForTests.generateWordsList(19);

        List<Word> mockWordsNoTranslation = UtilClassForTests.generateWordsList(19);
        Word word = new Word("test", 100, "trnsc", ""); // Слово без перевода
        mockWordsNoTranslation.add(word);

        // Проверяем
        assertThrows(RuntimeException.class, () -> questionsCreator.createQuestions(mockWordsSmall));
        assertThrows(RuntimeException.class, () -> questionsCreator.createQuestions(mockWordsNoTranslation));

    }

}