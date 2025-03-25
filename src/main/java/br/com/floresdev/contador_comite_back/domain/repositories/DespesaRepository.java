package br.com.floresdev.contador_comite_back.domain.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.floresdev.contador_comite_back.domain.despesa.Despesa;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    List<Despesa> findByDate(LocalDate date);

}
