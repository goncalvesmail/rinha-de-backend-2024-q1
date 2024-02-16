package com.goncalvesmail.rinhabackend.usecase;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.goncalvesmail.rinhabackend.exception.ClienteNaoEncontradoException;
import com.goncalvesmail.rinhabackend.exception.NegocioException;
import com.goncalvesmail.rinhabackend.model.Cliente;
import com.goncalvesmail.rinhabackend.model.Transacao;
import com.goncalvesmail.rinhabackend.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CriarTransacaoTest {
    @Mock
    private ClienteRepository clienteRepository;
    @InjectMocks
    private CriarTransacao criarTransacao;

    @Test
    public void criarTransacao_DadosValidos_RetornaClient() throws NegocioException, ClienteNaoEncontradoException {
        Cliente clienteFake = new Cliente(1L, "Daniel", 1000L, 0L);
        Optional<Cliente> clienteFakeOptional = Optional.of(clienteFake);
        Transacao transacao = Transacao.novaTransacao("1","c","credito");

        when(clienteRepository.findById(anyLong())).thenReturn(clienteFakeOptional);
        when(clienteRepository.save(any())).thenReturn(clienteFake);

        criarTransacao.execute(1L, transacao);
        assertThat(clienteFake.getTransacoes().size()).isEqualTo(1);
        assertThat(clienteFake.getSaldo()).isEqualTo(1L);
    }

    @Test
    public void criarTransacao_TipoTransacaoInvalido_NegocioException() throws NegocioException {
        Cliente clienteFake = new Cliente(1L, "Daniel", 1000L, 0L);
        Optional<Cliente> clienteFakeOptional = Optional.of(clienteFake);
        Transacao transacao = Transacao.novaTransacao("1","x","credito");

        when(clienteRepository.findById(anyLong())).thenReturn(clienteFakeOptional);

        assertThatThrownBy(() ->criarTransacao.execute(1L, transacao)).isInstanceOf(NegocioException.class);
    }

    @Test
    public void criarTransacao_LimiteInvalido_NegocioException() throws NegocioException, ClienteNaoEncontradoException {
        Cliente clienteFake = new Cliente(1L, "Daniel", 10L, 0L);
        Optional<Cliente> clienteFakeOptional = Optional.of(clienteFake);
        Transacao transacao = Transacao.novaTransacao("1000","d","credito");

        when(clienteRepository.findById(anyLong())).thenReturn(clienteFakeOptional);

        assertThatThrownBy(() ->criarTransacao.execute(1L, transacao)).isInstanceOf(NegocioException.class);
    }

    @Test
    public void criarTransacao_UsuarioInvalido_UsuarioNaoEncontradoException() throws NegocioException {
        Optional<Cliente> clienteFakeOptional = Optional.empty();
        Transacao transacao = Transacao.novaTransacao("1","c","credito");

        when(clienteRepository.findById(anyLong())).thenReturn(clienteFakeOptional);
        assertThatThrownBy(() -> criarTransacao.execute(6L, transacao))
                .isInstanceOf(ClienteNaoEncontradoException.class);
    }
}
