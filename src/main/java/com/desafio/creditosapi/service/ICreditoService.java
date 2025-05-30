package com.desafio.creditosapi.service;

import com.desafio.creditosapi.model.Credito;
import java.util.List;

public interface ICreditoService {
    List<Credito> buscarPorNumeroNfse(String numeroNfse);
    Credito buscarPorNumeroCredito(String numeroCredito);
} 