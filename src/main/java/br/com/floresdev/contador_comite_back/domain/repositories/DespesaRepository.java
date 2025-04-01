package br.com.floresdev.contador_comite_back.domain.repositories;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.floresdev.contador_comite_back.domain.despesa.Despesa;
import br.com.floresdev.contador_comite_back.domain.despesa.dto.CostSummaryDTO;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    Optional<List<Despesa>> findByDate(LocalDate date);

    @Query("SELECT SUM(d.totalCost) FROM Despesa d WHERE d.date BETWEEN ?1 AND ?2")
    BigDecimal getTotalCosts(LocalDate startDate, LocalDate endDate); 

    @Query("SELECT new br.com.floresdev.contador_comite_back.domain.despesa.dto.CostSummaryDTO(" +
           "  d.item," +
           "  SUM(d.totalCost)" +
           ") " +
           "FROM Despesa d " +
           "WHERE d.date BETWEEN :startDate AND :endDate " +
           "GROUP BY d.item")
    List<CostSummaryDTO> getSpecificCosts(LocalDate startDate, LocalDate endDate);
}
