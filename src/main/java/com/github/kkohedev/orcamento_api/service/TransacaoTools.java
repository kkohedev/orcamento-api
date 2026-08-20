package com.github.kkohedev.orcamento_api.service;

import com.github.kkohedev.orcamento_api.domain.Transacao;
import com.github.kkohedev.orcamento_api.domain.TipoTransacao;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.math.BigDecimal;

@Component
public class TransacaoTools {

    private final TransacaoService transacaoService;

    public TransacaoTools(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @Tool(description = "Registra uma nova transação financeira (entrada ou saída) no orçamento do usuário")
    public String criarTransacao(
            @ToolParam(description = "Descrição da transação, ex: Salário, Mercado, Uber") String descricao,
            @ToolParam(description = "Valor da transação em reais") Double valor,
            @ToolParam(description = "Tipo: ENTRADA ou SAIDA") String tipo,
            @ToolParam(description = "Categoria, ex: Trabalho, Alimentação, Transporte") String categoria
    ) {
        Transacao transacao = new Transacao();
        transacao.setDescricao(descricao);
        transacao.setValor(BigDecimal.valueOf(valor));
        transacao.setTipo(TipoTransacao.valueOf(tipo.toUpperCase()));
        transacao.setCategoria(categoria);
        transacao.setData(LocalDate.now());

        Transacao salva = transacaoService.registrar(transacao);
        return "Transação registrada com sucesso: " + salva.getDescricao() + " no valor de R$ " + salva.getValor();
    }

    @Tool(description = "Lista todas as transações financeiras já registradas pelo usuário")
    public String listarTransacoes() {
        List<Transacao> transacoes = transacaoService.listarTodas();

        if (transacoes.isEmpty()) {
            return "Nenhuma transação registrada ainda.";
        }

        StringBuilder resposta = new StringBuilder("Transações registradas:\n");
        for (Transacao t : transacoes) {
            resposta.append("- ").append(t.getDescricao())
                    .append(": R$ ").append(t.getValor())
                    .append(" (").append(t.getTipo()).append(", ")
                    .append(t.getCategoria()).append(")\n");
        }
        return resposta.toString();
    }
}