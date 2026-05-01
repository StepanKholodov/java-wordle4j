package ru.yandex.practicum.wordleDictionaryTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.WordleDictionary;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class WordleDictionaryTest {

    private WordleDictionary wordleDictionary;

    @BeforeEach
    void setUp() {
        wordleDictionary = new WordleDictionary(new ArrayList<>());
    }


    @Test
    @DisplayName("Слово с пробелами в начале и конце добавляется без них")
    void shouldTrimWhitespace() {
        wordleDictionary.addWord(" герой ");
        assertEquals("герой", wordleDictionary.getWord(0));
    }

    @Test
    @DisplayName("Слово переводится в нижний регистр")
    void shouldConvertToLowerCase() {
        wordleDictionary.addWord("ГЕРОЙ");
        assertEquals("герой", wordleDictionary.getWord(0));
    }

    @Test
    @DisplayName("Буква 'ё' заменяется на 'е'")
    void shouldReplaceYoWithYe() {
        wordleDictionary.addWord("ёлка");
        assertTrue(wordleDictionary.containsWord("елка"));
        assertFalse(wordleDictionary.containsWord("ёлка"));
    }

    @Test
    @DisplayName("Слово из одной буквы 'ё' становится 'е'")
    void shouldHandleSingleYo() {
        wordleDictionary.addWord("ё");
        assertEquals("е", wordleDictionary.getWord(0));
    }


    @Test
    @DisplayName("getWord возвращает слово по индексу")
    void getWordShouldReturnCorrectWord() {
        wordleDictionary.addWord("герой");
        assertEquals("герой", wordleDictionary.getWord(0));
    }

    @Test
    @DisplayName("getWord с неверным индексом выбрасывает IndexOutOfBoundsException")
    void getWordShouldThrowWhenIndexOutOfBounds() {
        wordleDictionary.addWord("герой");
        assertThrows(IndexOutOfBoundsException.class, () -> wordleDictionary.getWord(1));
    }

    @Test
    @DisplayName("containsWord возвращает true для существующего слова")
    void containsWordShouldReturnTrueForExisting() {
        wordleDictionary.addWord("герой");
        assertTrue(wordleDictionary.containsWord("герой"));
    }

    @Test
    @DisplayName("containsWord возвращает false для отсутствующего слова")
    void containsWordShouldReturnFalseForMissing() {
        wordleDictionary.addWord("герой");
        assertFalse(wordleDictionary.containsWord("лампа"));
    }

}