package br.com.floresdev.contador_comite_back.domain.financeiro;

import java.math.BigDecimal;
import java.util.List;

import br.com.floresdev.contador_comite_back.domain.despesa.dto.CostSummaryDTO;
import br.com.floresdev.contador_comite_back.domain.venda.dto.ProductSummaryDTO;
import br.com.floresdev.contador_comite_back.domain.venda.dto.ProductSummaryTroteDTO;

public record ReportDTO(
    BigDecimal totalIncome, 
    BigDecimal totalProfit,
    BigDecimal totalCosts,
    ProductSummaryDTO income,
    ProductSummaryDTO profit,
    ProductSummaryTroteDTO incomeTrote,
    ProductSummaryTroteDTO profitTrote,
    List<CostSummaryDTO> costs
) {
    public ReportDTO {

    }
}
