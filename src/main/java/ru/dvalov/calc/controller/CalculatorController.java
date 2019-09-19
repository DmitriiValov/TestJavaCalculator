package ru.dvalov.calc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.dvalov.calc.calculator.exceptions.ConstantOverflowException;
import ru.dvalov.calc.calculator.exceptions.DivideByZeroException;
import ru.dvalov.calc.calculator.exceptions.InvalidTokenException;
import ru.dvalov.calc.calculator.exceptions.ParsingException;
import ru.dvalov.calc.calculator.operators.Expression;
import ru.dvalov.calc.calculator.parser.Parser;
import ru.dvalov.calc.calculator.token.TokenType;
import ru.dvalov.calc.database.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("v1/calc")
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
        double result = 0.0;

        try {
            parser = new Parser();
            Expression exp = parser.parse(expression);
            result = exp.evaluate();
        }
        catch (DivideByZeroException exc) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Divizion by zero", exc);
        }
        catch (ConstantOverflowException exc) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Constant overflow", exc);
        }
        catch (InvalidTokenException exc) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid token", exc);
        }
        catch (ParsingException exc) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Parsing error", exc);
        }

        if (parser != null) {
            List<Double> constants = parser.getListOfConsts();
            List<TokenType> operations = parser.getListOfOperations();

            List<Operation> os = new ArrayList();
            for (TokenType operation: operations) {
                Operation o = new Operation(operation.ordinal());
                os.add(o);
            }

            List<Constant> cs = new ArrayList();
            for (Double value: constants) {
                Constant cnt = new Constant(value);
                cs.add(cnt);
            }

            Calculation c = Calculation.createCalculation(
                    expression,
                    result,
                    new Date(),
                    os,
                    cs);
            repCalc.save(c);
        }
        return result;
    }
}
