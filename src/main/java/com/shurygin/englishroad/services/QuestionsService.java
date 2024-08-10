package com.shurygin.englishroad.services;

import com.shurygin.englishroad.model.Question;
import com.shurygin.englishroad.model.Translation;
import com.shurygin.englishroad.model.Word;
import com.shurygin.englishroad.repositories.TranslationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.IntStream;

@Service
@Transactional(readOnly = true)
public class QuestionsService {

    private final WordService wordService;
    private final TranslationRepository translationRepository;

    @Autowired
    public QuestionsService(WordService wordService, TranslationRepository translationRepository) {
        this.wordService = wordService;
        this.translationRepository = translationRepository;
    }

    public List<Question> getByLevel(Integer levelIndex, Integer count) {

        List<Word> words = wordService.findByLevelId(levelIndex);
        List<Translation> allTranslations = translationRepository.findAll();

        Collections.shuffle(words);

        PrimitiveIterator.OfInt randomIterator = new Random().ints(0, allTranslations.size()).iterator();

        List<Question> questions = words.stream()
            .map(word -> {
                Question question = new Question(word);
                Translation correctOption = word.getRandomTranslation();
                question.setCorrectOption(correctOption);
                question.tryAddOption(correctOption);
                while (question.getOptions().size() < Question.COUNT_OPTIONS) {
                    question.tryAddOption(allTranslations.get(randomIterator.nextInt()));
                }
                return question;
            })
            .limit(count).toList();

        return questions;
    }
}
