package ru.dvalov.calc.database;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Calculation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String expression;
    private Double result;
    private Date date;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "calculation_id", nullable = false)
    private List<Operation> operations;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "calculation_id", nullable = false)
    private List<Constant> constants;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

    public List<Constant> getConstants() {
        return constants;
    }

    public void setConstants(List<Constant> constants) {
        this.constants = constants;
    }
}
