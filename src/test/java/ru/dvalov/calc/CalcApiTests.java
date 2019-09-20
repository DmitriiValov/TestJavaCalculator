package ru.dvalov.calc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.dvalov.calc.calculator.token.TokenType;
import ru.dvalov.calc.database.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest
public class CalcApiTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CalculationRepository calculationRepository;
    @MockBean
    private OperationRepository operationRepository;
    @MockBean
    private ConstantRepository constantRepository;

    @Test
    public void apiTest1() throws Exception {

        when(calculationRepository.findExpressionsWithOperation(1)).thenReturn(Collections.emptyList());
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/v1/stats/expressions/operation/1")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn();

        System.out.println(mvcResult.getResponse());
        verify(calculationRepository).findExpressionsWithOperation(1);
    }

    @Test
    public void apiTest2() throws Exception {

        List<Operation> os = new ArrayList();
        os.add(new Operation(TokenType.MULT.ordinal()));
        os.add(new Operation(TokenType.PLUS.ordinal()));
        os.add(new Operation(TokenType.MINUS.ordinal()));

        List<Constant> cs = new ArrayList();
        cs.add(new Constant(5.0));
        cs.add(new Constant(5.0));
        cs.add(new Constant(6.0));
        cs.add(new Constant(6.0));

        Calculation c = Calculation.createCalculation(
                "5*5+6-6",
                25.0,
                new Date(),
                os,
                cs);
        calculationRepository.save(c);

        List<String> resList = new ArrayList();
        resList.add("5.0");
        resList.add("6.0");
        when(constantRepository.findPopular()).thenReturn(resList);
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/v1/stats/constants/popular")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        verify(constantRepository).findPopular();
    }
}