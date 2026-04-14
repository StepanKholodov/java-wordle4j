package ru.yandex.practicum;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
 */
public class WordleDictionaryLoader {
    private final String fileName;
    private final PrintWriter logger;

    public WordleDictionaryLoader(String fileName, PrintWriter logger) {
        this.fileName = fileName;
        this.logger = logger;
    }


    public WordleDictionary loadWords() throws IOException {
        logger.println("Начало загрузки словаря из файла: " + fileName);
        WordleDictionary wordleDictionary = new WordleDictionary(new ArrayList<>());
        int total = 0;
        int loaded = 0;
        try (BufferedReader br = new BufferedReader(
                new FileReader(fileName, StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                total++;
                String normalized = line.trim().toLowerCase().replace('ё', 'е');
                if (normalized.length() == 5) {
                    wordleDictionary.addWord(normalized);
                    loaded++;
                }
            }
        }
        logger.println("Всего прочитано строк: " + total);
        logger.println("Загружено слов (длина 5): " + loaded);
        return wordleDictionary;
    }
}
