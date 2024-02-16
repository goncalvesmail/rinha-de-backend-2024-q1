package com.goncalvesmail.rinhabackend.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.goncalvesmail.rinhabackend.exception.NegocioException;
import com.goncalvesmail.rinhabackend.model.Transacao;
import org.junit.jupiter.api.Test;

public class TransacaoDTOTest {

    @Test
    public void toTransacao_DadosValidos_RetornaTransacao() throws NegocioException {
        TransacaoDTO dto = new TransacaoDTO("1000","c", "transacao", null);
        Transacao transacao = dto.toTransacao();
        assertThat(dto.descricao()).isEqualTo(transacao.getDescricao());
        assertThat(transacao.getRealizadaEm()).isNotNull();
    }

    @Test
    public void toTransacao_nullDescription_NegocioException() {
        TransacaoDTO dto = new TransacaoDTO("1000","c", null, null);
        assertThatThrownBy(dto::toTransacao).isInstanceOf(NegocioException.class);
    }

    @Test
    public void toTransacao_ValorDecimal_NegocioException() {
        TransacaoDTO dto = new TransacaoDTO("10.50","c", "trasacao", null);
        assertThatThrownBy(dto::toTransacao).isInstanceOf(NegocioException.class);
    }

    @Test
    public void toTransacao_TransacaoInvalida_NegocioException() {
        TransacaoDTO dto = new TransacaoDTO("10.50","x", "trasacao", null);
        assertThatThrownBy(dto::toTransacao).isInstanceOf(NegocioException.class);
    }
}
