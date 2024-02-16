package com.goncalvesmail.rinhabackend.model;

import com.goncalvesmail.rinhabackend.exception.NegocioException;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.*;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue
    private Long id;
    private String nome;
    private Long limite;
    private Long saldo;
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @OrderBy("realizadaEm DESC")
    private List<Transacao> transacoes = new ArrayList<>();

    public Cliente(Long id, String nome, Long limite, Long saldo) {
        this.id = id;
        this.nome = nome;
        this.limite = limite;
        this.saldo = saldo;
    }

    public static Cliente novoCliente(String nome, Long limite, Long saldo) {
        Cliente output = new Cliente();
        output.setSaldo(saldo);
        output.setNome(nome);
        output.setLimite(limite);
        return output;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Cliente cliente = (Cliente) o;
        return getId() != null && Objects.equals(getId(), cliente.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    public void atualizarSaldo(Transacao transacao) throws NegocioException {
        if(transacao.getTipo().equals("c")) {
            this.saldo = this.saldo + transacao.getValor();
            this.getTransacoes().add(transacao);
            transacao.setCliente(this);
        } else if (transacao.getTipo().equals("d")) {
            long novoSaldo = this.saldo - transacao.getValor();
            if(novoSaldo < this.limite*-1) {
                throw new NegocioException("Limite indisponível");
            }
            this.saldo = novoSaldo;
            this.getTransacoes().add(transacao);
            transacao.setCliente(this);
        } else {
            throw new NegocioException("Tipo de transação não aceito");
        }
    }
}
