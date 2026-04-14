package ru.yandex.practicum.wordleDictionaryTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.WordleDictionary;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WordleDictionaryMatchPatternTest {

    @Test
    @DisplayName("Все буквы на своих местах → все '+'")
    void allCorrectLetters() {
        String pattern = WordleDictionary.getMatchPattern("герой", "герой");
        assertEquals("+++++", pattern);
    }

    @Test
    @DisplayName("Все буквы не на своих местах и без повторений → все '^'")
    void allLettersMisplacedNoDuplicates() {
        String pattern = WordleDictionary.getMatchPattern("герой", "ойгер");
        assertEquals("^^^^^", pattern);
    }

    @Test
    @DisplayName("Букв нет в загаданном слове → все '-'")
    void noMatchingLetters() {
        String pattern = WordleDictionary.getMatchPattern("герой", "лампа");
        assertEquals("-----", pattern);
    }

    @Test
    @DisplayName("Смешанный случай с точными и не на своих местах")
    void mixedMatches() {
        String pattern = WordleDictionary.getMatchPattern("герой", "гонец");
        assertEquals("+^-^-", pattern);
    }

    @Test
    @DisplayName("Повторяющаяся буква в загаданном слове одна, а в догадке две → вторая помечается '-'")
    void repeatedLetterInGuessButOnlyOneInSecret() {
        String pattern = WordleDictionary.getMatchPattern("арбуз", "баран");
        // 'а' на позиции 1 (вторая буква) - зелёная? Проверим:
        // secret: а р б у з
        // guess:  б а р а н
        // Позиции: 0:'б' (есть на 2 месте) -> '^'
        //          1:'а' (есть на 0 месте) -> '^'
        //          2:'р' (есть на 1 месте) -> '^'
        //          3:'а' (вторая 'а', но secret уже использовал 'а') -> '-'
        //          4:'н' (нет) -> '-'
        // Ожидаем: "^^^--"
        assertEquals("^^^--", pattern);
    }

    @Test
    @DisplayName("Две одинаковые буквы в загаданном слове, обе на правильных местах")
    void duplicateLettersBothCorrect() {
        String pattern = WordleDictionary.getMatchPattern("кабан", "кабан");
        assertEquals("+++++", pattern);
    }

    @Test
    @DisplayName("Две одинаковые буквы в загаданном слове: одна на месте, вторая на другом")
    void duplicateLettersOneCorrectOneMisplaced() {
        String pattern = WordleDictionary.getMatchPattern("кабан", "атака");
        assertEquals("^-^^-", pattern);
    }

    @Test
    @DisplayName("Буквы 'ё' и 'е' должны считаться одинаковыми (нормализация должна быть до вызова)")
    void normalizationYoAndYe() {

        String pattern = WordleDictionary.getMatchPattern("елкаa", "ёлкаa");
        assertEquals("+++++", pattern);
    }

    @Test
    @DisplayName("Случай с разным регистром (метод должен работать с нормализованными строками)")
    void caseInsensitiveIfNormalized() {
        String pattern = WordleDictionary.getMatchPattern("герой", "ГЕРОЙ");
        assertEquals("+++++", pattern);
    }
}