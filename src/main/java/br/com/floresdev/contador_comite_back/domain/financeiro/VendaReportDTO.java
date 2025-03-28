package br.com.floresdev.contador_comite_back.domain.financeiro;

import java.math.BigDecimal;
import java.util.List;
import br.com.floresdev.contador_comite_back.domain.venda.dto.VendaDTO;

public record VendaReportDTO(
    BigDecimal totalIncome, 
    BigDecimal totalProfit,
    BigDecimal specificIncome,
    String specificIncomePercentage,
    List<VendaDTO> vendas
) {}
