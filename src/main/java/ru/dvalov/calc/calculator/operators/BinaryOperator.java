package ru.dvalov.calc.calculator.operators;

public abstract class BinaryOperator implements Expression {
    private Expression left;
    private Expression right;

    public BinaryOperator(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    protected abstract Double evaluateImpl(Double left, Double right);

    @Override
    public Double evaluate() {
        return evaluateImpl(left.evaluate(), right.evaluate());
    }
}