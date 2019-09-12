package ru.dvalov.calc.calculator.operators;

import ru.dvalov.calc.calculator.exceptions.ConstantOverflowException;

public class Const implements Expression {
    public static final Const MINUS_ONE = new Const(-1.0);
    public static final Const ZERO = new Const(0.0);

    private Double val;

    public Const(Double val) {
        this.val = val;
    }

    public static Const valueOf(String val, int idx) throws ConstantOverflowException {
        try {
            Double number = Double.parseDouble(val);
            return new Const(number);
        } catch (NumberFormatException e) {
            throw new ConstantOverflowException(val, idx);
        }
    }

    @Override
    public Double evaluate() {
        return val;
    }
}