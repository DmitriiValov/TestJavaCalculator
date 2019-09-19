package ru.dvalov.calc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;
import ru.dvalov.calc.calculator.exceptions.ConstantOverflowException;
import ru.dvalov.calc.calculator.exceptions.DivideByZeroException;
import ru.dvalov.calc.calculator.exceptions.InvalidTokenException;
import ru.dvalov.calc.calculator.exceptions.ParsingException;
import ru.dvalov.calc.calculator.operators.Expression;
import ru.dvalov.calc.calculator.parser.Parser;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CalcApplicationTests {

	@Test
	public void testParserPositive1() throws ParsingException {
		String expression = "(-7*8+9-(9/4.5))^2";
		Double expectedResult = 2401.0;
		Parser parser = new Parser();
		Expression exp = parser.parse(expression);
		Double actualResult = exp.evaluate();

		assertThat(
				String.format("Expected result: %s, actual result: %s", expectedResult, actualResult),
				actualResult.compareTo(expectedResult),
				equalTo(0)
		);
	}

	@Test
	public void testParserPositive2() throws ParsingException {
		String expression = "9*1+4.5";
		Double expectedResult = 13.5;
		Parser parser = new Parser();
		Expression exp = parser.parse(expression);
		Double actualResult = exp.evaluate();

		assertThat(
				String.format("Expected result: %s, actual result: %s", expectedResult, actualResult),
				actualResult.compareTo(expectedResult),
				equalTo(0)
		);
	}

	@Test(expected = ParsingException.class)
	public void testParserNegative1() throws ParsingException  {
		String expression = "9*1**4.5";

		Parser parser = new Parser();
		parser.parse(expression);
	}

	@Test(expected = InvalidTokenException.class)
	public void testParserNegative2() throws ParsingException  {
		String expression = "9#67*6";

		Parser parser = new Parser();
		parser.parse(expression);
	}

	@Test(expected = ParsingException.class)
	public void testParserNegative3() throws ParsingException  {
		String expression = "1+2+3*";

		Parser parser = new Parser();
		parser.parse(expression);
	}
}
