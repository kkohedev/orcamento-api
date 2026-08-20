package com.github.kkohedev.orcamento_api.controller;

import com.github.kkohedev.orcamento_api.domain.LogInteracao;
import com.github.kkohedev.orcamento_api.repository.LogInteracaoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/logs")
public class LogInteracaoController {

    private final LogInteracaoRepository logInteracaoRepository;

    public LogInteracaoController(LogInteracaoRepository logInteracaoRepository) {
        this.logInteracaoRepository = logInteracaoRepository;
    }

    @GetMapping
    public ResponseEntity<List<LogInteracao>> listar() {
        return ResponseEntity.ok(logInteracaoRepository.findAll());
    }
}