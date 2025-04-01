package br.com.floresdev.contador_comite_back.services;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.floresdev.contador_comite_back.domain.despesa.dto.CostSummaryDTO;
import br.com.floresdev.contador_comite_back.domain.financeiro.ReportPeriod;
import br.com.floresdev.contador_comite_back.domain.financeiro.ReportDTO;
import br.com.floresdev.contador_comite_back.domain.repositories.DespesaRepository;
import br.com.floresdev.contador_comite_back.domain.repositories.VendaRepository;
import br.com.floresdev.contador_comite_back.domain.venda.dto.ProductSummaryDTO;
import br.com.floresdev.contador_comite_back.domain.venda.dto.ProductSummaryTroteDTO;

@Service
public class ReportService {
    
    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private DespesaRepository despesaRepository;

    /**
     * Ok, o que preciso fazer é o seguinte:
     * Com base num ReportPeriod fornecido, preciso obter as datas de início e fim de cálculo.
     * Com base nessas datas, faço o cálculo de receita total, lucro total e despesas totais.
     */
     public List<LocalDate> getReportDates(ReportPeriod period) {
        LocalDate today = LocalDate.now();

        switch (period) {
            case ReportPeriod.DIARIO -> {
                return List.of(today, today);
            }

            case ReportPeriod.SEMANAL -> {
                int daysSinceMonday = today.getDayOfWeek().getValue() - DayOfWeek.MONDAY.getValue();
                LocalDate startOfWeek = today.minusDays(daysSinceMonday); // Início comercial da semana (segunda-feira)
                LocalDate endOfWeek = startOfWeek.plusDays(4); // Fim comercial da semana (sexta-feira)

                return List.of(startOfWeek, endOfWeek);
            }

            case ReportPeriod.MENSAL -> {
                LocalDate startOfMonth = today.withDayOfMonth(1);
                LocalDate endOfMonth = today.withDayOfMonth(today.lengthOfMonth());

                return List.of(startOfMonth, endOfMonth);
            }

            case null, default -> throw new IllegalArgumentException("Invalid period: " + period);
        }        
     }

     public ReportDTO getReport(String period, boolean trote) {
        try {
            var reportPeriod = ReportPeriod.fromString(period);
            var dates = getReportDates(reportPeriod);

            ProductSummaryTroteDTO incomeTrote = null;
            ProductSummaryTroteDTO profitTrote = null;

            if (trote) {
                incomeTrote = getProductSummaryTrote(dates, false);
                profitTrote = getProductSummaryTrote(dates, true);
            }

            return new ReportDTO(
                getTotalIncome(dates),
                getTotalProfit(dates),
                getTotalCosts(dates),
                getProductSummary(dates, false),
                getProductSummary(dates, true),
                incomeTrote,
                profitTrote,
                getSpecificCosts(dates)
            );

        } catch (IllegalArgumentException e) {
            throw e;
        }
     }

     private BigDecimal getTotalIncome(List<LocalDate> dates) {
        return vendaRepository.getTotalIncome(dates.get(0), dates.get(1));
     }

     private BigDecimal getTotalProfit(List<LocalDate> dates) {
        return vendaRepository.getTotalProfit(dates.get(0), dates.get(1));
     }

     private BigDecimal getTotalCosts(List<LocalDate> dates) {
         return despesaRepository.getTotalCosts(dates.get(0), dates.get(1));
     }

     private List<CostSummaryDTO> getSpecificCosts(List<LocalDate> dates) {
         return despesaRepository.getSpecificCosts(dates.get(0), dates.get(1));
     }

     private ProductSummaryDTO getProductSummary(List<LocalDate> dates, boolean... profit) {
        if (profit.length > 0) return vendaRepository.getProductSummary(dates.get(0), dates.get(1), profit[0]);

        return vendaRepository.getProductSummary(dates.get(0), dates.get(1), false);
     }

     private ProductSummaryTroteDTO getProductSummaryTrote(List<LocalDate> dates, boolean... profit) {
        if (profit.length > 0) return vendaRepository.getProductSummaryTrote(dates.get(0), dates.get(1), profit[0]);

        return vendaRepository.getProductSummaryTrote(dates.get(0), dates.get(1), false);
     }
}
