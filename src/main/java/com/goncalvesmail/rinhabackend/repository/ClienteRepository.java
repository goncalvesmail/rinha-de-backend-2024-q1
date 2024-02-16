package com.goncalvesmail.rinhabackend.repository;

import com.goncalvesmail.rinhabackend.model.Cliente;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClienteRepository extends CrudRepository<Cliente, Long> {
   /* @Query("select c from Cliente c where c.id = ?1")
    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    public Optional<Cliente> findByIdPessimisticWrite(Long idLong);*/
}
