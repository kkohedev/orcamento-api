package com.github.kkohedev.orcamento_api.repository;

import com.github.kkohedev.orcamento_api.domain.LogInteracao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogInteracaoRepository extends JpaRepository<LogInteracao, Long> {
}