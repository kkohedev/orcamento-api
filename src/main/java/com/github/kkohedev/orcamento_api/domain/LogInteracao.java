package com.github.kkohedev.orcamento_api.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "log_interacao")
public class LogInteracao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    private String entrada;

    @Column(length = 1000)
    private String resposta;

    private String origem;

    private LocalDateTime dataHora;

    public LogInteracao() {
    }

    public LogInteracao(String entrada, String resposta, String origem) {
        this.entrada = entrada;
        this.resposta = resposta;
        this.origem = origem;
        this.dataHora = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getEntrada() {
        return entrada;
    }

    public String getResposta() {
        return resposta;
    }

    public String getOrigem() {
        return origem;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }
}