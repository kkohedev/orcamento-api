package com.github.kkohedev.orcamento_api.repository;

import com.github.kkohedev.orcamento_api.domain.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
}