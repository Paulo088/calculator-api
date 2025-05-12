package com.paulo.calculator.services;

import com.paulo.calculator.dtos.CalculationRequest;
import com.paulo.calculator.dtos.CalculationResponse;
import com.paulo.calculator.entities.Calculation;
import com.paulo.calculator.enums.OperationType;
import com.paulo.calculator.repositories.CalculationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CalculatorServiceTest {

    @Mock
    private CalculationRepository repository;

    @Mock
    private Logger logger;

    @InjectMocks
    private CalculatorService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddition() {
        CalculationRequest request = new CalculationRequest();
        request.setOperand1(5);
        request.setOperand2(3);
        request.setOperation(OperationType.ADD);
        CalculationResponse response = service.calculate(request);

        assertEquals(8, response.getResult());
        verify(repository, times(1)).save(any(Calculation.class));
    }

    @Test
    void testDivisionByZero() {
        CalculationRequest request = new CalculationRequest();
        request.setOperand1(5);
        request.setOperand2(0);
        request.setOperation(OperationType.DIVIDE);

        assertThrows(IllegalArgumentException.class, () -> service.calculate(request));
    }

    @Test
    void testFindAll() {
        Calculation c1 = new Calculation();
        c1.setId(1L);
        c1.setOperand1(1);
        c1.setOperand2(2);
        c1.setOperation(OperationType.ADD);
        c1.setResult(3);

        Calculation c2 = new Calculation();
        c2.setId(2L);
        c2.setOperand1(4);
        c2.setOperand2(2);
        c2.setOperation(OperationType.DIVIDE);
        c2.setResult(2);

        when(repository.findAll()).thenReturn(Arrays.asList(c1, c2));

        List<Calculation> result = service.findAll();
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    void testFindById_Success() {
        Calculation calc = new Calculation();
        calc.setId(1L);
        calc.setOperand1(2);
        calc.setOperand2(2);
        calc.setOperation(OperationType.MULTIPLY);
        calc.setResult(4);

        when(repository.findById(1L)).thenReturn(Optional.of(calc));

        Calculation found = service.findById(1L);
        assertEquals(4, found.getResult());
    }

    @Test
    void testFindById_NotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> service.findById(99L));
    }

    @Test
    void testListByOperation() {
        Calculation calc = new Calculation();
        calc.setId(3L);
        calc.setOperand1(6);
        calc.setOperand2(2);
        calc.setOperation(OperationType.DIVIDE);
        calc.setResult(3);

        when(repository.findByOperation(OperationType.DIVIDE)).thenReturn(List.of(calc));

        List<Calculation> result = service.listByOperation(OperationType.DIVIDE);
        assertEquals(1, result.size());
        assertEquals(OperationType.DIVIDE, result.get(0).getOperation());
    }
}
