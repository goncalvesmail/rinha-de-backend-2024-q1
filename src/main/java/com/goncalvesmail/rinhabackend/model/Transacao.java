package com.goncalvesmail.rinhabackend.model;

import com.goncalvesmail.rinhabackend.exception.NegocioException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "transacoes")
public class Transacao {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private Long valor;
    private String tipo;
    private String descricao;
    @Column(name = "realizada_em")
    private LocalDateTime realizadaEm;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    private static Logger logger = LoggerFactory.getLogger(Transacao.class);

    public static Transacao novaTransacao(String valor, String tipo, String descricao) throws NegocioException {
        Long valorConvertido = validarValor(valor);
        validarTipo(tipo);
        validarDescricao(descricao);
        Transacao output = new Transacao();
        output.setDescricao(descricao);
        output.setTipo(tipo);
        output.setValor(valorConvertido);
        output.setRealizadaEm(LocalDateTime.now());

        return output;
    }

    private static  void validarDescricao(String descricao) throws NegocioException {
        if(descricao == null ||
                descricao.isEmpty() ||
                descricao.isBlank() ||
                descricao.length() > 10) {
            throw new NegocioException("Descrição não pode ser null e deve ser menor que 10");
        }
    }
    private static  void validarTipo(String tipo) throws NegocioException {
        if (tipo == null ||
                tipo.isEmpty() ||
                tipo.isBlank() &&
                (!tipo.equals("c") && !tipo.equals("d"))) {
            throw new NegocioException("Tipo da transação deve ser c ou d");
        }
    }
    private static Long validarValor(String valor) throws NegocioException {
        if(valor == null || valor.isBlank() || valor.isEmpty()) {
            throw new NegocioException("O valor é obrigatório e maio que zero");
        }
        long valorConvertido;
        try {
            valorConvertido = Long.parseLong(valor);
            if(valorConvertido < 0) {
                throw new NegocioException("O valor tem que ser maior que zero");
            }
            return valorConvertido;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new NegocioException("O valor tem que ser sermpre inteiro");
        }
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Transacao transacao = (Transacao) o;
        return getId() != null && Objects.equals(getId(), transacao.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
