package com.goncalvesmail.rinhabackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.goncalvesmail.rinhabackend.exception.NegocioException;
import com.goncalvesmail.rinhabackend.model.Transacao;
import java.time.LocalDateTime;

public record TransacaoDTO(
        String valor,
        String tipo,
        String descricao,
        @JsonProperty("realizada_em") LocalDateTime realizada) {
    public static TransacaoDTO novaTransacaoDTO(String valor, String tipo, String descricao) {
        return new TransacaoDTO(valor, tipo, descricao, LocalDateTime.now());
    }
    public Transacao toTransacao() throws NegocioException {
        return Transacao.novaTransacao(valor, tipo, descricao);
    }
    public static TransacaoDTO fromTransacao(Transacao transacao) {
        return new TransacaoDTO(
                transacao.getValor().toString(),
                transacao.getTipo(),
                transacao.getDescricao(),
                transacao.getRealizadaEm());
    }
}
