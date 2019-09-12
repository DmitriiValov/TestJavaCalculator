package ru.dvalov.calc.calculator.token;

public class Token {
    private TokenType type;
    private String value;
    private int index;

    public Token(TokenType type, String value, int index) {
        this.type = type;
        this.value = value;
        this.index = index;
    }

    public TokenType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public int getIndex() {
        return index;
    }
}