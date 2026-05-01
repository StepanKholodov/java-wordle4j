package ru.yandex.practicum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */
public class WordleDictionary {

    private final List<String> words;

    public WordleDictionary(List<String> words) {
        this.words = words;
    }

    public void addWord(String word) {
        String normalized = word.trim().toLowerCase().replace('ё', 'е');
        words.add(normalized);
    }

    public List<String> getAllWords() {
        return new ArrayList<>(words);
    }

    public String getWord(int index) {
        return words.get(index);
    }

    public String getRandomWord() {
        Random random = new Random();
        int index = random.nextInt(words.size());
        return words.get(index);
    }

    public boolean containsWord(String word) {
        return words.contains(word);
    }

    public static String getMatchPattern(String secret, String guess) {

        char[] secretArrayChar = secret.toCharArray();
        char[] result = new char[secretArrayChar.length];


        Arrays.fill(result, '-');

        guess = guess.trim().toLowerCase().replace('ё', 'е');


        for (int i = 0; i < secretArrayChar.length; i++) {
            if (guess.charAt(i) == secretArrayChar[i]) {
                result[i] = '+';
                secretArrayChar[i] = '_';
            }
        }

        for (int i = 0; i < result.length; i++) {

            if (result[i] != '+') {
                for (int j = 0; j < secretArrayChar.length; j++) {
                    if (secretArrayChar[j] != '_' && guess.charAt(i) == secretArrayChar[j]) {
                        result[i] = '^';
                        secretArrayChar[j] = '_';
                        break;
                    }
                }

            }

        }

        return new String(result);
    }

}
