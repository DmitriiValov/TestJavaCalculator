package ru.dvalov.calc.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConstantRepository extends JpaRepository<Constant, Integer> {

    @Query(value = "select c.value from Constant c group by c.value " +
            "having count(c.value) = (select count(d.value) from Constant d group by d.value order by count(d.value) desc limit 1)", nativeQuery = true)
    List<String> findPopular();
}



