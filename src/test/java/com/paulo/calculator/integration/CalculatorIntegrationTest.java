package com.paulo.calculator.integration;

import com.paulo.calculator.dtos.CalculationRequest;
import com.paulo.calculator.enums.OperationType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CalculatorIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getUrl(String endpoint) {
        return "http://localhost:" + port + "/api/calculator" + endpoint;
    }

    @Test
    void shouldAddNumbersSuccessfully() {
        CalculationRequest request = new CalculationRequest();
        request.setOperand1(10);
        request.setOperand2(5);
        request.setOperation(OperationType.ADD);

        ResponseEntity<String> response = restTemplate.postForEntity(getUrl(""), request, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("\"result\":15"));
    }

    @Test
    void shouldReturnBadRequestForDivisionByZero() {
        CalculationRequest request = new CalculationRequest();
        request.setOperand1(10);
        request.setOperand2(0);
        request.setOperation(OperationType.DIVIDE);

        ResponseEntity<Map> response = restTemplate.postForEntity(getUrl(""), request, Map.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Cannot divide by zero", response.getBody().get("error"));
    }

    @Test
    void shouldReturnNotFoundForInvalidId() {
        ResponseEntity<Map> response = restTemplate.getForEntity(getUrl("/999"), Map.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("404 NOT_FOUND", response.getBody().get("error"));
    }

    @Test
    void shouldReturnBadRequestForInvalidOperationType() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String invalidRequest = "{\"operand1\":5,\"operand2\":3,\"operation\":\"INVALID\"}";
        HttpEntity<String> entity = new HttpEntity<>(invalidRequest, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(getUrl(""), entity, Map.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid operation. Allowed values are: ADD, SUBTRACT, MULTIPLY, DIVIDE", response.getBody().get("error"));
    }

    @Test
    void shouldReturnAllCalculations() {
        ResponseEntity<String> response = restTemplate.getForEntity(getUrl(""), String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("result"));
    }

    @Test
    void shouldReturnCalculationsByOperation() {
        CalculationRequest request = new CalculationRequest();
        request.setOperand1(10);
        request.setOperand2(5);
        request.setOperation(OperationType.ADD);

        restTemplate.postForEntity(getUrl(""), request, String.class);
        ResponseEntity<String> response = restTemplate.getForEntity(getUrl("/operation/ADD"), String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("result"));
    }
}
