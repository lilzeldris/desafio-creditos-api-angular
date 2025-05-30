package com.desafio.creditosapi.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPICO = "consultas-credito";

    public NotificacaoService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void notificarConsulta(String mensagem) {
        kafkaTemplate.send(TOPICO, mensagem);
    }
} 