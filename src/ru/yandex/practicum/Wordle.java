package ru.yandex.practicum;

import ru.yandex.practicum.exeption.InvalidWordLengthException;
import ru.yandex.practicum.exeption.NoMoreHintsException;
import ru.yandex.practicum.exeption.WordNotFoundInDictionaryException;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/*
в главном классе нам нужно:
    создать лог-файл (он должен передаваться во все классы)
    создать загрузчик словарей WordleDictionaryLoader
    загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
    затем создать игру WordleGame и передать ей словарь
    вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
    вывести состояние игры и конечный результат
 */
public class Wordle {

    public static final String FILE_NAME = "words_ru.txt";

    public static void main(String[] args) {


        // try-with-resources гарантирует закрытие лог-файла
        try (PrintWriter logger = new PrintWriter("wordle.log", StandardCharsets.UTF_8)) {
            logger.println("=== Запуск игры Wordle ===");

            // 1. Загрузка словаря
            WordleDictionaryLoader loader = new WordleDictionaryLoader(FILE_NAME, logger);
            WordleDictionary dictionary;
            try {
                dictionary = loader.loadWords();
            } catch (IOException e) {
                String msg = "Не удалось загрузить словарь: " + e.getMessage();
                logger.println("КРИТИЧЕСКАЯ ОШИБКА: " + msg);
                return;
            }

            // Проверка, что словарь не пуст
            if (dictionary.getAllWords().isEmpty()) {
                String msg = "Словарь пуст. Невозможно начать игру.";
                logger.println("КРИТИЧЕСКАЯ ОШИБКА: " + msg);
                return;
            }

            logger.println("Словарь загружен. Слов: " + dictionary.getAllWords().size());


            WordleGame game = new WordleGame(logger, dictionary);
            logger.println("Загадано слово: " + game.getAnswer());


            Scanner scanner = new Scanner(System.in);
            System.out.println("Добро пожаловать в Wordle!");
            System.out.println("Угадайте слово из 5 букв. У вас 6 попыток.");
            System.out.println("Для подсказки нажмите Enter.");

            while (game.isRunning()) {
                System.out.print("Введите слово: ");
                String input = scanner.nextLine().trim();

                // Пустой ввод — запрос подсказки
                if (input.isEmpty()) {
                    try {
                        String hint = game.getHint();
                        System.out.println("Подсказка: " + hint);
                    } catch (NoMoreHintsException e) {
                        System.out.println("Нет доступных подсказок.");
                        logger.println("Ошибка: " + e.getMessage());
                    }
                    continue;
                }

                // Попытка сделать ход
                try {
                    String pattern = game.makeMove(input);
                    System.out.println(pattern);
                    logger.println("Ход: '" + input + "' -> " + pattern);

                    if (game.isWin()) {
                        System.out.println("Поздравляем! Вы угадали слово '" + game.getAnswer() + "'!");
                        logger.println("Игрок победил.");
                        break;
                    }
                } catch (InvalidWordLengthException e) {
                    System.out.println("Ошибка: " + e.getMessage());
                    logger.println("Ошибка ввода: " + e.getMessage());
                } catch (WordNotFoundInDictionaryException e) {
                    System.out.println("Ошибка: " + e.getMessage());
                    logger.println("Ошибка ввода: " + e.getMessage());
                }
            }

            // Проверка на проигрыш
            if (game.isLose()) {
                System.out.println("Вы проиграли. Загаданное слово: " + game.getAnswer());
                logger.println("Игрок проиграл. Загаданное слово: " + game.getAnswer());
            }

            System.out.println("Спасибо за игру!");
            logger.println("=== Игра завершена ===");

        } catch (IOException e) {
            // Ошибка создания лог-файла
            System.err.println("Не удалось создать лог-файл: " + e.getMessage());
        }
    }

}
