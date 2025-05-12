package com.paulo.calculator.dtos;

public class CalculationResponse {

    public CalculationResponse(double result) {
        this.result = result;
    }

    private double result;

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }
}
