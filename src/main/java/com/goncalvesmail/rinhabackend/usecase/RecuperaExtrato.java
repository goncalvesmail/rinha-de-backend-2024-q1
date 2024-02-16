package com.goncalvesmail.rinhabackend.usecase;

import com.goncalvesmail.rinhabackend.dto.RetornoExtratoDTO;
import com.goncalvesmail.rinhabackend.dto.SaldoDTO;
import com.goncalvesmail.rinhabackend.dto.TransacaoDTO;
import com.goncalvesmail.rinhabackend.dto.TransacaoOutputDTO;
import com.goncalvesmail.rinhabackend.exception.ClienteNaoEncontradoException;
import com.goncalvesmail.rinhabackend.model.Cliente;
import com.goncalvesmail.rinhabackend.model.Transacao;
import com.goncalvesmail.rinhabackend.repository.ClienteRepository;
import com.goncalvesmail.rinhabackend.repository.TransacaoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecuperaExtrato {

    private final ClienteRepository clienteRepository;
    private final TransacaoRepository transacaoRepository;

    public RecuperaExtrato(ClienteRepository clienteRepository, TransacaoRepository transacaoRepository) {
        this.clienteRepository = clienteRepository;
        this.transacaoRepository = transacaoRepository;
    }

    public RetornoExtratoDTO execute(Long idCliente) throws ClienteNaoEncontradoException {
        Optional<Cliente> clienteOptional = clienteRepository.findById(idCliente);
        Cliente cliente = clienteOptional.orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado"));

        SaldoDTO saldo = new SaldoDTO(cliente.getSaldo(), LocalDateTime.now(), cliente.getLimite());
        List<Transacao> top10Transacoes = transacaoRepository.findTop10ByCliente_IdOrderByRealizadaEmDesc(idCliente);
        return new RetornoExtratoDTO(saldo, top10Transacoes.stream()
                .map(TransacaoOutputDTO::fromTransacao).collect(Collectors.toList()));
    }
}
