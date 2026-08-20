package com.github.kkohedev.orcamento_api.service;

import com.github.kkohedev.orcamento_api.domain.Transacao;
import com.github.kkohedev.orcamento_api.repository.TransacaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;

    public TransacaoService(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    public Transacao registrar(Transacao transacao) {
        return transacaoRepository.save(transacao);
    }

    public List<Transacao> listarTodas() {
        return transacaoRepository.findAll();
    }
}