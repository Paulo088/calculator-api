package com.paulo.calculator.dtos;

import com.paulo.calculator.enums.OperationType;
import jakarta.validation.constraints.NotNull;

public class CalculationRequest {

    @NotNull(message = "Operand 1 is required")
    private Double operand1;

    @NotNull(message = "Operand 2 is required")
    private Double operand2;

    @NotNull(message = "Operation is required")
    private OperationType operation;

    public double getOperand1() {
        return operand1;
    }

    public void setOperand1(double operand1) {
        this.operand1 = operand1;
    }

    public double getOperand2() {
        return operand2;
    }

    public void setOperand2(double operand2) {
        this.operand2 = operand2;
    }

    public OperationType getOperation() {
        return operation;
    }

    public void setOperation(OperationType operation) {
        this.operation = operation;
    }
}
