package com.goncalvesmail.rinhabackend.repository;

import com.goncalvesmail.rinhabackend.model.Cliente;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class ClienteRepositoryTest {
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void createClient_ValidData_ReturClient() {
        Cliente novoCliente = clienteRepository.save(Cliente.novoCliente("Daniel",1000L, 0L));
        Cliente cliente = testEntityManager.find(Cliente.class, novoCliente.getId());

        assertThat(cliente.getSaldo()).isEqualTo(0L);
        assertThat(cliente.getLimite()).isEqualTo(1000L);
        assertThat(cliente.getNome()).isEqualTo("Daniel");
    }
}
