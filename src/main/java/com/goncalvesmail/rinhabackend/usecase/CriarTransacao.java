package com.goncalvesmail.rinhabackend.usecase;

import com.goncalvesmail.rinhabackend.exception.ClienteNaoEncontradoException;
import com.goncalvesmail.rinhabackend.exception.NegocioException;
import com.goncalvesmail.rinhabackend.model.Cliente;
import com.goncalvesmail.rinhabackend.model.Transacao;
import com.goncalvesmail.rinhabackend.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.BlockingQueue;

@Service
public class CriarTransacao {
    private final ClienteRepository clienteRepository;
    private final BlockingQueue<Cliente> clienteQueue;
    public CriarTransacao(ClienteRepository clienteRepository, BlockingQueue<Cliente> clienteQueue) {
        this.clienteRepository = clienteRepository;
        this.clienteQueue = clienteQueue;
    }

    @Transactional
    public Cliente execute(Long idCliente, Transacao transacao) throws NegocioException, ClienteNaoEncontradoException {
        Optional<Cliente> clienteOptional = clienteRepository.findById(idCliente);
        if(clienteOptional.isEmpty()) {
            throw new ClienteNaoEncontradoException("Cliente não encontrado");
        }
        Cliente cliente = clienteOptional.get();
        clienteQueue.add(cliente);
        cliente.atualizarSaldo(transacao);
        //return clienteRepository.save(cliente);
        return cliente;
    }
}