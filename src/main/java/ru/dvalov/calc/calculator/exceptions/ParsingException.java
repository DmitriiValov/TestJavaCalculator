package ru.dvalov.calc.calculator.exceptions;

public class ParsingException extends Exception {

    public ParsingException(String message) {
        super(message);
    }

    public ParsingException(String message, int index) {
        super(String.format("%s at position %d", message, index + 1));
    }
}