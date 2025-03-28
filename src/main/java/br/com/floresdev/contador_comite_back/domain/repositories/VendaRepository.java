package br.com.floresdev.contador_comite_back.domain.repositories;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.floresdev.contador_comite_back.domain.venda.Venda;


public interface VendaRepository extends JpaRepository<Venda, Long>{
    List<Venda> findByDate(LocalDate date);

    // @Query(nativeQuery = true, value= "SELECT SUM(v.total_price) FROM vendas v WHERE v.date >= :startDate AND v.date <= :endDate")
    @Query("SELECT SUM(v.totalPrice) FROM Venda v WHERE v.date >= :startDate AND v.date <= :endDate")
    BigDecimal getTotalIncome(LocalDate startDate, LocalDate endDate);

    @Query("SELECT SUM(v.totalPrice - v.purchasePrice) FROM Venda v WHERE v.date >= :startDate AND v.date <= :endDate")
    BigDecimal getTotalProfit(LocalDate startDate, LocalDate endDate);

    // TODO: query pra pegar as porcentagens específicas de cada venda dentro do contexto geral do lucro para aquele período
    // Ex.: com base no lucro total, pegar a porcentagem de cada tipo de item vendido

}
