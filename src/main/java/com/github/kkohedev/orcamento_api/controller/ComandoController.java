package com.github.kkohedev.orcamento_api.controller;

import com.github.kkohedev.orcamento_api.domain.LogInteracao;
import com.github.kkohedev.orcamento_api.repository.LogInteracaoRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comando")
public class ComandoController {

    private final ChatClient chatClient;
    private final LogInteracaoRepository logInteracaoRepository;

    public ComandoController(ChatClient chatClient, LogInteracaoRepository logInteracaoRepository) {
        this.chatClient = chatClient;
        this.logInteracaoRepository = logInteracaoRepository;
    }

    @PostMapping
    public ResponseEntity<String> processar(@RequestBody String comando) {
        String resposta = chatClient.prompt(comando).call().content();

        LogInteracao log = new LogInteracao(comando, resposta, "TEXTO");
        logInteracaoRepository.save(log);

        return ResponseEntity.ok(resposta);
    }
}