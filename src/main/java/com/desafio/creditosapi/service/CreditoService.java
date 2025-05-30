package com.desafio.creditosapi.service;

import com.desafio.creditosapi.model.Credito;
import com.desafio.creditosapi.repository.CreditoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import java.util.List;

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
        List<Credito> creditos = creditoRepository.findByNumeroNfse(numeroNfse);
        notificacaoService.notificarConsulta("Consulta realizada por NFS-e: " + numeroNfse);
        return creditos;
    }

    @Override
    @Transactional(readOnly = true)
    public Credito buscarPorNumeroCredito(String numeroCredito) {
        Credito credito = creditoRepository.findByNumeroCredito(numeroCredito)
                .orElseThrow(() -> new EntityNotFoundException("Crédito não encontrado: " + numeroCredito));
        notificacaoService.notificarConsulta("Consulta realizada por número de crédito: " + numeroCredito);
        return credito;
    }
} 