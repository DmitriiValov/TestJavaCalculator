package ru.dvalov.calc.controller;

import com.sun.codemodel.internal.JTryBlock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.dvalov.calc.calculator.exceptions.DivideByZeroException;
import ru.dvalov.calc.calculator.exceptions.ParsingException;
import ru.dvalov.calc.calculator.operators.Expression;
import ru.dvalov.calc.calculator.parser.Parser;
import ru.dvalov.calc.calculator.token.TokenType;
import ru.dvalov.calc.database.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("calc")
public class CalculatorController {

    @Autowired
    private CalculationRepository repCalc;
    @Autowired
    private OperationRepository repOper;
    @Autowired
    private ConstantRepository repConst;

    @GetMapping
    public Double calc(@RequestParam String expression) throws ParsingException {
        Parser parser = null;
        double res = 0.0;

        try {
            parser = new Parser();
            Expression exp = parser.parse(expression);
            res = exp.evaluate();
        }
        catch (ParsingException exc) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Parsing error", exc);
        }
        catch (DivideByZeroException exc) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Divizion by zero", exc);
        }

        List<Double> constants = parser.getListOfConsts();
        List<TokenType> operations = parser.getListOfOperations();

        Calculation c = new Calculation();
        c.setDate(new Date());
        c.setExpression(expression);
        c.setResult(res);
        repCalc.save(c);

        for (TokenType operation: operations) {
            Operation o = new Operation();
            o.setType(operation);
            o.setCalculation(c);
            repOper.save(o);
        }

        for (Double value: constants) {
            Constant cnt = new Constant();
            cnt.setValue(value);
            cnt.setCalculation(c);
            repConst.save(cnt);
        }

        return res;
    }
}
