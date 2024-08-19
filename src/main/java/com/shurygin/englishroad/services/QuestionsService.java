package com.shurygin.englishroad.services;

import com.shurygin.englishroad.dto.Question;
import com.shurygin.englishroad.model.Word;
import com.shurygin.englishroad.repositories.TranslationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class QuestionsService {

    public static final int OPTIONS_NUMBER = 2;

    private final WordService wordService;
    private final TranslationRepository translationRepository;

    @Autowired
    public QuestionsService(WordService wordService, TranslationRepository translationRepository) {
        this.wordService = wordService;
        this.translationRepository = translationRepository;
    }

    public List<Question> getByLevelIndex(Integer levelIndex, Integer count) {

        List<Word> words = wordService.findByLevelIndex(levelIndex);
        //List<Translation> allTranslations = translationRepository.findAll();
        Collections.shuffle(words);
        //PrimitiveIterator.OfInt randomIterator = new Random().ints(0, words.size()).iterator();

        count = count <= 0 ? Integer.MAX_VALUE : count;
        List<Question> questions = words.stream()
            .map(word -> getQuestion(word, words))
            .limit(count).toList();

        return questions;
    }

    private static Question getQuestion(Word word, List<Word> words) {

        String correctOption = word.getRandomTranslation();

        List<String> options = new ArrayList<>(OPTIONS_NUMBER);
        options.add(correctOption);

        while (options.size() < OPTIONS_NUMBER) {

            String option = words.get((int) (Math.random() * words.size())).getRandomTranslation();

            if (options.contains(option) || word.getTranslationsList().contains(option))
                continue;

            options.add(option);
            //question.tryAddOption(allTranslations.get(randomIterator.nextInt()));
        }

        return new Question(word, options, correctOption);
    }
}
