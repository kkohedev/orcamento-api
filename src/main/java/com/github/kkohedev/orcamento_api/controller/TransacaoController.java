package com.github.kkohedev.orcamento_api.controller;

import com.github.kkohedev.orcamento_api.domain.Transacao;
import com.github.kkohedev.orcamento_api.service.TransacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @PostMapping
    public ResponseEntity<Transacao> registrar(@RequestBody Transacao transacao) {
        Transacao salva = transacaoService.registrar(transacao);
        return ResponseEntity.ok(salva);
    }

    @GetMapping
    public ResponseEntity<List<Transacao>> listar() {
        return ResponseEntity.ok(transacaoService.listarTodas());
    }
}