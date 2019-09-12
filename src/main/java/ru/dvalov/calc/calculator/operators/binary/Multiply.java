package ru.dvalov.calc.calculator.operators.binary;

import ru.dvalov.calc.calculator.operators.BinaryOperator;
import ru.dvalov.calc.calculator.operators.Expression;

public class Multiply extends BinaryOperator {
    public Multiply(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected Double evaluateImpl(Double left, Double right) {
        return left * right;
    }
}