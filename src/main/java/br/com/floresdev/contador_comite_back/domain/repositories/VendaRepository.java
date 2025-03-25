package br.com.floresdev.contador_comite_back.domain.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.floresdev.contador_comite_back.domain.venda.Venda;


public interface VendaRepository extends JpaRepository<Venda, Long>{
    List<Venda> findByDate(LocalDate date);
}
