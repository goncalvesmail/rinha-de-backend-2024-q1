package com.goncalvesmail.rinhabackend;

import static org.assertj.core.api.Assertions.assertThat;

import com.goncalvesmail.rinhabackend.dto.RetornoExtratoDTO;
import com.goncalvesmail.rinhabackend.dto.RetornoTransacaoDTO;
import com.goncalvesmail.rinhabackend.dto.TransacaoDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Objects;

@ActiveProfiles("it")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/inserir_clientes.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class ClienteIT {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void inclusaoTransacao_ValidarOrdenacao() {
        ResponseEntity<RetornoTransacaoDTO> t1 = restTemplate.postForEntity(
                "/clientes/1/transacoes",
                TransacaoDTO.novaTransacaoDTO("1", "c", "toma"), RetornoTransacaoDTO.class);
        assertThat(t1.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(t1.getBody()).saldo()).isEqualTo(1);

        ResponseEntity<RetornoTransacaoDTO> t2 = restTemplate.postForEntity(
                "/clientes/1/transacoes",
                TransacaoDTO.novaTransacaoDTO("1", "d", "devolve"), RetornoTransacaoDTO.class);
        assertThat(t2.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(t2.getBody()).saldo()).isEqualTo(0);


        restTemplate.postForEntity(
                "/clientes/1/transacoes",
                TransacaoDTO.novaTransacaoDTO("1", "d", "ultimo"), RetornoTransacaoDTO.class);


        ResponseEntity<RetornoExtratoDTO> extrato = restTemplate.getForEntity("/clientes/1/extrato", RetornoExtratoDTO.class);
        assertThat(extrato.getStatusCode()).isEqualTo(HttpStatus.OK);
        RetornoExtratoDTO body = extrato.getBody();
        assertThat(Objects.requireNonNull(body).saldo().total()).isEqualTo(-1L);
        assertThat(Objects.requireNonNull(body).ultimasTransacoes().getFirst().descricao()).isEqualTo("ultimo");
        assertThat(Objects.requireNonNull(body).ultimasTransacoes().getLast().descricao()).isEqualTo("toma");
    }
}
