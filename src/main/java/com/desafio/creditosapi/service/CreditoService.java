package com.desafio.creditosapi.service;

import com.desafio.creditosapi.model.Credito;
import com.desafio.creditosapi.repository.CreditoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CreditoService implements ICreditoService {

    private final CreditoRepository creditoRepository;
    private final NotificacaoService notificacaoService;

    public CreditoService(CreditoRepository creditoRepository, NotificacaoService notificacaoService) {
        this.creditoRepository = creditoRepository;
        this.notificacaoService = notificacaoService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Credito> buscarPorNumeroNfse(String numeroNfse) {
        log.info("Buscando créditos por NFS-e: {}", numeroNfse);
        List<Credito> creditos = creditoRepository.findByNumeroNfse(numeroNfse);
        notificacaoService.notificarConsulta(numeroNfse, "CONSULTA_NFSE");
        log.info("Encontrados {} créditos para NFS-e: {}", creditos.size(), numeroNfse);
        return creditos;
    }

    @Override
    @Transactional(readOnly = true)
    public Credito buscarPorNumeroCredito(String numeroCredito) {
        log.info("Buscando crédito por número: {}", numeroCredito);
        Credito credito = creditoRepository.findByNumeroCredito(numeroCredito)
                .orElseThrow(() -> {
                    log.error("Crédito não encontrado: {}", numeroCredito);
                    return new EntityNotFoundException("Crédito não encontrado: " + numeroCredito);
                });
        notificacaoService.notificarConsulta(numeroCredito, "CONSULTA_CREDITO");
        log.info("Crédito encontrado: {}", numeroCredito);
        return credito;
    }
} 