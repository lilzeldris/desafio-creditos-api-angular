package com.desafio.creditosapi.integration;

import com.desafio.creditosapi.model.Credito;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class CreditoIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void buscarPorNumeroNfse_DeveRetornarCreditos() {
        ResponseEntity<List<Credito>> response = restTemplate.exchange(
            "http://localhost:" + port + "/api/creditos/7891011",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<Credito>>() {}
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    public void buscarPorNumeroCredito_DeveRetornarCredito() {
        ResponseEntity<Credito> response = restTemplate.getForEntity(
            "http://localhost:" + port + "/api/creditos/credito/123456",
            Credito.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("123456", response.getBody().getNumeroCredito());
    }

    @Test
    public void buscarPorNumeroCredito_DeveRetornarNotFound() {
        ResponseEntity<Credito> response = restTemplate.getForEntity(
            "http://localhost:" + port + "/api/creditos/credito/999999",
            Credito.class
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
} 