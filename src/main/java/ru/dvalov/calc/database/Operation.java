package ru.dvalov.calc.database;

import ru.dvalov.calc.calculator.token.TokenType;

import javax.persistence.*;

@Entity
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private TokenType type;
    @OneToOne
    private Calculation calculation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public Calculation getCalculation() {
        return calculation;
    }

    public void setCalculation(Calculation calculation) {
        this.calculation = calculation;
    }
}
