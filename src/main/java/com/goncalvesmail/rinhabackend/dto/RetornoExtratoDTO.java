package com.goncalvesmail.rinhabackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.goncalvesmail.rinhabackend.model.Cliente;
import com.goncalvesmail.rinhabackend.model.Transacao;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public record RetornoExtratoDTO(SaldoDTO saldo,
        @JsonProperty("ultimas_transacoes") List<TransacaoOutputDTO> ultimasTransacoes) {

    public static RetornoExtratoDTO fromCliente(Cliente cliente) {
        SaldoDTO saldo = new SaldoDTO(cliente.getSaldo(), LocalDateTime.now(), cliente.getLimite());
        return new RetornoExtratoDTO(
                saldo,
                cliente.getTransacoes().stream()
                        .sorted(Comparator.comparing(Transacao::getRealizadaEm, Comparator.reverseOrder()))
                        //.limit(10)
                        .map(TransacaoOutputDTO::fromTransacao).toList());
    }
}