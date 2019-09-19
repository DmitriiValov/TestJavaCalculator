package ru.dvalov.calc.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.dvalov.calc.calculator.token.TokenType;

import java.util.List;

@Repository
public interface CalculationRepository extends JpaRepository<Calculation, Integer> {

    @Query("select c.expression from Calculation c inner join c.operations as op where op.type = ?1")
    List<String> findExpressionsWithOperation(@Param("operationId") int operation);
}
