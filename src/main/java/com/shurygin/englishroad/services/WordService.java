package com.shurygin.englishroad.services;

import com.shurygin.englishroad.model.Word;
import com.shurygin.englishroad.repositories.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class WordService {

    private final WordRepository wordRepository;

    @Autowired
    public WordService(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    public List<Word> findByLevelId(Integer levelId) {
        return wordRepository.findByLevelIndex(levelId);
    }

}
