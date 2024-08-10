package com.shurygin.englishroad.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
//@RequiredArgsConstructor
public class Question {

    public static final int COUNT_OPTIONS = 2;

    private final Word word;

    private List<Translation> options = new ArrayList<>(COUNT_OPTIONS);

    private Translation correctOption;

    public void tryAddOption(Translation option) {
        if (!options.contains(option))
            options.add(option);
    }

}
