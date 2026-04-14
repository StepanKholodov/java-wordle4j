package ru.yandex.practicum.exeption;

public class NoMoreHintsException extends Exception {
    public NoMoreHintsException() {
    }

    public NoMoreHintsException(String message) {
        super(message);
    }
}
