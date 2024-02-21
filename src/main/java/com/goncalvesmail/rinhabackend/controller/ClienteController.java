package com.goncalvesmail.rinhabackend.controller;

import com.goncalvesmail.rinhabackend.dto.RetornoExtratoDTO;
import com.goncalvesmail.rinhabackend.dto.RetornoTransacaoDTO;
import com.goncalvesmail.rinhabackend.dto.TransacaoDTO;
import com.goncalvesmail.rinhabackend.exception.ClienteNaoEncontradoException;
import com.goncalvesmail.rinhabackend.exception.NegocioException;
import com.goncalvesmail.rinhabackend.model.Cliente;
import com.goncalvesmail.rinhabackend.usecase.CriarTransacao;
import com.goncalvesmail.rinhabackend.usecase.RecuperaExtrato;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.BlockingQueue;

@RestController("clientes")
@RequestMapping("/clientes")
public class ClienteController {
    private final CriarTransacao criarTransacao;
    private final RecuperaExtrato recuperaExtrato;

    public ClienteController(
            CriarTransacao criarTransacao, RecuperaExtrato recuperaExtrato) {
        this.criarTransacao = criarTransacao;
        this.recuperaExtrato = recuperaExtrato;
    }

    @PostMapping("/{id}/transacoes")
    public ResponseEntity<RetornoTransacaoDTO> criarTransacao(
            @PathVariable Long id, @RequestBody TransacaoDTO transacaoDTO ) throws NegocioException, ClienteNaoEncontradoException {
        Cliente cliente = criarTransacao.execute(id, transacaoDTO.toTransacao());

        return ResponseEntity.ok(new RetornoTransacaoDTO(cliente.getLimite(), cliente.getSaldo()));
    }

    @GetMapping("/{id}/extrato")
    public ResponseEntity<RetornoExtratoDTO> recuperaExtrato(@PathVariable Long id) throws ClienteNaoEncontradoException {
        RetornoExtratoDTO extratoDTO = recuperaExtrato.execute(id);
        return ResponseEntity.ok(extratoDTO);
    }
}