package com.goncalvesmail.rinhabackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.goncalvesmail.rinhabackend.exception.NegocioException;
import com.goncalvesmail.rinhabackend.model.Transacao;

import java.time.LocalDateTime;

public record TransacaoOutputDTO(
        Long valor,
        String tipo,
        String descricao,
        @JsonProperty("realizada_em") LocalDateTime realizada) {

    public static TransacaoOutputDTO fromTransacao(Transacao transacao) {
        return new TransacaoOutputDTO(
                transacao.getValor(),
                transacao.getTipo(),
                transacao.getDescricao(),
                transacao.getRealizadaEm());
    }
}
