package ru.yandex.practicum.exeption;

public class InvalidWordLengthException extends RuntimeException {
  public InvalidWordLengthException(String message) {
    super(message);
  }
}
