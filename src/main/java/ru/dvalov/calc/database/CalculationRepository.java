package ru.dvalov.calc.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CalculationRepository extends JpaRepository<Calculation, Integer> {

    @Query(value = "select c.expression from Calculation c inner join c.operations as op where op.type = ?1", nativeQuery = false)
    List<String> findExpressionsWithOperation(@Param("operationId") int operation);

    @Query(value = "select count(c.expression) from Calculation c inner join c.operations as op where op.type = ?1", nativeQuery = false)
    int getExpressionsCountWithOperation(@Param("operationId") int operation);

    @Query(value = "select c.expression from Calculation c where c.date between :dateBegin and :DateEnd", nativeQuery = false)
    List<String> findExpressionsWithDate(@Param("dateBegin") Date dateBegin,
                                         @Param("DateEnd") Date DateEnd);

    @Query(value = "select count(c.expression) from Calculation c where c.date between :dateBegin and :DateEnd", nativeQuery = false)
    int getExpressionsCountWithDate(@Param("dateBegin") Date dateBegin,
                                    @Param("DateEnd") Date DateEnd);
}


