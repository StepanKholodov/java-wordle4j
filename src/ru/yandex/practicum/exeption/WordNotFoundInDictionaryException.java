package ru.yandex.practicum.exeption;

public class WordNotFoundInDictionaryException extends Exception{

    public WordNotFoundInDictionaryException() {
    }

    public WordNotFoundInDictionaryException(String message) {
        super(message);
    }
}
