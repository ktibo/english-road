package com.shurygin.englishroad;

import com.shurygin.englishroad.dto.Question;
import com.shurygin.englishroad.model.Level;
import com.shurygin.englishroad.model.Word;

import java.util.ArrayList;
import java.util.List;

public class UtilClassForTests {

    public static List<Word> generateWordsList(int numberOfWords) {
        List<Word> words = new ArrayList<>();
        for (int i = 1; i <= numberOfWords; i++) {
            words.add(new Word("word"+i, i, "trnsc"+i, "tr"+i+"_1, tr"+i+"_2, tr"+i+"_3"));
        }
        return words;
    }

    public static List<Question> generateQuestionsList(int numberOfQuestions) {

        List<Question> questions = new ArrayList<>();
        List<Word> words = generateWordsList(numberOfQuestions);
        for (Word word : words) {
            questions.add(new Question(word, List.of("option1", "option2", "option3", "option4"), "option1"));
        }
        return questions;
    }

    public static List<Level> generateLevelsList(int numberOfLevels) {

        List<Level> levels = new ArrayList<>();
        for (int i = 1; i <= numberOfLevels; i++) {
            levels.add(new Level(i, "Уровень"+i, "описание уровня "+i, i*10, i*10+9));
        }
        return levels;
    }

}
