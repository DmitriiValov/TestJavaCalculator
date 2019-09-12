package ru.dvalov.calc.calculator.token;


import ru.dvalov.calc.calculator.exceptions.InvalidTokenException;
import ru.dvalov.calc.calculator.exceptions.ParsingException;

import java.util.ArrayList;

public class TokensReader {
    private ArrayList<Token> tokens = new ArrayList<>();
    private int current = -1;
    private String expression;

    public TokensReader(String expression) throws ParsingException {
        this.expression = expression;
        read(expression);
        tokens.add(new Token(TokenType.END, "end of expression", expression.length()));
        if (tokens.size() == 1) {
            throw new ParsingException("empty expression");
        }
    }

    public boolean hasNext() {
        return current < tokens.size() - 1;
    }

    public Token next() {
        return tokens.get(++current);
    }

    public Token previous() {
        return tokens.get(--current);
    }

    public Token current() {
        return tokens.get(current);
    }

    public boolean isFirst() {
        return current == 0;
    }

    private void read(String s) throws InvalidTokenException {
        s = s.replace(" ", "");
        for (int i = 0; i < s.length(); i++) {
            switch (s.charAt(i)) {
                case '(':
                    tokens.add(new Token(TokenType.LB, "(", i));
                    break;

                case ')':
                    tokens.add(new Token(TokenType.RB, ")", i));
                    break;

                case '+':
                    tokens.add(new Token(TokenType.PLUS, "+", i));
                    break;

                case '-':
                    tokens.add(new Token(TokenType.MINUS, "-", i));
                    break;

                case '*':
                    tokens.add(new Token(TokenType.MULT, "*", i));
                    break;

                case '/':
                    tokens.add(new Token(TokenType.DIV, "/", i));
                    break;

                case '^':
                    tokens.add(new Token(TokenType.POW, "^", i));
                    break;

                default:
                    if (!Character.isDigit(s.charAt(i))) {
                        throw new InvalidTokenException(s.charAt(i), i);
                    }

                    int j = i;
                    while (j < s.length() && (Character.isDigit(s.charAt(j)) || s.charAt(j) == '.')) {
                        j++;
                    }
                    String number = s.substring(i, j);
                    tokens.add(new Token(TokenType.CONST, number, i));
                    i = j - 1;
            }
        }
    }
}