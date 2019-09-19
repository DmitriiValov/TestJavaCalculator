package ru.dvalov.calc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.dvalov.calc.calculator.token.TokenType;
import ru.dvalov.calc.database.CalculationRepository;
import ru.dvalov.calc.database.ConstantRepository;

import java.util.List;

@RestController
@RequestMapping("v1/stats")
public class StatisticController {

    @Autowired
    private CalculationRepository repCalc;
    @Autowired
    private ConstantRepository repConst;

    @GetMapping("/expressions/count/{data}")
    public int getExpressionsCountForData(@PathVariable String data) {
        return 3;
    }

    @GetMapping("/expressions/operation/{operationId}")
    public @ResponseBody List<String> getExpressionForOperation(@PathVariable int operationId) {
        return repCalc.findExpressionsWithOperation(operationId);
    }

    @GetMapping("/constants/popular")
    public @ResponseBody List<String> getPopular() {
        return repConst.findPopular();
    }
}