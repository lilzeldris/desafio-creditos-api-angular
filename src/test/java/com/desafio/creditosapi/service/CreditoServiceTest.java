package com.desafio.creditosapi.service;

import com.desafio.creditosapi.model.Credito;
import com.desafio.creditosapi.repository.CreditoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreditoServiceTest {

    @Mock
    private CreditoRepository creditoRepository;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    private CreditoService creditoService;

    @BeforeEach
    void setUp() {
        creditoService = new CreditoService(creditoRepository, kafkaTemplate);
    }

    @Test
    void buscarPorNumeroNfse_DeveRetornarListaDeCreditos() {
        Credito credito = new Credito();
        credito.setNumeroNfse("7891011");
        when(creditoRepository.findByNumeroNfse("7891011")).thenReturn(Arrays.asList(credito));

        List<Credito> resultado = creditoService.buscarPorNumeroNfse("7891011");

        assertFalse(resultado.isEmpty());
        assertEquals("7891011", resultado.get(0).getNumeroNfse());
        verify(kafkaTemplate).send(eq("consultas-credito"), any());
    }

    @Test
    void buscarPorNumeroCredito_DeveRetornarCredito() {
        Credito credito = new Credito();
        credito.setNumeroCredito("123456");
        when(creditoRepository.findByNumeroCredito("123456")).thenReturn(Optional.of(credito));

        Credito resultado = creditoService.buscarPorNumeroCredito("123456");

        assertNotNull(resultado);
        assertEquals("123456", resultado.getNumeroCredito());
        verify(kafkaTemplate).send(eq("consultas-credito"), any());
    }

    @Test
    void buscarPorNumeroCredito_DeveRetornarEntityNotFoundException() {
        when(creditoRepository.findByNumeroCredito("999999")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            creditoService.buscarPorNumeroCredito("999999");
        });
    }
} 