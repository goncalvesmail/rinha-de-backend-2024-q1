package com.goncalvesmail.rinhabackend.repository;

import com.goncalvesmail.rinhabackend.model.Transacao;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransacaoRepository extends CrudRepository<Transacao, Long> {

    List<Transacao> findTop10ByCliente_IdOrderByRealizadaEmDesc(Long id);
}
