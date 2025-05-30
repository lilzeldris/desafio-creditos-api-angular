package com.desafio.creditosapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.SettableListenableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificacaoServiceTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    private NotificacaoService notificacaoService;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        notificacaoService = new NotificacaoService(kafkaTemplate, objectMapper);
    }

    @Test
    void deveEnviarNotificacaoComSucesso() {
        String numeroNfse = "7891011";
        String tipoConsulta = "CONSULTA_NFSE";
        SettableListenableFuture<SendResult<String, String>> future = new SettableListenableFuture<>();
        future.set(new SendResult<>(null, null));
        when(kafkaTemplate.send(eq("consultas-creditos"), any())).thenReturn(future);

        notificacaoService.notificarConsulta(numeroNfse, tipoConsulta);

        verify(kafkaTemplate).send(eq("consultas-creditos"), any());
    }

    @Test
    void deveTratarErroAoEnviarNotificacao() {
        String numeroNfse = "7891011";
        String tipoConsulta = "CONSULTA_NFSE";
        when(kafkaTemplate.send(eq("consultas-creditos"), any()))
            .thenThrow(new RuntimeException("Erro ao enviar mensagem"));

        notificacaoService.notificarConsulta(numeroNfse, tipoConsulta);
    }
} 