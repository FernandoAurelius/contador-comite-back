package br.com.floresdev.contador_comite_back.domain.repositories;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.floresdev.contador_comite_back.domain.venda.Venda;
import br.com.floresdev.contador_comite_back.domain.venda.dto.ProductSummaryDTO;
import br.com.floresdev.contador_comite_back.domain.venda.dto.ProductSummaryTroteDTO;

public interface VendaRepository extends JpaRepository<Venda, Long> {

       List<Venda> findByDate(LocalDate date);

       @Query("SELECT SUM(v.totalPrice) FROM Venda v WHERE v.date >= :startDate AND v.date <= :endDate")
       BigDecimal getTotalIncome(LocalDate startDate, LocalDate endDate);

       @Query("SELECT SUM(v.totalPrice - v.purchasePrice) FROM Venda v WHERE v.date >= :startDate AND v.date <= :endDate")
       BigDecimal getTotalProfit(LocalDate startDate, LocalDate endDate);
       
       @Query(
          "SELECT new br.com.floresdev.contador_comite_back.domain.venda.dto.ProductSummaryDTO(" + 
          "    CASE WHEN :profit = true THEN SUM(v.totalPrice - v.purchasePrice) ELSE SUM(v.totalPrice) END," +
          "    " +
          "    SUM(CASE WHEN v.itemType = 'REFRI_COPO' " +
          "            THEN (CASE WHEN :profit = true " +
          "                      THEN v.totalPrice - v.purchasePrice " +
          "                      ELSE v.totalPrice END) " +
          "            ELSE 0 END), " +
          "    (SUM(CASE WHEN v.itemType = 'REFRI_COPO' " + 
          "            THEN (CASE WHEN :profit = true " +
          "                      THEN v.totalPrice - v.purchasePrice " +
          "                      ELSE v.totalPrice END) " +
          "            ELSE 0 END) * 100.0) / NULLIF(SUM(v.totalPrice), 0), " +
          "    " +
          "    SUM(CASE WHEN v.itemType = 'REFRI_GARRAFA' " +
          "            THEN (CASE WHEN :profit = true " +
          "                      THEN v.totalPrice - v.purchasePrice " +
          "                      ELSE v.totalPrice END) " +
          "            ELSE 0 END), " +
          "    (SUM(CASE WHEN v.itemType = 'REFRI_GARRAFA' " + 
          "            THEN (CASE WHEN :profit = true " +
          "                      THEN v.totalPrice - v.purchasePrice " +
          "                      ELSE v.totalPrice END) " +
          "            ELSE 0 END) * 100.0) / NULLIF(SUM(v.totalPrice), 0), " +
          "    " +
          "    SUM(CASE WHEN v.itemType = 'PICOLE' " +
          "            THEN (CASE WHEN :profit = true " +
          "                      THEN v.totalPrice - v.purchasePrice " +
          "                      ELSE v.totalPrice END) " +
          "            ELSE 0 END), " +
          "    (SUM(CASE WHEN v.itemType = 'PICOLE' " + 
          "            THEN (CASE WHEN :profit = true " +
          "                      THEN v.totalPrice - v.purchasePrice " +
          "                      ELSE v.totalPrice END) " +
          "            ELSE 0 END) * 100.0) / NULLIF(SUM(v.totalPrice), 0)" +
          ") " +
          "FROM Venda v " + 
          "WHERE v.date BETWEEN ?1 AND ?2")
       ProductSummaryDTO getProductSummary(LocalDate startDate, LocalDate endDate, boolean profit);

       @Query("SELECT new br.com.floresdev.contador_comite_back.domain.venda.dto.ProductSummaryTroteDTO(" + 
                 "    SUM(CASE WHEN v.itemType = 'CARTELA_BINGO' " +
                 "            THEN (CASE WHEN :profit = true " +
                 "                      THEN v.totalPrice - v.purchasePrice " +
                 "                      ELSE v.totalPrice END) " +
                 "            ELSE 0 END), " +
                 "    (SUM(CASE WHEN v.itemType = 'CARTELA_BINGO' " + 
                 "            THEN (CASE WHEN :profit = true " +
                 "                      THEN v.totalPrice - v.purchasePrice " +
                 "                      ELSE v.totalPrice END) " +
                 "            ELSE 0 END) * 100.0) / NULLIF(SUM(v.totalPrice), 0), " +
                 "    SUM(CASE WHEN v.itemType = 'CADEIA_DO_AMOR' " +
                 "            THEN (CASE WHEN :profit = true " +
                 "                      THEN v.totalPrice - v.purchasePrice " +
                 "                      ELSE v.totalPrice END) " +
                 "            ELSE 0 END), " +
                 "    (SUM(CASE WHEN v.itemType = 'CADEIA_DO_AMOR' " + 
                 "            THEN (CASE WHEN :profit = true " +
                 "                      THEN v.totalPrice - v.purchasePrice " +
                 "                      ELSE v.totalPrice END) " +
                 "            ELSE 0 END) * 100.0) / NULLIF(SUM(v.totalPrice), 0), " +
                 "    SUM(CASE WHEN v.itemType = 'CORREIO_ELEGANTE' " +
                 "            THEN (CASE WHEN :profit = true " +
                 "                      THEN v.totalPrice - v.purchasePrice " +
                 "                      ELSE v.totalPrice END) " +
                 "            ELSE 0 END), " +
                 "    (SUM(CASE WHEN v.itemType = 'CORREIO_ELEGANTE' " + 
                 "            THEN (CASE WHEN :profit = true " +
                 "                      THEN v.totalPrice - v.purchasePrice " +
                 "                      ELSE v.totalPrice END) " +
                 "            ELSE 0 END) * 100.0) / NULLIF(SUM(v.totalPrice), 0), " +
                 "    SUM(CASE WHEN v.itemType = 'OUTROS' " +
                 "            THEN (CASE WHEN :profit = true " +
                 "                      THEN v.totalPrice - v.purchasePrice " +
                 "                      ELSE v.totalPrice END) " +
                 "            ELSE 0 END), " +
                 "    (SUM(CASE WHEN v.itemType = 'OUTROS' " + 
                 "            THEN (CASE WHEN :profit = true " +
                 "                      THEN v.totalPrice - v.purchasePrice " +
                 "                      ELSE v.totalPrice END) " +
                 "            ELSE 0 END) * 100.0) / NULLIF(SUM(v.totalPrice), 0)" +
                 ") FROM Venda v " +
                 "WHERE v.date BETWEEN ?1 AND ?2")
       ProductSummaryTroteDTO getProductSummaryTrote(LocalDate startDate, LocalDate endDate, boolean profit);

}
