package com.paulo.calculator.controllers;

import com.paulo.calculator.dtos.CalculationRequest;
import com.paulo.calculator.dtos.CalculationResponse;
import com.paulo.calculator.entities.Calculation;
import com.paulo.calculator.enums.OperationType;
import com.paulo.calculator.services.CalculatorService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/calculator")
@Tag(name = "Calculator", description = "API for mathematical operations")
public class CalculatorController {

    private final CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @Operation(summary = "Performs a calculation", description = "Performs the specified operation (addition, subtraction, multiplication, or division) between the given numbers.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CalculationResponse> calculate(
            @Valid @RequestBody
            @Parameter(description = "Request containing the numbers and the operation", required = true, schema = @Schema(implementation = CalculationRequest.class))
            CalculationRequest request) {
        return ResponseEntity.ok(calculatorService.calculate(request));
    }

    @Operation(summary = "List all calculations", description = "Returns a list with all calculations performed.")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Calculation> findAll() {
        return this.calculatorService.findAll();
    }

    @Operation(summary = "Search calculation by ID", description = "Returns a specific calculation based on the given ID.")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Calculation findById(
            @Parameter(description = "Calculation ID to fetch", required = true)
            @PathVariable long id) {
        return this.calculatorService.findById(id);
    }

    @Operation(summary = "List calculations by operation", description = "Returns a list of calculations performed based on the operation type.")
    @GetMapping("/operation/{operation}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Calculation> findByName(
            @Parameter(description = "Operation type (ADD, SUBTRACT, MULTIPLY, DIVIDE)", required = true)
            @PathVariable OperationType operation) {
        return this.calculatorService.listByOperation(operation);
    }
}
