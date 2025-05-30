package com.desafio.creditosapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NotificacaoService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private static final String TOPIC_CONSULTAS = "consultas-creditos";

    public NotificacaoService(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void notificarConsulta(String numeroNfse, String tipoConsulta) {
        try {
            NotificacaoConsulta notificacao = new NotificacaoConsulta(
                numeroNfse,
                tipoConsulta,
                LocalDateTime.now(),
                "CONSULTA_REALIZADA"
            );
            
            String mensagem = objectMapper.writeValueAsString(notificacao);
            kafkaTemplate.send(TOPIC_CONSULTAS, mensagem);
            log.info("Notificação enviada com sucesso: {}", mensagem);
        } catch (Exception e) {
            log.error("Erro ao enviar notificação: {}", e.getMessage(), e);
        }
    }
}

class NotificacaoConsulta {
    private String numeroNfse;
    private String tipoConsulta;
    private LocalDateTime timestamp;
    private String status;

    public NotificacaoConsulta(String numeroNfse, String tipoConsulta, LocalDateTime timestamp, String status) {
        this.numeroNfse = numeroNfse;
        this.tipoConsulta = tipoConsulta;
        this.timestamp = timestamp;
        this.status = status;
    }

    public String getNumeroNfse() { return numeroNfse; }
    public void setNumeroNfse(String numeroNfse) { this.numeroNfse = numeroNfse; }
    public String getTipoConsulta() { return tipoConsulta; }
    public void setTipoConsulta(String tipoConsulta) { this.tipoConsulta = tipoConsulta; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
} 