package ru.dvalov.calc.calculator.exceptions;

public class DivideByZeroException extends ArithmeticException {
    public DivideByZeroException(int left) {
        super(String.format("division by zero %d / 0", left));
    }
}