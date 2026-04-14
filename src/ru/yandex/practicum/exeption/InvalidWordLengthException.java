package ru.yandex.practicum.exeption;

public class InvalidWordLengthException extends Exception {

    public InvalidWordLengthException() {
    }

    public InvalidWordLengthException(String message) {
        super(message);
    }
}
