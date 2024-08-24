package com.shurygin.englishroad.services;

import com.shurygin.englishroad.util.QuestionsCreator;
import com.shurygin.englishroad.dto.Question;
import com.shurygin.englishroad.model.Word;
import com.shurygin.englishroad.util.SecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class QuestionsService {

    private final WordService wordService;
    private final QuestionsCreator questionsCreator;
    private final SecurityManager securityManager;

    @Autowired
    public QuestionsService(WordService wordService, QuestionsCreator questionsCreator, SecurityManager securityManager) {
        this.wordService = wordService;
        this.questionsCreator = questionsCreator;
        this.securityManager = securityManager;
    }

    public List<Question> getByLevelIndex(Integer levelIndex) {

        if (!securityManager.isLevelAllowed(levelIndex)) return null;

        List<Word> words = wordService.findByLevelIndex(levelIndex);
        return questionsCreator.createQuestions(words);
    }

}
