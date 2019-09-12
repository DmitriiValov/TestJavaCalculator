package ru.dvalov.calc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.dvalov.calc.calculator.exceptions.ParsingException;
import ru.dvalov.calc.calculator.operators.Expression;
import ru.dvalov.calc.calculator.parser.Parser;
import ru.dvalov.calc.database.Calculation;
import ru.dvalov.calc.database.CalculationRepository;

import java.util.Date;

@RestController
@RequestMapping("calc")
public class CalculatorController {

    @Autowired
    private CalculationRepository repository;

    @GetMapping
    public Double calc(@RequestParam String expression) throws ParsingException {

        Parser parser = new Parser();
        Expression exp = parser.parse(expression);
        double res = exp.evaluate();

        Calculation c = new Calculation();
        c.setDate(new Date());
        c.setExpression(expression);
        c.setResult(res);

        repository.save(c);
        return res;
    }
}
