package com.shurygin.englishroad.services;

import com.shurygin.englishroad.QuestionsCreator;
import com.shurygin.englishroad.dto.Question;
import com.shurygin.englishroad.model.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class QuestionsService {

    private final WordService wordService;
    private final QuestionsCreator questionsCreator;

    @Autowired
    public QuestionsService(WordService wordService, QuestionsCreator questionsCreator) {
        this.wordService = wordService;
        this.questionsCreator = questionsCreator;
    }

    public List<Question> getByLevelIndex(Integer levelIndex) {
        List<Word> words = wordService.findByLevelIndex(levelIndex);
        return questionsCreator.createQuestions(words);
    }

}
