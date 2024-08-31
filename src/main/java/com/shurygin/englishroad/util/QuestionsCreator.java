package com.shurygin.englishroad.util;

import com.shurygin.englishroad.dto.Question;
import com.shurygin.englishroad.model.Word;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@PropertySource("classpath:game.properties")
public class QuestionsCreator {

    @Value("${questions.number_of_questions_per_round}")
    private int number_of_questions_per_round = 20;

    @Value("${questions.number_of_options}")
    private int number_of_options = 4;

    public List<Question> createQuestions (List<Word> words) {

        if (words.size() < number_of_questions_per_round)
            throw new RuntimeException("Not enough words!");

        Collections.shuffle(words);

        List<Question> questions = words.stream()
            .map(word -> createQuestion(word, words))
            .limit(number_of_questions_per_round).toList();

        return questions;

    }

    private Question createQuestion(Word word, List<Word> words) {

        String correctOption = getOption(word);

        if (correctOption.isEmpty()) throw new RuntimeException(String.format("Word '%s' has no translations!", word));

        List<String> options = new ArrayList<>(number_of_options);
        options.add(correctOption);

        while (options.size() < number_of_options) {

            Word randomWord = words.get((int) (Math.random() * words.size()));
            String option = getOption(randomWord);

            if (option.isEmpty() || options.contains(option) || word.getTranslationsList().contains(option))
                continue;

            options.add(option);

        }

        Collections.shuffle(options);

        return new Question(word, options, correctOption);
    }

    private String getOption(Word word) {
        List<String> translations = word.getTranslationsList();
        if (translations.size() == 0) return "";
        return translations.get(0);
    }

}
