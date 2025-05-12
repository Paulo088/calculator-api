package com.paulo.calculator.repositories;

import com.paulo.calculator.entities.Calculation;
import com.paulo.calculator.enums.OperationType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalculationRepository extends CrudRepository<Calculation, Long> {

    List<Calculation> findByOperation(OperationType operation);
}
