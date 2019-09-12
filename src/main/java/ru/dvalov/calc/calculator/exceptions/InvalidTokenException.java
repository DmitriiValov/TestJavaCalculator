package ru.dvalov.calc.calculator.exceptions;

public class InvalidTokenException extends ParsingException {

    public InvalidTokenException(char message, int index) {
        super(String.format("unknown token %s at position %d", message, index + 1));
    }
    public InvalidTokenException(String message, int index) {
        super(String.format("unknown token %s at position %d", message, index + 1));
    }
}