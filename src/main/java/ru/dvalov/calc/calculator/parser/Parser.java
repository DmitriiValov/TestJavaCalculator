package ru.dvalov.calc.calculator.parser;

import ru.dvalov.calc.calculator.exceptions.ParsingException;
import ru.dvalov.calc.calculator.operators.Const;
import ru.dvalov.calc.calculator.operators.Expression;
import ru.dvalov.calc.calculator.operators.binary.*;
import ru.dvalov.calc.calculator.token.Token;
import ru.dvalov.calc.calculator.token.TokenType;
import ru.dvalov.calc.calculator.token.TokensReader;

public class Parser {

    private TokensReader tokens;
    private int lastLeftBracket = -1;

    public Expression parse(String expression) throws ParsingException {
        tokens = new TokensReader(expression);
        return expression(false);
    }

    private Expression expression(boolean isLeftBracketPresent) throws ParsingException {
        Expression acc = add();

        if (tokens.hasNext()) {
            Token token = tokens.next();

            switch (token.getType()) {
                case END:
                    if (isLeftBracketPresent) {
                        throw new ParsingException("no pairing closing parenthesis for opening parenthesis", lastLeftBracket);
                    }
                    break;
                case LB:
                    throw new ParsingException("unexpected ( found", token.getIndex());
                case RB:
                    if (!isLeftBracketPresent) {
                        throw new ParsingException("no pairing opening parenthesis for closing parenthesis", token.getIndex());
                    }
                    break;
                default:
                    throw new ParsingException("incorrect expression", token.getIndex());
            }
        }
        return acc;
    }

    private Expression add() throws ParsingException {
        Expression acc = term();

        while (tokens.hasNext()) {
            Token operation = tokens.next();

            switch (operation.getType()) {
                case PLUS:
                    acc = new Add(acc, term());
                    break;

                case MINUS:
                    acc = new Subtract(acc, term());
                    break;

                default:
                    tokens.previous();
                    return acc;
            }
        }
        return acc;
    }

    private Expression term() throws ParsingException {
        Expression acc = primary();

        while (tokens.hasNext()) {
            Token operation = tokens.next();

            switch (operation.getType()) {
                case MULT:
                    acc = new Multiply(acc, primary());
                    break;

                case DIV:
                    acc = new Divide(acc, primary());
                    break;

                case POW:
                    acc = new Pow(acc, primary());
                    break;

                default:
                    tokens.previous();
                    return acc;
            }
        }
        return acc;
    }

    private Expression primary() throws ParsingException {
        Token token = tokens.next();
        Expression primary = null;

        switch (token.getType()) {
            case END:
            case RB:
                if (tokens.isFirst()) {
                    throw new ParsingException("no pairing opening parenthesis for closing parenthesis", token.getIndex());
                }
                if (tokens.previous().getType() == TokenType.LB) {
                    if (token.getType() == TokenType.RB) {
                        throw new ParsingException(String.format("empty parenthesis sequence at positions %d - %d", lastLeftBracket, token.getIndex()));
                    }
                    else {
                        throw new ParsingException("no pairing closing parenthesis for opening parenthesis", lastLeftBracket);
                    }
                }
                throw new ParsingException("no last argument for operator " + tokens.current().getValue(), token.getIndex());

            case CONST:
                primary = Const.valueOf(token.getValue(), token.getIndex());
                break;

            case MINUS:
                if (tokens.hasNext() && tokens.next().getType() == TokenType.CONST) {
                    Token number = tokens.current();
                    primary = Const.valueOf("-" + number.getValue(), token.getIndex());
                } else {
                    tokens.previous();
                    //primary = new CheckedNegate(primary());
                }
                break;

            case LB:
                lastLeftBracket = token.getIndex();
                primary = expression(true);
                if (!(tokens.current().getType() == TokenType.RB)) {
                    throw new ParsingException("no pairing closing parenthesis for opening parenthesis", lastLeftBracket);
                }
                return primary;

            default:
                if (token.getIndex() == 0) {
                    throw new ParsingException("no first argument for operator " + token.getValue(), token.getIndex());
                }
                Token prev = tokens.previous();
                if (prev.getType() == TokenType.LB || prev.getType() == TokenType.MINUS) {
                    throw new ParsingException("no first argument for operator " + token.getValue(), token.getIndex());
                }
                throw new ParsingException(String.format("no middle argument between operator %s " +
                                "at position %d and operator %s",
                        prev.getValue(), prev.getIndex() + 1, token.getValue()), token.getIndex());
        }

        return primary;
    }
}
