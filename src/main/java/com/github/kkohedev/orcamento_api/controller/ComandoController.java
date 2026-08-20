package com.github.kkohedev.orcamento_api.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comando")
public class ComandoController {

    private final ChatClient chatClient;

    public ComandoController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @PostMapping
    public ResponseEntity<String> processar(@RequestBody String comando) {
        String resposta = chatClient.prompt(comando).call().content();
        return ResponseEntity.ok(resposta);
    }
}