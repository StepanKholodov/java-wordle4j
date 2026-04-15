package ru.yandex.practicum;

import ru.yandex.practicum.exeption.InvalidWordLengthException;
import ru.yandex.practicum.exeption.NoMoreHintsException;
import ru.yandex.practicum.exeption.WordNotFoundInDictionaryException;

import java.io.PrintWriter;
import java.util.*;


public class WordleGame {

    public static final int WORD_LENGTH = 5;
    public static final int MAX_ATTEMPTS = 6;
    public static final String WIN_PATTERN = "+++++";

    private final String answer;

    private int steps;

    private final WordleDictionary dictionary;

    private final PrintWriter logger;

    private final Map<String, String> history;

    public WordleGame(String answer, WordleDictionary dictionary, PrintWriter logger) {
        this.answer = answer;
        this.dictionary = dictionary;
        this.logger = logger;
        this.steps = MAX_ATTEMPTS;
        this.history = new LinkedHashMap<>();
    }

    public WordleGame(PrintWriter logger, WordleDictionary dictionary) {
        this.logger = logger;
        this.dictionary = dictionary;
        this.steps = MAX_ATTEMPTS;
        this.answer = dictionary.getRandomWord();
        this.history = new LinkedHashMap<>();
    }

    public Map<String, String> getHistory() {
        return history;
    }

    public String makeMove(String userWord) throws InvalidWordLengthException, WordNotFoundInDictionaryException {
        userWord = userWord.trim().toLowerCase().replace('ё', 'е');

        if (userWord.length() != WORD_LENGTH) {
            throw new InvalidWordLengthException("Длина введенного слова не равна " + WORD_LENGTH);
        }

        if (!dictionary.containsWord(userWord)) {
            throw new WordNotFoundInDictionaryException("Такого слова нет в базе программы");
        }

        steps--;
        String pattern = WordleDictionary.getMatchPattern(answer, userWord);
        history.put(userWord, pattern);
        logger.println("Сделана попытка угадать");

        return pattern;

    }

    public boolean isWin() {
        return history.containsValue(WIN_PATTERN);
    }

    public boolean isLose() {
        return steps == 0 && !isWin();
    }

    public boolean isRunning() {
        return steps > 0 && !isWin();
    }


    public String getHint() throws NoMoreHintsException {
        List<String> allWords = dictionary.getAllWords();

        List<String> suitable = new ArrayList<>();
        for (String word : allWords) {
            if (word.length() != WORD_LENGTH) continue;
            if (history.containsKey(word)) continue;
            if (matchesAllHistory(word)) suitable.add(word);
        }

        if (suitable.isEmpty()) {
            logger.println("Ошибка: не найдено подходящих слов для подсказки!");
            throw new NoMoreHintsException("Нет подходящих слов для подсказки");
        }

        Random random = new Random();
        return suitable.get(random.nextInt(suitable.size()));
    }

    private boolean matchesAllHistory(String candidate) {
        for (Map.Entry<String, String> entry : history.entrySet()) {
            String guess = entry.getKey();
            String expectedPattern = entry.getValue();
            String actualPattern = WordleDictionary.getMatchPattern(candidate, guess);
            if (!actualPattern.equals(expectedPattern)) return false;
        }
        return true;
    }

    public String getAnswer() {
        return answer;
    }
}
