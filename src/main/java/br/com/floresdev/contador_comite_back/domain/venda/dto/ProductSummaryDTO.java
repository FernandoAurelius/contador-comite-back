package br.com.floresdev.contador_comite_back.domain.venda.dto;

import java.math.BigDecimal;

public record ProductSummaryDTO(
    BigDecimal totalIncome,

    BigDecimal copoSum,
    Double copoPercentage,

    BigDecimal garrafaSum,
    Double garrafaPercentage,

    BigDecimal picoleSum,
    Double picolePercentage
) {}
