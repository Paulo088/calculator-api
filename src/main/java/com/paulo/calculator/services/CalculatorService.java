package com.paulo.calculator.services;

import com.paulo.calculator.dtos.CalculationRequest;
import com.paulo.calculator.dtos.CalculationResponse;
import com.paulo.calculator.entities.Calculation;
import com.paulo.calculator.enums.OperationType;
import com.paulo.calculator.repositories.CalculationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class CalculatorService {

    private static final Logger logger = LoggerFactory.getLogger(CalculatorService.class);
    private final CalculationRepository repository;

    public CalculatorService(CalculationRepository repository) {
        this.repository = repository;
    }

    public CalculationResponse calculate(CalculationRequest request) {
        logger.info("Received calculation request: {} {} {}", request.getOperand1(), request.getOperation(), request.getOperand2());
        double result;

        try {
            switch (request.getOperation()) {
                case ADD -> result = request.getOperand1() + request.getOperand2();
                case SUBTRACT -> result = request.getOperand1() - request.getOperand2();
                case MULTIPLY -> result = request.getOperand1() * request.getOperand2();
                case DIVIDE -> {
                    if (request.getOperand2() == 0) {
                        logger.error("Division by zero attempted");
                        throw new IllegalArgumentException("Cannot divide by zero");
                    }
                    result = request.getOperand1() / request.getOperand2();
                }
                default -> {
                    logger.error("Unsupported operation: {}", request.getOperation());
                    throw new UnsupportedOperationException("Invalid operation");
                }
            }
        } catch (Exception e) {
            logger.error("Error processing calculation: {}", e.getMessage());
            throw e;
        }

        Calculation calc = new Calculation();
        calc.setOperand1(request.getOperand1());
        calc.setOperand2(request.getOperand2());
        calc.setOperation(request.getOperation());
        calc.setResult(result);

        repository.save(calc);

        logger.info("Calculation result saved: {}", result);
        return new CalculationResponse(result);
    }

    public List<Calculation> findAll() {
        logger.debug("Fetching all calculations");
        List<Calculation> list = new ArrayList<>();
        repository.findAll().forEach(list::add);
        return list;
    }

    public Calculation findById(long id) {
        logger.debug("Fetching calculation by ID: {}", id);
        return repository.findById(id).orElseThrow(() -> {
            logger.warn("Calculation not found for ID: {}", id);
            return new ResponseStatusException(HttpStatus.NOT_FOUND);
        });
    }

    public List<Calculation> listByOperation(OperationType operation) {
        logger.debug("Fetching calculations by operation: {}", operation);
        return this.repository.findByOperation(operation);
    }
}
