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


    public WordleDictionary loadWords() throws IOException{
        logger.println("Начало загрузки словаря из файла: " + fileName);
        WordleDictionary wordleDictionary = new WordleDictionary(new ArrayList<>());
        int count = 0;
        try (BufferedReader bufferedReader = new BufferedReader(
                new FileReader(fileName, StandardCharsets.UTF_8))) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                wordleDictionary.addWord(line);
                count++;
            }

        }
        logger.println("Загружено слов: " + count);
        return wordleDictionary;
    }
}
