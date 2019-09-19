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
import ru.dvalov.calc.database.CalculationRepository;
import ru.dvalov.calc.database.ConstantRepository;
import ru.dvalov.calc.database.OperationRepository;

import java.util.Collections;

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
    public void apiTest() throws Exception {

        when(calculationRepository.findExpressionsWithOperation(1)).thenReturn(Collections.emptyList());
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/v1/stats/expressions/operation/1")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn();

        System.out.println(mvcResult.getResponse());
        verify(calculationRepository).findExpressionsWithOperation(1);
    }
}