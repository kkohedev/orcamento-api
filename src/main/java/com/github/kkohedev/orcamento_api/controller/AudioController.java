package com.github.kkohedev.orcamento_api.controller;

import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/audio")
public class AudioController {

    private final OpenAiAudioTranscriptionModel transcriptionModel;
    private final ChatClient chatClient;

    public AudioController(OpenAiAudioTranscriptionModel transcriptionModel, ChatClient chatClient) {
        this.transcriptionModel = transcriptionModel;
        this.chatClient = chatClient;
    }

    @PostMapping("/comando")
    public ResponseEntity<String> processarAudio(@RequestParam("file") MultipartFile file) throws IOException {

        Resource audioResource = new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        };

        OpenAiAudioTranscriptionOptions options = OpenAiAudioTranscriptionOptions.builder()
                .model("whisper-1")
                .build();

        AudioTranscriptionPrompt prompt = new AudioTranscriptionPrompt(audioResource, options);
        AudioTranscriptionResponse transcricao = transcriptionModel.call(prompt);
        String textoTranscrito = transcricao.getResult().getOutput();

        String respostaIA = chatClient.prompt(textoTranscrito).call().content();

        return ResponseEntity.ok("Você disse: \"" + textoTranscrito + "\"\n\nResposta: " + respostaIA);
    }
}