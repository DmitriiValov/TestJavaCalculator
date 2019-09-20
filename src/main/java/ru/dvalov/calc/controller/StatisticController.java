package ru.dvalov.calc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.dvalov.calc.database.CalculationRepository;
import ru.dvalov.calc.database.ConstantRepository;
import ru.dvalov.calc.utils.Utils;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("v1/stats")
public class StatisticController {

    @Autowired
    private CalculationRepository repCalc;
    @Autowired
    private ConstantRepository repConst;

    @GetMapping("/expressions/count/date/{date}")
    public @ResponseBody int getExpressionsCountForDate(@PathVariable String date) {
        Date dateBegin = null;
        Date dateEnd = null;
        try {
            dateBegin = Utils.parseDate(date + " 00:00");
            dateEnd = Utils.parseDate(date + " 23:59");
        }
        catch (ParseException exc) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Parsing error in date", exc);
        }
        return repCalc.getExpressionsCountWithDate(dateBegin, dateEnd);
    }

    @GetMapping("/expressions/date/{date}")
    public @ResponseBody List<String> getExpressionsForDate(@PathVariable String date) {
        Date dateBegin = null;
        Date dateEnd = null;
        try {
            dateBegin = Utils.parseDate(date + " 00:00");
            dateEnd = Utils.parseDate(date + " 23:59");
        }
        catch (ParseException exc) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Parsing error in date", exc);
        }
        return repCalc.findExpressionsWithDate(dateBegin, dateEnd);
    }

    @GetMapping("/expressions/count/operation/{operationId}")
    public @ResponseBody int getExpressionsCountForOperation(@PathVariable int operationId) {
        return repCalc.getExpressionsCountWithOperation(operationId);
    }

    @GetMapping("/expressions/operation/{operationId}")
    public @ResponseBody List<String> getExpressionsForOperation(@PathVariable int operationId) {
        return repCalc.findExpressionsWithOperation(operationId);
    }

    @GetMapping("/constants/popular")
    public @ResponseBody List<String> getPopular() {
        return repConst.findPopular();
    }
}