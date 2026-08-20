package com.github.kkohedev.orcamento_api.controller;

import org.springframework.ai.audio.tts.TextToSpeechPrompt;
import org.springframework.ai.audio.tts.TextToSpeechResponse;
import org.springframework.ai.openai.OpenAiAudioSpeechModel;
import org.springframework.ai.openai.OpenAiAudioSpeechOptions;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tts")
public class TtsController {

    private final OpenAiAudioSpeechModel speechModel;

    public TtsController(OpenAiAudioSpeechModel speechModel) {
        this.speechModel = speechModel;
    }

    @PostMapping
    public ResponseEntity<byte[]> gerarAudio(@RequestBody String texto) {

        OpenAiAudioSpeechOptions options = OpenAiAudioSpeechOptions.builder()
                .model("tts-1")
                .voice(OpenAiAudioSpeechOptions.Voice.ALLOY)
                .responseFormat(OpenAiAudioSpeechOptions.AudioResponseFormat.MP3)
                .build();

        TextToSpeechPrompt speechPrompt = new TextToSpeechPrompt(texto, options);
        TextToSpeechResponse response = speechModel.call(speechPrompt);
        byte[] audioBytes = response.getResult().getOutput();

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("audio/mpeg"))
                .body(audioBytes);
    }
}