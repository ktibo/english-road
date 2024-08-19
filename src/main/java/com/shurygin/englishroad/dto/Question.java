package com.shurygin.englishroad.dto;

import com.shurygin.englishroad.model.Translation;
import com.shurygin.englishroad.model.Word;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Question {

    private final Word word;

    private final List<String> options;

    private final String correctOption;

}
