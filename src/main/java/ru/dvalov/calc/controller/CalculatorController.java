package ru.dvalov.calc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.dvalov.calc.calculator.exceptions.ParsingException;
import ru.dvalov.calc.calculator.operators.Expression;
import ru.dvalov.calc.calculator.parser.Parser;

@RestController
@RequestMapping("calc")
public class CalculatorController {
    @GetMapping
    public Double calc(@RequestParam String expression) throws ParsingException {

        Parser parser = new Parser();
        Expression exp = parser.parse(expression);
        double res = exp.evaluate();

        return res;
    }
}
