package ru.dvalov.calc.calculator.operators.binary;

import ru.dvalov.calc.calculator.operators.BinaryOperator;
import ru.dvalov.calc.calculator.operators.Expression;

public class Pow extends BinaryOperator {
    public Pow(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected Double evaluateImpl(Double left, Double right) {
        return Math.pow(left, right);
    }
}