package com.goncalvesmail.rinhabackend.model;

import com.goncalvesmail.rinhabackend.exception.NegocioException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;

public class ClienteTest {
    @Test
    public void atualizarSaldo_Credito_RetornoOk() throws NegocioException {
        Cliente cliente = new Cliente(1L, "Daniel", 100000L, 0L);
        cliente.atualizarSaldo(Transacao.novaTransacao("100000","c","credita"));

        assertThat(cliente.getSaldo()).isEqualTo(100000L);
        assertThat(cliente.getTransacoes().size()).isEqualTo(1);
    }

    //@Test Não está funcionando pois a data da transação está ficando tudo igual
    public void atualizarSaldo_CreditoDebito_ValidaExtrato() throws NegocioException {
        Cliente cliente = new Cliente(1L, "Daniel", 100000L, 0L);
        cliente.atualizarSaldo(Transacao.novaTransacao("1","c","toma"));
        cliente.atualizarSaldo(Transacao.novaTransacao("1","d","devolve"));
        List<Transacao> transacoesOrdenadas = cliente.getTransacoes().stream().sorted(
                Comparator.comparing(Transacao::getRealizadaEm, Comparator.reverseOrder())).toList();
        assertThat(cliente.getSaldo()).isEqualTo(0);
        assertThat(transacoesOrdenadas.size()).isEqualTo(2);
        assertThat(transacoesOrdenadas.getFirst().getDescricao()).isEqualTo("devolve");
    }

    @Test
    public void atualizarSaldo_DebitoSemLimite_NegocioException() throws NegocioException {
        Cliente cliente = new Cliente(1L, "Daniel", 100L, 0L);
        assertThatThrownBy(() -> cliente.atualizarSaldo(
                Transacao.novaTransacao("1000","d","devolve"))).isInstanceOf(NegocioException.class);
    }

    @Test
    public void atualizarSaldo_TipoErrado_NegocioException() throws NegocioException {
        Cliente cliente = new Cliente(1L, "Daniel", 100L, 0L);
        assertThatThrownBy(() -> cliente.atualizarSaldo(
                Transacao.novaTransacao("1000","x","devolve"))).isInstanceOf(NegocioException.class);
    }
}
