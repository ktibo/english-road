package com.shurygin.englishroad.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordTest {

    @Test
    void getTranslationsList() {

        Word word = new Word("example", 1, "ɪɡˈzæmpl", "пример, образец, образ");

        List<String> expectedList = List.of("пример", "образец", "образ");

        assertEquals(expectedList, word.getTranslationsList());

    }
}