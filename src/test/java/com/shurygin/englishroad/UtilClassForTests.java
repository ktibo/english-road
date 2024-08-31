package com.shurygin.englishroad;

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

}
