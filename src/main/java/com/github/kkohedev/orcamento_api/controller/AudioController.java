package com.github.kkohedev.orcamento_api.controller;

import org.springframework.ai.audio.tts.TextToSpeechPrompt;
import org.springframework.ai.audio.tts.TextToSpeechResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiAudioSpeechModel;
import org.springframework.ai.openai.OpenAiAudioSpeechOptions;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/audio")
public class AudioController {

    private final OpenAiAudioTranscriptionModel transcriptionModel;
    private final OpenAiAudioSpeechModel speechModel;
    private final ChatClient chatClient;

    public AudioController(OpenAiAudioTranscriptionModel transcriptionModel,
                           OpenAiAudioSpeechModel speechModel,
                           ChatClient chatClient) {
        this.transcriptionModel = transcriptionModel;
        this.speechModel = speechModel;
        this.chatClient = chatClient;
    }

    @PostMapping("/comando")
    public ResponseEntity<byte[]> processarAudio(@RequestParam("file") MultipartFile file) throws IOException {

        Resource audioResource = new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        };

        OpenAiAudioTranscriptionOptions transcriptionOptions = OpenAiAudioTranscriptionOptions.builder()
                .model("whisper-1")
                .build();

        AudioTranscriptionPrompt transcriptionPrompt = new AudioTranscriptionPrompt(audioResource, transcriptionOptions);
        AudioTranscriptionResponse transcricao = transcriptionModel.call(transcriptionPrompt);
        String textoTranscrito = transcricao.getResult().getOutput();

        String respostaTexto = chatClient.prompt(textoTranscrito).call().content();

        OpenAiAudioSpeechOptions speechOptions = OpenAiAudioSpeechOptions.builder()
                .model("tts-1")
                .voice(OpenAiAudioSpeechOptions.Voice.ALLOY)
                .responseFormat(OpenAiAudioSpeechOptions.AudioResponseFormat.MP3)
                .build();

        TextToSpeechPrompt speechPrompt = new TextToSpeechPrompt(respostaTexto, speechOptions);
        TextToSpeechResponse speechResponse = speechModel.call(speechPrompt);
        byte[] audioResposta = speechResponse.getResult().getOutput();

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("audio/mpeg"))
                .header("X-Texto-Transcrito", textoTranscrito)
                .header("X-Resposta-Texto", respostaTexto)
                .body(audioResposta);
    }
}