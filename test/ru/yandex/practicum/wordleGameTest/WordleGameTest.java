package ru.yandex.practicum.wordleGameTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.WordleDictionary;
import ru.yandex.practicum.WordleGame;
import ru.yandex.practicum.exeption.InvalidWordLengthException;
import ru.yandex.practicum.exeption.NoMoreHintsException;
import ru.yandex.practicum.exeption.WordNotFoundInDictionaryException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordleGameTest {

    private WordleDictionary dictionary;
    private PrintWriter logger;
    private WordleGame game;

    @BeforeEach
    void setUp() {
        dictionary = new WordleDictionary(List.of("кошка", "мышка", "лошадь", "слово", "банан"));
        logger = new PrintWriter(new StringWriter());
        game = new WordleGame("кошка", dictionary, logger);
    }

    // --- makeMove ---

    @Test
    void makeMoveCorrectWordReturnsAllPlus() throws Exception {
        String pattern = game.makeMove("кошка");
        assertEquals("+++++", pattern);
    }

    @Test
    void makeMoveWrongLengthThrowsInvalidWordLengthException() {
        assertThrows(InvalidWordLengthException.class, () -> game.makeMove("кот"));
    }

    @Test
    void makeMove_wordNotInDictionary_throwsWordNotFoundInDictionaryException() {
        assertThrows(WordNotFoundInDictionaryException.class, () -> game.makeMove("абвгд"));
    }

    @Test
    void makeMoveTrimsAndLowercases() throws Exception {
        String pattern = game.makeMove("  КОШКА  ");
        assertEquals("+++++", pattern);
    }


    @Test
    void makeMoveAddsToHistory() throws Exception {
        game.makeMove("мышка");
        assertTrue(game.getHistory().containsKey("мышка"));
    }

    @Test
    void makeMoveDecreasesSteps() throws Exception {
        for (int i = 0; i < 6; i++) {
            game.makeMove("мышка");
        }
        assertTrue(game.isLose());
    }

    // --- isWin / isLose / isRunning ---

    @Test
    void isWinAfterCorrectGuessReturnsTrue() throws Exception {
        game.makeMove("кошка");
        assertTrue(game.isWin());
    }

    @Test
    void isWinAfterWrongGuessReturnsFalse() throws Exception {
        game.makeMove("мышка");
        assertFalse(game.isWin());
    }

    @Test
    void isLoseAfterSixWrongGuessesReturnsTrue() throws Exception {
        for (int i = 0; i < 6; i++) {
            game.makeMove("мышка");
        }
        assertTrue(game.isLose());
    }

    @Test
    void isLoseAfterCorrectGuessReturnsFalse() throws Exception {
        game.makeMove("кошка");
        assertFalse(game.isLose());
    }

    @Test
    void isRunningInitiallyReturnsTrue() {
        assertTrue(game.isRunning());
    }

    @Test
    void isRunningAfterWinReturnsFalse() throws Exception {
        game.makeMove("кошка");
        assertFalse(game.isRunning());
    }

    @Test
    void isRunningAfterSixWrongGuessesReturnsFalse() throws Exception {
        for (int i = 0; i < 6; i++) {
            game.makeMove("мышка");
        }
        assertFalse(game.isRunning());
    }

    // --- getHint ---

    @Test
    void getHintReturnsWordFromDictionary() throws Exception {
        game.makeMove("мышка"); // добавим историю
        String hint = game.getHint();
        assertNotNull(hint);
        assertTrue(dictionary.getAllWords().contains(hint));
    }

    @Test
    void getHintDoesNotReturnAlreadyGuessedWord() throws Exception {
        game.makeMove("мышка");
        String hint = game.getHint();
        assertNotEquals("мышка", hint);
    }

    @Test
    void getHintWhenNoSuitableWordsThrowsNoMoreHintsException() throws Exception {
        game.makeMove("кошка");
        assertThrows(NoMoreHintsException.class, () -> game.getHint());
    }
}